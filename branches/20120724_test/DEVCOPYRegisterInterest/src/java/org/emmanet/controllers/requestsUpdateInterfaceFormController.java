/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.emmanet.jobs.WebRequests;
import org.emmanet.model.WebRequestsDAO;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.validation.BindException;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.apache.velocity.app.VelocityEngine;
import org.apache.commons.lang.StringEscapeUtils;
import org.emmanet.model.ProjectsStrainsDAO;
import org.emmanet.model.RToolsDAO;
import org.emmanet.model.Sources_RequestsDAO;
import org.emmanet.model.StrainsDAO;
import org.emmanet.model.StrainsManager;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

/**
 *
 * @author phil
 */
public class requestsUpdateInterfaceFormController extends SimpleFormController {

    WebRequests wr = new WebRequests();
    WebRequestsDAO wrd = new WebRequestsDAO();
    private Map returnedOut = new HashMap();
    public static final String MAP_KEY = "returnedOut";
    private String subFile;
    private String baseURL;
    private String recentBaseURL;
    private String serverPDFLocation;
    private String serverPDFLocationTail;
    private String serverPDFRecentLocation;
    private String strippedDate;
    private String thisisthebase;
    private String surname;
    private String pdfURL;
    private MailSender mailSender;
    private JavaMailSender javaMailSender;
    private VelocityEngine velocityEngine;
    private SimpleMailMessage simpleMailMessage;
    private String[] cc;
    private String[] bcc;
    private String msgSubject;
    private String templatePath;
    private String taOrRequestYesTemplate;
    private String taOrRequestNoTemplate;
    private String taOnlyNoTemplate;
    private String sangerLineDistEmail;
    private Map model = new HashMap();
    private Sources_RequestsDAO srd;
    private String pathToMTA;
    private String fromAddress;

    @Override
    protected Object formBackingObject(HttpServletRequest request) {

        if (request.getParameter("Edit") != null) {
            subFile = "req_";
            setBaseURL((String) this.getBaseURL());
            wrd = wr.getReqByID(request.getParameter("Edit").toString());
            String date = wrd.getTimestamp();

            HttpSession session = request.getSession(true);
            //added to assist file location as perl code doesn't add a full timestamp to database when request submitted
            //   surname = wrd.getSci_surname().toLowerCase();
         /*   surname = request.getParameter("strainID").toString();

                 if (date != null) {
            
            strippedDate = date.replace("-", "");
            strippedDate = strippedDate.replace(":", "");
            strippedDate = strippedDate.replace(".", "");
            strippedDate = strippedDate.replace(" ", "");
            
            //get year to determine search directory
            String pdfYearDir = "PDF-";
            pdfYearDir = pdfYearDir + strippedDate.substring(0, 4);
            //Take out leading 2 digits of year
            strippedDate = strippedDate.substring(2, strippedDate.length() - 1);
            //server location of pdf to search for
            String locatePDF = getServerPDFLocation() + pdfYearDir + getServerPDFLocationTail();
            String pdfsources[] = {locatePDF, getServerPDFRecentLocation() + getServerPDFLocationTail()};
            Boolean fileFound = false;
            String pdfUrl = "";
            
            subFile = subFile + strippedDate.substring(0, 6);
            System.out.println("~~~surname is now strain id :) :: " + surname);
            //ok lets search for...
            for (int i = 0; i < pdfsources.length; i++) {
            File dir = new File(pdfsources[i]);
            locatePDF = pdfsources[i];
            System.out.println("SEARCHING DIRECTORY " + locatePDF + " FOR FILE " + subFile);
            if (i == 1) {
            System.out.println("Setting base url to (i=1 then) :: " + getRecentBaseURL());
            thisisthebase = getRecentBaseURL();
            } else if (i == 0) {
            System.out.println("Setting base url to (i=0 then):: " + getRecentBaseURL() + pdfYearDir);
            thisisthebase = getBaseURL() + pdfYearDir + this.getServerPDFLocationTail();
            }
            String[] children = dir.list();
            if (children == null) {
            // Either dir does not exist or is not a directory
            System.out.println("It appears that the directory " + locatePDF +
            " doesn't exist.");
            } else {
            System.out.println("Directory exists::" + locatePDF);
            for (int ii = 0; ii < children.length; ii++) {
            // Get filename of file or directory
            String filename = children[ii];
            System.out.println(filename + " located");
            System.out.println(subFile);
            
            if (filename.startsWith(subFile) && filename.indexOf(surname) != -1) {
            subFile = filename;
            fileFound = true;
            //test
            int iiii = filename.indexOf(surname);
            System.out.println("THE INDEx OF THE SUBSTRING SURNAME IS: " + iiii);
            } else {
            System.out.println("No file with this name " + subFile + " here " + filename.indexOf(surname));
            }
            
            if (fileFound) {
            System.out.println("OK file found let's stop the search");
            break;//stop searching now
            }
            }
            }
            if (fileFound) {
            break;
            }
            }//end of pdfsources loop
            
            if (fileFound) {
            //We have a pdf file located
            pdfUrl = null;
            pdfUrl = thisisthebase + subFile;
            setPdfURL(pdfUrl);
            
            System.out.println("Accessor method value for getPDF == " + getPdfURL());
           //  */
            
           //  session.setAttribute("reqPDF", this.getPdfURL());

           //  session.setAttribute("reqPDF", wrd.getId_req() + "_temp.pdf"/**/);
             session.setAttribute("reqID", wrd.getId_req());
          //  System.out.println("URL as retrieved from the session object is:-- " + session.getAttribute("reqpdfUrl"));
          //  if (request.getParameter("pdfView") != null) {
                //String pdfLocation = pdf.createRequestPDF(wrd, serverPDFLocation);
               // return new ModelAndView("redirect:"+ pdfLocation);
               

       //     }
            

        //      } else {
           // System.out.println("No file can be found with the name " + subFile);
          //  System.out.println("URL as retrieved from the session object on no location of file is:-- " + session.getAttribute("reqpdfUrl"));
        //    }
       //     }
            
            StrainsManager sm = new StrainsManager();
            int str_id_str = Integer.parseInt(request.getParameter("strainID").toString());
            ProjectsStrainsDAO psd = new ProjectsStrainsDAO();
            psd = sm.getProjectsByID(str_id_str);
            session.setAttribute("projectID", psd.getProject_id());
            //changes made to use rtools id=9 to determine eucomm strains as project id (3 for eucomm) is now used differently
            BigInteger iRTOOLS = sm.getRToolsCount(str_id_str);
            StrainsDAO sd = new StrainsDAO();
            RToolsDAO rtd = new RToolsDAO();
            int irtoolsID = 0;
            int iRtools = iRTOOLS.intValue();
            if (iRtools > 1) {

                List dupRtools = sm.getRToolsByID(str_id_str);
                String arr = dupRtools.toArray().toString();
                //  Object[] obj = (Object[]) dupRtools.get(0);
                System.out.println("RTOOLS LIST SIZE IS " + dupRtools.size());
                for (Iterator it = dupRtools.listIterator(); it.hasNext();) {
                    // Object[] obj = (Object[]) it.next();
                    RToolsDAO returnRTD = (RToolsDAO) it.next();
                    // Object obj = (Object) it.next();

                    irtoolsID = returnRTD.getRtls_id();

                    // irtoolsID = Integer.parseInt(obj.toString()) ;
                    // rtd=sm.getRTOOLSByID(irtoolsID);

                    // System.out.println("OBJECT2STRING IS :: " + rtd.getRtls_id());
                }


                // rtd=sm.get()RTOOLSByID(irtoolsID);

            } else if (iRtools == 1) {

                rtd = sm.getRtoolsByID(str_id_str);
                //TODO SCREEN FOR STRAINS WITH NO ENTRY IN RTOOLS_STRAINS TABLE
                irtoolsID = rtd.getRtls_id();

            } else {
                irtoolsID = 8;
            }

            sd = sm.getStrainByID(str_id_str);
            if (sd.getRtoolsDAO().size() != 0) {
                System.out.println("project/rtools id=" + rtd.getRtls_id());
                session.setAttribute("projectID", irtoolsID);//rtd.getRtls_id());
            } else {
                session.setAttribute("projectID", 8);
            }
            return wr.getReqByID(request.getParameter("Edit").toString());
        }

        return wr;
    }

    @Override
    protected ModelAndView onSubmit(
            HttpServletRequest request,
            HttpServletResponse response,
            Object command,
            BindException errors)
            throws ServletException, Exception {

        WebRequestsDAO webRequest = (WebRequestsDAO) command;
        
   

        if (!request.getParameter("noTAinfo").equals("true")) {
            String panelDecision = webRequest.getTa_panel_decision();

            String applicationType = webRequest.getApplication_type();

            if (panelDecision.equals("yes") || panelDecision.equals("no") && applicationType.contains("ta")) {
                //check if mail already sent by checking notes for string
                if (!webRequest.getNotes().contains("TA mail sent")) {

                    //Decision has been made therefore decision mails should be triggered
                    //SimpleMailMessage msg = getSimpleMailMessage();
                    MimeMessage msg = getJavaMailSender().createMimeMessage();
                    MimeMessageHelper helper = new MimeMessageHelper(msg, true, "UTF-8");
                    String content = "";
                    String toAddress = webRequest.getSci_e_mail();
                    // msg.setTo(toAddress);
                    helper.setTo(toAddress);
                    helper.setFrom(getFromAddress());

                    String[] ccs = getCc();
                    List lCc = new ArrayList();
                    for (int i = 0; i < ccs.length; i++) {
                        //add configurated cc addresses to list
                        String CcElement = ccs[i];
                        lCc.add(CcElement);
                    }
                    lCc.add(webRequest.getCon_e_mail());
                    List ccCentre = wr.ccArchiveMailAddresses(webRequest.getStr_id_str());

                    Object[] o = null;
                    Iterator it = ccCentre.iterator();
                    while (it.hasNext()) {
                        o = (Object[]) it.next();
                        lCc.add(o[1].toString());
                    }
                    String[] ar = new String[lCc.size()];
                    for (int i = 0; i < lCc.size(); i++) {
                        Object oo = lCc.get(i);
                        ar[i] = oo.toString();
                        System.out.println(oo.toString());
                    }

                    String[] bccs = getBcc();
                    //msg.
                    helper.setBcc(bccs);

                    //msg.
                    helper.setCc(ar);

                    /*  format date string */
                    String date = webRequest.getTimestamp().toString();
                    String yyyy = date.substring(0, 4);
                    String MM = date.substring(5, 7);
                    String dd = date.substring(8, 10);

                    date = dd + "-" + MM + "-" + yyyy;

                    model.put("name", webRequest.getSci_firstname() + " " + webRequest.getSci_surname());
                    model.put("emmaid", webRequest.getStrain_id().toString());
                    model.put("strainname", webRequest.getStrain_name());
                    model.put("timestamp", date);
                    model.put("sci_title", webRequest.getSci_title());
                    model.put("sci_firstname", webRequest.getSci_firstname());
                    model.put("sci_surname", webRequest.getSci_surname());
                    model.put("sci_e_mail", webRequest.getSci_e_mail());
                    model.put("strain_id", webRequest.getStrain_id());
                    model.put("strain_name", webRequest.getStrain_name());
                    model.put("common_name_s", webRequest.getCommon_name_s());
                    model.put("req_material", webRequest.getReq_material());
                    //new mta file inclusion
                    model.put("requestID", webRequest.getId_req());
                    StrainsManager sm = new StrainsManager();
                    StrainsDAO sd = sm.getStrainByID(webRequest.getStr_id_str());
                    if (!webRequest.getLab_id_labo().equals("4")) {
                        /*
                         * FOR LEGAL REASONS MTA FILE AND USAGE TEXT SHOULD NOT BE SHOWN FOR MRC STOCK.
                         * MRC WILL SEND MTA SEPARATELY (M.FRAY EMMA IT MEETING 28-29 OCT 2010)
                         */
                        model.put("mtaFile", sd.getMta_file());
                    }
                    //########################################################            

                    String rtoolsID = "";
                    List rtools = wr.strainRToolID(webRequest.getStr_id_str());
                    it = rtools.iterator();
                    while (it.hasNext()) {
                        Object oo = it.next();
                        rtoolsID = oo.toString();
                    }

                    model.put("rtoolsID", rtoolsID);
                    //TEMPLATE SELECTION

                    if (panelDecision.equals("yes")) {
                        //we need to send a mail
                        if (webRequest.getApplication_type().contains("ta_")) {
                            System.out.println(getTemplatePath() + getTaOrRequestYesTemplate());
                            content = VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(),
                                    getTemplatePath() + getTaOrRequestYesTemplate(), model);
                        }
                    }
                    /* webRequest.getTa_panel_decision().equals("no") */
                    if (panelDecision.equals("no")) {
                        System.out.println("panel decision == no ==");
                        if (applicationType.equals("ta_or_request")) {
                            System.out.println("path to template for ta_or_req and no==" + getTemplatePath() + getTaOrRequestNoTemplate());
                            content = VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(),
                                    getTemplatePath() + getTaOrRequestNoTemplate(), model);
                        }
                        if (applicationType.equals("ta_only")) {
                            //TODO IF NO AND TA_ONLY THEN REQ_STATUS=CANC
                            webRequest.setReq_status("CANC");
                            System.out.println("path to template for ta_only and no==" + getTemplatePath() + getTaOnlyNoTemplate());
                            content = VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(),
                                    getTemplatePath() + getTaOnlyNoTemplate(), model);
                        }
                    }

                    //send message
                    //msg.
                    helper.setSubject(msgSubject + webRequest.getStrain_name() + "(" + webRequest.getStrain_id() + ")");
                    //msg.
                    helper.setText(content);


                    //###/add mta file if associated with strain id
                    String mtaFile = "";
                    mtaFile = (new StringBuilder()).append(mtaFile).append(sd.getMta_file()).toString();

                    if (mtaFile != null || model.get("mtaFile").toString().equals("")) {
                        FileSystemResource fileMTA = new FileSystemResource(new File(getPathToMTA() + mtaFile));
                        //need to check for a valid mta filename use period extension separator, all mtas are either .doc or .pdf
                        if (fileMTA.exists() && fileMTA.toString().contains(".")) {
                            //M Hagn decided now to not send an MTA at this point as users should have already received one. Was confusing users to receive another philw@ebi.ac.uk 05042011
                            //helper.addAttachment(model.get("mtaFile").toString(), fileMTA);
                        }
                    }
                    System.out.println("Rtools=" + model.get("rtoolsID") + " and labID=" + model.get("labID"));
                    if (request.getParameter("TEST") != null) {
                        // TESTER SUBMITTED EMAIL ADDRESS
                        //msg.
                        helper.setTo(request.getParameter("TEST"));
                        //msg.
                        helper.setCc(request.getParameter("TEST"));
                        String Ccs = "";
                        for (int i = 0; i < ar.length; i++) {
                            Ccs = Ccs + ", " + ar[i];
                        }
                        //msg.
                        helper.setText("TEST EMAIL, LIVE EMAIL SENT TO " + toAddress + " CC'D TO :\n\n " + Ccs + " LIVE MESSAGE TEXT FOLLOWS BELOW::\n\n" + content);
                    }
                    if (!toAddress.equals("")) {
                        //Set notes to contain ta mail sent trigger
                        String notes = webRequest.getNotes();
                        notes = notes + "TA mail sent";
                        webRequest.setNotes(notes);
                    }
                    getJavaMailSender().send(msg);
                }
            }
        }

        if (request.getParameter("fSourceID") != null || !request.getParameter("fSourceID").equals("")) {
            //save requestfunding source ID
            boolean saveOnly = false;
            int sour_id = Integer.parseInt(request.getParameter("fSourceID"));
            int srcID = Integer.parseInt(webRequest.getId_req());
            System.out.println("fSourceID==" + srcID);
            srd = wr.getReqSourcesByID(srcID);//sm.getSourcesByID(srcID);

            //  
            try {
                srd.getSour_id();//ssd.getSour_id();
                srd.setSour_id(sour_id);
            } catch (NullPointerException np) {
                srd = new Sources_RequestsDAO();
                int reqID = Integer.parseInt(webRequest.getId_req());

                srd.setReq_id_req(reqID);

                srd.setSour_id(sour_id);
                //save only here not save or update
                saveOnly = true;

            }

            if (saveOnly) {
                wr.saveOnly(srd);
                System.out.println("THIS IS NOW SAVED:: SAVEONLY");
            } else if (!saveOnly) {
                //save or update
                System.out.println("SAVEORUPDATEONLY + value is.." + srd);
                wr.save(srd);
                System.out.println("THIS IS NOW SAVED:: SAVEORUPDATEONLY");
            }

        }


        
        
        wr.saveRequest(webRequest);
        request.getSession().setAttribute(
                "message",
                getMessageSourceAccessor().getMessage("Message",
                "Your update submitted successfully"));

        if (webRequest.getProjectID().equals("3") && webRequest.getLab_id_labo().equals("1961")) {
            //OK this is a Sanger/Eucomm strain and could be subject to charging so may need to send xml
            if (request.getParameter("currentReqStatus").contains("PR") && webRequest.getReq_status().equals("SHIP")) {

//status changed to shipped from a non cancelled request so subject to a charge
                model.put("name", webRequest.getSci_firstname() + " " + webRequest.getSci_surname());
                model.put("emmaid", webRequest.getStrain_id().toString());
                model.put("strainname", webRequest.getStrain_name());
                model.put("timestamp", webRequest.getTimestamp());
                model.put("ftimestamp", webRequest.getFtimestamp());
                model.put("sci_title", webRequest.getSci_title());
                model.put("sci_firstname", webRequest.getSci_firstname());
                model.put("sci_surname", webRequest.getSci_surname());
                model.put("sci_e_mail", webRequest.getSci_e_mail());
                model.put("sci_phone", webRequest.getSci_phone());
                model.put("sci_fax", webRequest.getSci_fax());
                model.put("con_title", webRequest.getCon_title());
                model.put("con_firstname", webRequest.getCon_firstname());
                model.put("con_surname", webRequest.getCon_surname());
                model.put("con_e_mail", webRequest.getCon_e_mail());
                model.put("con_phone", webRequest.getCon_phone());
                model.put("con_fax", webRequest.getCon_fax());
                model.put("con_institution", webRequest.getCon_institution());
                model.put("con_dept", webRequest.getCon_dept());
                model.put("con_addr_1", webRequest.getCon_addr_1());
                model.put("con_addr_2", webRequest.getCon_addr_2());
                model.put("con_province", webRequest.getCon_province());
                model.put("con_town", webRequest.getCon_town());
                model.put("con_postcode", webRequest.getCon_postcode());
                model.put("con_country", webRequest.getCon_country());

                //billing details

                if (!webRequest.getRegister_interest().equals("1")) {
                    model.put("PO_ref", webRequest.getPO_ref());
                    model.put("bil_title", webRequest.getBil_title());
                    model.put("bil_firstname", webRequest.getBil_firstname());
                    model.put("bil_surname", webRequest.getBil_surname());
                    model.put("bil_e_mail", webRequest.getBil_e_mail());
                    model.put("bil_phone", webRequest.getBil_phone());
                    model.put("bil_fax", webRequest.getBil_fax());
                    model.put("bil_institution", webRequest.getBil_institution());
                    model.put("bil_dept", webRequest.getBil_dept());
                    model.put("bil_addr_1", webRequest.getBil_addr_1());
                    model.put("bil_addr_2", webRequest.getBil_addr_2());
                    model.put("bil_province", webRequest.getBil_province());
                    model.put("bil_town", webRequest.getBil_town());
                    model.put("bil_postcode", webRequest.getBil_postcode());
                    model.put("bil_country", webRequest.getBil_country());
                    model.put("bil_vat", webRequest.getBil_vat());
                }
                //end biling details

                model.put("strain_id", webRequest.getStrain_id());
                model.put("strain_name", escapeXml(webRequest.getStrain_name()));
                model.put("common_name_s", webRequest.getCommon_name_s());
                model.put("req_material", webRequest.getReq_material());
                model.put("live_animals", webRequest.getLive_animals());
                model.put("frozen_emb", webRequest.getFrozen_emb());
                model.put("frozen_spe", webRequest.getFrozen_spe());
                // TA application details
                model.put("application_type", webRequest.getApplication_type());
                model.put("ta_eligible", webRequest.getEligible_country());

                if (webRequest.getApplication_type().contains("ta")) {
                    model.put("ta_proj_desc", webRequest.getProject_description());
                    model.put("ta_panel_sub_date", webRequest.getTa_panel_sub_date());
                    model.put("ta_panel_decision_date", webRequest.getTa_panel_decision_date());
                    model.put("ta_panel_decision", webRequest.getTa_panel_decision());
                } else {
                    model.put("ta_proj_desc", "");
                    model.put("ta_panel_sub_date", "");
                    model.put("ta_panel_decision_date", "");
                    model.put("ta_panel_decision", "");
                }
                model.put("ROI", webRequest.getRegister_interest());

                //now create xml file

                String xmlFileContent = VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(),
                        "org/emmanet/util/velocitytemplates/requestXml-Template.vm", model);

                File file = new File("/nfs/panda/emma/tmp/sangerlinedistribution.xml");
                Writer out = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(file), "UTF8"));
                out.write(xmlFileContent);
                out.close();

                //email file now
                MimeMessage message = getJavaMailSender().createMimeMessage();

                try {
                    MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
                    helper.setReplyTo("emma@emmanet.org");
                    helper.setFrom("emma@emmanet.org");
                    helper.setBcc(bcc);
                    helper.setTo(sangerLineDistEmail);
                    helper.setSubject("Sanger Line Distribution " + webRequest.getStrain_id());
                    helper.addAttachment("sangerlinedistribution.xml", file);
                    getJavaMailSender().send(message);
                } catch (MessagingException ex) {
                    ex.printStackTrace();
                }
            }
        }
        return new ModelAndView("redirect:requestsUpdateInterface.emma?Edit=" + request.getParameter("Edit").toString() + "&strainID=" + request.getParameter("strainID").toString() + "&archID=" + request.getParameter("archID").toString());
    }

    public String escapeXml(String strToEscape) {
        StringEscapeUtils seu = new StringEscapeUtils();
        return seu.escapeXml(strToEscape);

        // return strToEscape;
    }

    public String getSubFile() {
        return subFile;
    }

    public void setSubFile(String subFile) {
        this.subFile = subFile;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
    }

    public String getRecentBaseURL() {
        return recentBaseURL;
    }

    public void setRecentBaseURL(String recentBaseURL) {
        this.recentBaseURL = recentBaseURL;
    }

    public String getServerPDFLocation() {
        return serverPDFLocation;
    }

    public void setServerPDFLocation(String serverPDFLocation) {
        this.serverPDFLocation = serverPDFLocation;
    }

    public String getServerPDFLocationTail() {
        return serverPDFLocationTail;
    }

    public void setServerPDFLocationTail(String serverPDFLocationTail) {
        this.serverPDFLocationTail = serverPDFLocationTail;
    }

    public String getServerPDFRecentLocation() {
        return serverPDFRecentLocation;
    }

    public void setServerPDFRecentLocation(String serverPDFRecentLocation) {
        this.serverPDFRecentLocation = serverPDFRecentLocation;
    }

    public String getPdfURL() {
        return pdfURL;
    }

    public void setPdfURL(String pdfURL) {
        this.pdfURL = pdfURL;
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

    public String getMsgSubject() {
        return msgSubject;
    }

    public void setMsgSubject(String msgSubject) {
        this.msgSubject = msgSubject;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getTaOrRequestYesTemplate() {
        return taOrRequestYesTemplate;
    }

    public void setTaOrRequestYesTemplate(String taOrRequestYesTemplate) {
        this.taOrRequestYesTemplate = taOrRequestYesTemplate;
    }

    public String getTaOrRequestNoTemplate() {
        return taOrRequestNoTemplate;
    }

    public void setTaOrRequestNoTemplate(String taOrRequestNoTemplate) {
        this.taOrRequestNoTemplate = taOrRequestNoTemplate;
    }

    public String getTaOnlyNoTemplate() {
        return taOnlyNoTemplate;
    }

    public void setTaOnlyNoTemplate(String taOnlyNoTemplate) {
        this.taOnlyNoTemplate = taOnlyNoTemplate;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String[] getBcc() {
        return bcc;
    }

    public void setBcc(String[] bcc) {
        this.bcc = bcc;
    }

    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public String getSangerLineDistEmail() {
        return sangerLineDistEmail;
    }

    public void setSangerLineDistEmail(String sangerLineDistEmail) {
        this.sangerLineDistEmail = sangerLineDistEmail;
    }

    public String getPathToMTA() {
        return pathToMTA;
    }

    public void setPathToMTA(String pathToMTA) {
        this.pathToMTA = pathToMTA;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }
}
