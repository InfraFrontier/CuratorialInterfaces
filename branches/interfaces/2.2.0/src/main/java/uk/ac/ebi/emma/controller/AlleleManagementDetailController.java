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

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
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
     * 'Edit/New Allele' icon implementation
     * 
     * @param allele_key the allele ID being edited (a value of 0 indicates a new
     *                allele is to be added).
     * @param filterAlleleKey the Allele Id part of the filter
     * @param filterAlleleName the Allele name part of the filter
     * @param filterAlleleSymbol the Allele symbol part of the filter
     * @param filterAlleleMgiReference the MGI reference part of the filter
     * @param filterGeneKey the Gene Id part of the filter
     * @param filterGeneName the Gene name part of the filter
     * @param filterGeneSymbol the Gene symbol part of the filter
     * @param model the model
     * @return the view to show
     */
    @RequestMapping(value="/edit", method=RequestMethod.GET)
    public String edit(
            @RequestParam(value="allele_key") Integer allele_key
            
          , @RequestParam(value="filterAlleleKey") String filterAlleleKey
          , @RequestParam(value="filterAlleleName") String filterAlleleName
          , @RequestParam(value="filterAlleleSymbol") String filterAlleleSymbol
          , @RequestParam(value="filterAlleleMgiReference") String filterAlleleMgiReference
          , @RequestParam(value="filterGeneKey") String filterGeneKey
          , @RequestParam(value="filterGeneName") String filterGeneName
          , @RequestParam(value="filterGeneSymbol") String filterGeneSymbol
            
          , Model model)
    {
        // Save the filter info and add to model.
        Filter filter = buildFilter(filterAlleleKey, filterAlleleName, filterAlleleSymbol, filterAlleleMgiReference,
                                    filterGeneKey, filterGeneName, filterGeneSymbol);
        model.addAttribute(filter);
        
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("loggedInUser", loggedInUser);
        
        Allele allele = allelesManager.getAllele(allele_key);
        if (allele == null) {
            allele = new Allele();
        }
        
        model.addAttribute("allele", allele);
    
        return "alleleManagementDetail";
    }
    
    /**
     * Save the form data.
     * 
     * @param allele the allele instance
     * @param errors the Errors binding result object
     * @param allele_key the actual allele primary key
     * @param filterAlleleKey
     * @param filterAlleleName
     * @param filterAlleleSymbol
     * @param filterAlleleMgiReference
     * @param filterGeneKey
     * @param filterGeneName
     * @param filterGeneSymbol
     * @param model the filter data, saved above in edit().
     * @return redirected view to same gene detail data.
     */
    @RequestMapping(value="/save", method=RequestMethod.POST)
    public String save(
            @Valid Allele allele, Errors errors
          
          , @RequestParam(value="allele_key") Integer allele_key
            
          , @RequestParam(value="filterAlleleKey") String filterAlleleKey
          , @RequestParam(value="filterAlleleName") String filterAlleleName
          , @RequestParam(value="filterAlleleSymbol") String filterAlleleSymbol
          , @RequestParam(value="filterAlleleMgiReference") String filterAlleleMgiReference
          , @RequestParam(value="filterGeneKey") String filterGeneKey
          , @RequestParam(value="filterGeneName") String filterGeneName
          , @RequestParam(value="filterGeneSymbol") String filterGeneSymbol
            
          , Model model) 
    {
        allele.setAllele_key(allele_key);
        
        // Load up the model in case we have to redisplay the detail form.            // Save the filter info and add to model.
        Filter filter = buildFilter(filterAlleleKey, filterAlleleName, filterAlleleSymbol, filterAlleleMgiReference,
                                    filterGeneKey, filterGeneName, filterGeneSymbol);
        model.addAttribute(filter);
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
                + "?allele_key=" + allele.getAllele_key()
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
