/*
 * AjaxSpreadsheetController.java
 *
 * Created on 28 March 2008, 11:24
 *
 */
package org.emmanet.controllers;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.*;
import org.emmanet.jobs.WebRequests;

import org.emmanet.util.CreateSpreadsheet;
import org.emmanet.util.RunShell;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 *
 * @author phil
 */
public class SpreadsheetController implements Controller {

    private WebRequests webRequest;
    private Map model = new HashMap();
    List data = null;
    List columns = null;

    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {
        System.out.println("=========== controller called");
//httpServletResponse.sendRedirect("http://internal.emmanet.org/statistics/statistics.xls");
        if (httpServletRequest.getParameter("ID") != null) {
            if (httpServletRequest.getParameter("ID").equals("stats")) {
                //OK SPREADSHEET CREATION HALTED TO SAVE DEVELOPMENT TIME USING EXISTING archive2excel.pl
                //so ping off to the following 

                RunShell rs = new RunShell();
                //TODO BEFORE GOING LIVE CHECK THAT THIS RUNS /INTERNAL/SCRIPTS/PERL/archive2excel.pl
                // AND ADD PATH AND SCRIPT IN PLACE OF BELOW AND UNCOMMENT //#####
                //String[] run = {"/home/phil/","string.pl >  /home/phil/commandout.txt"};
                //Hardcoded for now until this is rebuilt TODO
                //////////////// String[] run = {"/data/web/internal/scripts/perl/","archive2excel.pl -v -o statistics.xls -u phil.1 -p wilkinson -d emmastr"};
                String[] run = {"/data/web/TESTsubmissions/", "spreadsheets.sh"};
                //String[] run = {"/data/web/EmmaStrains/cron/", "statistics.sh search strains emmastr"};
                rs.execute(run);
                //#####httpServletResponse.sendRedirect("http://internal.emmanet.org/statistics/statistics.xls");
                httpServletResponse.sendRedirect("http://internal.emmanet.org/statistics/statistics.xls");


                String[] sheets = {"emmaStrains"};/*, "requestsByCentre" "requestsByOrigin",
                "requestsByStrains", "submissions", "listOfSubmissions", "listOfRequests",
                "archive"};*/
                model.put("sheets", sheets);
                String[] headers_emmaStrains = {"Muttype", "subtype", "EMMA ID", "name", "gene"};
                String[] headers_requestsByCentre = {"Date {quarter of the year}", "CNR", "CDTA", "GSF", "MRC", "KI", "FCG", "SUM"};
                String[] headers_submissions = {"Date {quarter of the year}", "SUM"};
                String[] headers_listOfSubmissions = {"Date", "EMMA_ID", "Original Strain Name", "Accepted", "Archiving centre", "Country of origin"};
                String[] headers_listOfRequests = {"EMMA ID", "Strain name", "Depositor", "Archcentre", "Request arrived", "Processed date", "Requester", "Country to go", "Requested material", "State of the requested material", "Shipped Material", "Notes", "ST-live", "ST-frozen", "ST-rederivation"};
                String[] headers_archive = {"EMMA ID", "Strain name", "Archiving Centre", "Submitted {date}", "Accepted/Rejected {date}", "WT lines arrived {date}", "WT lines rederivation started {date}", "Received by the Centre {date}", "Freezing started {date}", "Archived {date}", "Mut type/subtype", "Str Access", "Grace period end", "Availability", "Funding source", "Time for evaluation", "Time evaluation - arrival", "WT KO lines, rederivation", "Time arrived - start of freezing", "Time start of freezing - archived", "Time submitted - archived"};

                List columns_emmaStrains = Arrays.asList(headers_emmaStrains);
                List columns_requestsByCentre = Arrays.asList(headers_requestsByCentre);
                // TODO SET FROM OBJECT List columns_requestsByOrigin = Arrays.asList( headers_requestsByOrigin);
                // TODO SET FROM OBJECT List columns_requestsByStrains = Arrays.asList( headers_requestsByStrains);
                List columns_submissions = Arrays.asList(headers_submissions);
                List columns_listOfSubmissions = Arrays.asList(headers_listOfSubmissions);
                List columns_listOfRequests = Arrays.asList(headers_listOfRequests);
                List columns_archive = Arrays.asList(headers_archive);

                model.put("columns_emmaStrains", columns_emmaStrains);
                model.put("columns_requestsByCentre", columns_requestsByCentre);
                //model.put("columns_requestsByOrigin", columns_requestsByOrigin);
                // model.put("columns_requestsByStrains", columns_requestsByStrains);
                model.put("columns_submissions", columns_submissions);
                model.put("columns_listOfRequests", columns_listOfRequests);
                model.put("columns_archive", columns_archive);

                model.put("filename", "stats");

                //TODO 4 TESTING ONLY
                columns = Arrays.asList(headers_emmaStrains);
                model.put("columns", columns_emmaStrains);

                data = getWebRequest().emmaStrains();

                // TODO need data map for each sheet
                model.put("data_emmaStrains", data);

            } else if (httpServletRequest.getParameter("ID").equals("strains")) {
                /* Due to time constraints and difficulties returning values from model that aren't affected by duplicate col names
                 * the decision was made to use existing perl script for now to generate statistics workbook
                 * Perl script called from org.emmanet.utils.RunShell.java
                 */
                // Need filename to determine action in CreateSpreadsheet.java
                String[] sheets = {"EMMA Strains Table"};
                data = getWebRequest().spreadSheetStrainsTbl();
                columns = getWebRequest().colsStrainsTbl();
                //CREATE MODEL
                model.put("data", data);

                model.put("columns", columns);

                model.put("sheets", sheets);

                model.put("filename", "strains");

            } else if (httpServletRequest.getParameter("view") != null) {
                //EUCOMM/EUMODIC Spreadsheet generation
                CreateSpreadsheet cs = new CreateSpreadsheet();


            }
        }

        // specific elements added to map accoring to workbook being created
        // prior to this point
        CreateSpreadsheet workbook = new CreateSpreadsheet();
        return new ModelAndView("spreadsheets", model);
    }

    public WebRequests getWebRequest() {
        return webRequest;
    }

    public void setWebRequest(WebRequests webRequest) {
        this.webRequest = webRequest;
    }
}
