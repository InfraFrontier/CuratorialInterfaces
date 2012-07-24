/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.controllers;

import org.springframework.validation.Errors;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.emmanet.model.StrainsDAO;
import org.emmanet.model.SubmissionsDAO;
import org.springframework.validation.ValidationUtils;

/**
 *
 * @author phil
 */
public class SubmissionFormValidator implements
        org.springframework.validation.Validator {
    // REGEX PATTERNS

    private static final String EMAIL_PATTERN =
            /* "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)"
            + "|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";*/
            "^((([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+"
            + "(\\.([a-z]|\\d|[!#\\$%&'\\*\\+\\-\\/=\\?\\^_`{\\|}~]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])+"
            + ")*)|((\\x22)((((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?(([\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x7f]|\\x21|[\\x23"
            + "-\\x5b]|[\\x5d-\\x7e]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(\\\\([\\x01-\\x09\\x0b\\x0c\\x0d-\\"
            + "x7f]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF]))))*(((\\x20|\\x09)*(\\x0d\\x0a))?(\\x20|\\x09)+)?"
            + "(\\x22)))@((([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|\\d|[\\u00A0-\\uD7FF"
            + "\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])"
            + "*([a-z]|\\d|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])))\\.)+(([a-z]|[\\u00A0-\\uD7FF\\uF900-"
            + "\\uFDCF\\uFDF0-\\uFFEF])|(([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])([a-z]|\\d|-|\\.|_|~|["
            + "\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-\\uFFEF])*([a-z]|[\\u00A0-\\uD7FF\\uF900-\\uFDCF\\uFDF0-"
            + "\\uFFEF])))\\.?$";

    private static final String FAXTEL_PATTERN = "^\\+\\d+[\\d\\-\\(\\)\\s]+(x\\d+)?$";

    public boolean supports(Class type) {
        return type.equals(StrainsDAO.class);
    }

    public void validate(Object o, Errors errors) {
       // StrainsDAO sd = (StrainsDAO) o;
        SubmissionsDAO sd = (SubmissionsDAO) o;
        if (sd == null) {
            return;
        } else {
            validateSubmissionForm1(sd, errors);

        }
    }

    public void validateSubmissionForm0(SubmissionsDAO sd, Errors errors) {
    }

    public void validateSubmissionForm1(SubmissionsDAO sd, Errors errors) {

        if (!patternMatch(EMAIL_PATTERN, sd.getSubmitter_email()/*.getPeopleDAO().getEmail()*/)) {
            errors.rejectValue("submitter_email", "incorrect.email",
                    "The submitted Email address field appears to be incorrect");
        }

        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "submitter_email",
                "required.email", "Email is a required field.");

    }

    public void validateSubmissionForm2(StrainsDAO sd, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "peopleDAO.firstname",
                "required.userName", "The Scientists firstname is a required field");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "peopleDAO.surname","required.familyname",
                "The Scientists family name is a required field");
    }
    
        public void validateSubmissionForm9(SubmissionsDAO sd, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "breeding_performance",
                "required.breeding_performance", "The breeding performance selection needs to be made");
    }
        
    public boolean patternMatch(String patternValue, String input) {
        Pattern pattern = Pattern.compile(patternValue);
        Matcher matcher = pattern.matcher(input);
        boolean matchFound = matcher.matches();
        System.out.println(input + " validator reports " + matchFound);
        return matchFound;
    }
}
