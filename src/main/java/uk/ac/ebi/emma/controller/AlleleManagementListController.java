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
import uk.ac.ebi.emma.entity.Allele;
import uk.ac.ebi.emma.manager.AllelesManager;
import uk.ac.ebi.emma.util.Filter;

/**
 *
 * @author mrelac 
 */
@Controller
@RequestMapping("/alleleManagementList")
public class AlleleManagementListController {
    private final AllelesManager allelesManager = new AllelesManager();
    
    /**
     * 'Go' button implementation
     * 
     * @param filterAlleleId the allele id search criterion (may be empty)
     * @param filterAlleleName the allele name search criterion (may be empty)
     * @param filterAlleleSymbol the allele symbol search criterion (may be empty)
     * @param filterGeneId the gene id search criterion (may be empty)
     * @param filterGeneName the gene name search criterion (may be empty)
     * @param filterGeneSymbol the gene symbol search criterion (may be empty)
     * @param filterAlleleMgiReference the allele MGI reference search criterion (may be empty)
     * @param model the data model
     * @return the view to show
     */
    @RequestMapping(value="/go", method=RequestMethod.GET)
    public String go(
            @RequestParam(value="filterAlleleId") String filterAlleleId
          , @RequestParam(value="filterAlleleName") String filterAlleleName
          , @RequestParam(value="filterAlleleSymbol") String filterAlleleSymbol
          , @RequestParam(value="filterAlleleMgiReference") String filterAlleleMgiReference
          , @RequestParam(value="filterGeneId") String filterGeneId
          , @RequestParam(value="filterGeneName") String filterGeneName
          , @RequestParam(value="filterGeneSymbol") String filterGeneSymbol
          , Model model)
    {
        Filter filter = new Filter();
        filter.setAlleleId(filterAlleleId != null ? filterAlleleId : "");
        filter.setAlleleName(filterAlleleName != null ? filterAlleleName : "");
        filter.setAlleleSymbol(filterAlleleSymbol != null ? filterAlleleSymbol : "");
        filter.setGeneId(filterGeneId != null ? filterGeneId : "");
        filter.setGeneName(filterGeneName != null ? filterGeneName : "");
        filter.setGeneSymbol(filterGeneSymbol != null ? filterGeneSymbol : "");
        filter.setAlleleMgiReference(filterAlleleMgiReference != null ? filterAlleleMgiReference : "");
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
     * @param id_allele primary key of the allele to be deleted
     * @return a JSON string containing 'status' [ok or fail], and a message [
     * empty string if status is ok; error message otherwise]
     */
    @RequestMapping(value = "/deleteAllele"
                  , method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> deleteAllele(@RequestParam int id_allele) {
        String status, message;
        
        try {
            allelesManager.delete(id_allele);
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
     * @param filterAlleleId the allele id search criterion (may be empty)
     * @param filterAlleleName the allele name search criterion (may be empty)
     * @param filterAlleleSymbol the allele symbol search criterion (may be empty)
     * @param filterAlleleMgiReference the allele MGI reference search criterion (may be empty)
     * @param filterGeneId the gene id search criterion (may be empty)
     * @param filterGeneName the gene name search criterion (may be empty)
     * @param filterGeneSymbol the gene symbol search criterion (may be empty)
     * @param model the data model
     * @return the view to show
     */
    @RequestMapping(value="/showFilter", method=RequestMethod.GET)
    public String showFilter(
            @RequestParam(value="filterAlleleId", required=false) String filterAlleleId
          , @RequestParam(value="filterAlleleName", required=false) String filterAlleleName
          , @RequestParam(value="filterAlleleSymbol", required=false) String filterAlleleSymbol
          , @RequestParam(value="filterAlleleMgiReference", required=false) String filterAlleleMgiReference
          , @RequestParam(value="filterGeneId", required=false) String filterGeneId
          , @RequestParam(value="filterGeneName", required=false) String filterGeneName
          , @RequestParam(value="filterGeneSymbol", required=false) String filterGeneSymbol
          , Model model)
    {
        Filter filter = new Filter();
        filter.setAlleleId((filterAlleleId != null ? filterAlleleId : ""));
        filter.setAlleleName((filterAlleleName != null ? filterAlleleName : ""));
        filter.setAlleleSymbol((filterAlleleSymbol != null ? filterAlleleSymbol : ""));
        filter.setAlleleId((filterGeneId != null ? filterGeneId : ""));
        filter.setAlleleName((filterGeneName != null ? filterGeneName : ""));
        filter.setAlleleSymbol((filterGeneSymbol != null ? filterGeneSymbol : ""));
        filter.setAlleleMgiReference((filterAlleleMgiReference != null ? filterAlleleMgiReference : ""));
        
        model.addAttribute("filter", filter);
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
        return allelesManager.getNames(filterTerm);
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
        return allelesManager.getSymbols(filterTerm);
    }
    
    /**
     * Returns a distinct filtered list of MGI references suitable from alleles
     * for autocomplete sourcing.
     * 
     * @param filterTerm the filter term for the gene symbol (used in sql LIKE clause)
     * @@return a <code>List&lt;String&gt;</code> of distinct MGI references filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     * */
    @RequestMapping(value = "/getMgiReferences"
                  , method = RequestMethod.GET
    )
    @ResponseBody
    public List<String> getMgiReferences(@RequestParam String filterTerm) {
        return allelesManager.getMGIReferences(filterTerm);
    }

    
}
