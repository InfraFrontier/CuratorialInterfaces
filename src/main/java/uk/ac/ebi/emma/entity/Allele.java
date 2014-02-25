/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package uk.ac.ebi.emma.entity;

import java.util.Date;
import java.util.Set;

/**
 *
 * @author mrelac
 */
public class Allele {
    
    private Integer id_allele;
    private String name;
    private String symbol;
    private String mgi_ref;
    private String username;
    private Date last_change;
    private Gene gene;
    private Set<Mutation> mutations;
    private int gen_id_gene;

    public Integer getId_allele() {
        return id_allele;
    }

    public void setId_allele(Integer id_allele) {
        this.id_allele = id_allele;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getMgi_ref() {
        return mgi_ref;
    }

    public void setMgi_ref(String mgi_ref) {
        this.mgi_ref = mgi_ref;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Date getLast_change() {
        return last_change;
    }

    public void setLast_change(Date last_change) {
        this.last_change = last_change;
    }

    public Gene getGene() {
        return gene;
    }

    public void setGene(Gene gene) {
        this.gene = gene;
    }

    public Set<Mutation> getMutations() {
        return mutations;
    }

    public void setMutations(Set<Mutation> mutations) {
        this.mutations = mutations;
    }

    public int getGen_id_gene() {
        return gen_id_gene;
    }

    public void setGen_id_gene(int gen_id_gene) {
        this.gen_id_gene = gen_id_gene;
    }

}
