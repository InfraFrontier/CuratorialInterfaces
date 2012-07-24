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
import javax.servlet.http.HttpSession;
import org.emmanet.jobs.WebRequests;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

/**
 *
 * @author phil
 */
public class requestInterfaceFormController extends SimpleFormController {

    private Map returnedOut = new HashMap();
    public static final String MAP_KEY = "returnedOut";
    WebRequests wr = new WebRequests();
    // private boolean subPressed = false;

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // CLEAR HASHMAP AS SEEMS TO RETAIN DATA WHEN SEARCHES ARE CHANGED
        returnedOut.clear();
        List results = null;
        String searchDescription = "";
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Object o = ((UserDetails) obj).getUsername();
        String userName = o.toString();
        HttpSession session = request.getSession(true);
        session.setAttribute("displayShip", "N");
        returnedOut.put(
                "UserName", userName);

        /* Now get parameter values */

        //   Enumeration e = request.getParameterNames();
        //     while (e.hasMoreElements()) {
        //        String val = (String) e.nextElement();
        //        System.out.println(val + " = " + request.getParameter(val));

        String commonStrainname = request.getParameter("commonStrainName");
        String commonStrainnameText = request.getParameter("commonStrainNameText");
        String emmaIdText = request.getParameter("emmaIdText");
        String strainNameReqText = request.getParameter("strainNameReqText");
        String reqSci_surname = request.getParameter("reqSci_surname");
        String reqCon_contact = request.getParameter("reqCon_contact");
        String reqStatus = request.getParameter("reqStatus");
        String reqConCountry = request.getParameter("reqConCountry");
        String strainNameReq = request.getParameter("strainNameReq");
        String reqROI = request.getParameter("reqROI");
        String reqArchcentres = request.getParameter("ReqArchCentres");
        String emmaID = request.getParameter("emmaID");
        String reqRtools = request.getParameter("reqrTools");
        String reqProjects = request.getParameter("reqProjects");
        String reqShipDateAfter = request.getParameter("reqShipDateAfter");
        String reqShipDatebefore = request.getParameter("reqShipDatebefore");
        String reqSubDateAfter = request.getParameter("reqSubDateAfter");
        String reqSubDatebefore = request.getParameter("reqSubDatebefore");
        String reqShipmentType = request.getParameter("reqShipmentType");

        String funding = request.getParameter("reqFunding");
        String reqfundingSources[] = request.getParameterValues("reqFunding");
        String reqApplicationType = request.getParameter("reqApplicationType");
        String reqTADecision = request.getParameter("reqTADecision");

        //new field request id
        String reqIdText = request.getParameter("reqIdText");
        String reqMaterial = request.getParameter("reqMaterial");




        /* LETS DO SOME RETIEVAL FROM DATABASE AND ADD TO ReturnedOut */

        /*
         * Code for list ing requests per site/username
         */
        if (request.getParameter("listAll") != null) {
            results = wr.getLiveReqsByCentre(userName);
            returnedOut.put("searchDescription", "all requests to fulfill for user " + userName);
            returnedOut.put("results", results);
            returnedOut.put("searchSize", results.size());
            return new ModelAndView(getSuccessView(), MAP_KEY, returnedOut);
        }

        /*
         * Code for edit requests button
         */
        if (request.getParameter("listReqs") != null) {
            results = results = wr.getReqsByEmmaID(emmaID);
            returnedOut.put("searchDescription", "requests for EMMA ID:  " + emmaID);
            returnedOut.put("results", results);
            returnedOut.put("searchSize", results.size());
            return new ModelAndView(getSuccessView(), MAP_KEY, returnedOut);
        }

        // just use one of the name/ID filters if set and none of the other ones
   /*     if (/*emmaIdText.length() != 0 ||emmaID.length() != 0 */
        /*commonStrainname.length() != 0 ||
        strainNameReqText.length() != 0 || strainNameReq.length() != 0*///) {

        /*    if (emmaIdText.length() != 0 || emmaID.length() != 0) {
        if (emmaIdText.length() == 0) {
        results = wr.getReqsByEmmaID(emmaID);
        returnedOut.put("searchDescription", "emma ID equals " + emmaID);
        } else {
        // emmaIdText = "%" + emmaIdText;
        results = wr.getReqsByEmmaID("%" + emmaIdText);
        returnedOut.put("searchDescription", "emma ID contains " + emmaIdText);
        }
        
        returnedOut.put("results", results);
        returnedOut.put("searchSize", results.size());
        System.out.println("+=+=+= " + results.size());
        }
         * */



        /* COMMON STRAIN NAME LIKE SEARCH */
        /*  if (commonStrainname.length() != 0) {
        results = null;
        results = wr.getReqsByComName("%" + commonStrainname);
        returnedOut.put("searchDescription", "common strain name like " + commonStrainname);
        returnedOut.put("results", results);
        returnedOut.put("searchSize", results.size());
        }
         * */

        /* STRAIN NAME SEARCHES */

        /*   if (strainNameReq.length() != 0 || strainNameReqText.length() != 0) {
        results = null;
        if (strainNameReq.length() != 0) {
        results = wr.getReqsByStrainName(strainNameReq);
        returnedOut.put("searchDescription", "strain name equals " + strainNameReq);
        } else {
        results = wr.getReqsByStrainName("%" + strainNameReqText);
        returnedOut.put("searchDescription", "strain name contains " + strainNameReqText);
        }
        
        returnedOut.put("results", results);
        returnedOut.put("searchSize", results.size());
        }*/
        //    } else {// rest of filters - combine with ANDs

        if (!reqShipDateAfter.equals("") || !reqShipDatebefore.equals("") || reqStatus.equals("SHIP")) {
            //trigger for extra date field in jsp display
            System.out.println("shipped after date = " + reqShipDateAfter + " shipped before date = " + reqShipDatebefore + " Status=" + reqStatus);
            session.setAttribute("displayShip", "Y");
        }

        String sql = "SELECT id_req,strain_id,code,"
                + "strains.name,sci_e_mail, sci_firstname,sci_surname,req_status,"
                + "str_access ,available_to_order,register_interest,id_str,timestamp,wr.str_id_str,con_country,date_processed,notes,str_status";

        String fromClause = " FROM web_requests AS wr,strains,laboratories";

        String whereClause = "";
        String where = " WHERE wr.str_id_str = strains.id_str "
                + "AND (laboratories.id_labo=wr.lab_id_labo ";
        String buildClause = "";
        String ROIwhereClause = "";

        if (reqArchcentres.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append("lab_id_labo =" + reqArchcentres + " ").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("repository " + reqArchcentres + ", ").toString();
        }

        if (reqROI.length() != 0) {
            if (!reqROI.equals("0")) {
                if (!"".equals(whereClause)) {
                    whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
                }
            }
            if (reqROI.equals("0")) {
                // LOOK FOR NULL VALUES TOO
                reqROI = reqROI + " OR register_interest IS NULL";
                ROIwhereClause = (new StringBuilder()).append(" AND (register_interest =" + reqROI + ") ").toString();
                searchDescription = (new StringBuilder()).append(searchDescription).append("roi " + reqROI + ", ").toString();
            } else {
                whereClause = (new StringBuilder()).append(whereClause).append("register_interest =" + reqROI + " ").toString();
                searchDescription = (new StringBuilder()).append(searchDescription).append("roi " + reqROI + ", ").toString();
            }
        }

        if (reqStatus.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append("req_status = '" + reqStatus + "' ").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("request status " + reqStatus + ", ").toString();
        }

        if (reqConCountry.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append("con_country = '" + reqConCountry + "' ").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("request country " + reqConCountry + ", ").toString();
        }

        if (reqSci_surname.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append("sci_surname like '%" + reqSci_surname + "%' ").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("sci surname " + reqSci_surname + ", ").toString();
        }

        if (reqCon_contact.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append("con_surname like '%" + reqCon_contact + "%' ").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("con surname " + reqCon_contact + ", ").toString();
        }


        ///////////////////////////////////////

        if (reqRtools.length() != 0) {
            //need to amend from clause and where clause

            fromClause = (new StringBuilder()).append(fromClause).append(",rtools_strains").toString();//fromClause + ",rtools_strains";

            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append("rtools_strains.rtls_id = '" + reqRtools + "' AND rtools_strains.str_id_str=wr.str_id_str").toString();

            searchDescription = (new StringBuilder()).append(searchDescription).append("rTools id equals " + reqRtools + ", ").toString();
        }

        if (reqProjects.length() != 0) {
            //need to amend from clause and where clause

            fromClause = fromClause + ",projects_strains";

            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append("projects_strains.project_id = '" + reqProjects + "' AND projects_strains.str_id_str=wr.str_id_str").toString();

            searchDescription = (new StringBuilder()).append(searchDescription).append("project id equals " + reqProjects + ", ").toString();
        }

        //////////////////////////////////////

        if (reqSubDatebefore.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append("timestamp < '" + reqSubDatebefore + "' ").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("sub date before " + reqSubDatebefore + ", ").toString();
        }

        if (reqSubDateAfter.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append("timestamp > '" + reqSubDateAfter + "' ").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("sub date after " + reqSubDateAfter + ", ").toString();
        }

        if (reqShipDatebefore.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append("date_processed < '" + reqShipDatebefore + "' ").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("ship date before " + reqShipDatebefore + ", ").toString();
        }

        if (reqShipDateAfter.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }

            whereClause = (new StringBuilder()).append(whereClause).append("date_processed > '" + reqShipDateAfter + "' ").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("shipping date date after " + reqShipDateAfter + ", ").toString();
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        if (emmaIdText.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append("strain_id LIKE '%" + emmaIdText + "%'").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("EMMA ID contains ").append(emmaIdText).append(", ").toString();
        }


        ////////////////////////////////////////////////////////////////////NEW FIELD FOR REQUEST ID and material

        if (reqIdText.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append("id_req LIKE '%" + reqIdText + "%'").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("Request ID contains ").append(reqIdText).append(", ").toString();
        }

        if (reqMaterial.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append("req_material like '%" + reqMaterial + "%' ").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("requested material contains " + reqMaterial + ", ").toString();
        }

        ///////////////////////////////////////////////////////////////////END NEW FIELD REQUEST ID

        /* COMMON STRAIN NAME LIKE SEARCH */
        if (commonStrainnameText.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            System.out.println("preHERE STARTS SQL MODULE OUTPUT:--");
            System.out.println("preselect CLAUSE (sql):--" + sql);
            System.out.println("preFROM CLAUSE:--" + fromClause);
            System.out.println("preWHERE:--" + where);
            System.out.println("preWHERE CLAUSE:--" + whereClause);
            fromClause = (new StringBuilder()).append(fromClause).append(",syn_strains as ss").toString();

            where = " WHERE ss.name LIKE '%" + commonStrainnameText + "%'";

            buildClause = (new StringBuilder()).append("(" + whereClause
                    + " wr.str_id_str = ss.str_id_str AND laboratories.id_labo=wr.lab_id_labo AND wr.str_id_str = strains.id_str ").toString();

            where = (new StringBuilder()).append(where).append(" AND " + buildClause).toString();//+whereclause

/////////////////////////////////////////////////////////////////////////////##############

            whereClause = "";

            searchDescription = (new StringBuilder()).append(searchDescription).append("common strain name like ").append(commonStrainnameText).append(", ").toString();

            System.out.println("HERE STARTS SQL MODULE OUTPUT:--");
            System.out.println("select CLAUSE (sql):--" + sql);
            System.out.println("FROM CLAUSE:--" + fromClause);
            System.out.println("WHERE:--" + where);
            System.out.println("WHERE CLAUSE:--" + whereClause);
        }
        if (reqROI.contains("wwNULL")) {
            //special sql case

            whereClause = " AND (register_interest =0 OR register_interest IS NULL)";
            buildClause = (new StringBuilder()).append(buildClause
                    + " AND (wr.str_id_str = ss.str_id_str AND laboratories.id_labo=wr.lab_id_labo AND wr.str_id_str = strains.id_str ").append(")" + whereClause).toString();
            System.out.println("roiwhereclause====" + whereClause);
            System.out.println("buildclause====" + buildClause);
            System.out.println("where====" + where);


        }
        /* STRAIN NAME SEARCHES
        
        String sql = "SELECT id_req,strain_id,code," +
        "strains.name,sci_e_mail, sci_firstname,sci_surname,req_status," +
        "str_access ,available_to_order,register_interest,id_str,timestamp,wr.str_id_str";
        
        String fromClause = " FROM web_requests AS wr,strains,laboratories";
        
        String whereClause = "";
        String where =  " WHERE wr.str_id_str = strains.id_str " +
        "AND (laboratories.id_labo=wr.lab_id_labo AND ";
        
         */

        if (strainNameReq.length() != 0 || strainNameReqText.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            if (strainNameReq.length() != 0) {
                whereClause = (new StringBuilder()).append(whereClause).append("strains.name = '" + strainNameReq + "'").toString();
                searchDescription = "strain name equals " + strainNameReq;
            } else {
                whereClause = (new StringBuilder()).append(whereClause).append(" strains.name LIKE '%" + strainNameReqText + "%'").toString();
                searchDescription = "strain name contains " + strainNameReqText;
            }
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

        if (reqApplicationType.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }

            whereClause = (new StringBuilder()).append(whereClause).append("application_type = '" + reqApplicationType + "' ").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("application type equals " + reqApplicationType + ", ").toString();

        }

        if (reqTADecision.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }

            whereClause = (new StringBuilder()).append(whereClause).append("ta_panel_decision = '" + reqTADecision + "' ").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("TA panel decision equals " + reqTADecision + ", ").toString();

        }

        if (reqShipmentType.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }

            whereClause = (new StringBuilder()).append(whereClause).append("eucomm_funding = '" + reqShipmentType + "' ").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("eucomm funding equals " + reqShipmentType + ", ").toString();

        }


        String joinClause = "";
        if (funding.length() != 0) {
            String searchText = "";
            fromClause = (new StringBuilder()).append(fromClause).append(",sources_requests").toString();

            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).toString();//append(" !AND ")
            }

            joinClause = (new StringBuilder()).append(joinClause).append(" AND (sources_requests.req_id_req=wr.id_req)").toString();//added ) removed AND sources_requests.sour_id=

            for (int i = 0; i < reqfundingSources.length; i++) {
                if (i == 0) {
                    // joinClause = (new StringBuilder()).append(joinClause).append(reqfundingSources[i]).toString(); rmoved
                    joinClause = (new StringBuilder()).append(joinClause).append(" AND (sources_requests.sour_id=").append(reqfundingSources[i]).toString();
                    searchText = (new StringBuilder()).append(searchText).append(reqfundingSources[i]).toString();
                } else {
                    joinClause = (new StringBuilder()).append(joinClause).append(" OR sources_requests.sour_id=").append(reqfundingSources[i]).toString();
                    //whereClause = (new StringBuilder()).append(whereClause).append(" OR sources_requests.sour_id=").append(reqfundingSources[i]).toString();
                    searchText = (new StringBuilder()).append(searchText).append(" OR ").append(reqfundingSources[i]).toString();
                }
            }
            where = (new StringBuilder()).append(" WHERE wr.str_id_str = strains.id_str ").append(joinClause).append(") ").toString();
            where = (new StringBuilder()).append(where).append(" AND (laboratories.id_labo=wr.lab_id_labo ")./*append(whereClause).*/toString();//added .append(whereClause)
            //(new StringBuilder()).append(where).append(joinClause).append(") ").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("funding source equals ").append(searchText).append(", ").toString();

        }
        sql += fromClause + where;
        if (!"".equals(whereClause)) {
            sql += " AND " + whereClause;
        }
        sql += ")";
        if (reqROI.contains("NULL")) {
            sql += ROIwhereClause;
        }
        sql += " ORDER BY req_status DESC,timestamp DESC,strain_id";
        // }
        results = null;
        System.out.println("DEBUG SQL:: " + sql);
        results = wr.getReqs(sql);

        returnedOut.put("searchDescription", searchDescription);
        returnedOut.put("results", results);
        returnedOut.put("searchSize", results.size());
        return new ModelAndView(getSuccessView(), MAP_KEY, returnedOut);
    }
}
