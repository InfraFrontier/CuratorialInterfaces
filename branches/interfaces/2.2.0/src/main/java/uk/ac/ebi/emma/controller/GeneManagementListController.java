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
import uk.ac.ebi.emma.entity.Gene;
import uk.ac.ebi.emma.manager.GenesManager;
import uk.ac.ebi.emma.util.Filter;

/**
 *
 * @author mrelac 
 */
@Controller
@RequestMapping("/geneManagementList")
public class GeneManagementListController {
    private final GenesManager genesManager = new GenesManager();
    
    /**
     * 'Go' button implementation
     * 
     * @param geneId the gene id search criterion (may be empty)
     * @param geneName the gene name search criterion (may be empty)
     * @param geneSymbol the gene symbol search criterion (may be empty)
     * @param chromosome the chromosome search criterion (may be empty)
     * @param geneMgiReference the MGI reference search criterion (may be empty)
     * @param model the data model
     * @return the view to show
     */
    @RequestMapping(value="/go", method=RequestMethod.GET)
    public String go(
            @RequestParam(value="geneId") String geneId
          , @RequestParam(value="geneName") String geneName
          , @RequestParam(value="geneSymbol") String geneSymbol
          , @RequestParam(value="chromosome") String chromosome
          , @RequestParam(value="geneMgiReference") String geneMgiReference
          , Model model)
    {
        Filter filter = new Filter();
        filter.setGeneId(geneId != null ? geneId : "");
        filter.setGeneName(geneName != null ? geneName : "");
        filter.setGeneSymbol(geneSymbol != null ? geneSymbol : "");
        filter.setChromosome(chromosome != null ? chromosome : "");
        filter.setGeneMgiReference(geneMgiReference != null ? geneMgiReference : "");
        model.addAttribute("filter", filter);
        List<Gene> filteredGenesList = genesManager.getFilteredGenesList(filter);
        model.addAttribute("filteredGenesList", filteredGenesList);
        model.addAttribute("showResultsForm", true);
        model.addAttribute("resultsCount", filteredGenesList.size());
    
        return "geneManagementList";
    }
    
    /**
     * Deletes the gene identified by <b>id</b>. This method is configured as
     * a GET because it is intended to be called as an ajax call. Using GET
     * avoids re-posting problems with the back button. NOTE: It is the caller's
     * responsibility to insure there are no foreign key constraints.
     * 
     * @param id_gene primary key of the gene to be deleted
     * @return a JSON string containing 'status' [ok or fail], and a message [
     * empty string if status is ok; error message otherwise]
     */
    @RequestMapping(value = "/deleteGene"
                  , method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> deleteGene(@RequestParam int id_gene) {
        String status, message;
        
        try {
            genesManager.delete(id_gene);
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
     * Displays the form with no results grid.
     * @param geneId the gene id search criterion (may be empty)
     * @param geneName the gene name search criterion (may be empty)
     * @param geneSymbol the gene symbol search criterion (may be empty)
     * @param chromosome the chromosome search criterion (may be empty)
     * @param geneMgiReference the MGI reference search criterion (may be empty)
     * @param model the data model
     * @return the view to show
     */
    @RequestMapping(value="/showFilter", method=RequestMethod.GET)
    public String showFilter(
            @RequestParam(value="geneId", required=false) String geneId
          , @RequestParam(value="geneName", required=false) String geneName
          , @RequestParam(value="geneSymbol", required=false) String geneSymbol
          , @RequestParam(value="chromosome", required=false) String chromosome
          , @RequestParam(value="geneMgiReference", required=false) String geneMgiReference
            
          , Model model)
    {
        Filter filter = new Filter();
        filter.setGeneId((geneId != null ? geneId : ""));
        filter.setGeneName((geneName != null ? geneName : ""));
        filter.setGeneSymbol((geneSymbol != null ? geneSymbol : ""));
        filter.setChromosome((chromosome != null ? chromosome : ""));
        filter.setGeneMgiReference((geneMgiReference != null ? geneMgiReference : ""));
        
        model.addAttribute("filter", filter);
        model.addAttribute("showResultsForm", false);
        
        return "geneManagementList";
    }
    
    
    // GETTERS

    
    /**
     * Returns a [distinct], unfiltered list of all chromosomes suitable for autocomplete
     * sourcing.
     * 
     * @return a [distinct], unfiltered list of all chromosomes suitable for autocomplete
     * sourcing.
     * */
    @RequestMapping(value = "/getChromosomes"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getChromosomes() {
        return genesManager.getChromosomes();
    }
    
    /**
     * Returns a [distinct], unfiltered list of all gene ids suitable for autocomplete
     * sourcing.
     * 
     * @@return a [distinct], unfiltered list of all gene ids suitable for autocomplete
     * sourcing.
     * */
    @RequestMapping(value = "/getGeneIds"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getGeneIds() {
        return genesManager.getGeneIds();
    }

    /**
     * Returns a distinct filtered list of gene names suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the gene symbol (used in sql LIKE clause)
     * @@return a <code>List&lt;String&gt;</code> of distinct gene names filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     * */
    @RequestMapping(value = "/getGeneNames"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getGeneNames(@RequestParam String filterTerm) {
        return genesManager.getNames(filterTerm);
    }    
    
    /**
     * Returns a distinct filtered list of gene symbols suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the gene symbol (used in sql LIKE clause)
     * @@return a <code>List&lt;String&gt;</code> of distinct gene ids filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     * */
    @RequestMapping(value = "/getGeneSymbols"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getGeneSymbols(@RequestParam String filterTerm) {
        return genesManager.getSymbols(filterTerm);
    }

    /**
     * Returns a distinct filtered list of MGI references suitable from genes
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
        return genesManager.getMGIReferences(filterTerm);
    }

    
}
