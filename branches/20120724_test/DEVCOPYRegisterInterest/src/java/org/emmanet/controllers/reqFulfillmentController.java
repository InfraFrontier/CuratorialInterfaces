/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.controllers;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.velocity.app.VelocityEngine;
import org.emmanet.jobs.WebRequests;
import org.emmanet.model.WebRequestsDAO;
import org.emmanet.util.Encrypter;
import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 *
 * @author phil
 */
public class reqFulfillmentController implements Controller {

    private String msgSubject;
    private String cc;
    private String bcc;
    private String fromAddress;
    private String template;
    private String templatePath;
    private MailSender mailSender;
    private SimpleMailMessage simpleMailMessage;
    private JavaMailSender javaMailSender;
    private VelocityEngine velocityEngine;
    private Map Cc = new HashMap();
    private Encrypter enc=new Encrypter();

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        if (!request.getParameter("reqID").isEmpty() && !request.getParameter("fulfill").isEmpty());
        WebRequestsDAO wrd = new WebRequestsDAO();
        WebRequests wr = new WebRequests();

        wrd = wr.getReqByID(request.getParameter("reqID"));
        System.out.println(wrd.getId_req());
        //DONE REMOVE RESTRICTION ON EMAIL EMMA-158
        if (wrd.getRegister_interest() != null || !wrd.getRegister_interest().equals("0")) {
            ///OK to send mail
            Map model = new HashMap();
String encryptedID = enc.encrypt(wrd.getId_req());
encryptedID =   java.net.URLEncoder.encode(encryptedID, "UTF-8");
            model.put("name", wrd.getSci_firstname() + " " + wrd.getSci_surname());
            model.put("email", wrd.getSci_e_mail());
            model.put("emmaid", wrd.getStrain_id());
            model.put("strainname", wrd.getStrain_name());
            model.put("id", encryptedID);//new Integer(wrd.getId_req()));
            model.put("labid", wrd.getLab_id_labo());
            model.put("pid", wrd.getProjectID());
          
            String rtoolsID = "";
        List rtools = wr.strainRToolID(wrd.getStr_id_str());
        System.out.println("rtools size=" + rtools.size());
        Iterator itt = rtools.iterator();

        while (itt.hasNext()) {
            Object oo = itt.next();
            rtoolsID = oo.toString();
            System.out.println("rtoolsid=" + oo.toString() + "  -  labid=" + wrd.getLab_id_labo());
        }
        model.put("rtoolsID", rtoolsID);

            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setReplyTo(fromAddress);
            msg.setFrom(fromAddress);
           
            msg.setTo(wrd.getSci_e_mail());

            msg.setBcc(bcc);

            Cc.clear();
            Cc.put("1", new String("info@emmanet.org"));
            // Get responsible centre mail address(es) and add to map
            List ccCentre = wr.ccArchiveMailAddresses(wrd.getStr_id_str());
            String[] ccAddress = null;
            //map key + 1
            int im = 2;
            Object[] o = null;
            Iterator it = ccCentre.iterator();
            while (it.hasNext()) {
                o = (Object[]) it.next();
                Cc.put("" + im + "", o[1].toString());
                wrd.setLab_id_labo(o[0].toString());
                im++;
            }
// TODO ADD SHIPPING CONTACT EMAIL HERE NEED TO MAKE SURE WRD AND DAO ARE PULLING SHIPPING CONTACT DETAILS CURRENTLY NOT Cc.put(im, wrd.getS);
            ccAddress = new String[Cc.size()];
            Iterator it1 = Cc.entrySet().iterator();
            int iii = 0;
            while (it1.hasNext()) {
                Map.Entry mvalue = (Map.Entry) it1.next();
                ccAddress[iii] = (String) mvalue.getValue();
                iii++;
            }
             
            msg.setCc(ccAddress);

            msg.setSubject("Your EMMA Strain Interest Registration Form - "
                    + "strain can now be ordered: " + model.get("emmaid") + " (" + model.get("strainname") + ")");
            String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                    "org/emmanet/util/velocitytemplates/regInt-Template.vm", model);

            msg.setText(content);
            try {
                getJavaMailSender().send(msg);
                System.out.println(msg);
            } catch (MailException ex) {
                System.err.println(ex.getMessage());
            }

            Calendar cal = new GregorianCalendar();

// Get the components of the date

            int year = cal.get(Calendar.YEAR);
            int month = cal.get(Calendar.MONTH);
            int day = cal.get(Calendar.DAY_OF_MONTH);

            wrd.setNotes("Strain can now be ordered mail sent: " + day + "/" + (month + 1) + "/" + year + " by fullfillment option");
            wr.saveRequest(wrd);
        }
        //REMOVED RESTRICTION ON EMAIL EMMA-158
        HttpSession session = request.getSession(true);
        return new ModelAndView("redirect:requestsInterface.emma" + session.getAttribute("urlQstring") );

    }

    public String getMsgSubject() {
        return msgSubject;
    }

    public void setMsgSubject(String msgSubject) {
        this.msgSubject = msgSubject;
    }

    public String getCc() {
        return cc;
    }

    public void setCc(String cc) {
        this.cc = cc;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public MailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(MailSender mailSender) {
        this.mailSender = mailSender;
    }

    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }

    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }

    public SimpleMailMessage getSimpleMailMessage() {
        return simpleMailMessage;
    }

    public void setSimpleMailMessage(SimpleMailMessage simpleMailMessage) {
        this.simpleMailMessage = simpleMailMessage;
    }

    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
}
