/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.emmanet.model;

/**
 *
 * @author phil
 */
public class AllelesDAO {
    
    private int id_allel;
    private String name;
    private String alls_form;
    private String mgi_ref;
    private String username;
    private String last_change;
    private String gen_id_gene;
    
    private GenesDAO genesDAO;
    private MutationsDAO mutationsDAO;

    public int getId_allel() {
        return id_allel;
    }

    public void setId_allel(int id_allel) {
        this.id_allel = id_allel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlls_form() {
        return alls_form;
    }

    public void setAlls_form(String alls_form) {
        this.alls_form = alls_form;
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

    public String getLast_change() {
        return last_change;
    }

    public void setLast_change(String last_change) {
        this.last_change = last_change;
    }

    public String getGen_id_gene() {
        return gen_id_gene;
    }

    public void setGen_id_gene(String gen_id_gene) {
        this.gen_id_gene = gen_id_gene;
    }

    public GenesDAO getGenesDAO() {
        return genesDAO;
    }

    public void setGenesDAO(GenesDAO genesDAO) {
        this.genesDAO = genesDAO;
    }

    public MutationsDAO getMutationsDAO() {
        return mutationsDAO;
    }

    public void setMutationsDAO(MutationsDAO mutationsDAO) {
        this.mutationsDAO = mutationsDAO;
    }

    

    
}
