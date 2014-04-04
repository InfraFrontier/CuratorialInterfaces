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
import java.util.Map;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.emma.entity.Allele;
import uk.ac.ebi.emma.manager.AllelesManager;
import uk.ac.ebi.emma.manager.GenesManager;
import uk.ac.ebi.emma.util.Filter;

/**
 *
 * @author mrelac 
 */
@Controller
@RequestMapping("/alleleManagementList")
public class AlleleManagementListController {
    private final AllelesManager allelesManager = new AllelesManager();
    private final GenesManager genesManager = new GenesManager();
    
    /**
     * Because this is a @ModelAttribute it gets called before every other method
     * call. This behaviour is used to build the filter and load it into the model.
     * 
     * @param filter - the filter instance
     * @param model 
     */
    @ModelAttribute
    public void populateFilter(
            @ModelAttribute Filter filter
          , Model model) {
        
        model.addAttribute(filter);
    }
    
    /**
     * 'Go' button implementation
     * 
     * @param model the data model
     * @return the view to show
     */
    @RequestMapping(value="/go", method=RequestMethod.GET)
    public String go(Model model)
    {
        // Get the filter, then re-add it to the model.
        Map modelMap = model.asMap();
        Filter filter = (Filter)modelMap.get("filter");
        model.addAttribute("filter", filter);
        
        List<Allele> filteredAllelesList = allelesManager.getFilteredAllelesList(filter);
        model.addAttribute("filteredAllelesList", filteredAllelesList);
        model.addAttribute("showResultsForm", true);
        model.addAttribute("resultsCount", filteredAllelesList.size());
    
        return "alleleManagementList";
    }
    
    /**
     * Deletes the allele identified by <b>id</b>. This method is configured as
     * a GET because it is intended to be called as an ajax call. Using GET
     * avoids re-posting problems with the back button. NOTE: It is the caller's
     * responsibility to insure there are no foreign key constraints.
     * 
     * @param allele_key primary key of the allele to be deleted
     * @return a JSON string containing 'status' [ok or fail], and a message [
     * empty string if status is ok; error message otherwise]
     */
    @RequestMapping(value = "/deleteAllele"
                  , method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> deleteAllele(@RequestParam(value="allele_key", required=true) int allele_key) {
        String status, message;
        
        try {
            allelesManager.delete(allele_key);
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
     * allele management curation, the filter parameter values are optional.
     * @param model the data model
     * @return the view to show
     */
    @RequestMapping(value="/showFilter", method=RequestMethod.GET)
    public String showFilter(Model model)
    {
        model.addAttribute("showResultsForm", false);
        
        return "alleleManagementList";
    }
    
    
    // GETTERS

    
    /**
     * Returns a distinct filtered list of allele names suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the allele symbol (used in sql LIKE clause)
     * @@return a <code>List&lt;String&gt;</code> of distinct allele names filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     * */
    @RequestMapping(value = "/getAlleleNames"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getAlleleNames(@RequestParam String filterTerm) {
        return allelesManager.getNames(filterTerm.trim());
    }    
    
    /**
     * Returns a distinct filtered list of allele symbols suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the allele symbol (used in sql LIKE clause)
     * @@return a <code>List&lt;String&gt;</code> of distinct allele ids filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     * */
    @RequestMapping(value = "/getAlleleSymbols"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getAlleleSymbols(@RequestParam String filterTerm) {
        return allelesManager.getSymbols(filterTerm.trim());
    }
    
    /**
     * Returns a distinct filtered list of allele MGI references suitable 
     * for autocomplete sourcing.
     * 
     * @param filterTerm the filter term for the allele MGI reference (used in sql LIKE clause)
     * @@return a <code>List&lt;String&gt;</code> of distinct allele MGI references filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     * */
    @RequestMapping(value = "/getAlleleMgiReferences"
                  , method = RequestMethod.GET
    )
    @ResponseBody
    public List<String> getAlleleMgiReferences(@RequestParam String filterTerm) {
        return allelesManager.getMGIReferences(filterTerm.trim());
    }
    
    /**
     * Returns a distinct filtered list of gene MGI references suitable 
     * for autocomplete sourcing.
     * 
     * @param filterTerm the filter term for the gene MGI reference (used in sql LIKE clause)
     * @@return a <code>List&lt;String&gt;</code> of distinct gene MGI references filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     * */
    @RequestMapping(value = "/getGeneMgiReferences"
                  , method = RequestMethod.GET
    )
    @ResponseBody
    public List<String> getGeneMgiReferences(@RequestParam String filterTerm) {
        return genesManager.getMGIReferences(filterTerm.trim());
    }
    
}
