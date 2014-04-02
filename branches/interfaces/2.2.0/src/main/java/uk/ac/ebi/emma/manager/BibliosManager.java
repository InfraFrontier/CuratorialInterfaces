/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package uk.ac.ebi.emma.manager;

/**
 *
 * @author phil
 */
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import uk.ac.ebi.emma.Exception.PersistFailedException;
import uk.ac.ebi.emma.entity.Biblio;
import uk.ac.ebi.emma.util.Filter;
import uk.ac.ebi.emma.util.Utils;

public class BibliosManager extends AbstractManager {


    /**
     * Delete biblio.
     * 
     * @param biblio the biblio to be deleted
     */
    public void delete(Biblio biblio) {
        try {
            getCurrentSession().beginTransaction();
            getCurrentSession().delete(biblio);
            getCurrentSession().getTransaction().commit();

        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
    }
    
    /**
     * Delete biblio.
     * 
     * @param biblio_key the primary key of the biblio to be deleted
     */
    public void delete(int biblio_key) {
        delete(getBiblio(biblio_key));
    }

    /**
     * Returns the <code>Biblio</code> matching <code>mutation_key</code>
     * @param biblio_key the desired mutation primary key [may be comma-separated list]
     * 
     * @return the <code>Biblio</code> matching <code>biblio_key</code>.
     * @throws HibernateException if a hibernate error occurs
     */
    public Biblio getBiblio(Integer biblio_key) throws HibernateException {
        if (biblio_key == null)
            return null;
        
        Biblio biblio = null;
        try {
            getCurrentSession().beginTransaction();
            biblio = (Biblio)getCurrentSession()
                    .createQuery("FROM Biblio b LEFT JOIN FETCH b.strains WHERE b.biblio_key = :biblio_key")
                    .setParameter("biblio_key", biblio_key)
                    .uniqueResult();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
        
        return biblio;
    }
    
    /**
     * Given an optionally null or empty pubmed_id string containing 0 or more
     * [optionally comma-separated] pubmed ids, this method returns a
     * list of zero or more matching <code>Biblio</code> objects.
     * 
     * <strong>NOTE: The returned list is guaranteed to not be null.</strong>
     * @param pubmedId the pubmed id [or list] against which to search
     * @return all matched records. The list is guaranteed never to be null.
     */
    public List<Biblio> getBibliosByPubmedId(String pubmedId) {
        List<Biblio> biblioList = new ArrayList();
        
        String pubmedIds = Utils.cleanIntArray(pubmedId);
        if (Utils.isValidIntArray(pubmedIds)) {
            try {
                getCurrentSession().beginTransaction();
                biblioList = (List<Biblio>)getCurrentSession()
                        .createQuery("FROM Biblio b LEFT JOIN FETCH b.strains WHERE pubmed_id IN (" + pubmedId + ")\n")
                        .list();
                getCurrentSession().getTransaction().commit();
            } catch (HibernateException e) {
                getCurrentSession().getTransaction().rollback();
            }
        }
        return biblioList;
    }
    
    /**
     * Given a <code>Filter</code> object describing any/all of the following
     * fields:
     * <ul>
     * <li>biblio_key (may be a comma-separated list)</li>
     * <li>strain_key (may be a comma-separated list)</li>
     * <li>pubmed ID (single value only)</li>
     * <li>author</li>
     * <li>isCurated</li>
     * <li>title</li>
     * </ul>
     * this method performs a query, ANDing all non-empty fields in a WHERE
     * clause against the biblios table (joined to the strains table via
     * biblios_strains for strain keys).
     * 
     * The result is a <code>List&lt;Biblio&gt;</code> of
     * qualifying results. A list is always returned, even if there are no results.
     * 
     * @param filter values to filter by
     * @return a list of matching <code>Biblio</code> instances.
     * @throws NumberFormatException if keys are not numeric (commas and whitespace are OK)
     */
    public List<Biblio> getFilteredBibliosList(Filter filter) throws NumberFormatException {
        String biblioKeyWhere = "";
        String strainKeyWhere = "";
        String pubmedIdWhere = "";
        String author1Where = "";
        String journalWhere = "";
        String titleWhere = "";
        String yearWhere = "";
        
        List<Biblio> targetList = new ArrayList();
        String queryString = "SELECT b.*, GROUP_CONCAT(bs.str_id_str) AS strain_keys\n"
                           + "FROM biblios b\n"
                           + "LEFT OUTER JOIN biblios_strains bs ON bs.bib_id_biblio = b.id_biblio\n"
                           + "WHERE (1 = 1)\n";     
        if ((filter.getBiblio_key() != null) && ( ! filter.getBiblio_key().isEmpty())) {
            String biblioIds = Utils.cleanIntArray(filter.getBiblio_key());
            if (Utils.isValidIntArray(biblioIds)) {
                biblioKeyWhere = "  AND (b.id_biblio IN (" + biblioIds + "))\n";
                queryString += biblioKeyWhere;
            }
        }
        if ((filter.getStrain_key() != null) && ( ! filter.getStrain_key().isEmpty())) {
            String strainIds = Utils.cleanIntArray(filter.getStrain_key());
            if (Utils.isValidIntArray(strainIds)) {
                strainKeyWhere = "  AND (bs.str_id_str IN (" + strainIds + "))\n";
                queryString += strainKeyWhere;
            }
        }
        if ((filter.getPubmedId() != null) && ( ! filter.getPubmedId().isEmpty())) {
            pubmedIdWhere = "  AND (b.pubmed_id LIKE :pubmedId)\n";
            queryString += pubmedIdWhere;
        }
        if ((filter.getBiblioAuthor1() != null) && ( ! filter.getBiblioAuthor1().isEmpty())) {
            author1Where = "  AND (b.author1 LIKE :author1)\n";
            queryString += author1Where;
        }
        if ((filter.getBiblioJournal() != null) && ( ! filter.getBiblioJournal().isEmpty())) {
            journalWhere = "  AND (b.journal LIKE :journal)\n";
            queryString += journalWhere;
        }
        if ((filter.getBiblioTitle() != null) && ( ! filter.getBiblioTitle().isEmpty())) {
            titleWhere = "  AND (b.title LIKE :title)\n";
            queryString += titleWhere;
        }
        if ((filter.getBiblioYear() != null) && ( ! filter.getBiblioYear().isEmpty())) {
            yearWhere = "  AND (b.year = :year)\n";
            queryString += yearWhere;
        }

        queryString += "ORDER BY b.id_biblio DESC\n";
        
        try {
            getCurrentSession().beginTransaction();
            SQLQuery query = getCurrentSession().createSQLQuery(queryString);
            
            if ( ! pubmedIdWhere.isEmpty())
                query.setParameter("pubmedId", "%" + filter.getPubmedId() + "%");
            if ( ! author1Where.isEmpty())
                query.setParameter("author1", "%" + filter.getBiblioAuthor1() + "%");
            if ( ! journalWhere.isEmpty())
                query.setParameter("journal", "%" + filter.getBiblioJournal() + "%");
            if ( ! titleWhere.isEmpty())
                query.setParameter("title", "%" + filter.getBiblioTitle() + "%");
            if ( ! yearWhere.isEmpty())
                query.setParameter("year", filter.getBiblioYear());
            List resultSet = query.addEntity(Biblio.class).addScalar("strain_keys").list();
            getCurrentSession().getTransaction().commit();
            
            if (resultSet != null) {
                for (Object result : resultSet) {
                    Object[] row = (Object[]) result;
                    Biblio biblio = (Biblio)row[0];
                    if (biblio != null) {
                        biblio.setStrain_keys((row[1] == null ? "" : row[1].toString()));     // Add strain_keys to transient Biblio instance.
                        biblio.setStrains(null);
                        targetList.add(biblio);
                    }
                }
            }
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }
        
        return targetList;
    }
    
    /**
     * Saves the given <code>Biblio</code> instance
     * @param biblio the <code>Biblio</code> instance to be saved
     * @throws PersistFailedException if save fails
     */
    public void save(Biblio biblio) throws PersistFailedException {
        biblio.setLast_change(new Date());
        biblio.setUsername(username);
        
        try {
            getCurrentSession().beginTransaction();
            getCurrentSession().saveOrUpdate(biblio);
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw new PersistFailedException("Failed to save biblio. Reason: " + e.getLocalizedMessage());
        }
    }
    
    
    // GETTERS AND SETTERS
    
    
    /**
     * Returns a distinct list of author1s suitable for autocomplete sourcing.
     * @param filterTerm the filter term for the author1 (used in sql LIKE clause)
     * @return a list of author1s suitable for autocomplete sourcing.
     */
    public List<String> getAuthor1s(String filterTerm) throws HibernateException {
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT author1 FROM biblios WHERE author1 LIKE :author1 ORDER BY author1")
                    .setParameter("author1", "%" + filterTerm.trim() + "%")
                    .list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        return sourceList;
    }
    
    /**
     * Returns a distinct list of journals suitable for autocomplete sourcing.
     * @param filterTerm the filter term for the journal (used in sql LIKE clause)
     * @return a list of journals suitable for autocomplete sourcing.
     */
    public List<String> getJournals(String filterTerm) throws HibernateException {
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT journal FROM biblios WHERE journal LIKE :journal ORDER BY journal")
                    .setParameter("journal", "%" + filterTerm.trim() + "%")
                    .list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        return sourceList;
    }
    
    /**
     * Returns a distinct list of titles suitable for autocomplete sourcing.
     * @param filterTerm the filter term for the title (used in sql LIKE clause)
     * @return a list of titles suitable for autocomplete sourcing.
     */
    public List<String> getTitles(String filterTerm) throws HibernateException {
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT title FROM biblios WHERE title LIKE :title ORDER BY title")
                    .setParameter("title", "%" + filterTerm.trim() + "%")
                    .list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        return sourceList;
    }
    
    /**
     * Returns a distinct list of years suitable for autocomplete sourcing.
     * @param filterTerm the filter term for the year (used in sql LIKE clause)
     * @return a list of years suitable for autocomplete sourcing.
     */
    public List<String> getYears(String filterTerm) throws HibernateException {
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT year FROM biblios WHERE year LIKE :year ORDER BY year")
                    .setParameter("year", "%" + filterTerm.trim() + "%")
                    .list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        return sourceList;
    }
    
    /**
     * Returns a distinct list of pubmed ids suitable for autocomplete sourcing.
     * @param filterTerm the filter term for the pubmed id (used in sql LIKE clause)
     * @return a list of pubmed ids suitable for autocomplete sourcing.
     */
    public List<String> getPubmedIds(String filterTerm) throws HibernateException {
        List sourceList = null;
        try {
            getCurrentSession().beginTransaction();
            sourceList = getCurrentSession()
                    .createSQLQuery("SELECT DISTINCT pubmed_id FROM biblios WHERE pubmed_id LIKE :pubmedId ORDER BY CAST(pubmed_id AS unsigned) ASC")
                    .setParameter("pubmedId", "%" + filterTerm.trim() + "%")
                    .list();
            getCurrentSession().getTransaction().commit();
        } catch (HibernateException e) {
            getCurrentSession().getTransaction().rollback();
            throw e;
        }

        return sourceList;
    }

}