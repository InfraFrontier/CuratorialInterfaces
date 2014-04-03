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
import uk.ac.ebi.emma.entity.Biblio;
import uk.ac.ebi.emma.manager.BibliosManager;
import uk.ac.ebi.emma.util.Filter;
import uk.ac.ebi.emma.util.Utils;

/**
 *
 * @author mrelac 
 */
@Controller
@RequestMapping("/biblioManagementList")
public class BiblioManagementListController {
    private final BibliosManager bibliosManager = new BibliosManager();
    
    /**
     * 'Go' button implementation
     * 
     * @param filterBiblio_key the biblio key search criterion (may be empty)
     * @param filterStrain_key the strain key search criterion (may be empty)
     * @param filterPubmedId the pubmed id search criterion (may be empty)
     * @param filterBiblioAuthor1 the biblio author1 search criterion (may be empty)
     * @param filterBiblioJournal the biblio journal search criterion (may be empty)
     * @param filterBiblioTitle the biblio title search criterion (may be empty)
     * @param filterBiblioYear the biblio year search criterion (may be empty)
     * @param model the data model
     * @return the view to show
     */
    @RequestMapping(value="/go", method=RequestMethod.GET)
    public String go(
            @RequestParam(value="filterBiblioKey",     required=true) String filterBiblio_key
          , @RequestParam(value="filterStrainKey",     required=true) String filterStrain_key
          , @RequestParam(value="filterPubmedId",      required=true) String filterPubmedId
          , @RequestParam(value="filterBiblioAuthor1", required=true) String filterBiblioAuthor1
          , @RequestParam(value="filterBiblioJournal", required=true) String filterBiblioJournal
          , @RequestParam(value="filterBiblioTitle",   required=true) String filterBiblioTitle
          , @RequestParam(value="filterBiblioYear",    required=true) String filterBiblioYear
          , Model model)
    {
        Filter filter = buildFilter(filterBiblio_key, filterStrain_key, filterPubmedId, filterBiblioAuthor1,
                                    filterBiblioJournal, filterBiblioTitle, filterBiblioYear);
        
        model.addAttribute("filter", filter);
        List<Biblio> filteredBibliosList = bibliosManager.getFilteredBibliosList(filter);
        model.addAttribute("filteredBibliosList", filteredBibliosList);
        model.addAttribute("showResultsForm", true);
        model.addAttribute("resultsCount", filteredBibliosList.size());
        model.addAttribute("emmaContextPath", Utils.getEmmaContextPath());
    
        return "biblioManagementList";
    }
    
    /**
     * Deletes the biblio identified by <b>id</b>. This method is configured as
     * a GET because it is intended to be called as an ajax call. Using GET
     * avoids re-posting problems with the back button. NOTE: It is the caller's
     * responsibility to insure there are no foreign key constraints.
     * 
     * @param biblio_key primary key of the biblio to be deleted
     * @return a JSON string containing 'status' [ok or fail], and a message [
     * empty string if status is ok; error message otherwise]
     */
    @RequestMapping(value = "/deleteBiblio"
                  , method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> deleteBiblio(@RequestParam int biblio_key) {
        String status, message;
        
        try {
            bibliosManager.delete(biblio_key);
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
     * biblio management curation, the filter parameter values are optional.
     * @param filterBiblio_key the biblio key search criterion (may be empty)
     * @param filterStrain_key the strain key search criterion (may be empty)
     * @param filterPubmedId the pubmed id search criterion (may be empty)
     * @param filterBiblioAuthor1 the biblio author1 search criterion (may be empty)
     * @param filterBiblioJournal the biblio journal search criterion (may be empty)
     * @param filterBiblioTitle the biblio title search criterion (may be empty)
     * @param filterBiblioYear the biblio year search criterion (may be empty)
     * @param model the data model
     * @return the view to show
     */
    @RequestMapping(value="/showFilter", method=RequestMethod.GET)
    public String showFilter(
            @RequestParam(value="filterBiblioKey",     required=false) String filterBiblio_key
          , @RequestParam(value="filterStrainKey",     required=false) String filterStrain_key
          , @RequestParam(value="filterPubmedId",      required=false) String filterPubmedId
          , @RequestParam(value="filterBiblioAuthor1", required=false) String filterBiblioAuthor1
          , @RequestParam(value="filterBiblioJournal", required=false) String filterBiblioJournal
          , @RequestParam(value="filterBiblioTitle",   required=false) String filterBiblioTitle
          , @RequestParam(value="filterBiblioYear",    required=false) String filterBiblioYear
          , Model model)
    {
        Filter filter = buildFilter(filterBiblio_key, filterStrain_key, filterPubmedId, filterBiblioAuthor1,
                                    filterBiblioJournal, filterBiblioTitle, filterBiblioYear);
        
        model.addAttribute("filter", filter);
        model.addAttribute("showResultsForm", false);
        
        return "biblioManagementList";
    }
    
    
    // GETTERS

    
    /**
     * Returns a distinct filtered list of biblio author1s suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the biblio author1 (used in sql LIKE clause)
     * @@return a <code>List&lt;String&gt;</code> of distinct biblio1 authors filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     * */
    @RequestMapping(value = "/getAuthor1s"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getAuthor1s(@RequestParam String filterTerm) {
        return bibliosManager.getAuthor1s(filterTerm.trim());
    }
    
    /**
     * Returns a distinct filtered list of journals suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the journal (used in sql LIKE clause)
     * @@return a <code>List&lt;String&gt;</code> of distinct journals filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     * */
    @RequestMapping(value = "/getJournals"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getJournals(@RequestParam String filterTerm) {
        return bibliosManager.getJournals(filterTerm.trim());
    }
    
    /**
     * Returns a distinct filtered list of biblio titles suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the biblio title (used in sql LIKE clause)
     * @@return a <code>List&lt;String&gt;</code> of distinct biblio titles filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     * */
    @RequestMapping(value = "/getTitles"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getTitles(@RequestParam String filterTerm) {
        return bibliosManager.getTitles(filterTerm.trim());
    }
    
    /**
     * Returns a distinct filtered list of biblio years suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the biblio year (used in sql LIKE clause)
     * @@return a <code>List&lt;String&gt;</code> of distinct biblio years filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     * */
    @RequestMapping(value = "/getYears"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getYears(@RequestParam String filterTerm) {
        return bibliosManager.getYears(filterTerm.trim());
    }
    
    /**
     * Returns a distinct filtered list of pubmed ids suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the pubmed id (used in sql LIKE clause)
     * @@return a <code>List&lt;String&gt;</code> of distinct pubmed ids filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     * */
    @RequestMapping(value = "/getPubmedIds"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getPubmedIds(@RequestParam String filterTerm) {
        return bibliosManager.getPubmedIds(filterTerm.trim());
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

    
}
