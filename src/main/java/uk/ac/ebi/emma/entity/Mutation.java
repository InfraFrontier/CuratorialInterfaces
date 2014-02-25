/*
 * AvailabilitiesStrainsDAO.java
 *
 * Created on 07 January 2008, 14:49
 *
 */

package uk.ac.ebi.emma.entity;

/**
 *
 * @author phil
 */
public class Mutation {
    private Integer id;
    private String main_type;
    private String sub_type;
    private String dominance;
    private String tm_esline;
    private String ch_ano_name;
    private String ch_ano_desc;
    private String mu_cause;
    private int alls_id_allel;
    private String bg_id_bg;
    private String str_id_str;
    private String sex;
    private String genotype;
    private String ki_alter;
    private String username;
    private String last_change;
    private int alls_id_allel_replaced;
    private String chromosome;

    private Allele allele;
    private Background background;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public int getAlls_id_allel() {
        return alls_id_allel;
    }

    public void setAlls_id_allel(int alls_id_allel) {
        this.alls_id_allel = alls_id_allel;
    }

    public String getBg_id_bg() {
        return bg_id_bg;
    }

    public void setBg_id_bg(String bg_id_bg) {
        this.bg_id_bg = bg_id_bg;
    }

    public String getStr_id_str() {
        return str_id_str;
    }

    public void setStr_id_str(String str_id_str) {
        this.str_id_str = str_id_str;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getGenotype() {
        return genotype;
    }

    public void setGenotype(String genotype) {
        this.genotype = genotype;
    }

    public String getKi_alter() {
        return ki_alter;
    }

    public void setKi_alter(String ki_alter) {
        this.ki_alter = ki_alter;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLast_change() {
        return last_change;
    }

    public void setLast_change(String last_change) {
        this.last_change = last_change;
    }

    public int getAlls_id_allel_replaced() {
        return alls_id_allel_replaced;
    }

    public void setAlls_id_allel_replaced(int alls_id_allel_replaced) {
        this.alls_id_allel_replaced = alls_id_allel_replaced;
    }

    public String getChromosome() {
        return chromosome;
    }

    public void setChromosome(String chromosome) {
        this.chromosome = chromosome;
    }

    public Allele getAllele() {
        return allele;
    }

    public void setAllele(Allele allele) {
        this.allele = allele;
    }

    public Background getBackground() {
        return background;
    }

    public void setBackground(Background background) {
        this.background = background;
    }



}
