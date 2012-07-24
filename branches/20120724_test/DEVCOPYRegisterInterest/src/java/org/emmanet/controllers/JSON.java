package org.emmanet.controllers;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.emmanet.model.StrainsManager;
import org.emmanet.model.LaboratoriesManager;
import org.emmanet.model.StrainsDAO;
import org.json.simple.*;
import org.springframework.web.servlet.mvc.SimpleFormController;
import org.emmanet.model.LabsDAO;
import org.emmanet.util.MedianFinder;

/**
 *
 * @author phil
 */
public class JSON extends SimpleFormController {

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
    private String dateDiffGlt = "";
    private boolean eucState = false;
    private boolean reportPeriod = false;
    private String manualCalcFields1="";
    private String manualCalcFields2="";

    @Override
    protected Object formBackingObject(HttpServletRequest request) {
        returnedOut = new HashMap();

        obj = new JSONObject();
        JSONobjects = new LinkedList();

        String sql = "";
        String sql2 = "";
        String fromClause = "";
        String joinClause = "LEFT JOIN sources_strains ss ON ss.str_id_str=s.id_str"
                + " LEFT JOIN rtools_strains rt ON rt.str_id_str=s.id_str"
                + " LEFT JOIN projects_strains ps ON ps.str_id_str=s.id_str"
                + " LEFT JOIN availabilities_strains ast ON ast.str_id_str=s.id_str"
                + " LEFT JOIN archive a ON s.archive_id=a.id"
                + " LEFT JOIN laboratories l ON a.lab_id_labo = l.id_labo"
                + " LEFT JOIN mutations_strains ms ON ms.str_id_str=s.id_str"
                + " LEFT JOIN mutations m ON m.id=ms.mut_id";
        String groupClause = "";
        String whereClause = "";
        String whereClauseDates = "";
        String orClause = "";
        String received = "received";
        String multiMutWhereClause = "";

        fromClause = " FROM strains s ";
        whereClause = " WHERE s.str_status NOT IN ('EVAL','RJCTD','TNA')";

        String strain_accessInClause = "(";
        String projectinClause = "(";
        String rtoolsinClause = "(";
        String fundingInClause = "(";
        String stockInClause = "(";
        String mutInClause = "(";
        String multiMutInClause = "(";
        String modStockInClause = "";
        String modStockInClauseEnd = "";
        String subQuerySQL = "";

        String selectFromSub = "SELECT COUNT(sub.id_str) AS COUNT,sub.code,";
        String subSelectFields = "";

        boolean eucCheck = true;

        Map listarray = new HashMap();
        List listcount;

        //we are searching lets grab parameter values

        Enumeration param = request.getParameterNames();
        do {
            if (!param.hasMoreElements()) {
                break;
            }
            String paramName = (String) param.nextElement();
            if (paramName.startsWith("strainaccess")) {
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
                    // mutInClause = (new StringBuilder()).append(mutInClause).append(",").toString();
                }

                mutInClause = (new StringBuilder()).append(mutInClause).append(",").toString();
            }

            if (paramName.startsWith("multimutation")) {

                String value = request.getParameter(paramName);

                if (value.equals("1")) {
                    //request.getParameter("multimutaion").
                    // multiMutInClause = (new StringBuilder()).append(multiMutInClause).append("'1.0'").toString();
                    //DO NOTHING
                    //mutInClause = (new StringBuilder()).append(mutInClause).append(",").toString();
                }
                if (value.equals("2")) {
                    multiMutInClause = (new StringBuilder()).append(multiMutInClause).append("'2.0'").toString();
                    multiMutInClause = (new StringBuilder()).append(multiMutInClause).append(",").toString();
                }
                // multiMutInClause = (new StringBuilder()).append(multiMutInClause).append(",").toString();
            }
        } while (true);

        strain_accessInClause = cleanClause(strain_accessInClause);
        strain_accessInClause = cleanClause(strain_accessInClause);
        projectinClause = cleanClause(projectinClause);

        rtoolsinClause = cleanClause(rtoolsinClause);
        if (eucCheck) {
            //rtoolsinClause = (new StringBuilder()).append(rtoolsinClause).append(")").toString();
            fundingInClause = cleanClause(fundingInClause);
            stockInClause = cleanClause(stockInClause);
            if (request.getParameter("stock.sperm") != null && request.getParameter("stock.embryos") != null) {
                if (request.getParameter("stock.sperm").equals("on") && request.getParameter("stock.embryos").equals("on")) {
                    //need to modify stockinClause to include an 'AND' statement and appropriate brackets

                    modStockInClause = stockInClause.substring(0, 4);
                    modStockInClause = (new StringBuilder()).append(modStockInClause).append(")").toString();
                    modStockInClauseEnd = stockInClause.substring(5, 9);
                    modStockInClauseEnd = (new StringBuilder()).append("(").append(modStockInClauseEnd).toString();

                    //################################### NEW CODE TO RESOLVE QUERY ISSUES

                    subQuerySQL = " s.id_str= ANY "
                            + "(SELECT DISTINCT str_id_str FROM availabilities_strains,cv_availabilities "
                            + "WHERE avail_id=id AND code IN ('E') AND in_stock=1 "
                            + "AND str_id_str IN "
                            + "("
                            + "SELECT DISTINCT str_id_str "
                            + "FROM availabilities_strains,cv_availabilities "
                            + "WHERE avail_id=id AND code IN ('S') AND in_stock=1"
                            + ") "
                            + ")";

                    //###################################END NEW CODE TO RESOLVE QUERY ISSUES

                }
            }

            mutInClause = cleanClause(mutInClause);
            multiMutInClause = cleanClause(multiMutInClause);
            if (!request.getParameter("view").startsWith("spreadsheet")) {
                sql = "SELECT "
                        + "COUNT(distinct s.id_str) AS COUNT,"
                        + "l.code";
                sql2 = "SELECT l.code,";

                // System.out.println("reportingperiod=" + request.getParameter("reportingperiod"));
                if (request.getParameter("reportingperiod") != null) {
                    sql = (new StringBuilder()).append(sql).append(",").toString();
                    if (request.getParameter("reportingperiod").equals("subeval")) {
                        sql = (new StringBuilder()).append(sql).append(
                                "SUM(CASE WHEN DATEDIFF(evaluated , submitted ) < 0 THEN NULL ELSE DATEDIFF(evaluated , submitted ) END) AS SUM, "
                                + "AVG(CASE WHEN DATEDIFF(evaluated , submitted ) < 0 THEN NULL ELSE DATEDIFF(evaluated , submitted ) END) AS AVG, "
                                + "MAX(CASE WHEN DATEDIFF(evaluated , submitted ) < 0 THEN NULL ELSE DATEDIFF(evaluated , submitted ) END) AS MAX,"
                                + "MIN(CASE WHEN DATEDIFF(evaluated , submitted ) < 0 THEN NULL ELSE DATEDIFF(evaluated , submitted ) END) AS MIN ").toString();


                        selectFromSub = (new StringBuilder()).append(selectFromSub).append(
                                "SUM(CASE WHEN DATEDIFF(sub.evaluated , sub.submitted ) < 0 THEN NULL ELSE DATEDIFF(sub.evaluated , sub.submitted ) END) AS SUM, "
                                + "AVG(CASE WHEN DATEDIFF(sub.evaluated , sub.submitted ) < 0 THEN NULL ELSE DATEDIFF(sub.evaluated , sub.submitted ) END) AS AVG, "
                                + "MAX(CASE WHEN DATEDIFF(sub.evaluated , sub.submitted ) < 0 THEN NULL ELSE DATEDIFF(sub.evaluated , sub.submitted ) END) AS MAX,"
                                + "MIN(CASE WHEN DATEDIFF(sub.evaluated , sub.submitted ) < 0 THEN NULL ELSE DATEDIFF(sub.evaluated , sub.submitted ) END) AS MIN ").toString();

                        subSelectFields = "a.evaluated , a.submitted";

                        dateDiff = "CASE WHEN DATEDIFF(evaluated , submitted ) < 0 THEN NULL ELSE DATEDIFF(evaluated , submitted ) END";
                        sql2 = (new StringBuilder().append(sql2).append(dateDiff + " AS DIFF")).toString();
                        whereClauseDates = " AND (a.evaluated IS NOT NULL AND a.submitted IS NOT NULL) AND (a.submitted <= a.evaluated)";//OR REMOVED 07102011
                        reportPeriod = false;

                    } else if (request.getParameter("reportingperiod").equals("evalarr")) {
                        sql = (new StringBuilder()).append(sql).append(
                                "SUM(CASE WHEN DATEDIFF(" + received + ",evaluated) < 0 THEN NULL ELSE DATEDIFF(" + received + ",evaluated)  END ) AS SUM, "
                                + "AVG(CASE WHEN DATEDIFF(" + received + ",evaluated) < 0 THEN NULL ELSE DATEDIFF(" + received + ",evaluated) END ) AS AVG, "
                                + "MAX(CASE WHEN DATEDIFF(" + received + ",evaluated) < 0 THEN NULL ELSE DATEDIFF(" + received + ",evaluated) END ) AS MAX,"
                                + "MIN(CASE WHEN DATEDIFF(" + received + ",evaluated) < 0 THEN NULL ELSE DATEDIFF(" + received + ",evaluated) END ) AS MIN,"
                                + "SUM(CASE WHEN DATEDIFF(sub.glt,sub.evaluated) < 0 THEN NULL ELSE DATEDIFF(sub.glt,sub.evaluated) END ) AS SUMGLT, "
                                + "AVG(CASE WHEN DATEDIFF(sub.glt,sub.evaluated) < 0 THEN NULL ELSE DATEDIFF(sub.glt,sub.evaluated) END ) AS AVGGLT, "
                                + "MAX(CASE WHEN DATEDIFF(sub.glt,sub.evaluated) < 0 THEN NULL ELSE DATEDIFF(sub.glt,sub.evaluated) END ) AS MAXGLT,"
                                + "MIN(CASE WHEN DATEDIFF(sub.glt,sub.evaluated) < 0 THEN NULL ELSE DATEDIFF(sub.glt,sub.evaluated) END) AS MINGLT").toString();



                        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////               

                        selectFromSub = (new StringBuilder()).append(selectFromSub).append(
                                "SUM(CASE WHEN DATEDIFF(sub." + received + ",sub.evaluated ) < 0 THEN NULL ELSE DATEDIFF(sub." + received + ",sub.evaluated  ) END) AS SUM, "
                                + "AVG(CASE WHEN DATEDIFF(sub." + received + ",sub.evaluated  ) < 0 THEN NULL ELSE DATEDIFF(sub." + received + ",sub.evaluated  ) END) AS AVG, "
                                + "MAX(CASE WHEN DATEDIFF(sub." + received + ",sub.evaluated) < 0 THEN NULL ELSE DATEDIFF(sub." + received + ",sub.evaluated ) END) AS MAX,"
                                + "MIN(CASE WHEN DATEDIFF(sub." + received + ",sub.evaluated ) < 0 THEN NULL ELSE DATEDIFF(sub." + received + ",sub.evaluated ) END) AS MIN, "
                                + "SUM(CASE WHEN DATEDIFF(sub.glt,sub.evaluated) < 0 THEN NULL ELSE DATEDIFF(sub." + received + ",sub.evaluated) END) AS SUMGLT, "
                                + "AVG(CASE WHEN DATEDIFF(sub.glt,sub.evaluated) < 0 THEN NULL ELSE DATEDIFF(sub." + received + ",sub.evaluated) END) AS AVGGLT, "
                                + "MAX(CASE WHEN DATEDIFF(sub.glt,sub.evaluated) < 0 THEN NULL ELSE DATEDIFF(sub." + received + ",sub.evaluated) END) AS MAXGLT,"
                                + "MIN(CASE WHEN DATEDIFF(sub.glt,sub.evaluated ) < 0 THEN NULL ELSE DATEDIFF(sub." + received + ",sub.evaluated) END) AS MINGLT ").toString();

                        subSelectFields = "a." + received + ",a.evaluated,a.glt";
                        manualCalcFields1="glt,evaluated";
manualCalcFields2="received,evaluated";

                        //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////// 




                        dateDiff = "CASE WHEN DATEDIFF(" + received + ",evaluated) < 0 THEN NULL ELSE DATEDIFF(" + received + ",evaluated)  END";
                        dateDiffGlt = "CASE WHEN DATEDIFF(glt,evaluated) < 0 THEN NULL ELSE DATEDIFF(glt,evaluated)  END";
                        sql2 = (new StringBuilder().append(sql2).append(dateDiff + " AS DIFF, " + dateDiffGlt + " AS DIFFGLT")).toString();
                        reportPeriod = true;
                        whereClauseDates = " AND (a.evaluated IS NOT NULL AND (a." + received + " IS NOT NULL OR glt IS NOT NULL))";//FIRST OR REMOVED AFTER A.EVALUATED
                    } else if (request.getParameter("reportingperiod").equals("arrfrz")) {
                        sql = (new StringBuilder()).append(sql).append(
                                "SUM(CASE WHEN DATEDIFF(freezing_started , " + received + ") < 0 THEN NULL ELSE DATEDIFF(freezing_started , " + received + ") END) AS SUM, "
                                + "AVG(CASE WHEN DATEDIFF(freezing_started , " + received + " ) < 0 THEN NULL ELSE DATEDIFF(freezing_started , " + received + ") END) AS AVG, "
                                + "MAX(CASE WHEN DATEDIFF(freezing_started ," + received + " ) < 0 THEN NULL ELSE DATEDIFF(freezing_started , " + received + ") END) AS MAX,"
                                + "MIN(CASE WHEN DATEDIFF(freezing_started , " + received + " ) < 0 THEN NULL ELSE DATEDIFF(freezing_started , " + received + ") END) AS MIN,"
                                + "SUM(CASE WHEN DATEDIFF(freezing_started , glt) < 0 THEN NULL ELSE DATEDIFF(freezing_started , " + received + ") END) AS SUM, "
                                + "AVG(CASE WHEN DATEDIFF(freezing_started , glt ) < 0 THEN NULL ELSE DATEDIFF(freezing_started , " + received + ") END) AS AVG, "
                                + "MAX(CASE WHEN DATEDIFF(freezing_started ,glt ) < 0 THEN NULL ELSE DATEDIFF(freezing_started , " + received + ") END) AS MAX,"
                                + "MIN(CASE WHEN DATEDIFF(freezing_started , glt ) < 0 THEN NULL ELSE DATEDIFF(freezing_started , " + received + ") END) AS MIN ").toString();

                        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////               

                        selectFromSub = (new StringBuilder()).append(selectFromSub).append(
                                "SUM(CASE WHEN DATEDIFF(sub.freezing_started , sub." + received + " ) < 0 THEN NULL ELSE DATEDIFF(sub.freezing_started , sub." + received + "  ) END) AS SUM, "
                                + "AVG(CASE WHEN DATEDIFF(sub.freezing_started , sub." + received + "  ) < 0 THEN NULL ELSE DATEDIFF(sub.freezing_started , sub." + received + "  ) END) AS AVG, "
                                + "MAX(CASE WHEN DATEDIFF(sub.freezing_started , sub." + received + " ) < 0 THEN NULL ELSE DATEDIFF(sub.freezing_started , sub." + received + " ) END) AS MAX,"
                                + "MIN(CASE WHEN DATEDIFF(sub.freezing_started , sub." + received + "  ) < 0 THEN NULL ELSE DATEDIFF(sub.freezing_started , sub." + received + " ) END) AS MIN, "
                                + "SUM(CASE WHEN DATEDIFF(sub.freezing_started , sub.glt) < 0 THEN NULL ELSE DATEDIFF(sub.freezing_started , " + received + ") END) AS SUMGLT, "
                                + "AVG(CASE WHEN DATEDIFF(sub.freezing_started , sub.glt ) < 0 THEN NULL ELSE DATEDIFF(sub.freezing_started , " + received + ") END) AS AVGGLT, "
                                + "MAX(CASE WHEN DATEDIFF(sub.freezing_started ,sub.glt ) < 0 THEN NULL ELSE DATEDIFF(sub.freezing_started , " + received + ") END) AS MAXGLT,"
                                + "MIN(CASE WHEN DATEDIFF(sub.freezing_started , sub.glt ) < 0 THEN NULL ELSE DATEDIFF(sub.freezing_started , " + received + ") END) AS MINGLT ").toString();

                        subSelectFields = "a.glt,a.freezing_started ,a." + received;
                        manualCalcFields1="freezing_started , glt";
manualCalcFields2="freezing_started , received";
                        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////              


                        dateDiff = "CASE WHEN DATEDIFF(freezing_started ," + received + ") < 0 THEN NULL ELSE DATEDIFF(freezing_started , " + received + ") END";
                        dateDiffGlt = "CASE WHEN DATEDIFF(freezing_started ,glt) < 0 THEN NULL ELSE DATEDIFF(freezing_started , glt) END";
                        sql2 = (new StringBuilder().append(sql2).append(dateDiff + " AS DIFF," + dateDiffGlt + " AS DIFFGLT")).toString();
                        reportPeriod = true;
                        whereClauseDates = " AND (a.freezing_started IS NOT NULL AND (a." + received + " IS NOT NULL OR glt IS NOT NULL))";//FIRST OR REMOVED AFTER A.FREEZING
                    } else if (request.getParameter("reportingperiod").equals("frzarch")) {
                        sql = (new StringBuilder()).append(sql).append(
                                "SUM(CASE WHEN DATEDIFF(archived,freezing_started ) < 0 THEN NULL ELSE DATEDIFF(archived,freezing_started ) END) AS SUM, "
                                + "AVG(CASE WHEN DATEDIFF(archived,freezing_started) < 0 THEN NULL ELSE DATEDIFF(archived,freezing_started ) END) AS AVG, "
                                + "MAX(CASE WHEN DATEDIFF(archived,freezing_started) < 0 THEN NULL ELSE DATEDIFF(archived,freezing_started ) END) AS MAX,"
                                + "MIN(CASE WHEN DATEDIFF(archived,freezing_started ) < 0 THEN NULL ELSE DATEDIFF(archived,freezing_started ) END) AS MIN ").toString();



                        selectFromSub = (new StringBuilder()).append(selectFromSub).append(
                                "SUM(CASE WHEN DATEDIFF(sub.archived , sub.freezing_started ) < 0 THEN NULL ELSE DATEDIFF(sub.archived , sub.freezing_started ) END) AS SUM, "
                                + "AVG(CASE WHEN DATEDIFF(sub.archived , sub.freezing_started ) < 0 THEN NULL ELSE DATEDIFF(sub.archived , sub.freezing_started ) END) AS AVG, "
                                + "MAX(CASE WHEN DATEDIFF(sub.archived , sub.freezing_started ) < 0 THEN NULL ELSE DATEDIFF(sub.archived , sub.freezing_started ) END) AS MAX,"
                                + "MIN(CASE WHEN DATEDIFF(sub.archived , sub.freezing_started ) < 0 THEN NULL ELSE DATEDIFF(sub.archived , sub.freezing_started ) END) AS MIN ").toString();

                        subSelectFields = "a.archived , a.freezing_started";


                        dateDiff = "CASE WHEN DATEDIFF(archived,freezing_started ) < 0 THEN NULL ELSE DATEDIFF(archived,freezing_started ) END";
                        sql2 = (new StringBuilder().append(sql2).append(dateDiff + " AS DIFF")).toString();
                        whereClauseDates = " AND (a.archived IS NOT NULL AND a.freezing_started IS NOT NULL)  AND (a.freezing_started <= a.archived)";//OR REMOVED 07102011
                        reportPeriod = false;
                    } else if (request.getParameter("reportingperiod").equals("subarch")) {
                        sql = (new StringBuilder()).append(sql).append(
                                "SUM(CASE WHEN DATEDIFF(archived,submitted ) < 0 THEN NULL ELSE DATEDIFF(archived,submitted ) END) AS SUM, "
                                + "AVG(CASE WHEN DATEDIFF(archived,submitted) < 0 THEN NULL ELSE DATEDIFF(archived,submitted ) END) AS AVG, "
                                + "MAX(CASE WHEN DATEDIFF(archived,submitted) < 0 THEN NULL ELSE DATEDIFF(archived,submitted ) END) AS MAX,"
                                + "MIN(CASE WHEN DATEDIFF(archived,submitted) < 0 THEN NULL ELSE DATEDIFF(archived,submitted ) END) AS MIN ").toString();
                        dateDiff = "CASE WHEN DATEDIFF(archived,submitted) < 0 THEN NULL ELSE DATEDIFF(archived,submitted ) END";


                        selectFromSub = (new StringBuilder()).append(selectFromSub).append(
                                "SUM(CASE WHEN DATEDIFF(sub.archived , sub.submitted ) < 0 THEN NULL ELSE DATEDIFF(sub.archived , sub.submitted ) END) AS SUM, "
                                + "AVG(CASE WHEN DATEDIFF(sub.archived , sub.submitted ) < 0 THEN NULL ELSE DATEDIFF(sub.archived , sub.submitted ) END) AS AVG, "
                                + "MAX(CASE WHEN DATEDIFF(sub.archived , sub.submitted ) < 0 THEN NULL ELSE DATEDIFF(sub.archived , sub.submitted ) END) AS MAX,"
                                + "MIN(CASE WHEN DATEDIFF(sub.archived , sub.submitted ) < 0 THEN NULL ELSE DATEDIFF(sub.archived , sub.submitted ) END) AS MIN ").toString();

                        subSelectFields = "a.archived , a.submitted";


                        sql2 = (new StringBuilder().append(sql2).append(dateDiff + " AS DIFF")).toString();
                        whereClauseDates = " AND (a.archived IS NOT NULL AND a.submitted IS NOT NULL)  AND (a.submitted <= a.archived)";//OR REMOVED 07102011
                        reportPeriod = false;
                    }
                }
            }
            //use mutations table if mutations are being searched on
            if (!mutInClause.equals("")) {
                //do nothing
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
                    //add modded where here
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
        }
        if (request.getParameter("view").equals("grid")) {
            groupClause = " GROUP BY l.code";

            sql = (new StringBuilder()).append(sql).append(
                    fromClause
                    + joinClause
                    + whereClause
                    + groupClause).toString();
            System.out.println(" TOTAL COUNT SQL QUERY IS :: " + sql);


            String subSelect = "( SELECT DISTINCT s.id_str," + subSelectFields + ", l.code";

            subSelect = (new StringBuilder()).append(subSelect).append(
                    fromClause
                    + joinClause
                    + whereClause
                    + " ) AS sub GROUP BY sub.code").toString();

            System.out.println("selectfromsub IS::" + selectFromSub);
            selectFromSub = (new StringBuilder()).append(selectFromSub).append(
                    " FROM "
                    + subSelect).toString();

            System.out.println("FINAL SUBSELECT IS::" + selectFromSub);
            
            list = sm.getStrainsBySQL(selectFromSub);

        }

        if (request.getParameter("view").equals("grid")) {
            BigDecimal bdo0 = new BigDecimal("0.0");
            BigDecimal actCountbdo0 = new BigDecimal("0.0");
            BigDecimal bdo2 = new BigDecimal("0.0");
            BigDecimal bdo3 = new BigDecimal("0.0");
            BigDecimal bdo4 = new BigDecimal("0.0");
            BigDecimal bdo5 = new BigDecimal("0.0");
            ArrayList<Double> medianData = new ArrayList<Double>();

            try {
                BufferedWriter out = new BufferedWriter(new FileWriter("/nfs/panda/emma/tmp/phil.txt", false));

                for (Iterator it = list.listIterator(); it.hasNext();) {
                    int i = 0;
                    obj = new JSONObject();

                    Object[] o = (Object[]) it.next();
                    /* CODE BELOW TO CHECK WHETHER GLT COUNTS CAN BE USED INSTEAD OF ARRIVED COUNTS WHEN EUCOMM PROJECT OR .EUC RTOOL */

                    if (eucState && reportPeriod) {
                        System.out.println("eucState and reportPeriod are true");
                        if (o[6] != null) {
                            //System.out.println("o[6] is being used");
                            o[2] = o[6];
                        }
                        if (o[7] != null) {
                            //System.out.println("o[7] is being used");
                            o[3] = o[7];
                        }
                        if (o[8] != null) {
                            //System.out.println("o[8] is being used");
                            o[4] = o[8];
                        }
                        if (o[9] != null) {
                            //System.out.println("o[9] is being used");
                            o[5] = o[9];
                        }
                    }
                    obj.put("COUNT", o[0]);

                    obj.put("ACTUALCOUNT", actualCount(joinClause, fromClause, whereClause, whereClauseDates, o[1].toString()));

                    actCountbdo0 = actCountbdo0.add(new BigDecimal("" + obj.get("ACTUALCOUNT")));

                    //prevent negative values being included in total count
                /*
                     * 
                     * 
                     */
                    if (!o[0].toString().startsWith("-")) {
                        bdo0 = bdo0.add(new BigDecimal("" + o[0]));
                    }
                    // bdo0 = bdo0.add(new BigDecimal("" + o[0]));
                /*
                     * 
                     * 
                     */
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
                    if (dee_o2 >= 0.0) {
                        obj.put("SUM", roundDecimals(dee_o2));
                        out.write(obj.get("SUM") + " \\ " + obj.get("ACTUALCOUNT") + "\n");
                    }
                    //=
                    if (dee_o3 >= 0.0) {
                        bdo3 = bdo3.add(new BigDecimal("" + roundDecimals(dee_o3)));
                        obj.put("AVG", roundDecimals(dee_o3));
                    }
                    if (dee_o4 >= 0.0) {
                        bdo4 = bdo4.add(new BigDecimal("" + roundDecimals(dee_o4)));
                        obj.put("MAX", roundDecimals(dee_o4));
                    }
                    if (dee_o5 >= 0.0) {
                        bdo5 = bdo5.add(new BigDecimal("" + roundDecimals(dee_o5)));
                        obj.put("MIN", roundDecimals(dee_o5));
                    }
                    String mediansql = this.medianSQL(sql2, joinClause, fromClause, whereClause, groupClause, o[1].toString(), dateDiff, eucState, reportPeriod);
                    System.out.println("MEDIANSQL IS :: " + mediansql);
                    List listDiffs = sm.getStrainsBySQL(mediansql);

                    getMedianValue(obj, listDiffs, eucState, reportPeriod);
                    //MEDIAN NO ZEROS OR NEG VALUES
                    if (obj.get("MEDIAN") == null || obj.get("MEDIAN").toString().startsWith("-")) {
                        //medianData.add(i, (Double) 0.0);
                    } else {
                        medianData.add(i, (Double) obj.get("MEDIAN"));
                    }
                    //System.out.println("MEDIAN VALUE IS::-" + obj.get("MEDIAN"));
                    i++;
                    JSONobjects.add(obj);
                     if (eucState && reportPeriod) {
                         JSONobjects.clear();
                         JSONobjects.add(this.manualMetrics(joinClause, fromClause, whereClause, manualCalcFields1, manualCalcFields2));
                     }
                    
                }
                out.close();
            } catch (IOException e) {
                e.printStackTrace();

            }
//get median of medians

            JSONObject obj4 = new JSONObject();
            obj4.put("code", "~~ TOTAL ~~");
            obj4.put("COUNT", bdo0);
            obj4.put("ACTUALCOUNT", actCountbdo0);
            //redefine bdo 3,4,5 to results of new search to get total values from database

            String sqlModify = /*sql*/ selectFromSub.replace("COUNT(sub.id_str) AS COUNT,sub.code,", "");

            sqlModify = sqlModify.replace(" GROUP BY sub.code", "");

            System.out.println("MODDED SQL STATEMENT IS::" + sqlModify);
            List listTotals = sm.getStrainsBySQL(sqlModify);
            for (Iterator it = listTotals.listIterator(); it.hasNext();) {
                Object[] O = (Object[]) it.next();
                if (O[1] != null || O[2] != null || O[3] != null) {
                    Double dee_o3 = Double.parseDouble(O[1].toString());
                    bdo3 = new BigDecimal("" + roundDecimals(dee_o3));//AVG  roundDecimals(dee_o3)
                    bdo4 = new BigDecimal(Double.parseDouble(O[2].toString()));//MAX
                    bdo5 = new BigDecimal(Double.parseDouble(O[3].toString()));//MIN
                }
            }

            if (bdo3 != null) {
                obj4.put("AVG", bdo3);
            }
            obj4.put("MAX", bdo4);
            obj4.put("MIN", bdo5);

            //Adds MEDIAN TOTALS
            MedianFinder mf = new MedianFinder();
            //line below calls error
            Double medianValue = mf.find(medianData);

            String totalmediansql = this.medianSQL(sql2, joinClause, fromClause, whereClause, groupClause, "NO", dateDiff, eucState, reportPeriod);
            System.out.println("TOTALMEDIANSQL IS :: " + totalmediansql);

            //GET SQL2 QUERY
            ArrayList<Double> totalmedianData = new ArrayList<Double>();
            List TOTALMEDIANVALUES = sm.getStrainsBySQL(totalmediansql);
            for (Iterator itot = TOTALMEDIANVALUES.listIterator(); itot.hasNext();) {
                Object[] toto = (Object[]) itot.next();
                Double doubletoto = 0.0;
                if (toto[2] != null) {
                    doubletoto = Double.parseDouble(toto[2].toString());
                } else {
                    doubletoto = Double.parseDouble(toto[3].toString());
                }


                totalmedianData.add(doubletoto);
            }
            System.out.println("TOTAL ARRAYLIST SIZE IS::-" + totalmedianData.size());
            MedianFinder totMF = new MedianFinder();
            medianValue = mf.find(totalmedianData);
            /*
             * 
             * STOP NULL VALUES BEING INCLUDED IN TOTAL MEDIAN CALC
             */
            if (medianValue != null || medianValue >= 0.0) {
                //System.out.println("Median value = " + medianValue + " --- ");
                obj4.put("MEDIAN", medianValue);

            }
            
                                 if (eucState && reportPeriod) {
                         //DO NOTHING TOTALS ALREADY PART OF JSONOBJECTS
                     }else {
                                     JSONobjects.add(obj4);
                                     
                                 }
            
            
            returnedOut.put("JSON", JSONobjects);


            sql = "";
            List listLabs = lm.getArchivesByCode();
            JSONObject obj2 = new JSONObject();
            List JSONobjects2 = new LinkedList();
            String selectClause = "SELECT COUNT(distinct s.id_str),l.code,s.str_status ";
            for (Iterator labsIt = listLabs.listIterator(); labsIt.hasNext();) {
                ld = (LabsDAO) labsIt.next();
                String lab = "'" + ld.getCode().toString() + "'";

                String multiMutSql = "";
                List multiMutList;
                Map multiMut = new HashMap();

                if (multiMutInClause.contains("2.0")) {
                    multiMutWhereClause = whereClause.replace("AND s.reporting_count IN ('2.0')", "");
                }

                sql = (new StringBuilder()).append(
                        selectClause
                        + fromClause
                        + joinClause
                        + whereClause + " AND l.code = " + lab
                        + " GROUP BY l.code,s.str_status").toString();
                if (multiMutInClause.contains("2.0")) {

                    sql = (new StringBuilder()).append(
                            selectClause
                            + fromClause
                            + joinClause
                            + multiMutWhereClause + " AND l.code = " + lab
                            + " GROUP BY l.code,s.str_status").toString();

                    multiMutSql = (new StringBuilder()).append(
                            selectClause
                            + fromClause
                            + joinClause
                            + whereClause + " AND l.code = " + lab
                            + " GROUP BY l.code,s.str_status").toString();

                }

                System.out.println("LEFT GRID SQL QUERY IS :: " + sql);

                list = sm.getStrainsBySQL(sql);

                if (multiMutInClause.contains("2.0")) {
                    multiMutList = sm.getStrainsBySQL(multiMutSql);
                    multiMut = multiMutCounts(multiMutList);
                }

                //System.out.println("About to return " + list.size());
                listarray.put("CODE", ld.getCode().toString());
                obj2 = new JSONObject();
                obj2.put("code", ld.getCode().toString());
                Integer iCount = 0;

                for (Iterator it = list.listIterator(); it.hasNext();) {
                    Object[] o = (Object[]) it.next();
                    listarray.put("COUNT", o[0].toString());
                    listarray.put("STATUS", o[2].toString());
                    if (o[2].toString().equals("ACCD")) {

                        if (multiMutInClause.contains("2.0")) {

                            Integer iAdd = Integer.parseInt(o[0].toString());
                            Integer iAddTo = Integer.parseInt(multiMut.get("accepted").toString());
                            o[0] = iAdd + iAddTo;
                        }
                        obj2.put("accepted", o[0]);
                        listarray.put("ACCD", o[0].toString());
                    } else {
                    }
                    if (o[2].toString().equals("ARRD")) {
                        if (multiMutInClause.contains("2.0")) {
                            Integer iAdd = Integer.parseInt(o[0].toString());
                            Integer iAddTo = Integer.parseInt(multiMut.get("arrived").toString());
                            o[0] = iAdd + iAddTo;
                        }
                        obj2.put("arrived", o[0]);
                        listarray.put("ARRD", o[0].toString());
                    } else {
                    }
                    if (o[2].toString().equals("ARING")) {
                        if (multiMutInClause.contains("2.0")) {
                            Integer iAdd = Integer.parseInt(o[0].toString());
                            Integer iAddTo = Integer.parseInt(multiMut.get("archiving").toString());
                            o[0] = iAdd + iAddTo;
                        }
                        obj2.put("archiving", o[0]);
                        listarray.put("ARING", o[0].toString());
                    } else {
                    }
                    if (o[2].toString().equals("ARCHD")) {
                        if (multiMutInClause.contains("2.0")) {
                            Integer iAdd = Integer.parseInt(o[0].toString());
                            Integer iAddTo = Integer.parseInt(multiMut.get("archived").toString());
                            o[0] = iAdd + iAddTo;
                        }
                        obj2.put("archived", o[0]);
                        listarray.put("ARCHD", o[0].toString());
                    } else {
                    }
                }


//NOW GET TOTAL COUNT OF ROW AND ADD TO JSON OBJECT
                String sqlCOUNT = "";
                if (multiMutInClause.contains("2.0")) {
                    sqlCOUNT = (new StringBuilder()).append("SELECT FORMAT(SUM(reporting_count),0) FROM strains WHERE id_str IN (SELECT  DISTINCT id_str"
                            + fromClause
                            + joinClause
                            + multiMutWhereClause + " AND l.code = " + lab
                            + " )").toString();
                } else {
                    sqlCOUNT = (new StringBuilder()).append(
                            "SELECT COUNT(distinct s.id_str) "
                            + fromClause
                            + joinClause
                            + whereClause + " AND l.code = " + lab).toString();
                }
                listcount = sm.getStrainsBySQL(sqlCOUNT);
                if (listcount.get(0) == null) {
                    listcount.set(0, "0");
                }
                obj2.put("totalcount", listcount.get(0).toString());
                if (multiMutInClause.contains("2.0")) {
                } else {
                    iCount = iCount + Integer.parseInt(listcount.get(0).toString());
                }
                JSONobjects2.add(obj2);
            }
            //end of labs iterator
            String sqlCOUNT = "";
            String keyVals = listarray.keySet().toString();
            String[] statusValues = {"ACCD", "ARRD", "ARING", "ARCHD"};
            JSONObject obj3 = new JSONObject();
            obj3.put("code", "~~TOTAL~~");

            //remove dates from whereclause
            for (int iStatusValues = 0; iStatusValues < statusValues.length; iStatusValues++) {
                if (multiMutInClause.contains("2.0")) {
                    sqlCOUNT = (new StringBuilder()).append("SELECT FORMAT(SUM(reporting_count),0) FROM strains WHERE id_str IN (SELECT  DISTINCT id_str"
                            + fromClause
                            + joinClause
                            + multiMutWhereClause + " AND s.str_status='" + statusValues[iStatusValues] + "'"
                            + " )").toString();
                } else {
                    sqlCOUNT = (new StringBuilder()).append(
                            "SELECT COUNT(distinct s.id_str) "
                            + fromClause
                            + joinClause
                            + whereClause + " AND s.str_status='" + statusValues[iStatusValues] + "'").toString();
                }

                listcount = sm.getStrainsBySQL(sqlCOUNT);
                // NEW JSON OBJECT
                if (listcount.get(0) == null) {
                    listcount.set(0, "0");
                }
                if (statusValues[iStatusValues].toString().equals("ACCD")) {
                    obj3.put("accepted", listcount.get(0).toString());
                } else if (statusValues[iStatusValues].toString().equals("ARRD")) {
                    obj3.put("arrived", listcount.get(0).toString());
                } else if (statusValues[iStatusValues].toString().equals("ARING")) {
                    obj3.put("archiving", listcount.get(0).toString());
                } else if (statusValues[iStatusValues].toString().equals("ARCHD")) {
                    obj3.put("archived", listcount.get(0).toString());
                }

            }//end of status values iterator

            if (multiMutInClause.contains("2.0")) {
                sqlCOUNT = (new StringBuilder()).append("SELECT FORMAT(SUM(reporting_count),0) FROM strains WHERE id_str IN (SELECT  DISTINCT id_str"
                        + fromClause
                        + joinClause
                        + multiMutWhereClause
                        + " )").toString();

            } else {
                sqlCOUNT = (new StringBuilder()).append(
                        "SELECT COUNT(distinct s.id_str) "
                        + fromClause
                        + joinClause
                        + whereClause).toString();
            }

            listcount = sm.getStrainsBySQL(sqlCOUNT);
            obj3.put("totalcount", listcount.get(0).toString());
            JSONobjects2.add(obj3);
            returnedOut.put("JSONstatic", JSONobjects2);
            returnedOut.put("JSON", JSONobjects);
            JSONobjects = new LinkedList();
        }
        return returnedOut;
    }

    public String cleanClause(String clause) {
        if (clause.endsWith(",")) {
            clause = (new StringBuilder()).append(clause).append(")").toString();
        }
        if (clause.endsWith(",,)")) {
            clause = clause.replace(",,)", ",)");
        }
        if (clause.endsWith(",)")) {
            clause = clause.replace(",)", ")");
        }
        if (clause.endsWith("(") || clause.endsWith("()")) {
            clause = "";
        }
        return clause;
    }

    public JSONObject getMedianValue(JSONObject obj, List listDiffs, boolean eucState, boolean reportPeriod) {

        ArrayList<Double> data = new ArrayList<Double>();
        data = new ArrayList<Double>();
        for (Iterator it1 = listDiffs.listIterator(); it1.hasNext();) {
            Object[] o1 = (Object[]) it1.next();


            if (eucState && reportPeriod) {
                if (o1[3] != null) {
                    o1[2] = o1[3];
                }
            }

            if (o1[2] == null) {
                // o1[1] = 0;
            }
            if (o1[2] != null) {
                if (!o1[2].toString().equals("0")) {
                    String iVal = o1[2].toString();
                    Double d = Double.parseDouble(iVal);
                    data.add(d);
                } else {
                }
            }
        }

        if (data.size() > 0) {
            MedianFinder mf = new MedianFinder();
            Double medianValue = mf.find(data);
            if (medianValue >= 0.0) {
                obj.put("MEDIAN", medianValue);
            }
        }
        return obj;
    }

    public String medianSQL(String sql2, String joinClause, String fromClause, String whereClause, String groupClause, String labCode, String dateDiff, boolean eucState, boolean reportPeriod) {
        groupClause = "ORDER BY DIFF";
        if (labCode.equals("NO")) {
            //Used to retrieve median of all values for total median
            whereClause = (new StringBuilder()).append(whereClause).append(" AND " + dateDiff + " IS NOT NULL ").toString();
        } else {
            whereClause = (new StringBuilder()).append(whereClause).append(" AND l.code='" + labCode + "' AND (" + dateDiff + ") ").toString();
        }
        if (eucState && reportPeriod) {
            groupClause = (new StringBuilder()).append(groupClause).append(",DIFFGLT").toString();
            //DATEDIFF(glt,evaluated)
            whereClause = (new StringBuilder()).append(whereClause).append(" OR " + dateDiffGlt + " IS NOT NULL ").toString();
        }
        sql2 = sql2.replace("SELECT", "SELECT DISTINCT s.id_str,");
        sql2 = (new StringBuilder()).append(sql2).append(
                fromClause
                + joinClause
                + whereClause
                + groupClause).toString();
        return sql2;
    }

    public String actualCount(String joinClause, String fromClause, String whereClause, String whereClauseDates, String labCode) {
        String actCountSQL = "SELECT COUNT(distinct s.id_str) ";
        actCountSQL = (new StringBuilder()).append(actCountSQL).append(fromClause).append(joinClause).append(whereClause).append(whereClauseDates).append(" AND l.code='" + labCode + "'").toString();
        List l = sm.getStrainsBySQL(actCountSQL);
        String count = l.get(0).toString();
        System.out.println("actual count sql=" + actCountSQL);
        return count;
    }

    public Map multiMutCounts(List l) {
        Map obj = new HashMap();
        for (Iterator it = l.listIterator(); it.hasNext();) {
            Object[] o = (Object[]) it.next();

            if (o[2].toString().equals("ACCD")) {
                obj.put("accepted", o[0]);
            } else {
            }
            if (o[2].toString().equals("ARRD")) {
                obj.put("arrived", o[0]);
            } else {
            }
            if (o[2].toString().equals("ARING")) {
                obj.put("archiving", o[0]);
            } else {
            }
            if (o[2].toString().equals("ARCHD")) {
                obj.put("archived", o[0]);
            } else {
            }
        }
        Set objKeys = obj.keySet();
        if (!objKeys.contains("accepted")) {
            obj.put("accepted", 0);
        }
        if (!objKeys.contains("arrived")) {
            obj.put("arrived", 0);
        }
        if (!objKeys.contains("archiving")) {
            obj.put("archiving", 0);
        }
        if (!objKeys.contains("archived")) {
            obj.put("archived", 0);
        }

        return obj;
    }
    
    public List manualMetrics(String joinClause, String fromClause, String whereClause, String fieldsGLT, String fieldsARR) {
        String selectStatementLab = "SELECT DISTINCT l.code ";
        String selectStatement = "SELECT DISTINCT s.id_str,DATEDIFF(" + fieldsGLT + "),DATEDIFF(" + fieldsARR + ")";

        Double dblDiffGLT = 0.0;
        ArrayList<Double> dateDiffFinalValues = new ArrayList<Double>();
        ArrayList<Double> totMAXValues = new ArrayList<Double>();
        ArrayList<Double> totMINValues = new ArrayList<Double>();
        ArrayList<Double> totDateDiffValues = new ArrayList<Double>();
        BigDecimal totSUM = new BigDecimal("0.0");

        List returnObjects = new LinkedList();
        
        //for totals
        BigDecimal totActualCount=new BigDecimal("0.0");
        BigDecimal totCount=new BigDecimal("0.0");
        BigDecimal AVG = new BigDecimal("0.0");
        
        double MAX=0;
        double MIN =0;

        List labCode = sm.getStrainsBySQL(selectStatementLab + fromClause + joinClause + whereClause + " ORDER BY l.code");
        for (Iterator it = labCode.listIterator(); it.hasNext();) {
            dateDiffFinalValues = new ArrayList<Double>();
            Object oLab = it.next().toString();
            BigDecimal sum = new BigDecimal("0.0");

            List dateDiffData = sm.getStrainsBySQL(selectStatement + fromClause + joinClause + whereClause + "AND l.code='" + oLab + "'");
            System.out.println("manualmetrics query is:-- " + selectStatement + fromClause + joinClause + whereClause + "AND l.code='" + oLab + "'");
            int i = 0;
            for (Iterator it2 = dateDiffData.listIterator(); it2.hasNext();) {
                Object[] oDiffData = (Object[]) it2.next();
                if (oDiffData[1] != null && !oDiffData[1].toString().startsWith("-")) {
                    dblDiffGLT = Double.parseDouble(oDiffData[1].toString());
                } else if (oDiffData[2] != null && !oDiffData[2].toString().startsWith("-")) {
                    dblDiffGLT = Double.parseDouble(oDiffData[2].toString());
                } else {
                    dblDiffGLT = null;
                }

                if (dblDiffGLT != null) {
                    dateDiffFinalValues.add(dblDiffGLT);
                    //Sum values for avg calculation
                    sum = sum.add(new BigDecimal(dblDiffGLT));
                    totSUM = totSUM.add(new BigDecimal(dblDiffGLT));
                    totDateDiffValues.add(dblDiffGLT);
                }
                i++;
            }//end of diff date iteration   
            //i = i - 1;
            Collections.sort(dateDiffFinalValues);

            MedianFinder mf = new MedianFinder();
            Double medianValue = mf.find(dateDiffFinalValues);
            //int divisor = dateDiffFinalValues.size();
            BigDecimal divisor = new BigDecimal(Integer.valueOf(dateDiffFinalValues.size()));
            System.out.println("LOG:: DIVISOR=" + sum + "\\" + divisor);
            BigDecimal div = new BigDecimal(String.valueOf(divisor));
            totActualCount=totActualCount.add(divisor);
            BigDecimal addToTotal=new BigDecimal(Integer.valueOf(dateDiffData.size()));
            totCount=totCount.add(addToTotal);
            //    BigDecimal AVG = sum.divide(new BigDecimal(String.valueOf(divisor),9, BigDecimal.ROUND_HALF_UP));
            if (dateDiffFinalValues.size() != 0){
            AVG = sum.divide(divisor, 0, RoundingMode.HALF_UP);
            MAX = dateDiffFinalValues.get(dateDiffFinalValues.size() - 1);
            MIN = dateDiffFinalValues.get(0);
            } else {
             AVG = new BigDecimal("0.0");
            MAX = 0;
            MIN = 0;
            }
JSONObject objMan = new JSONObject();
            objMan.put("code", oLab);
            objMan.put("COUNT", dateDiffData.size());
            objMan.put("SUM", sum);
            objMan.put("AVG", AVG);
            objMan.put("MAX", MAX);
            objMan.put("MIN", MIN);
            objMan.put("ACTUALCOUNT",divisor);
            objMan.put("MEDIAN",medianValue);
            //FOR TOTALS
        totMAXValues.add(MAX/*dateDiffFinalValues.get(dateDiffFinalValues.size() - 1)*/);
        totMINValues.add(MIN/*dateDiffFinalValues.get(0)*/);
            
            returnObjects.add(objMan);
        }//end of lab code iteration
//TOTALS NOW
        Collections.sort(totMAXValues);
        Collections.sort(totMINValues);
        Collections.sort(totDateDiffValues);
        
        JSONObject objTot = new JSONObject();
        objTot.put("code", "~~ TOTAL ~~");
        objTot.put("ACTUALCOUNT", totActualCount);
        objTot.put("COUNT", totCount);
        objTot.put("AVG", totSUM.divide(totActualCount, 0, RoundingMode.HALF_UP));//sum.divide(divisor, 0, RoundingMode.HALF_UP);
        objTot.put("MAX", totDateDiffValues.get(totDateDiffValues.size() - 1));
        objTot.put("MIN", totDateDiffValues.get(0));
        Double medianValue = MedianFinder.find(totDateDiffValues);
        objTot.put("MEDIAN", medianValue);

        returnObjects.add(objTot);
        System.out.println("FINAL JSON OBJECT=" + objTot.toString());

        return returnObjects;
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
