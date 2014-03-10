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
import uk.ac.ebi.emma.entity.Allele;
import uk.ac.ebi.emma.entity.Background;
import uk.ac.ebi.emma.entity.Mutation;
import uk.ac.ebi.emma.entity.Strain;
import uk.ac.ebi.emma.manager.AllelesManager;
import uk.ac.ebi.emma.manager.BackgroundsManager;
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
    private MutationValidator validator;

////////////    /**
////////////     * Return the full list of genes
////////////     * @param model the Genes list data model
////////////     * 
////////////     * @return the full list of genes
////////////     */
////////////    @RequestMapping(method=RequestMethod.GET)
////////////    @ModelAttribute
////////////    public String initialize(Model model)
////////////    {
////////////        List<Gene> genesList = genesManager.getGenes();
////////////        model.addAttribute("genesList", genesList);
////////////        
////////////        return "mutationManagementDetail";
////////////    }
    
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
            
          , Model model)
    {
        // Save the filter info and add to model.
        Filter filter = buildFilter(filterMutationKey, filterMutationType, filterMutationSubtype, filterStrainKey,
                                    filterAlleleKey, filterBackgroundKey);
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
     * @param allele_key the allele primary key
     * @param mutation the mutation instance
     * @param errors the Errors binding result object
     * @param filterMutationKey the mutation id search criterion (may be empty)
     * @param filterMutationType the mutation type search criterion (may be empty)
     * @param filterMutationSubtype the mutation subtype search criterion (may be empty)
     * @param filterStrainKey the strain id search criterion (may be empty)
     * @param filterAlleleKey the allele id search criterion (may be empty)
     * @param filterBackgroundKey the background id search criterion (may be empty)
     * @param model the filter data, saved above in edit().
     * @return redirected view to same gene detail data.
     */
    @RequestMapping(value="/save", method=RequestMethod.POST)
    public String save(
            @Valid Mutation mutation, Errors errors
          
    //      , @RequestParam(value="allele_key") Integer allele_key     // WE MAY NOT NEED THIS !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
            
          , @RequestParam(value="filterMutationKey") String filterMutationKey
          , @RequestParam(value="filterMutationType") String filterMutationType
          , @RequestParam(value="filterMutationSubtype") String filterMutationSubtype
          , @RequestParam(value="filterStrainKey") String filterStrainKey
          , @RequestParam(value="filterAlleleKey") String filterAlleleKey
          , @RequestParam(value="filterBackgroundKey") String filterBackgroundKey
            
          , Model model) 
    {
   //     allele.setAllele_key(allele_key);
        
        // Load up the model in case we have to redisplay the detail form.
        // Save the filter info and add to model.
        Filter filter = buildFilter(filterMutationKey, filterMutationType, filterMutationSubtype, filterStrainKey,
                                    filterAlleleKey, filterBackgroundKey);
        model.addAttribute(filter);
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("loggedInUser", loggedInUser);
        
        // Validate.
        validator.validate(mutation, errors);
        if (errors.hasErrors()) {
            return "mutationManagementDetail";
        }
        
        try {
            mutationsManager.save(mutation);
        } catch (PersistFailedException pfe) {
            errors.reject(null, pfe.getLocalizedMessage());
            return "mutationManagementDetail";
        }
        
        return "redirect:/curation/mutationManagementDetail/edit"
                + "?filterMutationKey=" + filterMutationKey
                + "&filterMutationType=" + filterMutationType
                + "&filterMutationSubtype=" + filterMutationSubtype
                + "&filterStrainKey=" + filterStrainKey
                + "&filterAlleleKey=" + filterAlleleKey
                + "&filterBackgroundKey=" + filterBackgroundKey;
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
            
          , Model model) 
    {
        return "redirect:/curation/mutationManagementList/showFilter"
                + "?filterMutationKey=" + filterMutationKey
                + "&filterMutationType=" + filterMutationType
                + "&filterMutationSubtype=" + filterMutationSubtype
                + "&filterStrainKey=" + filterStrainKey
                + "&filterAlleleKey=" + filterAlleleKey
                + "&filterBackgroundKey=" + filterBackgroundKey;
    }
    
    
    // PRIVATE METHODS
    
    
    private Filter buildFilter(String filterMutationKey, String filterMutationType, String filterMutationSubtype, String filterStrainKey,
                                    String filterAlleleKey, String filterBackgroundKey) {
        Filter filter = new Filter();
        filter.setMutation_key(filterMutationKey != null ? filterMutationKey : "");
        filter.setMutationType(filterMutationType != null ? filterMutationType : "");
        filter.setMutationSubtype(filterMutationSubtype != null ? filterMutationSubtype : "");
        filter.setStrain_key(filterStrainKey != null ? filterStrainKey : "");
        filter.setAllele_key(filterAlleleKey != null ? filterAlleleKey : "");
        filter.setBackground_key(filterBackgroundKey != null ? filterBackgroundKey : "");
        
        return filter;
    }
    
    
    // GETTERS AND SETTERS
    


}
