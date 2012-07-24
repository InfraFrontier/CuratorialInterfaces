/*
 * RequestUpdateFormValidator.java
 *
 * Created on 09 March 2010, 17:24
 *
 * Validates required fields in request update form. Currently only required field is
 * eucomm_funding and only when shipped
 *
 * On validation error message alerts user in message area at top of page.
 * On success redirects user back to strain list with success message displayed.
 *
 * 
 */
package org.emmanet.controllers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.emmanet.model.WebRequestsDAO;
import org.springframework.validation.Errors;

/**
 *
 * @author Phil Wilkinson
 */
public class RequestUpdateFormValidator implements
        org.springframework.validation.Validator {

    private HttpServletRequest request;

    public boolean supports(Class aClass) {

        return aClass.equals(WebRequestsDAO.class);
    }

    public void validate(Object object, Errors errors) {
        WebRequestsDAO webReq = (WebRequestsDAO) object;
        if (webReq == null) {
            return;
        }
         if(webReq.getRegister_interest() == null){
             return; 
         }
        if(webReq.getRegister_interest() != null || webReq.getRegister_interest().isEmpty() || webReq.getRegister_interest().trim().length() <1){
                if (webReq.getRegister_interest().equals("1")) {
             System.out.println("VALIDATOR == " + webReq.getRegister_interest() + " :: " + webReq.getReq_status());
            if (webReq.getReq_status().equals("CANC")) {
                if (webReq.getCancelation_reason() == null || webReq.getCancelation_reason().trim().length() < 1 || webReq.getCancelation_reason().isEmpty()) {
                    errors.reject("Message", "Please ensure a reason for ROI cancellation is selected.");
                }
            }
        }
        }
        if (!webReq.getUser().equals("super")) {
           // return;//remove once deployed live and in use
        }
        if (!webReq.getReq_status().equals("SHIP")) {
            return;
        }
        System.out.println("VALIDATOR == " + webReq.getReq_status());
        if (!webReq.getProjectID().equals("9")) {
            return;
        }
        System.out.println("VALIDATOR == " + webReq.getProjectID());
        if (webReq.getEucomm_funding().isEmpty()) {
            errors.reject("Message", "Please choose EMMA mice or EUCOMM/EMMA mice, necessary to determine EUCOMM funding");
        }

    }
}
