/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.model;

import java.io.Serializable;

/**
 *
 * @author phil
 */
public class CategoriesStrainsDAO implements Serializable {

    private int cat_id_cat;
    private int str_id_str;
    private CategoriesDAO categoriesDAO;

    /**
     * @return the cat_id_cat
     */
    public int getCat_id_cat() {
        return cat_id_cat;
    }

    /**
     * @param cat_id_cat the cat_id_cat to set
     */
    public void setCat_id_cat(int cat_id_cat) {
        this.cat_id_cat = cat_id_cat;
    }

    /**
     * @return the str_id_str
     */
    public int getStr_id_str() {
        return str_id_str;
    }

    /**
     * @param str_id_str the str_id_str to set
     */
    public void setStr_id_str(int str_id_str) {
        this.str_id_str = str_id_str;
    }

    /**
     * @return the categoriesDAO
     */
    public CategoriesDAO getCategoriesDAO() {
        return categoriesDAO;
    }

    /**
     * @param categoriesDAO the categoriesDAO to set
     */
    public void setCategoriesDAO(CategoriesDAO categoriesDAO) {
        this.categoriesDAO = categoriesDAO;
    }
}
