/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.controllers;

/**
 *
 * @author phil
 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.emmanet.model.BibliosDAO;
import org.emmanet.model.BibliosManager;
import org.emmanet.model.BibliosStrainsDAO;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.validation.BindException;

public class bibliosUpdateInterfaceFormController extends SimpleFormController {

    private BibliosStrainsDAO bsDAO;
    private BibliosDAO bDAO;
    private BibliosManager bm = new BibliosManager();
    List bibliosStrains = null;
    private String pubmedURL;
    private String pubmedURLTail;
    //public static final String MAP_KEY = "returnedOut";
    //private Map returnedOut = new HashMap();
    private String listView;

    @Override
    protected Object formBackingObject(HttpServletRequest request) {

        HttpSession session = request.getSession(true);

        if (request.getParameter("action") != null) {
            int id = Integer.parseInt(request.getParameter("EditStrain"));
            String action = request.getParameter("action");
            session.setAttribute("pubmedUrl", this.pubmedURL);
            session.setAttribute("pubmedUrlTail", this.pubmedURLTail);
            bibliosStrains = bm.BibliosStrains(id);

            if (action.equals("edit")) {

                if (bibliosStrains.size() == 1 || request.getParameter("Single").equals("Y")) {
                    //only 1 bib reference

                    if (request.getParameter("bibid") != null) {

                        //bibid is set therefore we are editing a specific bib ref with a supplied id from bib list interface
                        Integer bibID = (Integer.parseInt(request.getParameter("bibid")));
                        bDAO = bm.getBibByID(bibID);

                    } else {

                        bsDAO = (BibliosStrainsDAO) bibliosStrains.get(0);
                        int bibID = bsDAO.getBib_id_biblio();
                        bDAO = bm.getBibByID(bibID);
                    }

                    //Set  flag to use correct display for edit
                    session.setAttribute("view", "Edit");
                    return bDAO;
                //////} //else {

                /*   //More than one biblio ref so list for edit
                session.setAttribute("view", "show");
                for (int it = 0; it < bibliosStrains.size(); it++) {
                bsDAO = (BibliosStrainsDAO) bibliosStrains.get(it);
                int bibID = bsDAO.getBib_id_biblio();
                bDAO = bm.getBibByID(bibID);
                returnedOut.put("BibliosDAO", bDAO);
                System.out.println("A D D E D  B I B L I O  T O RETURNEDOUT>>>>>>>>>>");
                //return new ModelAndView(getSuccessView(), MAP_KEY, returnedOut);
                
                }
                //return returnedOut;
                return new ModelAndView(getListView(), returnedOut);
                // this.handleRequest(request, response, returnedOut);
                } */                }
            } else if (action.equals("add")) {
                // this will now be an addition
                for (int it = 0; it < bibliosStrains.size(); it++) {
                    session.setAttribute("view", "show");
                    bsDAO = (BibliosStrainsDAO) bibliosStrains.get(it);
                }


                bDAO = new BibliosDAO();
                return bDAO;
            }
            return null;
        } else {
            // Single edits from multiple mutation lists
            //if (request.getParameter("Edit") != null) {

            int ID = Integer.parseInt(request.getParameter("EditStrain"));
            bibliosStrains = bm.BibliosStrains(ID);
            bsDAO = (BibliosStrainsDAO) bibliosStrains.get(0);
            int bibID = bsDAO.getBib_id_biblio();

            bDAO = bm.getBibByID(bibID);
            //Set  flag to use correct display for edit
            session.setAttribute("view", "Edit");
            return bDAO;
        }

    // }
    // return null;
    }

    @Override
    protected ModelAndView onSubmit(
            HttpServletRequest request,
            HttpServletResponse response,
            Object command,
            BindException errors)
            throws ServletException, Exception
    {

        bDAO = (BibliosDAO) command;

//TODO MAY HAVE TO LOOK AT TRANSACTION MANAGEMENT HERE IF THIS CAUSES ISSUES BIBLIOSTRAINS IS DEPENDANT ON HAVING A BIBLIOID
        bm.save(bDAO);
        if (request.getParameter("action").equals("add")) {

            bsDAO = new BibliosStrainsDAO();
            int id = Integer.parseInt(request.getParameter("EditStrain"));
            bsDAO.setStr_id_str(id);

            bsDAO.setBib_id_biblio(bDAO.getId_biblio());

            bm.save(bsDAO);
        }
        System.out.println("saved");
        request.getSession().setAttribute(
                "message",
                getMessageSourceAccessor().getMessage("Message",
                "Your update submitted successfully"));

        // return new ModelAndView(getSuccessView());
        ModelAndView mav = showForm(request, response, errors);
        return mav;
    }

    public String getPubmedURL() {
        return pubmedURL;
    }

    public void setPubmedURL(String pubmedURL) {
        this.pubmedURL = pubmedURL;
    }

    public String getPubmedURLTail() {
        return pubmedURLTail;
    }

    public void setPubmedURLTail(String pubmedURLTail) {
        this.pubmedURLTail = pubmedURLTail;
    }

    public String getListView() {
        return listView;
    }

    public void setListView(String listView) {
        this.listView = listView;
    }
}
