/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.model;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import org.emmanet.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author phil
 */
public class ArchiveManager {

    public String setPdfURL(String pdfURL) {

        ArchiveDAO ad = (ArchiveDAO) new ArchiveDAO();
        ad.setPdfURL(pdfURL);
        System.out.println("SETTING URL TO " + pdfURL);
        System.out.println(ad.getPdfURL());
        String pdfUrl = ad.getPdfURL();
        System.out.println();
        System.out.println("THE URL IS : " + pdfUrl + " RETURNED FROM GETPDFURL");
        System.out.println();
        return pdfUrl;


    }

    public String getPdfURL() {

        ArchiveDAO ad = (ArchiveDAO) new ArchiveDAO();
        String url = ad.getPdfURL();
        System.out.println("GETTING URL  " + url);
        System.out.println(url);
        return url;

    }

    public List getArchiveStrainsBySubDate(String sql) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strainIDs = null;
        try {
            strainIDs = session.createSQLQuery(
                    "SELECT str_id_str FROM archive WHERE submitted " + sql + "AND str_id_str IS NOT NULL").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strainIDs;
    }

    public List getArchiveStrainsByEvalDate(String sql) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strainIDs = null;
        try {
            strainIDs = session.createSQLQuery(
                    "SELECT str_id_str FROM archive WHERE evaluated " + sql + "AND str_id_str IS NOT NULL").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strainIDs;
    }

    public List getArchiveStrainsByRecDate(String sql) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strainIDs = null;
        try {
            strainIDs = session.createSQLQuery(
                    "SELECT str_id_str FROM archive WHERE received " + sql + "AND str_id_str IS NOT NULL").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strainIDs;
    }

    public List getArchiveStrainsByArchStart(String sql) {

        //TODO FREEZING START TAKEN AS ARCHIVING STARTED NEED TO CLARIFY IF THIS IS CORRECT
        // Clarified by Michael Hagn
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strainIDs = null;
        try {
            strainIDs = session.createSQLQuery(
                    "SELECT str_id_str FROM archive WHERE freezing_started " + sql + "AND str_id_str IS NOT NULL").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strainIDs;
    }

    public List getArchiveStrainsByArchDate(String sql) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strainIDs = null;
        try {
            strainIDs = session.createSQLQuery(
                    "SELECT str_id_str FROM archive WHERE archived " + sql + "AND str_id_str IS NOT NULL").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strainIDs;
    }

    public List getArchiveDetailsByStrain(String strID) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List archiveDetails = null;
        try {
            archiveDetails = session.createQuery(
                    "FROM ArchiveDAO WHERE str_id_str=?").setParameter(0, strID).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return archiveDetails;
    }

    public ArchiveDAO getReqByID(int str_id_str) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        ArchiveDAO ar = null;
        try {

            ar = (ArchiveDAO) session.get(ArchiveDAO.class,
                    str_id_str);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return ar;
    }

    public ArchiveDAO getReqByArchID(int id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        ArchiveDAO ar = null;
        try {

            ar = (ArchiveDAO) session.get(ArchiveDAO.class,
                    id);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return ar;
    }

    public String getPerEmail(int per_id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String email = "";
        try {

            email = session.createSQLQuery("SELECT e_mail FROM person where id_per=" + per_id).toString();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return email;
    }

    public List getStrainAvail(int strainID) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List availDetails = null;
        try {
            availDetails = session.createSQLQuery(
                    "SELECT * FROM availabilities_strains WHERE str_id_str=?").setParameter(0, strainID).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return availDetails;
    }

    public CVAvailabilitiesDAO getCVAvail(int id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        CVAvailabilitiesDAO cvaDAO = null;
        try {

            cvaDAO = (CVAvailabilitiesDAO) session.get(CVAvailabilitiesDAO.class,
                    id);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return cvaDAO;
    }

    public int getCVAvailID(int instock, int todist, String code) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        int cvid;
        try {

            cvid = (Integer) session.createSQLQuery(
                    "SELECT id FROM cv_availabilities WHERE to_distr=? AND in_stock=? AND (code=?)").setParameter(1, todist).setParameter(0, instock).setParameter(2, code).uniqueResult();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return cvid;
    }

    
    public BigInteger getBackgroundIDCount(int bg_id, String sex) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        BigInteger bgCount = null;
        try {
            bgCount = (BigInteger) session.createSQLQuery("SELECT count(" + sex + "_bg_id) FROM archive where " + sex + "_bg_id=?").setParameter(0, bg_id).uniqueResult();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return bgCount;
    }
    
    public void save(ArchiveDAO aDAO) {
        /*
         * A brief explanation for why this is here..
         * If the string holds an empty value from the web form
         * a java.sql.BatchUpdateException: Data truncation:
         * Incorrect datetime value: '' for column 'received' at row 1
         * is thrown and the update fails.
         * The following statements check for empty values and converts them to
         * null which prevents this error when using Hibernate
         * ref: Hibernate manual section 9.4.1. Updating in the same Session
         */
        //length use instead 1.6 dependant method isEmpty()

       // System.out.println("REACHED HERE");
        
        if (aDAO.getStrainsDAO() != null) {
            if (aDAO.getStrainsDAO().getGp_release() != null) {


                if (aDAO.getStrainsDAO().getGp_release().length() == 0) {
                    System.out.println("gp_release empty");
                    aDAO.getStrainsDAO().setGp_release(null);
                }
            }
        }
        if (aDAO.getArchived() != null) {

            if (aDAO.getArchived().length() == 0) {
                System.out.println("archived empty");
                aDAO.setArchived(null);
            }

        }

        if (aDAO.getSubmitted() != null) {


            if (aDAO.getSubmitted().length() == 0) {
                System.out.println("submitted empty");
                aDAO.setSubmitted(null);
            }

        }


        if (aDAO.getReceived() != null) {


            if (aDAO.getReceived().length() == 0) {
                System.out.println("received empty");
                aDAO.setReceived(null);
            }

        }

        if (aDAO.getFreezing_started() != null) {


            if (aDAO.getFreezing_started().length() == 0) {
                System.out.println("freezing empty");
                aDAO.setFreezing_started(null);
            }

        }
        /*/
        if (aDAO.getWt_rederiv_started().isEmpty()) {
        aDAO.setWt_rederiv_started(null);
        }*/
        if (aDAO.getEvaluated() != null) {

            if (aDAO.getEvaluated().length() == 0) {
                aDAO.setEvaluated(null);

            }
        }

        /*
        if (aDAO.getWt_received().isEmpty()) {
        aDAO.setWt_received(null);
        }
         */
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        try {
            //System.out.println("REACHED HERE TOO");
            
            session.saveOrUpdate(aDAO);
            
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    //  public void save(int str_id_str, int cvavailID) {
    public void save(AvailabilitiesStrainsDAO_1 asd) {
        //SQL SAVE

        System.out.println(asd.getAvail_id());
        System.out.println(asd.getStr_id_str());


        System.out.print("Save called in controller");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        try {
            // System.out.println("str_id_str=" + str_id_str + " cvavailID= " + cvavailID);
            // String query="";

            //session.createSQLQuery("INSERT INTO availabilities_strains VALUES (" + str_id_str + "," + cvavailID + ")");
            session.save(asd);
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public void delete(AvailabilitiesStrainsDAO asd) {
        //SQL delete
        System.out.print("Delete called in controller");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        try {
            session.delete(asd);
            session.getTransaction().commit();

            System.out.print("Deleted");
        } catch (HibernateException e) {
            System.out.println("Exception generated  " + e);
            session.getTransaction().rollback();
            throw e;
        }
    }
    
    /////////////////////////////
    
     public void delete(int strainID) {
        //SQL delete
        System.out.print("Delete called in controller");
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        try {
            session.createSQLQuery("DELETE FROM availabilities_strains " +
                    "WHERE str_id_str = ?").setInteger(0, strainID).executeUpdate();
            session.getTransaction().commit();

            System.out.print("Deleted");
        } catch (HibernateException e) {
            System.out.println("Exception generated  " + e);
            session.getTransaction().rollback();
            throw e;
        }
    }
}
