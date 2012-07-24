/*
 * DashboardController.java
 *
 * Created on 21 February 2008, 11:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package org.emmanet.controllers;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.emmanet.jobs.WebRequests;

import org.emmanet.model.LabsDAO;
import org.emmanet.util.BarChart;
import org.emmanet.util.CreateSpreadsheet;
import org.emmanet.util.PieCharts;

import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 *
 * @author phil
 */
public class DashboardController implements Controller {

    private String successView;
    private String EUCOMMSuccessView;
    private String EUCOMMSheetView;
    public static final String MAP_KEY = "returnedOut";
    private String strainUrl = "http://emmanet.org/strains/strain_";
    private String strainUrlSuffix = ".utf8.php";//TODO SET FROM APP CONTEXT
    private List archives = null;
    private WebRequests webRequest;
    private Map returnedOut = new HashMap();
    private Map chart1Data = new HashMap();
    private Map Centres = new TreeMap();
    private double[] sendData = null;
    private String[] sendDataLabels = null;
    String[] arrCountries = {"Australia", "Austria", "Belgium", "Brazil", "Canada",
        "China", "Croatia", "Cyprus", "Czech Republic", "Denmark", "Estonia", "Finland",
        "France", "Germany", "Greece", "Hungary", "Ireland", "Israel", "Italy", "Japan",
        "Netherlands", "New Zealand", "Norway", "Poland", "Portugal", "Quebec",
        "Slovenia", "South Korea", "Spain", "Sweden", "Switzerland", "Taiwan",
        "United Kingdom", "United States"
    };
    private int[] arrProjYearMonths = {7, 8, 9, 10, 11, 12, 1, 2, 3, 4, 5, 6};
    private String chartImageUrl;
    private int projectID;
    private List list = new LinkedList();
   private int[] listDupReqs = new int[6];
   
    
        public void getDuplicates() {
       //finds duplicate request counts for each site for later use
      
      /*  listDupReqs[0] = webRequest.getEUCOMMEMMADuplicatedReqCounts("CNB", projectID);
        listDupReqs[1] = webRequest.getEUCOMMEMMADuplicatedReqCounts("CNR", projectID);
        listDupReqs[2] = webRequest.getEUCOMMEMMADuplicatedReqCounts("HMGU", projectID);
        listDupReqs[3] = webRequest.getEUCOMMEMMADuplicatedReqCounts("ICS", projectID);
        listDupReqs[4] = webRequest.getEUCOMMEMMADuplicatedReqCounts("MRC", projectID);
        listDupReqs[5] = webRequest.getEUCOMMEMMADuplicatedReqCounts("SANG", projectID);*/

        returnedOut.put("dupReqs", listDupReqs);
            
    }

    public int getYear() {
        Calendar cal = new GregorianCalendar();

        returnedOut.put("dupReqs", listDupReqs);
        // Get the components of the date
        // Used to autopopulate Map of results/year for charts
        int currYear = cal.get(Calendar.YEAR);
        int lastYear = currYear - 1;
        return lastYear;
    }

    public String buildStrainUrl(int id) {
        String myNameIsUrl = strainUrl + id + strainUrlSuffix;
        return myNameIsUrl;
    }

    public String chart1Data() {
        // This method generates data relating to number of EMMA strain submissions/Year
        // TODO check data collections as unsure they are correct.
        double[] hold = null;
        String[] holdDate = null;
        int currYear = getYear() + 1;
        double[] sendData = {4, 13, 44, 85, 172, 334, 688, 1377,2039,2723};
        String[] sendDataLabels = {"2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009","2010","2011"};
        int index = Arrays.binarySearch(sendDataLabels, "" + getYear() + "");

        if (index > 0) {
            //not in array so add label and data to data array
            // Last years database data not present need to query database and add to array
            //# List shipped = webRequest.strainsShippedCurrYear(currYear);
            //#Double val = (double)shipped.size();

            String shipped = webRequest.reqsCurrProjectYear("" + currYear + "%");
            double val = Double.valueOf(shipped.trim()).doubleValue();

            int size = sendData.length;
            size++;
            hold = new double[size];
            holdDate = new String[size];
            for (int i = 0; i < sendData.length; i++) {
                hold[i] = sendData[i];
                holdDate[i] = sendDataLabels[i];
            }
            // GET LAST VALUE OF ARRAY IN ORDER TO ADD TO CURRENT YEARS VALUE TO GET TOTAL
            double lastVal = hold[size - 2];
            System.out.println("LAST VALUE = " + lastVal);
            size--;
            hold[size] = val + lastVal;
            holdDate[size] = String.valueOf(currYear);
        }
        // SEND VALUES TO BAR CHART CLASS
        int[] sendChartSize = {400, 240};
        String sendXLabel = "Year";
        String sendYLabel = "Total Requests";
        String sendChartTitle = "EMMA Total Requests per Year";
        BarChart graphTotReq = new BarChart();
        String chartPath = graphTotReq.createChart(hold, holdDate, sendChartSize, sendXLabel, sendYLabel, sendChartTitle);
        returnedOut.put("chartTotReq", chartImageUrl + chartPath);
        return chartPath;
    }

    public String chart2Data() {
        // This method generates data relating to number of EMMA strain requests/Year
        // TODO check data collections as unsure they are correct.

        double[] hold = null;
        String[] holdDate = null;
        int currYear = getYear() + 1;
        double[] sendData2 = {4, 9, 31, 41, 87, 162, 354, 689, 662, 684};
        String[] sendDataLabels2 = {"2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009","2010","2011"};
        int index = Arrays.binarySearch(sendDataLabels2, "" + getYear() + "");

        if (index > 0) {
            //not in array so add label and data to data array
            // Last years database data not present need to query database and add to array
            //##List shipped = webRequest.strainsShippedCurrYear(currYear);

            String shipped = webRequest.reqsCurrProjectYear("" + currYear + "%");//##

            //## Double val = (double)shipped.size();
            double val = Double.valueOf(shipped.trim()).doubleValue();
            int size = sendData2.length;
            size++;
            hold = new double[size];
            holdDate = new String[size];
            for (int i = 0; i < sendData2.length; i++) {
                hold[i] = sendData2[i];
                holdDate[i] = sendDataLabels2[i];
            }
            size--;
            hold[size] = val;
            holdDate[size] = String.valueOf(currYear);
        }
        // SEND VALUES TO BAR CHART CLASS
        int[] sendChartSize = {400, 240};
        String sendXLabel = "Year";
        String sendYLabel = "Requests";
        String sendChartTitle = "EMMA Requests per Year";
        BarChart graph2 = new BarChart();
        System.out.println(hold.length + " - " + holdDate.length + " - " + sendChartSize.length);
        String chartPath = graph2.createChart(hold, holdDate, sendChartSize, sendXLabel, sendYLabel, sendChartTitle);
        returnedOut.put("chart2", chartImageUrl + chartPath);
        return chartPath;
    }

    public String reqsByCountry(int year) {
        LinkedList list;
        list = new LinkedList(Arrays.asList(arrCountries));
        LinkedList reqCountCountry;
        reqCountCountry = new LinkedList();
        List countryResult = webRequest.requestsByCountry(year);
        List countries = new ArrayList();
        List countryCount = new ArrayList();
        int index = -1;

        for (Iterator it = countryResult.listIterator(); it.hasNext();) {
            Object[] o = (Object[]) it.next();
            String ctry = (String) o[1];
            if (o[0].toString().equals("0")) {
                //do nothing with 0 vals not needed in chart
                //also prevents array out of bounds exception in Arrays.binarySearch
            } else {
                index = Arrays.binarySearch(arrCountries, ctry);
            }

            if (index >= 0) {
                //In array so add to lists
                countries.add(o[1]);
                countryCount.add(o[0].toString());
            } else {
                //Do nothing
            }
        }

        String[] arrData = (String[]) countryCount.toArray(new String[]{});
        double[] sendData = new double[arrData.length];

        for (int i = 0; i < arrData.length; i++) {
            //sendData[i] = arrData[i].doubleValue();
            String s = arrData[i];
            //sendData[i] = arrData[i];
            //Convert String to a double then add to List
            double d = Double.valueOf(s.trim()).doubleValue();
            sendData[i] = d;
        }

        returnedOut.put("country", countries);
        returnedOut.put("countryCounts", countryCount);

        String[] arrLabels = (String[]) countries.toArray(new String[]{});
        int[] sendChartSize4 = {800, 240};
        int[] sendPieSize = {200, 140, 100};
        String sendChartTitle = "Requests per Country";
        if (year != 0) {
            sendChartTitle += " for Current Project Year (July-June)";
        }
        BarChart chartReqCountries = new BarChart();

        String chartC = chartReqCountries.createChart(sendData, arrLabels,
                sendChartSize4, "Country", "Requests", sendChartTitle);
        if (year != 0) {

            returnedOut.put("chartCountryReqProjYear", chartImageUrl + chartC);
        } else {

            returnedOut.put("pieReqCountries", chartImageUrl + chartC);
        }
        return chartC;
    }

    public String reqsByProjYear() {
        LinkedList data;
        data = new LinkedList();

        LinkedList dataLabels;
        dataLabels = new LinkedList();

        NumberFormat formatter = new DecimalFormat("00");
        int prevYear = getYear();
        Calendar cal = new GregorianCalendar();
        int currMonth = cal.get(Calendar.MONTH) + 1;
        String formattedMonth;

        for (int i = 0; i < arrProjYearMonths.length; i++) {
            String queryDate = null;
            int projYearMonths = arrProjYearMonths[i];
            formattedMonth = formatter.format(projYearMonths);

            if (projYearMonths < 7) {
                if (projYearMonths <= currMonth) {
                    queryDate = prevYear + 1 + "-" + formattedMonth + "%";
                    int year = prevYear + 1;
                    dataLabels.add(formattedMonth + "/" + year);
                }

            } else {
                queryDate = prevYear + "-" + formattedMonth + "%";
                dataLabels.add(formattedMonth + "/" + prevYear);
            }
            if (queryDate != null) {
                String count = webRequest.reqsCurrProjectYear(queryDate);
                //System.out.println(queryDate + " count(*) =" + count);
                data.add(new Double(count));
            }

        }
        // Convert lists to arrays
        Double[] arrData = (Double[]) data.toArray(new Double[]{});
        double[] sendData = new double[arrData.length];

        for (int i = 0; i < arrData.length; i++) {
            sendData[i] = arrData[i].doubleValue();
        }

        String[] sendDataLabels = (String[]) dataLabels.toArray(new String[]{});

        int[] sendChartSize = {400, 240};
        String sendXLabel = "Month";
        String sendYLabel = "Requests";
        String sendChartTitle = "EMMA Requests Current Project Year (July-June)";
        BarChart graphTotReq = new BarChart();
        String chartPath = graphTotReq.createChart(sendData, sendDataLabels, sendChartSize, sendXLabel, sendYLabel, sendChartTitle);
        returnedOut.put("chartReqProjYear", chartImageUrl + chartPath);

        return chartPath;
    }

    public String chartSubmissions() {
        // This method generates data relating to number of EMMA strain submissions/Year
        // TODO check data collections as unsure they are correct.
        double[] hold = null;
        String[] holdDate = null;
        int currYear = getYear() + 1;
        double[] sendData2 = {121, 176, 272, 290, 237, 235, 307,437,344,358};
        String[] sendDataLabels2 = {"2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009","2010","2011"};
        int index = Arrays.binarySearch(sendDataLabels2, "" + getYear() + "");

        if (index > 0) {
            //not in array so add label and data to data array
            // Last years database data not present need to query database and add to array
            String submissionsCount = webRequest.submissionsPerYear("" + currYear + "%");

            double val = Double.valueOf(submissionsCount.trim()).doubleValue();
            int size = sendData2.length;
            size++;
            hold = new double[size];
            holdDate = new String[size];
            for (int i = 0; i < sendData2.length; i++) {
                hold[i] = sendData2[i];
                holdDate[i] = sendDataLabels2[i];
            }
            size--;
            hold[size] = val;
            holdDate[size] = String.valueOf(currYear);
        }
        // SEND VALUES TO BAR CHART CLASS
        int[] sendChartSize = {400, 240};
        String sendXLabel = "Year";
        String sendYLabel = "Submissions";
        String sendChartTitle = "EMMA Submissions per Year";
        BarChart graph2 = new BarChart();
        String chartPath = graph2.createChart(hold, holdDate, sendChartSize, sendXLabel, sendYLabel, sendChartTitle);
        returnedOut.put("submissionChartPerYear", chartImageUrl + chartPath);
        return chartPath;
    }

    public String totalSubmissions() {
        // This method generates data relating to number of EMMA strain submissions/Year
        // TODO check data collections as unsure they are correct.
        double[] hold = null;
        String[] holdDate = null;
        int currYear = getYear() + 1;
        double[] sendData = {121, 297, 569, 859, 1096, 1331, 1638,2075,2419,2777};
        String[] sendDataLabels = {"2002", "2003", "2004", "2005", "2006", "2007", "2008", "2009","2010","2011"};
        int index = Arrays.binarySearch(sendDataLabels, "" + getYear() + "");

        if (index > 0) {
            //not in array so add label and data to data array
            // Last years database data not present need to query database and add to array
            String submissionsCount = webRequest.submissionsPerYear("" + currYear + "%");///##
            double val = Double.valueOf(submissionsCount.trim()).doubleValue();
            int size = sendData.length;
            size++;
            hold = new double[size];
            holdDate = new String[size];
            for (int i = 0; i < sendData.length; i++) {
                hold[i] = sendData[i];
                holdDate[i] = sendDataLabels[i];
            }
            // GET LAST VALUE OF ARRAY IN ORDER TO ADD TO CURRENT YEARS VALUE TO GET TOTAL
            double lastVal = hold[size - 2];
            System.out.println("LAST VALUE = " + lastVal);
            size--;
            hold[size] = val + lastVal;
            holdDate[size] = String.valueOf(currYear);
        }
        // SEND VALUES TO BAR CHART CLASS
        int[] sendChartSize = {400, 240};
        String sendXLabel = "Year";
        String sendYLabel = "Total Submissions";
        String sendChartTitle = "EMMA Total Submissions per Year";
        BarChart graphTotReq = new BarChart();
        String chartPath = graphTotReq.createChart(hold, holdDate, sendChartSize, sendXLabel, sendYLabel, sendChartTitle);
        returnedOut.put("totalSubmissionsPerYear", chartImageUrl + chartPath);
        return chartPath;//TODO RETURN MAP
    }

    public String submissionProjectYear() {
        LinkedList data;
        data = new LinkedList();

        LinkedList dataLabels;
        dataLabels = new LinkedList();

        NumberFormat formatter = new DecimalFormat("00");
        int prevYear = getYear();
        Calendar cal = new GregorianCalendar();
        int currMonth = cal.get(Calendar.MONTH) + 1;
        String formattedMonth;

        for (int i = 0; i < arrProjYearMonths.length; i++) {
            String queryDate = null;
            int projYearMonths = arrProjYearMonths[i];
            formattedMonth = formatter.format(projYearMonths);

            if (projYearMonths < 7) {
                if (projYearMonths <= currMonth) {
                    queryDate = prevYear + 1 + "-" + formattedMonth + "%";
                    int year = prevYear + 1;
                    dataLabels.add(formattedMonth + "/" + year);
                }

            } else {
                queryDate = prevYear + "-" + formattedMonth + "%";
                dataLabels.add(formattedMonth + "/" + prevYear);
            }
            if (queryDate != null) {

                //change method to handle month and year to construct string
                String count = webRequest.submissionsPerYear(queryDate);
                //System.out.println(queryDate + " count(*) =" + count);
                data.add(new Double(count));
            }

        }


        Double[] arrData = (Double[]) data.toArray(new Double[]{});
        double[] sendData = new double[arrData.length];

        for (int i = 0; i < arrData.length; i++) {
            sendData[i] = arrData[i].doubleValue();
        }

        String[] sendDataLabels = (String[]) dataLabels.toArray(new String[]{});

        int[] sendChartSize = {400, 240};
        String sendXLabel = "Month";
        String sendYLabel = "Submissions";
        String sendChartTitle = "EMMA Submissions Current Project Year (July-June)";
        BarChart graphTotReq = new BarChart();
        String chartPath = graphTotReq.createChart(sendData, sendDataLabels, sendChartSize, sendXLabel, sendYLabel, sendChartTitle);
        returnedOut.put("chartSubmissionsProjYear", chartImageUrl + chartPath);

        return chartPath;


    }

    public String EUCOMMReqsByMonthYear(String project) {

        // SEND VALUES TO BAR CHART CLASS
        int[] sendChartSize = {400, 300};
        String sendXLabel = "Month/Year";
        String sendYLabel = "Total Requests";
        String sendChartTitle = "EMMA " + project + " Total Requests By Month/Year";
        List results = webRequest.getEUCOMMReqsByMonthYear(projectID);
        returnedOut.put("EUCOMMReqsByMonthYear", results);

        String[] sendDataLabels = new String[results.size()];
        double[] sendData = new double[results.size()];
        int i = 0;
        for (Iterator it = results.listIterator(); it.hasNext();) {
            Object[] o = (Object[]) it.next();
            sendDataLabels[i] = o[0].toString() + " " + o[1].toString();
            String str = o[2].toString();
            double d = Double.valueOf(str).doubleValue();
            sendData[i] = d;
            i++;
        }

        BarChart graphTotReq = new BarChart();
        String chartPath = graphTotReq.createChart(sendData, sendDataLabels, sendChartSize, sendXLabel, sendYLabel, sendChartTitle);
        returnedOut.put("EucommMonthYearChart", chartImageUrl + chartPath);
        return chartPath;
    }

    public String EUCOMMReqsByMonthYearCentre(String project) {

        // SEND VALUES TO BAR CHART CLASS
        int[] sendChartSize = {900, 450};
        String sendXLabel = "Month/Year";
        String sendYLabel = "Total Requests";
        String sendChartTitle = "EMMA Total " + project + " Requests By Centre";

        List monthYearResults = webRequest.getEUCOMMMonthYear(projectID);
        returnedOut.put("EUCOMMreqsMonthYear", monthYearResults);
        String[] sendDataLabels = new String[monthYearResults.size()];

        double[] sendDataCNB = new double[monthYearResults.size()];
        double[] sendDataCNR = new double[monthYearResults.size()];
        double[] sendDataHMGU = new double[monthYearResults.size()];
        double[] sendDataICS = new double[monthYearResults.size()];
        double[] sendDataMRC = new double[monthYearResults.size()];
        double[] sendDataSANG = new double[monthYearResults.size()];
        double[] sendDataCNRS = new double[monthYearResults.size()];
//###
        
        double[] sendDataUNIOULU = new double[monthYearResults.size()];
        //###
        int ii = 0;
        for (Iterator it1 = monthYearResults.listIterator(); it1.hasNext();) {
            Object[] oo = (Object[]) it1.next();

            sendDataLabels[ii] = oo[0].toString() + " " + oo[1].toString();
            System.out.println("DATALABEL= " + sendDataLabels[ii]);

            // need to query db on each iteration of month year
            int i = 0;
            List results = webRequest.getEUCOMMReqsByMonthYearCentre(oo[0].toString(), oo[1].toString(), projectID);
            for (Iterator it = results.listIterator(); it.hasNext();) {
                Object[] o = (Object[]) it.next();

                if (o[0].toString().equals("CNB")) {
                    sendDataCNB[ii] = doubleDStrConversion(o[4].toString());// - webRequest.getEUCOMMEMMADuplicatedReqCountsByMonth(o[0].toString(),projectID, oo[0].toString(), oo[1].toString());
                    System.out.println("VALUEADDED=CNB " + doubleDStrConversion(o[4].toString()));
                }/* else if (!o[0].toString().equals("CNB")) {
                sendDataCNB[ii] = 0;
                System.out.println("VALUEADDED=CNB " + sendDataCNB[ii]);String LabCode, int projectID, String month, String year
                }*/
                if (o[0].toString().equals("CNR")) {
                    sendDataCNR[ii] = doubleDStrConversion(o[4].toString());// - webRequest.getEUCOMMEMMADuplicatedReqCountsByMonth(o[0].toString(),projectID, oo[0].toString(), oo[1].toString());
                    System.out.println("VALUEADDED=CNR " + doubleDStrConversion(o[4].toString()));
                }/* else if (!o[0].toString().equals("CNR")) {
                sendDataCNR[ii] = 0;
                System.out.println("VALUEADDED=CNR " + sendDataCNR[ii]);
                }*/
                if (o[0].toString().equals("HMGU")) {
                    sendDataHMGU[ii] = doubleDStrConversion(o[4].toString());// - webRequest.getEUCOMMEMMADuplicatedReqCountsByMonth(o[0].toString(),projectID, oo[0].toString(), oo[1].toString());
                    System.out.println("VALUEADDED=HMGU " + doubleDStrConversion(o[4].toString()));
                }/* else if (!o[0].toString().equals("HMGU")) {
                sendDataHMGU[ii] = 0;
                System.out.println("VALUEADDED=HMGU " + sendDataHMGU[ii]);
                }*/
                if (o[0].toString().equals("ICS")) {
                    sendDataICS[ii] = doubleDStrConversion(o[4].toString());// - webRequest.getEUCOMMEMMADuplicatedReqCountsByMonth(o[0].toString(),projectID, oo[0].toString(), oo[1].toString());
                    System.out.println("VALUEADDED=ICS " + doubleDStrConversion(o[4].toString()));
                }/* else if (!o[0].toString().equals("ICS")) {
                sendDataICS[ii] = 0;
                System.out.println("VALUEADDED=ICS " + sendDataICS[ii]);
                }*/
                if (o[0].toString().equals("MRC")) {
                    sendDataMRC[ii] = doubleDStrConversion(o[4].toString());// - webRequest.getEUCOMMEMMADuplicatedReqCountsByMonth(o[0].toString(),projectID, oo[0].toString(), oo[1].toString());
                    System.out.println("VALUEADDED=MRC " + doubleDStrConversion(o[4].toString()));
                }/* else if (!o[0].toString().equals("MRC")) {
                sendDataMRC[ii] = 0;
                System.out.println("VALUEADDED=MRC " + sendDataMRC[ii]);
                }*/
                if (o[0].toString().equals("SANG")) {
                    sendDataSANG[ii] = doubleDStrConversion(o[4].toString());// - webRequest.getEUCOMMEMMADuplicatedReqCountsByMonth(o[0].toString(),projectID, oo[0].toString(), oo[1].toString());
                   // System.out.println("VALUEADDED=SANG " + doubleDStrConversion(o[4].toString()) + " AND VALUE REMOVED= " + webRequest.getEUCOMMEMMADuplicatedReqCountsByMonth(o[0].toString(),projectID, oo[0].toString(), oo[1].toString()));
                }/* else if (!o[0].toString().equals("SANG")) {
                sendDataSANG[ii] = 0;
                System.out.println("VALUEADDEDSANG " + sendDataSANG[ii]);
                }*/
                
                //###
                                if (o[0].toString().equals("UNIOULU")) {
                    sendDataUNIOULU[ii] = doubleDStrConversion(o[4].toString());// - webRequest.getEUCOMMEMMADuplicatedReqCountsByMonth(o[0].toString(),projectID, oo[0].toString(), oo[1].toString());
                   // System.out.println("VALUEADDED=SANG " + doubleDStrConversion(o[4].toString()) + " AND VALUE REMOVED= " + webRequest.getEUCOMMEMMADuplicatedReqCountsByMonth(o[0].toString(),projectID, oo[0].toString(), oo[1].toString()));
                }
                                                               if (o[0].toString().equals("CNRS")) {
                    sendDataCNRS[ii] = doubleDStrConversion(o[4].toString());// - webRequest.getEUCOMMEMMADuplicatedReqCountsByMonth(o[0].toString(),projectID, oo[0].toString(), oo[1].toString());
                   // System.out.println("VALUEADDED=SANG " + doubleDStrConversion(o[4].toString()) + " AND VALUE REMOVED= " + webRequest.getEUCOMMEMMADuplicatedReqCountsByMonth(o[0].toString(),projectID, oo[0].toString(), oo[1].toString()));
                }
                //###

                //sendData[i] = doubleDStrConversion(o[2].toString());
                Centres.put(o[0].toString(), i);
                i++; //old pos

            }
            ii++;
        }
        for (int j = 0; j < sendDataLabels.length; j++) {
            System.out.println("\t" + sendDataCNB[j] + "\t" + sendDataCNR[j] + "\t" + sendDataHMGU[j] + "\t" + sendDataICS[j] + "\t" + sendDataMRC[j] + "\t" + sendDataSANG[j] + "\t"+ sendDataCNRS[j] + "\t");

        }


        BarChart graphTotReq = new BarChart();
        String chartPath = graphTotReq.createMultiBarChart(sendDataCNB, sendDataCNR, sendDataHMGU, sendDataICS, sendDataMRC, sendDataSANG, sendDataLabels, sendChartSize, sendXLabel, sendYLabel, sendChartTitle);
        returnedOut.put("Centres", Centres);
        returnedOut.put("EUCOMMReqsByMonthYearCentre-CNB", sendDataCNB);
        returnedOut.put("EUCOMMReqsByMonthYearCentre-CNR", sendDataCNR);
        returnedOut.put("EUCOMMReqsByMonthYearCentre-HMGU", sendDataHMGU);
        returnedOut.put("EUCOMMReqsByMonthYearCentre-ICS", sendDataICS);
        returnedOut.put("EUCOMMReqsByMonthYearCentre-MRC", sendDataMRC);
        returnedOut.put("EUCOMMReqsByMonthYearCentre-SANG", sendDataSANG);
        //###
         returnedOut.put("EUCOMMReqsByMonthYearCentre-UNIOULU", sendDataUNIOULU);
         returnedOut.put("EUCOMMReqsByMonthYearCentre-CNRS", sendDataCNRS);
         //###
        returnedOut.put("EucommMonthYearCentreChart", chartImageUrl + chartPath);

        int[] list = new int[8];
        list[0] = webRequest.getEUCOMMEMMAReqCounts("CNB", projectID, false);
        list[1] = webRequest.getEUCOMMEMMAReqCounts("CNR", projectID, false);
        list[2] = webRequest.getEUCOMMEMMAReqCounts("HMGU", projectID, false);
        list[3] = webRequest.getEUCOMMEMMAReqCounts("ICS", projectID, false);
        list[4] = webRequest.getEUCOMMEMMAReqCounts("MRC", projectID, false);
        list[5] = webRequest.getEUCOMMEMMAReqCounts("SANG", projectID, false);
        //###
        list[6] = webRequest.getEUCOMMEMMAReqCounts("UNIOULU", projectID, false);
         list[7] = webRequest.getEUCOMMEMMAReqCounts("CNRS", projectID, false);
        //###
        
               //TEST
                for (int j = 0; j < list.length; j++) {
            System.out.println("list before" + list[j]);

        }

        

        int[] listCanc = new int[8];
        listCanc[0] = webRequest.getEUCOMMEMMAReqCounts("CNB", projectID, true);
        listCanc[1] = webRequest.getEUCOMMEMMAReqCounts("CNR", projectID, true);
        listCanc[2] = webRequest.getEUCOMMEMMAReqCounts("HMGU", projectID, true);
        listCanc[3] = webRequest.getEUCOMMEMMAReqCounts("ICS", projectID, true);
        listCanc[4] = webRequest.getEUCOMMEMMAReqCounts("MRC", projectID, true);
        listCanc[5] = webRequest.getEUCOMMEMMAReqCounts("SANG", projectID, true);
        //###
        listCanc[6] = webRequest.getEUCOMMEMMAReqCounts("UNIOULU", projectID, true);
        listCanc[7] = webRequest.getEUCOMMEMMAReqCounts("CNRS", projectID, true);
        //###

        returnedOut.put("reqsCountsCanc", listCanc);

      
        //TODO ADD TO SPREADSHEET AND REMOVE THESE FIGURES FROM TOTAL REQUEST COUNT
        //reset counts of requests to value after removing the duplicates
        
      /*            //TEST
                for (int j = 0; j < listDupReqs.length; j++) {
            System.out.println("list remove\t" + listDupReqs[j]);

        }
        
      list[0] = list[0] - listDupReqs[0];
        list[1] = list[1] - listDupReqs[1];
        list[2] = list[2] - listDupReqs[2];
        list[3] = list[3] - listDupReqs[3];
        list[4] = list[4] - listDupReqs[4];
        list[5] = list[5] - listDupReqs[5];
        
        //TEST
                for (int j = 0; j < list.length; j++) {
            System.out.println("listafter\t" + list[j]);

        }
        returnedOut.put("reqsCounts", list);
       * */
        returnedOut.put("reqsCounts", list);
// returnedOut.put("reqsCounts", listDupReqs);
        return chartPath;
    }

    /*
    //discard dups
    Map map = new HashMap();
    int iii = 0;
    while (iii < sendDataLabels.length) {
    //  Object obj = sendDataLabels [iii].toString();
    System.out.println("DATALABEL SIZE = " + iii + "        " + sendDataLabels.length);
    map.put(sendDataLabels[iii].toString(), "");
    iii++;
    }
    System.out.println("mapsize eq " + map.size());
    iii = 0;
    String[] sendDataLabels1 = new String[map.size()];
    Iterator mi = map.keySet().iterator();
    while (mi.hasNext()) {
    String label = (String) mi.next();
    sendDataLabels1[iii] = label;
    System.out.println("DATALABEL1 SIZE = " + sendDataLabels1.length);
    iii++;
    }
    
     */
    public String EUCOMMShippedByMonthYearCentre(String project) {

        // SEND VALUES TO BAR CHART CLASS
        int[] sendChartSize = {900, 450};
        String sendXLabel = "Month/Year";
        String sendYLabel = "Total Lines Shipped";
        String sendChartTitle = "EMMA Total " + project + " Lines Shipped By Centre";

        List monthYearResults = webRequest.getEUCOMMShippedByMonthYear(projectID);
        returnedOut.put("EUCOMMshipMonthYear", monthYearResults);
        String[] sendDataLabels = new String[monthYearResults.size()];


        double[] sendDataCNB = new double[monthYearResults.size()];
        double[] sendDataCNR = new double[monthYearResults.size()];
        double[] sendDataHMGU = new double[monthYearResults.size()];
        double[] sendDataICS = new double[monthYearResults.size()];
        double[] sendDataMRC = new double[monthYearResults.size()];
        double[] sendDataSANG = new double[monthYearResults.size()];
        double[] sendDataCNRS= new double[monthYearResults.size()];

        //###
        double[] sendDataUNIOULU = new double[monthYearResults.size()];
        
        int ii = 0;
        for (Iterator it1 = monthYearResults.listIterator(); it1.hasNext();) {
            Object[] oo = (Object[]) it1.next();

            sendDataLabels[ii] = oo[0].toString() + " " + oo[1].toString();
            System.out.println("DATALABEL= " + sendDataLabels[ii]);

            // need to query db on each iteration of month year
            int i = 0;
            List results = webRequest.getEUCOMMShippedByMonthYearCentre(oo[0].toString(), oo[1].toString(), projectID);
            for (Iterator it = results.listIterator(); it.hasNext();) {
                Object[] o = (Object[]) it.next();

                if (o[0].toString().equals("CNB")) {
                    sendDataCNB[ii] = doubleDStrConversion(o[4].toString());
                    System.out.println("VALUEADDED=CNB " + doubleDStrConversion(o[4].toString()));
                }/* else if (!o[0].toString().equals("CNB")) {
                sendDataCNB[ii] = 0;
                System.out.println("VALUEADDED=CNB " + sendDataCNB[ii]);
                }*/
                if (o[0].toString().equals("CNR")) {
                    sendDataCNR[ii] = doubleDStrConversion(o[4].toString());
                    System.out.println("VALUEADDED=CNR " + doubleDStrConversion(o[4].toString()));
                }/* else if (!o[0].toString().equals("CNR")) {
                sendDataCNR[ii] = 0;
                System.out.println("VALUEADDED=CNR " + sendDataCNR[ii]);
                }*/
                if (o[0].toString().equals("HMGU")) {
                    sendDataHMGU[ii] = doubleDStrConversion(o[4].toString());
                    System.out.println("VALUEADDED=HMGU " + doubleDStrConversion(o[4].toString()));
                }/* else if (!o[0].toString().equals("HMGU")) {
                sendDataHMGU[ii] = 0;
                System.out.println("VALUEADDED=HMGU " + sendDataHMGU[ii]);
                }*/
                if (o[0].toString().equals("ICS")) {
                    sendDataICS[ii] = doubleDStrConversion(o[4].toString());
                    System.out.println("VALUEADDED=ICS " + doubleDStrConversion(o[4].toString()));
                }/* else if (!o[0].toString().equals("ICS")) {
                sendDataICS[ii] = 0;
                System.out.println("VALUEADDED=ICS " + sendDataICS[ii]);
                }*/
                if (o[0].toString().equals("MRC")) {
                    sendDataMRC[ii] = doubleDStrConversion(o[4].toString());
                    System.out.println("VALUEADDED=MRC " + doubleDStrConversion(o[4].toString()));
                }/* else if (!o[0].toString().equals("MRC")) {
                sendDataMRC[ii] = 0;
                System.out.println("VALUEADDED=MRC " + sendDataMRC[ii]);
                }*/
                if (o[0].toString().equals("SANG")) {
                    sendDataSANG[ii] = doubleDStrConversion(o[4].toString());
                    System.out.println("VALUEADDED=SANG " + doubleDStrConversion(o[4].toString()));
                }/* else if (!o[0].toString().equals("SANG")) {
                sendDataSANG[ii] = 0;
                System.out.println("VALUEADDEDSANG " + sendDataSANG[ii]);
                }*/
                //###
                if (o[0].toString().equals("UNIOULU")) {
                    sendDataUNIOULU[ii] = doubleDStrConversion(o[4].toString());
                    System.out.println("VALUEADDED=UNIOULU " + doubleDStrConversion(o[4].toString()));
                }
                                if (o[0].toString().equals("CNRS")) {
                    sendDataCNRS[ii] = doubleDStrConversion(o[4].toString());
                    System.out.println("VALUEADDED=CNRS " + doubleDStrConversion(o[4].toString()));
                }
                //###
                //sendData[i] = doubleDStrConversion(o[2].toString());
                i++; //old pos
            }
            ii++;
        }
        for (int j = 0; j < sendDataLabels.length; j++) {
            System.out.println("\t" + sendDataCNB[j] + "\t" + sendDataCNR[j] + "\t" + sendDataHMGU[j] + "\t" + sendDataICS[j] + "\t" + sendDataMRC[j] + "\t" + sendDataSANG[j] + "\t" + sendDataCNRS[j] + "\t");

        }


        BarChart graphTotReq = new BarChart();
        String chartPath = graphTotReq.createMultiBarChart(sendDataCNB, sendDataCNR, sendDataHMGU, sendDataICS, sendDataMRC, sendDataSANG, sendDataLabels, sendChartSize, sendXLabel, sendYLabel, sendChartTitle);
        returnedOut.put("EUCOMMShipByMonthYearCentre-CNB", sendDataCNB);
        returnedOut.put("EUCOMMShipByMonthYearCentre-CNR", sendDataCNR);
        returnedOut.put("EUCOMMShipByMonthYearCentre-HMGU", sendDataHMGU);
        returnedOut.put("EUCOMMShipByMonthYearCentre-ICS", sendDataICS);
        returnedOut.put("EUCOMMShipByMonthYearCentre-MRC", sendDataMRC);
        returnedOut.put("EUCOMMShipByMonthYearCentre-SANG", sendDataSANG);
        //###
        returnedOut.put("EUCOMMShipByMonthYearCentre-UNIOULU", sendDataUNIOULU);
        returnedOut.put("EUCOMMShipByMonthYearCentre-CNRS", sendDataCNRS);
        //###
        returnedOut.put("EucommMonthYearCentreShippedChart", chartImageUrl + chartPath);

        // list = new ArrayList();

        int[] list = new int[8];
        list[0] = webRequest.getEUCOMMEMMAShippedCounts("CNB", projectID);
        list[1] = webRequest.getEUCOMMEMMAShippedCounts("CNR", projectID);
        list[2] = webRequest.getEUCOMMEMMAShippedCounts("HMGU", projectID);
        list[3] = webRequest.getEUCOMMEMMAShippedCounts("ICS", projectID);
        list[4] = webRequest.getEUCOMMEMMAShippedCounts("MRC", projectID);
        list[5] = webRequest.getEUCOMMEMMAShippedCounts("SANG", projectID);
        //###
list[6] = webRequest.getEUCOMMEMMAShippedCounts("UNIOULU", projectID);
list[7] = webRequest.getEUCOMMEMMAShippedCounts("CNRS", projectID);
//###
        /*  list.add(0, webRequest.getEUCOMMEMMAShippedCounts("CNB", projectID));
        list.add(1, webRequest.getEUCOMMEMMAShippedCounts("CNR", projectID));
        list.add(2, webRequest.getEUCOMMEMMAShippedCounts("HMGU", projectID));
        list.add(3, webRequest.getEUCOMMEMMAShippedCounts("ICS", projectID));
        list.add(4, webRequest.getEUCOMMEMMAShippedCounts("MRC", projectID));
        list.add(5, webRequest.getEUCOMMEMMAShippedCounts("SANG", projectID));*/


        returnedOut.put("shipCounts", list);


        return chartPath;
    }

    public String EUCOMMEMMAPercentageChart(String project) {
        int[] sendChartSize = {400, 300};
        String sendXLabel = "Month/Year";
        String sendYLabel = "Percentage of Total Shipments";
        String sendChartTitle = "EMMA " + project + " Percentage of Total EMMA Shipments";
        //First grab relevant months and iterate
        List monthYearResults = webRequest.getEUCOMMShippedByMonthYear(projectID);
        returnedOut.put("EUCOMMshipMonthYear", monthYearResults);
        String[] sendDataLabels = new String[monthYearResults.size()];
        double[] sendData = new double[monthYearResults.size()];


        int i = 0;
        for (Iterator it1 = monthYearResults.listIterator(); it1.hasNext();) {
            Object[] oo = (Object[]) it1.next();

            sendDataLabels[i] = oo[0].toString() + " " + oo[1].toString();
            //System.out.println("anaww = " + oo[0].toString() + " " + oo[1].toString());

            // need to query db on each iteration of month year

            int resultH = webRequest.getEUCOMMEMMACountsbyMonth(oo[0].toString(), oo[1].toString(), "AND req_status='SHIP'", "date_processed", projectID);

            int resultL = webRequest.getEUCOMMEMMACountsbyMonth(oo[0].toString(), oo[1].toString(), "AND req_status='SHIP' AND rtls_id =9", "date_processed", projectID);

            //work out percentage of total requests were eucomm
            //System.out.println("bnawww = " + resultL + " " + resultH);
            double calc = (double) resultL / (double) resultH * (double) 100;

            BigDecimal bd = new BigDecimal(Double.toString(calc));
            bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
            calc = bd.doubleValue();

            String result = "" + calc + "";

            sendData[i] = calc;//doubleDStrConversion(result)
            //tomorrow create graph using normal chart create with single dataset
            System.out.println("nawww = " + calc + " naww2 = " + sendData[i]);
            i++;
        }
        BarChart graphTotReq = new BarChart();
        String chartPath = graphTotReq.createChart(sendData, sendDataLabels, sendChartSize, sendXLabel, sendYLabel, sendChartTitle);
        returnedOut.put("EUCOMMShipPercentages", sendData);
        returnedOut.put("EucommMonthYearCentrePercentChart", chartImageUrl + chartPath);
        return chartPath;
    }

    public String EUCOMMEMMAPercentageReqsChart(String project) {
        int[] sendChartSize = {400, 300};
        String sendXLabel = "Month/Year";
        String sendYLabel = "Percentage of Total Requests";
        String sendChartTitle = "EMMA " + project + " Percentage of Total EMMA Requests";
        //First grab relevant months and iterate
        List monthYearResults = webRequest.getEUCOMMReqsByMonthYear(projectID);//.getEUCOMMShippedByMonthYear();
        returnedOut.put("EUCOMMreqsMonthYear", monthYearResults);
        String[] sendDataLabels = new String[monthYearResults.size()];
        double[] sendData = new double[monthYearResults.size()];
        int i = 0;
        for (Iterator it1 = monthYearResults.listIterator(); it1.hasNext();) {
            Object[] oo = (Object[]) it1.next();

            sendDataLabels[i] = oo[0].toString() + " " + oo[1].toString();
            System.out.println("anaww = " + oo[0].toString() + " " + oo[1].toString());

            // need to query db on each iteration of month year

            int resultH = webRequest.getEUCOMMEMMACountsbyMonth(oo[0].toString(), oo[1].toString(), "", "timestamp", projectID);//getEUCOMMReqsByMonthYearCentre
            int resultL = webRequest.getEUCOMMEMMACountsbyMonth(oo[0].toString(), oo[1].toString(), "AND rtls_id =9", "timestamp", projectID);
            //work out percentage of total requests were eucomm
            System.out.println("bnawww = " + resultL + " " + resultH);
            double calc;
            if (resultH > resultL) {
                calc = (double) resultL / (double) resultH * (double) 100;
            } else {
                calc = 0;
            }
            BigDecimal bd = new BigDecimal(Double.toString(calc));
            bd = bd.setScale(1, BigDecimal.ROUND_HALF_UP);
            calc = bd.doubleValue();

            String result = "" + calc + "";

            sendData[i] = calc;//doubleDStrConversion(result)
            //tomorrow create graph using normal chart create with single datasetx
            System.out.println("nawww = " + calc + " naww2 = " + sendData[i]);
            i++;
        }
        BarChart graphTotReq = new BarChart();
        String chartPath = graphTotReq.createChart(sendData, sendDataLabels, sendChartSize, sendXLabel, sendYLabel, sendChartTitle);
        returnedOut.put("EUCOMMReqPercentages", sendData);
        returnedOut.put("EucommMonthYearCentrePercentReqsChart", chartImageUrl + chartPath);
        return chartPath;
    }
    //####################################################################
    //  pie charts
    //####################################################################
    public String pie1Data() {
        String[] sendDataLabelsPieReq;
        double[] sendDataPieReq;
        // Cast Object to integer to set array size
        Integer lc = (Integer) returnedOut.get("labCount");
        int id_lab_labo = lc.intValue();
        sendDataPieReq = new double[id_lab_labo];
        sendDataLabelsPieReq = new String[id_lab_labo];

        for (int i = 0; i < id_lab_labo; i++) {
            //Cast Object to double
            //Double labReqCount = (Double) returnedOut.get("Lab" + i); THROWS CASTING ERROR SO DO INT THEN TO DBLE
            Integer labReqCount = (Integer) returnedOut.get("Lab" + i);
            //double laboCount = labReqCount.doubleValue();
            int laboCount = labReqCount.intValue();
            double dblLaboCount = (double) laboCount;
            sendDataPieReq[i] = laboCount;
            //Cast Object to String
            String Obj = (String) returnedOut.get("labCode" + i);
            sendDataLabelsPieReq[i] = Obj + " (" + laboCount + ")";
        }
        //TODO WORK OUT ALGORITHM TO SET SECTOR DEPTHS DYNAMICALLY
        double[] sendSectorDepths = {10, 7, 5, 3, 2, 2, 2, 2, 2};
        int[] sendChartSize4 = {600, 300};
        int[] sendPieSize = {300, 140, 100};
        String sendChartTitle4 = "Requests per Centre";
        int sendxPlodingSector = 0;
        PieCharts pie = new PieCharts();
        String pie1 = pie.createChart(sendDataPieReq, sendDataLabelsPieReq, sendSectorDepths,
                sendChartSize4, sendPieSize, sendxPlodingSector, sendChartTitle4);
        returnedOut.put("pie1", chartImageUrl + pie1);
        return pie1;//TODO RETURN STRING URL
    }
    //Pie Frozen/live
    public String pieMaterial() {

        List frozen = webRequest.requestsFrozen("all", 0);
        double fcount = (double) frozen.size();
        System.out.println("DEBUGGER1.2");
        List live = webRequest.requestsLive("all", 0);
        System.out.println("DEBUGGER3");
        double lcount = (double) live.size();
        double[] sendDataPie = {fcount, lcount};
        double[] sendSectorDepths = {10, 7};
        int[] sendChartSize4 = {400, 300};
        int[] sendPieSize = {200, 140, 100};
        String sendChartTitle4 = "Total Requested Material";
        int sendxPlodingSector = 0;

        returnedOut.put("FrozenCount", fcount);
        returnedOut.put("LiveCount", lcount);
        String[] sendDataLabelsPie = {"Frozen (" + frozen.size() + ")", "Live (" + live.size() + ")"};
        PieCharts pie = new PieCharts();
        String pieC = pie.createChart(sendDataPie, sendDataLabelsPie, sendSectorDepths,
                sendChartSize4, sendPieSize, sendxPlodingSector, sendChartTitle4);
        returnedOut.put("pieMaterial", chartImageUrl + pieC);
        return pieC;
    }

    public String pieMaterialByYear(int year) {

        List frozen = webRequest.requestsFrozen("", year);
        double fcount = (double) frozen.size();
        List live = webRequest.requestsLive("", year);
        double lcount = (double) live.size();
        double[] sendDataPie = {fcount, lcount};
        double[] sendSectorDepths = {10, 7};
        int[] sendChartSize4 = {250, 175};
        int[] sendPieSize = {125, 88, 38};
        String sendChartTitle4 = "Requested Material " + year;
        int sendxPlodingSector = 0;

        returnedOut.put("FrozenCount" + year, fcount);
        returnedOut.put("LiveCount" + year, lcount);
        String[] sendDataLabelsPie = {"Frozen (" + frozen.size() + ")", "Live (" + live.size() + ")"};
        PieCharts pie = new PieCharts();
        String pieC = pie.createChart(sendDataPie, sendDataLabelsPie, sendSectorDepths,
                sendChartSize4, sendPieSize, sendxPlodingSector, sendChartTitle4);
        return pieC;

    }

    public String pieEUCOMMrequestsByCountry(String project) {

        List EuReqsCountry = webRequest.getEUCOMMReqsByCountry(projectID);
        returnedOut.put("EUCOMMreqsByCountryData", EuReqsCountry);
        String[] sendDataLabelsPie = new String[EuReqsCountry.size()];
        double[] sendDataPie = new double[EuReqsCountry.size()];
        double[] sendSectorDepths = new double[EuReqsCountry.size()];
        String sendChartTitlePieEucomm = "EMMA " + project + " Requests by Country";
        int i = 0;
        for (Iterator it = EuReqsCountry.listIterator(); it.hasNext();) {
            Object[] o = (Object[]) it.next();
            sendDataLabelsPie[i] = o[0].toString() + " (" + o[1].toString() + ")";
            String str = o[1].toString();
            double d = Double.valueOf(str).doubleValue();
            sendDataPie[i] = d;
            sendSectorDepths[i] = EuReqsCountry.size() - i;
            i++;
        }
        int[] sendChartSize4 = {800, 400};
        int[] sendPieSize = {350, 140, 100};
        int sendxPlodingSector = EuReqsCountry.size() - 1;
        PieCharts pie = new PieCharts();
        String pieEucomm = pie.createChart(sendDataPie, sendDataLabelsPie, sendSectorDepths,
                sendChartSize4, sendPieSize, sendxPlodingSector, sendChartTitlePieEucomm);
        System.out.println("EUCOMM URL =" + pieEucomm);
        returnedOut.put("EUCOMMReqsByCountryPie", chartImageUrl + pieEucomm);
        return pieEucomm;
    }
    //####################################################################
    //  END pie charts
    //####################################################################
    //####################################################################
    //  START spreadsheets
    //####################################################################
    //TODO GET RID OF NOW USING VIEWS
    public boolean createStrainsWorkbook() throws IOException {
        String[] sheets = {"EMMA Strains Table"};
        List data = webRequest.spreadSheetStrainsTbl();
        List columns = webRequest.colsStrainsTbl();
        CreateSpreadsheet workbook = new CreateSpreadsheet();
        //workbook.create(sheets, 1, "Strains", data, columns);
        return true;
    }

    public ModelAndView handleRequest(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws Exception {

        // Get user ID
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        String uID = ((UserDetails) obj).getUsername();
        Object o = ((UserDetails) obj).getUsername();
        System.out.println("UID = " + uID.toUpperCase());
        returnedOut.put("UserName", o.toString());
        //GENERATE TEXTUAL STATS.
        // Pull archiving centres

        //EUCOMM or EUMODIC
        if (httpServletRequest.getParameter("page") != null) {
            // this.setSuccessView("/EUCOMMdashboard");

            //go to eucomm chart methods (for eumodic too)
            if (httpServletRequest.getParameter("page").equals("eucomm")) {
                projectID = 9;//now rtools_id is used
                returnedOut.put("page", "EUCOMM");
            }
            if (httpServletRequest.getParameter("page").equals("eumodic")) {
                projectID = 9;
                returnedOut.put("page", "EUMODIC");
            }
            getDuplicates();
            pieEUCOMMrequestsByCountry(httpServletRequest.getParameter("page").toUpperCase());
            EUCOMMReqsByMonthYear(httpServletRequest.getParameter("page").toUpperCase());

            EUCOMMReqsByMonthYearCentre(httpServletRequest.getParameter("page").toUpperCase());
            EUCOMMShippedByMonthYearCentre(httpServletRequest.getParameter("page").toUpperCase());
            EUCOMMEMMAPercentageChart(httpServletRequest.getParameter("page").toUpperCase());
            EUCOMMEMMAPercentageReqsChart(httpServletRequest.getParameter("page").toUpperCase());

            List popEUCOMM = webRequest.getEUCOMMReqsPopular(projectID);
            returnedOut.put("popularEUCOMMStrains", popEUCOMM);

            List EUCOMMCurrDistTime = webRequest.getEUCOMMEMMADistTimeCurrent();
            returnedOut.put("CurrentDistTime", EUCOMMCurrDistTime);

            List EUCOMMShipDistTime = webRequest.getEUCOMMEMMADistTimeShipped();
            returnedOut.put("ShippedDistTime", EUCOMMShipDistTime);

            if (httpServletRequest.getParameter("view") != null) {
                if (httpServletRequest.getParameter("view").equals("spreadsheet")) {
                    //spreadsheet view
                    return new ModelAndView(new statsSpreadsheetController(), returnedOut);
                }
            } else {
                return new ModelAndView(getEUCOMMSuccessView(), MAP_KEY, returnedOut);
            }

        }



        String path = chart2Data();
        String pathTotReq = chart1Data();
        LabsDAO ld;
        archives = webRequest.emmaArchCentres();
        System.out.println("Count = " + archives.size());

        reqsByCountry(0);

        int currYear = getYear() + 1;
        reqsByCountry(currYear);
        reqsByProjYear();
        // createStrainsWorkbook();

        for (int i = 0; i < archives.size(); i++) {
            //SEED OTHER QUERIES WITH LABS RESULTS
            ld = (LabsDAO) archives.get(i);
            System.out.println(ld.getId_labo() + ". " + ld.getName());
            returnedOut.put("labCount", archives.size());
            returnedOut.put("labName" + i, ld.getName());
            returnedOut.put("labCountry" + i, ld.getCountry());
            returnedOut.put("labCode" + i, ld.getCode());

            //################################################
            // NOW QUERY FOR STRAIN REQUESTS PER CENTRE
            //################################################
            List reqByLabo = webRequest.requestsPerLab(ld.getId_labo());
            //List reqByLabo = webRequest.requestsPerLab(ld.getId_labo());
            returnedOut.put("Lab" + i, reqByLabo.size());
            //TODO RETURN UNIQUE RESULT RATHER THAN SIZE OF LIST
            //################################################
            // NOW QUERY FOR ARCHIVE/FREEZING TIMES PER CENTRE
            //################################################

            Object[] laboServiceTimes = webRequest.labServiceTimes(ld.getId_labo());
            returnedOut.put("LabServiceTimeToArch" + i, laboServiceTimes[0]);
            returnedOut.put("LabServiceTimeToFrze" + i, laboServiceTimes[1]);

            //################################################
            // NOW QUERY FOR STRAINS PUBLIC/RECEIVED PER CENTRE
            //################################################

            String laboStrains = webRequest.labStrainCount(ld.getId_labo());
            returnedOut.put("LabStrainCount" + i, laboStrains);
            String laboPubStrains = webRequest.labPublicStrainCount(ld.getId_labo());
            returnedOut.put("LabPubStrainCount" + i, laboPubStrains);
            String laboArrdStrains = webRequest.labStrainsArrd(ld.getId_labo());
            returnedOut.put("LabArrdStrainCount" + i, laboArrdStrains);
            String laboNotArrdStrains = webRequest.labStrainsNotArrd(ld.getId_labo());
            returnedOut.put("LabNotArrdStrainCount" + i, laboNotArrdStrains);
        }

        String piePath = pie1Data();
        String piePathMaterial = pieMaterial();

        // int currYear = getYear() + 1;
        List chartUrls = new ArrayList();
        for (int i = currYear; i >= 2005; i--) {
            String pieMaterialByYear = pieMaterialByYear(i);
            chartUrls.add(chartImageUrl + pieMaterialByYear);
        }
        returnedOut.put("piematerialbyyear", chartUrls);

        //   List top5ROI = webRequest.top5ROI();
        //   WebRequestsDAO wr;
        //  for (int i = 0; i < 5; i++) {
        //      wr = (WebRequestsDAO) top5ROI.get(i);
        //     System.out.println(wr.getStrain_id().toString());
        // returnedOut.put("top5RoiId" + i, wr.getStrain_id());
        //returnedOut.put("top5RoiName" + i, wr.getStrain_name());
        //returnedOut.put("top5RoiStrainUrl" + i,
        // buildStrainUrl(wr.getStr_id_str()));
        //    }

        //  List top5Strains = webRequest.top5Strains();
        chartSubmissions();
        totalSubmissions();
        submissionProjectYear();

        //   for (int i = 0; i < 10; i++) {
        //       wr = (WebRequestsDAO) top5Strains.get(i);
        //     System.out.println(wr.getStrain_id().toString());
        // returnedOut.put("top5StrainsId" + i, wr.getStrain_id());
        //returnedOut.put("top5StrainsName" + i, wr.getStrain_name());
        //returnedOut.put("top5StrainUrl" + i,
        //  buildStrainUrl(wr.getStr_id_str()));
        //    }

        return new ModelAndView(getSuccessView(), MAP_KEY, returnedOut);
    }

    public double doubleDStrConversion(String str) {
        double d = Double.valueOf(str).doubleValue();
        return d;
    }

    public boolean doubleDStrConversion(int senti, String mapID) {
        boolean bool = true;
        return bool;
    }

    public String getSuccessView() {
        return successView;
    }

    public void setSuccessView(String successView) {
        this.successView = successView;
    }

    public WebRequests getWebRequest() {
        return webRequest;
    }

    public void setWebRequest(WebRequests webRequest) {
        this.webRequest = webRequest;
    }

    public String getChartImageUrl() {
        return chartImageUrl;
    }

    public void setChartImageUrl(String chartImageUrl) {
        this.chartImageUrl = chartImageUrl;
    }

    public String getEUCOMMSuccessView() {
        return EUCOMMSuccessView;
    }

    public void setEUCOMMSuccessView(String EUCOMMSuccessView) {
        this.EUCOMMSuccessView = EUCOMMSuccessView;
    }

    public String getEUCOMMSheetView() {
        return EUCOMMSheetView;
    }

    public void setEUCOMMSheetView(String EUCOMMSheetView) {
        this.EUCOMMSheetView = EUCOMMSheetView;
    }
}
