/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.controllers;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.emmanet.model.CVProjectsDAO;
import org.emmanet.model.CVSourcesDAO;
import org.emmanet.model.StrainsDAO;
import org.emmanet.model.StrainsManager;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

/**
 *
 * @author phil
 */
public class interstitialWp4SpreadsheetController implements Controller {

    public static final String MAP_KEY = "returnedOut";
    private Map returnedOut = new HashMap();
    private JSON json = new JSON();
    private StrainsManager sm = new StrainsManager();
    private StrainsDAO sd = new StrainsDAO();
    private List list;
    private boolean eucState = false;

    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse httpServletResponse) throws Exception {

        System.out.println("interstitial controller");
//NEED TO USE COLUMN ALIAS INJECTION IN FORMAT as {TABLE.COLUMN} NOT TABLE.COLUMN

        //NEW ADDED
        String received = "received";
        String fullReport = "";
        if (request.getParameter("report").equals("f")) {
            fullReport = "a.submitted,CONCAT(m.main_type,' / ',m.sub_type),";
        }
        System.out.println("Full report is::" + fullReport);
        String sql = "";
        String fromClause = "";
        String joinClause = " LEFT JOIN sources_strains ss ON ss.str_id_str=s.id_str"
                + " LEFT JOIN rtools_strains rt ON rt.str_id_str=s.id_str"
                + " LEFT JOIN projects_strains ps ON ps.str_id_str=s.id_str"
                + " LEFT JOIN availabilities_strains ast ON ast.str_id_str=s.id_str"
                + " LEFT JOIN archive a ON s.archive_id=a.id"
                + " LEFT JOIN laboratories l ON a.lab_id_labo = l.id_labo"
                + " LEFT JOIN mutations_strains ms ON ms.str_id_str=s.id_str"
                + " LEFT JOIN mutations m ON m.id=ms.mut_id"
                + " LEFT JOIN people p ON p.id_per=s.per_id_per"
                + /* " LEFT JOIN sources_strains ss ON ss.str_id_str=s.id_str" +*/ " LEFT JOIN cv_projects cvp ON cvp.id=ps.project_id"
                + " LEFT JOIN cv_sources cvs ON cvs.id=ss.sour_id";
        String groupClause = "";
        String whereClause = "";
        String  subQuerySQL="";

        fromClause = (new StringBuilder()).append(fromClause).append(" FROM strains s").toString();
        // joinClause = "";
        whereClause = " WHERE s.str_status NOT IN ('EVAL','RJCTD','TNA') ";
            //  + "AND a.evaluated IS NOT NULL "
               // + "AND a.submitted IS NOT NULL ";
        String strain_accessInClause = "(";
        String projectinClause = "(";
        String rtoolsinClause = "(";
        String fundingInClause = "(";
        String stockInClause = "(";
        String mutInClause = "(";
        String multiMutInClause = "(";
        String modStockInClause = "";
        String modStockInClauseEnd = "";

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
                    //set cvrtools euc to 9 instead as projects won't give accurate results for eucomm strains.
                    projectinClause = (new StringBuilder()).append(projectinClause).append("3").toString();
                    // rtoolsinClause = (new StringBuilder()).append(rtoolsinClause).append("9,").toString();
                    // eucCheck = true;
                    //  received = "glt";
                    eucState = true;

                }
                if (paramName.endsWith(".eumodic")) {
                    projectinClause = (new StringBuilder()).append(projectinClause).append("4").toString();
                }
                projectinClause = (new StringBuilder()).append(projectinClause).append(",").toString();
            }

            if (paramName.startsWith("rtools")) {
                //  projectinClause = "(";
                if (paramName.endsWith(".cre")) {
                    rtoolsinClause = (new StringBuilder()).append(rtoolsinClause).append("1").toString();
                }
                if (paramName.endsWith(".loxp")) {
                    rtoolsinClause = (new StringBuilder()).append(rtoolsinClause).append("2").toString();
                }
                if (paramName.endsWith(".flp")) {
                    rtoolsinClause = (new StringBuilder()).append(rtoolsinClause).append("3").toString();
                }
                if (paramName.endsWith(".frt")) {
                    rtoolsinClause = (new StringBuilder()).append(rtoolsinClause).append("4").toString();
                }
                if (paramName.endsWith(".tet")) {
                    rtoolsinClause = (new StringBuilder()).append(rtoolsinClause).append("5").toString();
                }
                if (paramName.endsWith(".lex")) {
                    rtoolsinClause = (new StringBuilder()).append(rtoolsinClause).append("6").toString();
                }
                if (paramName.endsWith(".del")) {
                    rtoolsinClause = (new StringBuilder()).append(rtoolsinClause).append("7").toString();
                }
                if (paramName.endsWith(".euc")) {
                    /*QUERY NEEDS WORK TO REMOVE ISSUES WHEN SEARCHING BOTH PROJECTS AND RTOOLS
                    SEARCH CURRENTLY LOOKS FOR PROJECTS (VALUES 5,6,7,8) AND WHERE RTOOLS IN 9*/
                    // if (!rtoolsinClause.contains("9")) {
                    rtoolsinClause = (new StringBuilder()).append(rtoolsinClause).append("9").toString();
                    // received = "glt";
                    eucState = true;

                    // }
                }
                rtoolsinClause = (new StringBuilder()).append(rtoolsinClause).append(",").toString();

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
                    mutInClause = (new StringBuilder()).append(mutInClause).append("'TM'").toString();
                }
                if (paramName.endsWith(".transgenic")) {
                    mutInClause = (new StringBuilder()).append(mutInClause).append("'TG'").toString();
                }
                if (paramName.endsWith(".targeted")) {
                    mutInClause = (new StringBuilder()).append(mutInClause).append("'TM'").toString();
                }

                mutInClause = (new StringBuilder()).append(mutInClause).append(",").toString();
            }

            if (paramName.startsWith("multimutation")) {

                String value = request.getParameter(paramName);

                if (value.equals("1")) {
                    //DO NOTHING 
                }
                if (value.equals("2")) {
                    multiMutInClause = (new StringBuilder()).append(multiMutInClause).append("'2.0'").toString();
                    multiMutInClause = (new StringBuilder()).append(multiMutInClause).append(",").toString();
                }
            }
        } while (true);

        strain_accessInClause = cleanClause(strain_accessInClause);
        //  System.out.println("WHERE clause=" + strain_accessInClause);
        strain_accessInClause = cleanClause(strain_accessInClause);
        projectinClause = cleanClause(projectinClause);
        //System.out.println("WHERE clause=" + projectinClause);
        fundingInClause = cleanClause(fundingInClause);
        //System.out.println("WHERE clause=" + fundingInClause);
        stockInClause = cleanClause(stockInClause);
        if (request.getParameter("stock.sperm") != null && request.getParameter("stock.embryos") != null) {
            if (request.getParameter("stock.sperm").equals("on") && request.getParameter("stock.embryos").equals("on")) {
                //need to modify stockinClause to include an 'AND' statement and appropriate brackets
                    modStockInClause = stockInClause.substring(0, 4);
                    modStockInClause = (new StringBuilder()).append(modStockInClause).append(")").toString();
                    modStockInClauseEnd = stockInClause.substring(5, 9);
                    modStockInClauseEnd = (new StringBuilder()).append("(").append(modStockInClauseEnd).toString();
                    //###############################################################
                     subQuerySQL=" s.id_str= ANY "
                            + "(SELECT DISTINCT str_id_str FROM availabilities_strains,cv_availabilities "
                            + "WHERE avail_id=id AND code IN ('E') AND in_stock=1 "
                            + "AND str_id_str IN "
                            + "("
                            + "SELECT DISTINCT str_id_str "
                            + "FROM availabilities_strains,cv_availabilities "
                            + "WHERE avail_id=id AND code IN ('S') AND in_stock=1"
                            + ") "
                            //+ "ORDER BY `availabilities_strains`.`str_id_str` ASC"
                            + ")";
                    //###############################################################
            }
        }
        mutInClause = cleanClause(mutInClause);
        //System.out.println("WHERE clause=" + mutInClause);
        multiMutInClause = cleanClause(multiMutInClause);
        //System.out.println("WHERE clause=" + multiMutInClause);
        rtoolsinClause = cleanClause(rtoolsinClause);
        //use mutations table if mutations are being searched on
        if (!mutInClause.equals("") /*|| !multiMutInClause.equals("")*/) {
            // fromClause = (new StringBuilder()).append(fromClause).append(",mutations m").toString();
            // whereClause = (new StringBuilder()).append(whereClause).append(" AND s.id_str = m.str_id_str ").toString();
        }
        if (!strain_accessInClause.equals("")) {
            if (!" WHERE ".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append("s.str_access IN " + strain_accessInClause).toString();
        }
        if (!projectinClause.equals("")) {
            if (!" WHERE ".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }

            whereClause = (new StringBuilder()).append(whereClause).append("ps.project_id IN " + projectinClause).toString();

        }

        if (!rtoolsinClause.equals("")) {

            if (!" WHERE ".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
                // }
            }
            whereClause = (new StringBuilder()).append(whereClause).append("rt.rtls_id IN " + rtoolsinClause).toString();
        }

        if (!fundingInClause.equals("")) {
            if (!" WHERE ".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append("ss.sour_id IN " + fundingInClause).toString();
        }
        if (!stockInClause.equals("")) {
            if (!" WHERE ".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            if (request.getParameter("stock.sperm") != null && request.getParameter("stock.embryos") != null) {
                //whereClause = (new StringBuilder()).append(whereClause).append(" (ast.avail_id IN " + modStockInClause + "AND ast.avail_id IN " + modStockInClauseEnd + ")").toString();
                whereClause = (new StringBuilder()).append(whereClause).append(subQuerySQL).toString();
            } else {
                whereClause = (new StringBuilder()).append(whereClause).append("ast.avail_id IN " + stockInClause).toString();
            }
        }
        if (!mutInClause.equals("")) {
            if (!" WHERE ".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append("m.main_type IN " + mutInClause).toString();
            if (mutInClause.contains("TG")) {
            }
            if (request.getParameter("mutation.targetedko") != null) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
                whereClause = (new StringBuilder()).append(whereClause).append(" m.sub_type IN ('KO') ").toString();
            }
        }
        if (!multiMutInClause.equals("")) {
            if (!" WHERE ".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append("s.reporting_count IN " + multiMutInClause).toString();
        }


        sql = "SELECT DISTINCT s.id_str,s.emma_id,s.name,l.code,s.reporting_count,s.str_status,s.available_to_order,s.str_access,s.gp_release,a.notes," + fullReport
                + "ps.project_id,ss.sour_id,a.submitted,a.evaluated,a.received,a.glt,a.freezing_started,a.archived,"
                + "DATEDIFF(a.evaluated , a.submitted ) AS duration_evalsub,"
                + "DATEDIFF(a." + received + ",a.evaluated) AS duration_receval,"
                + "DATEDIFF(a.freezing_started , a." + received + " ) AS duration_frzrec,"
                + "DATEDIFF(a.archived,a.freezing_started ) AS duration_arcfrz,"
                + "DATEDIFF(a.archived,a.submitted ) AS duration_subarc,"
                + "a.submitted,p.lab_id_labo,p.surname";

        groupClause = " GROUP BY s.id_str";
        sql = (new StringBuilder()).append(sql).append(",DATEDIFF(glt,evaluated),DATEDIFF(freezing_started , glt) "
                + fromClause
                + joinClause
                + whereClause
                + groupClause).toString();

        System.out.println("SPREADSHEETS SQL QUERY IS :: " + sql);

        list = sm.getStrainsBySQL(sql);

        //the following code block is a workaround the hibernate confusion of retrieving duplicate field names using creatSQLQuery
        //alias injection doesn't seem to work for me so another 2 queries to pull text description for sources and projects.

        List submitterLab = null;
        for (Iterator it = list.listIterator(); it.hasNext();) {
            int iposition = 0;
            if (request.getParameter("report").equals("f")) {
                iposition = 0;
            } else if (request.getParameter("report").equals("r")) {
                iposition = 2;
            }
            int pid = 0;
            int sid = 0;
            Object[] obj = (Object[]) it.next();
            for (int ii = 0; ii < obj.length; ii++) {
                System.out.println("Object " + ii + "::- " + obj[ii]);
            }
            if (request.getParameter("report").equals("f")) {
                 if(obj[12] != null){
                     pid = Integer.parseInt(obj[12/*2*//*2*//*1*/].toString());
                 }else{
                    pid=0;
                }
                
                if(obj[13] != null){
                    sid = Integer.parseInt(obj[13/*3*//*3*//*2*/].toString());
                }else{
                    sid=0;
                }
                //retrieve mutations from separate query then concatenate
                //current query not retrieving all mutations for a strain with multiple ones.

                List Muts = mutations(obj[0].toString());
                String concatMuts = "";
                String submuts = "";
                for (Iterator itMuts = Muts.listIterator(); itMuts.hasNext();) {
                    submuts = "";
                    Object[] ObjMuts = (Object[]) itMuts.next();
                    String mut = ObjMuts[0].toString();
                    if (ObjMuts[1] != null) {
                        submuts = ObjMuts[1].toString();
                    } else {
                        submuts = "";
                    }
                    concatMuts = (new StringBuilder()).append(concatMuts).append(mut + "/" + submuts + ",").toString();
                    System.out.println("concatMuts = " + concatMuts);
                    System.out.println("Muts = " + Muts.size());
                }
                if (concatMuts.endsWith(",")) {
                    int muti = concatMuts.lastIndexOf(",");
                    char pos = concatMuts.charAt(muti);

                    concatMuts = concatMuts.substring(0, muti);
                }
                if (concatMuts.endsWith("/")) {
                    concatMuts = concatMuts.trim();
                    int muti = concatMuts.lastIndexOf("/");
                    char pos = concatMuts.charAt(muti);

                    concatMuts = concatMuts.substring(0, muti);
                }
                concatMuts = concatMuts.replace("/", " / ");
                concatMuts = concatMuts.replace(",", ", ");
                System.out.println("concatMutsClean = " + concatMuts);
                obj[11] = concatMuts;

            } else if (request.getParameter("report").equals("r")) {
                pid = Integer.parseInt(obj[10].toString());
                sid = Integer.parseInt(obj[11].toString());
            }
            // System.out.println("object 11=" + obj[11]);
            //System.out.println("object 12=" + obj[12]);
            CVProjectsDAO cvp = new CVProjectsDAO();
            CVSourcesDAO svp = new CVSourcesDAO();
            cvp = sm.getProjectsByPID(pid);
            svp = sm.getSourcesBySID(sid);
            if (request.getParameter("report").equals("f")) {
                
                if(sid != 0){
                obj[13/*3*/] = svp.getCode();//.getDescription();
                }else{
                    obj[13]= "";
                }
                                if(pid != 0){
               obj[12/*2*/] = cvp.getCode();//.getDescription();
                }else{
                    obj[12]= "";
                }
            } else if (request.getParameter("report").equals("r")) {
                obj[10] = cvp.getCode();//.getDescription();
                obj[11] = svp.getCode();//.getDescription();
            }
            if (request.getParameter("report").equals("f")) {
                obj[10/*24*/] = this.stockValues(obj[0].toString());
                submitterLab = this.submitterDetails(obj[26/*6*//*5*/ - iposition].toString());
            } else if (request.getParameter("report").equals("r")) {
                obj[23] = "";//this.stockValues(obj[0].toString()); NOT NEEDED IN NON-RUDUNDANT LIST
                submitterLab = this.submitterDetails(obj[26/*5*/ - iposition].toString());
            }
            System.out.println("test1" + obj[25/*5*/ - iposition]);//correct
            System.out.println("test1" + obj[21]);
            System.out.println("test2" + obj[23 - 3]);
            System.out.println("test2" + obj[23 - 1]);

            // List submitterLab = this.submitterDetails(obj[26/*5*/-iposition].toString());

            String surname = obj[26 - iposition + 1].toString();
            //System.out.println("xSURNAME IS:::" + surname);
            if (!request.getParameter("report").equals("f")) {
                //iposition=3;/////////////////////////////;
                surname = obj[25].toString();
                obj[23] = surname;
                //replace last field placeholder with empty string
                //  obj[27-iposition+1] ="";
            } else {
                //surname=obj[24].toString();
                obj[25 - iposition] = surname;
            }


            if (eucState) {
//System.out.println("STATE AND PERIOD ARE TRUE");
                if (request.getParameter("report").equals("f")) {
                    //System.out.println("FULL REPORT");
                    //System.out.println("OBJECT 21 IS:-  " + obj[21] + " AND OBJECT 28 IS:- " + obj[28]);
                    if (/*obj[21] == null &&*/ obj[28] != null ) {
                        //obj[20] =  obj[20] + "==" + obj[28];
                       // if(!obj[28].toString().startsWith("-")) 
                        obj[21] = obj[28];
                    }
                    if (/*obj[22] == null && */obj[29] != null) {
                        //obj[21] = obj[21] + "==" + obj[29];
                        obj[22] = obj[29];
                    }

                    obj[29] = "";

                    obj[28] = "";

                } else {
                    //System.out.println("OBJECT 18 IS:-  " + obj[18] + " AND OBJECT 26 IS:- " + obj[26]);
                    if (obj[19] == null && obj[27] != null) {
                        //obj[18] = obj[18] + "==" +obj[26];
                        obj[19] = obj[27];
                    }
                    if (obj[20] == null && obj[27] != null) {
                        // obj[19] =  obj[19] + "==" + obj[27];
                        obj[20] = obj[27];
                    }
                    obj[27] = "";
                    obj[26] = "";
                }
            }


            //obj[24-iposition]= surname;

            for (Iterator oIT = submitterLab.listIterator(); oIT.hasNext();) {
                Object[] labObj = (Object[]) oIT.next();
                if (!request.getParameter("report").equals("f")) {
                    // iposition=4;
                    obj[24] = labObj[0].toString();
                    obj[25] = labObj[1].toString();

                } else {
                    obj[26 - iposition] = labObj[0].toString();
                    obj[27 - iposition] = labObj[1].toString();
                }
            }
        }
        // end

        // System.out.println("About to return " + list.size());
        returnedOut.put("wp4SpreadsheetQuery", list);
        // System.out.println("Results added to map " + sql);

        return new ModelAndView(new wp4SpreadsheetController(), returnedOut);

    }

    public String cleanClause(String clause) {
        //System.out.println("CLAUSE TO CLEAN :: " + clause);
        if (clause.endsWith(",")) {
            clause = (new StringBuilder()).append(clause).append(")").toString();
        }
        if (clause.endsWith(",,)")) {
            clause = clause.replace(",,)", ",)");
        }
        if (clause.endsWith(",)")) {
           // System.out.println("we should be here");
            clause = clause.replace(",)", ")");
        }
        if (clause.endsWith("(") || clause.endsWith("()")) {
            //empty no values
            //System.out.println("Deleting clause " + clause);
            clause = "";
            //System.out.println("Deleted clause " + clause);
        }
        return clause;
    }

    public String stockValues(String str_id) {
        String stockVal = "";

        String sql = "SELECT * FROM availabilities_strains,cv_availabilities "
                + "WHERE str_id_str='" + str_id + "' AND avail_id=id AND in_stock=1";//AND in_stock=1 removed as giving innacurate result in spreadsheet not comesurate with other availabilities searches. 
        List stock = sm.getStrainsBySQL(sql);

        for (Iterator i = stock.listIterator(); i.hasNext();) {
            Object[] o = (Object[]) i.next();
            stockVal = (new StringBuilder()).append(stockVal).append(o[3/*4*/] + ",").toString();
        }
        if (stockVal.endsWith(",")) {
            int posi = stockVal.lastIndexOf(",");
            stockVal = stockVal.substring(0, posi);
        }
        return stockVal;
    }

    public List submitterDetails(String labID) {
        List results;
        String query = "SELECT name,country from laboratories WHERE id_labo=" + labID;
        results = sm.getStrainsBySQL(query);

        return results;
    }

    public List mutations(String id_str) {
        List results;
        String query = "SELECT mutations.main_type,mutations.sub_type "
                + "FROM mutations,mutations_strains "
                + "WHERE mutations_strains.str_id_str = " + id_str + " "
                + "AND mut_id=id";
        results = sm.getStrainsBySQL(query);

        return results;
    }
}
