/*
 * ArchiveDAO.java
 *
 * Created on 10 March 2008, 15:24
 *
 */
package org.emmanet.model;

import java.util.Set;

/**
 *
 * @author phil
 */
public class ArchiveDAO {

    private String str_id_str;
    private String submitted;
    private String evaluated;
    private String received;
    private String wt_received;
    private String wt_rederiv_started;
    private String freezing_started;
    //addition for sanger embryos
    private String frozen_sanger_embryos_arrived;
    //end
    private String archived;
    private String lab_id_labo;
    private String notes;
    private int id;
    private String breeding;
    private String archiving_method_id;
    private String males;
    private String females;
    private String male_bg_id;
    private String female_bg_id;
    private String embryo_state;
    private String timetoarchive;
    private String freezingtime;
    private StrainsDAO strainsDAO;
    private LabsDAO labsDAO;
    // private Set wrDAO;
    private CVArchivingMethodDAO cvamDAO;
    private String pdfURL;

    public String getStr_id_str() {
        return str_id_str;
    }

    public void setStr_id_str(String str_id_str) {
        this.str_id_str = str_id_str;
    }

    public String getSubmitted() {
        return submitted;
    }

    public void setSubmitted(String submitted) {
        this.submitted = submitted;
    }

    public String getEvaluated() {
        return evaluated;
    }

    public void setEvaluated(String evaluated) {
        this.evaluated = evaluated;
    }

    public String getReceived() {
        return received;
    }

    public void setReceived(String received) {
        this.received = received;
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

    public String getArchived() {
        return archived;
    }

    public void setArchived(String archived) {
        this.archived = archived;
    }

    public String getLab_id_labo() {
        return lab_id_labo;
    }

    public void setLab_id_labo(String lab_id_labo) {
        this.lab_id_labo = lab_id_labo;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTimetoarchive() {
        return timetoarchive;
    }

    public void setTimetoarchive(String timetoarchive) {
        this.timetoarchive = timetoarchive;
    }

    public String getFreezingtime() {
        return freezingtime;
    }

    public void setFreezingtime(String freezingtime) {
        this.freezingtime = freezingtime;
    }

    public String getFreezing_started() {
        return freezing_started;
    }

    public void setFreezing_started(String freezing_started) {
        this.freezing_started = freezing_started;
    }

    public StrainsDAO getStrainsDAO() {
        return strainsDAO;
    }

    public void setStrainsDAO(StrainsDAO strainsDAO) {
        this.strainsDAO = strainsDAO;
    }


    /*  public Sources_StrainsDAO getSources_strainsDAO() {
    return sources_strainsDAO;
    }
    
    public void setSources_strainsDAO(Sources_StrainsDAO sources_strainsDAO) {
    this.sources_strainsDAO = sources_strainsDAO;
    }*/
    public LabsDAO getLabsDAO() {
        return labsDAO;
    }

    public void setLabsDAO(LabsDAO labsDAO) {
        this.labsDAO = labsDAO;
    }

    public String getPdfURL() {
        return pdfURL;
    }

    public void setPdfURL(String pdfURL) {
        this.pdfURL = pdfURL;
    }

    public Set getWrDAO() {
        return null;//wrDAO;
    }

    public void setWrDAO(Set wrDAO) {
        //    this.wrDAO = wrDAO;
    }

    public String getBreeding() {
        return breeding;
    }

    public void setBreeding(String breeding) {
        this.breeding = breeding;
    }

    public String getArchiving_method_id() {
        return archiving_method_id;
    }

    public void setArchiving_method_id(String archiving_method_id) {
        this.archiving_method_id = archiving_method_id;
    }

    public String getMales() {
        return males;
    }

    public void setMales(String males) {
        this.males = males;
    }

    public String getFemales() {
        return females;
    }

    public void setFemales(String females) {
        this.females = females;
    }

    public String getMale_bg_id() {
        return male_bg_id;
    }

    public void setMale_bg_id(String male_bg_id) {
        this.male_bg_id = male_bg_id;
    }

    public String getFemale_bg_id() {
        return female_bg_id;
    }

    public void setFemale_bg_id(String female_bg_id) {
        this.female_bg_id = female_bg_id;
    }

    public String getEmbryo_state() {
        return embryo_state;
    }

    public void setEmbryo_state(String embryo_state) {
        this.embryo_state = embryo_state;
    }

    public CVArchivingMethodDAO getCvamDAO() {
        return cvamDAO;
    }

    public void setCvamDAO(CVArchivingMethodDAO cvamDAO) {
        this.cvamDAO = cvamDAO;
    }

    public String getFrozen_sanger_embryos_arrived() {
        return frozen_sanger_embryos_arrived;
    }

    public void setFrozen_sanger_embryos_arrived(String frozen_sanger_embryos_arrived) {
        this.frozen_sanger_embryos_arrived = frozen_sanger_embryos_arrived;
    }
}
