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

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.ac.ebi.emma.manager.GenesManager;

/**
 *
 * @author mrelac
 */
@Controller
@RequestMapping("/interfaces/geneManagementDetail")
public class GeneManagementDetailController /*extends SimpleFormController implements Validator*/ {
    private final GenesManager genesManager = new GenesManager();

    @RequestMapping(method=RequestMethod.GET)
    public String showForm(Model model) {
        model.addAttribute("geneIdFilter", "");
        
        return "interfaces/geneManagementDetail";
    }
    
    
    
    
    
    
    
    
//    
//    @Override
//    protected Object formBackingObject(HttpServletRequest request) {
//        GenesDAO gene = null;
//        String action = request.getParameter("action");
//        if (action == null) {
//            logger.debug("formBackingObject: action is null.");
//        } else {
//            logger.debug("formBackingObject: action = " + action);
//            
//            Integer id = Utils.tryParseInt(request.getParameter("id"));
//            if (id != null) {
//                gene = genesManager.getGene(id.intValue());
//            }
//        }
//        return (gene == null ? new GenesDAO() : gene);
//    }
    
    
    
    
    
    
    
    
    
    
    
    
//
//    /**
//     * Process the form submission (GET or POST).
//     * 
//     * @param request the <code>HttpServletRequest</code> instance
//     * @param response the <code>HttpServletResponse</code> instance
//     * @param command the form's fields (model) to be operated upon
//     * @param errors the <code>BindException</code> errors instance
//     * @return the <code>ModelAndView</code> instance to invoke
//     * @throws Exception upon error
//     */
//    @Override
//      protected ModelAndView onSubmit(
//            HttpServletRequest request,
//            HttpServletResponse response,
//            Object command,
//            BindException errors)
//            throws Exception {
//        
//        GenesDAO gene = (GenesDAO)command;
////        ModelAndView modelAndView = new ModelAndView(getSuccessView(), "gene", gene);
//        ModelAndView modelAndView = new ModelAndView(new RedirectView("geneManagementDetail.emma?id=" + gene.getId_gene() + "&action=editGene"), "gene", gene);
//        String action = request.getParameter("action");
//        if (action == null) {
//            logger.debug("processFormSubmission: action is null.");
//        } else {
//            logger.debug("processFormSubmission: action = " + action);
//            if (action.compareToIgnoreCase("newSynonym") == 0) {
//                genesManager.addSynonym(gene);
//                modelAndView = new ModelAndView(new RedirectView("geneManagementDetail.emma?id=" + gene.getId_gene() + "&action=editGene"), "gene", gene);
//            } else if (action.compareToIgnoreCase("deleteSynonym") == 0) {
//                int id_syn = Utils.tryParseInt(request.getParameter("id_syn"));
//                genesManager.deleteSynonym(gene, id_syn);
//            } else if (action.compareToIgnoreCase("save") == 0) {
//                genesManager.save(gene);
//                modelAndView = new ModelAndView(new RedirectView("geneManagementList.emma"));
//            }
//        }
//        
//        return modelAndView;
//    }
//
//    
//    /**
//     * Required for Validator implementation.
//     * @param clazz caller's class
//     * @return true if caller's class is supported; false otherwise.
//     */
//    @Override
//    public boolean supports(Class clazz) {
//            //just validate the Customer instances
//            return GenesDAO.class.isAssignableFrom(clazz);
//    }
//
//    /**
//     * Required for Validator implementation.
//     * @param target target object to be validated
//     * @param errors errors object
//     */
//    @Override
//    public void validate(Object target, Errors errors) {
//        GenesDAO gene = (GenesDAO)target;
//        
//        // Centimorgan, if supplied, must be an integer.
//        if ((gene.getCentimorgan() != null) && ( ! gene.getCentimorgan().isEmpty())) {
//            Integer centimorgan = Utils.tryParseInt(gene.getCentimorgan());
//            if (centimorgan == null) {
//                errors.rejectValue("centimorgan", null, "Please enter an integer.");
//            }
//        }
//        if ((gene.getName() != null) && (gene.getName().trim().length() == 0)) {
//            errors.rejectValue("name", null, "Please provide a name for the gene.");
//        }
//        
//        Utils.validateMaxFieldLengths(gene, "genes", errors);                   // Validate 'gene' max String lengths
//        
//        // Validate that Syn_GenesDAO String data doesn't exceed maximum varchar lengths.
//        if (gene.getSynonyms() != null) {                                       // Validate each 'synGenes' instance's max String lengths
//            Set<Syn_GenesDAO> geneSynonyms = gene.getSynonyms();
//            Iterator<Syn_GenesDAO> synGenesIterator = geneSynonyms.iterator();
//            int i = 0;
//            while (synGenesIterator.hasNext()) {
//                Syn_GenesDAO geneSynonym = (Syn_GenesDAO)synGenesIterator.next();
//                errors.pushNestedPath("synonyms[" + Integer.toString(i) + "]");
//                Utils.validateMaxFieldLengths(geneSynonym, "syn_genes", errors);
//                errors.popNestedPath();
//                i++;
//            }
//        }
//    }
//    
//    // PRIVATE METHODS
//    
//    
//    // GETTERS AND SETTERS
    

}

