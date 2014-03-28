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

import java.util.List;
import javax.validation.Valid;
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
import uk.ac.ebi.emma.entity.Background;
import uk.ac.ebi.emma.manager.BackgroundsManager;
import uk.ac.ebi.emma.util.Filter;
import uk.ac.ebi.emma.validator.BackgroundValidator;

/**
 *
 * @author mrelac
 */
@Controller
@RequestMapping("/backgroundManagementDetail")
public class BackgroundManagementDetailController {
    @Autowired
    private final BackgroundsManager backgroundManager = new BackgroundsManager();
    
    @Autowired
    private BackgroundValidator validator;

    /**
     * 'Edit/New Background' icon implementation
     * 
     * @param background_key the background ID being edited (a value of 0 indicates a new
     *                background is to be added).
     * @param filterBackgroundKey the Background Id part of the filter
     * @param filterBackgroundName the Background name part of the filter
     * @param filterBackgroundSymbol the Background symbol part of the filter
     * @param filterBackgroundIsCurated the curated search criterion (may be empty)
     * @param filterBackgroundIsInbred the inbred search criterion (may be empty)
     * @param model the model
     * @return the view to show
     */
    @RequestMapping(value="/edit", method=RequestMethod.GET)
    public String edit(
            @RequestParam(value="background_key") Integer background_key
            
          , @RequestParam(value="filterBackgroundKey")       String filterBackgroundKey
          , @RequestParam(value="filterBackgroundName")      String filterBackgroundName
          , @RequestParam(value="filterBackgroundSymbol")    String filterBackgroundSymbol
          , @RequestParam(value="filterBackgroundIsCurated") String filterBackgroundIsCurated
          , @RequestParam(value="filterBackgroundIsInbred")  String filterBackgroundIsInbred
            
          , Model model)
    {
        // Save the filter info and add to model.
        Filter filter = buildFilter(filterBackgroundKey, filterBackgroundName, filterBackgroundSymbol,
                filterBackgroundIsCurated, filterBackgroundIsInbred);
        model.addAttribute(filter);
        
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("loggedInUser", loggedInUser);
        
        Background background = backgroundManager.getBackground(background_key);
        if (background == null) {
            background = new Background();
        }
        
        model.addAttribute("background", background);
    
        return "backgroundManagementDetail";
    }
    
    /**
     * Save the form data.
     * 
     * @param background the background instance
     * @param errors the Errors binding result object
     * @param background_key the actual background primary key
     * @param filterBackgroundKey
     * @param filterBackgroundName
     * @param filterBackgroundSymbol
     * @param filterBackgroundIsCurated the curated search criterion (may be empty)
     * @param filterBackgroundIsInbred the inbred search criterion (may be empty)
     * @param model the filter data, saved above in edit().
     * @return redirected view to same gene detail data.
     */
    @RequestMapping(value="/save", method=RequestMethod.POST)
    public String save(
            @Valid Background background, Errors errors
          
          , @RequestParam(value="background_key") Integer background_key
            
          , @RequestParam(value="filterBackgroundKey")       String filterBackgroundKey
          , @RequestParam(value="filterBackgroundName")      String filterBackgroundName
          , @RequestParam(value="filterBackgroundSymbol")    String filterBackgroundSymbol
          , @RequestParam(value="filterBackgroundIsCurated") String filterBackgroundIsCurated
          , @RequestParam(value="filterBackgroundIsInbred")  String filterBackgroundIsInbred
            
          , Model model) 
    {
        background.setBackground_key(background_key);
        
        // Save the filter info and add to model.
        Filter filter = buildFilter(filterBackgroundKey, filterBackgroundName, filterBackgroundSymbol,
                filterBackgroundIsCurated, filterBackgroundIsInbred);
        model.addAttribute(filter);
        
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("loggedInUser", loggedInUser);
        
        // Validate.
        validator.validate(background, errors);
        if (errors.hasErrors()) {
            return "backgroundManagementDetail";
        }
        
        try {
            backgroundManager.save(background);
        } catch (PersistFailedException pfe) {
            errors.reject(null, pfe.getLocalizedMessage());
            return "backgroundManagementDetail";
        }
        
        return "redirect:/curation/backgroundManagementDetail/edit"
                + "?background_key=" + background.getBackground_key()
                + "&filterBackgroundKey=" + filterBackgroundKey
                + "&filterBackgroundName=" + filterBackgroundName
                + "&filterBackgroundSymbol=" + filterBackgroundSymbol
                + "&filterBackgroundIsCurated=" + filterBackgroundIsCurated
                + "&filterBackgroundIsInbred=" + filterBackgroundIsInbred;
    }
    
    
    // PRIVATE METHODS
    
    
    private Filter buildFilter(String background_key, String backgroundName, String backgroundSymbol,
            String filterBackgroundIsCurated, String filterBackgroundIsInbred) {
        Filter filter = new Filter();
        filter.setBackground_key(background_key != null ? background_key : "");
        filter.setBackgroundName(backgroundName != null ? backgroundName : "");
        filter.setBackgroundSymbol(backgroundSymbol != null ? backgroundSymbol : "");
        filter.setBackgroundIsCurated(filterBackgroundIsCurated != null ? filterBackgroundIsCurated : "");
        filter.setBackgroundIsInbred(filterBackgroundIsInbred != null ? filterBackgroundIsInbred : "");
        
        return filter;
    }
    
    
    // GETTERS AND SETTERS
    
    
    /**
     * Returns a [distinct], unfiltered list of all species suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the species (used in sql LIKE clause)
     * @return a [distinct], unfiltered list of all species suitable for autocomplete
     * sourcing.
     * */
    @RequestMapping(value = "/getSpecies"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getSpecies(@RequestParam String filterTerm) {
        return backgroundManager.getSpecies(filterTerm.trim());
    }
}
