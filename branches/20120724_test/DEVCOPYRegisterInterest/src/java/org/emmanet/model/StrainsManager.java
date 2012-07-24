/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.model;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.emmanet.util.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;

/**
 *
 * @author phil
 */
public class StrainsManager {
    /* ALL STRAINS */

    public StrainsDAO getStrainByID(int id_str) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        StrainsDAO sd = null;
        try {

            sd = (StrainsDAO) session.get(StrainsDAO.class,
                    id_str);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return sd;
    }

    public List getStrains() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strains = null;
        try {
            strains = session.createQuery(
                    "FROM StrainsDAO").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strains;
    }

    public List getStrainsByArchID(int archID) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strains = null;
        try {
            strains = session.createQuery(
                    "FROM StrainsDAO WHERE archive_id=?").setInteger(0, archID).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strains;
    }

    public List getStrainsDAOByArchID(int archID) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        // StrainsDAO
        List sd = null;
        try {
            // (StrainsDAO)

            sd = session.createQuery(
                    "FROM StrainsDAO WHERE archive_id=?").setInteger(0, archID).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return sd;
    }

    public List getStrainNamesOnly() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strains = null;
        try {
            strains = session.createQuery(
                    "SELECT id_str,name,emma_id,code_internal FROM StrainsDAO ORDER BY name").list();
            // raffaele updates from testing SELECT  str_id_str,name FROM Syn_StrainsDAO ORDER BY str_id_str  replaced order by id_str with name from todo list
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strains;
    }

    public List getComStrainNamesOnly() {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strains = null;
        try {
            strains = session.createQuery(
                    "SELECT  str_id_str,name,id_syn FROM Syn_StrainsDAO ORDER BY str_id_str").list();
            // raffaele updates from testing SELECT  str_id_str,name FROM Syn_StrainsDAO ORDER BY str_id_str  SELECT id_str,name,emma_id,code_internal FROM StrainsDAO ORDER BY id_str
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strains;
    }
    /////////////////////////////////////
    public List getLiveArchivesByCentre(String str) {

        List reqsByCentre = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

            reqsByCentre = session.createSQLQuery("SELECT emma_id,submitted,code,strains.name,surname,str_status," +
                    "str_access,available_to_order,id_str " +
                    "FROM strains,people,laboratories, archive " +
                    "WHERE people.id_per = strains.per_id_per " +
                    "AND archive.id=strains.archive_id " +
                    "AND laboratories.id_labo=archive.lab_id_labo " +
                    "AND str_status IN('ACCD','ARRD', 'ARING', 'TNA') " +
                    "AND str_access != 'R' and code ='" + str.toUpperCase() + "' ORDER BY emma_id").list();

            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return reqsByCentre;
    }

    public List getGenes() { // to be replaced with a reference to GenesDAO eventually

        List genes = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

            genes = session.createSQLQuery("SELECT symbol FROM genes ORDER BY symbol").list();

            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return genes;
    }

    public List getLiveStrainsByCentre(String str) {

        List reqsByCentre = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

            reqsByCentre = session.createQuery("FROM StrainsDAO s " +
                    "WHERE s.archiveDAO.labsDAO.code ='" + str.toUpperCase() + "' ORDER BY emma_id").list();


            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return reqsByCentre;
    }

    public List getLivePOByCentre(String str) {

        List reqsByCentre = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

            reqsByCentre = session.createSQLQuery("SELECT emma_id,submitted,'N/A',strains.name,surname,str_status," +
                    "str_access,available_to_order,id_str,archive_id " +
                    "FROM strains,people, archive " +
                    "WHERE people.id_per = per_id_per " +
                    "AND archive.id=strains.archive_id " +
                    "AND str_status = 'EVAL' " +
                    "ORDER BY emma_id").list();

            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return reqsByCentre;
    }

    public List getStrains(String sql) {

        List reqs = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

            reqs = session.createQuery(sql)
                    .list();//createQuery(sql).list();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return reqs;
    }
    
     public List getStrainsBySQL(String sql) {

        List reqs = null;

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        try {

            reqs = session.createSQLQuery(sql).list();//createQuery(sql).list();
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        
     /*   for (Iterator i=reqs.listIterator();i.hasNext();){
            Object[] o = (Object[]) i.next();
             for (int in = 0; in < o.length; in++) {
                  if (o[in]==null) o[in]="";
                 System.out.println(in + " direct query iteration value = " + o[in].toString());
           
        }  }*/

        return reqs;
    }
    // SINGLE STRAIN
    public List getStrain(String strainID) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strain = null;
        try {
            strain = session.createQuery(
                    "FROM StrainsDAO where id_str =" + /*id*/ strainID).list();
            /*   strain = session.createQuery(
            "SELECT strains.emma_id,strains.date_published,strains.username," +
            "strains.name,strains.per_id_per,strains.str_status,strains.str_access," +
            "strains.available_to_order,strains.id_str,people.surname " +
            "FROM strains,people " +
            "WHERE strain.id_str =" + strainID +
            " AND people.id_per = per_id_per").list();*/
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strain;
    }

    public List getDistinctStatus() {
        /* DISTINCT STRAIN STATUS */
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List status = null;
        try {
            status = session.createQuery(
                    "SELECT DISTINCT str_status FROM StrainsDAO").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return status;
    }
//todo this query needs modifying to return all values
    public List getMuts(String sql) {
        /* DISTINCT STRAIN STATUS */
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List mut = null;
        try {
            mut = session.createSQLQuery(
                    "SELECT DISTINCT " +
                    "main_type " +
                    "FROM mutations " +
                    "WHERE main_type IS NOT NULL " +
                    sql +
                    "ORDER BY main_type").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return mut;
    }

    public List getMutsSub(String sql) {
        /* DISTINCT STRAIN STATUS */
        System.out.println("getMutsSub called " + sql);
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List mut = null;
        try {
            mut = session.createSQLQuery(
                    "SELECT DISTINCT " +
                    "sub_type " +
                    "FROM mutations " +
                    "WHERE sub_type IS NOT NULL " +
                    sql +
                    "ORDER BY sub_type").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return mut;
    }

    public List getStrainAccess() {
        /* DISTINCT STRAIN ACCESS VALUES */
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strainAccess = null;
        try {
            strainAccess = session.createQuery(
                    "SELECT DISTINCT str_access " +
                    "FROM StrainsDAO " +
                    "ORDER BY str_access").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strainAccess;
    }

    public List getGrantSource() {
        /* FUNDING SOURCES */
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List status = null;
        try {
            status = session.createSQLQuery(
                    "SELECT *  FROM cv_sources").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return status;
    }

    public List getStrainRelatedFundingSource() {
        /* FUNDING SOURCES */
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List status = null;
        try {
            status = session.createSQLQuery(
                    "SELECT * FROM cv_sources " +
                    "WHERE code NOT LIKE 'sTA%' " +
                    "ORDER BY FIELD(code, 'UNKN','s1WP4','s2WP4','s3WP4','1JRA1'," +
                    "'2JRA1','3JRA1','4JRA1','1JRA2','2JRA2','3JRA2','4JRA2','Del1','Del2'," +
                    "'Lex1','Lex2')").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return status;
    }

    public List getReqRelatedFundingSource() {
        /* FUNDING SOURCES */
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List status = null;
        try {
            status = session.createSQLQuery(
                    "SELECT * FROM cv_sources " +
                    "WHERE code LIKE 'sTA%' OR code = 'UNKN' " +
                    "ORDER BY FIELD(code, 'UNKN','sTA01','sTA02','sTA03','sTA04'," +
                    "'sTA05','sTA06','sTA07','sTA08','sTA09','sTA10','sTA11','sTA12'," +
                    "'sTA13','sTA14','sTA15')").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return status;
    }

    public CVRtoolsDAO getRToolsDAO() {
        /* cv_rtools */
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        CVRtoolsDAO rTools = null;
        try {
            //  sd = (StrainsDAO) session.get(StrainsDAO.class,
            //   id_str);
           // rTools = (CVRtoolsDAO) session.get(CVRtoolsDAO.class);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return rTools;
    }

    public List getRTools() {
        /* cv_rtools */
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List rTools = null;
        try {
            rTools = session.createQuery("FROM CVRtoolsDAO").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return rTools;
    }
    
                            public RToolsDAO getRTOOLSByID(int ID) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        RToolsDAO rtd = null;
        try {

            rtd =(RToolsDAO) session.createQuery("FROM RToolsDAO WHERE str_id_str=?").setInteger(0, ID);//(RToolsDAO) session.get(RToolsDAO.class,
                  //  ID);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
System.out.println("DAO FROM SM==" + rtd.getRtls_id());
        return rtd;
    }
    
        public List getRToolsByID(int id) {
        /* cv_rtools */
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List rTools = null;
        try {
            rTools = session.createQuery("FROM RToolsDAO WHERE str_id_str=?").setInteger(0, id).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return rTools;
    }

    public RToolsDAO getRtoolsByID(int id_str) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        RToolsDAO rd = null;
        try {

            rd = (RToolsDAO) session.get(RToolsDAO.class,
                    id_str);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return rd;
    }

    public BigInteger getRToolsCount(int id_str) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        BigInteger count;
        try {
            count = (BigInteger) session.createSQLQuery(
                    "SELECT COUNT(*) FROM rtools_strains WHERE str_id_str=?").setParameter(0, id_str).uniqueResult();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return count;
    }

    public List getProjects() {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List projects = null;
        try {
            projects = session.createSQLQuery("SELECT * FROM cv_projects").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return projects;
    }

    public Sources_StrainsDAO getSourcesByID(int str_id_str) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        Sources_StrainsDAO ssd = null;
        try {

            ssd = (Sources_StrainsDAO) session.get(Sources_StrainsDAO.class,
                    str_id_str);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return ssd;
    }
    
            public CVProjectsDAO getProjectsByPID(int projectID) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        CVProjectsDAO psd = null;
        try {

            psd = (CVProjectsDAO) session.get(CVProjectsDAO.class,
                    projectID);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return psd;
    }

                        public ProjectsStrainsDAO getProjectsByID(int projectID) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        ProjectsStrainsDAO psd = null;
        try {

            psd = (ProjectsStrainsDAO) session.get(ProjectsStrainsDAO.class,
                    projectID);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return psd;
    }
                        
    public CVSourcesDAO getSourcesBySID(int sid) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        CVSourcesDAO cvs = null;
        try {

            cvs = (CVSourcesDAO) session.get(CVSourcesDAO.class,
                    sid);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return cvs;
    }
    
  /*      public CVProjectsDAO getProjectsByPID(int projectID) {
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        CVProjectsDAO psd = null;
        try {

            psd = (CVProjectsDAO) session.get(CVProjectsDAO.class,
                    projectID);
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }

        return psd;
    }
*/
    public List getArchCentre(int archID) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List centre = null;
        try {
            centre = session.createQuery(
                    "FROM LabsDAO where id_labo = ?").setInteger(0, archID).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return centre;
    }

    public int getArchID(int ID) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        int archID;
        try {
            archID = (Integer) session.createQuery(
                    "SELECT archive_id FROM StrainsDAO where id_str = ?").setInteger(0, ID).uniqueResult();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return archID;
    }

    public List getStrainsByName(String name) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strains = null;
        try {
            strains = session.createQuery(
                    "FROM StrainsDAO WHERE name LIKE '" + name + "%'").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strains;
    }

    public List getStrainsByGene(String name) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strains = null;
        try {
            strains = session.createQuery(
                    "SELECT s " +
                    "FROM StrainsDAO s " +
                    "JOIN s.mutationsStrainsDAO as ms " +
                    "WHERE ms.mutationsDAO.allelesDAO.genesDAO.symbol LIKE '" + name + "%'").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strains;
    }

    public List getStrainsByEmmaID(String id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strains = null;
        try {
            strains = session.createQuery(
                    "FROM StrainsDAO WHERE emma_id LIKE '" + id + "%'").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strains;
    }

    public List getStrainsByArchCentre(int id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strains = null;
        try {
            strains = session.createQuery(
                    "FROM StrainsDAO s WHERE s.archiveDAO.labsDAO.id_labo = ?").setInteger(0, id).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strains;
    }

    public List getStrainsByAccessStatus(String access) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strains = null;
        try {
            strains = session.createQuery(
                    "FROM StrainsDAO WHERE str_access = ?").setString(0, access).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strains;
    }

    public List getStrainsByStrainStatus(String status) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strains = null;
        try {
            strains = session.createQuery(
                    "FROM StrainsDAO WHERE str_status = " + status).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strains;
    }

    public List getStrainsByPhenoDesc(String desc) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strains = null;
        try {
            strains = session.createQuery(
                    "FROM StrainsDAO WHERE pheno_text LIKE '%" + desc + "%'").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strains;
    }

    public List getStrainIDByFundSource(List sour_id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strains = null;
        // BUILD SQL STRING
        String sql = "SELECT str_id_str FROM sources_strains WHERE sour_id = ";
        if (sour_id.size() > 1) {
            for (ListIterator it = sour_id.listIterator(); it.hasNext();) {
                int index = it.nextIndex();
                Integer source = (Integer) it.next();
                if (index == 0) {
                    sql = sql + source;
                } else {
                    sql = sql + " OR sour_id = " + source;
                }
            }
        } else {
            Iterator it = sour_id.iterator();
            sql = sql + "" + it.next();
        }
        try {
            strains = session.createSQLQuery(sql).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strains;
    }

    public List getStrainIDsByRTools(int desc) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strains = null;
        try {
            strains = session.createSQLQuery(
                    "SELECT str_id_str FROM rtools_strains WHERE rtls_id = ?").setInteger(0, desc).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strains;
    }

    public List getStrainIDsByMutation(String mutType) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strains = null;
        try {
            strains = session.createSQLQuery(
                    "SELECT str_id_str FROM mutations WHERE main_type = ? AND  str_id_str IS NOT NULL").setString(0, mutType).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strains;
    }

    public List getStrainIDsByMutationSubtype(String mutSubType) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strains = null;
        try {
            strains = session.createSQLQuery(
                    "SELECT str_id_str FROM mutations WHERE sub_type = ? AND  str_id_str IS NOT NULL").setString(0, mutSubType).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strains;
    }

    public List getStrainsByAvailability(String avail) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strains = null;
        try {
            if (avail.equals("no")) {
                //CATCHES THOSE RECORDS WHERE AVAILABILITY NOT SET
                avail = "'" + avail + "' OR available_to_order IS NULL";
                strains = session.createQuery(
                        "FROM StrainsDAO WHERE available_to_order = " + avail).list();
            } else {
                strains = session.createQuery(
                        "FROM StrainsDAO WHERE available_to_order = ?").setString(0, avail).list();
            }
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strains;
    }

    public List getStrainsMGI(int mgiRef) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strains = null;
        try {
            strains = session.createQuery(
                    "FROM StrainsDAO WHERE mgi_ref like '%" + mgiRef + "%'").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strains;
    }

    public List getStrainsByCommName(String commonName) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strains = null;
        try {
            /*   strains = session.createSQLQuery(
            "SELECT strains.* from strains,syn_strains where strains.id_str = syn_strains.str_id_str " +
            "AND (strains.code_internal LIKE '" + commonName + "%' " +
            "OR syn_strains.name like '" + commonName + "%')").list();*/
            strains = session.createSQLQuery("SELECT str_id_str from syn_strains WHERE name LIKE '" + commonName + "%'").list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strains;
    }

    public List getStrainsAndPerson(int id) {
        /* NB QUERY ORDER IMPORTANT!! ONLY ADD ADDITIONAL FIELDS TO END OF 
         * SELECT I.E. BEFORE FROM STATEMENT 
         */
        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List strains = null;
        try {
            strains = session.createSQLQuery(
                    "SELECT strains.emma_id,strains.date_published,strains.username," +
                    "strains.name,strains.per_id_per,strains.str_status,strains.str_access," +
                    "strains.available_to_order,strains.id_str,people.surname,people.e_mail," +
                    "people.title,people.firstname " +
                    "FROM strains,people " +
                    "WHERE id_str = ? " +
                    "AND people.id_per = per_id_per").setInteger(0, id).list();
            session.getTransaction().commit();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return strains;
    }

    public List getBackgrounds() {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List bgDAO = null;
        try {
            bgDAO = session.createSQLQuery("SELECT * FROM backgrounds ORDER BY name").list();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return bgDAO;
    }
    
        public BigInteger getBackgroundIDCount(int bg_id) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        BigInteger bgCount = null;
        try {
            bgCount = (BigInteger) session.createSQLQuery("SELECT count(bg_id_bg) FROM strains where bg_id_bg=?")
                    .setParameter(0, bg_id).uniqueResult();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return bgCount;
    }

    public List getCodeInternal() {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List codeInt = null;
        try {
            codeInt = session.createSQLQuery("SELECT code_internal " +
                    "FROM strains " +
                    "WHERE code_internal != '' " +
                    "ORDER BY code_internal ASC").list();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return codeInt;
    }

    public List getNameStatusList() {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();
        List nameStatus = null;
        try {
            nameStatus = session.createSQLQuery("SELECT DISTINCT name_status " +
                    "FROM strains " +
                    "WHERE name_status != '' " +
                    "AND name_status IS NOT NULL " +
                    "ORDER BY name_status ASC").list();
        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
        return nameStatus;
    }

    public void save(StrainsDAO sDAO) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        try {
            session.saveOrUpdate(sDAO);
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public void saveArchive(StrainsDAO sDAO) {

        /* Why is this here??
        OK basically db dictates that at times archiveDAO has no str_id_str which is used to retrieve the strainsDAO object
         * thus this breaks any save from
        
        
        
         */

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


        if (sDAO.getGp_release() != null) {

            System.out.println(sDAO.getGp_release());
            if (sDAO.getGp_release().length() == 0) {
                System.out.println("gp_release empty");
                sDAO.setGp_release(null);
            }

        }
        if (sDAO.getArchiveDAO().getArchived() != null) {
            if (sDAO.getArchiveDAO().getArchived().length() == 0) {
                System.out.println("archived empty");
                sDAO.getArchiveDAO().setArchived(null);
            }

        }

        if (sDAO.getArchiveDAO().getSubmitted() != null) {


            if (sDAO.getArchiveDAO().getSubmitted().length() == 0) {
                System.out.println("submitted empty");
                sDAO.getArchiveDAO().setSubmitted(null);
            }

        }


        if (sDAO.getArchiveDAO().getReceived() != null) {


            if (sDAO.getArchiveDAO().getReceived().length() == 0) {
                System.out.println("received empty");
                sDAO.getArchiveDAO().setReceived(null);
            }

        }

        if (sDAO.getArchiveDAO().getFreezing_started() != null) {


            if (sDAO.getArchiveDAO().getFreezing_started().length() == 0) {
                System.out.println("freezing empty");
                sDAO.getArchiveDAO().setFreezing_started(null);
            }

        }

        if (sDAO.getArchiveDAO().getEvaluated() != null) {

            if (sDAO.getArchiveDAO().getEvaluated().length() == 0) {
                sDAO.getArchiveDAO().setEvaluated(null);

            }
        }

        if (sDAO.getArchiveDAO().getWt_received() != null) {

            if (sDAO.getArchiveDAO().getWt_received().length() == 0) {
                sDAO.getArchiveDAO().setWt_received(null);

            }
        }

        if (sDAO.getArchiveDAO().getWt_rederiv_started() != null) {

            if (sDAO.getArchiveDAO().getWt_rederiv_started().length() == 0) {
                sDAO.getArchiveDAO().setWt_rederiv_started(null);

            }
        }

        if (sDAO.getArchiveDAO().getFrozen_sanger_embryos_arrived() != null) {

            if (sDAO.getArchiveDAO().getFrozen_sanger_embryos_arrived().length() == 0) {
                sDAO.getArchiveDAO().setFrozen_sanger_embryos_arrived(null);
            }
        }

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        try {
            session.saveOrUpdate(sDAO);
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public void save(RToolsDAO rDAO) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        try {
            session.saveOrUpdate(rDAO);
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public void saveSsd(Sources_StrainsDAO ssDAO) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        try {
            session.saveOrUpdate(ssDAO);
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public void savePsd(ProjectsStrainsDAO psDAO) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        try {
            session.saveOrUpdate(psDAO);
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public void saveOnlyPsd(ProjectsStrainsDAO psDAO) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        try {
            session.save(psDAO);
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
    }

    public void saveOnlySsd(Sources_StrainsDAO ssDAO) {

        Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        session.beginTransaction();

        try {
            session.save(ssDAO);
            session.getTransaction().commit();

        } catch (HibernateException e) {
            session.getTransaction().rollback();
            throw e;
        }
    }
}
