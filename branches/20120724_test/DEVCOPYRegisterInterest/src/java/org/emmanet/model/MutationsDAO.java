/*
 * AvailabilitiesStrainsDAO.java
 *
 * Created on 07 January 2008, 14:49
 *
 */

package org.emmanet.model;

/**
 *
 * @author phil
 */
public class MutationsDAO {
    private int id;
    private String main_type;
    private String sub_type;
    private String dominance;
    private String tm_esline;
    private String ch_ano_name;
    private String ch_ano_desc;
    private String mu_cause;
    private int alls_id_allel;
    private String bg_id_bg;
    
    private AllelesDAO allelesDAO;
    private BackgroundDAO backgroundDAO;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMain_type() {
        return main_type;
    }

    public void setMain_type(String main_type) {
        this.main_type = main_type;
    }

    public String getSub_type() {
        return sub_type;
    }

    public void setSub_type(String sub_type) {
        this.sub_type = sub_type;
    }

    public int getAlls_id_allel() {
        return alls_id_allel;
    }

    public void setAlls_id_allel(int alls_id_allel) {
        this.alls_id_allel = alls_id_allel;
    }

    public AllelesDAO getAllelesDAO() {
        return allelesDAO;
    }

    public void setAllelesDAO(AllelesDAO allelesDAO) {
        this.allelesDAO = allelesDAO;
    }

    public BackgroundDAO getBackgroundDAO() {
        return backgroundDAO;
    }

    public void setBackgroundDAO(BackgroundDAO backgroundDAO) {
        this.backgroundDAO = backgroundDAO;
    }

    public String getDominance() {
        return dominance;
    }

    public void setDominance(String dominance) {
        this.dominance = dominance;
    }

    public String getTm_esline() {
        return tm_esline;
    }

    public void setTm_esline(String tm_esline) {
        this.tm_esline = tm_esline;
    }

    public String getCh_ano_name() {
        return ch_ano_name;
    }

    public void setCh_ano_name(String ch_ano_name) {
        this.ch_ano_name = ch_ano_name;
    }

    public String getCh_ano_desc() {
        return ch_ano_desc;
    }

    public void setCh_ano_desc(String ch_ano_desc) {
        this.ch_ano_desc = ch_ano_desc;
    }

    public String getMu_cause() {
        return mu_cause;
    }

    public void setMu_cause(String mu_cause) {
        this.mu_cause = mu_cause;
    }

    public String getBg_id_bg() {
        return bg_id_bg;
    }

    public void setBg_id_bg(String bg_id_bg) {
        this.bg_id_bg = bg_id_bg;
    }

}
