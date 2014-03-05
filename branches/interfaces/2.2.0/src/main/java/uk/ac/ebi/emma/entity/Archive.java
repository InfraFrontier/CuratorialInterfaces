 /**
 * Copyright © 2008 EMBL - European Bioinformatics Institute
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

/**
 *
 * @author phil, mrelac
 */
public class Archive {
    private Integer id = 0;                                                     // primary key

    private String archived;
    private String archiving_method_id;
    private String breeding;
    private String embryo_state;
    private String evaluated;
    private String females;
    private String freezing_started;
    private String freezingtime;
    //addition for sanger embryos
    private String frozen_sanger_embryos_arrived;
    //end
    private String males;
    private String notes;
    private String pdfURL;
    private String received;
    private String submitted;
    private String timetoarchive;
    private String wt_received;
    private String wt_rederiv_started;
    
    // FOREIGN KEYS
    private Integer female_bg_id;                                               // foreign key to backgrounds table for females
    private Integer lab_id_labo;                                                // foreign key to laboratories table
    private Integer male_bg_id;                                                 // foreign key to backgrounds table for females
    private Integer str_id_str;                                                 // foreign key to strains table

    // CLASS INSTANCES
    private CvArchivingMethod archivingMethod;                                  // (was cvamDAO)
    private Laboratory laboratory;                                              // (was labsDAO)
    private Strain strain;                                                      // (was strainsDAO)

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getArchived() {
        return archived;
    }

    public void setArchived(String archived) {
        this.archived = archived;
    }

    public String getArchiving_method_id() {
        return archiving_method_id;
    }

    public void setArchiving_method_id(String archiving_method_id) {
        this.archiving_method_id = archiving_method_id;
    }

    public String getBreeding() {
        return breeding;
    }

    public void setBreeding(String breeding) {
        this.breeding = breeding;
    }

    public String getEmbryo_state() {
        return embryo_state;
    }

    public void setEmbryo_state(String embryo_state) {
        this.embryo_state = embryo_state;
    }

    public String getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(String evaluated) {
        this.evaluated = evaluated;
    }

    public String getFemales() {
        return females;
    }

    public void setFemales(String females) {
        this.females = females;
    }

    public String getFreezing_started() {
        return freezing_started;
    }

    public void setFreezing_started(String freezing_started) {
        this.freezing_started = freezing_started;
    }

    public String getFreezingtime() {
        return freezingtime;
    }

    public void setFreezingtime(String freezingtime) {
        this.freezingtime = freezingtime;
    }

    public String getFrozen_sanger_embryos_arrived() {
        return frozen_sanger_embryos_arrived;
    }

    public void setFrozen_sanger_embryos_arrived(String frozen_sanger_embryos_arrived) {
        this.frozen_sanger_embryos_arrived = frozen_sanger_embryos_arrived;
    }

    public String getMales() {
        return males;
    }

    public void setMales(String males) {
        this.males = males;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getPdfURL() {
        return pdfURL;
    }

    public void setPdfURL(String pdfURL) {
        this.pdfURL = pdfURL;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
    }

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public String getTimetoarchive() {
        return timetoarchive;
    }

    public void setTimetoarchive(String timetoarchive) {
        this.timetoarchive = timetoarchive;
    }

    public String getWt_received() {
        return wt_received;
    }

    public void setWt_received(String wt_received) {
        this.wt_received = wt_received;
    }

    public String getWt_rederiv_started() {
        return wt_rederiv_started;
    }

    public void setWt_rederiv_started(String wt_rederiv_started) {
        this.wt_rederiv_started = wt_rederiv_started;
    }

    public Integer getFemale_bg_id() {
        return female_bg_id;
    }

    public void setFemale_bg_id(Integer female_bg_id) {
        this.female_bg_id = female_bg_id;
    }

    public Integer getLab_id_labo() {
        return lab_id_labo;
    }

    public void setLab_id_labo(Integer lab_id_labo) {
        this.lab_id_labo = lab_id_labo;
    }

    public Integer getMale_bg_id() {
        return male_bg_id;
    }

    public void setMale_bg_id(Integer male_bg_id) {
        this.male_bg_id = male_bg_id;
    }

    public Integer getStr_id_str() {
        return str_id_str;
    }

    public void setStr_id_str(Integer str_id_str) {
        this.str_id_str = str_id_str;
    }

    public CvArchivingMethod getArchivingMethod() {
        return archivingMethod;
    }

    public void setArchivingMethod(CvArchivingMethod archivingMethod) {
        this.archivingMethod = archivingMethod;
    }

    public Laboratory getLaboratory() {
        return laboratory;
    }

    public void setLaboratory(Laboratory laboratory) {
        this.laboratory = laboratory;
    }

    public Strain getStrain() {
        return strain;
    }

    public void setStrain(Strain strain) {
        this.strain = strain;
    }

}
