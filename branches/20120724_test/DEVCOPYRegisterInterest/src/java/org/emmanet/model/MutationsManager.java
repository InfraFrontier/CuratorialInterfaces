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

public class MutationsManager {

    public MutationsDAO getMutByID(int id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        MutationsDAO md = null;
        try {

            md = (MutationsDAO) session.get(MutationsDAO.class,
                    id);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return md;
    }

    public List getMutationIDsByStrain(int strID) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List mutIds = null;
        try {
            mutIds = session.createQuery(
                    "FROM MutationsStrainsDAO WHERE str_id_str=?").setParameter(0, strID).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return mutIds;
    }

    public BigInteger getBackgroundIDCount(int bg_id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        BigInteger bgCount = null;
        try {
            bgCount = (BigInteger) session.createSQLQuery("SELECT count(bg_id_bg) FROM mutations where bg_id_bg=?").setParameter(0, bg_id).uniqueResult();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return bgCount;
    }

    public void save(MutationsDAO mDAO) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        try {
            session.saveOrUpdate(mDAO);
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    //Submission mutations 
    // Saves and deletes submission form mutations prior to them reaching the mutations table
    // allows users to modify their mutation submissions before final submission form controller stores data relating to the new strain.
    public List getSubMutationBySubID(int subID) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List subMutations = null;
        try {
            //.setMaxResults(10)
            subMutations = session.createQuery(
                    "FROM submissionMutationsDAO WHERE id_sub=? ORDER BY id DESC").setMaxResults(10).setParameter(0, subID).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return subMutations;
    }

    public SubmissionMutationsDAO getSubMutationBySubMutID(int subMutID) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        SubmissionMutationsDAO smd = null;
        try {

            smd = (SubmissionMutationsDAO) session.get(SubmissionMutationsDAO.class,
                    subMutID);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return smd;
    }

    public void save(SubmissionMutationsDAO smDAO) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        try {
            session.saveOrUpdate(smDAO);
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public void delete(int mutID) {
        SubmissionMutationsDAO smDAO = this.getSubMutationBySubMutID(mutID);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        try {
            session.delete(smDAO);
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
    }
}
