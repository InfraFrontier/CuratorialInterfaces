/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.controllers;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.emmanet.model.PeopleDAO;
import org.emmanet.model.PeopleManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.validation.BindException;

/**
 *
 * @author phil
 */
public class PeopleUpdateInterfaceFormController extends SimpleFormController {

    private PeopleManager pm = new PeopleManager();
    private PeopleDAO pDAO;

    @Override
    protected Object formBackingObject(HttpServletRequest request) {

        if (request.getParameter("contactID") != null) {
            pDAO = (PeopleDAO) pm.getPerson(request.getParameter("contactID"));
            return pDAO;

        }
        return pDAO;

    }
    // SAVE
    @Override
    protected ModelAndView onSubmit(
            HttpServletRequest request,
            HttpServletResponse response,
            Object command,
            BindException errors)
            throws ServletException, Exception {
        pDAO = (PeopleDAO) command;

        pm.save(pDAO);
        System.out.println("saved");
        request.getSession().setAttribute(
                "message",
                getMessageSourceAccessor().getMessage("Message",
                "Your update submitted successfully"));

        //  return new ModelAndView(getSuccessView());
        ModelAndView mav = showForm(request, response, errors);
        return mav;
    }
}
