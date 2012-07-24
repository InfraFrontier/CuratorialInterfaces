/*
 * RequestFormValidator.java
 *
 * Created on 09 January 2008, 16:24
 *
 * Validates required fields in register interest form.
 *
 * On validation error message alerts user in message area at top of page.
 * On success redirects user back to strain list with success message displayed.
 *
 * v1.0 released 04-02-2008
 */

package org.emmanet.controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.emmanet.model.WebRequestsDAO;
import org.springframework.validation.Errors;

/**
 *
 * @author Phil Wilkinson
 */
public class RequestFormValidator implements
        org.springframework.validation.Validator {
    
    private static final String EMAIL_PATTERN =
            "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)" +
            "|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
    
    public boolean supports(Class aClass) {
        
        return aClass.equals(WebRequestsDAO.class);
    }
    
    public void validate(Object object, Errors errors) {
        WebRequestsDAO webReq = (WebRequestsDAO) object;
        if (webReq == null) return;
        
        System.out.println("TERMS READ -    " + webReq.getTerms_read());
        if (webReq.getTerms_read() == null)
                errors.reject("Message", "Please read the conditions and check the box to confirm acceptance");
        if (webReq.getSci_firstname() == null
                || webReq.getSci_firstname().trim().length() < 1)
            errors.reject("Message", "The Scientists firstname field appears to have no value");
        if (webReq.getSci_surname() == null
                || webReq.getSci_surname().trim().length() < 1)
            errors.reject("Message", "The Scientists surname field appears to have no value");
        if (webReq.getSci_e_mail() == null || webReq.getSci_e_mail().trim().length() < 1
                || !patternMatch(EMAIL_PATTERN,webReq.getSci_e_mail()))
            errors.reject("Message", "The Scientists Email address field appears to have no value or is incorrect");
        if (webReq.getSci_phone() == null
                || webReq.getSci_phone().trim().length() < 1)
            errors.reject("Message", "The Scientists phone field appears to have no value or is incorrect");
        if (webReq.getCon_institution() == null
                || webReq.getCon_institution().trim().length() < 1)
            errors.reject("Message", "The institution field appears to have no value");
        if (webReq.getCon_addr_1() == null
                || webReq.getCon_addr_1().trim().length() < 1)
            errors.reject("Message", "The address field appears to have no value");
        if (webReq.getCon_town() == null
                || webReq.getCon_town().trim().length() < 1)
            errors.reject("Message", "The town field appears to have no value");
        if (webReq.getCon_postcode() == null
                || webReq.getCon_postcode().trim().length() < 1)
            errors.reject("Message", "The postcode field appears to have no value");
        if (webReq.getCon_country() == null
                || webReq.getCon_country().trim().length() < 1)
            errors.reject("Message", "The country field appears to have no value");
        
         if (webReq.getCon_e_mail() == null || webReq.getCon_e_mail().trim().length() < 1
                || !patternMatch(EMAIL_PATTERN,webReq.getCon_e_mail()))
            errors.reject("Message", "The Contacts Email address field appears to have no value or is incorrect");
         if (webReq.getCon_phone() == null
                || webReq.getCon_phone().trim().length() < 1)
            errors.reject("Message", "The Shipping Contacts phone field appears to have no value or is incorrect");
        if (webReq.getReq_material() == null)
            errors.reject("Message", "Please select the material you wish to order");
    }
    
    public boolean patternMatch(String patternValue, String input) {
        Pattern pattern = Pattern.compile(patternValue);
        Matcher matcher = pattern.matcher(input);
        boolean matchFound = matcher.matches();
        
        //System.out.println(input);
        System.out.println(input + " validator reports " + matchFound);
        return matchFound;
    }
}
