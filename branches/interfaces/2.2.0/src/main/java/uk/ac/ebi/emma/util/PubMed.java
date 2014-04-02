/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 /**
 * Copyright © 2013 EMBL - European Bioinformatics Institute
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License.  
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package uk.ac.ebi.emma.util;

import ebi.ws.client.Authors;
import ebi.ws.client.Journal;
import ebi.ws.client.JournalInfo;
import ebi.ws.client.ResponseWrapper;
import ebi.ws.client.Result;
import ebi.ws.client.WSCitationImpl;
import ebi.ws.client.WSCitationImplService;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

/**
 *
 * @author mrelac
 */
public class PubMed {
    protected Logger logger = Logger.getLogger(PubMed.class);
    
    private String pubmedId;
    private String title;
    private String author1;
    private String otherAuthors;
    private String journal;
    private String volume;
    private String issue;
    private String pages;
    private String paperid;
    private int year;
    private List<Authors> authors;
    private String authorsString;
    private boolean valid;
    
    public PubMed() {
        this("");
    }
    
    public PubMed(long pubmedId) {
        this(Long.toString(pubmedId));
    }
    
    public PubMed(String pubmedId) {
        this.pubmedId = pubmedId;
        title = "";
        author1 = "";
        otherAuthors = "";
        journal = "";
        volume = "";
        issue = "";
        pages = "";
        paperid = "";
        year = 0;
        authors = new ArrayList();
        authorsString = "";
        valid = false;
        loadFromWebService(pubmedId);
    }
    
    public boolean isValid() {
        return valid;
    }
    
    
    // PRIVATE METHODS
    
    
    private void loadFromWebService(String pubmedId) {
        valid = false;
        if (pubmedId == null)
            return;
        
        this.pubmedId = pubmedId;
        WSCitationImplService service = new WSCitationImplService();
        WSCitationImpl port = service.getWSCitationImplPort();
        String dataSet = "metadata";
        String resultType = "core";
        int offset = 0;
        boolean synonym = false;
        String email = "";
        
        ResponseWrapper resultsBean = null;
        try {
            resultsBean = port.searchPublications(pubmedId, dataSet, resultType, offset, synonym, email);
        }
        catch (Exception e) {
            logger.error("getResult exception: " + e.getLocalizedMessage());
            return;
        }
        
        if ((resultsBean == null)  || (resultsBean.getResultList() == null) || (resultsBean.getResultList().getResult() == null))
            return;

        for (Result resultBean : resultsBean.getResultList().getResult()) {
            String pmid = resultBean.getPmid();
            if ((pmid != null) && (pmid.equals(pubmedId))) {
                if ((resultBean.getAuthorList() != null) && (resultBean.getAuthorList().getAuthor() != null) && (resultBean.getAuthorList().getAuthor().size() > 0)) {
                    this.authors = resultBean.getAuthorList().getAuthor();
                    this.authorsString = resultBean.getAuthorString();
                    if (authorsString.charAt(authorsString.length() - 1) == '.')
                        authorsString = authorsString.substring(0, authorsString.length() - 1); // Remove the trailing '.' if present.
                    for (int i = 0; i < authors.size(); i++) {                  // Author fields.
                        Authors author = authors.get(i);
                        if (i == 0)
                            this.author1 = author.getFullName();
                        else if (i == 1) {
                            this.otherAuthors = author.getFullName();
                        } else {
                            this.otherAuthors += ", " + author.getFullName();
                        }
                    }
                }
                
                if ((resultBean.getJournalInfo() != null) && (resultBean.getJournalInfo().getJournal() != null)) {
                    JournalInfo ji = resultBean.getJournalInfo();
                    Journal j = ji.getJournal();
                    if (resultBean.getTitle() != null)
                        this.title = resultBean.getTitle();
                    if (j.getTitle() != null)
                        this.journal = j.getTitle();
                    this.year = ji.getYearOfPublication();
                    if (ji.getVolume() != null)
                        this.volume = ji.getVolume();
                    if (ji.getIssue() != null)
                        this.issue = ji.getIssue();
                }
                
                if (resultBean.getPageInfo() != null)
                    this.pages = resultBean.getPageInfo();

                valid = true;
            }
        }
    }
    
    @Override
    public String toString() {
        return (this.pubmedId == null ? "<null>" : this.pubmedId);
    }
    
    
    // GETTERS AND SETTERS

    
    public String getPubmedId() {
        return pubmedId;
    }

    public void setPubmedId(String pubmedId) {
        loadFromWebService(pubmedId);
    }

    public void setPubmedId(long pubmedId) {
        loadFromWebService(Long.toString(pubmedId));
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor1() {
        return author1;
    }

    public String getOtherAuthors() {
        return otherAuthors;
    }

    public String getJournal() {
        return journal;
    }

    public String getVolume() {
        return volume;
    }

    public String getIssue() {
        return issue;
    }

    public String getPages() {
        return pages;
    }

    public String getPaperid() {
        return paperid;
    }

    public int getYear() {
        return year;
    }

    public List<Authors> getAuthors() {
        return authors;
    }

    public String getAuthorsString() {
        return authorsString;
    }
    
}
