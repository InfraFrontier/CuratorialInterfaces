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

/**
 *
 * @author mrelac 
 */
@Controller
@RequestMapping("/mutationManagementList")
public class MutationManagementListController {
    private final MutationsManager mutationsManager = new MutationsManager();
    
    /**
     * 'Go' button implementation
     * 
     * @param filterMutationId the mutation id search criterion (may be empty)
     * @param filterMutationType the mutation type search criterion (may be empty)
     * @param filterMutationSubtype the mutation subtype search criterion (may be empty)
     * @param filterStrainId the strain id search criterion (may be empty)
     * @param filterAlleleId the allele id search criterion (may be empty)
     * @param filterBackgroundId the background id search criterion (may be empty)
     * @param model the data model
     * @return the view to show
     */
    @RequestMapping(value="/go", method=RequestMethod.GET)
    public String go(
            @RequestParam(value="filterMutationId") String filterMutationId
          , @RequestParam(value="filterMutationType") String filterMutationType
          , @RequestParam(value="filterMutationSubtype") String filterMutationSubtype
          , @RequestParam(value="filterStrainId") String filterStrainId
          , @RequestParam(value="filterAlleleId") String filterAlleleId
          , @RequestParam(value="filterBackgroundId") String filterBackgroundId
          , Model model)
    {
        Filter filter = new Filter();
        filter.setMutationId(filterMutationId != null ? filterMutationId : "");
        filter.setMutationType(filterMutationType != null ? filterMutationType : "");
        filter.setMutationSubtype(filterMutationSubtype != null ? filterMutationSubtype : "");
        filter.setStrainId(filterStrainId != null ? filterStrainId : "");
        filter.setAlleleId(filterAlleleId != null ? filterAlleleId : "");
        filter.setBackgroundId(filterBackgroundId != null ? filterBackgroundId : "");
        
        model.addAttribute("filter", filter);
        
        List<Mutation> filteredMutationsList = mutationsManager.getFilteredMutationsList(filter);
        model.addAttribute("filteredMutationsList", filteredMutationsList);
        model.addAttribute("showResultsForm", true);
        model.addAttribute("resultsCount", filteredMutationsList.size());
    
        return "mutationManagementList";
    }
    
    /**
     * Deletes the mutations identified by <b>id</b>. This method is configured as
     * a GET because it is intended to be called as an ajax call. Using GET
     * avoids re-posting problems with the back button. NOTE: It is the caller's
     * responsibility to insure there are no foreign key constraints.
     * 
     * @param id_mutation primary key of the mutation to be deleted
     * @return a JSON string containing 'status' [ok or fail], and a message [
     * empty string if status is ok; error message otherwise]
     */
    @RequestMapping(value = "/deleteMutation"
                  , method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> deleteMutation(@RequestParam int id_mutation) {
        String status, message;
        
        try {
            mutationsManager.delete(id_mutation);
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
     * @param filterMutationId the mutation id search criterion (may be empty)
     * @param filterMutationType the mutation type search criterion (may be empty)
     * @param filterMutationSubtype the mutation subtype search criterion (may be empty)
     * @param filterStrainId the strain id search criterion (may be empty)
     * @param filterAlleleId the allele id search criterion (may be empty)
     * @param filterBackgroundId the background id search criterion (may be empty)
     * @param model the data model
     * @return the view to show
     */
    @RequestMapping(value="/showFilter", method=RequestMethod.GET)
    public String showFilter(
            @RequestParam(value="filterMutationId", required=false) String filterMutationId
          , @RequestParam(value="filterMutationType", required=false) String filterMutationType
          , @RequestParam(value="filterMutationSubtype", required=false) String filterMutationSubtype
          , @RequestParam(value="filterStrainId", required=false) String filterStrainId
          , @RequestParam(value="filterAlleleId", required=false) String filterAlleleId
          , @RequestParam(value="filterBackgroundId", required=false) String filterBackgroundId
          , Model model)
    {
        Filter filter = new Filter();
        filter.setMutationId(filterMutationId != null ? filterMutationId : "");
        filter.setMutationType(filterMutationType != null ? filterMutationType : "");
        filter.setMutationSubtype(filterMutationSubtype != null ? filterMutationSubtype : "");
        filter.setStrainId(filterStrainId != null ? filterStrainId : "");
        filter.setAlleleId(filterAlleleId != null ? filterAlleleId : "");
        filter.setBackgroundId(filterBackgroundId != null ? filterBackgroundId : "");
        
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
        return mutationsManager.getMutationTypes();
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
        return mutationsManager.getMutationSubtypes();
    }
}
