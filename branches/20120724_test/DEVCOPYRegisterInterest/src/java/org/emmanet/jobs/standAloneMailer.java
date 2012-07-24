/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.jobs;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.core.io.FileSystemResource;

import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.quartz.QuartzJobBean;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.collections.ListUtils;
import org.springframework.mail.javamail.JavaMailSender;

/**
 *
 * @author phil
 */
public class standAloneMailer extends QuartzJobBean {
//r extends SimpleFormController

    /* pdfTitle determined according to request being register
     * interest or new request
     */    //make into a scheduled job to run once at a certain time during minimal server traffic
    private String subject;
    private boolean pdfConditions;
    private Map Bcc;
    private String content = "";
    private Map Cc;
    private Iterator it;
    private JavaMailSender javaMailSender;
    private static final String EMAIL_PATTERN =
            "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)" +
            "|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";

    public void onSubmit() {
        /*  String[] euCountriesList = {"Austria", "Belgium", "Bulgaria", "Cyprus", "Czech Republic", "Denmark", "Estonia", "Finland", "France", "Germany", "Greece", "Hungary", "Ireland", "Italy", "Latvia", "Lithuania", "Luxembourg", "Malta", "Netherlands", "Poland", "Portugal", "Romania", "Slovakia", "Slovenia", "Spain", "Sweden", "United Kingdom"};
        String[] assocCountriesList = {"Albania", "Croatia", "Iceland", "Israel", "Liechtenstein", "Macedonia", "Montenegro", "Norway", "Serbia", "Switzerland", "Turkey"};
        Arrays.sort(euCountriesList);
        Arrays.sort(assocCountriesList);*/

        //read from file
        try {
            BufferedReader in = new BufferedReader(new FileReader("/nfs/panda/emma/tmp/content.html"));
            String str;
            while ((str = in.readLine()) != null) {
                //content = content + str;
                content = (new StringBuilder()).append(content).append(str).toString();
            }

            in.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
        subject = "EMMAservice – Transnational Access program – User group questionnaire";//New Cre driver mouse lines";
        System.out.println("Subject: " + subject + "\n\nContent: " + content);

        //iterate over database email results adding to bcc use map keys ae address to prevent dups
        setCc(new HashMap());
        //getCc().put(new String("info@emmanet.org"), "");
        //getCc().put(new String("emma@emmanet.org"), "");
        // getCc().put(new String("sabine.fessele@helmholtz-muenchen.de"), "");
        getCc().put(new String("michael.hagn@helmholtz-muenchen.de"), "");
        getCc().put(new String("philw@ebi.ac.uk"), "");

        setBcc(new HashMap());
        //PeopleManager pm = new PeopleManager();
        WebRequests wr = new WebRequests();
        //List Bccs1 = wr.sciMails("sci_e_mail");
        //List Bccs2 = wr.sciMails("sci_e_mail");
        List Bccs =  wr.sciMails("nullfield");//ListUtils.union(Bccs1,Bccs2);
        int BccSize = Bccs.size();
        System.out.println("Size of list is: " + BccSize);
        //user asked to be removed,don't want to remove from database as details for email needed
//Bccs1.remove("kgroden@interchange.ubc.ca");
//Bccs2.remove("kgroden@interchange.ubc.ca");
        for (it = Bccs.listIterator(); it.hasNext();) {
            // Object[] o = (Object[]) it.next();
            //System.out.println("object is:: " + o);
            String element = it.next().toString();

            //String country = o[1].toString();

            if (!Bcc.containsKey(it)) {


                // int index = Arrays.binarySearch(euCountriesList, country);

                // int index1 = Arrays.binarySearch(euCountriesList, country);


                //  if (index >= 0 || index1 >= 0) {
                // System.out.println("Country OK :- " + country);
                System.out.println("element is: " + element);
                Bcc.put(element, "");
            //  }
            }
        }

        MimeMessage message = getJavaMailSender().createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            //helper.setValidateAddresses(false);
            helper.setReplyTo("info@emmanet.org");
            helper.setFrom("info@emmanet.org");
            System.out.println("BCC SIZE -- " + Bcc.size());
            Iterator it1 = Bcc.keySet().iterator();

            while (it1.hasNext()) {
                String BccAddress = (String) it1.next();
                System.out.println("BccADDRESS===== " + BccAddress);
                if (BccAddress == null || BccAddress.trim().length() < 1 || !patternMatch(EMAIL_PATTERN, BccAddress)) {
                    System.out.println("The Scientists Email address field appears to have no value or is incorrect");
                    BccSize = BccSize - 1;
                } else {
                    //~~  
                    helper.addBcc(BccAddress);
                }
            }
            System.out.println("CC SIZE -- " + Cc.size());
            Iterator i = Cc.keySet().iterator();
            while (i.hasNext()) {
                String CcAddress = (String) i.next();
                System.out.println("ccADDRESS===== " + CcAddress);
                helper.addCc(CcAddress);
            }

            helper.setTo("info@emmanet.org");//info@emmanet.org
            //helper.setCc("webmaster.emmanet.org");
            //helper.setBcc("philw@ebi.ac.uk");
            helper.setText(content, true);
            helper.setSubject(subject);
            String filePath = "/nfs/panda/emma/tmp/";
            //String fileName = "PhenotypingSurveyCombinedNov2009.doc";
            //String fileName2 = "EMPReSSslimpipelines-1.pdf";
            //FileSystemResource file = new FileSystemResource(new File(filePath + fileName));
           // FileSystemResource file2 = new FileSystemResource(new File(filePath + fileName2));
            //helper.addAttachment(fileName, file);
            //helper.addAttachment(fileName2, file2);
            getJavaMailSender().send(message);
            try {
                BufferedWriter out = new BufferedWriter(new FileWriter("/nfs/panda/emma/tmp/finalmailcount.txt"));

                out.write("FINAL BCC SIZE IS::" + BccSize);
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            System.out.println(helper.getMimeMessage());

        } catch (MessagingException ex) {
            ex.printStackTrace();
        }

    }

    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {

        // FOR LOGS
        System.out.println("Job Started");

        // Call main method that handles functionality
        onSubmit();

    }

    public boolean patternMatch(String patternValue, String input) {
        Pattern pattern = Pattern.compile(patternValue);
        Matcher matcher = pattern.matcher(input);
        boolean matchFound = matcher.matches();
        System.out.println(input + " validator reports " + matchFound);
        return matchFound;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public boolean isPdfConditions() {
        return pdfConditions;
    }

    public void setPdfConditions(boolean pdfConditions) {
        this.pdfConditions = pdfConditions;
    }

    public Map getCc() {
        return Cc;
    }

    public void setCc(Map Cc) {
        this.Cc = Cc;
    }

    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }

    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public Map getBcc() {
        return Bcc;
    }

    public void setBcc(Map Bcc) {
        this.Bcc = Bcc;
    }
}
