/*
 * RequestFormController.java
 *
 * Created on 19 December 2007, 09:32
 *
 * Form controller to carry out actions on form to support
 * register interest requests and retrieve previously submitted
 * register an interest requests. Creates supporting request xml/pdf files and 
 * confirmation messages. Submitted data validated by
 * org.emmanet.controllers.RequestFormValidator
 *
 * Dependencies - org.emmanet.jobs.WebRequests 
 * org.emmanet.model.WebRequestsDAO
 *
 * v1.0 released 04-02-2008
 * v1.1 released 14-02-2008 - xml files no longer need to be sent with e-mail message
 * Removal requested by sabine.fessele@helmholtz-muenchen.de
 * Removed by philw@ebi.ac.uk 14-02-2008
 *
 */

package org.emmanet.controllers;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.Color;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.velocity.app.VelocityEngine;

import org.emmanet.jobs.WebRequests;
import org.emmanet.model.WebRequestsDAO;

import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;


/**
 *
 * @author Phil Wilkinson
 */
public class RequestFormController extends SimpleFormController {
    
    private JavaMailSender javaMailSender;
    
    private WebRequests webRequest;
    public static final String ID = "1";
    private List requestByID;
    
    private MailSender mailSender;
    private VelocityEngine velocityEngine;
    private String pathToXml;//TODO USE ACCESSOR METHOD TO SET FROM
    private File file;
    private String xmlFileName;
    private String pdfFile;
    private String xmlExt = ".xml";
    private String pdfExt = ".pdf";
    private String mailSubjectText;
    /* pdfTitle determined according to request being register
     * interest or new request 
    */
    private String pdfTitle;
    private boolean pdfConditions;
    private String Bcc;
    private Map Cc = new HashMap();
    
@Override
    protected Object formBackingObject(HttpServletRequest request) {
        /*
         * ADDED TO SOLVE ISSUE WHERE MISSING PARAM CAUSES  formBackingObject() must not be null ERROR
         * THIS ALLOWS FORM TO DISPLAY FOR NEW REQUESTS
         */
        if (request.getParameter("ID") != null) {
            return webRequest.getReqByID(request.getParameter("ID").toString());  
        } else {
            WebRequestsDAO wr = new WebRequestsDAO();
            if (request.getParameter("wr") != null) {
                wr.setRegister_interest("1");
            }
            if (request.getParameter("sname") != null) {
                wr.setStrain_name(request.getParameter("sname").toString());
            }
            if (request.getParameter("id") != null) {
                wr.setStrain_id(request.getParameter("id").toString());
            }
            
            if (request.getParameter("str_id_id") == null) {
                String toFormat = request.getParameter("id");
                int start = 3;
                int end = toFormat.length();
                toFormat = toFormat.substring(start, end);
                int i = Integer.parseInt(toFormat);
                
                wr.setStr_id_str(i);
            }
            return wr;
        }
    }
 @Override   
    protected ModelAndView onSubmit(
            HttpServletRequest request,
            HttpServletResponse response,
            Object command,
            BindException errors) {
        
        WebRequests wr = new WebRequests();
        WebRequestsDAO webRequest = (WebRequestsDAO) command;
        
        wr.saveRequest(webRequest);
        request.getSession().setAttribute(
                "message",
                getMessageSourceAccessor().getMessage("Message",
                webRequest.getSci_firstname() + " " + webRequest.getSci_surname() + ", Your request submitted successfully, you will receive " +
                "confirmation by e-mail sent to the address " + webRequest.getSci_e_mail()));
        // Late stage addition for multiple cc addresses TODO MAKE BETTER
        Cc.put("1", new String ("sabine.fessele@helmholtz-muenchen.de"));
        Cc.put("2", new String ("Michael.hagn@helmholtz-muenchen.de"));
        Cc.put("3", new String ("emma@emmanet.org"));
        //TODO PULL ARCHIVE CENTRE MAIL ADDRESSES FROM DATABASE TO CC IN
        // Create hashmap for velocity TODO may not need investigate when time allows
        Map model = new HashMap();
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
        model.put("strain_id", webRequest.getStrain_id());
        model.put("strain_name", webRequest.getStrain_name());
        model.put("common_name_s", webRequest.getCommon_name_s());
        model.put("req_material", webRequest.getReq_material());
        model.put("live_animals", webRequest.getLive_animals());
        model.put("frozen_emb", webRequest.getFrozen_emb());
        model.put("frozen_spe", webRequest.getFrozen_spe());
        // E-mail content
        //Added to address final request email for reg int strains
        System.out.println(webRequest.getRegister_interest());
        String velocTemplate = null;
        
        if (webRequest.getRegister_interest().equals("0")) {
            /* Set values for template to be used, PDF Title & mail message subject text
             * for strain submission
             */
            velocTemplate = "org/emmanet/util/velocitytemplates/submissionConfirmation-Template.vm";
            pdfTitle = "EMMA Mutant Request Form";
            //TODO SET MAIL SUBJECT LINE HERE
            mailSubjectText = "Your Request from EMMA - confirmation of receipt: ";
            pdfConditions = true;
        } else if (webRequest.getRegister_interest().equals("1")) {
            /* Set values for template to be used, PDF Title & mail message subject text
             * for registration of interest
             */
            velocTemplate = "org/emmanet/util/velocitytemplates/interestSubmission-Template.vm";
             pdfTitle = "EMMA Strain Interest Registration Form";
             mailSubjectText = "Your EMMA Strain Interest Registration Form - confirmation of receipt: "; 
        }
        System.out.println("Templ8 to be used is : " + velocTemplate);//"org/emmanet/util/velocitytemplates/interestSubmission-Template.vm"
        String content = VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(),
               velocTemplate, model);
        // XML file content Template created by Velocity
        String xmlFileContent = VelocityEngineUtils.mergeTemplateIntoString(getVelocityEngine(),
                "org/emmanet/util/velocitytemplates/requestXml-Template.vm", model);
        System.out.print(xmlFileContent);
        // Format strain id to correct 0 padding TODO MAY NEED TO GO NOT NECESSARY HERE AS DONE ABOVE
        NumberFormat formatter = new DecimalFormat("00000");
        String formattedID = formatter.format(webRequest.getStr_id_str());
        try {
            /* Create request XML file from velocity template
             * named to format req_TIMESTAMP_FIRSTNAME_SURNAME_STRAINID.xml
             */
            xmlFileName = "req_" + webRequest.getTimestamp() + "_"
                    + webRequest.getSci_firstname() + "_" + webRequest.getSci_surname()
                    + "_" + formattedID;
            file = new File(pathToXml + xmlFileName + xmlExt);
            // Create pdf from model
            pdfFile =
                    createPDF(model,pathToXml + xmlFileName + pdfExt);
            
            // Create file if it does not exist
            boolean success = file.createNewFile();
            if (success) {
                // File did not exist and was created
                Writer out = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(file), "UTF8"));
                out.write(xmlFileContent);
                out.close();
                
            } else {
                // File already exists
                // Do nothing
            }
        } catch (IOException e) {
            //TODO HANDLE
        }
        
        MimeMessage message = getJavaMailSender().createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setReplyTo("emma@emmanet.org");
            helper.setFrom("emma@emmanet.org");
            helper.setBcc(Bcc);
            System.out.println("bccADDRESS===== " + Bcc);
            //helper.setCc(Cc);
            Iterator it = Cc.values().iterator();
                    while (it.hasNext()) {
                        String ccAddress = (String) it.next();
                        System.out.println("ccADDRESS===== " + ccAddress);
                        helper.addCc(ccAddress);
                    }
            
            helper.setTo(webRequest.getSci_e_mail());
            
            helper.setSubject(mailSubjectText + webRequest.getStrain_id() + " ("
                    + webRequest.getStrain_name() + ")");
            helper.setText(content);
            /* xml files no longer need to be sent with e-mail message
             * Removal requested by sabine.fessele@helmholtz-muenchen.de
             * Removed by philw@ebi.ac.uk 14-02-2008
             */
            //helper.addAttachment(xmlFileName + xmlExt,file);
            FileSystemResource file = new FileSystemResource(new File(pdfFile));
            
            helper.addAttachment(xmlFileName + pdfExt,file);
            
            getJavaMailSender().send(message);
            
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
        
        return new ModelAndView(getSuccessView());
    }
    
    public String createPDF(Map model, String filePath) {
                /* I really don't like this iText library.
                 * Takes so much effort to figure it all out
                 * to get an acceptable pdf rendition. */
        
        Document doc = new Document();
        try {
            System.out.println(model.get("timestamp"));
            
            PdfWriter writer = PdfWriter.getInstance(doc, new FileOutputStream(
                    filePath));
            doc.open();
            Paragraph pHead = new Paragraph(
                    pdfTitle + "\n\n", FontFactory.getFont(
                    FontFactory.HELVETICA, 11));
            pHead.setAlignment(Element.ALIGN_CENTER);
            doc.add(pHead);
            doc.add(new Paragraph(pdfTitle + "\n\n",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20)));
            Paragraph pSubHead = new Paragraph(
                    "Following data have been submitted to EMMA on "
                    + model.get("ftimestamp"), FontFactory.getFont(
                    FontFactory.HELVETICA, 11));
            pSubHead.setAlignment(Element.ALIGN_CENTER);
            
            doc.add(pSubHead);
            doc.add(Chunk.NEWLINE);
            // Space padding underline
            Chunk underlined = new Chunk(
                    "                                                                                     "
                    + "                                                                       ");
            underlined.setUnderline(new Color(0x00, 0x00, 0x00), 0.0f, 0.2f,
                    16.0f, 0.0f, PdfContentByte.LINE_CAP_BUTT);//Black line
            doc.add(underlined);
            Font font = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11);
            // Set table cell widths equiv. to 25% and 75%
            float[] widths = { 0.25f, 0.75f };
            PdfPTable table = new PdfPTable(widths);
            
            table.setWidthPercentage(100);
            
            PdfPCell cell = new PdfPCell(new Paragraph("Scientist\n\n", font));
            cell.setColspan(2);
            cell.setBorder(0);
            
            table.addCell(cell);
            table.addCell("Title");
            table.addCell("" + model.get("sci_title"));
            table.addCell("Firstname");
            table.addCell("" + model.get("sci_firstname"));
            table.addCell("Surname");
            table.addCell("" + model.get("sci_surname"));
            table.addCell("E-mail");
            table.addCell("" + model.get("sci_e_mail"));
            table.addCell("Phone");
            table.addCell("" + model.get("sci_phone"));
            table.addCell("Fax");
            table.addCell("" + model.get("sci_fax"));
            PdfPCell cellShip = new PdfPCell(new Paragraph(
                    "\nShipping Contact\n\n", font));
            cellShip.setColspan(2);
            cellShip.setBorder(0);
            table.addCell(cellShip);
            
            table.addCell("Title");
            table.addCell("" + model.get("con_title"));
            table.addCell("Firstname");
            table.addCell("" + model.get("con_firstname"));
            table.addCell("Surname");
            table.addCell("" + model.get("con_surname"));
            table.addCell("E-mail");
            table.addCell("" + model.get("con_e_mail"));
            table.addCell("Phone");
            table.addCell("" + model.get("con_phone"));
            table.addCell("Fax");
            table.addCell("" + model.get("con_fax"));
            table.addCell("Institution");
            table.addCell("" + model.get("con_institution"));
            table.addCell("Department");
            table.addCell("" + model.get("con_dept"));
            table.addCell("Address Line 1");
            table.addCell("" + model.get("con_addr_1"));
            table.addCell("Address Line 2");
            table.addCell("" + model.get("con_addr_2"));
            table.addCell("County/province");
            table.addCell("" + model.get("con_province"));
            table.addCell("Town");
            table.addCell("" + model.get("con_town"));
            table.addCell("Postcode");
            table.addCell("" + model.get("con_postcode"));
            table.addCell("Country");
            table.addCell("" + model.get("con_country"));
            
            PdfPCell cellStrain = new PdfPCell(new Paragraph(
                    "\nStrain Details\n\n", font));
            cellStrain.setColspan(2);
            cellStrain.setBorder(0);
            
            table.addCell(cellStrain);
            table.addCell("Strain ID");
            table.addCell("" + model.get("strain_id"));
            table.addCell("Strain name");
            table.addCell("" + model.get("strain_name"));
            table.addCell("Common Name(s)");
            table.addCell("" + model.get("common_name_s"));
            
            if (model.get("req_material") != null) {
                PdfPCell cellMaterial = new PdfPCell(new Paragraph(
                        
                        "\nRequested Material\n\n", font));
                cellMaterial.setColspan(2);
                cellMaterial.setBorder(0);
                
                
                table.addCell(cellMaterial);
                table.addCell("Material");
                table.addCell("" + model.get("req_material"));
            }
            
            if (model.get("live_animals") != null) {
                table.addCell("Live Animals");
                table.addCell("Selected");
            }
            if (model.get("frozen_emb") != null) {
                table.addCell("Frozen Embryos");
                table.addCell("Selected");
            }
            if (model.get("frozen_spe") != null) {
                table.addCell("Frozen Sperm");
                table.addCell("Selected");
            }
            
            if (pdfConditions) {
                PdfPCell cellConditions = new PdfPCell(
            
                    new Paragraph(
                    "\nYou have indicated that you have read the conditions and agree to pay the transmittal fee "
                    + "plus shipping costs."));
            cellConditions.setColspan(2);
            cellConditions.setBorder(0);
            table.addCell(cellConditions);
            }
            doc.add(table);
            
        } catch (DocumentException de) {
            System.err.println(de.getMessage());
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        doc.close();
        return filePath;
    }
    
    public WebRequests getWebRequest() {
        return webRequest;
    }
    
    public void setWebRequest(WebRequests webRequest) {
        this.webRequest = webRequest;
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
    
    public String getPathToXml() {
        return pathToXml;
    }
    
    public void setPathToXml(String pathToXml) {
        this.pathToXml = pathToXml;
    }
    
    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }
    
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public String getBcc() {
        return Bcc;
    }

    public void setBcc(String Bcc) {
        this.Bcc = Bcc;
    }

    public Map getCc() {
        return Cc;
    }

    public void setCc(Map Cc) {
        this.Cc = Cc;
    }
}
