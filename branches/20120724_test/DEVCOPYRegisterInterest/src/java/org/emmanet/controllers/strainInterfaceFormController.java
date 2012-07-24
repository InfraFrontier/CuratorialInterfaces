/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.controllers;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.emmanet.jobs.WebRequests;
import org.emmanet.model.ArchiveManager;
import org.emmanet.model.CVAvailabilitiesDAO;
import org.emmanet.model.LaboratoriesManager;
import org.emmanet.model.StrainsDAO;
import org.emmanet.model.StrainsManager;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

public class strainInterfaceFormController extends SimpleFormController {

    private ModelAndView includeView;
    private Map returnedOut;
    public static final String MAP_KEY = "returnedOut";
    private String pathToMTA;
    Boolean strainIDexists;
    Boolean archIDexists;
    Boolean edit;
    private StrainsManager sm;
    private LaboratoriesManager lm;
    private ArchiveManager ar;
    private StrainsManager sr;
    private WebRequests wr;

    public strainInterfaceFormController() {
        returnedOut = new HashMap();
        strainIDexists = Boolean.valueOf(false);
        archIDexists = Boolean.valueOf(false);
        edit = Boolean.valueOf(false);
        sm = new StrainsManager();
        ar = new ArchiveManager();
        sr = new StrainsManager();
        wr = new WebRequests();
    }

    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        System.out.println();
        System.out.println();
        System.out.println(getSuccessView());
        System.out.println();
        System.out.println();
        setSuccessView("/interfaces/strainsInterface");
        System.out.println();
        System.out.println();
        System.out.println(getSuccessView());
        System.out.println();
        System.out.println();
        Object obj = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Object o = ((UserDetails) obj).getUsername();
        String userName = o.toString();
        returnedOut.put("UserName", userName);
        Enumeration param = request.getParameterNames();
        do {
            if (!param.hasMoreElements()) {
                break;
            }
            String paramName = (String) param.nextElement();
            if (paramName.equals("strainID")) {
                strainIDexists = Boolean.valueOf(true);
            }
            if (paramName.equals("archID")) {
                archIDexists = Boolean.valueOf(true);
            }
            if (paramName.equals("Edit")) {
                edit = Boolean.valueOf(true);
            }
        } while (true);
        if (strainIDexists.booleanValue()) {
            List strainDetails = sr.getStrain(request.getParameter("strainID"));
            returnedOut.put("strain", strainDetails);

            int strainiD = Integer.parseInt(request.getParameter("strainID"));
            StrainsDAO sd = sm.getStrainByID(strainiD);

            returnedOut.put("mtaPath", pathToMTA);
            //ystem.out.println(archIDexists.booleanValue());
            //System.out.println(edit.booleanValue());

            returnedOut.put("centre", sd.getArchiveDAO().getLabsDAO().getName());
            returnedOut.put("submitted", sd.getArchiveDAO().getSubmitted());
            strainIDexists = Boolean.valueOf(false);
            //     }
            setSuccessView("/interfaces/strainDetails_inc");
            strainIDexists = Boolean.valueOf(false);


            //stock information
            int strainID = Integer.parseInt(request.getParameter("strainID"));
            List as = ar.getStrainAvail(strainID);
            String cvavailDetails = "";
            CVAvailabilitiesDAO cvaDAO = new CVAvailabilitiesDAO();

            System.out.println("size is ::" + as.size());
            for (Iterator it = as.listIterator(); it.hasNext();) {

                System.out.println(as.size());
                Object[] ob = (Object[]) it.next();
                String availid = ob[1].toString();
                int availID = Integer.parseInt(availid);
                cvaDAO = (CVAvailabilitiesDAO) ar.getCVAvail(availID);
                String inStock = "";
                String toDist = "";
                if (cvaDAO.getIn_stock() == 0 || cvaDAO.getTo_distr() == 0) {
                    inStock = "No";
                    toDist = "No";
                } else {
                    inStock = "Yes";
                    toDist = "Yes";
                }
                cvavailDetails = cvavailDetails + cvaDAO.getDescription() + " ::-    In stock: " + inStock + " ::-   To distribute: " + toDist + "||";

                returnedOut.put("cvAvail", cvavailDetails);
            }

            return new ModelAndView(getSuccessView(), "returnedOut", returnedOut);
        }
        String strainName = request.getParameter("strainName");
        String strainNameText = request.getParameter("strainNameText");
        String archCentres = request.getParameter("archCentres");
        String emmaID = request.getParameter("emmaID");
        String emmaIdText = request.getParameter("emmaIdText");
        String strainAccess = request.getParameter("strainAccess");
        String commonStrainName = request.getParameter("commonStrainName");
        String status = request.getParameter("status");
        String statusTypes[] = request.getParameterValues("status");
        String commonStrainText = request.getParameter("commonStrainText");
        String affectedGene = request.getParameter("affectedGene");
        String affectedGeneText = request.getParameter("affectedGeneText");
        String phenoDesc = request.getParameter("phenoDesc");
        String funding = request.getParameter("funding");
        String fundingSources[] = request.getParameterValues("funding");
        String rTools = request.getParameter("rTools");
        String projects = request.getParameter("projects");
        String mutantType = request.getParameter("mutantType");
        String mutantSubType = request.getParameter("mutantSubType");
        String conCountry = request.getParameter("conCountry");
        String sciSurname = request.getParameter("sciSurname");
        String conSurname = request.getParameter("conSurname");
        String mgiId = request.getParameter("mgiId");
        String orderAvail = request.getParameter("orderAvail");
        String subDateAfter = request.getParameter("subDateAfter");
        String subDatebefore = request.getParameter("subDatebefore");
        String evalDateAfter = request.getParameter("evalDateAfter");
        String evalDatebefore = request.getParameter("evalDatebefore");
        String recDateAfter = request.getParameter("recDateAfter");
        String recDatebefore = request.getParameter("recDatebefore");
        String archstartDateAfter = request.getParameter("archstartDateAfter");
        String archstartDatebefore = request.getParameter("archstartDatebefore");
        String archDateAfter = request.getParameter("archDateAfter");
        String archDatebefore = request.getParameter("archDatebefore");
        String ss = request.getParameter("ss");
        String codeInternal = request.getParameter("codeInternal");
        List results = null;
        if (request.getParameter("listAll") != null) {
            results = sm.getLiveStrainsByCentre(userName);
            returnedOut.put("searchDescription", (new StringBuilder()).append("all strains assigned to user ").append(userName).toString());
            returnedOut.put("results", results);
            returnedOut.put("searchSize", Integer.valueOf(results.size()));
            return new ModelAndView(getSuccessView(), "returnedOut", returnedOut);
        }
        /*  if emmaIdText.length() != 0 ||emmaID.length() != 0 ||commonStrainName.length() != 0 || commonStrainText.length() != 0 || affectedGene.length() != 0 || affectedGeneText.length() != 0 || strainNameText.length() != 0 || strainName.length() != 0 || archCentres.length() != 0 || strainAccess.length() != 0 || status.length() != 0 ||phenoDesc.length() != 0) {*/
        /* if (strainName.length() != 0 || strainNameText.length() != 0) {
        results = null;
        if (strainName.length() != 0) {
        results = sm.getStrainsByName(strainName);
        returnedOut.put("searchDescription", (new StringBuilder()).append("strain name equals ").append(strainName).toString());
        } else {
        results = sm.getStrainsByName((new StringBuilder()).append("%").append(strainNameText).toString());
        returnedOut.put("searchDescription", (new StringBuilder()).append("strain name contains ").append(strainNameText).toString());
        }
        returnedOut.put("results", results);
        returnedOut.put("searchSize", Integer.valueOf(results.size()));
        }*/
        /*  if (affectedGene.length() != 0 || affectedGeneText.length() != 0) {
        results = null;
        if (affectedGene.length() != 0) {
        results = sm.getStrainsByGene(affectedGene);
        returnedOut.put("searchDescription", (new StringBuilder()).append("affected gene equals ").append(affectedGene).toString());
        } else {
        results = sm.getStrainsByGene((new StringBuilder()).append("%").append(affectedGeneText).toString());
        returnedOut.put("searchDescription", (new StringBuilder()).append("affected gene contains ").append(affectedGeneText).toString());
        }
        returnedOut.put("results", results);
        returnedOut.put("searchSize", Integer.valueOf(results.size()));
        }
         * */
        /*   if (commonStrainName.length() != 0 || commonStrainText.length() != 0) {
        results = null;
        List results2 = null;
        if (commonStrainName.length() != 0) {
        results = sm.getStrainsByCommName(commonStrainName);
        } else {
        results = sm.getStrainsByCommName((new StringBuilder()).append("%").append(commonStrainText).toString());
        }
        String selectSQL = "";
        for (ListIterator it = results.listIterator(); it.hasNext();) {
        int index = it.nextIndex();
        Object element = it.next();
        if (index == 0) {
        selectSQL = (new StringBuilder()).append(selectSQL).append(element.toString()).toString();
        } else {
        selectSQL = (new StringBuilder()).append(selectSQL).append(" OR id_str = ").append(element.toString()).toString();
        }
        }
        
        if (results.size() != 0) {
        results2 = sm.getStrain(selectSQL);
        returnedOut.put("results", results2);
        returnedOut.put("searchSize", Integer.valueOf(results2.size()));
        returnedOut.put("searchDescription", (new StringBuilder()).append("common strain name like ").append(commonStrainName).toString());
        } else {
        returnedOut.put("searchSize", "0");
        }
        }
         * 
        if (emmaID.length() != 0 || emmaIdText.length() != 0) {
        results = null;
        if (emmaID.length() != 0) {
        results = sm.getStrainsByEmmaID(emmaID);
        returnedOut.put("searchDescription", (new StringBuilder()).append("EMMA ID equals ").append(emmaID).toString());
        } else {
        results = sm.getStrainsByEmmaID((new StringBuilder()).append("%").append(emmaIdText).toString());
        returnedOut.put("searchDescription", (new StringBuilder()).append("EMMA ID contains ").append(emmaIdText).toString());
        }
        returnedOut.put("results", results);
        returnedOut.put("searchSize", Integer.valueOf(results.size()));
        }
        /* if (archCentres.length() != 0) {
        results = null;
        Integer archiveID = Integer.valueOf(Integer.parseInt(archCentres));
        results = sm.getStrainsByArchCentre(archiveID.intValue());
        returnedOut.put("searchDescription", (new StringBuilder()).append("Archive centre equals ").append(archCentres).toString());
        returnedOut.put("results", results);
        returnedOut.put("searchSize", Integer.valueOf(results.size()));
        }
        if (strainAccess.length() != 0) {
        results = null;
        results = sm.getStrainsByAccessStatus(strainAccess);
        returnedOut.put("searchDescription", (new StringBuilder()).append("strain access equals ").append(strainAccess).toString());
        returnedOut.put("results", results);
        returnedOut.put("searchSize", Integer.valueOf(results.size()));
        }*/
        /*    if (status.length() != 0) {
        results = null;
        String sql = "";
        String searchText = "";
        for (int loopIndex = 0; loopIndex < statusTypes.length; loopIndex++) {
        if (loopIndex == 0) {
        sql = (new StringBuilder()).append(sql).append("'").append(statusTypes[loopIndex]).append("'").toString();
        searchText = (new StringBuilder()).append(searchText).append(statusTypes[loopIndex]).toString();
        } else {
        sql = (new StringBuilder()).append(sql).append(" OR str_status = '").append(statusTypes[loopIndex]).append("'").toString();
        searchText = (new StringBuilder()).append(searchText).append(" or ").append(statusTypes[loopIndex]).toString();
        }
        }
        results = sm.getStrainsByStrainStatus(sql);
        returnedOut.put("searchDescription", (new StringBuilder()).append("Strain status equals ").append(searchText).toString());
        returnedOut.put("results", results);
        returnedOut.put("searchSize", Integer.valueOf(results.size()));
        }
        if (phenoDesc.length() != 0) {
        results = null;
        results = sm.getStrainsByPhenoDesc(phenoDesc);
        returnedOut.put("searchDescription", (new StringBuilder()).append("phenotype description like ").append(phenoDesc).toString());
        returnedOut.put("results", results);
        returnedOut.put("searchSize", Integer.valueOf(results.size()));
        }
        } else {*/

        String searchDescription = "";
        String joinClause = "";
        String whereClause = "";


        if (funding.length() != 0) {
            String searchText = "";
            if (!"".equals(whereClause)) {
                //  whereClause = (new StringBuilder()).append(whereClause).append(" AND (").toString();
                }
            joinClause = (new StringBuilder()).append(joinClause).append(" JOIN s.sources_StrainsDAO as source ").toString();
            for (int i = 0; i < fundingSources.length; i++) {
                if (i == 0) {
                    whereClause = (new StringBuilder()).append(whereClause).append(" (source.sour_id = ").append(fundingSources[i]).toString();
                    searchText = (new StringBuilder()).append(searchText).append(fundingSources[i]).toString();
                } else {
                    whereClause = (new StringBuilder()).append(whereClause).append(" OR source.sour_id = ").append(fundingSources[i]).toString();
                    searchText = (new StringBuilder()).append(searchText).append(" OR ").append(fundingSources[i]).toString();
                }
            }
            whereClause = (new StringBuilder()).append(whereClause).append(") ").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("funding source equals ").append(searchText).append(", ").toString();
        }

        if (archCentres.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            joinClause = (new StringBuilder()).append(joinClause).append(" JOIN s.archiveDAO as lab ").toString();
            whereClause = (new StringBuilder()).append(whereClause).append(" lab.labsDAO.id_labo = ").append(archCentres).toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("Archive equals ").append(archCentres).append(", ").toString();
        }

        if (status.length() != 0) {
            String sql = "";
            String searchText = "";

            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            for (int loopIndex = 0; loopIndex < statusTypes.length; loopIndex++) {
                if (loopIndex == 0) {
                    sql = (new StringBuilder()).append(sql).append("'").append(statusTypes[loopIndex]).append("'").toString();
                    searchText = (new StringBuilder()).append(searchText).append(statusTypes[loopIndex]).toString();
                } else {
                    sql = (new StringBuilder()).append(sql).append(" OR str_status = '").append(statusTypes[loopIndex]).append("'").toString();
                    searchText = (new StringBuilder()).append(searchText).append(" or ").append(statusTypes[loopIndex]).toString();
                }
            }

            //joinClause = (new StringBuilder()).append(joinClause).append(" JOIN s.archiveDAO as lab ").toString();
            whereClause = (new StringBuilder()).append(whereClause).append(" (str_status = " + sql + ") ").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("Strain Status equals ").append(searchText).append(", ").toString();
        }

        if (strainAccess.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            //              joinClause = (new StringBuilder()).append(joinClause).append(" JOIN s.archiveDAO as lab ").toString();
            whereClause = (new StringBuilder()).append(whereClause).append(" str_access = '").append(strainAccess).append("' ").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("strain access equals ").append(strainAccess).append(", ").toString();

        }

        if (emmaIdText.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append(" emma_id LIKE '%").append(emmaIdText + "%'").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("EMMA ID contains ").append(emmaIdText).append(", ").toString();
        }

        if (affectedGene.length() != 0 || affectedGeneText.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }

            if (affectedGene.length() != 0) {
                joinClause = (new StringBuilder()).append(joinClause).append(" JOIN s.mutationsStrainsDAO as ms ").toString();
                whereClause = (new StringBuilder()).append(whereClause).append(" ms.mutationsDAO.allelesDAO.genesDAO.symbol LIKE '").append(affectedGene + "%'").toString();
                searchDescription = (new StringBuilder()).append(searchDescription).append("affected gene equals  ").append(affectedGene).append(", ").toString();
            } else {
                joinClause = (new StringBuilder()).append(joinClause).append(" JOIN s.mutationsStrainsDAO as ms ").toString();
                whereClause = (new StringBuilder()).append(whereClause).append("ms.mutationsDAO.allelesDAO.genesDAO.symbol LIKE '%").append(affectedGeneText + "%'").toString();
                searchDescription = (new StringBuilder()).append(searchDescription).append("affected gene contains ").append(affectedGeneText).toString();
            }
        }

        if (strainName.length() != 0 || strainNameText.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }

            if (strainName.length() != 0) {
                whereClause = (new StringBuilder()).append(whereClause).append(" s.name LIKE '").append(strainName + "%'").toString();
                searchDescription = (new StringBuilder()).append(searchDescription).append("strain name equals ").append(strainName).append(", ").toString();
            } else {

                whereClause = (new StringBuilder()).append(whereClause).append(" s.name LIKE '%").append(strainNameText + "%'").toString();
                searchDescription = (new StringBuilder()).append(searchDescription).append("strain name contains ").append(strainNameText).append(",").toString();
            }
        }

        if (commonStrainName.length() != 0 || commonStrainText.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            if (commonStrainName.length() != 0) {
                results = sm.getStrainsByCommName(commonStrainName);
                joinClause = (new StringBuilder()).append(joinClause).append(" JOIN s.syn_strainsDAO as ss ").toString();
                whereClause = (new StringBuilder()).append(whereClause).append(" ss.name LIKE '").append(commonStrainName + "%'").toString();
                searchDescription = (new StringBuilder()).append(searchDescription).append("common strain name equals ").append(commonStrainName).toString();
            } else {
                joinClause = (new StringBuilder()).append(joinClause).append(" JOIN s.syn_strainsDAO as ss ").toString();
                whereClause = (new StringBuilder()).append(whereClause).append(" ss.name LIKE '%").append(commonStrainText + "%'").toString();
                searchDescription = (new StringBuilder()).append(searchDescription).append("common strain name contains ").append(commonStrainText).toString();
            }
        }

        if (phenoDesc.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append(" s.pheno_text LIKE '%").append(phenoDesc + "%'").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("phenotype description like ").append(phenoDesc).toString();
        }

        ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


        if (rTools.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            joinClause = (new StringBuilder()).append(joinClause).append(" JOIN s.rtoolsDAO as rtool ").toString();
            whereClause = (new StringBuilder()).append(whereClause).append(" rtool.rtls_id = ").append(rTools).toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("R Tools equal ").append(rTools).append(", ").toString();
        }


        if (projects.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            joinClause = (new StringBuilder()).append(joinClause).append(" JOIN s.projectsDAO AS projects ").toString();
            whereClause = (new StringBuilder()).append(whereClause).append(" projects.project_id = ").append(projects).toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("projects equal ").append(projects).append(", ").toString();
        }


        if (mutantType.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            joinClause = (new StringBuilder()).append(joinClause).append(" JOIN s.mutationsStrainsDAO as ms ").toString();
            whereClause = (new StringBuilder()).append(whereClause).append(" ms.mutationsDAO.main_type = '").append(mutantType).append("'").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("Mutation main type equals ").append(mutantType).append(", ").toString();
        }
        if (mutantSubType.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            if (mutantType.length() == 0) {
                joinClause = (new StringBuilder()).append(joinClause).append(" JOIN s.mutationsStrainsDAO as ms ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append(" ms.mutationsDAO.sub_type = '").append(mutantSubType).append("'").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("Mutation sub type equals ").append(mutantSubType).append(", ").toString();
        }
        if (conCountry.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append(" s.peopleDAO.labsDAO.country = '").append(conCountry).append("'").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("Contact country equals ").append(conCountry).append(", ").toString();
        }
        if (sciSurname.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append(" s.peopleDAO.surname like '%").append(sciSurname).append("%'").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("Scientist surname equals ").append(sciSurname).append(", ").toString();
        }
        if (conSurname.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append(" s.peopleDAO.surname like '%").append(conSurname).append("%' AND s.per_id_per_contact=s.peopleDAO.id_per").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("Contact surname equals ").append(conSurname).append(", ").toString();
        }
        if (orderAvail.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            if (orderAvail.equals("no")) {
                whereClause = (new StringBuilder()).append(whereClause).append(" (s.available_to_order = 'no' OR available_to_order IS NULL) ").toString();
            } else {
                whereClause = (new StringBuilder()).append(whereClause).append(" s.available_to_order = 'yes' ").toString();
            }
            searchDescription = (new StringBuilder()).append(searchDescription).append("Available to order equals ").append(orderAvail).append(", ").toString();
        }
        if (mgiId.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append(" s.mgi_ref like '%").append(mgiId).append("%'").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("MGI ref contains ").append(mgiId).append(", ").toString();
        }
        /* Phil - Modified whereClauses as broken for range searches (too many ''AND's) 
         * and also incorrect positioning of AND in quesry string 31/10/2008 
         */
        if (subDateAfter.length() != 0) {
            System.out.println(subDateAfter);
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append(" s.archiveDAO.submitted > '").append(subDateAfter).append("'").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("Submitted after ").append(subDateAfter).append(", ").toString();
        }
        if (subDatebefore.length() != 0) {
            if (!"".equals(whereClause) || subDateAfter.length() != 0) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append("s.archiveDAO.submitted < '").append(subDatebefore).append("'").toString();//
            if (!"".equals(whereClause) || subDateAfter.length() != 0) {
                //we have an range search therefore add 'AND' to the equation
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append(" s.archiveDAO.submitted < '").append(subDatebefore).append("'").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("Submitted before ").append(subDatebefore).append(", ").toString();
        }
        if (evalDateAfter.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();//
            }
            whereClause = (new StringBuilder()).append(whereClause).append(" s.archiveDAO.evaluated > '").append(evalDateAfter).append("'").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("Evaluated after ").append(evalDateAfter).append(", ").toString();
        }
        if (evalDatebefore.length() != 0) {
            if (!"".equals(whereClause) || evalDateAfter.length() != 0) {
               // if (evalDateAfter.length() != 0) {
                    ///we have an range search therefore add 'AND' to the equation
                    // whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
              //  }
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();//
            }
            whereClause = (new StringBuilder()).append(whereClause).append(" s.archiveDAO.evaluated < '").append(evalDatebefore).append("'").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("Evaluated before ").append(evalDatebefore).append(", ").toString();
        }
        if (recDateAfter.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();//
            }
            whereClause = (new StringBuilder()).append(whereClause).append(" s.archiveDAO.received > '").append(recDateAfter).append("'").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("received after ").append(recDateAfter).append(", ").toString();
        }
        if (recDatebefore.length() != 0) {
            if (!"".equals(whereClause) || recDateAfter.length() != 0) {
             /*   if (recDateAfter.length() != 0) {
                    //we have an range search therefore add 'AND' to the equation
                    whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
                }*/
                 whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            whereClause = (new StringBuilder()).append(whereClause).append(" s.archiveDAO.received < '").append(recDatebefore).append("'").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("Received before ").append(recDatebefore).append(", ").toString();
        }
        if (archstartDateAfter.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();//
            }
            whereClause = (new StringBuilder()).append(whereClause).append(" s.archiveDAO.freezing_started > '").append(archstartDateAfter).append("'").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("Freezing started after ").append(archstartDateAfter).append(", ").toString();
        }
        if (archstartDatebefore.length() != 0) {
            if (!"".equals(whereClause) || archstartDateAfter.length() != 0) {
              //  if (archstartDateAfter.length() != 0) {
                    //we have an range search therefore add 'AND' to the equation
                    whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
              //  }
            }
            whereClause = (new StringBuilder()).append(whereClause).append(" s.archiveDAO.freezing_started < '").append(archstartDatebefore).append("'").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("Freezing started before ").append(archstartDatebefore).append(", ").toString();
        }
        if (archDateAfter.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();//
            }
            whereClause = (new StringBuilder()).append(whereClause).append(" s.archiveDAO.archived > '").append(archDateAfter).append("'").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("Archived after ").append(archDateAfter).append(", ").toString();
        }
        if (archDatebefore.length() != 0) {
            if (!"".equals(whereClause) || archDateAfter.length() != 0) {
               // if (archDateAfter.length() != 0) {
                    //we have an range search therefore add 'AND' to the equation
                    whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
               // }
            }
            whereClause = (new StringBuilder()).append(whereClause).append(" s.archiveDAO.archived < '").append(archDatebefore).append("'").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("Archived before ").append(archDatebefore).append(", ").toString();
        }



        if (codeInternal.length() != 0) {
            if (!"".equals(whereClause)) {
                whereClause = (new StringBuilder()).append(whereClause).append(" AND ").toString();
            }
            //              joinClause = (new StringBuilder()).append(joinClause).append(" JOIN s.archiveDAO as lab ").toString();
            whereClause = (new StringBuilder()).append(whereClause).append(" code_internal = '").append(codeInternal).append("' ").toString();
            searchDescription = (new StringBuilder()).append(searchDescription).append("code internal equals ").append(codeInternal).append(", ").toString();

        }




        String sql;
        if (!"".equals(whereClause)) {
            sql = (new StringBuilder()).append("SELECT s FROM StrainsDAO s ").append(joinClause).append(" WHERE ").append(whereClause).append(" ORDER BY emma_id").toString();
        } else {
            sql = "FROM StrainsDAO s ORDER BY emma_id";
        }

        System.out.println("DEBUG SQL ~~~ " + sql);
        results = null;
        results = sm.getStrains(sql);
        returnedOut.put("searchDescription", searchDescription);
        returnedOut.put("results", results);
        returnedOut.put("searchSize", Integer.valueOf(results.size()));
        //  }

        return new ModelAndView(getSuccessView(), "returnedOut", returnedOut);
    }

    public ModelAndView getIncludeView() {
        return includeView;
    }

    public void setIncludeView(ModelAndView includeView) {
        this.includeView = includeView;
    }

    public String getPathToMTA() {
        return pathToMTA;
    }

    public void setPathToMTA(String pathToMTA) {
        this.pathToMTA = pathToMTA;
    }
}