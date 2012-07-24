/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.controllers;

import java.math.BigDecimal;
import java.net.BindException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.servlet.http.HttpSession;
import org.emmanet.model.StrainsManager;
import org.emmanet.model.LaboratoriesManager;
import org.emmanet.model.StrainsDAO;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import org.json.simple.*;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;


import org.apache.commons.lang.StringEscapeUtils;
import org.emmanet.model.LabsDAO;
import org.emmanet.util.MedianFinder;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author phil
 */
public class JSON_1 extends SimpleFormController {

    private StrainsManager sm = new StrainsManager();
    private StrainsDAO sd = new StrainsDAO();
    private LaboratoriesManager lm = new LaboratoriesManager();
    private LabsDAO ld = new LabsDAO();
    private Map returnedOut;
    public static final String MAP_KEY = "returnedOut";
    private List list;
    private List JSONobjects = new LinkedList();
    private JSONObject obj;
    private String dateDiff = "";
//ModelAndView
    //     @Override
    //    public void handleRequest(HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {
    @Override
    // @SuppressWarnings("empty-statement")
    protected Object formBackingObject(HttpServletRequest request) {
        returnedOut = new HashMap();

        obj = new JSONObject();
        JSONobjects = new LinkedList();

        String sql = "";
        String sql2 = "";
        String fromClause = "";
        String joinClause = "";
        String groupClause = "";
        String whereClause = "";
        if (request.getParameter("view").equals("grid") || request.getParameter("view").equals("spreadsheets")) {
            fromClause = " FROM strains s, archive a,laboratories l,projects_strains ps,sources_strains ss,availabilities_strains ast";
            joinClause = "";
            whereClause = " WHERE " +
                    "s.archive_id=a.id " +
                    "AND a.lab_id_labo = l.id_labo " +
                    "AND s.id_str=ps.str_id_str " +
                    "AND s.id_str=ss.str_id_str " +
                    "AND s.id_str=ast.str_id_str " +
                    "AND a.evaluated IS NOT NULL " +
                    "AND a.submitted IS NOT NULL ";
            String strain_accessInClause = "(";
            String projectinClause = "(";
            String fundingInClause = "(";
            String stockInClause = "(";
            String mutInClause = "(";
            String multiMutInClause = "(";



            //  if (request.getParameter("submit") != null) {
            //we are searching lets grab parameter values

            Enumeration param = request.getParameterNames();


            do {
                if (!param.hasMoreElements()) {
                    break;
                }
                String paramName = (String) param.nextElement();
                if (paramName.startsWith("strainaccess")) {
                    // strain_accessInClause = "(";

                    if (paramName.endsWith(".public")) {
                        strain_accessInClause = (new StringBuilder()).append(strain_accessInClause).append("'P'").toString();
                    }
                    if (paramName.endsWith(".confidential")) {
                        strain_accessInClause = (new StringBuilder()).append(strain_accessInClause).append("'C'").toString();
                    }
                    if (paramName.endsWith(".notfordist")) {
                        strain_accessInClause = (new StringBuilder()).append(strain_accessInClause).append("'N'").toString();
                    }
                    if (paramName.endsWith(".retracted")) {
                        strain_accessInClause = (new StringBuilder()).append(strain_accessInClause).append("'R'").toString();
                    }
                    strain_accessInClause = (new StringBuilder()).append(strain_accessInClause).append(",").toString();
                }
                if (paramName.startsWith("project")) {
                    //  projectinClause = "(";
                    if (paramName.endsWith(".community")) {
                        projectinClause = (new StringBuilder()).append(projectinClause).append("2").toString();
                    }
                    if (paramName.endsWith(".mgp")) {
                        projectinClause = (new StringBuilder()).append(projectinClause).append("5").toString();
                    }
                    if (paramName.endsWith(".eucomm")) {
                        projectinClause = (new StringBuilder()).append(projectinClause).append("3").toString();
                    }
                    if (paramName.endsWith(".eumodic")) {
                        projectinClause = (new StringBuilder()).append(projectinClause).append("4").toString();
                    }
                    projectinClause = (new StringBuilder()).append(projectinClause).append(",").toString();
                }
                if (paramName.startsWith("funding")) {
                    //  fundingInClause = "(";
                    if (paramName.endsWith(".s1wp4")) {
                        fundingInClause = (new StringBuilder()).append(fundingInClause).append("16").toString();
                    }
                    if (paramName.endsWith(".s2wp4")) {
                        fundingInClause = (new StringBuilder()).append(fundingInClause).append("17").toString();
                    }
                    if (paramName.endsWith(".s3wp4")) {
                        fundingInClause = (new StringBuilder()).append(fundingInClause).append("18").toString();
                    }
                    fundingInClause = (new StringBuilder()).append(fundingInClause).append(",").toString();
                }

                if (paramName.startsWith("stock")) {
                    // stockInClause = "(";
                    if (paramName.endsWith(".embryos")) {
                        stockInClause = (new StringBuilder()).append(stockInClause).append("4,").toString();
                        stockInClause = (new StringBuilder()).append(stockInClause).append("6").toString();
                    }
                    if (paramName.endsWith(".sperm")) {
                        stockInClause = (new StringBuilder()).append(stockInClause).append("7,").toString();
                        stockInClause = (new StringBuilder()).append(stockInClause).append("9").toString();
                    }
                    stockInClause = (new StringBuilder()).append(stockInClause).append(",").toString();
                }

                if (paramName.startsWith("mutation")) {
                    if (paramName.endsWith(".chranomoly")) {
                        mutInClause = (new StringBuilder()).append(mutInClause).append("'CH'").toString();
                    }
                    if (paramName.endsWith(".gene-trap")) {
                        mutInClause = (new StringBuilder()).append(mutInClause).append("'GT'").toString();
                    }
                    if (paramName.endsWith(".induced")) {
                        mutInClause = (new StringBuilder()).append(mutInClause).append("'IN'").toString();
                    }
                    if (paramName.endsWith(".spontaneous")) {
                        mutInClause = (new StringBuilder()).append(mutInClause).append("'SP'").toString();
                    }
                    if (paramName.endsWith(".targetedko")) {
                        mutInClause = (new StringBuilder()).append(mutInClause).append("'TG'").toString();
                    }
                    if (paramName.endsWith(".transgenic")) {
                        mutInClause = (new StringBuilder()).append(mutInClause).append("'TM'").toString();
                    }
                    mutInClause = (new StringBuilder()).append(mutInClause).append(",").toString();
                }

                if (paramName.startsWith("multimutation")) {
                    if (paramName.endsWith(".1")) {
                        //multiMutInClause = (new StringBuilder()).append(multiMutInClause).append("1").toString();
                    }
                    if (paramName.endsWith(".2")) {
                        // multiMutInClause = (new StringBuilder()).append(multiMutInClause).append("2").toString();
                    }
                // multiMutInClause = (new StringBuilder()).append(multiMutInClause).append(",").toString();
                }
            } while (true);

            /*  if (request.getParameter("reportingperiod").equals("subeval")) {
            strain_accessInClause = cleanClause(strain_accessInClause);
            }*/

            strain_accessInClause = cleanClause(strain_accessInClause);
            //  System.out.println("WHERE clause=" + strain_accessInClause);
            strain_accessInClause = cleanClause(strain_accessInClause);
            projectinClause = cleanClause(projectinClause);
            //System.out.println("WHERE clause=" + projectinClause);
            fundingInClause = cleanClause(fundingInClause);
            //System.out.println("WHERE clause=" + fundingInClause);
            stockInClause = cleanClause(stockInClause);
            //System.out.println("WHERE clause=" + stockInClause);
            mutInClause = cleanClause(mutInClause);
            //System.out.println("WHERE clause=" + mutInClause);
            multiMutInClause = cleanClause(multiMutInClause);
            //System.out.println("WHERE clause=" + multiMutInClause);
            //  }
//////////////////////////////////////////////////////////////////////////////////////
            if (!request.getParameter("view").startsWith("spreadsheet")) {
                sql = "SELECT " +
                        "COUNT(a.id) AS COUNT," +
                        "l.code";
                sql2 = "SELECT l.code,";
                System.out.println("reportingperiod=" + request.getParameter("reportingperiod"));
                if (request.getParameter("reportingperiod") != null) {
                    sql = (new StringBuilder()).append(sql).append(",").toString();
                    if (request.getParameter("reportingperiod").equals("subeval")) {
                        sql = (new StringBuilder()).append(sql).append(
                                "SUM(DATEDIFF(evaluated , submitted )) AS SUM, " +
                                "AVG(DATEDIFF(evaluated , submitted )) AS AVG, " +
                                "MAX(DATEDIFF(evaluated , submitted )) AS MAX," +
                                "MIN(DATEDIFF(evaluated , submitted )) AS MIN ").toString();
                        dateDiff = "DATEDIFF(evaluated , submitted )";
                        sql2 = (new StringBuilder().append(sql2).append(dateDiff + " AS DIFF")).toString();

                    } else if (request.getParameter("reportingperiod").equals("evalarr")) {
                        sql = (new StringBuilder()).append(sql).append(
                                "SUM(DATEDIFF(received,evaluated)) AS SUM, " +
                                "AVG(DATEDIFF(received,evaluated)) AS AVG, " +
                                "MAX(DATEDIFF(received,evaluated)) AS MAX," +
                                "MIN(DATEDIFF(received,evaluated)) AS MIN ").toString();
                        dateDiff = "DATEDIFF(received,evaluated)";
                        sql2 = (new StringBuilder().append(sql2).append(dateDiff + " AS DIFF")).toString();

                    } else if (request.getParameter("reportingperiod").equals("arrfrz")) {
                        sql = (new StringBuilder()).append(sql).append(
                                "SUM(DATEDIFF(freezing_started , received )) AS SUM, " +
                                "AVG(DATEDIFF(freezing_started , received )) AS AVG, " +
                                "MAX(DATEDIFF(freezing_started , received )) AS MAX," +
                                "MIN(DATEDIFF(freezing_started , received )) AS MIN ").toString();
                        dateDiff = "DATEDIFF(freezing_started , received)";
                        sql2 = (new StringBuilder().append(sql2).append(dateDiff + "AS DIFF")).toString();

                    } else if (request.getParameter("reportingperiod").equals("frzarch")) {
                        sql = (new StringBuilder()).append(sql).append(
                                "SUM(DATEDIFF(archived,freezing_started )) AS SUM, " +
                                "AVG(DATEDIFF(archived,freezing_started)) AS AVG, " +
                                "MAX(DATEDIFF(archived,freezing_started)) AS MAX," +
                                "MIN(DATEDIFF(archived,freezing_started )) AS MIN ").toString();
                        dateDiff = "DATEDIFF(archived,freezing_started)";
                        sql2 = (new StringBuilder().append(sql2).append(dateDiff + " AS DIFF")).toString();

                    } else if (request.getParameter("reportingperiod").equals("subarch")) {
                        sql = (new StringBuilder()).append(sql).append(
                                "SUM(DATEDIFF(archived,submitted )) AS SUM, " +
                                "AVG(DATEDIFF(archived,submitted)) AS AVG, " +
                                "MAX(DATEDIFF(archived,submitted)) AS MAX," +
                                "MIN(DATEDIFF(archived,submitted)) AS MIN ").toString();
                        dateDiff = "DATEDIFF(archived,submitted)";
                        sql2 = (new StringBuilder().append(sql2).append(dateDiff + " AS DIFF")).toString();

                    }
                }
            }
            
            //use mutations table if mutations are being searched on
            
            if (!mutInClause.equals("") || !multiMutInClause.equals("")) {
                 fromClause = (new StringBuilder()).append(fromClause).append(",mutations m").toString();
                whereClause =  (new StringBuilder()).append(whereClause).append("AND s.id_str = m.str_id_str ").toString();
              
            }

            if (!strain_accessInClause.equals("")) {
                if (!"".equals(whereClause)) {
                    whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
                }
                whereClause = (new StringBuilder()).append(whereClause).append("s.str_access IN " + strain_accessInClause).toString();
            }
            if (!projectinClause.equals("")) {
                if (!"".equals(whereClause)) {
                    whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
                }
                whereClause = (new StringBuilder()).append(whereClause).append("ps.project_id IN " + projectinClause).toString();
            //fromClause = (new StringBuilder()).append(fromClause).append(",projects_strains ps ").toString();
            }
            if (!fundingInClause.equals("")) {
                if (!"".equals(whereClause)) {
                    whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
                }
                whereClause = (new StringBuilder()).append(whereClause).append("ss.sour_id IN " + fundingInClause).toString();
            // fromClause = (new StringBuilder()).append(fromClause).append(",sources_strains ss ").toString();
            }
            if (!stockInClause.equals("")) {
                if (!"".equals(whereClause)) {
                    whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
                }
                whereClause = (new StringBuilder()).append(whereClause).append("ast.avail_id IN " + stockInClause).toString();
            // fromClause = (new StringBuilder()).append(fromClause).append(",availabilities_strains ast ").toString();
            }
            if (!mutInClause.equals("")) {
                if (!"".equals(whereClause)) {
                    whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
                }
                whereClause = (new StringBuilder()).append(whereClause).append("m.main_type IN " + mutInClause).toString();
               // fromClause = (new StringBuilder()).append(fromClause).append(",mutations m ").toString();
                if (mutInClause.contains("TG")) {
                    //Targetted KO in conjunction with a sub-type of KO

                    whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();


                    whereClause = (new StringBuilder()).append(whereClause).append(" m.sub_type IN ('KO') ").toString();
                }
            }
            if (!multiMutInClause.equals("")) {
                if (!"".equals(whereClause)) {
                    whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
                }
                whereClause = (new StringBuilder()).append(whereClause).append("multimutation IN " + multiMutInClause).toString();
            }
            if (request.getParameter("view").equals("grid")) {
                groupClause = " GROUP BY l.code";

                sql = (new StringBuilder()).append(sql).append(
                        joinClause +
                        fromClause +
                        whereClause +
                        groupClause).toString();

                System.out.println("SQL QUERY IS :: " + sql);
//sql="select id_req from web_requests where str_id_str like '%23%'";
                list = sm.getStrainsBySQL(sql);
                System.out.println("About to return " + list.size());
            //sd = (StrainsDAO) sm.getStrainByID(2112);//TODO CHANGE TO QUERY RESULTS

            // List list = sm.getStrainsByEmmaID("EM:0230");
            }
        }

        //    System.out.println("Results list size is :: " + list.size());

        if (request.getParameter("view") != null) {
            if (request.getParameter("view").equals("grid")) {

                for (Iterator it = list.listIterator(); it.hasNext();) {
                    obj = new JSONObject();

                    Object[] o = (Object[]) it.next();

                    obj.put("COUNT", o[0]);
                    obj.put("code", o[1]);
                    Double dee_o2 = 0.0;
                    Double dee_o3 = 0.0;
                    Double dee_o4 = 0.0;
                    Double dee_o5 = 0.0;

                    if (o[2] != null) {
                        dee_o2 = Double.parseDouble(o[2].toString());
                    }
                    if (o[3] != null) {
                        dee_o3 = Double.parseDouble(o[3].toString());
                    }
                    if (o[4] != null) {
                        dee_o4 = Double.parseDouble(o[4].toString());
                    }
                    if (o[5] != null) {
                        dee_o5 = Double.parseDouble(o[5].toString());
                    }
                    if (dee_o2 > 0.0) {
                        obj.put("SUM", roundDecimals(dee_o2));//o[2]);
                    }
                    if (dee_o3 > 0.0) {
                        obj.put("AVG", roundDecimals(dee_o3));//o[3]);
                    }
                    if (dee_o4 > 0.0) {
                        obj.put("MAX", roundDecimals(dee_o4));//o[4]);
                    }
                    if (dee_o5 > 0.0) {
                        obj.put("MIN", roundDecimals(dee_o5));//o[5]);                    //######################################
                    }
                    String mediansql = this.medianSQL(sql2, joinClause, fromClause, whereClause, groupClause, o[1].toString(), dateDiff);

                    List listDiffs = sm.getStrainsBySQL(mediansql);
                    getMedianValue(obj, listDiffs);

                    //###########################################
                    System.out.println(" JSONobjects size=" + JSONobjects.size());// JSONobjects=null;

                    JSONobjects.add(obj);
                }
                // System.out.flush();

                //DATEDIFF(archived,         freezing_started )
                //order by l.code
               /*groupClause = "ORDER BY l.code,DIFF";
                sql2 = (new StringBuilder()).append(sql2).append(
                joinClause +
                fromClause +
                whereClause +
                groupClause).toString();
                List listDiffs = sm.getStrainsBySQL(sql2);
                LaboratoriesManager lm = new LaboratoriesManager();
                List labCodes = lm.getArchivesByCode();
                for (Iterator Labit = labCodes.listIterator(); Labit.hasNext();) {
                LabsDAO ld = (LabsDAO) Labit.next();
                String labCode = ld.getCode();
                //  getMedianValue(obj, labCode, listDiffs);
                
                
                }*/
                System.out.flush();
                returnedOut.put("JSON", JSONobjects);
                JSONobjects = new LinkedList();

            //#########################################

            //   return new ModelAndView("JSON",MAP_KEY, returnedOut);

            //    wp4SpreadsheetController wp4ss = new wp4SpreadsheetController();
            //  wp4ss.buildExcelDocument(returnedOut, arg1, request, arg3);
            //##########################################

            } else if (request.getParameter("view").equals("staticGrid")) {
                sql = "";
                String tableString = "";
                List listLabs = lm.getArchivesByCode();

                /*  sql = "SELECT COUNT(s.id_str),l.code,s.str_status ";
                fromClause = "FROM strains s,archive a,laboratories l ";
                whereClause = " WHERE s.archive_id=a.id " +
                
                "AND l.id_labo=a.lab_id_labo " +
                "AND s.str_status NOT IN ('RJCTD','TNA')";*/
                String selectClause = "SELECT COUNT(s.id_str),l.code,s.str_status ";
                fromClause = "FROM strains s,archive a,laboratories l ";
                whereClause = " WHERE s.archive_id=a.id " +
                        "AND l.id_labo=a.lab_id_labo " +
                        "AND s.str_status NOT IN ('RJCTD','TNA')";

                whereClause = (new StringBuilder()).append(whereClause).append(" AND l.code = ").toString();
                
                                    //STATIC TABLE INSTEAD OF JQGRID WHICH IS CAUSING ISSUES WHEN USING 2 GRIDS ON ONE PAGE
                    tableString = "<div style=\"width: 350px;\" dir=\"ltr\" id=\"gbox_list2\" class=\"ui-jqgrid ui-widget ui-widget-content ui-corner-all\">" +
                            "<div class=\"ui-widget-overlay jqgrid-overlay\" id=\"lui_list2\"></div>" +
                            
                            "<div style=\"width: 350px;\" id=\"gview_list2\" class=\"ui-jqgrid-view\"><div class=\"ui-jqgrid-titlebar ui-widget-header ui-corner-top ui-helper-clearfix\">" +
                            "<a style=\"right: 0px;\" class=\"ui-jqgrid-titlebar-close HeaderButton\" role=\"link\" href=\"javascript:void(0)\">" +
                            "<span class=\"ui-icon ui-icon-circle-triangle-n\"></span></a><span class=\"ui-jqgrid-title\">Number of Strains</span></div>" +
                            "<table  class=\"ui-jqgrid-htable\"  width=\"100%\" cellpadding=\"2\">";
                    tableString = (new StringBuilder()).append(tableString).append("<tbody><tr class=\"ui-jqgrid-labels\">" +
                            "<th class=\"ui-state-default ui-th-column ui-th-ltr\" width=\"25%\">Centre</th>" +
                            "<th class=\"ui-state-default ui-th-column ui-th-ltr\">Accepted</th>" +
                            "<th class=\"ui-state-default ui-th-column ui-th-ltr\">Arrived</th>" +
                            "<th class=\"ui-state-default ui-th-column ui-th-ltr\">Archiving</th>" +
                            "<th class=\"ui-state-default ui-th-column ui-th-ltr\">Archived</th>" +
                            "</tr>").toString();
                                tableString = (new StringBuilder()).append(tableString).append("<tr class=\"ui-widget-content jqgrow ui-row-ltr\">").toString();            
                for (Iterator labsIt = listLabs.listIterator(); labsIt.hasNext();) {

                    ld = (LabsDAO) labsIt.next();
                    // whereClause  = (new StringBuilder()).append(whereClause).append("'" + ld.getCode().toString() +"',").toString();
                    String lab = "'" + ld.getCode().toString() + "'";
                    obj = new JSONObject();
                    obj.put("code", ld.getCode().toString());
                    // }
                    // whereClause = whereClause + lab + ")";
                    //whereClause = cleanClause(whereClause);
                    // whereClause = (new StringBuilder()).append(whereClause).append(")").toString();
                    // whereClause = cleanClause(whereClause);
                    sql = (new StringBuilder()).append(
                            selectClause +
                            fromClause +
                            whereClause + lab +
                            " GROUP BY l.code,s.str_status").toString();

                    System.out.println("SQL QUERY IS :: " + sql);
                    list = sm.getStrainsBySQL(sql);
                    System.out.println("About to return " + list.size());
Map listarray = new HashMap();
 listarray.put("CODE", ld.getCode().toString());//o[1].toString());//;add(1, o[1].toString());
                    ///#########    tableString = (new StringBuilder()).append(tableString).append("<tr class=\"ui-widget-content jqgrow ui-row-ltr\">").toString();
                    for (Iterator it = list.listIterator(); it.hasNext();) {
                        // obj = new JSONObject();
                        Object[] o = (Object[]) it.next();

                        //if o[1] equals  ld.getCode().toString()
                        //obj.put("code", ld.getCode().toString());

                        // obj.put("COUNT", o[0]);
                        System.out.println(o[0] + "-" + o[1] + "-" + o[2]);
                        
                        System.out.println("arraylist size=" + listarray.size() + " lab from lab string=" +  ld.getCode().toString() + " lab from object o[1]=" + o[1].toString());
                        
      //    while( ld.getCode().toString().equals(o[1].toString()))    {  
        //      System.out.println("Lab equality");
                        listarray.put("COUNT", o[0].toString());//.add(0, o[0].toString());
                      //  listarray.put("CODE", ld.getCode().toString());//o[1].toString());//;add(1, o[1].toString());
                        listarray.put("STATUS",o[2].toString());//add(2, o[2].toString());

                        if (o[2].toString().equals("ACCD")) {
                            obj.put("accepted", o[0]);


                            listarray.put("ACCD", o[0].toString());
                        } else {
                          //  listarray.put("ACCD", "0");
                            //System.out.println("listarrayval=" + listarray.get(3));
                        }
                        if (o[2].toString().equals("ARRD")) {
                            obj.put("arrived", o[0]);


                            listarray.put("ARRD", o[0].toString());

                        } else {
                          //  listarray.put("ARRD", "0");
                            //System.out.println("listarrayval=" + listarray.get(4));
                        }
                        if (o[2].toString().equals("ARING")) {
                            obj.put("archiving", o[0]);

                            listarray.put("ARING", o[0].toString());
                        } else {
                           // listarray.put("ARING", "0");
                            //System.out.println("listarrayval=" + listarray.get(5));
                        }
                        if (o[2].toString().equals("ARCHD")) {
                            obj.put("archived", o[0]);


                            listarray.put("ARCHD", o[0].toString());
                        } else {
                           // listarray.put("ARCHD", "0");
                            //System.out.println("listarrayval=" + listarray.get(6));
                        }
     }//end of list iterator
                        System.out.println("arraylist size=" + listarray.size());            //  JSONobjects.add(obj);
                                     tableString = (new StringBuilder()).append(tableString).append("<tr class=\"ui-widget-content jqgrow ui-row-ltr\">").toString();
                                            /* for (Iterator aye = listarray.listIterator(); aye.hasNext();) {
                                           String ayeo = aye.next().toString();
                                                 System.out.println("ayeo="+ayeo);
                                             }*/
                       //
                        ///start loop for lab id
                   //     labCodeLoop:
                                     
                                     String keyVals = listarray.keySet().toString();
                                     System.out.println("key vals before=" + keyVals);
                                     String[] statusValues = {"ACCD","ARRD","ARING","ARCHD"};
                                     for (int iStatusValues=0;iStatusValues < statusValues.length;iStatusValues++) {
                                         if(!keyVals.contains(statusValues[iStatusValues])){
                                              listarray.put(statusValues[iStatusValues], "0");
                                         }
                                     }
                              System.out.println("complete keyvals?=" + listarray.keySet().toString());       
System.out.println(listarray.get("CODE").toString() + " - " + listarray.get("ACCD").toString() + " - " +  listarray.get("ACCD").toString() + " - " +listarray.get("ARRD").toString() + " - " + listarray.get("ARING").toString() + " - " + listarray.get("ARCHD").toString() ) ;
                        if (listarray.get("CODE").toString().equals("0")) {
                            tableString = (new StringBuilder()).append(tableString).append("<td>&nbsp;</td>").toString();
                        } else {
                            tableString = (new StringBuilder()).append(tableString).append("<td>" + listarray.get("CODE").toString()/* jOB.get("code").toString()*/ + "</td>").toString();
                        }

                        if (listarray.get("ACCD").toString().equals("0")) {
                            tableString = (new StringBuilder()).append(tableString).append("<td>&nbsp;</td>").toString();
                        } else {
                            tableString = (new StringBuilder()).append(tableString).append("<td>" + listarray.get("ACCD").toString() /*jOB.get("accepted").toString()*/ + "</td>").toString();
                        }

                        if (listarray.get("ARRD").toString().equals("0")) {
                            tableString = (new StringBuilder()).append(tableString).append("<td>&nbsp;</td>").toString();
                        } else {
                            tableString = (new StringBuilder()).append(tableString).append("<td>" + listarray.get("ARRD").toString()/*jOB.get("arrived").toString()*/ + "</td>").toString();
                        }

                        if (listarray.get("ARING").toString().equals("0")) {
                            tableString = (new StringBuilder()).append(tableString).append("<td>&nbsp;</td>").toString();
                        } else {
                            tableString = (new StringBuilder()).append(tableString).append("<td>" + listarray.get("ARING").toString()/*jOB.get("archiving").toString()*/ + "</td>").toString();
                        }

                        if (listarray.get("ARCHD").toString().equals("0")) {
                            tableString = (new StringBuilder()).append(tableString).append("<td>&nbsp;</td>").toString();
                        } else {
                            tableString = (new StringBuilder()).append(tableString).append("<td>" + listarray.get("ARCHD").toString()/*jOB.get("archived").toString()*/ + "</td>").toString();
                        }
                      
//end loop for lab id
//}
//end of listarray loop              
              //    }//end of list iterator


                }//end of labs iterator
                     tableString = (new StringBuilder()).append(tableString).append("</tr>").toString();
                         tableString = (new StringBuilder()).append(tableString).append("</tbody>").toString();
                                        tableString = (new StringBuilder()).append(tableString).append("</table>").toString();
                    tableString = (new StringBuilder()).append(tableString).append("</div></div></div>").toString();


                    System.out.println("HTML::" + tableString);
                System.out.println(" JSONobjects size=" + JSONobjects.size());

                JSONobjects.add(obj);
                HttpSession session = request.getSession(true);

                session.setAttribute("staticHTML", tableString);
            }

            System.out.flush();
            returnedOut.put("JSONstatic", JSONobjects);

            // returnedOut.put("staticHTML", tableString);
            JSONobjects = new LinkedList();

        }/* else if (request.getParameter("view").equals("spreadsheets")) {
        
        System.out.println("SPREADSHEET VIEW CALLED");
        // this.setSuccessView("spreadsheets.emma");
        //,availabilities_strains ast,cv_availabilities cva,
        //ast.str_id_str=s.id_str AND cva.id=ast.avail_id AND
        //cva.description,
        
        fromClause = "";
        fromClause = (new StringBuilder()).append(fromClause).append(" FROM strains s, archive a,laboratories l,projects_strains ps,sources_strains ss,cv_projects cvp,cv_sources cvs,mutations m,availabilities_strains ast").toString();
        whereClause = (new StringBuilder()).append(whereClause).append(" AND ps.project_id=cvp.id AND ss.sour_id=cvs.id ").toString();
        sql = "SELECT s.id_str,s.emma_id,s.name,l.code,s.str_status,s.available_to_order,str_access,s.gp_release,a.notes," +
        "m.main_type,m.sub_type,cvp.description,cvs.description AS source,a.submitted,a.evaluated," +
        "DATEDIFF(a.evaluated , a.submitted ) AS duration_evalsub," +
        "DATEDIFF(a.received,a.evaluated) AS duration_receval," +
        "DATEDIFF(a.freezing_started , a.received ) AS duration_frzrec," +
        "DATEDIFF(a.archived,a.freezing_started ) AS duration_arcfrz," +
        "DATEDIFF(a.archived,a.submitted ) AS duration_subarc";
        
        groupClause = " GROUP BY s.id_str";
        sql = (new StringBuilder()).append(sql).append(
        joinClause +
        fromClause +
        whereClause +
        groupClause).toString();
        
        System.out.println("SQL QUERY IS :: " + sql);
        
        list = sm.getStrainsBySQL(sql);
        System.out.println("About to return " + list.size());
        //    returnedOut.put("wp4SpreadsheetQuery", list);
        System.out.println("Results added to map " + sql);
        //  return new ModelAndView(new wp4SpreadsheetController(), returnedOut);
        //return new ModelAndView("spreadsheets.emma",returnedOut);
        //return new interstitialWp4SpreadsheetController(returnedOut);
        return returnedOut;
        
        }*/

        // }
        //  }

        return returnedOut;



    //    return new ModelAndView(new wp4SpreadsheetController(), MAP_KEY,returnedOut);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String cleanClause(String clause) {
        System.out.println("CLAUSE TO CLEAN :: " + clause);
        if (clause.endsWith(",")) {
            clause = (new StringBuilder()).append(clause).append(")").toString();
        }

        if (clause.endsWith(",)")) {
            System.out.println("we should be here");
            clause = clause.replace(",)", ")");
        }


        if (clause.endsWith("(")) {
            //empty no values
            System.out.println("Deleting clause " + clause);
            clause = "";
            System.out.println("Deleted clause " + clause);
        }
        return clause;
    }    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public JSONObject getMedianValue(JSONObject obj, List listDiffs) {
        System.out.println("list diffs size is " + listDiffs.size() + " -- ");

        ArrayList<Double> data = new ArrayList<Double>();
        data = new ArrayList<Double>();
        for (Iterator it1 = listDiffs.listIterator(); it1.hasNext();) {
            Object[] o1 = (Object[]) it1.next();

            //  if (o1[0].toString().equals(labCode)) {

            if (o1[1] == null) {
                o1[1] = 0;
            }
            String iVal = o1[1].toString();
            Double d = Double.parseDouble(iVal);
//System.out.println("double d value is " + d);
            data.add(d);
        }

        if (data.size() > 0) {
            System.out.println("data.size=" + data.size());
            MedianFinder mf = new MedianFinder();
            Double medianValue = mf.find(data);
            if (medianValue > 0.0) {
                System.out.println("Median value = " + medianValue + " --- ");
                obj.put("MEDIAN", medianValue);
            }
        }

        return obj;
    }

    public String medianSQL(String sql2, String joinClause, String fromClause, String whereClause, String groupClause, String labCode, String dateDiff) {
        groupClause = "ORDER BY DIFF";
        whereClause = (new StringBuilder()).append(whereClause).append(" AND l.code='" + labCode + "' AND " + dateDiff + " IS NOT NULL ").toString();
        sql2 = (new StringBuilder()).append(sql2).append(
                joinClause +
                fromClause +
                whereClause +
                groupClause).toString();

        return sql2;
    }
    
   public double roundDecimals(double d) {
        	DecimalFormat twoDForm = new DecimalFormat("#");
		return Double.valueOf(twoDForm.format(d));
}

    public LaboratoriesManager getLm() {
        return lm;
    }

    public void setLm(LaboratoriesManager lm) {
        this.lm = lm;
    }
}
