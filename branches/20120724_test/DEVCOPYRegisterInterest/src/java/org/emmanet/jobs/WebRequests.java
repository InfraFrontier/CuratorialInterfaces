/*
 * WebRequests.java
 *
 * Created on 07 December 2007, 10:09
 *
 * Essentially should live with model/DAOs acts upon
 * org.emmanet.model.AvailabilitiesStrainsDAO,
 * import org.emmanet.model.CVAvailabilitiesDAO,
 * import org.emmanet.model.WebRequestsDAO to query and return model to controllers.
 * Called by RegInterestJOB nightly.
 *
 * v1.0 released 04-02-2008
 *
 */
package org.emmanet.jobs;

import java.math.BigInteger;
import java.util.List;
import org.emmanet.model.Sources_RequestsDAO;
import org.emmanet.model.WebRequestsDAO;
import org.emmanet.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author phil
 */
public class WebRequests {

    public List webRequests(String emmaId) {
        List regInterestList = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

            /*   regInterestList = session.createQuery("FROM WebRequestsDAO WHERE strain_id = ?" +
            " AND register_interest IS NOT NULL" +
            "").setString(0, emmaId).list(); */
            regInterestList = session.createQuery("FROM WebRequestsDAO WHERE strain_id = ?" +
                    " AND register_interest=1 AND req_status !='CANC'" +
                    "").setString(0, emmaId).list();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return regInterestList;
    }

    public List ccArchiveMailAddresses(int str_id_str) {

        List ccArchive = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            ccArchive = session.createSQLQuery(
                    "SELECT DISTINCT people.lab_id_labo,people.e_mail " +
                    "FROM people, archive, strains, web_requests, roles_people " +
                    "WHERE strains.id_str = ? " +
                    "AND people.lab_id_labo = archive.lab_id_labo " +
                    "AND archive.id = strains.archive_id " +
                    "AND roles_people.per_id_per = people.id_per " +
                    "AND roles_people.role_id = 3 ").setInteger(0, str_id_str).list();

            /*      SELECT
            DISTINCT people.lab_id_labo,
            people.e_mail 
            FROM
            people,
            archive,
            strains,
            web_requests,
            roles_people 
            WHERE
            strains.id_str = 4605 
            AND people.lab_id_labo = archive.lab_id_labo 
            AND archive.id = strains.archive_id 
            AND roles_people.per_id_per = people.id_per 
            AND roles_people.role_id = 3 
             * 
             *                     "SELECT DISTINCT people.lab_id_labo,people.e_mail " +
            "FROM people, laboratories_strains, strains, web_requests, roles_people " +
            "WHERE laboratories_strains.str_id_str = ? " +
            "AND people.lab_id_labo = laboratories_strains.lab_id_labo " +
            "AND laboratories_strains.str_id_str = strains.id_str " +
            "AND roles_people.per_id_per = people.id_per " +
            "AND roles_people.role_id = 3 ").setInteger(0, str_id_str).list();
             * 
             * */


            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return ccArchive;
    }

    public List strainsUpdates(String shortDate, String fullDate) {
        List updatesList = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            updatesList = session.createQuery("from StrainsDAO where last_change" + " like '" + shortDate + "%' AND (str_access = 'P'" + " AND str_status = 'ARCHD' OR str_status = 'ARING' OR str_status = 'ARRD') AND (gp_release < '" + fullDate + "' or gp_release IS NULL" + " OR available_to_order = 'yes') " + "order by gp_release DESC").list();

            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return updatesList;
    }

    public List strainListArrd() {

        List strainList = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

            /* strainList = session.createQuery("from StrainsDAO where str_status = 'ARRD' OR str_status = 'ARING' AND str_access = 'P'").list();*/
            strainList = session.createQuery("from StrainsDAO where str_status IN ('ARRD', 'ARING', 'TNA') AND str_access = 'P'").list();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }


        return strainList;
    }

    public List requestByID(int requestId) {
        //System.out.println("statement >>" + requestId);
        List requestById = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

            requestById = session.createQuery("from WebRequestsDAO,StrainsDAO where id_req = " + requestId).list();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return requestById;
    }

    public WebRequestsDAO getReqByID(String id) {
        int ID = Integer.parseInt(id);//added 9/10/2008
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        WebRequestsDAO webReq = null;
        try {
            //webReq = (WebRequestsDAO) session.get(WebRequestsDAO.class, "3");
            webReq = (WebRequestsDAO) session.get(WebRequestsDAO.class,
                    id);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return webReq;
    }

    public List getAvailMaterial(String id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List avail = null;

        try {

            avail = session.createQuery(
                    "FROM WebRequestsDAO, AvailabilitiesStrainsDAO, CVAvailabilitiesDAO " +
                    "WHERE WebRequestsDAO.str_id_str=AvailabilitiesStrainsDAO.str_id_str " +
                    "AND AvailabilitiesStrainsDAO.avail_id=CVAvailabilitiesDAO.id " +
                    "AND WebRequestsDAO.id_req=1000").list();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return avail;
    }

    public List emmaArchCentres() {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List archives = null;

        try {

            archives = session.createQuery(
                    "FROM LabsDAO WHERE authority is not null").list();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return archives;//TODO return list
    }

    /* 
    
     *
     *count(*) returns big integer unable to cast to OBJECT HENCE CAST TO STRING AND RETURN STRING
     */
    public String labStrainCount(String id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        String labStrainCount = null;

        try {

            BigInteger bi = (BigInteger) session.createSQLQuery(
                    "SELECT COUNT(*) FROM strains, archive " +
                    "WHERE id_str = str_id_str " +
                    "AND lab_id_labo = ? " +
                    "AND (str_status = 'ACCD' " +
                    "OR str_status='ARING' " +
                    "OR str_status='ARRD' " +
                    "OR str_status='ARCHD' " +
                    "OR str_status='TNA')").setString(0, id).uniqueResult();
            String count = bi.toString();
            //System.out.println("String = " + count);
            labStrainCount = count;
            //System.out.println("Strain Count = " + labStrainCount[0]);
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return labStrainCount;
    }

    public String labPublicStrainCount(String id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String labPublicStrainCount = null;

        try {

            BigInteger bi = (BigInteger) session.createSQLQuery(
                    "SELECT count(*) FROM strains, archive " +
                    "WHERE id_str = str_id_str " +
                    "AND lab_id_labo = ? " +
                    "AND str_status='ARCHD'").setString(0, id).uniqueResult();
            labPublicStrainCount = bi.toString();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return labPublicStrainCount;
    }

    public String labStrainsArrd(String id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String labArrdStrainCount = null;

        try {
            BigInteger bi = (BigInteger) session.createSQLQuery(
                    "SELECT count(*) FROM strains, archive " +
                    "WHERE id_str = str_id_str " +
                    "AND lab_id_labo = ? " +
                    "AND (str_status='ARING' OR str_status='ARRD')").setString(0, id).uniqueResult();
            labArrdStrainCount = bi.toString();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return labArrdStrainCount;

    }

    public String labStrainsNotArrd(String id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String labNotArrdStrainCount = null;

        try {

            BigInteger bi = (BigInteger) session.createSQLQuery(
                    "SELECT count(*) FROM strains, archive " +
                    "WHERE id_str = str_id_str " +
                    "AND lab_id_labo = ? " +
                    "AND str_status='ACCD'").setString(0, id).uniqueResult();
            labNotArrdStrainCount = bi.toString();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return labNotArrdStrainCount;
    }

    public Object[] labServiceTimes(String id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Object serviceTimes[];

        try {
            /*
             * Using native sql query here as cannot get this query working with hql
             * if anyone is able to resolve this then I'd be grateful to hear how it is done.
             */

            serviceTimes = (Object[]) session.createSQLQuery(
                    "select FORMAT((AVG(TO_DAYS(archive.archived)-TO_DAYS(archive.submitted))),2) " +
                    "as timetoarchive, " +
                    "FORMAT((AVG(TO_DAYS(archive.freezing_started)-TO_DAYS(archive.submitted))),2) " +
                    "as freezingtime " +
                    "from archive " +
                    "where lab_id_labo=? " +
                    "and archived IS NOT NULL").setString(0, id).uniqueResult();//list();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return serviceTimes;//TODO return list
    }

    public List requestsPerLab(String lab_id_labo) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List labRequests = null;
        int lab_id = Integer.parseInt(lab_id_labo);
        try {

            labRequests = session.createQuery(" from WebRequestsDAO " +
                    "where lab_id_labo = ?").setInteger(0, lab_id).list();/*.setString(0, lab_id_labo).list();*/

            session.getTransaction().commit();
//WebRequestsDAO
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return labRequests;
    }

    public List requestsByCountry(int currYear) {
        List reqByCountry = null;
        String whereSql = "";
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        if (currYear != 0) {
            //Where query
            //emma PROJECT YEAR 01JUL - 30JUN
            int lastYear = currYear - 1;
            whereSql = "WHERE timestamp " +
                    "BETWEEN '" + lastYear + "-07-01' " +
                    "AND '" + currYear + "-06-30' ";
        }
        try {
            reqByCountry = session.createSQLQuery(
                    "SELECT count(con_country), con_country " +
                    "FROM web_requests " +
                    whereSql +
                    "GROUP BY con_country " +
                    "ORDER BY count(con_country) desc, con_country").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return reqByCountry;
    }

    public List requestsFrozen(String type, int year) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List requestsFrozen = null;
        String query = null;
        if (type.equals("all")) {
            query = "FROM WebRequestsDAO " +
                    "WHERE req_material like '%frozen%'";
        //WebRequestsDAO
        } else {
            //Year query
            query = " FROM WebRequestsDAO " +
                    "WHERE req_material like '%frozen%' " +
                    "and date_processed like '" + year + "%'";
        //WebRequestsDAO
        }
        try {

            requestsFrozen = session.createQuery(query /*"from WebRequestsDAO " +
                    "where req_material like '%frozen%'"?*/).list();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return requestsFrozen;
    }

    public List requestsLive(String type, int year) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List requestsLive = null;
        String query = null;
        /* QUERY CHANGED TO ID_REQ SELECT FROM * TO PREVENT HIBERNATE DIALECT TYPE ERROR ON UNKNOWN FIELD */
        if (type.equals("all")) {
            query = "SELECT id_req from web_requests " +
                    "where req_material like '%live%'";
        } else {
            //Year query
            query = "SELECT id_req from web_requests " +
                    "where req_material like '%live%' " +
                    "and date_processed like '" + year + "%'";
        }
        //WebRequestsDAO

        try {
            System.out.println("DEBUGGER1.3");
            requestsLive = session.createSQLQuery(query).list();
            System.out.println("DEBUGGER1.1");
            session.getTransaction().commit();
            System.out.println("DEBUGGER1");

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        System.out.println("DEBUGGER2");
        return requestsLive;
    }

    public String submissionsPerYear(String currYear) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        long submissionsCount;
        String s;
        try {

            submissionsCount = (Long) session.createQuery(
                    "SELECT count(*) FROM ArchiveDAO " +
                    "WHERE submitted like ?").setString(0, currYear).uniqueResult();
            session.getTransaction().commit();
            s = "" + submissionsCount + "";

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return s;
    }

    public List top5Strains() {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List top5 = null;

        try {

            top5 = session.createQuery(
                    "from WebRequestsDAO " +
                    "group by strain_id " +
                    "order by count(strain_id) desc").list();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return top5;//TODO return list
    }

    public List top5ROI() {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List top5ROI = null;

        try {

            top5ROI = session.createQuery(
                    "from WebRequestsDAO " +
                    "where register_interest = 1 " +
                    "group by strain_id " +
                    "order by count(strain_id) descgoogle.co.uk" +
                    "#").list();//   order by count desc // TODO why is this not liked by hql??count(strain_id)
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return top5ROI;//TODO return list
    }

    public String reqsCurrProjectYear(String date) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String reqMonthCount = null;

        try {

            BigInteger bi = (BigInteger) session.createSQLQuery(
                    "SELECT count(*) FROM web_requests " +
                    "WHERE timestamp like ?").setString(0, date).uniqueResult();

            reqMonthCount = bi.toString();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return reqMonthCount;
    }
    //////////////////////////////////////////////////////////////////////
    // CREATE QUERY FOR SPREADSHEETS
    /////////////////////////////////////////////////////////////////////
    public List spreadSheetStrainsTbl() {
        List strainTable = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

            strainTable = session.createQuery("from StrainsDAO").list();

            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return strainTable;
    }
    //QUERY FOR COL NAMES IN STRAINS TABLE
    //TODO Prob better to use  hibernate QueryTranslator??
    public List colsStrainsTbl() {
        //System.out.println("Q U E R Y I N G ! !");
        List strainCols = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

            strainCols = session.createSQLQuery("SELECT COLUMN_NAME" +
                    " FROM information_schema.COLUMNS C " +
                    "WHERE table_name = 'strains'").list();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return strainCols;
    }

    public List reqStatus() {
        //System.out.println("Q U E R Y I N G ! !");
        List reqStatus = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

            /* reqStatus = session.createSQLQuery("SELECT DISTINCT req_status" +
            " FROM web_requests")
            .list();
            session.getTransaction().commit();*/

            reqStatus = session.createQuery("SELECT DISTINCT req_status" +
                    " FROM WebRequestsDAO").list();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return reqStatus;
    }

    public List countries() {
        //System.out.println("Q U E R Y I N G ! !");
        List conCountry = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

            /* reqStatus = session.createSQLQuery("SELECT DISTINCT req_status" +
            " FROM web_requests")
            .list();
            session.getTransaction().commit();*/

            conCountry = session.createQuery("SELECT DISTINCT con_country" +
                    " FROM WebRequestsDAO ORDER BY  con_country").list();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return conCountry;
    }
    
        public List isoCountries() {

        List isoCountries = null;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            isoCountries = session.createSQLQuery("SELECT printable_name " +
                    "FROM cv_countries " +
                    "ORDER BY printable_name").list();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return isoCountries;
    }

    public List emmaStrains() {
        // Used by statistics spreadsheet, sheet emmaStrains
        List emmaStrains = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

            emmaStrains = session.createSQLQuery("SELECT mutations.main_type, mutations.sub_type, " +
                    "strains.emma_id, strains.name, genes.name " +
                    "FROM strains, mutations, genes, alleles " +
                    "WHERE strains.id_str = mutations.str_id_str " +
                    "AND mutations.alls_id_allel = alleles.id_allel " +
                    "AND alleles.gen_id_gene = genes.id_gene " +
                    "AND str_access = 'P' " +
                    "ORDER BY mutations.main_type, mutations.sub_type") /*.addEntity("genes").addEntity("strains")*/.list();
            // IN ('P')
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return emmaStrains;
    }

    /* QUERIES USED BY INTERFACES */
    public List getReqsByEmmaID(String emmaID) {

        List reqsByID = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            /* reqsByID = session.createQuery("FROM WebRequestsDAO " +
            "WHERE strain_id like  '" + emmaID + "%' " +
            "ORDER BY strain_id").list(); */
            reqsByID = session.createSQLQuery("SELECT id_req,strain_id,code," +
                    "strains.name,sci_e_mail, sci_firstname,sci_surname,req_status," +
                    "str_access ,available_to_order,register_interest,id_str,timestamp,lab_id_labo,con_country,date_processed,notes,str_status " +
                    "FROM web_requests,strains,laboratories " +
                    "WHERE web_requests.str_id_str = strains.id_str " +
                    "AND laboratories.id_labo=web_requests.lab_id_labo " +
                    "AND strains.emma_id like  '" + emmaID + "%' " +
                    "ORDER BY req_status DESC,timestamp DESC,strain_id").list();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return reqsByID;
    }

    public List getReqsByComName(String str) {

        List reqsByComName = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

            /*    reqsByComName = session.createQuery("FROM WebRequestsDAO " +
            "WHERE common_name_s like ? " +
            "ORDER BY strain_id").setString(0, str).list();*/
            reqsByComName = session.createSQLQuery("SELECT id_req,strain_id,code," +
                    "strains.name,sci_e_mail, sci_firstname,sci_surname,req_status," +
                    "str_access ,available_to_order,register_interest,id_str " +
                    "FROM web_requests,strains,laboratories, syn_strains " +
                    "WHERE web_requests.str_id_str = strains.id_str " +
                    "AND laboratories.id_labo=web_requests.lab_id_labo " +
                    "AND syn_strains.str_id_str=strains.id_str " +
                    "AND (strains.code_internal like '" + str + "%'" +
                    "OR syn_strains.name like '" + str + "%')").list();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return reqsByComName;
    }

    public List getReqsByStrainName(String str) {

        List reqsByStrainName = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

            reqsByStrainName = session.createSQLQuery("SELECT id_req,strain_id,code," +
                    "strains.name,sci_e_mail, sci_firstname,sci_surname,req_status," +
                    "str_access ,available_to_order,register_interest,id_str " +
                    "FROM web_requests,strains,laboratories " +
                    "WHERE web_requests.str_id_str = strains.id_str " +
                    "AND laboratories.id_labo=web_requests.lab_id_labo " +
                    "AND strains.name like '" + str + "%'").list();

            /* session.createQuery("FROM WebRequestsDAO " +
            "WHERE strains.name like '%" + str + "%'" +
            "ORDER BY strain_id").list();*/
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return reqsByStrainName;
    }

    public List getLiveReqsByCentre(String str) {

        List reqsByCentre = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

            reqsByCentre = session.createSQLQuery("SELECT id_req,strain_id,code," +
                    "strains.name,sci_e_mail, sci_firstname,sci_surname,req_status," +
                    "str_access ,available_to_order,register_interest,id_str,timestamp,con_country,con_country,date_processed,notes,str_status " +
                    "FROM web_requests,strains,laboratories " +
                    "WHERE web_requests.str_id_str = strains.id_str " +
                    "AND laboratories.id_labo=web_requests.lab_id_labo " +
                    "AND req_status IN('IN_PR','TO_PR') " +
                    "AND code ='" + str.toUpperCase() +
                    "' ORDER BY req_status DESC,timestamp DESC,strain_id").list();

            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return reqsByCentre;
    }

    /*
    public List getReqsByCentre(String str) {
    
    List reqsByCentre = null;
    
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    try {
    
    reqsByCentre = session.createSQLQuery("SELECT id_req,strain_id,code," +
    "strains.name,sci_e_mail, sci_firstname,sci_surname,req_status," +
    "str_access ,available_to_order,register_interest,id_str " +
    "FROM web_requests,strains,laboratories " +
    "WHERE web_requests.str_id_str = strains.id_str " +
    "AND laboratories.id_labo=web_requests.lab_id_labo " +
    "AND lab_id_labo =" + str).list();
    
    session.getTransaction().commit();
    
    } catch (HibernateException e) {
    session.getTransaction().rollback();
    throw e;
    }
    
    return reqsByCentre;
    }
    
    public List getReqsByROI(String str) {
    
    List reqsByCentre = null;
    
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    try {
    
    reqsByCentre = session.createSQLQuery("SELECT id_req,strain_id,code," +
    "strains.name,sci_e_mail, sci_firstname,sci_surname,req_status," +
    "str_access ,available_to_order,register_interest,id_str " +
    "FROM web_requests,strains,laboratories " +
    "WHERE web_requests.str_id_str = strains.id_str " +
    "AND laboratories.id_labo=web_requests.lab_id_labo " +
    "AND (register_interest =" + str + ")").list();
    
    session.getTransaction().commit();
    
    } catch (HibernateException e) {
    session.getTransaction().rollback();
    throw e;
    }
    
    return reqsByCentre;
    }
    
    public List getReqsByCountry(String str) {
    
    List reqsByCountry = null;
    
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    try {
    
    reqsByCountry = session.createSQLQuery("SELECT id_req,strain_id,code," +
    "strains.name,sci_e_mail, sci_firstname,sci_surname,req_status," +
    "str_access ,available_to_order,register_interest,id_str " +
    "FROM web_requests,strains,laboratories " +
    "WHERE web_requests.str_id_str = strains.id_str " +
    "AND laboratories.id_labo=web_requests.lab_id_labo " +
    "AND con_country = '" + str + "'").list();
    session.getTransaction().commit();
    
    } catch (HibernateException e) {
    session.getTransaction().rollback();
    throw e;
    }
    
    return reqsByCountry;
    }
     */
    public List getReqs(String sql) {

        List reqs = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

            reqs = session.createSQLQuery(sql).list();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return reqs;
    }

    /*
    public List getReqsByStatus(String str) {
    
    List reqsByStatus = null;
    
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    try {
    
    reqsByStatus = session.createSQLQuery("SELECT id_req,strain_id,code," +
    "strains.name,sci_e_mail, sci_firstname,sci_surname,req_status," +
    "str_access ,available_to_order,register_interest,id_str " +
    "FROM web_requests,strains,laboratories " +
    "WHERE web_requests.str_id_str = strains.id_str " +
    "AND laboratories.id_labo=web_requests.lab_id_labo " +
    "AND req_status = '" + str + "'").list();
    session.getTransaction().commit();
    
    } catch (HibernateException e) {
    session.getTransaction().rollback();
    throw e;
    }
    
    return reqsByStatus;
    }
    
    public List getReqsBySurname(String str, String field) {
    
    List reqsBySurname = null;
    
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    try {
    
    reqsBySurname = session.createSQLQuery("SELECT id_req,strain_id,code," +
    "strains.name,sci_e_mail, sci_firstname,sci_surname,req_status," +
    "str_access ,available_to_order,register_interest,id_str " +
    "FROM web_requests,strains,laboratories " +
    "WHERE web_requests.str_id_str = strains.id_str " +
    "AND laboratories.id_labo=web_requests.lab_id_labo " +
    "AND " + field + " like '" + str + "%'").list();
    session.getTransaction().commit();
    
    } catch (HibernateException e) {
    session.getTransaction().rollback();
    throw e;
    }
    
    return reqsBySurname;
    }
    
    
    public List getReqsBySubShipDate(String str, String field) {
    
    List reqsBySurname = null;
    
    Session session = HibernateUtil.getSessionFactory().getCurrentSession();
    session.beginTransaction();
    try {
    
    reqsBySurname = session.createSQLQuery("SELECT id_req,strain_id,code," +
    "strains.name,sci_e_mail, sci_firstname,sci_surname,req_status," +
    "str_access ,available_to_order,register_interest,id_str " +
    "FROM web_requests,strains,laboratories " +
    "WHERE web_requests.str_id_str = strains.id_str " +
    "AND laboratories.id_labo=web_requests.lab_id_labo " +
    "AND " + field + " " + str).list();
    session.getTransaction().commit();
    
    } catch (HibernateException e) {
    session.getTransaction().rollback();
    throw e;
    }
    
    return reqsBySurname;
    }
     */
    public List getReqsByLabIDandStatus(int id) {

        List reqs = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

            reqs = session.createQuery("FROM WebRequestsDAO WHERE lab_id_labo=?" +
                    " AND req_status='IN_PR' OR req_status='TO_PR' " +
                    "ORDER BY timestamp,req_status DESC").setParameter(0, id).list();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return reqs;
    }

    public List getEUCOMMReqsByCountry(int projectID) {

        List queryResults = null;
//checked OK
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

         /*   queryResults = session.createSQLQuery("SELECT con_country, count( id_req ) " +
                    "FROM web_requests r, strains s, rtools_strains ps " +
                    "WHERE (r.id_req) NOT IN (  " +
                    "SELECT 1 FROM web_requests " +
                    "WHERE sci_e_mail = r.sci_e_mail " +
                    "AND str_id_str = r.str_id_str " +
                    "HAVING COUNT( 1 ) > 1 " +
                    ") " +
                    "AND s.id_str = r.str_id_str " +
                    "AND s.id_str = ps.str_id_str " +
                    "AND rtls_id =? " +
                    "AND con_country IS NOT NULL " +
                    "GROUP BY con_country " +
                    "ORDER BY con_country").setParameter(0, projectID).list();*/
            queryResults = session.createSQLQuery("SELECT con_country, count( id_req ) " +
                    "FROM web_requests r, strains s, rtools_strains ps " +
                    "WHERE "/*id_req IN (" +
                    "SELECT MIN(id_req) " +
                    "FROM web_requests w, rtools_strains rs " +
                    "WHERE w.str_id_str=rs.str_id_str " +
                    "AND rtls_id=9 " +
                    "GROUP BY w.str_id_str, sci_e_mail) " +
                    "AND */ + "s.id_str = r.str_id_str " +
                    "AND s.id_str = ps.str_id_str " +
                    "AND  rtls_id =? " +
                    "AND con_country IS NOT NULL " +
                    "GROUP BY con_country " +
                    "ORDER BY con_country").setParameter(0, projectID).list();
            session.getTransaction().commit();
            
            /*
             * 
             * WHERE id_req IN (SELECT id_req FROM web_requests w, rtools_strains rs WHERE w.str_id_str=rs.str_id_str AND rtls_id=9 GROUP BY w.str_id_str, sci_e_mail) 
             *  "WHERE EXISTS (SELECT 1 FROM web_requests WHERE sci_e_mail = r.sci_e_mail AND str_id_str = r.str_id_str HAVING COUNT( 1 ) =1) AND s.id_str = r.str_id_str " +
             
             EXISTS (SELECT 1 FROM web_requests WHERE sci_e_mail = r.sci_e_mail AND str_id_str = r.str_id_str HAVING COUNT( 1 ) =1) AND 
             *SELECT con_country, count( id_req ) FROM web_requests r, strains s, rtools_strains ps WHERE EXISTS (  SELECT 1 FROM web_requests WHERE sci_e_mail = r.sci_e_mail AND str_id_str = r.str_id_str HAVING COUNT( 1 ) = 1 ) AND s.id_str = r.str_id_str AND s.id_str = ps.str_id_str AND rtls_id =9 AND con_country IS NOT NULL GROUP BY con_country ORDER BY con_country;
             
             */

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return queryResults;
    }

    public List getEUCOMMReqsByMonthYear(int projectID) {

        List queryResults = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

            queryResults = session.createSQLQuery("SELECT DATE_FORMAT( timestamp,'%b' ) , year( timestamp ) , count( id_req ) " +
                    "FROM web_requests r, strains s, rtools_strains ps " +
                    "WHERE s.id_str = r.str_id_str " +
                    "AND s.id_str = ps.str_id_str " +
                    "AND rtls_id =? " +
                    "AND timestamp IS NOT NULL " +
                    "GROUP BY year(timestamp),month( timestamp ) " +
                    "ORDER BY timestamp").setParameter(0, projectID).list();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return queryResults;
    }

    public List getEUCOMMShippedByMonthYear(int projectID) {

        List queryResults = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

            queryResults = session.createSQLQuery("select DATE_FORMAT(date_processed, '%b'), year(date_processed) " +
                    "from laboratories l, web_requests r, strains s, rtools_strains ps " +
                    "where r.lab_id_labo=l.id_labo " +
                    "and s.id_str=r.str_id_str " +
                    "and s.id_str=ps.str_id_str " +
                    "and rtls_id = ? " +
                    "and date_processed is not null " +
                    "AND req_status='SHIP' " +
                    "GROUP BY year(date_processed),month( date_processed ) " +
                    "order by date_processed").setParameter(0, projectID).list();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return queryResults;
    }

    public List getEUCOMMReqsByMonthYearCentre(String month, String year, int projectID) {

        List queryResults = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

            /*    queryResults = session.createSQLQuery("SELECT l.code, l.name, date_format( timestamp, '%b' ) , year( timestamp ) , count( id_req ) " +
            "FROM laboratories l, web_requests r, strains s, projects_strains ps " +
            "WHERE r.lab_id_labo = l.id_labo " +
            "AND s.id_str = r.str_id_str " +
            "AND s.id_str = ps.str_id_str " +
            "AND project_id =3 " +
            "AND timestamp IS NOT NULL " +
            "GROUP BY date_format( timestamp, '%b' ) , lab_id_labo " +
            "ORDER BY timestamp, code").list();*/
        /*    queryResults = session.createSQLQuery("SELECT l.code, l.name, date_format( timestamp, '%b' ) , year( timestamp ) , count( id_req ) " +
                    "FROM laboratories l, web_requests r, strains s, rtools_strains ps " +
                    "WHERE (r.id_req) NOT IN  (" +
                    "SELECT 1 " +
                    "FROM web_requests " +
                    "WHERE sci_e_mail = r.sci_e_mail " +
                    "AND str_id_str = r.str_id_str " +
                    "HAVING COUNT( 1 ) > 1" +
                    ") " +
                    "AND r.lab_id_labo = l.id_labo " +
                    "AND s.id_str = r.str_id_str " +
                    "AND s.id_str = ps.str_id_str " +
                    "AND rtls_id =? " +
                    "AND date_format( timestamp, '%b' ) = ? " +
                    "AND YEAR( timestamp ) = ? " +
                    "GROUP BY date_format( timestamp, '%b' ) , year(timestamp), lab_id_labo " +
                    "ORDER BY timestamp, code").setString(1, month).setString(2, year).setParameter(0, projectID).list();*/
            
             queryResults = session.createSQLQuery("SELECT l.code, l.name, date_format( timestamp, '%b' ) , year( timestamp ) , count( id_req ) " +
                    "FROM laboratories l, web_requests r, strains s, rtools_strains ps " +
                    "WHERE "/*id_req IN (SELECT MIN(id_req) FROM web_requests w, rtools_strains rs WHERE w.str_id_str=rs.str_id_str AND rtls_id=9 GROUP BY w.str_id_str, sci_e_mail) " +
                    "AND*/+ "r.lab_id_labo = l.id_labo " +
                    "AND s.id_str = r.str_id_str " +
                    "AND s.id_str = ps.str_id_str " +
                    "AND rtls_id =? " +
                    "AND date_format( timestamp, '%b' ) = ? " +
                    "AND YEAR( timestamp ) = ? " +
                    "GROUP BY date_format( timestamp, '%b' ) , year(timestamp), lab_id_labo " +
                    "ORDER BY timestamp, code").setString(1, month).setString(2, year).setParameter(0, projectID).list();


            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return queryResults;
    }

    public List getEUCOMMReqsPopular(int projectID) {

        List queryResults = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            queryResults = session.createSQLQuery("SELECT s.emma_id,s.name,s.code_internal,count(id_req),s.str_status,l.code " +
                    "FROM laboratories l, web_requests r, strains s, rtools_strains ps " +
                    "WHERE "/*id_req IN (SELECT MIN(id_req) FROM web_requests w, rtools_strains rs WHERE w.str_id_str=rs.str_id_str AND rtls_id=9 GROUP BY w.str_id_str, sci_e_mail) AND */ + "r.lab_id_labo = l.id_labo " +
                    "AND s.id_str = r.str_id_str " +
                    "AND s.id_str = ps.str_id_str " +
                    "AND rtls_id =? " +
                    "AND timestamp IS NOT NULL " +
                    "GROUP BY s.emma_id " +
                    "ORDER BY count(id_str) DESC").setParameter(0, projectID).list();

            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return queryResults;
    }

    public List getEUCOMMShippedByMonthYearCentre(String month, String year, int projectID) {

        List queryResults = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {


            queryResults = session.createSQLQuery(" SELECT l.code, l.name, month( date_processed ) , year( date_processed ),count( id_req ) " +
                    "FROM laboratories l, web_requests r, strains s, rtools_strains ps " +
                    "WHERE r.lab_id_labo = l.id_labo " +
                    "AND s.id_str=r.str_id_str " +
                    "AND s.id_str=ps.str_id_str " +
                    "AND rtls_id = ? " +
                    "AND date_format( date_processed, '%b' ) = ? " +
                    "AND YEAR( date_processed ) = ? " +
                    "AND req_status='SHIP' " +
                    "GROUP BY DATE_FORMAT(date_processed, '%b'),year(date_processed),lab_id_labo,req_status " +
                    "ORDER BY date_processed, lab_id_labo").setParameter(0, projectID).setString(1, month).setString(2, year).list();

            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return queryResults;
    }

    public List getEUCOMMMonthYear(int projectID) {

        List queryResults = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {


            queryResults = session.createSQLQuery("SELECT date_format( timestamp, '%b' ) , year( timestamp ) " +
                    "FROM laboratories l, web_requests r, strains s, rtools_strains ps " +
                    "WHERE r.lab_id_labo = l.id_labo " +
                    "AND s.id_str = r.str_id_str " +
                    "AND s.id_str = ps.str_id_str " +
                    "AND rtls_id =? " +
                    "AND timestamp IS NOT NULL " +
                    "GROUP BY year(timestamp),date_format( timestamp, '%b' ) " +
                    "ORDER BY timestamp").setParameter(0, projectID).list();

            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return queryResults;
    }

    public int getEUCOMMEMMACountsbyMonth(String month, String year, String andClause, String dateField, int projectID) {

        int queryResults;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {


            BigInteger bi = (BigInteger) session.createSQLQuery("SELECT count( * ) " +
                    "FROM laboratories l, web_requests r, strains s, rtools_strains ps " +
                    "WHERE r.lab_id_labo = l.id_labo " +
                    "AND s.id_str = r.str_id_str " +
                    "AND s.id_str = ps.str_id_str " +
                    andClause +
                    " AND date_format( " + dateField + ", '%b' ) = ? " +
                    "AND YEAR( " + dateField + " ) = ? "/* +
                    " " +
                    "GROUP BY r.sci_e_mail,r.str_id_str "*/).setString(0, month).setString(1, year).uniqueResult();//AND req_status='SHIP'

            session.getTransaction().commit();
            queryResults = Integer.parseInt(bi.toString());

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return queryResults;
    }

    public List getEUCOMMEMMADistTimeCurrent() {

        List Results;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
//OK DUPLICATES REMOVED
            Results = session.createSQLQuery("SELECT l.code, s.emma_id,year(timestamp), datediff(now(),timestamp) AS Days " +
                    "FROM web_requests r, strains s, rtools_strains ps,laboratories l " +
                    "WHERE s.id_str=r.str_id_str " +
                    "AND s.id_str=ps.str_id_str " +
                    "AND  r.lab_id_labo = l.id_labo " +
                    "AND rtls_id = 9 " +
                    "AND timestamp IS NOT NULL " +
                    "AND req_status NOT IN ('SHIP','CANC') " +
                    "GROUP BY r.sci_e_mail,r.str_id_str " +
                    "ORDER BY Days DESC").list();

            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return Results;
    }

    public List getEUCOMMEMMADistTimeShipped() {
//checked OK DUPLICATES REMOVED
        List Results;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {


            Results = session.createSQLQuery("SELECT l.code, s.emma_id,year(timestamp), datediff(date_processed,timestamp) AS Days " +
                    "FROM web_requests r, strains s, rtools_strains ps,laboratories l " +
                    "WHERE s.id_str=r.str_id_str " +
                    "AND s.id_str=ps.str_id_str " +
                    "AND  r.lab_id_labo = l.id_labo " +
                    "AND rtls_id = 9 " +
                    "AND date_processed IS NOT NULL " +
                    "AND req_status = 'SHIP' " +
                    "GROUP BY r.sci_e_mail,r.str_id_str " +
                    "ORDER BY Days DESC").list();

            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return Results;
    }

    public int getEUCOMMEMMAShippedCounts(String LabCode, int projectID) {
//checked ok no dup difference
        int queryResults;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            BigInteger bi = (BigInteger) session.createSQLQuery("SELECT count(id_req) " +
                    "FROM laboratories l, web_requests r, strains s, rtools_strains ps " +
                    "WHERE "/*id_req IN (" +
                    "SELECT MIN(id_req) " +
                    "FROM web_requests w, rtools_strains rs " +
                    "WHERE w.str_id_str=rs.str_id_str " +
                    "AND rtls_id=9 " +
                    "GROUP BY w.str_id_str, sci_e_mail) " +
                    "AND */ + "r.lab_id_labo = l.id_labo " +
                    "AND s.id_str=r.str_id_str " +
                    "AND s.id_str=ps.str_id_str " +
                    "AND rtls_id = ? " +
                    "AND l.code=? " +
                    "AND req_status='SHIP' " +
                    "AND date_processed IS NOT NULL ").setString(1, LabCode).setInteger(0, projectID).uniqueResult();

            session.getTransaction().commit();
            queryResults = Integer.parseInt(bi.toString());

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        System.out.println("BIGINt=" + queryResults);
        return queryResults;
    }

    public int getEUCOMMEMMAReqCounts(String LabCode, int projectID, boolean cancReqs) {
//checked ok
        int queryResults;
        String sqlClause = "";
        if (cancReqs) {
            sqlClause = " AND req_status IN ('CANC')";
        }
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            BigInteger bi = (BigInteger) session.createSQLQuery("SELECT count(id_req) " +
                    "FROM laboratories l, web_requests r, strains s, rtools_strains ps " +
                    "WHERE "/*id_req IN (" +
                    "SELECT MIN(id_req) " +
                    "FROM web_requests w, rtools_strains rs " +
                    "WHERE w.str_id_str=rs.str_id_str " +
                    "AND rtls_id=9 " +
                    "GROUP BY w.str_id_str, sci_e_mail) " +
                    "AND */ + "r.lab_id_labo = l.id_labo " +
                    "AND s.id_str = r.str_id_str " +
                    "AND s.id_str = ps.str_id_str " +
                    "AND rtls_id =? " +
                    "AND l.code=? " +
                    "AND timestamp IS NOT NULL" +
                    sqlClause 
                    /*" GROUP BY r.sci_e_mail,r.str_id_str "*/).setInteger(0, projectID).setString(1, LabCode).uniqueResult();


            session.getTransaction().commit();
            queryResults = Integer.parseInt(bi.toString());
            System.out.println("QueryResults==" + queryResults + " rtlsid=" + projectID + " labcode=" + LabCode);

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return queryResults;
    }

  /*  public int getEUCOMMEMMADuplicatedReqCounts(String LabCode, int projectID) {

        int queryResults;
        String sqlClause = "";

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
//checked ok     - COUNT( DISTINCT sci_e_mail )
          /*  BigInteger bi = (BigInteger) session.createSQLQuery("SELECT COUNT( id_req ) " +
                    "FROM laboratories l, web_requests r, strains s, rtools_strains ps " +
                    "WHERE (r.id_req) NOT IN  (" +
                    "SELECT 1 " +
                    "FROM web_requests " +
                    "WHERE sci_e_mail = r.sci_e_mail " +
                    "AND str_id_str = r.str_id_str " +
                    "HAVING COUNT( 1 ) > 1" +
                    ")" +
                    "AND r.lab_id_labo = l.id_labo " +
                    "AND s.id_str = r.str_id_str " +
                    "AND s.id_str = ps.str_id_str " +
                    "AND rtls_id =? " +
                    "AND l.code=? " +
                    "AND timestamp IS NOT NULL").setInteger(0, projectID).setString(1, LabCode).uniqueResult();*/
            
           /*  BigInteger bi = (BigInteger) session.createSQLQuery("SELECT COUNT( id_req ) " +
                    "FROM laboratories l, web_requests r, strains s, rtools_strains ps " +
                    "WHERE id_req IN (SELECT MIN(id_req) FROM web_requests w, rtools_strains rs WHERE w.str_id_str=rs.str_id_str AND rtls_id=9 GROUP BY w.str_id_str, sci_e_mail) AND r.lab_id_labo = l.id_labo " +
                    "AND s.id_str = r.str_id_str " +
                    "AND s.id_str = ps.str_id_str " +
                    "AND rtls_id =? " +
                    "AND l.code=? " +
                    "AND timestamp IS NOT NULL").setInteger(0, projectID).setString(1, LabCode).uniqueResult();


            session.getTransaction().commit();
            queryResults = Integer.parseInt(bi.toString());

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return queryResults;
    }
*/
  /*  public int getEUCOMMEMMADuplicatedReqCountsByMonth(String LabCode, int projectID, String month, String year) {

        int queryResults;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

            /*BigInteger bi = (BigInteger) session.createSQLQuery("SELECT COUNT(id_req)-COUNT(DISTINCT sci_e_mail) " +
                    "AS Corrected " +
                    "FROM laboratories l, web_requests r, strains s, rtools_strains ps " +
                    "WHERE (r.id_req) NOT IN  (  " +
                    "SELECT 1 FROM web_requests " +
                    "WHERE sci_e_mail = r.sci_e_mail AND str_id_str = r.str_id_str " +
                    "HAVING COUNT( 1 ) >1" +
                    " ) " +
                    "AND r.lab_id_labo = l.id_labo " +
                    "AND s.id_str = r.str_id_str " +
                    "AND s.id_str = ps.str_id_str " +
                    "AND rtls_id =? AND date_format(r.timestamp, '%b' ) = ? " +
                    "AND YEAR(r.timestamp) = ? " +
                    "AND l.code = ? " +
                    "AND timestamp IS NOT NULL").setInteger(0, projectID).setString(1, month).setString(2, year).setString(3, LabCode).uniqueResult();*/
            
         /*    BigInteger bi = (BigInteger) session.createSQLQuery("SELECT COUNT(id_req)-COUNT(DISTINCT sci_e_mail) " +
                    "AS Corrected " +
                    "FROM laboratories l, web_requests r, strains s, rtools_strains ps " +
                    "WHERE id_req IN (SELECT MIN(id_req) FROM web_requests w, rtools_strains rs WHERE w.str_id_str=rs.str_id_str AND rtls_id=9 GROUP BY w.str_id_str, sci_e_mail) AND r.lab_id_labo = l.id_labo " +
                    "AND s.id_str = r.str_id_str " +
                    "AND s.id_str = ps.str_id_str " +
                    "AND rtls_id =? AND date_format(r.timestamp, '%b' ) = ? " +
                    "AND YEAR(r.timestamp) = ? " +
                    "AND l.code = ? " +
                    "AND timestamp IS NOT NULL").setInteger(0, projectID).setString(1, month).setString(2, year).setString(3, LabCode).uniqueResult();
            
            

            session.getTransaction().commit();
            queryResults = Integer.parseInt(bi.toString());

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return queryResults;
    }
*/
    public List sciMails(String field) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List sciMails = null;
        try {
           /* sciMails = session.createSQLQuery(
                    "SELECT DISTINCT " + field + " FROM web_requests " +
                    "WHERE " + field + " IS NOT NULL " +
                    "AND " + field + " LIKE '%@%' " +
                    "ORDER BY " + field + "").list();*/
            
           /* sciMails = session.createSQLQuery(
                    "SELECT DISTINCT " + field + " FROM web_requests " +
                    "WHERE " + field + " IS NOT NULL " +
                    "AND " + field + " LIKE '%@%' " +
                    "AND ta_panel_decision='yes' AND req_status='SHIP' " +
                    "ORDER BY " + field + "").list();*/
            
                        sciMails = session.createSQLQuery(
                                    "SELECT DISTINCT sci_e_mail "
                                + "FROM web_requests "
                                + "WHERE ta_panel_decision='yes' "
                                + "AND application_type IN ('ta_or_request','ta_only') "
                                + "AND ta_panel_decision_date BETWEEN '2010-07-01' AND '2011-12-31' "
                                + "ORDER BY ta_panel_decision_date").list();
            
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return sciMails;
    }

    public Sources_RequestsDAO getReqSourcesByID(int req_id_req) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Sources_RequestsDAO srd = null;
        try {

            srd = (Sources_RequestsDAO) session.get(Sources_RequestsDAO.class,
                    req_id_req);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return srd;
    }

    public List strainRToolID(int id) {

        List rtool;
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            //rtoolID 
            rtool = (List) session.createSQLQuery("SELECT rtls_id FROM rtools_strains " +
                    "WHERE str_id_str=" + id).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {

            session.getTransaction().rollback();
            throw e;
        }
        return rtool;
    }
    
      public List strainList(int id) {

        List strainList = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            strainList = session.createQuery("SELECT emma_id,name,id_str FROM StrainsDAO" +
                    " WHERE id_str LIKE '" + id + "%'" +
                    " AND str_access NOT IN ('C','N','R')" +
                    " ORDER BY emma_id").list();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strainList;
    }


    public void saveRequest(WebRequestsDAO saveWebReq) {
        // Fixes java.sql.BatchUpdateException: Data truncation: Incorrect datetime
        // value: '' for column 'date_processed' at row 1 when date field not updated
        if (saveWebReq.getDate_processed() != null) {
        if (saveWebReq.getDate_processed().length() == 0) {
            System.out.println("archived empty");
            saveWebReq.setDate_processed(null);
        }
        }

        //TA date null fixes

        if (saveWebReq.getTa_panel_decision_date() != null) {

            System.out.println(saveWebReq.getTa_panel_decision_date());
            if (saveWebReq.getTa_panel_decision_date().length() == 0) {
                System.out.println("TA panel decision date is empty");
                saveWebReq.setTa_panel_decision_date(null);
            }

        }
        if (saveWebReq.getTa_panel_sub_date() != null) {
            if (saveWebReq.getTa_panel_sub_date().length() == 0) {
                System.out.println("TA panel submission date is empty");
                saveWebReq.setTa_panel_sub_date(null);
            }

        }
        
             if (saveWebReq.getAll_paperwork_date() != null || saveWebReq.getMta_arrived_date() != null) {
           // System.out.println("AT LEAST ONE DATE IS NULL");
            if (saveWebReq.getAll_paperwork_date().length() == 0) {
                saveWebReq.setAll_paperwork_date(null);
               // System.out.println("All_paperwork_date DATE IS NULL -- " + saveWebReq.getAll_paperwork_date());
            } 
            if (saveWebReq.getMta_arrived_date().length() == 0){
                saveWebReq.setMta_arrived_date(null);
                //System.out.println("mta arrived_date IS NULL -- " + saveWebReq.getMta_arrived_date());
            }
        }

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        try {

            session.saveOrUpdate(saveWebReq);
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public void save(Sources_RequestsDAO srDAO) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        try {
            session.saveOrUpdate(srDAO);
            System.out.println("Saved");
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public void saveOnly(Sources_RequestsDAO srDAO) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        try {
            session.save(srDAO);
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
    }
}
