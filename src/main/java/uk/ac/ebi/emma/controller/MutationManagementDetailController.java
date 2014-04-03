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
import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.emma.Exception.PersistFailedException;
import uk.ac.ebi.emma.entity.EsCellLine;
import uk.ac.ebi.emma.entity.Mutation;
import uk.ac.ebi.emma.entity.Strain;
import uk.ac.ebi.emma.manager.MutationsManager;
import uk.ac.ebi.emma.manager.StrainsManager;
import uk.ac.ebi.emma.util.Filter;
import uk.ac.ebi.emma.validator.MutationValidator;

/**
 *
 * @author mrelac
 */
@Controller
@RequestMapping("/mutationManagementDetail")
public class MutationManagementDetailController {
    protected final Logger logger = Logger.getLogger(this.getClass());
    @Autowired
    private final MutationsManager mutationsManager = new MutationsManager();
    @Autowired
    private final StrainsManager strainsManager = new StrainsManager();
    
    @Autowired
    private MutationValidator validator;

    /**
     * 'Edit/New Mutation' icon implementation
     * 
     * @param mutation_key the mutation primary key being edited (a value of 0 indicates a new
     *                mutation is to be added).
     * @param filterMutationKey the mutation id search criterion (may be empty)
     * @param filterMutationType the mutation type search criterion (may be empty)
     * @param filterMutationSubtype the mutation subtype search criterion (may be empty)
     * @param filterStrainKey the strain id search criterion (may be empty)
     * @param filterAlleleKey the allele id search criterion (may be empty)
     * @param filterBackgroundKey the background id search criterion (may be empty)
     * @param filterGeneKey the gene primary key search criterion (may be empty)
     * @param filterGeneSymbol the gene symbol search criterion (may be empty)
     * @param model the model
     * @return the view to show
     */
    @RequestMapping(value="/edit", method=RequestMethod.GET)
    public String edit(
            @RequestParam(value="mutation_key") Integer mutation_key
            
          , @RequestParam(value="filterMutationKey") String filterMutationKey
          , @RequestParam(value="filterMutationType") String filterMutationType
          , @RequestParam(value="filterMutationSubtype") String filterMutationSubtype
          , @RequestParam(value="filterStrainKey") String filterStrainKey
          , @RequestParam(value="filterAlleleKey") String filterAlleleKey
          , @RequestParam(value="filterBackgroundKey") String filterBackgroundKey
          , @RequestParam(value="filterGeneKey") String filterGeneKey
          , @RequestParam(value="filterGeneSymbol") String filterGeneSymbol
            
          , Model model)
    {
        // Save the filter info and add to model.
        Filter filter = buildFilter(filterMutationKey, filterMutationType, filterMutationSubtype, filterStrainKey,
                                    filterAlleleKey, filterBackgroundKey, filterGeneKey, filterGeneSymbol);
        model.addAttribute(filter);
        
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("loggedInUser", loggedInUser);
        
        Mutation mutation = mutationsManager.getMutation(mutation_key);
        if (mutation == null) {
            mutation = new Mutation();
            mutation.setMutation_key(null);
        }
        
        model.addAttribute("mutation", mutation);
    
        return "mutationManagementDetail";
    }
    
    /**
     * Save the form data.
     * 
     * @param mutation the mutation instance
     * @param errors the Errors binding result object
     * 
     * @param mutation_key the allele primary key
     * @param allele_key the allele primary key
     * @param background_key the allele primary key
     * @param strain_keys the collection of strain keys (may be empty or null)
     * 
     * @param filterMutationKey the mutation id search criterion (may be empty)
     * @param filterMutationType the mutation type search criterion (may be empty)
     * @param filterMutationSubtype the mutation subtype search criterion (may be empty)
     * @param filterStrainKey the strain id search criterion (may be empty)
     * @param filterAlleleKey the allele id search criterion (may be empty)
     * @param filterBackgroundKey the background id search criterion (may be empty)
     * @param filterGeneKey the gene primary key search criterion (may be empty)
     * @param filterGeneSymbol the gene symbol search criterion (may be empty)
     * @param model the filter data, saved above in edit().
     * @return redirected view to same gene detail data.
     */
    @RequestMapping(value="/save", method=RequestMethod.POST)
    public String save(
            @Valid Mutation mutation, Errors errors
          
          , @RequestParam(value="mutation_key") Integer mutation_key
          , @RequestParam(value="allele_key") Integer allele_key
          , @RequestParam(value="background_key") Integer background_key
          , @RequestParam(value="strain_keys", required = false) String[] strain_keys
            
          , @RequestParam(value="filterMutationKey") String filterMutationKey
          , @RequestParam(value="filterMutationType") String filterMutationType
          , @RequestParam(value="filterMutationSubtype") String filterMutationSubtype
          , @RequestParam(value="filterStrainKey") String filterStrainKey
          , @RequestParam(value="filterAlleleKey") String filterAlleleKey
          , @RequestParam(value="filterBackgroundKey") String filterBackgroundKey
          , @RequestParam(value="filterGeneKey") String filterGeneKey
          , @RequestParam(value="filterGeneSymbol") String filterGeneSymbol
            
          , Model model) 
    {
        // Load the primary key, the foreign keys, and the strain key collection.
        mutation.setMutation_key(mutation_key);
        mutation.setAllele_key(allele_key);
        mutation.setBackground_key(background_key);
        mutation.setStrains(new LinkedHashSet());
        if (strain_keys != null) {
            for (String sStrain_key : strain_keys) {
                int strain_key = Integer.parseInt(sStrain_key);
                Strain strain = strainsManager.getStrain(strain_key);
                mutation.getStrains().add(strain);
            }
        }
       
        // Load up the model in case we have to redisplay the detail form.
        // Save the filter info and add to model.
        Filter filter = buildFilter(filterMutationKey, filterMutationType, filterMutationSubtype, filterStrainKey,
                                    filterAlleleKey, filterBackgroundKey, filterGeneKey, filterGeneSymbol);
        model.addAttribute(filter);
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("loggedInUser", loggedInUser);
        
        // Validate.
        validator.validate(mutation, errors);
        if (errors.hasErrors()) {
            return "mutationManagementDetail";
        }
        
        try {
            // Copy the class instance keys.
            mutation.getAllele().setAllele_key(allele_key);
            mutation.getBackground().setBackground_key(background_key);
            
            mutationsManager.save(mutation);
        } catch (PersistFailedException pfe) {
            errors.reject(null, pfe.getLocalizedMessage());
            return "mutationManagementDetail";
        }
        
        return "redirect:/curation/mutationManagementDetail/edit"
                + "?mutation_key=" + mutation.getMutation_key()
                + "&filterMutationKey=" + filterMutationKey
                + "&filterMutationType=" + filterMutationType
                + "&filterMutationSubtype=" + filterMutationSubtype
                + "&filterStrainKey=" + filterStrainKey
                + "&filterAlleleKey=" + filterAlleleKey
                + "&filterBackgroundKey=" + filterBackgroundKey
                + "&filterGeneKey=" + filterGeneKey
                + "&filterGeneSymbol=" + filterGeneSymbol;
    }
    
    /**
     * Show the list form with saved filter values.
     * 
     * @param filterMutationKey the mutation id search criterion (may be empty)
     * @param filterMutationType the mutation type search criterion (may be empty)
     * @param filterMutationSubtype the mutation subtype search criterion (may be empty)
     * @param filterStrainKey the strain id search criterion (may be empty)
     * @param filterAlleleKey the allele id search criterion (may be empty)
     * @param filterBackgroundKey the background id search criterion (may be empty)
     * @param filterGeneKey the gene primary key search criterion (may be empty)
     * @param filterGeneSymbol the gene symbol search criterion (may be empty)
     * @param model the filter data, saved above in edit().
     * @return redirected view to same gene detail data.
     */
    @RequestMapping(value="/showList", method=RequestMethod.GET)
    public String showList(
            @RequestParam(value="filterMutationKey") String filterMutationKey
          , @RequestParam(value="filterMutationType") String filterMutationType
          , @RequestParam(value="filterMutationSubtype") String filterMutationSubtype
          , @RequestParam(value="filterStrainKey") String filterStrainKey
          , @RequestParam(value="filterAlleleKey") String filterAlleleKey
          , @RequestParam(value="filterBackgroundKey") String filterBackgroundKey
          , @RequestParam(value="filterGeneKey") String filterGeneKey
          , @RequestParam(value="filterGeneSymbol") String filterGeneSymbol
            
          , Model model) 
    {
        return "redirect:/curation/mutationManagementList/showFilter"
                + "?filterMutationKey=" + filterMutationKey
                + "&filterMutationType=" + filterMutationType
                + "&filterMutationSubtype=" + filterMutationSubtype
                + "&filterStrainKey=" + filterStrainKey
                + "&filterAlleleKey=" + filterAlleleKey
                + "&filterBackgroundKey=" + filterBackgroundKey
                + "&filterGeneKey=" + filterGeneKey
                + "&filterGeneSymbol=" + filterGeneSymbol;
    }
    
    
    // PRIVATE METHODS
    
    
    private Filter buildFilter(String filterMutationKey, String filterMutationType, String filterMutationSubtype, String filterStrainKey,
                                    String filterAlleleKey, String filterBackgroundKey, String filterGeneKey, String filterGeneSymbol) {
        Filter filter = new Filter();
        filter.setMutation_key(filterMutationKey != null ? filterMutationKey : "");
        filter.setMutationType(filterMutationType != null ? filterMutationType : "");
        filter.setMutationSubtype(filterMutationSubtype != null ? filterMutationSubtype : "");
        filter.setStrain_key(filterStrainKey != null ? filterStrainKey : "");
        filter.setAllele_key(filterAlleleKey != null ? filterAlleleKey : "");
        filter.setBackground_key(filterBackgroundKey != null ? filterBackgroundKey : "");
        filter.setGene_key(filterGeneKey != null ? filterGeneKey : "");
        filter.setGeneSymbol(filterGeneSymbol != null ? filterGeneSymbol : "");
        
        return filter;
    }

    
    // GETTERS

    
    /**
     * Returns a distinct filtered list of targetd mutation ES types suitable for
     * autocomplete sourcing.
     * 
     * @param filterTerm the filter term for the es cell type name (used in sql LIKE clause)
     * @return a <code>List&lt;EsCellLine&gt;</code> of distinct targetd mutation
     * ES types filtered by <code>filterTerm</code> suitable for autocomplete sourcing.
     */
    @RequestMapping(value = "/getEsCellTypes"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<EsCellLine> getEsCellTypes(@RequestParam String filterTerm) {
        return mutationsManager.getEsCellTypes(filterTerm.trim());
    }
    
    /**
     * Returns a distinct filtered list of types suitable for autocomplete sourcing.
     * 
     * @return a <code>List&lt;String&gt;</code> of types suitable for autocomplete sourcing.
     */
    @RequestMapping(value = "/getTypes"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getTypes() {
        return mutationsManager.getTypes();
    }
    
    /**
     * Returns a distinct filtered list of subtypes suitable for autocomplete sourcing.
     * 
     * @return a <code>List&lt;String&gt;</code> of subtypes suitable for autocomplete sourcing.
     */
    @RequestMapping(value = "/getSubtypes"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getSubtypes() {
        return mutationsManager.getSubtypes();
    }
    
    /**
     * Returns a distinct filtered list of genotypes suitable for autocomplete sourcing.
     * 
     * @param filterTerm the filter term for the genotype name (used in sql LIKE clause)
     * @return a <code>List&lt;String&gt;</code> of genotypes suitable for autocomplete sourcing.
     */
    @RequestMapping(value = "/getGenotypes"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getGenotypes(@RequestParam String filterTerm) {
        return mutationsManager.getGenotypes(filterTerm.trim());
    }
    
    /**
     * Returns a distinct filtered list of dominance names suitable for autocomplete sourcing.
     * 
     * @param filterTerm the filter term for the dominance names (used in sql LIKE clause)
     * @return a <code>List&lt;String&gt;</code> of dominance names suitable for autocomplete sourcing.
     */
    @RequestMapping(value = "/getDominance"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getDominance(@RequestParam String filterTerm) {
        return mutationsManager.getDominance(filterTerm.trim());
    }
    
    /**
     * Returns a distinct filtered list of sex suitable for autocomplete sourcing.
     * 
     * @return a <code>List&lt;String&gt;</code> of sex suitable for autocomplete sourcing.
     */
    @RequestMapping(value = "/getSex"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getSex() {
        return mutationsManager.getSex();
    }
    
    /**
     * Returns a distinct filtered list of causes suitable for autocomplete sourcing.
     * 
     * @param filterTerm the filter term for the causes (used in sql LIKE clause)
     * @return a <code>List&lt;String&gt;</code> of causes suitable for autocomplete sourcing.
     */
    @RequestMapping(value = "/getCauses"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getCauses(@RequestParam String filterTerm) {
        return mutationsManager.getCauses(filterTerm.trim());
    }
}
