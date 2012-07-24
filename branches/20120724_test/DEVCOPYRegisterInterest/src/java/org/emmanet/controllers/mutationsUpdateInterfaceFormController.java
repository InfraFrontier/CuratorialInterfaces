/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.controllers;

/**
 *
 * @author phil
 */
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.emmanet.model.MutationsDAO;
import org.emmanet.model.MutationsManager;
import org.emmanet.model.MutationsStrainsDAO;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.validation.BindException;

public class mutationsUpdateInterfaceFormController extends SimpleFormController {

    private MutationsManager mm = new MutationsManager();
    private List mutsDAOs = null;
    private MutationsDAO md;
    private MutationsStrainsDAO msDAO;
    private int mutId;

    @Override
    protected Object formBackingObject(HttpServletRequest request) {
        HttpSession session = request.getSession(true);
        if (request.getParameter("action") != null) {
            String action = request.getParameter("action");

            if (action.equals("edit")) {

                Integer id = Integer.parseInt(request.getParameter("EditStrain"));
                List muts = mm.getMutationIDsByStrain(id);
                System.out.println(muts.size());
                if (muts.size() == 1) {
                    //Only 1 mutation so edit and not list mutations to select for editing
                    msDAO = (MutationsStrainsDAO) muts.get(0);
                    int mutID = msDAO.getMut_id();
                    md = mm.getMutByID(mutID);
                    //Set  flag to use correct display for edit
                    session.setAttribute("view", "Edit");
                    return md;
                }
                //msDAO = (MutationsStrainsDAO) muts.get(0);
                //  mutId = (Integer) msDAO.getMut_id();
                // md = mm.getMutByID(mutId);
                //} else {
                //  for (Iterator it = muts.iterator(); it.hasNext();) {
                for (int it = 0; it < muts.size(); it++) {
                    session.setAttribute("view", "show");
                    System.out.println(muts.get(it));
                    //Object[] element = (Object[]) it.next();
                    //MutationsStrainsDAO msDAO = (MutationsStrainsDAO) it.next();
                    msDAO = (MutationsStrainsDAO) muts.get(it);
                    // mutId = (Integer) msDAO.getMut_id();
                    // mutsDAOs.add(msDAO);
                    System.out.println("m,ut id is..." + mutId);

                    // md = mm.getMutByID(mutId);
                    //mutsDAOs.add(mm);
                    //   }
                    return muts;
                }
            } else {
                // Single edits from multiple mutation lists
                if (request.getParameter("Edit") != null) {
                    //String editID = request.getParameter("Edit");

                    //msDAO =  (MutationsStrainsDAO) muts.get(0);
                    int mutID = Integer.parseInt(request.getParameter("Edit"));
                    System.out.println("edit id is::::" + mutID);
                    md = mm.getMutByID(mutID);
                    //Set  flag to use correct display for edit

                    session.setAttribute("view", "Edit");
                    return md;
                }

            }
        }

        return null;
    }
    // SAVE
    @Override
    protected ModelAndView onSubmit(
            HttpServletRequest request,
            HttpServletResponse response,
            Object command,
            BindException errors)
            throws ServletException, Exception {
        MutationsDAO mDAO = (MutationsDAO) command;


        mm.save(mDAO);
        System.out.println("saved");
        request.getSession().setAttribute(
                "message",
                getMessageSourceAccessor().getMessage("Message",
                "Your update submitted successfully"));

        //return new ModelAndView(getSuccessView());
        ModelAndView mav = showForm(request, response, errors);
        return mav;
    }
}
