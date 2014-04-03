/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 /**
 * Copyright © 2014 EMBL - European Bioinformatics Institute
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

package uk.ac.ebi.emma.controller;

import java.util.LinkedHashSet;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.emma.Exception.PersistFailedException;
import uk.ac.ebi.emma.entity.Biblio;
import uk.ac.ebi.emma.entity.Strain;
import uk.ac.ebi.emma.manager.BibliosManager;
import uk.ac.ebi.emma.manager.StrainsManager;
import uk.ac.ebi.emma.util.Filter;
import uk.ac.ebi.emma.validator.BiblioValidator;

/**
 *
 * @author mrelac
 */
@Controller
@RequestMapping("/biblioManagementDetail")
public class BiblioManagementDetailController {
    protected final Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private final BibliosManager bibliosManager = new BibliosManager();
    @Autowired
    private final StrainsManager strainsManager = new StrainsManager();
    
    @Autowired
    private BiblioValidator validator;

    /**
     * 'Edit/New Biblio' icon implementation
     * 
     * @param biblio_key the biblio key being edited (a value of 0 indicates a new
     *                biblio is to be added).
     * @param filterBiblio_key the biblio key search criterion (may be empty)
     * @param filterStrain_key the strain key search criterion (may be empty)
     * @param filterPubmedId the pubmed id search criterion (may be empty)
     * @param filterBiblioAuthor1 the biblio author1 search criterion (may be empty)
     * @param filterBiblioJournal the biblio journal search criterion (may be empty)
     * @param filterBiblioTitle the biblio title search criterion (may be empty)
     * @param filterBiblioYear the biblio year search criterion (may be empty)
     * @param model the model
     * @return the view to show
     */
    @RequestMapping(value="/edit", method=RequestMethod.GET)
    public String edit(
            @RequestParam(value="biblio_key") Integer biblio_key
            
          , @RequestParam(value="filterBiblioKey",     required=true) String filterBiblio_key
          , @RequestParam(value="filterStrainKey",     required=true) String filterStrain_key
          , @RequestParam(value="filterPubmedId",      required=true) String filterPubmedId
          , @RequestParam(value="filterBiblioAuthor1", required=true) String filterBiblioAuthor1
          , @RequestParam(value="filterBiblioJournal", required=true) String filterBiblioJournal
          , @RequestParam(value="filterBiblioTitle",   required=true) String filterBiblioTitle
          , @RequestParam(value="filterBiblioYear",    required=true) String filterBiblioYear
            
          , Model model)
    {
        // Save the filter info and add to model.
        Filter filter = buildFilter(filterBiblio_key, filterStrain_key, filterPubmedId, filterBiblioAuthor1,
                                    filterBiblioJournal, filterBiblioTitle, filterBiblioYear);
        model.addAttribute(filter);
        
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("loggedInUser", loggedInUser);
        
        Biblio biblio = bibliosManager.getBiblio(biblio_key);
        if (biblio == null) {
            biblio = new Biblio();
        }
        
        model.addAttribute("biblio", biblio);
    
        return "biblioManagementDetail";
    }
    
    /**
     * Save the form data.
     * 
     * @param biblio the biblio instance
     * @param errors the Errors binding result object
     * 
     * @param biblio_key the biblio primary key
     * @param strain_keys the collection of strain keys (may be empty or null)
     * 
     * @param filterBiblio_key the biblio key search criterion (may be empty)
     * @param filterStrain_key the strain key search criterion (may be empty)
     * @param filterPubmedId the pubmed id search criterion (may be empty)
     * @param filterBiblioAuthor1 the biblio author1 search criterion (may be empty)
     * @param filterBiblioJournal the biblio journal search criterion (may be empty)
     * @param filterBiblioTitle the biblio title search criterion (may be empty)
     * @param filterBiblioYear the biblio year search criterion (may be empty)
     * 
     * @param model the filter data, saved above in edit().
     * @return redirected view to same gene detail data.
     */
    @RequestMapping(value="/save", method=RequestMethod.POST)
    public String save(
            @Valid Biblio biblio, Errors errors
          
          , @RequestParam(value="biblio_key",          required=true) Integer biblio_key
          , @RequestParam(value="strain_keys",         required=false) String[] strain_keys
            
          , @RequestParam(value="filterBiblioKey",     required=true) String filterBiblio_key
          , @RequestParam(value="filterStrainKey",     required=true) String filterStrain_key
          , @RequestParam(value="filterPubmedId",      required=true) String filterPubmedId
          , @RequestParam(value="filterBiblioAuthor1", required=true) String filterBiblioAuthor1
          , @RequestParam(value="filterBiblioJournal", required=true) String filterBiblioJournal
          , @RequestParam(value="filterBiblioTitle",   required=true) String filterBiblioTitle
          , @RequestParam(value="filterBiblioYear",    required=true) String filterBiblioYear
            
          , Model model) 
    {
        // Load the primary key, the foreign keys, and the strain key collection.
        biblio.setBiblio_key(biblio_key);
        biblio.setStrains(new LinkedHashSet());
        if (strain_keys != null) {
            for (String sStrain_key : strain_keys) {
                int strain_key = Integer.parseInt(sStrain_key);
                Strain strain = strainsManager.getStrain(strain_key);
                biblio.getStrains().add(strain);
            }
        }
        
        // Load up the model in case we have to redisplay the detail form.
        // Save the filter info and add to model.
        Filter filter = buildFilter(filterBiblio_key, filterStrain_key, filterPubmedId, filterBiblioAuthor1,
                                    filterBiblioJournal, filterBiblioTitle, filterBiblioYear);
        model.addAttribute(filter);
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("loggedInUser", loggedInUser);
        
        // Validate.
        validator.validate(biblio, errors);
        if (errors.hasErrors()) {
            return "biblioManagementDetail";
        }
    
        try {
            bibliosManager.save(biblio);
        } catch (PersistFailedException pfe) {
            errors.reject(null, pfe.getLocalizedMessage());
            return "biblioManagementDetail";
        }
        
        return "redirect:/curation/biblioManagementDetail/edit"
                + "?biblio_key="          + biblio.getBiblio_key()
                + "&filterBiblioKey="     + filterBiblio_key
                + "&filterStrainKey="     + filterStrain_key
                + "&filterPubmedId="      + filterPubmedId
                + "&filterBiblioAuthor1=" + filterBiblioAuthor1
                + "&filterBiblioJournal=" + filterBiblioJournal
                + "&filterBiblioTitle="   + filterBiblioTitle
                + "&filterBiblioYear="    + filterBiblioYear;
    }
    
    
    // PRIVATE METHODS
    
    
    private Filter buildFilter(String filterBiblio_key, String filterStrain_key, String filterPubmedId, String filterBiblioAuthor1,
                               String filterBiblioJournal, String filterBiblioTitle, String filterBiblioYear) {
        Filter filter = new Filter();
        filter.setBiblio_key(filterBiblio_key != null ? filterBiblio_key : "");
        filter.setStrain_key(filterStrain_key != null ? filterStrain_key : "");
        filter.setPubmedId(filterPubmedId != null ? filterPubmedId : "");
        filter.setBiblioAuthor1(filterBiblioAuthor1 != null ? filterBiblioAuthor1 : "");
        filter.setBiblioJournal(filterBiblioJournal != null ? filterBiblioJournal : "");
        filter.setBiblioTitle(filterBiblioTitle != null ? filterBiblioTitle : "");
        filter.setBiblioYear(filterBiblioYear != null ? filterBiblioYear : "");
        
        return filter;
    }
    
    
    // GETTERS AND SETTERS
    

}
