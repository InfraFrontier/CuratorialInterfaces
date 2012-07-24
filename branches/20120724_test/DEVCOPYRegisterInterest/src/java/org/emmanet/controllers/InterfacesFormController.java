/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.emmanet.jobs.WebRequests;
import org.emmanet.model.StrainsDAO;
import org.emmanet.model.StrainsManager;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author phil
 */
public class InterfacesFormController extends SimpleFormController {
//TODO - CHECK WHETHER FORM CONTROLLER IS NEEDED AS OPPOSED TO VIEW CONTROLLER
    public static final String MAP_KEY = "returnedOut";
    private Map returnedOut = new HashMap();
    private String strainsSuccessView;
    private String requestsSuccessView;

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {

        // Get user ID
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String uID = ((UserDetails) obj).getUsername();
        Object o = ((UserDetails) obj).getUsername();
        Object command = null;
        StrainsManager sm = new StrainsManager();
        StrainsDAO sdao = (StrainsDAO) command;

        WebRequests wr = new WebRequests();

        System.out.println("UID = " + uID.toUpperCase());
        returnedOut.put(
                "UserName", o.toString());

        List strains = new ArrayList();
        //strains = sm.getStrains();
        strains = sm.getStrainNamesOnly();
        returnedOut.put("strains", strains);//strains

        List commonNames = new ArrayList();//
        commonNames = sm.getComStrainNamesOnly();
        returnedOut.put("commonNames", commonNames);

        List status = new ArrayList();
        List statusSort = new ArrayList();

  //      status = sm.getDistinctStatus();
        //added to satisfy requirement of Sabine Fessel that drop list should be ordered in this way not by db order.
    //    for (Iterator it = status.iterator(); it.hasNext();) {
/*        for (int it = 0;status.size() <=it; it++) {
            Object element = status.get(it);//].next();
            System.out.println(element.toString());
            if (element.toString().equals("EVAL")) {
                statusSort.add(0, element);
            } else if (element.toString().equals("ACCD")) {
                statusSort.add(1, element);
            } else if (element.toString().equals("ARRD")) {
                statusSort.add(2, element);
            } else if (element.toString().equals("ARING")) {
                statusSort.add(3, element);
            } else if (element.toString().equals("ARCHD")) {
                statusSort.add(4, element);
            } else if (element.toString().equals("RJCTD")) {
                statusSort.add(5, element);
            } else if (element.toString().equals("TNA")) {
                statusSort.add(6, element);
            } else {
                //catch all for elements not specified above
                statusSort.add(element);
            }
        }
*/
       status.add("EVAL");
        status.add("ACCD");
        status.add("ARRD");
        status.add("ARING");
        status.add("ARCHD");
        status.add("RJCTD");
        status.add("TNA");
        
 returnedOut.put("status", status);

        List labs = new ArrayList();
        labs = wr.emmaArchCentres();
        returnedOut.put("labs", labs);

        List access = new ArrayList();
        access = sm.getStrainAccess();
        returnedOut.put("access", access);

        List funding = new ArrayList();
        funding = sm.getGrantSource();
        returnedOut.put("funding", funding);
        
        List strainRelatedFunding = new ArrayList();
        strainRelatedFunding = sm.getStrainRelatedFundingSource();
        returnedOut.put("fundingStrains", strainRelatedFunding);
        
        List reqRelatedFunding = new ArrayList();
        reqRelatedFunding = sm.getReqRelatedFundingSource();
        returnedOut.put("fundingReqs", reqRelatedFunding);

        List reqStatus = new ArrayList();
        reqStatus = wr.reqStatus();
        returnedOut.put("reqStatus", reqStatus);

        List conCountries = new ArrayList();
        conCountries = wr.countries();
        returnedOut.put("conCountries", conCountries);

        List muts = new ArrayList();
        muts = sm.getMuts("");
        returnedOut.put("muts", muts);

        List mutsSub = new ArrayList();
        mutsSub = sm.getMutsSub("");
        returnedOut.put("mutsSub", mutsSub);

        List rTools = new ArrayList();
        rTools = sm.getRTools();
        returnedOut.put("rTools", rTools);
        
        List projects = new ArrayList();
        projects = sm.getProjects();
        returnedOut.put("projects", projects);

        List genes = new ArrayList();
        genes = sm.getGenes();
        returnedOut.put("genes", genes);

        /* Now lets check to see if the form was submitted and whether it is a 
         * requests or strains search
         */

        if (request.getParameter("ss") != null) {
            /* We have a Strains search situation!! */
            return new ModelAndView(getStrainsSuccessView());
        } else if (request.getParameter("sr") != null) {
            /* We have a Requests search situation!! */
            return new ModelAndView(getRequestsSuccessView());
        } else {
            return new ModelAndView(getSuccessView(), MAP_KEY, returnedOut);
        }
    }

    public String getStrainsSuccessView() {
        return strainsSuccessView;
    }

    public void setStrainsSuccessView(String strainsSuccessView) {
        this.strainsSuccessView = strainsSuccessView;
    }

    public String getRequestsSuccessView() {
        return requestsSuccessView;
    }

    public void setRequestsSuccessView(String requestsSuccessView) {
        this.requestsSuccessView = requestsSuccessView;
    }
}
