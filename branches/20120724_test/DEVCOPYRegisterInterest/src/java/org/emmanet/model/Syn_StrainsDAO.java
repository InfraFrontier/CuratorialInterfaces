/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.model;

/**
 *
 * @author phil
 */
public class Syn_StrainsDAO {

    private int id_syn;
    private String name;
    private String last_change;
    private String username;
    private int str_id_str;

    public int getId_syn() {
        return id_syn;
    }

    public void setId_syn(int id_syn) {
        this.id_syn = id_syn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLast_change() {
        return last_change;
    }

    public void setLast_change(String last_change) {
        this.last_change = last_change;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getStr_id_str() {
        return str_id_str;
    }

    public void setStr_id_str(int str_id_str) {
        this.str_id_str = str_id_str;
    }
}
