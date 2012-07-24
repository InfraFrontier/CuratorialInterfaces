/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.model;

/**
 *
 * @author phil
 */
public class BibliosStrainsDAO {

    private int bib_id_biblio;
    private int str_id_str;    // RELATIONSHIP MAPPING
    private BibliosDAO bibliosDAO;
    private StrainsDAO strainsDAO;

    public int getBib_id_biblio() {
        return bib_id_biblio;
    }

    public void setBib_id_biblio(int bib_id_biblio) {
        this.bib_id_biblio = bib_id_biblio;
    }

    public int getStr_id_str() {
        return str_id_str;
    }

    public void setStr_id_str(int str_id_str) {
        this.str_id_str = str_id_str;
    }

    public BibliosDAO getBibliosDAO() {
        return bibliosDAO;
    }

    public void setBibliosDAO(BibliosDAO bibliosDAO) {
        this.bibliosDAO = bibliosDAO;
    }

    public StrainsDAO getStrainsDAO() {
        return strainsDAO;
    }

    public void setStrainsDAO(StrainsDAO strainsDAO) {
        this.strainsDAO = strainsDAO;
    }
}
