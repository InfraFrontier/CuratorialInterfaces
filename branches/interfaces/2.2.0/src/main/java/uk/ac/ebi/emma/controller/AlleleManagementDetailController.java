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

import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.emma.Exception.PersistFailedException;
import uk.ac.ebi.emma.entity.Allele;
import uk.ac.ebi.emma.manager.AllelesManager;
import uk.ac.ebi.emma.util.Filter;
import uk.ac.ebi.emma.validator.AlleleValidator;

/**
 *
 * @author mrelac
 */
@Controller
@RequestMapping("/alleleManagementDetail")
public class AlleleManagementDetailController {
    @Autowired
    private final AllelesManager allelesManager = new AllelesManager();
    
    @Autowired
    private AlleleValidator validator;

    /**
     * Because this is a @ModelAttribute it gets called before every other method
     * call. This behaviour is used to build the filter and load it into the model.
     * 
     * @param filterAlleleKey
     * @param filterAlleleName
     * @param filterAlleleSymbol
     * @param filterAlleleMgiReference
     * @param filterGeneKey
     * @param filterGeneName
     * @param filterGeneSymbol
     * @param model 
     */
    @ModelAttribute
    public void populateFilter(
            @RequestParam(value="filterAlleleKey",          required=false) String filterAlleleKey
          , @RequestParam(value="filterAlleleName",         required=false) String filterAlleleName
          , @RequestParam(value="filterAlleleSymbol",       required=false) String filterAlleleSymbol
          , @RequestParam(value="filterAlleleMgiReference", required=false) String filterAlleleMgiReference
          , @RequestParam(value="filterGeneKey",            required=false) String filterGeneKey
          , @RequestParam(value="filterGeneName",           required=false) String filterGeneName
          , @RequestParam(value="filterGeneSymbol",         required=false) String filterGeneSymbol
          , Model model) {
        Filter filter = buildFilter(filterAlleleKey, filterAlleleName, filterAlleleSymbol, filterAlleleMgiReference,
                        filterGeneKey,   filterGeneName,   filterGeneSymbol);
        
        model.addAttribute(filter);
    }
    
    /**
     * 'Edit/New Allele' icon implementation
     * 
     * @param allele_key the allele ID being edited (a value of 0 indicates a new
     * @param filterAlleleKey
     * @param filterAlleleName
     * @param filterAlleleSymbol
     * @param filterAlleleMgiReference
     * @param filterGeneKey
     * @param filterGeneName
     * @param filterGeneSymbol
     *                allele is to be added).
     * @param model the model
     * @return the view to show
     */
    @RequestMapping(value="/edit", method=RequestMethod.GET)
    public String edit(
            
            @RequestParam(value="hid_allele_key",           required=false) Integer allele_key
          , @RequestParam(value="filterAlleleKey",          required=false) String filterAlleleKey
          , @RequestParam(value="filterAlleleName",         required=false) String filterAlleleName
          , @RequestParam(value="filterAlleleSymbol",       required=false) String filterAlleleSymbol
          , @RequestParam(value="filterAlleleMgiReference", required=false) String filterAlleleMgiReference
          , @RequestParam(value="filterGeneKey",            required=false) String filterGeneKey
          , @RequestParam(value="filterGeneName",           required=false) String filterGeneName
          , @RequestParam(value="filterGeneSymbol",         required=false) String filterGeneSymbol
          , Model model)
    {
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("loggedInUser", loggedInUser);
        
        Allele allele = allelesManager.getAllele(allele_key);
        if (allele == null) {
            allele = new Allele();
        }
        
        model.addAttribute("allele", allele);
        
        Map modelMap = model.asMap();
        Filter filter = (Filter)modelMap.get("filter");
        filterAlleleKey = filter.getAllele_key();
        filterAlleleName = filter.getAlleleName();
        filterAlleleSymbol = filter.getAlleleSymbol();
        filterAlleleMgiReference = filter.getAlleleMgiReference();
        filterGeneKey = filter.getGene_key();
        filterGeneName = filter.getGeneName();
        filterGeneSymbol = filter.getGeneSymbol();
        model.addAttribute("filter", filter);
    
        return "alleleManagementDetail";
    }
    
    /**
     * Save the form data.
     * 
     * @param allele the allele instance
     * @param errors the Errors binding result object
     * @param filterAlleleKey
     * @param filterAlleleName
     * @param filterAlleleSymbol
     * @param filterAlleleMgiReference
     * @param filterGeneKey
     * @param filterGeneName
     * @param filterGeneSymbol
     * @param model the filter data, saved above in edit().
     * 
     * @return redirected view to same gene detail data.
     */
    @RequestMapping(value="/save", method=RequestMethod.POST)
    public String save(
            @Valid Allele allele, Errors errors
          , @RequestParam(value="filterAlleleKey",          required=false) String filterAlleleKey
          , @RequestParam(value="filterAlleleName",         required=false) String filterAlleleName
          , @RequestParam(value="filterAlleleSymbol",       required=false) String filterAlleleSymbol
          , @RequestParam(value="filterAlleleMgiReference", required=false) String filterAlleleMgiReference
          , @RequestParam(value="filterGeneKey",            required=false) String filterGeneKey
          , @RequestParam(value="filterGeneName",           required=false) String filterGeneName
          , @RequestParam(value="filterGeneSymbol",         required=false) String filterGeneSymbol
          , Model model) 
    {
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("loggedInUser", loggedInUser);
        
        // Validate.
        validator.validate(allele, errors);
        if (errors.hasErrors()) {
            return "alleleManagementDetail";
        }
        
        try {
            allele.getGene().setGene_key(allele.getGene_key());
            allelesManager.save(allele);
        } catch (PersistFailedException pfe) {
            errors.reject(null, pfe.getLocalizedMessage());
            return "alleleManagementDetail";
        }
        
        return "redirect:/curation/alleleManagementDetail/edit"
                + "?hid_allele_key=" + allele.getAllele_key()
                + "&filterAlleleKey=" + filterAlleleKey
                + "&filterAlleleName=" + filterAlleleName
                + "&filterAlleleSymbol=" + filterAlleleSymbol
                + "&filterAlleleMgiReference=" + filterAlleleMgiReference
                + "&filterGeneKey=" + filterGeneKey
                + "&filterGeneName=" + filterGeneName
                + "&filterGeneSymbol=" + filterGeneSymbol;
    }
    
    
    // PRIVATE METHODS
    
    
    private Filter buildFilter(String allele_key, String alleleName, String alleleSymbol, String alleleMgiReference,
                               String gene_key, String geneName, String geneSymbol) {
        Filter filter = new Filter();
        filter.setAllele_key(allele_key != null ? allele_key : "");
        filter.setAlleleName(alleleName != null ? alleleName : "");
        filter.setAlleleSymbol(alleleSymbol != null ? alleleSymbol : "");
        filter.setAlleleMgiReference(alleleMgiReference != null ? alleleMgiReference : "");
        filter.setGene_key(gene_key != null ? gene_key : "");
        filter.setGeneName(geneName != null ? geneName : "");
        filter.setGeneSymbol(geneSymbol != null ? geneSymbol : "");
        
        return filter;
    }
    
    
    // GETTERS AND SETTERS
    

}
