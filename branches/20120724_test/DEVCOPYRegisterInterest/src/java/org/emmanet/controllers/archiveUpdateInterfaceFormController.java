/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
//import java.util.Calendar;
import java.util.ArrayList;
import java.util.Date;
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
import org.apache.velocity.app.VelocityEngine;
import org.emmanet.jobs.WebRequests;
import org.emmanet.model.ArchiveManager;

import org.emmanet.model.BackgroundDAO;
import org.emmanet.model.BackgroundManager;
import org.emmanet.model.CVAvailabilitiesDAO;

import org.emmanet.model.ProjectsStrainsDAO;
import org.emmanet.model.Sources_StrainsDAO;
import org.emmanet.model.StrainsDAO;
import org.emmanet.model.StrainsManager;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author phil
 */
public class archiveUpdateInterfaceFormController extends SimpleFormController {

    private String requestsSuccessView;
    private ArchiveManager am = new ArchiveManager();
    private StrainsManager sm = new StrainsManager();
    private Object command;
    private StrainsDAO sDAO = (StrainsDAO) command;
    private StrainsDAO sd;
    private ProjectsStrainsDAO psd;
    private Sources_StrainsDAO ssd;
    private String baseURL;
    private String recentBaseURL;
    private String serverPDFLocation;
    private String serverPDFLocationTail;
    private String serverPDFRecentLocation;
    private MailSender mailSender;
    private JavaMailSender javaMailSender;
    private VelocityEngine velocityEngine;
    private SimpleMailMessage simpleMailMessage;
    private String archArrvTemplate;
    private String archFrzeTemplate;
    private String archCompTemplate;
    private String archCompCTemplate;
    private String templatePath;
    private String msgSubject;
    private String[] cc;
    private String strippedDate;
    private String thisisthebase;
    private String mtaPath;
    private String emailFailAlert = "";
    private Map model = new HashMap();
    private String fileLocation;
    private BackgroundManager bm = new BackgroundManager();
    private String testMailRecipient;

    @Override
    protected Object formBackingObject(HttpServletRequest request) {
        if (request.getParameter("EditArch") != null) {
            Integer strainID = Integer.parseInt(request.getParameter("EditArch"));
            //Check that background list file has been created by overnight job, if not create it to prevent error
            try {
                BufferedReader in = new BufferedReader(new FileReader(getFileLocation() + "bgNamesList.emma"));
                in.close();

            } catch (IOException e) {
                createList();
            }
            /*
            CODE BELOW TO CONVERT STRINGS TO DATES IF WE WORK WITH DATES RATHER THAN STRINGS
            Date date = (Date) aDAO.getArchived();
            calendar.setTime(date);
            ArchiveDAO aDAO = (ArchiveDAO) command;
            System.out.println("OK archivedao instantited");
            aDAO.setArchived(date);
             */

            //build pdf string
            // int archID = sm.getArchID(strainID);
            sd = sm.getStrainByID(strainID);
            //  ad = am.getReqByID(archID);
            //String subFile = "sub_";
            //baseURL = this.getBaseURL();
            //String date = sd.getArchiveDAO().getSubmitted();
            //@@@@@
            // String date = ad.getSubmitted();
            //@@@@@
            
            
            /*
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
                subFile = subFile + strippedDate;
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
                            if (filename.startsWith(subFile)) {
                                subFile = filename;
                                fileFound = true;
                            } else {
                                System.out.println("No file with this name " + subFile + " here");
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

                    am.setPdfURL(pdfUrl);

                    sd.getArchiveDAO().setPdfURL(pdfUrl);

                } else {
                    System.out.println("No file can be found with the name " + subFile);
                }
            }
            //TODO YUK THIS IS ORRIBLE ANOTHER FUGLEY HACK ADD URL TO SESSION COOKIE ENABLING RETRIEVAL IN JSP PAGE
             * 
             */
                 
            HttpSession session = request.getSession(true);

            //session.setAttribute("pdfUrl", sd.getArchiveDAO().getPdfURL());
            
      
            
            //strainsavailabilities is a pain to retrieve atm needs work add to session
            // SET IN SESSION THE MTAPATH
            session.setAttribute("mtaPath", this.getMtaPath());
            List as = am.getStrainAvail(strainID);
            String cvavailDetails = "";
            CVAvailabilitiesDAO cvaDAO = new CVAvailabilitiesDAO();
            System.out.println("size is ::" + as.size());
            for (Iterator it = as.listIterator(); it.hasNext();) {

                System.out.println(as.size());
                Object[] o = (Object[]) it.next();
                String availid = o[1].toString();
                int availID = Integer.parseInt(availid);
                cvaDAO = (CVAvailabilitiesDAO) am.getCVAvail(availID);
                String inStock = "";
                String toDist = "";
                if (cvaDAO.getIn_stock() == 0 || cvaDAO.getTo_distr() == 0) {
                    inStock = "No";
                    toDist = "No";
                } else {
                    inStock = "Yes";
                    toDist = "Yes";
                }
                cvavailDetails = cvavailDetails + cvaDAO.getDescription() + " ::-    In stock: " + inStock + " ::-   To distribute: " + toDist + "||";
                System.out.println(cvavailDetails);

            }
            session.setAttribute("cvAvail", cvavailDetails);
            session.setAttribute("AvaillCount", as.size());

            List person = sm.getStrainsAndPerson(strainID);
            //end changes
            for (Iterator it = person.iterator(); it.hasNext();) {
                Object[] element = (Object[]) it.next();


                model.put("emmaid", element[0]);
                model.put("strainname", element[3]);
                model.put("strainid", element[8]);
                model.put("title", element[11]);
                model.put("surname", element[9]);
            }

            String MSGcontentArrv = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                    getTemplatePath() + getArchArrvTemplate(), model);

            String MSGcontentFrz = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                    getTemplatePath() + getArchFrzeTemplate(), model);

            String MSGcontentComp = "";
            if (sd.getStr_access().equals("C")) {
                MSGcontentComp = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                        getTemplatePath() + getArchCompCTemplate(), model);
            } else {
                MSGcontentComp = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                        getTemplatePath() + getArchCompTemplate(), model);
            }

            sd.setMSGcontentArrv(MSGcontentArrv);
            sd.setMSGcontentFrz(MSGcontentFrz);
            sd.setMSGcontentComp(MSGcontentComp);
            return sd;
        }
        return sd;
    }

    @Override
    protected ModelAndView onSubmit(
            HttpServletRequest request,
            HttpServletResponse response,
            Object command,
            BindException errors)
            throws ServletException, Exception {
        if (request.getParameter("available_to_order") != null) {
            /* String orderAvailability = request.getParameter("available_to_order");
            System.out.println("We are hitting save, this is ato param value:-- " + orderAvailability);
            
            StrainsDAO sd = sm.getStrainByID( Integer.parseInt(request.getParameter("EditArch")) );
            sd.setAvailable_to_order(request.getParameter("available_to_order"));
            sm.save(sd);
            System.out.println("strainsDAO saved");*/
        }

        sDAO = (StrainsDAO) command;

        //TODO CODE BELOW TO CONVERT STRINGS TO DATES IF WE WORK WITH DATES RATHER THAN STRINGS
        /*
        aDAO.getArchived();
        Date date = (Date) aDAO.getArchived();
        calendar.setTime(date);
        aDAO.setArchived(date);
        ;*/
        //set msg for email
        /*
         * 
         * 
         * 
         * 
         * 
         * 
         *         MimeMessage message = getJavaMailSender().createMimeMessage();
        try {
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setReplyTo("emma@emmanet.org");
        helper.setFrom("emma@emmanet.org");
         * 
         * 
         * */
        if (request.getParameter("sendEmailArch").equals("yes") ||
                request.getParameter("sendEmailFreeze").equals("yes") ||
                request.getParameter("sendEmailComplete").equals("yes")) {
            String content = "";
            String toAddress = "";
            ////::  SimpleMailMessage msg = getSimpleMailMessage();
            FileSystemResource file = new FileSystemResource(new File(fileLocation + "EMMAImportReportForm.doc"));
            try {
                MimeMessage message = getJavaMailSender().createMimeMessage();
                MimeMessageHelper msg = new MimeMessageHelper(message, true, "UTF-8");
                msg.setSubject(getMsgSubject());
                msg.setReplyTo("emma@emmanet.org");
                msg.setFrom("emma@emmanet.org");

                //  int strainID = aDAO.getStr_id_str();
                //changes here to resolve subversion/damaian change
                Integer strainID = Integer.parseInt(request.getParameter("EditArch"));
                List person = sm.getStrainsAndPerson(strainID);
                //end changes
                for (Iterator it = person.iterator(); it.hasNext();) {
                    Object[] element = (Object[]) it.next();

                    toAddress = (String) element[10];
                    model.put("emmaid", element[0]);
                    model.put("strainname", element[3]);
                    model.put("strainid", element[8]);
                    model.put("title", element[11]);
                    model.put("surname", element[9]);
                    if (toAddress == null) {
                        System.out.println("toAddress value is null and will be reset to an empty string:: " + toAddress);
                        toAddress = "";
                    }
                    System.out.println("toAddress value is:: " + toAddress);

                }
                msg.setTo(toAddress);

                // START CCs
                String[] ccs = getCc();
                System.out.println(ccs.length);
                List lCc = new ArrayList();
                for (int i = 0; i < ccs.length; i++) {
                    //add configurated cc addresses to list
                    String CcElement = ccs[i];
                    lCc.add(CcElement);
                }
                //NOW get arch centre email address and add to list
                WebRequests wr = new WebRequests();
                List ccCentre = wr.ccArchiveMailAddresses(strainID);
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
                
                msg.setCc(ar);
                //END
                if (request.getParameter("TEST") != null) {
                    //test for mail functionality use test address from parameter
                    setTestMailRecipient(request.getParameter("TEST"));
                    msg.setTo(this.getTestMailRecipient());
                    msg.setBcc(this.getTestMailRecipient());
                    msg.setCc(this.getTestMailRecipient());
                    emailFailAlert = " A TEST EMAIL TO " + this.getTestMailRecipient() +" HAS BEEN DISPATCHED";
                    
                }
                if (request.getParameter("sendEmailArch").equals("yes")) {
                    //we need to send a mail
                    msg.setSubject(getMsgSubject() + " of strain " + model.get("strainname"));
                    content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                            getTemplatePath() + getArchArrvTemplate(), model);
                    msg.setText(content);
                    
                    if (!toAddress.equals("")) {
                        System.out.println("msg1==" + msg);
                        ///::  getJavaMailSender().send(msg);
                        getJavaMailSender().send(message);
                    }
                }
                if (request.getParameter("sendEmailFreeze").equals("yes")) {
                    msg.setSubject(getMsgSubject() + " of strain " + model.get("strainname"));
                    content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                            getTemplatePath() + getArchFrzeTemplate(), model);
                    msg.setText(content);
                    if (!toAddress.equals("")) {
                        System.out.println("msg2==" + msg);
                        ///::  getJavaMailSender().send(msg);
                        getJavaMailSender().send(message);
                    }
                }

                if (request.getParameter("sendEmailComplete").equals("yes") && sDAO.getStr_access().equals("C")) {
                    msg.setSubject(getMsgSubject() + " of strain " + model.get("strainname"));
                    content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                            getTemplatePath() + getArchCompCTemplate(), model);
                    msg.setText(content);
                    //add attach here
                    msg.addAttachment("EMMA_Import_Report_Form.doc", file);
                    if (!toAddress.equals("")) {
                        System.out.println("msg3==" + msg);
                        ///::  getJavaMailSender().send(msg);
                        getJavaMailSender().send(message);
                    }
                } else if (request.getParameter("sendEmailComplete").equals("yes")) {
                    msg.setSubject(getMsgSubject() + " of strain " + model.get("strainname"));
                    content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                            getTemplatePath() + getArchCompTemplate(), model);
                    msg.setText(content);
                    //add attach here
                    msg.addAttachment("EMMA_Import_Report_Form.doc", file);
                    if (!toAddress.equals("")) {
                        System.out.println("msg4==" + msg);
                        getJavaMailSender().send(message);
                    }


                }
                System.out.println("sendEmailComplete==" + request.getParameter("sendEmailComplete"));
                System.out.println("sendEmailFreeze==" + request.getParameter("sendEmailFreeze"));
                System.out.println("sendEmailArch==" + request.getParameter("sendEmailArch"));
                System.out.println("toAddress==" + toAddress);
                //equals("")
                if (toAddress.isEmpty() &&
                        (request.getParameter("sendEmailComplete").equals("yes") ||
                        request.getParameter("sendEmailFreeze").equals("yes") ||
                        request.getParameter("sendEmailArch").equals("yes"))) {
                    //NEED AN ALERT
                    emailFailAlert = " BUT ** an e-mail was unable to be sent due to the recipient not having an address in the database **";
                }
            } catch (MessagingException ex) {
                ex.printStackTrace();
            }
        }
//SANGER SPECIFIC FROZEN EMBRYOS
        if (!sDAO.getArchiveDAO().getFrozen_sanger_embryos_arrived().equals("")) {
            System.out.println("Frozen Sanger material field has a value, alter strain_status to ARCHD");
            sDAO.setStr_status("ARCHD");
        }

        //now save sourceid and projectid
        if (!request.getParameter("sourceID").isEmpty() || !request.getParameter("projectID").isEmpty()) {

            System.out.println("sourceID and projectID values are not null");

            int sour_id = Integer.parseInt(request.getParameter("sourceID"));
            int project_id = Integer.parseInt(request.getParameter("projectID"));
            psd = new ProjectsStrainsDAO();


            ssd = sm.getSourcesByID(sDAO.getId_str());
            psd = sm.getProjectsByID(sDAO.getId_str());
            boolean saveOnlyPsd = false;
            boolean saveOnlySsd = false;
            try {
                psd.getProject_id();
            } catch (NullPointerException np) {
                psd = new ProjectsStrainsDAO();
                psd.setStr_id_str(sDAO.getId_str());

                psd.setProject_id(project_id);

                System.out.println(" id string is " + sDAO.getId_str());
                //save only here not save or update
                saveOnlyPsd = true;
            }

            try {
                ssd.getSour_id();
            } catch (NullPointerException np) {
                ssd = new Sources_StrainsDAO();
                ssd.setStr_id_str(sDAO.getId_str());
                ssd.setSour_id(sour_id);
                //save only here not save or update
                saveOnlySsd = true;
            }


            if (saveOnlyPsd) {
                sm.saveOnlyPsd(psd);
                System.out.println("Projects Strains saved using saveOnly");
            } else if (!saveOnlyPsd) {
                psd.setProject_id(project_id);
                sm.savePsd(psd);
                System.out.println("Projects Strains saved");
            }

            if (saveOnlySsd) {
                sm.saveOnlySsd(ssd);
                System.out.println("Sources Strains saved using saveOnly");
            } else if (!saveOnlySsd) {
                ssd.setSour_id(sour_id);
                sm.saveSsd(ssd);
                System.out.println("Sources Strains saved");
            }


        }


        if (request.getParameter("male_bg_id_new") != null) {
            //This is a new entry for the table backgrounds
            String newBGName = request.getParameter("male_bg_id_new");

            List bd = bm.getBGDAOByName(newBGName);
            if (bd.size() == 0) {
                //OK lets add a new one
                BackgroundDAO bDAO = sDAO.getBackgroundDAO();//. new BackgroundDAO();
                bDAO.setName(newBGName);

                bm.save(bDAO);
                // System.out.println("WOOHOO== " + bDAO.getId_bg() + " BGNAME= " + bDAO.getName());
                sDAO.getArchiveDAO().setMale_bg_id("" + bDAO.getId_bg() + "");

                //Now regenerate list
                createList();

            }
        }
        if (request.getParameter("female_bg_id_new") != null) {
            //This is a new entry for the table backgrounds
            String newBGName = request.getParameter("female_bg_id_new");

            List bd = bm.getBGDAOByName(newBGName);
            if (bd.size() == 0) {
                //OK lets add a new one
                BackgroundDAO bDAO = sDAO.getBackgroundDAO();//new BackgroundDAO();
                bDAO.setName(newBGName);

                bm.save(bDAO);
                // System.out.println("WOOHOO== " + bDAO.getId_bg() + " BGNAME= " + bDAO.getName());
                sDAO.getArchiveDAO().setFemale_bg_id("" + bDAO.getId_bg() + "");

                //Now regenerate list
                createList();
            }
        }


        sm.saveArchive(sDAO);
        System.out.println("saved");
        request.getSession().setAttribute(
                "message",
                getMessageSourceAccessor().getMessage("Message",
                "Your update submitted successfully" + emailFailAlert));
        emailFailAlert = "";
        return new ModelAndView(getSuccessView() + "?EditArch=" + request.getParameter("EditArch"));
    // ModelAndView mav = showForm(request, response, errors);
    //return mav;
    }

    public void createList() {
        //TODO CALL METHOD IN SCHEDULED JOB OR TRIGGER SCHEDULED JOB
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(getFileLocation() + "bgNamesList.emma"));
            List backGrounds = bm.getBackgrounds();
            for (Iterator it = backGrounds.listIterator(); it.hasNext();) {
                Object[] o = (Object[]) it.next();
                out.write(o[0].toString() + "||");
                out.write(o[1].toString());
                out.newLine();
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //TODO - MAY NOT BE NEEDED IF WE CONTINUE TO WORK WITH DATES AS STRINGS
    public Date formatDateString(String submittedDate) {
        SimpleDateFormat formatted = new SimpleDateFormat();
        Date date = new Date();
        ParsePosition pos = new ParsePosition(0);
        date = formatted.parse(submittedDate, pos);
        return date;
    }

    public String getRequestsSuccessView() {
        return requestsSuccessView;
    }

    public void setRequestsSuccessView(String requestsSuccessView) {
        this.requestsSuccessView = requestsSuccessView;
    }

    public String getBaseURL() {
        return baseURL;
    }

    public void setBaseURL(String baseURL) {
        this.baseURL = baseURL;
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

    public String getRecentBaseURL() {
        return recentBaseURL;
    }

    public void setRecentBaseURL(String recentBaseURL) {
        this.recentBaseURL = recentBaseURL;
    }

    public String getServerPDFRecentLocation() {
        return serverPDFRecentLocation;
    }

    public void setServerPDFRecentLocation(String serverPDFRecentLocation) {
        this.serverPDFRecentLocation = serverPDFRecentLocation;
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

    public String getArchArrvTemplate() {
        return archArrvTemplate;
    }

    public void setArchArrvTemplate(String archArrvTemplate) {
        this.archArrvTemplate = archArrvTemplate;
    }

    public String getArchFrzeTemplate() {
        return archFrzeTemplate;
    }

    public void setArchFrzeTemplate(String archFrzeTemplate) {
        this.archFrzeTemplate = archFrzeTemplate;
    }

    public String getArchCompTemplate() {
        return archCompTemplate;
    }

    public void setArchCompTemplate(String archCompTemplate) {
        this.archCompTemplate = archCompTemplate;
    }

    public String getTemplatePath() {
        return templatePath;
    }

    public void setTemplatePath(String templatePath) {
        this.templatePath = templatePath;
    }

    public String getMsgSubject() {
        return msgSubject;
    }

    public void setMsgSubject(String msgSubject) {
        this.msgSubject = msgSubject;
    }

    public String[] getCc() {
        return cc;
    }

    public void setCc(String[] cc) {
        this.cc = cc;
    }

    public String getMtaPath() {
        return mtaPath;
    }

    public void setMtaPath(String mtaPath) {
        this.mtaPath = mtaPath;
    }

    public String getArchCompCTemplate() {
        return archCompCTemplate;
    }

    public void setArchCompCTemplate(String archCompCTemplate) {
        this.archCompCTemplate = archCompCTemplate;
    }

    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public String getTestMailRecipient() {
        return testMailRecipient;
    }

    public void setTestMailRecipient(String testMailRecipient) {
        this.testMailRecipient = testMailRecipient;
    }
}
