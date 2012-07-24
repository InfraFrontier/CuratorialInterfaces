/*
 * A model of submission form data
 * 
 */
package org.emmanet.model;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author phil
 */
public class SubmissionsDAO implements Serializable {

    private List cvDAO;
    private List bgDAO;
    private String id_sub;
    private String encryptedId_sub;
    private String timestamp;
    private String submitter_email;
    private String submitter_title;
    private String submitter_firstname;
    private String submitter_lastname;
    private String submitter_tel;
    private String submitter_fax;
    private String submitter_inst;
    private String submitter_dept;
    private String submitter_addr_1;
    private String submitter_addr_2;
    private String submitter_city;
    private String submitter_county;
    private String submitter_postcode;
    private String submitter_country;
    private int per_id_per_sub;
    private String producer_email;
    private String producer_title;
    private String producer_firstname;
    private String producer_lastname;
    private String producer_tel;
    private String producer_fax;
    private String producer_inst;
    private String producer_dept;
    private String producer_addr_1;
    private String producer_addr_2;
    private String producer_city;
    private String producer_county;
    private String producer_postcode;
    private String producer_country;
    private String producer_ilar;
    private int per_id_per;
    private String shipper_email;
    private String shipper_title;
    private String shipper_firstname;
    private String shipper_lastname;
    private String shipper_tel;
    private String shipper_fax;
    private String shipper_inst;
    private String shipper_dept;
    private String shipper_addr_1;
    private String shipper_addr_2;
    private String shipper_city;
    private String shipper_county;
    private String shipper_postcode;
    private String shipper_country;
    private int per_id_per_contact;
    private String strain_name;
    private String genetic_descr;
    private int current_backg;
    private String current_backg_text;
    private String backcrosses;
    private String sibmatings;
    private String breeding_history;
    private String homozygous_phenotypic_descr;
    private String heterozygous_phenotypic_descr;
    //mutations
    private String mutation_original_backg;
    //bibliography
    private String published;
    private String notes;
    private String reference_descr_text_0;
    private String pubmed_id;
    private String title;
    private String author1;
    private String journal;
    private String year;
    private String volume;
    private String pages;
    private String addReference;
    private String removeReference;
    
    
    private String genotyping;
    private String genotyping_file;
    private String phenotyping;
    private String phenotyping_file;
    private String othertyping;
    private String othertyping_file;
    private String homozygous_viable;
    private String homozygous_viableYes;
    private String homozygous_viableNo;
    private String homozygous_viableMales;
    private String homozygous_viableFemales;
    private String homozygous_viableUnknown;
    private String homozygous_fertile;
    private String homozygous_fertileYes;
    private String homozygous_fertileNo;
    private String homozygous_fertileMales;
    private String homozygous_fertileemales;
    private String homozygous_fertileUnknown;
    private String heterozygous_fertile;
    private String heterozygous_fertileYes;
    private String heterozygous_fertileNo;
    private String heterozygous_fertileMales;
    private String heterozygous_fertileFemales;
    private String heterozygous_fertileUnknown;
    private String homozygous_matings_required;
    private String homozygous_matings_requiredYes;
    private String homozygous_matings_requiredNo;
    private String homozygous_matings_requiredUknown;
    private String homozygous_matings_required_text;
    private String reproductive_maturity_age;
    private String reproductive_decline_age;
    private String gestation_length;
    private String pups_at_birth;
    private String pups_at_weaning;
    private String weaning_age;
    private String litters_in_lifetime;
    private String breeding_performance;
    private String husbandry_requirements;
    private String immunocompromised;
    private String immunocompromisedYes;
    private String immunocompromisedNo;
    private String immunocompromisedUnknown;
    private String sanitary_status;
    private String sanitary_status_file;
    private String welfare;
    private String remedial_actions;
    private String human_condition;
    private String human_conditionYes;
    private String human_conditionNo;
    private String human_conditionUnknown;
    private String human_condition_omim;
    private String human_condition_text;
    private String human_condition_more;
    private String research_areas;
    
    private String research_tools;
    private String research_areas_other_text;
    private String research_tools_cre;
    private String research_tools_cre_text;
    private String research_tools_loxp;
    private String research_tools_flp;
    private String research_tools_flp_text;
    private String research_tools_frt;
    private String research_tools_tet;
    private String research_tools_other;
    private String research_tools_other_text;
    private String past_requests;
    private String deposited_elsewhere;
    private String deposited_elsewhere_text;
    private String similar_strains;
    private String ip_rights;
    private String ip_rights_text;
    private String exclusive_owner;
    private String exclusive_owner_text;
    private String owner_permission;
    private String owner_permission_text;
    private String delayed_release;
    private String delayed_release_text;
    private String mice_avail_month;
    private String mice_avail_year;
    private String mice_avail_males;
    private String mice_avail_females;
    private String additional_materials_file_1;
    private String additional_materials_file_2;
    private String additional_materials_file_3;
    private String additional_materials_file_4;
    private String additional_materials_file_5;
    
    private String step;
    private String encryptedStep;
    
    private List biblioRefs;

    /**
     * @return the submitter_email
     */
    public String getSubmitter_email() {
        return submitter_email;
    }

    /**
     * @param submitter_email the submitter_email to set
     */
    public void setSubmitter_email(String submitter_email) {
        this.submitter_email = submitter_email;
    }

    /**
     * @return the submitter_title
     */
    public String getSubmitter_title() {
        return submitter_title;
    }

    /**
     * @param submitter_title the submitter_title to set
     */
    public void setSubmitter_title(String submitter_title) {
        this.submitter_title = submitter_title;
    }

    /**
     * @return the submitter_firstname
     */
    public String getSubmitter_firstname() {
        return submitter_firstname;
    }

    /**
     * @param submitter_firstname the submitter_firstname to set
     */
    public void setSubmitter_firstname(String submitter_firstname) {
        this.submitter_firstname = submitter_firstname;
    }

    /**
     * @return the submitter_lastname
     */
    public String getSubmitter_lastname() {
        return submitter_lastname;
    }

    /**
     * @param submitter_lastname the submitter_lastname to set
     */
    public void setSubmitter_lastname(String submitter_lastname) {
        this.submitter_lastname = submitter_lastname;
    }

    /**
     * @return the submitter_tel
     */
    public String getSubmitter_tel() {
        return submitter_tel;
    }

    /**
     * @param submitter_tel the submitter_tel to set
     */
    public void setSubmitter_tel(String submitter_tel) {
        this.submitter_tel = submitter_tel;
    }

    /**
     * @return the submitter_fax
     */
    public String getSubmitter_fax() {
        return submitter_fax;
    }

    /**
     * @param submitter_fax the submitter_fax to set
     */
    public void setSubmitter_fax(String submitter_fax) {
        this.submitter_fax = submitter_fax;
    }

    /**
     * @return the submitter_inst
     */
    public String getSubmitter_inst() {
        return submitter_inst;
    }

    /**
     * @param submitter_inst the submitter_inst to set
     */
    public void setSubmitter_inst(String submitter_inst) {
        this.submitter_inst = submitter_inst;
    }

    /**
     * @return the submitter_dept
     */
    public String getSubmitter_dept() {
        return submitter_dept;
    }

    /**
     * @param submitter_dept the submitter_dept to set
     */
    public void setSubmitter_dept(String submitter_dept) {
        this.submitter_dept = submitter_dept;
    }

    /**
     * @return the submitter_addr_1
     */
    public String getSubmitter_addr_1() {
        return submitter_addr_1;
    }

    /**
     * @param submitter_addr_1 the submitter_addr_1 to set
     */
    public void setSubmitter_addr_1(String submitter_addr_1) {
        this.submitter_addr_1 = submitter_addr_1;
    }

    /**
     * @return the submitter_addr_2
     */
    public String getSubmitter_addr_2() {
        return submitter_addr_2;
    }

    /**
     * @param submitter_addr_2 the submitter_addr_2 to set
     */
    public void setSubmitter_addr_2(String submitter_addr_2) {
        this.submitter_addr_2 = submitter_addr_2;
    }

    /**
     * @return the submitter_city
     */
    public String getSubmitter_city() {
        return submitter_city;
    }

    /**
     * @param submitter_city the submitter_city to set
     */
    public void setSubmitter_city(String submitter_city) {
        this.submitter_city = submitter_city;
    }

    /**
     * @return the submitter_county
     */
    public String getSubmitter_county() {
        return submitter_county;
    }

    /**
     * @param submitter_county the submitter_county to set
     */
    public void setSubmitter_county(String submitter_county) {
        this.submitter_county = submitter_county;
    }

    /**
     * @return the submitter_postcode
     */
    public String getSubmitter_postcode() {
        return submitter_postcode;
    }

    /**
     * @param submitter_postcode the submitter_postcode to set
     */
    public void setSubmitter_postcode(String submitter_postcode) {
        this.submitter_postcode = submitter_postcode;
    }

    /**
     * @return the submitter_country
     */
    public String getSubmitter_country() {
        return submitter_country;
    }

    /**
     * @param submitter_country the submitter_country to set
     */
    public void setSubmitter_country(String submitter_country) {
        this.submitter_country = submitter_country;
    }

    /**
     * @return the producer_email
     */
    public String getProducer_email() {
        return producer_email;
    }

    /**
     * @param producer_email the producer_email to set
     */
    public void setProducer_email(String producer_email) {
        this.producer_email = producer_email;
    }

    /**
     * @return the producer_title
     */
    public String getProducer_title() {
        return producer_title;
    }

    /**
     * @param producer_title the producer_title to set
     */
    public void setProducer_title(String producer_title) {
        this.producer_title = producer_title;
    }

    /**
     * @return the producer_firstname
     */
    public String getProducer_firstname() {
        return producer_firstname;
    }

    /**
     * @param producer_firstname the producer_firstname to set
     */
    public void setProducer_firstname(String producer_firstname) {
        this.producer_firstname = producer_firstname;
    }

    /**
     * @return the producer_lastname
     */
    public String getProducer_lastname() {
        return producer_lastname;
    }

    /**
     * @param producer_lastname the producer_lastname to set
     */
    public void setProducer_lastname(String producer_lastname) {
        this.producer_lastname = producer_lastname;
    }

    /**
     * @return the producer_tel
     */
    public String getProducer_tel() {
        return producer_tel;
    }

    /**
     * @param producer_tel the producer_tel to set
     */
    public void setProducer_tel(String producer_tel) {
        this.producer_tel = producer_tel;
    }

    /**
     * @return the producer_fax
     */
    public String getProducer_fax() {
        return producer_fax;
    }

    /**
     * @param producer_fax the producer_fax to set
     */
    public void setProducer_fax(String producer_fax) {
        this.producer_fax = producer_fax;
    }

    /**
     * @return the producer_inst
     */
    public String getProducer_inst() {
        return producer_inst;
    }

    /**
     * @param producer_inst the producer_inst to set
     */
    public void setProducer_inst(String producer_inst) {
        this.producer_inst = producer_inst;
    }

    /**
     * @return the producer_dept
     */
    public String getProducer_dept() {
        return producer_dept;
    }

    /**
     * @param producer_dept the producer_dept to set
     */
    public void setProducer_dept(String producer_dept) {
        this.producer_dept = producer_dept;
    }

    /**
     * @return the producer_addr_1
     */
    public String getProducer_addr_1() {
        return producer_addr_1;
    }

    /**
     * @param producer_addr_1 the producer_addr_1 to set
     */
    public void setProducer_addr_1(String producer_addr_1) {
        this.producer_addr_1 = producer_addr_1;
    }

    /**
     * @return the producer_addr_2
     */
    public String getProducer_addr_2() {
        return producer_addr_2;
    }

    /**
     * @param producer_addr_2 the producer_addr_2 to set
     */
    public void setProducer_addr_2(String producer_addr_2) {
        this.producer_addr_2 = producer_addr_2;
    }

    /**
     * @return the producer_city
     */
    public String getProducer_city() {
        return producer_city;
    }

    /**
     * @param producer_city the producer_city to set
     */
    public void setProducer_city(String producer_city) {
        this.producer_city = producer_city;
    }

    /**
     * @return the producer_county
     */
    public String getProducer_county() {
        return producer_county;
    }

    /**
     * @param producer_county the producer_county to set
     */
    public void setProducer_county(String producer_county) {
        this.producer_county = producer_county;
    }

    /**
     * @return the producer_postcode
     */
    public String getProducer_postcode() {
        return producer_postcode;
    }

    /**
     * @param producer_postcode the producer_postcode to set
     */
    public void setProducer_postcode(String producer_postcode) {
        this.producer_postcode = producer_postcode;
    }

    /**
     * @return the producer_country
     */
    public String getProducer_country() {
        return producer_country;
    }

    /**
     * @param producer_country the producer_country to set
     */
    public void setProducer_country(String producer_country) {
        this.producer_country = producer_country;
    }

    /**
     * @return the producer_ilar
     */
    public String getProducer_ilar() {
        return producer_ilar;
    }

    /**
     * @param producer_ilar the producer_ilar to set
     */
    public void setProducer_ilar(String producer_ilar) {
        this.producer_ilar = producer_ilar;
    }

    /**
     * @return the shipper_email
     */
    public String getShipper_email() {
        return shipper_email;
    }

    /**
     * @param shipper_email the shipper_email to set
     */
    public void setShipper_email(String shipper_email) {
        this.shipper_email = shipper_email;
    }

    /**
     * @return the shipper_title
     */
    public String getShipper_title() {
        return shipper_title;
    }

    /**
     * @param shipper_title the shipper_title to set
     */
    public void setShipper_title(String shipper_title) {
        this.shipper_title = shipper_title;
    }

    /**
     * @return the shipper_firstname
     */
    public String getShipper_firstname() {
        return shipper_firstname;
    }

    /**
     * @param shipper_firstname the shipper_firstname to set
     */
    public void setShipper_firstname(String shipper_firstname) {
        this.shipper_firstname = shipper_firstname;
    }

    /**
     * @return the shipper_lastname
     */
    public String getShipper_lastname() {
        return shipper_lastname;
    }

    /**
     * @param shipper_lastname the shipper_lastname to set
     */
    public void setShipper_lastname(String shipper_lastname) {
        this.shipper_lastname = shipper_lastname;
    }

    /**
     * @return the shipper_tel
     */
    public String getShipper_tel() {
        return shipper_tel;
    }

    /**
     * @param shipper_tel the shipper_tel to set
     */
    public void setShipper_tel(String shipper_tel) {
        this.shipper_tel = shipper_tel;
    }

    /**
     * @return the shipper_fax
     */
    public String getShipper_fax() {
        return shipper_fax;
    }

    /**
     * @param shipper_fax the shipper_fax to set
     */
    public void setShipper_fax(String shipper_fax) {
        this.shipper_fax = shipper_fax;
    }

    /**
     * @return the shipper_inst
     */
    public String getShipper_inst() {
        return shipper_inst;
    }

    /**
     * @param shipper_inst the shipper_inst to set
     */
    public void setShipper_inst(String shipper_inst) {
        this.shipper_inst = shipper_inst;
    }

    /**
     * @return the shipper_dept
     */
    public String getShipper_dept() {
        return shipper_dept;
    }

    /**
     * @param shipper_dept the shipper_dept to set
     */
    public void setShipper_dept(String shipper_dept) {
        this.shipper_dept = shipper_dept;
    }

    /**
     * @return the shipper_addr_1
     */
    public String getShipper_addr_1() {
        return shipper_addr_1;
    }

    /**
     * @param shipper_addr_1 the shipper_addr_1 to set
     */
    public void setShipper_addr_1(String shipper_addr_1) {
        this.shipper_addr_1 = shipper_addr_1;
    }

    /**
     * @return the shipper_addr_2
     */
    public String getShipper_addr_2() {
        return shipper_addr_2;
    }

    /**
     * @param shipper_addr_2 the shipper_addr_2 to set
     */
    public void setShipper_addr_2(String shipper_addr_2) {
        this.shipper_addr_2 = shipper_addr_2;
    }

    /**
     * @return the shipper_city
     */
    public String getShipper_city() {
        return shipper_city;
    }

    /**
     * @param shipper_city the shipper_city to set
     */
    public void setShipper_city(String shipper_city) {
        this.shipper_city = shipper_city;
    }

    /**
     * @return the shipper_county
     */
    public String getShipper_county() {
        return shipper_county;
    }

    /**
     * @param shipper_county the shipper_county to set
     */
    public void setShipper_county(String shipper_county) {
        this.shipper_county = shipper_county;
    }

    /**
     * @return the shipper_postcode
     */
    public String getShipper_postcode() {
        return shipper_postcode;
    }

    /**
     * @param shipper_postcode the shipper_postcode to set
     */
    public void setShipper_postcode(String shipper_postcode) {
        this.shipper_postcode = shipper_postcode;
    }

    /**
     * @return the shipper_country
     */
    public String getShipper_country() {
        return shipper_country;
    }

    /**
     * @param shipper_country the shipper_country to set
     */
    public void setShipper_country(String shipper_country) {
        this.shipper_country = shipper_country;
    }

    /**
     * @return the strain_name
     */
    public String getStrain_name() {
        return strain_name;
    }

    /**
     * @param strain_name the strain_name to set
     */
    public void setStrain_name(String strain_name) {
        this.strain_name = strain_name;
    }

    /**
     * @return the genetic_descr
     */
    public String getGenetic_descr() {
        return genetic_descr;
    }

    /**
     * @param genetic_descr the genetic_descr to set
     */
    public void setGenetic_descr(String genetic_descr) {
        this.genetic_descr = genetic_descr;
    }

    /**
     * @return the current_backg
     */
    public int getCurrent_backg() {
        return current_backg;
    }

    /**
     * @param current_backg the current_backg to set
     */
    public void setCurrent_backg(int current_backg) {
        this.current_backg = current_backg;
    }

    /**
     * @return the current_backg_text
     */
    public String getCurrent_backg_text() {
        return current_backg_text;
    }

    /**
     * @param current_backg_text the current_backg_text to set
     */
    public void setCurrent_backg_text(String current_backg_text) {
        this.current_backg_text = current_backg_text;
    }

    /**
     * @return the backcrosses
     */
    public String getBackcrosses() {
        return backcrosses;
    }

    /**
     * @param backcrosses the backcrosses to set
     */
    public void setBackcrosses(String backcrosses) {
        this.backcrosses = backcrosses;
    }

    /**
     * @return the sibmatings
     */
    public String getSibmatings() {
        return sibmatings;
    }

    /**
     * @param sibmatings the sibmatings to set
     */
    public void setSibmatings(String sibmatings) {
        this.sibmatings = sibmatings;
    }

    /**
     * @return the breeding_history
     */
    public String getBreeding_history() {
        return breeding_history;
    }

    /**
     * @param breeding_history the breeding_history to set
     */
    public void setBreeding_history(String breeding_history) {
        this.breeding_history = breeding_history;
    }

    /**
     * @return the homozygous_phenotypic_descr
     */
    public String getHomozygous_phenotypic_descr() {
        return homozygous_phenotypic_descr;
    }

    /**
     * @param homozygous_phenotypic_descr the homozygous_phenotypic_descr to set
     */
    public void setHomozygous_phenotypic_descr(String homozygous_phenotypic_descr) {
        this.homozygous_phenotypic_descr = homozygous_phenotypic_descr;
    }

    /**
     * @return the heterozygous_phenotypic_descr
     */
    public String getHeterozygous_phenotypic_descr() {
        return heterozygous_phenotypic_descr;
    }

    /**
     * @param heterozygous_phenotypic_descr the heterozygous_phenotypic_descr to set
     */
    public void setHeterozygous_phenotypic_descr(String heterozygous_phenotypic_descr) {
        this.heterozygous_phenotypic_descr = heterozygous_phenotypic_descr;
    }

    /**
     * @return the genotyping
     */
    public String getGenotyping() {
        return genotyping;
    }

    /**
     * @param genotyping the genotyping to set
     */
    public void setGenotyping(String genotyping) {
        this.genotyping = genotyping;
    }

    /**
     * @return the genotyping_file
     */
    public String getGenotyping_file() {
        return genotyping_file;
    }

    /**
     * @param genotyping_file the genotyping_file to set
     */
    public void setGenotyping_file(String genotyping_file) {
        this.genotyping_file = genotyping_file;
    }

    /**
     * @return the phenotyping
     */
    public String getPhenotyping() {
        return phenotyping;
    }

    /**
     * @param phenotyping the phenotyping to set
     */
    public void setPhenotyping(String phenotyping) {
        this.phenotyping = phenotyping;
    }

    /**
     * @return the phenotyping_file
     */
    public String getPhenotyping_file() {
        return phenotyping_file;
    }

    /**
     * @param phenotyping_file the phenotyping_file to set
     */
    public void setPhenotyping_file(String phenotyping_file) {
        this.phenotyping_file = phenotyping_file;
    }

    /**
     * @return the othertyping
     */
    public String getOthertyping() {
        return othertyping;
    }

    /**
     * @param othertyping the othertyping to set
     */
    public void setOthertyping(String othertyping) {
        this.othertyping = othertyping;
    }

    /**
     * @return the homozygous_viableYes
     */
    public String getHomozygous_viableYes() {
        return homozygous_viableYes;
    }

    /**
     * @param homozygous_viableYes the homozygous_viableYes to set
     */
    public void setHomozygous_viableYes(String homozygous_viableYes) {
        this.homozygous_viableYes = homozygous_viableYes;
    }

    /**
     * @return the homozygous_viableNo
     */
    public String getHomozygous_viableNo() {
        return homozygous_viableNo;
    }

    /**
     * @param homozygous_viableNo the homozygous_viableNo to set
     */
    public void setHomozygous_viableNo(String homozygous_viableNo) {
        this.homozygous_viableNo = homozygous_viableNo;
    }

    /**
     * @return the homozygous_viableMales
     */
    public String getHomozygous_viableMales() {
        return homozygous_viableMales;
    }

    /**
     * @param homozygous_viableMales the homozygous_viableMales to set
     */
    public void setHomozygous_viableMales(String homozygous_viableMales) {
        this.homozygous_viableMales = homozygous_viableMales;
    }

    /**
     * @return the homozygous_viableFemales
     */
    public String getHomozygous_viableFemales() {
        return homozygous_viableFemales;
    }

    /**
     * @param homozygous_viableFemales the homozygous_viableFemales to set
     */
    public void setHomozygous_viableFemales(String homozygous_viableFemales) {
        this.homozygous_viableFemales = homozygous_viableFemales;
    }

    /**
     * @return the homozygous_viableUnknown
     */
    public String getHomozygous_viableUnknown() {
        return homozygous_viableUnknown;
    }

    /**
     * @param homozygous_viableUnknown the homozygous_viableUnknown to set
     */
    public void setHomozygous_viableUnknown(String homozygous_viableUnknown) {
        this.homozygous_viableUnknown = homozygous_viableUnknown;
    }

    /**
     * @return the homozygous_fertileYes
     */
    public String getHomozygous_fertileYes() {
        return homozygous_fertileYes;
    }

    /**
     * @param homozygous_fertileYes the homozygous_fertileYes to set
     */
    public void setHomozygous_fertileYes(String homozygous_fertileYes) {
        this.homozygous_fertileYes = homozygous_fertileYes;
    }

    /**
     * @return the homozygous_fertileNo
     */
    public String getHomozygous_fertileNo() {
        return homozygous_fertileNo;
    }

    /**
     * @param homozygous_fertileNo the homozygous_fertileNo to set
     */
    public void setHomozygous_fertileNo(String homozygous_fertileNo) {
        this.homozygous_fertileNo = homozygous_fertileNo;
    }

    /**
     * @return the homozygous_fertileMales
     */
    public String getHomozygous_fertileMales() {
        return homozygous_fertileMales;
    }

    /**
     * @param homozygous_fertileMales the homozygous_fertileMales to set
     */
    public void setHomozygous_fertileMales(String homozygous_fertileMales) {
        this.homozygous_fertileMales = homozygous_fertileMales;
    }

    /**
     * @return the homozygous_fertileemales
     */
    public String getHomozygous_fertileemales() {
        return homozygous_fertileemales;
    }

    /**
     * @param homozygous_fertileemales the homozygous_fertileemales to set
     */
    public void setHomozygous_fertileemales(String homozygous_fertileemales) {
        this.homozygous_fertileemales = homozygous_fertileemales;
    }

    /**
     * @return the homozygous_fertileUnknown
     */
    public String getHomozygous_fertileUnknown() {
        return homozygous_fertileUnknown;
    }

    /**
     * @param homozygous_fertileUnknown the homozygous_fertileUnknown to set
     */
    public void setHomozygous_fertileUnknown(String homozygous_fertileUnknown) {
        this.homozygous_fertileUnknown = homozygous_fertileUnknown;
    }

    /**
     * @return the heterozygous_fertileYes
     */
    public String getHeterozygous_fertileYes() {
        return heterozygous_fertileYes;
    }

    /**
     * @param heterozygous_fertileYes the heterozygous_fertileYes to set
     */
    public void setHeterozygous_fertileYes(String heterozygous_fertileYes) {
        this.heterozygous_fertileYes = heterozygous_fertileYes;
    }

    /**
     * @return the heterozygous_fertileNo
     */
    public String getHeterozygous_fertileNo() {
        return heterozygous_fertileNo;
    }

    /**
     * @param heterozygous_fertileNo the heterozygous_fertileNo to set
     */
    public void setHeterozygous_fertileNo(String heterozygous_fertileNo) {
        this.heterozygous_fertileNo = heterozygous_fertileNo;
    }

    /**
     * @return the heterozygous_fertileMales
     */
    public String getHeterozygous_fertileMales() {
        return heterozygous_fertileMales;
    }

    /**
     * @param heterozygous_fertileMales the heterozygous_fertileMales to set
     */
    public void setHeterozygous_fertileMales(String heterozygous_fertileMales) {
        this.heterozygous_fertileMales = heterozygous_fertileMales;
    }

    /**
     * @return the heterozygous_fertileFemales
     */
    public String getHeterozygous_fertileFemales() {
        return heterozygous_fertileFemales;
    }

    /**
     * @param heterozygous_fertileFemales the heterozygous_fertileFemales to set
     */
    public void setHeterozygous_fertileFemales(String heterozygous_fertileFemales) {
        this.heterozygous_fertileFemales = heterozygous_fertileFemales;
    }

    /**
     * @return the heterozygous_fertileUnknown
     */
    public String getHeterozygous_fertileUnknown() {
        return heterozygous_fertileUnknown;
    }

    /**
     * @param heterozygous_fertileUnknown the heterozygous_fertileUnknown to set
     */
    public void setHeterozygous_fertileUnknown(String heterozygous_fertileUnknown) {
        this.heterozygous_fertileUnknown = heterozygous_fertileUnknown;
    }

    /**
     * @return the homozygous_matings_requiredYes
     */
    public String getHomozygous_matings_requiredYes() {
        return homozygous_matings_requiredYes;
    }

    /**
     * @param homozygous_matings_requiredYes the homozygous_matings_requiredYes to set
     */
    public void setHomozygous_matings_requiredYes(String homozygous_matings_requiredYes) {
        this.homozygous_matings_requiredYes = homozygous_matings_requiredYes;
    }

    /**
     * @return the homozygous_matings_requiredNo
     */
    public String getHomozygous_matings_requiredNo() {
        return homozygous_matings_requiredNo;
    }

    /**
     * @param homozygous_matings_requiredNo the homozygous_matings_requiredNo to set
     */
    public void setHomozygous_matings_requiredNo(String homozygous_matings_requiredNo) {
        this.homozygous_matings_requiredNo = homozygous_matings_requiredNo;
    }

    /**
     * @return the homozygous_matings_requiredUknown
     */
    public String getHomozygous_matings_requiredUknown() {
        return homozygous_matings_requiredUknown;
    }

    /**
     * @param homozygous_matings_requiredUknown the homozygous_matings_requiredUknown to set
     */
    public void setHomozygous_matings_requiredUknown(String homozygous_matings_requiredUknown) {
        this.homozygous_matings_requiredUknown = homozygous_matings_requiredUknown;
    }

    /**
     * @return the homozygous_matings_required_text
     */
    public String getHomozygous_matings_required_text() {
        return homozygous_matings_required_text;
    }

    /**
     * @param homozygous_matings_required_text the homozygous_matings_required_text to set
     */
    public void setHomozygous_matings_required_text(String homozygous_matings_required_text) {
        this.homozygous_matings_required_text = homozygous_matings_required_text;
    }

    /**
     * @return the reproductive_maturity_age
     */
    public String getReproductive_maturity_age() {
        return reproductive_maturity_age;
    }

    /**
     * @param reproductive_maturity_age the reproductive_maturity_age to set
     */
    public void setReproductive_maturity_age(String reproductive_maturity_age) {
        this.reproductive_maturity_age = reproductive_maturity_age;
    }

    /**
     * @return the reproductive_decline_age
     */
    public String getReproductive_decline_age() {
        return reproductive_decline_age;
    }

    /**
     * @param reproductive_decline_age the reproductive_decline_age to set
     */
    public void setReproductive_decline_age(String reproductive_decline_age) {
        this.reproductive_decline_age = reproductive_decline_age;
    }

    /**
     * @return the gestation_length
     */
    public String getGestation_length() {
        return gestation_length;
    }

    /**
     * @param gestation_length the gestation_length to set
     */
    public void setGestation_length(String gestation_length) {
        this.gestation_length = gestation_length;
    }

    /**
     * @return the pups_at_birth
     */
    public String getPups_at_birth() {
        return pups_at_birth;
    }

    /**
     * @param pups_at_birth the pups_at_birth to set
     */
    public void setPups_at_birth(String pups_at_birth) {
        this.pups_at_birth = pups_at_birth;
    }

    /**
     * @return the pups_at_weaning
     */
    public String getPups_at_weaning() {
        return pups_at_weaning;
    }

    /**
     * @param pups_at_weaning the pups_at_weaning to set
     */
    public void setPups_at_weaning(String pups_at_weaning) {
        this.pups_at_weaning = pups_at_weaning;
    }

    /**
     * @return the weaning_age
     */
    public String getWeaning_age() {
        return weaning_age;
    }

    /**
     * @param weaning_age the weaning_age to set
     */
    public void setWeaning_age(String weaning_age) {
        this.weaning_age = weaning_age;
    }

    /**
     * @return the litters_in_lifetime
     */
    public String getLitters_in_lifetime() {
        return litters_in_lifetime;
    }

    /**
     * @param litters_in_lifetime the litters_in_lifetime to set
     */
    public void setLitters_in_lifetime(String litters_in_lifetime) {
        this.litters_in_lifetime = litters_in_lifetime;
    }

    /**
     * @return the breeding_performance
     */
    public String getBreeding_performance() {
        return breeding_performance;
    }

    /**
     * @param breeding_performance the breeding_performance to set
     */
    public void setBreeding_performance(String breeding_performance) {
        this.breeding_performance = breeding_performance;
    }

    /**
     * @return the husbandry_requirements
     */
    public String getHusbandry_requirements() {
        return husbandry_requirements;
    }

    /**
     * @param husbandry_requirements the husbandry_requirements to set
     */
    public void setHusbandry_requirements(String husbandry_requirements) {
        this.husbandry_requirements = husbandry_requirements;
    }

    /**
     * @return the immunocompromisedYes
     */
    public String getImmunocompromisedYes() {
        return immunocompromisedYes;
    }

    /**
     * @param immunocompromisedYes the immunocompromisedYes to set
     */
    public void setImmunocompromisedYes(String immunocompromisedYes) {
        this.immunocompromisedYes = immunocompromisedYes;
    }

    /**
     * @return the immunocompromisedNo
     */
    public String getImmunocompromisedNo() {
        return immunocompromisedNo;
    }

    /**
     * @param immunocompromisedNo the immunocompromisedNo to set
     */
    public void setImmunocompromisedNo(String immunocompromisedNo) {
        this.immunocompromisedNo = immunocompromisedNo;
    }

    /**
     * @return the immunocompromisedUnknown
     */
    public String getImmunocompromisedUnknown() {
        return immunocompromisedUnknown;
    }

    /**
     * @param immunocompromisedUnknown the immunocompromisedUnknown to set
     */
    public void setImmunocompromisedUnknown(String immunocompromisedUnknown) {
        this.immunocompromisedUnknown = immunocompromisedUnknown;
    }

    /**
     * @return the sanitary_status
     */
    public String getSanitary_status() {
        return sanitary_status;
    }

    /**
     * @param sanitary_status the sanitary_status to set
     */
    public void setSanitary_status(String sanitary_status) {
        this.sanitary_status = sanitary_status;
    }

    /**
     * @return the sanitary_status_file
     */
    public String getSanitary_status_file() {
        return sanitary_status_file;
    }

    /**
     * @param sanitary_status_file the sanitary_status_file to set
     */
    public void setSanitary_status_file(String sanitary_status_file) {
        this.sanitary_status_file = sanitary_status_file;
    }

    /**
     * @return the human_conditionYes
     */
    public String getHuman_conditionYes() {
        return human_conditionYes;
    }

    /**
     * @param human_conditionYes the human_conditionYes to set
     */
    public void setHuman_conditionYes(String human_conditionYes) {
        this.human_conditionYes = human_conditionYes;
    }

    /**
     * @return the human_conditionNo
     */
    public String getHuman_conditionNo() {
        return human_conditionNo;
    }

    /**
     * @param human_conditionNo the human_conditionNo to set
     */
    public void setHuman_conditionNo(String human_conditionNo) {
        this.human_conditionNo = human_conditionNo;
    }

    /**
     * @return the human_conditionUnknown
     */
    public String getHuman_conditionUnknown() {
        return human_conditionUnknown;
    }

    /**
     * @param human_conditionUnknown the human_conditionUnknown to set
     */
    public void setHuman_conditionUnknown(String human_conditionUnknown) {
        this.human_conditionUnknown = human_conditionUnknown;
    }

    /**
     * @return the human_condition_omim
     */
    public String getHuman_condition_omim() {
        return human_condition_omim;
    }

    /**
     * @param human_condition_omim the human_condition_omim to set
     */
    public void setHuman_condition_omim(String human_condition_omim) {
        this.human_condition_omim = human_condition_omim;
    }

    /**
     * @return the human_condition_text
     */
    public String getHuman_condition_text() {
        return human_condition_text;
    }

    /**
     * @param human_condition_text the human_condition_text to set
     */
    public void setHuman_condition_text(String human_condition_text) {
        this.human_condition_text = human_condition_text;
    }


    /**
     * @return the research_areas_other_text
     */
    public String getResearch_areas_other_text() {
        return research_areas_other_text;
    }

    /**
     * @param research_areas_other_text the research_areas_other_text to set
     */
    public void setResearch_areas_other_text(String research_areas_other_text) {
        this.research_areas_other_text = research_areas_other_text;
    }

    /**
     * @return the research_tools_cre
     */
    public String getResearch_tools_cre() {
        return research_tools_cre;
    }

    /**
     * @param research_tools_cre the research_tools_cre to set
     */
    public void setResearch_tools_cre(String research_tools_cre) {
        this.research_tools_cre = research_tools_cre;
    }

    /**
     * @return the research_tools_cre_text
     */
    public String getResearch_tools_cre_text() {
        return research_tools_cre_text;
    }

    /**
     * @param research_tools_cre_text the research_tools_cre_text to set
     */
    public void setResearch_tools_cre_text(String research_tools_cre_text) {
        this.research_tools_cre_text = research_tools_cre_text;
    }

    /**
     * @return the research_tools_loxp
     */
    public String getResearch_tools_loxp() {
        return research_tools_loxp;
    }

    /**
     * @param research_tools_loxp the research_tools_loxp to set
     */
    public void setResearch_tools_loxp(String research_tools_loxp) {
        this.research_tools_loxp = research_tools_loxp;
    }

    /**
     * @return the research_tools_flp
     */
    public String getResearch_tools_flp() {
        return research_tools_flp;
    }

    /**
     * @param research_tools_flp the research_tools_flp to set
     */
    public void setResearch_tools_flp(String research_tools_flp) {
        this.research_tools_flp = research_tools_flp;
    }

    /**
     * @return the research_tools_flp_text
     */
    public String getResearch_tools_flp_text() {
        return research_tools_flp_text;
    }

    /**
     * @param research_tools_flp_text the research_tools_flp_text to set
     */
    public void setResearch_tools_flp_text(String research_tools_flp_text) {
        this.research_tools_flp_text = research_tools_flp_text;
    }

    /**
     * @return the research_tools_frt
     */
    public String getResearch_tools_frt() {
        return research_tools_frt;
    }

    /**
     * @param research_tools_frt the research_tools_frt to set
     */
    public void setResearch_tools_frt(String research_tools_frt) {
        this.research_tools_frt = research_tools_frt;
    }

    /**
     * @return the research_tools_tet
     */
    public String getResearch_tools_tet() {
        return research_tools_tet;
    }

    /**
     * @param research_tools_tet the research_tools_tet to set
     */
    public void setResearch_tools_tet(String research_tools_tet) {
        this.research_tools_tet = research_tools_tet;
    }

    /**
     * @return the research_tools_other
     */
    public String getResearch_tools_other() {
        return research_tools_other;
    }

    /**
     * @param research_tools_other the research_tools_other to set
     */
    public void setResearch_tools_other(String research_tools_other) {
        this.research_tools_other = research_tools_other;
    }

    /**
     * @return the research_tools_other_text
     */
    public String getResearch_tools_other_text() {
        return research_tools_other_text;
    }

    /**
     * @param research_tools_other_text the research_tools_other_text to set
     */
    public void setResearch_tools_other_text(String research_tools_other_text) {
        this.research_tools_other_text = research_tools_other_text;
    }

    /**
     * @return the past_requests
     */
    public String getPast_requests() {
        return past_requests;
    }

    /**
     * @param past_requests the past_requests to set
     */
    public void setPast_requests(String past_requests) {
        this.past_requests = past_requests;
    }

    /**
     * @return the deposited_elsewhere_text
     */
    public String getDeposited_elsewhere_text() {
        return deposited_elsewhere_text;
    }

    /**
     * @param deposited_elsewhere_text the deposited_elsewhere_text to set
     */
    public void setDeposited_elsewhere_text(String deposited_elsewhere_text) {
        this.deposited_elsewhere_text = deposited_elsewhere_text;
    }

    /**
     * @return the ip_rights_text
     */
    public String getIp_rights_text() {
        return ip_rights_text;
    }

    /**
     * @param ip_rights_text the ip_rights_text to set
     */
    public void setIp_rights_text(String ip_rights_text) {
        this.ip_rights_text = ip_rights_text;
    }

    /**
     * @return the exclusive_owner_text
     */
    public String getExclusive_owner_text() {
        return exclusive_owner_text;
    }

    /**
     * @param exclusive_owner_text the exclusive_owner_text to set
     */
    public void setExclusive_owner_text(String exclusive_owner_text) {
        this.exclusive_owner_text = exclusive_owner_text;
    }

    /**
     * @return the owner_permission_text
     */
    public String getOwner_permission_text() {
        return owner_permission_text;
    }

    /**
     * @param owner_permission_text the owner_permission_text to set
     */
    public void setOwner_permission_text(String owner_permission_text) {
        this.owner_permission_text = owner_permission_text;
    }

    /**
     * @return the delayed_release_text
     */
    public String getDelayed_release_text() {
        return delayed_release_text;
    }

    /**
     * @param delayed_release_text the delayed_release_text to set
     */
    public void setDelayed_release_text(String delayed_release_text) {
        this.delayed_release_text = delayed_release_text;
    }

    /**
     * @return the mice_avail_month
     */
    public String getMice_avail_month() {
        return mice_avail_month;
    }

    /**
     * @param mice_avail_month the mice_avail_month to set
     */
    public void setMice_avail_month(String mice_avail_month) {
        this.mice_avail_month = mice_avail_month;
    }

    /**
     * @return the mice_avail_year
     */
    public String getMice_avail_year() {
        return mice_avail_year;
    }

    /**
     * @param mice_avail_year the mice_avail_year to set
     */
    public void setMice_avail_year(String mice_avail_year) {
        this.mice_avail_year = mice_avail_year;
    }

    /**
     * @return the mice_avail_males
     */
    public String getMice_avail_males() {
        return mice_avail_males;
    }

    /**
     * @param mice_avail_males the mice_avail_males to set
     */
    public void setMice_avail_males(String mice_avail_males) {
        this.mice_avail_males = mice_avail_males;
    }

    /**
     * @return the mice_avail_females
     */
    public String getMice_avail_females() {
        return mice_avail_females;
    }

    /**
     * @param mice_avail_females the mice_avail_females to set
     */
    public void setMice_avail_females(String mice_avail_females) {
        this.mice_avail_females = mice_avail_females;
    }

    /**
     * @return the additional_materials_file_1
     */
    public String getAdditional_materials_file_1() {
        return additional_materials_file_1;
    }

    /**
     * @param additional_materials_file_1 the additional_materials_file_1 to set
     */
    public void setAdditional_materials_file_1(String additional_materials_file_1) {
        this.additional_materials_file_1 = additional_materials_file_1;
    }

    /**
     * @return the additional_materials_file_2
     */
    public String getAdditional_materials_file_2() {
        return additional_materials_file_2;
    }

    /**
     * @param additional_materials_file_2 the additional_materials_file_2 to set
     */
    public void setAdditional_materials_file_2(String additional_materials_file_2) {
        this.additional_materials_file_2 = additional_materials_file_2;
    }

    /**
     * @return the additional_materials_file_3
     */
    public String getAdditional_materials_file_3() {
        return additional_materials_file_3;
    }

    /**
     * @param additional_materials_file_3 the additional_materials_file_3 to set
     */
    public void setAdditional_materials_file_3(String additional_materials_file_3) {
        this.additional_materials_file_3 = additional_materials_file_3;
    }

    /**
     * @return the additional_materials_file_4
     */
    public String getAdditional_materials_file_4() {
        return additional_materials_file_4;
    }

    /**
     * @param additional_materials_file_4 the additional_materials_file_4 to set
     */
    public void setAdditional_materials_file_4(String additional_materials_file_4) {
        this.additional_materials_file_4 = additional_materials_file_4;
    }

    /**
     * @return the additional_materials_file_5
     */
    public String getAdditional_materials_file_5() {
        return additional_materials_file_5;
    }

    /**
     * @param additional_materials_file_5 the additional_materials_file_5 to set
     */
    public void setAdditional_materials_file_5(String additional_materials_file_5) {
        this.additional_materials_file_5 = additional_materials_file_5;
    }

    /**
     * @return the per_id_per_sub
     */
    public int getPer_id_per_sub() {
        return per_id_per_sub;
    }

    /**
     * @param per_id_per_sub the per_id_per_sub to set
     */
    public void setPer_id_per_sub(int per_id_per_sub) {
        this.per_id_per_sub = per_id_per_sub;
    }

    /**
     * @return the per_id_per_contact
     */
    public int getPer_id_per_contact() {
        return per_id_per_contact;
    }

    /**
     * @param per_id_per_contact the per_id_per_contact to set
     */
    public void setPer_id_per_contact(int per_id_per_contact) {
        this.per_id_per_contact = per_id_per_contact;
    }

    /**
     * @return the per_id_per
     */
    public int getPer_id_per() {
        return per_id_per;
    }

    /**
     * @param per_id_per the per_id_per to set
     */
    public void setPer_id_per(int per_id_per) {
        this.per_id_per = per_id_per;
    }

    /**
     * @return the cvDAO
     */
    public List getCvDAO() {
        return cvDAO;
    }

    /**
     * @param cvDAO the cvDAO to set
     */
    public void setCvDAO(List cvDAO) {
        this.cvDAO = cvDAO;
    }

    /**
     * @return the mutation_original_backg_0
     */
    public String getMutation_original_backg() {
        return mutation_original_backg;
    }

    /**
     * @param mutation_original_backg_0 the mutation_original_backg_0 to set
     */
    public void setMutation_original_backg(String mutation_original_backg_0) {
        this.mutation_original_backg = mutation_original_backg_0;
    }

    /**
     * @return the published
     */
    public String getPublished() {
        return published;
    }

    /**
     * @param published the published to set
     */
    public void setPublished(String published) {
        this.published = published;
    }


    /**
     * @return the reference_descr_text_0
     */
    public String getReference_descr_text_0() {
        return reference_descr_text_0;
    }

    /**
     * @param reference_descr_text_0 the reference_descr_text_0 to set
     */
    public void setReference_descr_text_0(String reference_descr_text_0) {
        this.reference_descr_text_0 = reference_descr_text_0;
    }

    /**
     * @return the addReference
     */
    public String getAddReference() {
        return addReference;
    }

    /**
     * @param addReference the addReference to set
     */
    public void setAddReference(String addReference) {
        this.addReference = addReference;
    }

    /**
     * @return the removeReference
     */
    public String getRemoveReference() {
        return removeReference;
    }

    /**
     * @param removeReference the removeReference to set
     */
    public void setRemoveReference(String removeReference) {
        this.removeReference = removeReference;
    }

    /**
     * @return the othertyping_file
     */
    public String getOthertyping_file() {
        return othertyping_file;
    }

    /**
     * @param othertyping_file the othertyping_file to set
     */
    public void setOthertyping_file(String othertyping_file) {
        this.othertyping_file = othertyping_file;
    }

    /**
     * @return the homozygous_viable
     */
    public String getHomozygous_viable() {
        return homozygous_viable;
    }

    /**
     * @param homozygous_viable the homozygous_viable to set
     */
    public void setHomozygous_viable(String homozygous_viable) {
        this.homozygous_viable = homozygous_viable;
    }

    /**
     * @return the heterozygous_fertile
     */
    public String getHeterozygous_fertile() {
        return heterozygous_fertile;
    }

    /**
     * @param heterozygous_fertile the heterozygous_fertile to set
     */
    public void setHeterozygous_fertile(String heterozygous_fertile) {
        this.heterozygous_fertile = heterozygous_fertile;
    }

    /**
     * @return the homozygous_matings_required
     */
    public String getHomozygous_matings_required() {
        return homozygous_matings_required;
    }

    /**
     * @param homozygous_matings_required the homozygous_matings_required to set
     */
    public void setHomozygous_matings_required(String homozygous_matings_required) {
        this.homozygous_matings_required = homozygous_matings_required;
    }

    /**
     * @return the immunocompromised
     */
    public String getImmunocompromised() {
        return immunocompromised;
    }

    /**
     * @param immunocompromised the immunocompromised to set
     */
    public void setImmunocompromised(String immunocompromised) {
        this.immunocompromised = immunocompromised;
    }

    /**
     * @return the homozygous_fertile
     */
    public String getHomozygous_fertile() {
        return homozygous_fertile;
    }

    /**
     * @param homozygous_fertile the homozygous_fertile to set
     */
    public void setHomozygous_fertile(String homozygous_fertile) {
        this.homozygous_fertile = homozygous_fertile;
    }

    /**
     * @return the welfare
     */
    public String getWelfare() {
        return welfare;
    }

    /**
     * @param welfare the welfare to set
     */
    public void setWelfare(String welfare) {
        this.welfare = welfare;
    }

    /**
     * @return the remedial_actions
     */
    public String getRemedial_actions() {
        return remedial_actions;
    }

    /**
     * @param remedial_actions the remedial_actions to set
     */
    public void setRemedial_actions(String remedial_actions) {
        this.remedial_actions = remedial_actions;
    }

    /**
     * @return the human_condition
     */
    public String getHuman_condition() {
        return human_condition;
    }

    /**
     * @param human_condition the human_condition to set
     */
    public void setHuman_condition(String human_condition) {
        this.human_condition = human_condition;
    }

    /**
     * @return the human_condition_more
     */
    public String getHuman_condition_more() {
        return human_condition_more;
    }

    /**
     * @param human_condition_more the human_condition_more to set
     */
    public void setHuman_condition_more(String human_condition_more) {
        this.human_condition_more = human_condition_more;
    }

    /**
     * @return the research_areas
     */
    public String getResearch_areas() {
        return research_areas;
    }

    /**
     * @param research_areas the research_areas to set
     */
    public void setResearch_areas(String research_areas) {
        this.research_areas = research_areas;
    }

    /**
     * @return the deposited_elsewhere
     */
    public String getDeposited_elsewhere() {
        return deposited_elsewhere;
    }

    /**
     * @param deposited_elsewhere the deposited_elsewhere to set
     */
    public void setDeposited_elsewhere(String deposited_elsewhere) {
        this.deposited_elsewhere = deposited_elsewhere;
    }

    /**
     * @return the similar_strains
     */
    public String getSimilar_strains() {
        return similar_strains;
    }

    /**
     * @param similar_strains the similar_strains to set
     */
    public void setSimilar_strains(String similar_strains) {
        this.similar_strains = similar_strains;
    }

    /**
     * @return the ip_rights
     */
    public String getIp_rights() {
        return ip_rights;
    }

    /**
     * @param ip_rights the ip_rights to set
     */
    public void setIp_rights(String ip_rights) {
        this.ip_rights = ip_rights;
    }

    /**
     * @return the exclusive_owner
     */
    public String getExclusive_owner() {
        return exclusive_owner;
    }

    /**
     * @param exclusive_owner the exclusive_owner to set
     */
    public void setExclusive_owner(String exclusive_owner) {
        this.exclusive_owner = exclusive_owner;
    }

    /**
     * @return the owner_permission
     */
    public String getOwner_permission() {
        return owner_permission;
    }

    /**
     * @param owner_permission the owner_permission to set
     */
    public void setOwner_permission(String owner_permission) {
        this.owner_permission = owner_permission;
    }

    /**
     * @return the delayed_release
     */
    public String getDelayed_release() {
        return delayed_release;
    }

    /**
     * @param delayed_release the delayed_release to set
     */
    public void setDelayed_release(String delayed_release) {
        this.delayed_release = delayed_release;
    }

    /**
     * @return the id_sub
     */
    public String getId_sub() {
        return id_sub;
    }

    /**
     * @param id_sub the id_sub to set
     */
    public void setId_sub(String id_sub) {
        this.id_sub = id_sub;
    }

    /**
     * @return the research_tools
     */
    public String getResearch_tools() {
        return research_tools;
    }

    /**
     * @param research_tools the research_tools to set
     */
    public void setResearch_tools(String research_tools) {
        this.research_tools = research_tools;
    }

    /**
     * @return the biblioRefs
     */
    public List getBiblioRefs() {
        return biblioRefs;
    }

    /**
     * @param biblioRefs the biblioRefs to set
     */
    public void setBiblioRefs(List biblioRefs) {
        this.biblioRefs = biblioRefs;
    }

    /**
     * @return the timestamp
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * @param timestamp the timestamp to set
     */
    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * @return the step
     */
    public String getStep() {
        return step;
    }

    /**
     * @param step the step to set
     */
    public void setStep(String step) {
        this.step = step;
    }

    /**
     * @return the bgDAO
     */
    public List getBgDAO() {
        return bgDAO;
    }

    /**
     * @param bgDAO the bgDAO to set
     */
    public void setBgDAO(List bgDAO) {
        this.bgDAO = bgDAO;
    }

    /**
     * @return the encryptedId_sub
     */
    public String getEncryptedId_sub() {
        return encryptedId_sub;
    }

    /**
     * @param encryptedId_sub the encryptedId_sub to set
     */
    public void setEncryptedId_sub(String encryptedId_sub) {
        this.encryptedId_sub = encryptedId_sub;
    }

    /**
     * @return the encryptedStep
     */
    public String getEncryptedStep() {
        return encryptedStep;
    }

    /**
     * @param encryptedStep the encryptedStep to set
     */
    public void setEncryptedStep(String encryptedStep) {
        this.encryptedStep = encryptedStep;
    }

    /**
     * @return the notes
     */
    public String getNotes() {
        return notes;
    }

    /**
     * @param notes the notes to set
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * @return the pubmed_id
     */
    public String getPubmed_id() {
        return pubmed_id;
    }

    /**
     * @param pubmed_id the pubmed_id to set
     */
    public void setPubmed_id(String pubmed_id) {
        this.pubmed_id = pubmed_id;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the author1
     */
    public String getAuthor1() {
        return author1;
    }

    /**
     * @param author1 the author1 to set
     */
    public void setAuthor1(String author1) {
        this.author1 = author1;
    }

    /**
     * @return the journal
     */
    public String getJournal() {
        return journal;
    }

    /**
     * @param journal the journal to set
     */
    public void setJournal(String journal) {
        this.journal = journal;
    }

    /**
     * @return the year
     */
    public String getYear() {
        return year;
    }

    /**
     * @param year the year to set
     */
    public void setYear(String year) {
        this.year = year;
    }

    /**
     * @return the volume
     */
    public String getVolume() {
        return volume;
    }

    /**
     * @param volume the volume to set
     */
    public void setVolume(String volume) {
        this.volume = volume;
    }

    /**
     * @return the pages
     */
    public String getPages() {
        return pages;
    }

    /**
     * @param pages the pages to set
     */
    public void setPages(String pages) {
        this.pages = pages;
    }
}
