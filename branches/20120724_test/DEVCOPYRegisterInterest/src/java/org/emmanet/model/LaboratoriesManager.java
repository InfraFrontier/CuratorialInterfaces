/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.model;

/**
 *
 * @author phil
 * 
 * 
 */
import java.util.List;
import org.emmanet.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class LaboratoriesManager {

    public String getLabIDByCode(String labCode) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        String labID;
        try {
            System.out.println("");
            System.out.println("CODE IS :: " + labCode);
            System.out.println("");
            labID = (String) session.createQuery(
                    "SELECT id_labo FROM LabsDAO WHERE code = ?").setParameter(0, labCode).uniqueResult();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return labID;
    }

    public List getArchivesByCode() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List archive;
        try {
            archive = session.createQuery(
                    "FROM LabsDAO WHERE authority IS NOT NULL" + " ORDER BY code").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return archive;
    }

    public LaboratoriesStrainsDAO getLabByStrainID(int id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        LaboratoriesStrainsDAO lsDAO = null;
        try {
            lsDAO = (LaboratoriesStrainsDAO) session.get(LaboratoriesStrainsDAO.class,
                    id);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return lsDAO;
    }

    public void save(LaboratoriesStrainsDAO lsDAO) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {
            System.out.println("AT UPDATE POINT THE VALUE OF lsDAO lab_id_labo IS... " + lsDAO.getLab_id_labo());
            System.out.println("AT UPDATE POINT THE VALUE OF lsDAO STR_ID_STR IS... " + lsDAO.getStr_id_str());
            //session.saveOrUpdate(lsDAO);
            session.createSQLQuery("insert into laboratories_strains (lab_id_labo, str_id_str) values ( " + lsDAO.getLab_id_labo() + "," + lsDAO.getStr_id_str() + ")");
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
    }
}
