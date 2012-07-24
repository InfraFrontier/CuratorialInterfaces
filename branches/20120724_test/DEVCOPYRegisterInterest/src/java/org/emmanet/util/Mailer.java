/*
 * Mailer.java
 *
 * Created on 29 November 2007, 15:44
 * 
 * Currently not being used by Register an Interest functionality but released as
 * part of Register Interest work.
 *
 */

package org.emmanet.util;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.MailPreparationException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 *
 * @author phil
 */
public class Mailer implements MailManager {
    
    private JavaMailSender javaMailSender;
    private Resource templateBase;
    private VelocityEngine velocityEngine;
    
    private String defaultFromName;
    private String defaultFromAddress;
    private String toName;
    private String toAddress;
    
    private String templateLocation = "org/emmanet/util/velocitytemplates/";//TODO SET TEMPLATE ROOT
    private String textTemplateSuffix = "header.vm";
    private String htmlTemplateSuffix = "footer.vm";
    
    //FOR TESTING
    private String overrideToAddress;
    
    private String defaultToAddress;
    
    public void send(String senderName,
            String senderAddress,
            Map from,
            Map to,
            Map cc,
            Map bcc,
            String subject,
            Map mergeObjects,
            String template)
            throws MailException {
        // Create a mime message using the mail sender implementation
        MimeMessage message = javaMailSender.createMimeMessage();
        // Create the message using the specified template
        MimeMessageHelper helper;
        try {
            helper = new MimeMessageHelper(message, true, "UTF-8");
        } catch (MessagingException ex) {
            throw new MailPreparationException(
                    "Unable to create the mime message helper", ex
                    );
        }
        try {
            
            // Add the 'from' recipient(s) to the message
            helper.setFrom(senderAddress, senderName);
            // Add cc recipients
            if (cc != null && !cc.isEmpty()) {
                Iterator it = cc.keySet().iterator();
                while (it.hasNext()) {
                    String name = (String) it.next();
                    String recipient = (String) cc.get(name);
                    if (overrideToAddress != null){
                        recipient = defaultToAddress;
                    }
                    helper.addCc(recipient, name);
                }
            }
            
            //Add the Bcc recipients to the message
            
            if (bcc != null && !bcc.isEmpty()) {
                Iterator it = bcc.keySet().iterator();
                while (it.hasNext()) {
                    String name = (String) it.next();
                    String recipient = (String) bcc.get(name);
                    if (overrideToAddress != null){
                        recipient = defaultToAddress;
                    }
                    helper.addBcc(recipient, name);
                }  
            }
            
            //Add the to recipients to the message
            
            if (to != null && !to.isEmpty()) {
                Iterator it = to.keySet().iterator();
                while (it.hasNext()) {
                    String name = (String) it.next();
                    String recipient = (String) to.get(name);
                    if (overrideToAddress != null){
                        recipient = defaultToAddress;
                    }
                    helper.addTo(recipient, name);
                }
                
            } else {
                //Use the default to address
                
                if(defaultToAddress != null) {
                    helper.addTo(defaultToAddress, toName);
                } else {
                    helper.addTo(toAddress, toName);
                } 
            }
            
            //Mail message content, templates etc.
            
            StringWriter text = new StringWriter();
            VelocityEngineUtils.mergeTemplate(velocityEngine, templateLocation + "/"
                    + textTemplateSuffix, mergeObjects, text);
            
            StringWriter html = new StringWriter();
            VelocityEngineUtils.mergeTemplate(velocityEngine, templateLocation + "/"
                    + htmlTemplateSuffix, mergeObjects, text);
            
            text.close();
            html.close();
            
            helper.setText(text.toString(), html.toString());
            
            //Set subject
            
            helper.setSubject(subject);
            
            //TODO: ADD SUPPORT FOR ATTACHMENTS HERE IF NEEDED
            
            
            
            //SEND MESSAGE
            
            javaMailSender.send(message);
            
        }
        
        catch (IOException ex) {
            //catch (UnsupportedEncodingException ex) {
            //  ex.printStackTrace();
        } catch (MessagingException ex) {
        }
        
    }
    
    public void send(Map to,
            List objects,
            String template)
            throws MailException {
        //send(null, to, null, null, objects, template);
    }
    
    
    //Setters and Getters
    public JavaMailSender getJavaMailSender() {
        return javaMailSender;
    }
    
    public void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }
    
    public Resource getTemplateBase() {
        return templateBase;
    }
    
    public void setTemplateBase(Resource templateBase) {
        this.templateBase = templateBase;
    }
    
    public VelocityEngine getVelocityEngine() {
        return velocityEngine;
    }
    
    public void setVelocityEngine(VelocityEngine velocityEngine) {
        this.velocityEngine = velocityEngine;
    }
    
    public String getDefaultFromName() {
        return defaultFromName;
    }
    
    public void setDefaultFromName(String defaultFromName) {
        this.defaultFromName = defaultFromName;
    }
    
    public String getDefaultFromAddress() {
        return defaultFromAddress;
    }
    
    public void setDefaultFromAddress(String defaultFromAddress) {
        this.defaultFromAddress = defaultFromAddress;
    }
    
    public String getTextTemplateSuffix() {
        return textTemplateSuffix;
    }
    
    public void setTextTemplateSuffix(String textTemplateSuffix) {
        this.textTemplateSuffix = textTemplateSuffix;
    }
    
    public String getHtmlTemplateSuffix() {
        return htmlTemplateSuffix;
    }
    
    public void setHtmlTemplateSuffix(String htmlTemplateSuffix) {
        this.htmlTemplateSuffix = htmlTemplateSuffix;
    }
    
    public String getOverrideToAddress() {
        return overrideToAddress;
    }
    
    public void setOverrideToAddress(String overrideToAddress) {
        this.overrideToAddress = overrideToAddress;
    }
    
}
