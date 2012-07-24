/*
 * RegInterestJOB.java
 *
 * Created on 30 November 2007, 13:56
 *
 * Scheduled job to check for publicly available strains and alert subscribers who have registered an interest.
 *
 * Runs nightly according to schedule in applicationContext.xml to search database for strain records modified during the day
 * that have a str_status of 'ARCHD' and are publicly available. Compares these records to the list created when the job last
 * ran, held in file strainsAccepted, these records were not publicly available, having a str_status of 'ARRD'. If a match is
 * found then when they were last updated their status was changed to that of being publicly available. The database table
 * web_requests is then searched to determine if a user registered an interest in that strain, if so a personalized mail
 * message is sent via Spring framework mail utlising Velocity templating for the message body.
 *
 * v1.0 released 04-02-2008
 * v1.1 released 14-02-2008 - Added file copy code to readstrain() method 
 * to create copy of strain list to aid debugging in the event of failure
 *
 */



package org.emmanet.jobs;

//~--- non-JDK imports --------------------------------------------------------

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;
import java.util.Iterator;
import org.apache.velocity.app.VelocityEngine;

import org.emmanet.model.StrainsDAO;
import org.emmanet.model.WebRequestsDAO;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import org.springframework.mail.MailException;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.springframework.ui.velocity.VelocityEngineUtils;

//~--- JDK imports ------------------------------------------------------------

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author phil
 */
public class RegInterestJOB extends QuartzJobBean {
    
    // Strainlist file constant
    final static String ACCCEPTEDSTRAINS = "/nfs/panda/emma/tmp/strainsAccepted";//\ "/data/web/TESTsubmissions/strainsAccepted";
    List                list             = new LinkedList();
    
    // Scheduled MailSender test
    private MailSender     mailSender;
    private String         theDate;
    private String         theTime;
    private VelocityEngine velocityEngine;
    private Map Cc = new HashMap();
    private String Bcc;
    
    public void generateStrainList(String emmaId) {
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(ACCCEPTEDSTRAINS, true));
            
            out.write(emmaId);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
            
        }
    }
    
    public void readStrainList() {
        list = new ArrayList();
        
        try {
            BufferedReader in = new BufferedReader(new FileReader(ACCCEPTEDSTRAINS));
            String         str;
            
            while ((str = in.readLine()) != null) {
                list.add(str);
            }
            
            in.close();
            /* 
             * Added to create copy of strain list to aid debugging in the event of failure
             * philw@ebi.ac.uk 14-02-2008
             */
            // Create channel on the source
            FileChannel srcChannel = new FileInputStream(ACCCEPTEDSTRAINS).getChannel();
            
            // Create channel on the destination
            FileChannel dstChannel = new FileOutputStream(ACCCEPTEDSTRAINS + "." + theDate).getChannel();
            
            // Copy file contents from source to destination
            dstChannel.transferFrom(srcChannel, 0, srcChannel.size());
            
            // Close the channels
            srcChannel.close();
            dstChannel.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void RegInterest() {
        
        Cc.put("1", new String("info@emmanet.org"));
        Cc.put("2", new String("Michael.hagn@helmholtz-muenchen.de"));
        SimpleMailMessage msg = new SimpleMailMessage();
        
        msg.setReplyTo("emma@emmanet.org");
        msg.setFrom("emma@emmanet.org");
        
        Format formatter;
        
        // The four digit year
        formatter = new SimpleDateFormat("yyyy");
        
        // The two digit Month
        formatter = new SimpleDateFormat("MM");
        
        // The two digit day
        formatter = new SimpleDateFormat("dd");
        
        // Get today's date
        Date date = new Date();
        
        // Format date
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        theDate   = formatter.format(date);
        
        // TIME FORMAT
        // Get current hour in 24hr format
        formatter = new SimpleDateFormat("HH");
        
        // Get current minutes
        formatter = new SimpleDateFormat("mm");
        
        // Get current seconds
        formatter = new SimpleDateFormat("ss");
        
        // Format time (with leading space)
        formatter = new SimpleDateFormat(" HH:mm:ss");    // NOTE: Leading space
        theTime   = formatter.format(date);
        
        // Start to check modified strains from db query against list of accepted files in db
        // READ FILE AND POP ARRAY
        readStrainList();
        
        StrainsDAO  strainARCHD;
        WebRequests r   = new WebRequests();
        List        res = r.strainsUpdates(theDate, theDate + theTime);
        
        System.out.println("Size = " + res.size());
        
        for (int i = 0; i < res.size(); i++) {
            strainARCHD = (StrainsDAO) res.get(i);
            System.out.println(strainARCHD.getEmma_id());
            
            String s = strainARCHD.getEmma_id();
            
            // Check against listarray
            int index = Collections.binarySearch(list, s);
            
            // If index is negative then doesn't exist
            if (index >= 0) {
                System.out.print(index);
                System.out.println("ID in Collection: " + s + "\n");
                
                // GRAB EMAIL FROM REQUESTS TABLE
                WebRequestsDAO webReq;
                WebRequests    wr = new WebRequests();
                
                // List result = wr.webRequests("EM:01985");
                List result = wr.webRequests(s);
                
                System.out.println("Result size = " + result.size());
                
                // SEND EMAIL
                // Create hashmap for velocity templates
                Map model = new HashMap();
                
                for (int ii = 0; ii < result.size(); ii++) {
                    webReq = (WebRequestsDAO) result.get(ii);
                    System.out.println(webReq.getSci_firstname() + " " + webReq.getSci_surname() + " "
                            + webReq.getSci_e_mail() + " " + webReq.getStrain_name() + " "
                            + webReq.getStr_id_str() + "  " + webReq.getStrain_id());
                    webReq.toString();
                    model.put("name", webReq.getSci_firstname() + " " + webReq.getSci_surname());
                    model.put("email", webReq.getSci_e_mail()/*"philw@ebi.ac.uk")*/);
                    model.put("emmaid", s);
                    model.put("strainname", webReq.getStrain_name());
                    model.put("id", new Integer(webReq.getId_req()));
                    
                    // TODO: my email for testing - pull from database/hibernate
                    msg.setBcc("philw@ebi.ac.uk");
                    Iterator it = Cc.values().iterator();
                    while (it.hasNext()) {
                        String ccAddress = (String) it.next();
                        msg.setCc(ccAddress);
                    }
                    //msg.setCc("philw@ebi.ac.uk,phil.j.wilkinson@gmail.com");
                    msg.setTo(webReq.getSci_e_mail());
                    msg.setSubject("Your EMMA Strain Interest Registration Form - " +
                            "strain can now be ordered: " + model.get("emmaid") + " ("
                            + model.get("strainname") + ")");
                    String content = VelocityEngineUtils.mergeTemplateIntoString(velocityEngine,
                            "org/emmanet/util/velocitytemplates/regInt-Template.vm", model);
                    
                    msg.setText(content);
                    
                    try {
                        getMailSender().send(msg);
                    } catch (MailException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
            }
        }
        
        // POPULATE ACCEPTED STRAIN FILE
        StrainsDAO  strainARRD;
        WebRequests r2   = new WebRequests();
        List        res2 = r2.strainListArrd();
        
        new File(ACCCEPTEDSTRAINS).delete();
        
        for (int i = 0; i < res2.size(); i++) {
            strainARRD = (StrainsDAO) res2.get(i);
            generateStrainList(strainARRD.getEmma_id() + "\n");
        }
    }
    
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        
        // FOR LOGS
        System.out.println("Job Started");
        
        // Call main method that handles functionality
        RegInterest();
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
    
    public Map getCc() {
        return Cc;
    }
    
    public void setCc(Map Cc) {
        this.Cc = Cc;
    }
    
    public String getBcc() {
        return Bcc;
    }
    
    public void setBcc(String Bcc) {
        this.Bcc = Bcc;
    }
}

