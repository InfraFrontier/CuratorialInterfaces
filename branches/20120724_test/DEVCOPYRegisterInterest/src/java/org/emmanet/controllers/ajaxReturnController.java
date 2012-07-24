/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.controllers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.emmanet.jobs.EmmaBiblioJOB;
import org.emmanet.jobs.EmmaBiblioJOB.FetchBiblio;
import org.emmanet.jobs.WebRequests;
import org.emmanet.model.BackgroundManager;
import org.emmanet.model.PeopleDAO;
import org.emmanet.model.PeopleManager;
import org.json.simple.JSONObject;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author phil
 */
public class ajaxReturnController extends SimpleFormController {

    public static final String MAP_KEY = "returnedOut";
    private Map returnedOut = new HashMap();
    private WebRequests webRequest;
    private List returnedResults = null;
    private LinkedList JSONobjects;

    @Override
    //  public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
    //   protected Object formBackingObject(HttpServletRequest request) {
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        if (request.getParameter("q") != null) {
            System.out.println("we are here");
            //this is an jquery ajax call for autocomplete strain names from insert form
        /*    if (request.getParameter("list") != null) {
            if (request.getParameter("list").equals("cryo")) {
            //this is to generate a cryo list autocompletedropdown on the fly
            BackgroundManager bm = new BackgroundManager();
            String query = request.getParameter("q");
            bm.
            }
            }*/
            if (!request.getParameter("q").startsWith("em:") && request.getParameter("query") == null) {
                System.out.println("query will be from webrequests");
                webRequest = new WebRequests();
                int query = Integer.parseInt(request.getParameter("q"));
                returnedResults = webRequest.strainList(query);
                System.out.println("returned results" + returnedResults.size());
                returnedOut.put("ajaxReturn", returnedResults);

            } // 
            else if (request.getParameter("query") != null && request.getParameter("query").equals("bg")) {
                System.out.println("query will be from backgrounds");
                BackgroundManager bm = new BackgroundManager();
                String backGround = request.getParameter("q");
                System.out.println("q value is::" + request.getParameter("q"));
                System.out.println("q values to string is::" + backGround);
                returnedResults = bm.getBGDAOByName(backGround);//.getBackgrounds();

                System.out.println("returned out size==" + returnedResults.size());
                returnedOut.put("ajaxReturn", returnedResults);
            } else {
                System.out.println("query will be from null");
                returnedOut.clear();
            }
        }

        if (request.getParameter("funct") != null && request.getParameter("funct").equals("peopleCall")) {

            System.out.println("AJAXCONTROLLERFUNCTIONCALLED " + request.getParameter("email"));
            if (request.getParameter("email") != null || !request.getParameter("email").equals("")) {
                PeopleDAO pd = new PeopleDAO();
                PeopleManager pm = new PeopleManager();
                List people = pm.getPeopleByEMail(request.getParameter("email"));
                List peopleDAOs = new LinkedList();
                JSONObject obj = new JSONObject();
                if (people.isEmpty()) {
                    //Do nothing
                } else {
                    System.out.println("PEOPLEDATA FUNCTION CALLED AND THE PERSON LIST SIZE IS " + people.size());
                    JSONobjects = new LinkedList();
                    for (Iterator it = people.listIterator(); it.hasNext();) {
                        obj = new JSONObject();
                        pd = (PeopleDAO) it.next();
                        peopleDAOs.add(pd);

                        //return a JSON String

                        obj.put("id", pd.getId_per());
                        obj.put("title", pd.getTitle());
                        obj.put("firstname", pd.getFirstname());
                        obj.put("surname", pd.getSurname());
                        obj.put("email", pd.getEmail());
                        obj.put("phone", pd.getPhone());
                        obj.put("fax", pd.getFax());
                        obj.put("ilar", pd.getIlar_code());
                        //laboratory details
                        obj.put("institution", pd.getLabsDAO().getName());
                        obj.put("dept", pd.getLabsDAO().getName());
                        obj.put("address1", pd.getLabsDAO().getAddr_line_1());
                        obj.put("address2", pd.getLabsDAO().getAddr_line_2());
                        obj.put("town", pd.getLabsDAO().getTown());
                        obj.put("county", pd.getLabsDAO().getProvince());
                        obj.put("postcode", pd.getLabsDAO().getPostcode());
                        obj.put("country", pd.getLabsDAO().getCountry());

                        System.out.println(obj.toString());
                        JSONobjects.add(obj);
                    }
                    returnedOut.put("JSON", JSONobjects);
                }
                returnedOut.put("peopleDAO", peopleDAOs);
            }
        }


        if (request.getParameter("funct") != null && request.getParameter("funct").equals("pubMed")) {
            List paper = new LinkedList();
            EmmaBiblioJOB ej = new EmmaBiblioJOB();
            int pmID = Integer.parseInt(request.getParameter("pubmedid"));
            FetchBiblio fb = (FetchBiblio) ej.fetchPaper(pmID);
            paper.add(0, fb.title);
            paper.add(1, fb.author1);
            paper.add(2, fb.author2);
            paper.add(3, fb.journal);
            paper.add(4, fb.year);
            paper.add(5, fb.volume);
            paper.add(6, fb.issue);
            paper.add(7, fb.pages);
            paper.add(8, fb.paperid);
            returnedOut.put("paper", paper);
        }
        return new ModelAndView("ajaxReturn", MAP_KEY, returnedOut);
        //  return returnedResults;


    }
}
