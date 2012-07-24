/*
 * MailManager.java
 *
 * Interface Defines methods for Mailer
 *
 * Created on 29 November 2007, 15:45
 *
 * Currently not being used by Register an Interest functionality but released as
 * part of Register Interest work.
 *
 */

package org.emmanet.util;

import java.util.List;
import java.util.Map;
import org.springframework.mail.MailException;

/**
 *
 * @author phil
 */
public interface MailManager {
    void send(String senderName, String senderAddress, Map from, Map to, 
            Map cc, Map bcc, String subject, Map mergeObjects, String template)
    throws MailException;
    void send(Map to, List objects, String template)
    throws MailException;   
}
