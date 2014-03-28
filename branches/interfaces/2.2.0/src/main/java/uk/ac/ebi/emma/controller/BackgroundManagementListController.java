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
import uk.ac.ebi.emma.entity.Background;
import uk.ac.ebi.emma.manager.BackgroundsManager;
import uk.ac.ebi.emma.util.Filter;

/**
 *
 * @author mrelac 
 */
@Controller
@RequestMapping("/backgroundManagementList")
public class BackgroundManagementListController {
    private final BackgroundsManager backgroundManager = new BackgroundsManager();
    
    /**
     * 'Go' button implementation
     * 
     * @param filterBackgroundKey the background id search criterion (may be empty)
     * @param filterBackgroundName the background name search criterion (may be empty)
     * @param filterBackgroundSymbol the background symbol search criterion (may be empty)
     * @param filterBackgroundIsCurated the curated search criterion (may be empty)
     * @param filterBackgroundIsInbred the inbred search criterion (may be empty)
     * @param model the data model
     * @return the view to show
     */
    @RequestMapping(value="/go", method=RequestMethod.GET)
    public String go(
            @RequestParam(value="filterBackgroundKey")       String filterBackgroundKey
          , @RequestParam(value="filterBackgroundName")      String filterBackgroundName
          , @RequestParam(value="filterBackgroundSymbol")    String filterBackgroundSymbol
          , @RequestParam(value="filterBackgroundIsCurated") String filterBackgroundIsCurated
          , @RequestParam(value="filterBackgroundIsInbred")  String filterBackgroundIsInbred
          , Model model)
    {
        Filter filter = new Filter();
        filter.setBackground_key(filterBackgroundKey != null ? filterBackgroundKey : "");
        filter.setBackgroundName(filterBackgroundName != null ? filterBackgroundName : "");
        filter.setBackgroundSymbol(filterBackgroundSymbol != null ? filterBackgroundSymbol : "");
        filter.setBackgroundIsCurated(filterBackgroundIsCurated != null ? filterBackgroundIsCurated : "");
        filter.setBackgroundIsInbred(filterBackgroundIsInbred != null ? filterBackgroundIsInbred : "");
        
        model.addAttribute("filter", filter);
        List<Background> filteredBackgroundsList = backgroundManager.getFilteredBackgroundsList(filter);
        model.addAttribute("filteredBackgroundsList", filteredBackgroundsList);
        model.addAttribute("showResultsForm", true);
        model.addAttribute("resultsCount", filteredBackgroundsList.size());
    
        return "backgroundManagementList";
    }
    
    /**
     * Deletes the background identified by <b>id</b>. This method is configured as
     * a GET because it is intended to be called as an ajax call. Using GET
     * avoids re-posting problems with the back button. NOTE: It is the caller's
     * responsibility to insure there are no foreign key constraints.
     * 
     * @param background_key primary key of the background to be deleted
     * @return a JSON string containing 'status' [ok or fail], and a message [
     * empty string if status is ok; error message otherwise]
     */
    @RequestMapping(value = "/deleteBackground"
                  , method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> deleteBackground(@RequestParam int background_key) {
        String status, message;
        
        try {
            backgroundManager.delete(background_key);
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
     * background management curation, the filter parameter values are optional.
     * @param filterBackgroundKey the background id search criterion (may be empty)
     * @param filterBackgroundName the background name search criterion (may be empty)
     * @param filterBackgroundSymbol the background symbol search criterion (may be empty)
     * @param filterIsCurated the curated search criterion (may be empty)
     * @param filterIsInbred the inbred search criterion (may be empty)
     * @param model the data model
     * @return the view to show
     */
    @RequestMapping(value="/showFilter", method=RequestMethod.GET)
    public String showFilter(
            @RequestParam(value="filterBackgroundKey",       required=false) String filterBackgroundKey
          , @RequestParam(value="filterBackgroundName",      required=false) String filterBackgroundName
          , @RequestParam(value="filterBackgroundSymbol",    required=false) String filterBackgroundSymbol
          , @RequestParam(value="filterBackgroundIsCurated", required=false) String filterIsCurated
          , @RequestParam(value="filterBackgroundIsInbred",  required=false) String filterIsInbred
          , Model model)
    {
        Filter filter = new Filter();
        filter.setBackground_key(filterBackgroundKey != null ? filterBackgroundKey : "");
        filter.setBackgroundName(filterBackgroundName != null ? filterBackgroundName : "");
        filter.setBackgroundSymbol(filterBackgroundSymbol != null ? filterBackgroundSymbol : "");
        filter.setBackgroundIsCurated(filterIsCurated != null ? filterIsCurated : "");
        filter.setBackgroundIsInbred(filterIsInbred != null ? filterIsInbred : "");
        
        model.addAttribute("filter", filter);
        model.addAttribute("showResultsForm", false);
        
        return "backgroundManagementList";
    }
    
    
    // GETTERS

    
    /**
     * Returns a distinct filtered list of background names suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the background symbol (used in sql LIKE clause)
     * @@return a <code>List&lt;String&gt;</code> of distinct background names filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     * */
    @RequestMapping(value = "/getBackgroundNames"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getBackgroundNames(@RequestParam String filterTerm) {
        return backgroundManager.getNames(filterTerm.trim());
    }    
    
    /**
     * Returns a distinct filtered list of background symbols suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the background symbol (used in sql LIKE clause)
     * @@return a <code>List&lt;String&gt;</code> of distinct background ids filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     * */
    @RequestMapping(value = "/getBackgroundSymbols"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getBackgroundSymbols(@RequestParam String filterTerm) {
        return backgroundManager.getSymbols(filterTerm.trim());
    }

    
}
