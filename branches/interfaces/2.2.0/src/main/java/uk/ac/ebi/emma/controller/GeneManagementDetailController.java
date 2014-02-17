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

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.emma.entity.Gene;
import uk.ac.ebi.emma.entity.GeneSynonym;
import uk.ac.ebi.emma.manager.GenesManager;
import uk.ac.ebi.emma.util.DBUtils;
import uk.ac.ebi.emma.util.Filter;
import uk.ac.ebi.emma.validator.GeneValidator;

/**
 *
 * @author mrelac
 */
@Controller
@RequestMapping("/geneManagementDetail")
public class GeneManagementDetailController {
    @Autowired
    private GenesManager genesManager = new GenesManager();
    
    @Autowired
    private GeneValidator validator;

    /**
     * 'Edit/New Gene' icon implementation
     * 
     * @param id_gene the gene ID being edited (a value of 0 indicates a new
     *                gene is to be added).
     * @param filterGeneId the Gene Id part of the filter
     * @param filterGeneName the Gene name part of the filter
     * @param filterGeneSymbol the Gene symbol part of the filter
     * @param filterChromosome the chromosome part of the filter
     * @param filterMGIReference the MGI reference part of the filter
     * @param model the model
     * @return the view to show
     */
    @RequestMapping(value="/editGene", method=RequestMethod.GET)
    public String editGene(
            @RequestParam(value="id_gene", required=false) Integer id_gene
            
          , @RequestParam(value="filterGeneId", required=false) String filterGeneId
          , @RequestParam(value="filterGeneName", required=false) String filterGeneName
          , @RequestParam(value="filterGeneSymbol", required=false) String filterGeneSymbol
          , @RequestParam(value="filterChromosome", required=false) String filterChromosome
          , @RequestParam(value="filterMGIReference", required=false) String filterMGIReference
            
          , Model model)
    {
        // Save the filter info and add to model.
        Filter filter = buildFilter(filterGeneId, filterGeneName, filterGeneSymbol, filterChromosome, filterMGIReference);
        model.addAttribute(filter);
        
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("loggedInUser", loggedInUser);
        
        Gene gene = genesManager.getGene(id_gene);
        if (gene == null)
            gene = new Gene();
        model.addAttribute("gene", gene);
    
        return "geneManagementDetail";
    }
    
    /**
     * Save the form data.
     * 
     * @param gene the Gene instance
     * @param bindingResult the Errors binding result object
     * @param id_gene the actual gene primary key (for some reason gene.id_gene is 0, even for a valid gene instance).
     * @param synonymsAreDirty true for each dirty synonym; false for each clean (unmodified) one
     * @param hidSeedValues
     * @param synonymIds
     * @param synonymNames
     * @param synonymSymbols
     * @param filterGeneId
     * @param filterGeneName
     * @param filterGeneSymbol
     * @param filterChromosome
     * @param filterMGIReference
     * @param model the filter data, saved above in editGene().
     * @return redirected view to same gene detail data.
     */
    @RequestMapping(value="/save", method=RequestMethod.POST)
    public String save(
            @Valid Gene gene, BindingResult bindingResult
          
          , @RequestParam(value="id_gene") int id_gene
            
          , @RequestParam(value = "hidSeedValues", required=false) String[] hidSeedValues
          , @RequestParam(value = "synonymsAreDirty", required=false) String[] synonymsAreDirty
          , @RequestParam(value = "synonymIds", required=false) String[] synonymIds
          , @RequestParam(value = "synonymNames", required=false) String[] synonymNames
          , @RequestParam(value = "synonymSymbols", required=false) String[] synonymSymbols
            
          , @RequestParam(value="filterGeneId", required=false) String filterGeneId
          , @RequestParam(value="filterGeneName", required=false) String filterGeneName
          , @RequestParam(value="filterGeneSymbol", required=false) String filterGeneSymbol
          , @RequestParam(value="filterChromosome", required=false) String filterChromosome
          , @RequestParam(value="filterMGIReference", required=false) String filterMGIReference
            
          , Model model) 
    {
        // Since gene.id_gene is not bound (because we don't want a '0' to show in the Gene Id field),
        // we require the id_gene to be passed in. Plug it into the gene object.
        gene.setId_gene(id_gene);
        
        // re-attach any synonyms. The Gene object passed in does not contain the synonym objects.
        if (gene.getId_gene() > 0) {
            Gene dbGene = genesManager.getGene(gene.getId_gene());
            gene.setSynonyms(dbGene.getSynonyms());
        }
        
        // Spring does not reliably pass all tabular data (read: empty fields),
        // nor does it guarantee that all synonym array lengths (id, name, symbol)
        // will be of the same length. Sometimes spring passes all elements, empty
        // or not. Other times it passes only non-empty data elements. To handle
        // this, we use a hidden seed value array (hidSeedValues) that always has a value.
        // This guarantees the number of synonym rows the user wanted. Since the
        // synonym jsp controls are in a 'for' loop, the infrastructure serves
        // them up here as an array. When there are no synonyms, all synonym 
        // arrays are null. Where there is exactly 1 synonym, the array lengths
        // of those synonym arrays that contain data will be 1; the others will be
        // 0. When there is more than 1 synonym, the array lengths always appear
        // to be correct, regardless if the array elements are empty or not. Go figure.
        // That is why the array data extraction below is performed in a catch
        // block, ignoring any ArrayIndexOutOfBoundsException exceptions.
        if ((hidSeedValues == null) || (hidSeedValues.length == 0)) {
            gene.setSynonyms(null);
        } else {
            Set<GeneSynonym> geneSynonymSet = new LinkedHashSet<>();
            for (int i = 0; i < hidSeedValues.length; i++) {
                GeneSynonym geneSynonym = new GeneSynonym();
                Integer id_syn = 0;
                try {
                    id_syn = Integer.parseInt(synonymIds[i]);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) { }
                geneSynonym.setId_syn(id_syn);
                
                String name = null;
                try {
                    name = synonymNames[i];
                } catch (ArrayIndexOutOfBoundsException e) { }
                geneSynonym.setName(name);
                
                String symbol = null;
                try {
                    symbol = synonymSymbols[i];
                } catch (ArrayIndexOutOfBoundsException e) { }
                geneSynonym.setSymbol(symbol);
                
                boolean isDirty = false;
                try {
                    isDirty = synonymsAreDirty[i].compareToIgnoreCase("true") == 0;
                } catch (ArrayIndexOutOfBoundsException e) { }
                
                geneSynonym.setIsDirty(isDirty);
                geneSynonymSet.add(geneSynonym);
            }
            
            gene.setSynonyms(geneSynonymSet);
        }
        
        validator.validate(gene, bindingResult);
        if (bindingResult.hasErrors()) {
            // Add the filter and the logged in user to the model.
            Filter filter = buildFilter(filterGeneId, filterGeneName, filterGeneSymbol, filterChromosome, filterMGIReference);
            model.addAttribute(filter);
            
            String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
            model.addAttribute("loggedInUser", loggedInUser);
            
            return "geneManagementDetail";
        }
        
        genesManager.save(gene);
        
        return "redirect:/curation/geneManagementList/go"
                + "?geneId=" + filterGeneId
                + "&geneName=" + filterGeneName
                + "&geneSymbol=" + filterGeneSymbol
                + "&chromosome=" + filterChromosome
                + "&mgiReference=" + filterMGIReference;
    }
    
        /**
     * Show the list form with saved filter values.
     * 
     * @param filterGeneId
     * @param filterGeneName
     * @param filterGeneSymbol
     * @param filterChromosome
     * @param filterMGIReference
     * @param model the filter data, saved above in editGene().
     * @return redirected view to same gene detail data.
     */
    @RequestMapping(value="/showList", method=RequestMethod.GET)
    public String showList(
            @RequestParam(value="filterGeneId", required=false) String filterGeneId
          , @RequestParam(value="filterGeneName", required=false) String filterGeneName
          , @RequestParam(value="filterGeneSymbol", required=false) String filterGeneSymbol
          , @RequestParam(value="filterChromosome", required=false) String filterChromosome
          , @RequestParam(value="filterMGIReference", required=false) String filterMGIReference
            
          , Model model) 
    {
        return "redirect:/curation/geneManagementList/go"
                + "?geneId=" + filterGeneId
                + "&geneName=" + filterGeneName
                + "&geneSymbol=" + filterGeneSymbol
                + "&chromosome=" + filterChromosome
                + "&mgiReference=" + filterMGIReference;
    }
    
    /**
     * Return hashmap of maximum database column lengths of <code>String</code>
     * data. Used to dynamically set maxlength of client input HTML controls.
     * @param tablename Database table name of maximum string lengths to return
     * @return a hashmap of maximum database column lengths of <code>String</code>
     * data.
     */
    @RequestMapping(value="getFieldLengths")
    @ResponseBody
    public HashMap<String, Integer> getFieldLengths(String tablename) {
        return DBUtils.getMaxColumnLengths(tablename);
    }
    
    
    // PRIVATE METHODS
    
    
    private Filter buildFilter(String geneId, String geneName, String geneSymbol, String chromosome, String mgiReference) {
        Filter filter = new Filter();
        filter.setGeneId(geneId != null ? geneId : "");
        filter.setGeneName(geneName != null ? geneName : "");
        filter.setGeneSymbol(geneSymbol != null ? geneSymbol : "");
        filter.setChromosome(chromosome != null ? chromosome : "");
        filter.setMgiReference(mgiReference != null ? mgiReference : "");
        
        return filter;
    }
    
    
    // GETTERS AND SETTERS
    

}

