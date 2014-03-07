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
    private Integer archive_key = 0;                                           // primary key (was id)

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
    private Integer backgroundFemale_key;                                      // foreign key to backgrounds table for females (was female_bg_id)
    private Integer laboratory_key;                                            // foreign key to laboratories table (was lab_id_labo)
    private Integer backgroundMale_key;                                        // foreign key to backgrounds table for females (was male_bg_id
    private Integer strain_key;                                                // foreign key to strains table (was str_id_str)

    // CLASS INSTANCES
    private CvArchivingMethod archivingMethod;                                  // (was cvamDAO)
    private Laboratory        laboratory;                                       // (was labsDAO)
    private Strain            strain;                                           // (was strainsDAO)

    public Integer getArchive_key() {
        return archive_key;
    }

    public void setArchive_key(Integer archive_key) {
        this.archive_key = archive_key;
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

    public Integer getBackgroundFemale_key() {
        return backgroundFemale_key;
    }

    public void setBackgroundFemale_key(Integer backgroundFemale_key) {
        this.backgroundFemale_key = backgroundFemale_key;
    }

    public Integer getLaboratory_key() {
        return laboratory_key;
    }

    public void setLaboratory_key(Integer laboratory_key) {
        this.laboratory_key = laboratory_key;
    }

    public Integer getBackgroundMale_key() {
        return backgroundMale_key;
    }

    public void setBackgroundMale_key(Integer backgroundMale_key) {
        this.backgroundMale_key = backgroundMale_key;
    }

    public Integer getStrain_key() {
        return strain_key;
    }

    public void setStrain_key(Integer strain_key) {
        this.strain_key = strain_key;
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
