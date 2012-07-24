/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.controllers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.emmanet.model.ArchiveDAO;
import org.emmanet.model.ArchiveManager;
import org.emmanet.model.LaboratoriesManager;
//import org.emmanet.model.LaboratoriesStrainsDAO;
import org.emmanet.model.StrainsDAO;
import org.emmanet.model.StrainsManager;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author phil
 */
public class poUpdateInterfaceFormController extends SimpleFormController {

    private String requestsSuccessView;
    private ArchiveManager am = new ArchiveManager();
    private StrainsManager sm = new StrainsManager();
    private int strainID;
    private Map returnedOut = new HashMap();
    private List strainAccess;
    private Object command;
    //private ArchiveDAO xDAO = (ArchiveDAO) command;
    private StrainsDAO sDAO = (StrainsDAO) command;

    @Override
    protected Object formBackingObject(HttpServletRequest request) {
        if (request.getParameter("poEdit") != null) {
            strainID = Integer.parseInt(request.getParameter("poEdit"));
            int archID = sm.getArchID(strainID);
            ArchiveDAO ad = am.getReqByID(archID);//am.getReqByArchID(strainID);

            sDAO = sm.getStrainByID(strainID);
            return sDAO;//am.getReqByID(strainID);
        }
        return am;
    }

    @Override
    protected ModelAndView onSubmit(
            HttpServletRequest request,
            HttpServletResponse response,
            Object command,
            BindException errors)
            throws ServletException, Exception {
        StrainsDAO sDAO = (StrainsDAO) command;
       // LaboratoriesStrainsDAO lsDAO = new LaboratoriesStrainsDAO();
        LaboratoriesManager lm = new LaboratoriesManager();
        //am.save(aDAO);
        
        if(sDAO.getArchiveDAO().getLab_id_labo().length() == 0 && sDAO.getStr_status().equals("RJCTD")){
            sDAO.getArchiveDAO().setLab_id_labo("3");
        }
        
         sm.saveArchive(sDAO);
        System.out.println("saved");
        request.getSession().setAttribute(
                "message",
                getMessageSourceAccessor().getMessage("Message",
                "Your update submitted successfully"));

        System.out.println("ARCHIVE ID from sDAO " + sDAO.getArchiveDAO().getLab_id_labo());
        System.out.println("STRAIN ID FROM sDAO " + sDAO.getId_str());

       // Integer labid = Integer.parseInt(sDAO.getArchiveDAO().getLab_id_labo());
        //System.out.println("labid value is:: " + labid);
      //  lsDAO.setLab_id_labo(labid);
    //    lsDAO.setStr_id_str(sDAO.getId_str());

     //   System.out.println("id from lsdao " + lsDAO.getStr_id_str());
      //  System.out.println("archive id from lsdao " + lsDAO.getLab_id_labo());

        System.out.println("+++++++++++++++++++");
        System.out.println(" sDAO.getAvailabilitiesStrainsDAO().size() " + sDAO.getAvailabilitiesStrainsDAO().size());

     //   lm.save(lsDAO);
        //sm.saveArchive(sDAO);
        // return new ModelAndView(getSuccessView());
        ModelAndView mav = showForm(request, response, errors);
        return mav;
    }

    public String getRequestsSuccessView() {
        return requestsSuccessView;
    }

    public void setRequestsSuccessView(String requestsSuccessView) {
        this.requestsSuccessView = requestsSuccessView;
    }
}
