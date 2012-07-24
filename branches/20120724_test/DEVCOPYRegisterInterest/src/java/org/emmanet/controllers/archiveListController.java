/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.controllers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.emmanet.model.StrainsManager;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author phil
 */
public class archiveListController extends SimpleFormController {

    private ModelAndView includeView;
    private Map returnedOut = new HashMap();
    public static final String MAP_KEY = "returnedOut";
    private StrainsManager sm = new StrainsManager();

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        Object o = ((UserDetails) obj).getUsername();
        String userName = o.toString();
        returnedOut.put(
                "UserName", userName);
        
        List results = sm.getLiveArchivesByCentre(userName);
        returnedOut.put("searchDescription", "all strains being archived for user " + userName);
        returnedOut.put("results", results);
        returnedOut.put("searchSize", results.size());
        
        return new ModelAndView(getSuccessView(), MAP_KEY, returnedOut);
    }

    //public ModelAndView getIncludeView() {
    //    return includeView;
    //}

    //public void setIncludeView(ModelAndView includeView) {
    //    this.includeView = includeView;
    //}
}
