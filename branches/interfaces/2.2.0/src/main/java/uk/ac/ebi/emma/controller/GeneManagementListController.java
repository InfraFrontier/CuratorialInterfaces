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

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.ac.ebi.emma.entity.Gene;
import uk.ac.ebi.emma.manager.GenesManager;
import uk.ac.ebi.emma.util.Filter;
//import org.emmanet.model.GenesDAO;
//import org.emmanet.model.GenesManager;
//import org.emmanet.util.Filter;
/*
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;
*/
/**
 *
 * @author mrelac 
 */
@Controller
@RequestMapping("/interfaces/geneManagementList")
public class GeneManagementListController {
    private final GenesManager genesManager = new GenesManager();
    private Map<String, List<String>> options = null;
    
//    @RequestMapping(method=RequestMethod.GET)
//    public String showForm(Model model) {
//        model.addAttribute("geneIdFilter", "");
//        
//        return "interfaces/geneManagementList";
////        return model;
////        return "ALIVE!";
//    }
    
    
    
//    @RequestMapping(value="/search", method=RequestMethod.GET)
//    public @ModelAttribute("genes") Collection<Gene> search() {
//        List<Gene> genes = genesManager.getGenes();
//    
//        return genes;
//    }
    
    @RequestMapping(method=RequestMethod.GET)
    public @ModelAttribute("filter") Object showForm() {
        options = new HashMap();
        options.put("geneIds", genesManager.getGeneIds());
        options.put("geneNames", genesManager.getNames());
        options.put("geneSymbols", genesManager.getSymbols());
        options.put("chromosomes", genesManager.getChromosomes());
        options.put("mgiReferences", genesManager.getMGIReferences());
        
        Map<String, Object> map = new HashMap<>();
        map.put("filter",  new Filter());
        map.put("options", options);
        
        return new Filter();
    }




    
//    @RequestMapping(value="/getGeneList", method=RequestMethod.GET)
//    public String getGeneList(ModelMap model) {
        
//    }
    
//    @RequestMapping("/initializeform")
//    public String initializeForm(@RequestParam(value="name", required=false, defaultValue="World") String name, Model model) {
//        model.addAttribute("name", name);
//        return "geneManagementList";
//    }
    
    
    
    
//    private Map<String, List<String>> options = null;
//    private final GenesManager genesManager = new GenesManager();
    
//	public GeneManagementListController(){
//            setCommandClass(Filter.class);
//            setCommandName("filter");
//	}
    
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

    
    public Map getOptions() {
        return options;
    }


}
