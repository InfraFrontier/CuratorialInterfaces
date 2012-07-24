/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.model;

/**
 *
 * @author phil
 */
import java.math.BigInteger;
import java.util.List;
import org.emmanet.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class BibliosManager {

    public BibliosStrainsDAO getBibStrainsByID(int id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        BibliosStrainsDAO bsd = null;
        try {

            bsd = (BibliosStrainsDAO) session.get(BibliosStrainsDAO.class,
                    id);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return bsd;
    }

    public BibliosDAO getBibByID(int id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        BibliosDAO bd = null;
        try {

            bd = (BibliosDAO) session.get(BibliosDAO.class,
                    id);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return bd;
    }
    
        public BibliosDAO getPubmedIDByID(int pmid) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        BibliosDAO bd = null;
        try {

            bd = (BibliosDAO) session.createQuery("WHERE pubmed_id =?").setParameter(0, pmid);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return bd;
    }
        
                public List getPubmedID() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List bd = null;
        try {

            bd = session.createQuery("FROM BibliosDAO WHERE (updated IS NULL OR updated != 'Y') " +
                                                 "AND (pubmed_id IS NOT NULL AND pubmed_id !='')").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return bd;
    }


    public List Biblios(int id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List bibIDs = null;
        try {
            bibIDs = session.createQuery(
                    "FROM BibliosDAO WHERE id_biblio=?").setParameter(0, id).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return bibIDs;
    }

    public List BibliosStrains(int id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List bibIDs = null;
        try {
            bibIDs = session.createQuery(
                    "FROM BibliosStrainsDAO WHERE str_id_str=?").setParameter(0, id).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return bibIDs;
    }

    public BigInteger BibliosStrainCount(Integer id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        BigInteger count;
        try {
            count = (BigInteger) session.createSQLQuery(
                    "SELECT COUNT(*) FROM biblios_strains WHERE str_id_str=?").setParameter(0, id).uniqueResult();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return count;
    }

    public void save(BibliosDAO bDAO) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        try {
            session.saveOrUpdate(bDAO);
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public void save(BibliosStrainsDAO bsDAO) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        try {
            //session.saveOrUpdate(bsDAO);
            session.save(bsDAO);
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
    }
}
