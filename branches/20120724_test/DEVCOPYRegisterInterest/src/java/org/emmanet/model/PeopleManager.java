/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.model;

/**
 *
 * @author phil
 */
import java.util.List;
import org.emmanet.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class PeopleManager {

    public PeopleDAO getPerson(String id) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        //List personDetails = null;
        PeopleDAO personDetails;
        try {
            /* personDetails = (PeopleDAO)session.createQuery(
            "FROM PeopleDAO WHERE id_per = ?").setParameter(0, id);*/
            personDetails = (PeopleDAO) session.get(PeopleDAO.class, id);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return personDetails;
    }

    public List sciMails() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List sciMails = null;
        try {
            sciMails = session.createSQLQuery(
                    "SELECT DISTINCT e_mail,country FROM people p,roles_people rp,laboratories l " +
                    "WHERE role_id=4 " +
                    "AND (p.id_per=rp.per_id_per AND p.lab_id_labo=l.id_labo " +
                    "AND e_mail is not null AND e_mail LIKE '%@%' AND country IS NOT NULL) " +
                    "ORDER BY e_mail").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return sciMails;
    }
    
    public List getPeopleByEMail(String email) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List personDetails;
        try {
            personDetails = session.createQuery("FROM PeopleDAO WHERE e_mail=? AND e_mail is not null AND e_mail LIKE '%@%'").setParameter(0, email).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return personDetails;
    }

    public void save(PeopleDAO pDAO) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        try {
            session.saveOrUpdate(pDAO);
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
    }
}
