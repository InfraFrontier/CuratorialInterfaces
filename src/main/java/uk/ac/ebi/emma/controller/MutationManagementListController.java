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

package uk.ac.ebi.emma.controller;

import java.util.List;
import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.emma.entity.Mutation;
import uk.ac.ebi.emma.manager.MutationsManager;
import uk.ac.ebi.emma.util.Filter;
import uk.ac.ebi.emma.util.Utils;

/**
 *
 * @author mrelac 
 */
@Controller
@RequestMapping("/mutationManagementList")
public class MutationManagementListController {
    protected final Logger logger = Logger.getLogger(this.getClass());
    private final MutationsManager mutationsManager = new MutationsManager();

    /**
     * 'Go' button implementation
     * 
     * @param filterMutationKey the mutation primary key search criterion (may be empty)
     * @param filterMutationType the mutation type search criterion (may be empty)
     * @param filterMutationSubtype the mutation subtype search criterion (may be empty)
     * @param filterStrainKey the strain primary key search criterion (may be empty)
     * @param filterAlleleKey the allele primary key search criterion (may be empty)
     * @param filterBackgroundKey the background primary key search criterion (may be empty)
     * @param filterGeneKey the gene primary key search criterion (may be empty)
     * @param filterGeneSymbol the gene symbol search criterion (may be empty)
     * @param model the data model
     * @return the view to show
     */
    @RequestMapping(value="/go", method=RequestMethod.GET)
    public String go(
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
        Filter filter = new Filter();
        filter.setMutation_key(filterMutationKey != null ? filterMutationKey : "");
        filter.setMutationType(filterMutationType != null ? filterMutationType : "");
        filter.setMutationSubtype(filterMutationSubtype != null ? filterMutationSubtype : "");
        filter.setStrain_key(filterStrainKey != null ? filterStrainKey : "");
        filter.setAllele_key(filterAlleleKey != null ? filterAlleleKey : "");
        filter.setBackground_key(filterBackgroundKey != null ? filterBackgroundKey : "");
        filter.setGene_key(filterGeneKey != null ? filterGeneKey : "");
        filter.setGeneSymbol(filterGeneSymbol != null ? filterGeneSymbol : "");
        
        model.addAttribute("filter", filter);
        
        List<Mutation> filteredMutationsList = mutationsManager.getFilteredMutationsList(filter);
        model.addAttribute("filteredMutationsList", filteredMutationsList);
        model.addAttribute("showResultsForm", true);
        model.addAttribute("resultsCount", filteredMutationsList.size());
        model.addAttribute("emmaContextPath", Utils.getEmmaContextPath());
        
        return "mutationManagementList";
    }
    
    /**
     * Deletes the mutations identified by <b>id</b>. This method is configured as
     * a GET because it is intended to be called as an ajax call. Using GET
     * avoids re-posting problems with the back button. NOTE: It is the caller's
     * responsibility to insure there are no foreign key constraints.
     * 
     * @param mutation_key primary key of the mutation to be deleted
     * @return a JSON string containing 'status' [ok or fail], and a message [
     * empty string if status is ok; error message otherwise]
     */
    @RequestMapping(value = "/deleteMutation"
                  , method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> deleteMutation(@RequestParam int mutation_key) {
        String status, message;
        
        try {
            mutationsManager.delete(mutation_key);
            status = "ok";
            message = "";
        } catch (Exception e) {
            status = "fail";
            message = e.getLocalizedMessage();
        }
        
        JSONObject returnStatus = new JSONObject();
        returnStatus.put("status", status);
        returnStatus.put("message",   message);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json; charset=utf-8");
        
        return new ResponseEntity(returnStatus.toJSONString(), headers, HttpStatus.OK); 
    }
    
    /**
     * Displays the form with no results grid. Since this is the entry point to
     * mutation management curation, the filter parameter values are optional.
     * @param filterMutationKey the mutation primary key search criterion (may be empty)
     * @param filterMutationType the mutation type search criterion (may be empty)
     * @param filterMutationSubtype the mutation subtype search criterion (may be empty)
     * @param filterStrainKey the strain primary key search criterion (may be empty)
     * @param filterAlleleKey the allele primary key search criterion (may be empty)
     * @param filterBackgroundKey the background primary key search criterion (may be empty)
     * @param filterGeneKey the gene primary key search criterion (may be empty)
     * @param filterGeneSymbol the gene symbol search criterion (may be empty)
     * @param model the data model
     * @return the view to show
     */
    @RequestMapping(value="/showFilter", method=RequestMethod.GET)
    public String showFilter(
            @RequestParam(value="filterMutationKey", required=false) String filterMutationKey
          , @RequestParam(value="filterMutationType", required=false) String filterMutationType
          , @RequestParam(value="filterMutationSubtype", required=false) String filterMutationSubtype
          , @RequestParam(value="filterStrainKey", required=false) String filterStrainKey
          , @RequestParam(value="filterAlleleKey", required=false) String filterAlleleKey
          , @RequestParam(value="filterBackgroundKey", required=false) String filterBackgroundKey
          , @RequestParam(value="filterGeneKey", required=false) String filterGeneKey
          , @RequestParam(value="filterGeneSymbol", required=false) String filterGeneSymbol
          , Model model)
    {
        Filter filter = new Filter();
        filter.setMutation_key(filterMutationKey != null ? filterMutationKey : "");
        filter.setMutationType(filterMutationType != null ? filterMutationType : "");
        filter.setMutationSubtype(filterMutationSubtype != null ? filterMutationSubtype : "");
        filter.setStrain_key(filterStrainKey != null ? filterStrainKey : "");
        filter.setAllele_key(filterAlleleKey != null ? filterAlleleKey : "");
        filter.setBackground_key(filterBackgroundKey != null ? filterBackgroundKey : "");
        filter.setGene_key(filterGeneKey != null ? filterGeneKey : "");
        filter.setGeneSymbol(filterGeneSymbol != null ? filterGeneSymbol : "");
        
        model.addAttribute("filter", filter);
        model.addAttribute("showResultsForm", false);
        
        return "mutationManagementList";
    }
    
    
    // GETTERS

    
    /**
     * Returns a distinct filtered list of mutation types suitable for autocomplete
     * sourcing.
     * 
     * @@return a <code>List&lt;String&gt;</code> of distinct mutation types filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     * */
    @RequestMapping(value = "/getMutationTypes"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getMutationTypes() {
        return mutationsManager.getTypes();
    }    
    
    /**
     * Returns a distinct filtered list of mutation subtypes suitable for autocomplete
     * sourcing.
     * 
     * @@return a <code>List&lt;String&gt;</code> of distinct mutation subtypes filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     * */
    @RequestMapping(value = "/getMutationSubtypes"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getMutationSubtypes() {
        return mutationsManager.getSubtypes();
    }
    
    
    // PRIVATE METHODS
    

}
