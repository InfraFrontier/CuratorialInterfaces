/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.controllers;

import java.net.BindException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringEscapeUtils;
import org.emmanet.model.StrainsManager;
import org.emmanet.model.StrainsDAO;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import org.json.simple.*;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author phil
 */
public class wp4DashboardController extends SimpleFormController {

    private StrainsManager sm = new StrainsManager();
    private StrainsDAO sd;
    private Map returnedOut;
    public static final String MAP_KEY = "returnedOut";
    /*      public ModelAndView handleRequest(HttpServletRequest arg0, HttpServletResponse arg1) throws Exception {
    
    return new ModelAndView(getSuccessView(),MAP_KEY, returnedOut);
    }*/

    @Override
    // protected Object formBackingObject(HttpServletRequest request) {
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        returnedOut = new HashMap();

        if (request.getParameter("view") != null) {

            returnedOut = new HashMap();
            //sd = (StrainsDAO) sm.getStrainByID(2112);//TODO CHANGE TO QUERY RESULTS
            List list = sm.getStrainsByEmmaID(request.getParameter("emmaID"));
            returnedOut.put("list", list);
            List JSONobjects = new LinkedList();
            JSONObject obj;
            System.out.println("Results list size is :: " + list.size());

            if (request.getParameter("view") != null) {
                if (request.getParameter("view").equals("grid")) {

                } else if (request.getParameter("view").equals("spreadsheet")) {
                    System.out.println("SPREADSHEET VIEW CALLED");
                    this.setSuccessView("spreadsheets.emma");//TODO NEED TO CHANGE TO SPREADSHEET VIEW ONCE CREATED
                //   return new ModelAndView("redirect:spreadsheets.emma");//TODO NEED TO CHANGE TO SPREADSHEET VIEW ONCE CREATED
                }
            }
        //    return new ModelAndView(getSuccessView(), MAP_KEY, returnedOut);

        //return returnedOut;

        }
        return new ModelAndView(getSuccessView(), MAP_KEY, returnedOut);

    }

    public void setSm(StrainsManager sm) {
        this.sm = sm;
    }

    public StrainsManager getSm() {
        return sm;
    }
}


