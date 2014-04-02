 /**
 * Copyright © 2013 EMBL - European Bioinformatics Institute
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License.  
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.ebi.emma.entity;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Set;
import javax.persistence.Transient;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.transaction.PlatformTransactionManager;
import uk.ac.ebi.emma.util.Utils;

/**
 *
 * @author phil, mrelac
 * 02-Sept-2013 (mrelac) extended for JdbcTemplate use by adding RowMapper,
 *   JdbcTemplate getter/setter, and Serializable attribute.
 */
public class Biblio implements RowMapper {
    private Integer biblio_key = null;                                          // primary key (was id_biblio)
    
    // COLLECTIONS
    private Set<Strain> strains;
    
    @Transient
    private String strain_keys;                                                 // foreign key(s) to strains table (new for EMMA2) (comma-separated)

    private String author1;
    private String author2;
    private String journal;
    private String notes;
    private String pages;
    private String pubmed_id;
    private String title;
    private String updated;
    private String volume;
    private String year;
    
    private Date   last_change;                                                 // date last changed
    private String username;                                                    // changed by username

    private JdbcTemplate jdbcTemplate;
    private PlatformTransactionManager platformTransactionManager;

    /**
     * This method implements spring's JdbcTemplate RowMapper interface, allowing
     * BibliosDAO to be used in the spring jdbc template pattern.
     * @param rs the result set to be mapped
     * @param rowNum required by the interface
     * @return a list of <code>BibliosDAO</code> matching the result set
     * @throws SQLException if an sql exception occurs
     */
    @Override
    public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
        Biblio bibliosDAO = new Biblio();

        bibliosDAO.setBiblio_key(Integer.parseInt(Utils.getDbValue(rs, "id_biblio")));
        bibliosDAO.setTitle(Utils.getDbValue(rs, "title"));
        bibliosDAO.setAuthor1(Utils.getDbValue(rs, "author1"));
        bibliosDAO.setAuthor2(Utils.getDbValue(rs, "author2"));
        bibliosDAO.setYear(Utils.getDbValue(rs, "year"));
        bibliosDAO.setJournal(Utils.getDbValue(rs, "journal"));
        bibliosDAO.setUsername(Utils.getDbValue(rs, "username"));
        bibliosDAO.setVolume(Utils.getDbValue(rs, "volume"));
        bibliosDAO.setPages(Utils.getDbValue(rs, "pages"));
        bibliosDAO.setPubmed_id(Utils.getDbValue(rs, "pubmed_id"));
        bibliosDAO.setUpdated(Utils.getDbValue(rs, "updated"));
        bibliosDAO.setLast_change(Utils.getDbDateValue(rs, "last_change"));
        bibliosDAO.setNotes(Utils.getDbValue(rs, "notes"));

        return bibliosDAO;
    }

    // SETTERS AND GETTERS

    public Integer getBiblio_key() {
        return biblio_key;
    }

    public void setBiblio_key(Integer biblio_key) {
        this.biblio_key = biblio_key;
    }

    public Set<Strain> getStrains() {
        return strains;
    }
    
    public void setStrains(Set<Strain> strains) {
        this.strains = strains;
    }

    public String getStrain_keys() {
        return strain_keys;
    }

    public void setStrain_keys(String strain_keys) {
        this.strain_keys = strain_keys;
    }

    public String getAuthor1() {
        return author1;
    }

    public void setAuthor1(String author1) {
        this.author1 = author1;
    }

    public String getAuthor2() {
        return author2;
    }

    public void setAuthor2(String author2) {
        this.author2 = author2;
    }

    public String getJournal() {
        return journal;
    }

    public void setJournal(String journal) {
        this.journal = journal;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getPubmed_id() {
        return pubmed_id;
    }

    public void setPubmed_id(String pubmed_id) {
        this.pubmed_id = pubmed_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUpdated() {
        return updated;
    }

    public void setUpdated(String updated) {
        this.updated = updated;
    }

    public String getVolume() {
        return volume;
    }

    public void setVolume(String volume) {
        this.volume = volume;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Date getLast_change() {
        return last_change;
    }

    public void setLast_change(Date last_change) {
        this.last_change = last_change;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
    
    /**
     * This getter/setter pair permits spring <code>JdbcTemplate</code> to be
     * used in non-Hibernate situations where jdbc is the preferred pattern.
     * @return the <code>JdbcTemplate</code> object instantiated by spring (see
     * jobApplicationContext.xml).
     */
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }

    public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    
    /**
     * This getter/setter pair permits spring <code>DataSourceTransactionManager</code>
     * (an implementation of the <code>PlatformTransactionManager</code> interface)
     * to be used in non-Hibernate situations where dataSourceTransactionManager
     * is the preferred pattern.
     * @return the <code>DataSourceTransactionManager</code> object instantiated by spring (see
     * jobApplicationContext.xml).
     */
    public PlatformTransactionManager getPlatformTransactionManager() {
        return platformTransactionManager;
    }

    public void setPlatformTransactionManager(PlatformTransactionManager platformTransactionManager) {
        this.platformTransactionManager = platformTransactionManager;
    }
}
