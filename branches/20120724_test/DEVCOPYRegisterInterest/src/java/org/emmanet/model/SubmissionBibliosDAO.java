/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.emmanet.model;

/**
 *
 * @author phil
 */
public class SubmissionBibliosDAO {

    private int id_biblio;
    private int sub_id_sub;
    private String title;
    private String author1;
    private String author2;
    private String year;
    private String journal;
    private String username;
    private String volume;
    private String pages;
    private String pubmed_id;
    private int biblio_number;
    private String last_change;
    private String notes;
    private String mgi_original;

    /**
     * @return the id_biblio
     */
    public int getId_biblio() {
        return id_biblio;
    }

    /**
     * @param id_biblio the id_biblio to set
     */
    public void setId_biblio(int id_biblio) {
        this.id_biblio = id_biblio;
    }

    /**
     * @return the sub_id_sub
     */
    public int getSub_id_sub() {
        return sub_id_sub;
    }

    /**
     * @param sub_id_sub the sub_id_sub to set
     */
    public void setSub_id_sub(int sub_id_sub) {
        this.sub_id_sub = sub_id_sub;
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
     * @return the author2
     */
    public String getAuthor2() {
        return author2;
    }

    /**
     * @param author2 the author2 to set
     */
    public void setAuthor2(String author2) {
        this.author2 = author2;
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
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @param username the username to set
     */
    public void setUsername(String username) {
        this.username = username;
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
     * @return the biblio_number
     */
    public int getBiblio_number() {
        return biblio_number;
    }

    /**
     * @param biblio_number the biblio_number to set
     */
    public void setBiblio_number(int biblio_number) {
        this.biblio_number = biblio_number;
    }

    /**
     * @return the last_change
     */
    public String getLast_change() {
        return last_change;
    }

    /**
     * @param last_change the last_change to set
     */
    public void setLast_change(String last_change) {
        this.last_change = last_change;
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
     * @return the mgi_original
     */
    public String getMgi_original() {
        return mgi_original;
    }

    /**
     * @param mgi_original the mgi_original to set
     */
    public void setMgi_original(String mgi_original) {
        this.mgi_original = mgi_original;
    }
}
