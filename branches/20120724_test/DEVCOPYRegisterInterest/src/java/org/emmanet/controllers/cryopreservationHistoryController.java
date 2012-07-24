/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.controllers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.emmanet.jobs.BackgroundListJOB;
import org.emmanet.model.ArchiveDAO;
import org.emmanet.model.ArchiveManager;
import org.emmanet.model.BackgroundDAO;
import org.emmanet.model.BackgroundManager;
import org.quartz.core.QuartzScheduler;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author phil
 */
public class cryopreservationHistoryController extends SimpleFormController {

    private String requestsSuccessView;
    private int archiveID;
    private ArchiveManager am = new ArchiveManager();
    private String fileLocation;
    private BackgroundManager bm = new BackgroundManager();

    @Override
    protected Object formBackingObject(HttpServletRequest request) {
        if (request.getParameter("cryoArchID") != null) {
            archiveID = Integer.parseInt(request.getParameter("cryoArchID"));
        }
        ArchiveDAO aDAO = new ArchiveDAO();
        HttpSession session = request.getSession(true);
        aDAO = am.getReqByArchID(archiveID);
        session.setAttribute("femalebgid", aDAO.getFemale_bg_id());
        session.setAttribute("malebgid", aDAO.getMale_bg_id());
        bm = new BackgroundManager();
        bm.getBackgrounds();
        //Check that background list file has been created by overnight job, if not create it to prevent error
        try {
            BufferedReader in = new BufferedReader(new FileReader(getFileLocation() + "bgNamesList.emma"));
            in.close();

        } catch (IOException e) {
            createList();
        }
        return aDAO;
    }

    @Override
    protected ModelAndView onSubmit(
            HttpServletRequest request,
            HttpServletResponse response,
            Object command,
            BindException errors)
            throws ServletException, Exception {
        ArchiveDAO adao = (ArchiveDAO) command;
        if (request.getParameter("male_bg_id_new") != null) {
            //This is a new entry for the table backgrounds
            String newBGName = request.getParameter("male_bg_id_new");

            List bd = bm.getBGDAOByName(newBGName);
            if (bd.size() == 0) {
                //OK lets add a new one
                BackgroundDAO bDAO = adao.getStrainsDAO().getBackgroundDAO();//. new BackgroundDAO();
                bDAO.setName(newBGName);

                bm.save(bDAO);
               // System.out.println("WOOHOO== " + bDAO.getId_bg() + " BGNAME= " + bDAO.getName());
                adao.setMale_bg_id("" + bDAO.getId_bg() + "");

                //Now regenerate list
                createList();

            }
        }
        if (request.getParameter("female_bg_id_new") != null) {
            //This is a new entry for the table backgrounds
            String newBGName = request.getParameter("female_bg_id_new");

            List bd = bm.getBGDAOByName(newBGName);
            if (bd.size() == 0) {
                //OK lets add a new one
                BackgroundDAO bDAO = adao.getStrainsDAO().getBackgroundDAO();//new BackgroundDAO();
                bDAO.setName(newBGName);

                bm.save(bDAO);
               // System.out.println("WOOHOO== " + bDAO.getId_bg() + " BGNAME= " + bDAO.getName());
                adao.setFemale_bg_id("" + bDAO.getId_bg() + "");

                //Now regenerate list
                createList();
            }
        }

        am.save(adao);
        System.out.println("saved");
        request.getSession().setAttribute(
                "message",
                getMessageSourceAccessor().getMessage("Message",
                "Your update submitted successfully"));

        return new ModelAndView(getSuccessView());
    }

    public void createList() {
        //TODO CALL METHOD IN SCHEDULED JOB OR TRIGGER SCHEDULED JOB
        try {
            BufferedWriter out = new BufferedWriter(new FileWriter(getFileLocation() + "bgNamesList.emma"));
            List backGrounds = bm.getBackgrounds();
            for (Iterator it = backGrounds.listIterator(); it.hasNext();) {
                Object[] o = (Object[]) it.next();
                out.write(o[0].toString()  + "||" );
                out.write(o[1].toString());
                out.newLine();
            }
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getRequestsSuccessView() {
        return requestsSuccessView;
    }

    public void setRequestsSuccessView(String requestsSuccessView) {
        this.requestsSuccessView = requestsSuccessView;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }
}
