/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.model;

/**
 *
 * @author phil
 */
public class SubmissionMutationsDAO {

    private int id;
    private int id_sub;
    private String mutation_plasmid;
    private String mutation_founder_line_number;
    private String mutation_promoter;
    private String mutation_mutagen;
    private String mutation_es_cell_line;
    private String mutation_chrom_anomaly_descr;
    private String mutation_chrom_anomaly_name;
    private String mutation_original_backg;
    private String mutation_original_backg_text;
    private String mutation_dominance_pattern;
    private String mutation_chrom;
    private String mutation_allele_mgi_symbol;
    private String mutation_gene_mgi_symbol;
    private String mutation_transgene_mgi_symbol;
    private String mutation_subtype;
    private String mutation_type;
    private int mutationCount;

    /**
     * @return the id_sub
     */
    public int getId_sub() {
        return id_sub;
    }

    /**
     * @param id_sub the id_sub to set
     */
    public void setId_sub(int id_sub) {
        this.id_sub = id_sub;
    }

    /**
     * @return the mutation_plasmid
     */
    public String getMutation_plasmid() {
        return mutation_plasmid;
    }

    /**
     * @param mutation_plasmid the mutation_plasmid to set
     */
    public void setMutation_plasmid(String mutation_plasmid) {
        this.mutation_plasmid = mutation_plasmid;
    }

    /**
     * @return the mutation_founder_line_number
     */
    public String getMutation_founder_line_number() {
        return mutation_founder_line_number;
    }

    /**
     * @param mutation_founder_line_number the mutation_founder_line_number to set
     */
    public void setMutation_founder_line_number(String mutation_founder_line_number) {
        this.mutation_founder_line_number = mutation_founder_line_number;
    }

    /**
     * @return the mutation_promoter
     */
    public String getMutation_promoter() {
        return mutation_promoter;
    }

    /**
     * @param mutation_promoter the mutation_promoter to set
     */
    public void setMutation_promoter(String mutation_promoter) {
        this.mutation_promoter = mutation_promoter;
    }

    /**
     * @return the mutation_mutagen
     */
    public String getMutation_mutagen() {
        return mutation_mutagen;
    }

    /**
     * @param mutation_mutagen the mutation_mutagen to set
     */
    public void setMutation_mutagen(String mutation_mutagen) {
        this.mutation_mutagen = mutation_mutagen;
    }

    /**
     * @return the mutation_es_cell_line
     */
    public String getMutation_es_cell_line() {
        return mutation_es_cell_line;
    }

    /**
     * @param mutation_es_cell_line the mutation_es_cell_line to set
     */
    public void setMutation_es_cell_line(String mutation_es_cell_line) {
        this.mutation_es_cell_line = mutation_es_cell_line;
    }

    /**
     * @return the mutation_chrom_anomaly_descr
     */
    public String getMutation_chrom_anomaly_descr() {
        return mutation_chrom_anomaly_descr;
    }

    /**
     * @param mutation_chrom_anomaly_descr the mutation_chrom_anomaly_descr to set
     */
    public void setMutation_chrom_anomaly_descr(String mutation_chrom_anomaly_descr) {
        this.mutation_chrom_anomaly_descr = mutation_chrom_anomaly_descr;
    }

    /**
     * @return the mutation_chrom_anomaly_name
     */
    public String getMutation_chrom_anomaly_name() {
        return mutation_chrom_anomaly_name;
    }

    /**
     * @param mutation_chrom_anomaly_name the mutation_chrom_anomaly_name to set
     */
    public void setMutation_chrom_anomaly_name(String mutation_chrom_anomaly_name) {
        this.mutation_chrom_anomaly_name = mutation_chrom_anomaly_name;
    }

    /**
     * @return the mutation_original_backg
     */
    public String getMutation_original_backg() {
        return mutation_original_backg;
    }

    /**
     * @param mutation_original_backg the mutation_original_backg to set
     */
    public void setMutation_original_backg(String mutation_original_backg) {
        this.mutation_original_backg = mutation_original_backg;
    }

    /**
     * @return the mutation_dominance_pattern
     */
    public String getMutation_dominance_pattern() {
        return mutation_dominance_pattern;
    }

    /**
     * @param mutation_dominance_pattern the mutation_dominance_pattern to set
     */
    public void setMutation_dominance_pattern(String mutation_dominance_pattern) {
        this.mutation_dominance_pattern = mutation_dominance_pattern;
    }

    /**
     * @return the mutation_chrom
     */
    public String getMutation_chrom() {
        return mutation_chrom;
    }

    /**
     * @param mutation_chrom the mutation_chrom to set
     */
    public void setMutation_chrom(String mutation_chrom) {
        this.mutation_chrom = mutation_chrom;
    }

    /**
     * @return the mutation_allele_mgi_symbol
     */
    public String getMutation_allele_mgi_symbol() {
        return mutation_allele_mgi_symbol;
    }

    /**
     * @param mutation_allele_mgi_symbol the mutation_allele_mgi_symbol to set
     */
    public void setMutation_allele_mgi_symbol(String mutation_allele_mgi_symbol) {
        this.mutation_allele_mgi_symbol = mutation_allele_mgi_symbol;
    }

    /**
     * @return the mutation_gene_mgi_symbol
     */
    public String getMutation_gene_mgi_symbol() {
        return mutation_gene_mgi_symbol;
    }

    /**
     * @param mutation_gene_mgi_symbol the mutation_gene_mgi_symbol to set
     */
    public void setMutation_gene_mgi_symbol(String mutation_gene_mgi_symbol) {
        this.mutation_gene_mgi_symbol = mutation_gene_mgi_symbol;
    }

    /**
     * @return the mutation_transgene_mgi_symbol
     */
    public String getMutation_transgene_mgi_symbol() {
        return mutation_transgene_mgi_symbol;
    }

    /**
     * @param mutation_transgene_mgi_symbol the mutation_transgene_mgi_symbol to set
     */
    public void setMutation_transgene_mgi_symbol(String mutation_transgene_mgi_symbol) {
        this.mutation_transgene_mgi_symbol = mutation_transgene_mgi_symbol;
    }

    /**
     * @return the mutation_subtype
     */
    public String getMutation_subtype() {
        return mutation_subtype;
    }

    /**
     * @param mutation_subtype the mutation_subtype to set
     */
    public void setMutation_subtype(String mutation_subtype) {
        this.mutation_subtype = mutation_subtype;
    }

    /**
     * @return the mutation_type
     */
    public String getMutation_type() {
        return mutation_type;
    }

    /**
     * @param mutation_type the mutation_type to set
     */
    public void setMutation_type(String mutation_type) {
        this.mutation_type = mutation_type;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the mutation_original_backg_text
     */
    public String getMutation_original_backg_text() {
        return mutation_original_backg_text;
    }

    /**
     * @param mutation_original_backg_text the mutation_original_backg_text to set
     */
    public void setMutation_original_backg_text(String mutation_original_backg_text) {
        this.mutation_original_backg_text = mutation_original_backg_text;
    }

    /**
     * @return the mutationCount
     */
    public int getMutationCount() {
        return mutationCount;
    }

    /**
     * @param mutationCount the mutationCount to set
     */
    public void setMutationCount(int mutationCount) {
        this.mutationCount = mutationCount;
    }
}
