package org.emmanet.controllers;

import org.emmanet.model.WebRequestsDAO;
import org.springframework.validation.Errors;

/**
 *
 * @author Phil Wilkinson
 */
public class RequestUpdateValidator implements
        org.springframework.validation.Validator {

    public boolean supports(Class aClass) {

        return aClass.equals(WebRequestsDAO.class);
    }

    public void validate(Object object, Errors errors) {
        WebRequestsDAO webReq = (WebRequestsDAO) object;
        System.out.println();
        System.out.println(webReq.getReq_status() + "~~~~~~" + webReq.getDate_processed());
        System.out.println();
        
        System.out.println();
        System.out.println(webReq.getReq_status() + "~~~~~~" + webReq.getReq_material_state());
        System.out.println();

        if (webReq.getReq_status().equals("SHIP") && webReq.getDate_processed().equals(null)) {/*;.isEmpty()) {*/
            errors.reject("Message", "Please ensure that the processed date field is completed");
        }


        if (webReq.getReq_status().equals("SHIP") && webReq.getReq_material_state().equals(null)){/*.isEmpty()) {*/
            errors.reject("Message", "Please ensure that the material used to fulfill the request field is completed");
        }
   /*     if (webReq.getRegister_interest().equals("1")) {
            if (webReq.getReq_status().equals("CANC")) {
                if (webReq.getCancelation_reason() == null || webReq.getCancelation_reason().trim().length() < 1) {
                    errors.reject("Message", "Please ensure a reason for ROI cancellation is selected.");
                }

            }
        }*/
    }
}
