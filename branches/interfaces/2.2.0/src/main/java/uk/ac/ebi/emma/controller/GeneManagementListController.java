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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
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
    @Autowired
    private GenesManager genesManager;
    
    @RequestMapping(method=RequestMethod.GET)
    public @ModelAttribute("filter") Filter showForm(
            Model model)
    {
        model.addAttribute("showResultsForm", false);
        return new Filter();
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
     * Returns a distinct filtered list of MGI references suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the gene symbol (used in sql LIKE clause)
     * @@return a <code>List&lt;String&gt;</code> of distinct MGI references filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     * */
    @RequestMapping(value = "/getMGIReferences"
                  , method = RequestMethod.GET
    )
    @ResponseBody
    public List<String> getMGIReferences(@RequestParam String filterTerm) {
        return genesManager.getMGIReferences(filterTerm);
    }
    
    /**
     * 'Go' button implementation
     * 
     * @param filter the search criteria
     * @param model the results
     * @return the results model
     */
    @RequestMapping(value="/applyFilter", method=RequestMethod.GET)
    public String search(
            @ModelAttribute("filter") Filter filter
          , Model model)
    {
        List<Gene> filteredGenesList = genesManager.getFilteredGenesList(filter);
        model.addAttribute("filteredGenesList", filteredGenesList);
        model.addAttribute("showResultsForm", true);
        model.addAttribute("resultsCount", filteredGenesList.size());
    
        return "geneManagementList";
    }
    
    
    
    

    
    
    

    
////////    @RequestMapping(method=RequestMethod.GET)
////////    public @ModelAttribute("filter") Filter showForm(
//////////              @RequestParam(value="geneIds", required=false) List<String> geneIds
//////////            , @RequestParam(value="geneNames", required=false) List<String> geneNames
//////////            , @RequestParam(value="geneSymbols", required=false) List<String> geneSymbols
//////////            , @RequestParam(value="chromosomes", required=false) List<String> chromosomes
//////////            , @RequestParam(value="mgiReferences", required=false) List<String> mgiReferences
////////              @RequestParam(value="hidShowResultsForm", required=false) String hidShowResultsForm
//////////            , @RequestParam(value="options", required=false) Map<String, List<String>> options
////////    ) {
//////////        options = new HashMap();
//////////        options.put("geneIds", genesManager.getGeneIds());
//////////        options.put("geneNames", genesManager.getNames());
//////////        options.put("geneSymbols", genesManager.getSymbols());
//////////        options.put("chromosomes", genesManager.getChromosomes());
//////////        options.put("mgiReferences", genesManager.getMGIReferences());
////////        
////////        
//////////        geneIds = genesManager.getGeneIds();
//////////        geneNames = genesManager.getNames();
//////////        geneSymbols = genesManager.getSymbols();
//////////        chromosomes = genesManager.getChromosomes();
//////////        mgiReferences = genesManager.getMGIReferences();
////////        hidShowResultsForm = "0";
////////        
////////        
////////        
//////////        Map<String, Object> map = new HashMap<>();
//////////        map.put("filter",  new Filter());
//////////        map.put("options", options);
////////        
////////        return new Filter();
////////    }
    
//    @RequestMapping(value="/applyFilter", method=RequestMethod.GET)
//    public void applyFilter(
//              @RequestParam(value="filter", required=true) Filter filter
//            , @RequestParam(value="filteredGenesDAOList", required=false) List<Gene> filteredGenesList
//            , @RequestParam(value="hidShowResultsForm", required=false) String hidShowResultsForm
//            , @RequestParam(value="resultsCount", required=false) int resultsCount
//    ) {
//        filteredGenesList = genesManager.getFilteredGenesList(filter);
//        hidShowResultsForm = "1";                                               // Show results div now.
//        resultsCount = filteredGenesList.size();
//    }

    

    
    
    
//    @RequestMapping(value="/getGeneList", method=RequestMethod.GET)
//    public String getGeneList(ModelMap model) {
        
//    }
    
//    @RequestMapping("/initializeform")
//    public String initializeForm(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
//        model.addAttribute("name", name);
//        return "geneManagementList";
//    }
    
    

    /**
     * Initialize the form backing object.
     * 
     * @param request the <code>HttpServletRequest</code> instance
     * @return a new <code>Filter</code> instance
     */
//    @Override
//    protected Object formBackingObject(HttpServletRequest request) {
//        String action = request.getParameter("action");
//        initialize();
//        request.setAttribute("options", options);
//        request.setAttribute("filteredGenesDAOList", new ArrayList());
//        Filter filter = new Filter(request);
//        
//        if (request.getParameter("action") == null) {
//            logger.debug("formBackingObject: hiding divResults.");
//            request.setAttribute("hidShowResultsForm", 0);                      // Hide results div on iinitial entry to form.
//        } else {
//            List<GenesDAO> filteredGenesDAOList = genesManager.getFilteredGenesList(filter);
//            if (action.compareToIgnoreCase("applyFilter") == 0) {
//                request.setAttribute("hidShowResultsForm", 1);                  // Show results div now.
//            }
//            request.setAttribute("filteredGenesDAOList", filteredGenesDAOList);
//            request.setAttribute("resultsCount", filteredGenesDAOList.size());
//        }
//        
//        return filter;
//    }
    
    /**
     * Process the form submission (GET or POST).
     * 
     * @param request the <code>HttpServletRequest</code> instance
     * @param response the <code>HttpServletResponse</code> instance
     * @param command the form's fields (model) to be operated upon
     * @param errors the <code>BindException</code> errors instance
     * @return the <code>ModelAndView</code> instance to invoke
     * @throws Exception upon error
     */
//    @Override
//    protected ModelAndView processFormSubmission(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            Object command,
//            BindException errors)
//            throws Exception {
//        
//        Filter filter = (Filter)command;
//        
//        String action = request.getParameter("action");
//        if (action == null) {
//            logger.debug("processFormSubmission: action is null.");
//        } else {
//            logger.debug("processFormSubmission: action = " + action);
//            if (action.compareToIgnoreCase("deleteGene") == 0) {
//                int id = Integer.parseInt(request.getParameter("id"));
//                genesManager.delete(genesManager.getGene(id));
//            }
//            List<GenesDAO> filteredGenesDAOList = genesManager.getFilteredGenesList(filter);
//            request.setAttribute("filteredGenesDAOList", filteredGenesDAOList);
//            request.setAttribute("resultsCount", filteredGenesDAOList.size());
//        }
//
//        return new ModelAndView(getSuccessView(), "filter", filter);
//    }
    
    
    // PRIVATE METHODS
    
    
//    private void initialize() {
//        options = new HashMap();
//        options.put("geneIds", genesManager.getGeneIds());
//        options.put("geneNames", genesManager.getNames());
//        options.put("geneSymbols", genesManager.getSymbols());
//        options.put("chromosomes", genesManager.getChromosomes());
//        options.put("mgiReferences", genesManager.getMGIReferences());
//    }

    
    // GETTERS AND SETTERS

    
//    public Map getOptions() {
//        return options;
//    }


}
