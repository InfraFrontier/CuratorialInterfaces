/*
 * MutationsStrainsDAO.java
 *
 * Created on 07 January 2008, 14:49
 *
 */

package org.emmanet.model;

/**
 *
 * @author phil
 */
public class MutationsStrainsDAO {
    private int str_id_str;
    private int mut_id;
    private MutationsDAO mutationsDAO;
    private StrainsDAO strainsDAO;

    public int getStr_id_str() {
        return str_id_str;
    }

    public void setStr_id_str(int str_id_str) {
        this.str_id_str = str_id_str;
    }

    public int getMut_id() {
        return mut_id;
    }

    public void setMut_id(int mut_id) {
        this.mut_id = mut_id;
    }
    
    public MutationsDAO getMutationsDAO() {
        return mutationsDAO;
    }

    public void setMutationsDAO(MutationsDAO mutationsDAO) {
        this.mutationsDAO = mutationsDAO;
    }

    public StrainsDAO getStrainsDAO() {
        return strainsDAO;
    }

    public void setStrainsDAO(StrainsDAO strainsDAO) {
        this.strainsDAO = strainsDAO;
    } 
    
}
