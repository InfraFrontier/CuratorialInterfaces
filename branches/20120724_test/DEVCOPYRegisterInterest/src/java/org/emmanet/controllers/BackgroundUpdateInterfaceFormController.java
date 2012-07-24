/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.controllers;

import org.springframework.validation.BindException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.emmanet.model.BackgroundDAO;
import org.emmanet.model.BackgroundManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.security.userdetails.UserDetails;
import javax.servlet.http.HttpSession;
import org.emmanet.model.ArchiveManager;
import org.emmanet.model.MutationsDAO;
import org.emmanet.model.MutationsManager;
import org.emmanet.model.StrainsDAO;
import org.emmanet.model.StrainsManager;

/**
 *
 * @author phil
 */
public class BackgroundUpdateInterfaceFormController extends SimpleFormController {

    public static final String MAP_KEY = "returnedOut";
    BackgroundManager bm = new BackgroundManager();
    BackgroundDAO bd = new BackgroundDAO();
    private HttpSession session;

    @Override
    protected Object formBackingObject(HttpServletRequest request) {
        session = request.getSession(true);
        bd = new BackgroundDAO();
        //to prevent deletion button display
        request.setAttribute("preventDel", null);
        MutationsDAO md = new MutationsDAO();
        StrainsDAO sd = new StrainsDAO();
        MutationsManager mm = new MutationsManager();
        StrainsManager sm = new StrainsManager();
        ArchiveManager am = new ArchiveManager();

        if (request.getParameter("bgid") != null && !request.getParameter("bgid").equals("0")) {
            if (!request.getParameter("bgid").equals("")) {
                bd = (BackgroundDAO) bm.getBgById(Integer.parseInt(request.getParameter("bgid")));
                System.out.println("BDAO SET TO " + bd.getId_bg());
            }
        } else {
            //a new background to save
            bd = new BackgroundDAO();
            request.setAttribute("preventDel", "y");
            //System.out.println("BDAO =  " + bd.getId_bg());
        }
        int strainsBGCount = sm.getBackgroundIDCount(bd.getId_bg()).intValue();
        int mutationsBGCount = mm.getBackgroundIDCount(bd.getId_bg()).intValue();
        int archiveMaleBGCount = am.getBackgroundIDCount(bd.getId_bg(), "male").intValue();
        int archiveFemaleBGCount = am.getBackgroundIDCount(bd.getId_bg(), "female").intValue();
        //add cout for archive mail bg id and femal bg id
        System.out.println("strains count is " + strainsBGCount + " and mutations count is " + mutationsBGCount);
        if (strainsBGCount >= 1
                || mutationsBGCount >= 1 
                || archiveFemaleBGCount  >= 1
                || archiveMaleBGCount  >= 1 ) {
            //prevent background deletion as in use
            //session.setAttribute("preventDel", "y");

            request.setAttribute("preventDel", "y");
        } else if (bd.getId_bg() != 0) {
            request.setAttribute("preventDel", "n");
            //session.setAttribute("preventDel", "n");
        }


        List bgs = bm.getBGSpecies();
        cryopreservationHistoryController chc = new cryopreservationHistoryController();
        chc.setFileLocation("/nfs/panda/emma/tmp/");
        System.out.println("BGLIST FILE LOCATION IS:: " + chc.getFileLocation());
        chc.createList();
        session.setAttribute("species", bgs);
        return bd;
    }

    @Override
    protected ModelAndView onSubmit(
            HttpServletRequest request,
            HttpServletResponse response,
            Object command,
            BindException errors)
            throws ServletException, Exception {
        //String message = "Your ";
        StringBuffer message = new StringBuffer("Your ");
        boolean newSub = false;
        bm = new BackgroundManager();
        bd = (BackgroundDAO) command;
        MutationsDAO md = new MutationsDAO();
        StrainsDAO sd = new StrainsDAO();
        if (request.getParameter("deletion") != null) {
            //a record deletion, need to check whether background id exists in strains or mutations
            MutationsManager mm = new MutationsManager();
            StrainsManager sm = new StrainsManager();

        }
        if (bd.getId_bg() == 0) {
            newSub = true;
            message = new StringBuffer(message).append("new background submission ");
        } else {
            message = new StringBuffer(message).append("update for background id ").append(bd.getId_bg()).append(" submitted successfully ");
        }
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Object o = ((UserDetails) obj).getUsername();
        String userName = o.toString().toUpperCase();
        bd.setUsername(userName);
        if (request.getParameter("speciesNew") != null && request.getParameter("speciesNew").length() > 1) {
            bd.setSpecies(request.getParameter("speciesNew"));
        }
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        Date date = new Date();
        bd.setLast_change(dateFormat.format(date));
        if (request.getParameter("delete") != null) {
            if (request.getParameter("delete").equals("y")) {
                int bgid = bd.getId_bg();
                bm.delete(bd);
                message = new StringBuffer().append("The record with the background id of ").append(bgid).append(" has been successfully deleted.");
            }
        } else {
            bm.save(bd);
        }
        if (newSub) {
            message = new StringBuffer(message).append(" with the id of ").append(bd.getId_bg()).append(" has been successfully saved.");
        }
        request.getSession().setAttribute(
                "message",
                getMessageSourceAccessor().getMessage("Message",
                message.toString()));
        return new ModelAndView(getSuccessView());
    }
}
