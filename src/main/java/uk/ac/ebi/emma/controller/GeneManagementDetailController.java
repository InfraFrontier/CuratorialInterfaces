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

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.emma.Exception.PersistFailedException;
import uk.ac.ebi.emma.entity.Gene;
import uk.ac.ebi.emma.entity.GeneSynonym;
import uk.ac.ebi.emma.manager.GenesManager;
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
    private final GenesManager genesManager = new GenesManager();
    
    @Autowired
    private GeneValidator validator;

    /**
     * 'Edit/New Gene' icon implementation
     * 
     * @param gene_key the gene ID being edited (a value of 0 indicates a new
     *                gene is to be added).
     * @param filterGeneKey the Gene Id part of the filter
     * @param filterGeneName the Gene name part of the filter
     * @param filterGeneSymbol the Gene symbol part of the filter
     * @param filterChromosome the chromosome part of the filter
     * @param filterGeneMgiReference the MGI reference part of the filter
     * @param model the model
     * @return the view to show
     */
    @RequestMapping(value="/edit", method=RequestMethod.GET)
    public String edit(
            @RequestParam(value="gene_key") Integer gene_key
            
          , @RequestParam(value="filterGeneKey") String filterGeneKey
          , @RequestParam(value="filterGeneName") String filterGeneName
          , @RequestParam(value="filterGeneSymbol") String filterGeneSymbol
          , @RequestParam(value="filterChromosome") String filterChromosome
          , @RequestParam(value="filterGeneMgiReference") String filterGeneMgiReference
            
          , Model model)
    {
        // Save the filter info and add to model.
        Filter filter = buildFilter(filterGeneKey, filterGeneName, filterGeneSymbol, filterChromosome, filterGeneMgiReference);
        model.addAttribute(filter);
        
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("loggedInUser", loggedInUser);
        
        Gene gene = genesManager.getGene(gene_key);
        if (gene == null)
            gene = new Gene();
        model.addAttribute("gene", gene);
    
        return "geneManagementDetail";
    }
    
    /**
     * Save the form data.
     * 
     * @param gene the Gene instance
     * @param errors the Errors binding result object
     * @param synonymsAreDirty true for each dirty synonym; false for each clean (unmodified) one
     * @param hidSeedValues
     * @param synonymIds
     * @param synonymNames
     * @param synonymSymbols
     * @param filterGeneKey
     * @param filterGeneName
     * @param filterGeneSymbol
     * @param filterChromosome
     * @param filterGeneMgiReference
     * @param model the filter data, saved above in edit().
     * @return redirected view to same gene detail data.
     */
    @RequestMapping(value="/save", method=RequestMethod.POST)
    @Qualifier("uk.ac.ebi.emma.controller.GeneManagementDetailController")
    public String save(
            @Valid Gene gene, Errors errors
          , @RequestParam(value = "hidSeedValues", required=false) String[] hidSeedValues
          , @RequestParam(value = "synonymsAreDirty", required=false) String[] synonymsAreDirty
          , @RequestParam(value = "synonymIds", required=false) String[] synonymIds
          , @RequestParam(value = "synonymNames", required=false) String[] synonymNames
          , @RequestParam(value = "synonymSymbols", required=false) String[] synonymSymbols
            
          , @RequestParam(value="filterGeneKey") String filterGeneKey
          , @RequestParam(value="filterGeneName") String filterGeneName
          , @RequestParam(value="filterGeneSymbol") String filterGeneSymbol
          , @RequestParam(value="filterChromosome") String filterChromosome
          , @RequestParam(value="filterGeneMgiReference") String filterGeneMgiReference
            
          , Model model) 
    {
        // re-attach any synonyms and any alleles. The Gene object passed in does not contain them.
        if ((gene.getGene_key() != null) && (gene.getGene_key() > 0)) {
            Gene dbGene = genesManager.getGene(gene.getGene_key());
            gene.setSynonyms(dbGene.getSynonyms());
            gene.setAlleles(dbGene.getAlleles());
        }
        
        // Load up the model in case we have to redisplay the detail form.
        Filter filter = buildFilter(filterGeneKey, filterGeneName, filterGeneSymbol, filterChromosome, filterGeneMgiReference);
        model.addAttribute(filter);
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("loggedInUser", loggedInUser);
        
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
                Integer geneSynonym_key = null;
                try {
                    geneSynonym_key = Integer.parseInt(synonymIds[i]);
                } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) { }
                geneSynonym.setGeneSynonym_key(geneSynonym_key);
                
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
        
        validator.validate(gene, errors);
        if (errors.hasErrors()) {
            return "geneManagementDetail";
        }
        
        try {
            genesManager.save(gene);
        } catch (PersistFailedException pfe) {
            errors.reject(null, pfe.getLocalizedMessage());
            return "geneManagementDetail";
        }
            
        return "redirect:/curation/geneManagementDetail/edit"
                + "?gene_key=" + gene.getGene_key()
                + "&filterGeneKey=" + filterGeneKey
                + "&filterGeneName=" + filterGeneName
                + "&filterGeneSymbol=" + filterGeneSymbol
                + "&filterChromosome=" + filterChromosome
                + "&filterGeneMgiReference=" + filterGeneMgiReference;
    }
    
    
    // PRIVATE METHODS
    
    
    private Filter buildFilter(String gene_key, String geneName, String geneSymbol, String chromosome, String mgiReference) {
        Filter filter = new Filter();
        filter.setGene_key(gene_key != null ? gene_key : "");
        filter.setGeneName(geneName != null ? geneName : "");
        filter.setGeneSymbol(geneSymbol != null ? geneSymbol : "");
        filter.setChromosome(chromosome != null ? chromosome : "");
        filter.setGeneMgiReference(mgiReference != null ? mgiReference : "");
        
        return filter;
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
     * Returns a [distinct], unfiltered list of all centimorgans suitable for autocomplete
     * sourcing.
     * 
     * @return a [distinct], unfiltered list of all centimorgans suitable for autocomplete
     * sourcing.
     * */
    @RequestMapping(value = "/getCentimorgans"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getCentimorgans() {
        return genesManager.getCentimorgans();
    }
    
    /**
     * Returns a [distinct], unfiltered list of all cytobands suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the cytoband (used in sql LIKE clause)
     * @return a [distinct], unfiltered list of all cytobands suitable for autocomplete
     * sourcing.
     * */
    @RequestMapping(value = "/getCytobands"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getCytobands(@RequestParam String filterTerm) {
        return genesManager.getCytobands(filterTerm.trim());
    }
    
    /**
     * Returns a [distinct], unfiltered list of all plasmid constructs suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the plasmid construct (used in sql LIKE clause)
     * @return a [distinct], unfiltered list of all plasmid constructs suitable for autocomplete
     * sourcing.
     * */
    @RequestMapping(value = "/getPlasmidConstructs"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getPlasmidConstructs(@RequestParam String filterTerm) {
        return genesManager.getPlasmidConstructs(filterTerm.trim());
    }
    
    /**
     * Returns a [distinct], unfiltered list of all promoters suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the promoter (used in sql LIKE clause)
     * @return a [distinct], unfiltered list of all promoters suitable for autocomplete
     * sourcing.
     * */
    @RequestMapping(value = "/getPromoters"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getPromoters(@RequestParam String filterTerm) {
        return genesManager.getPromoters(filterTerm.trim());
    }
    
    /**
     * Returns a [distinct], unfiltered list of all founder line numbers suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the founder line number (used in sql LIKE clause)
     * @return a [distinct], unfiltered list of all founder line numbers suitable for autocomplete
     * sourcing.
     * */
    @RequestMapping(value = "/getFounderLineNumbers"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getFounderLineNumbers(@RequestParam String filterTerm) {
        return genesManager.getFounderLineNumbers(filterTerm.trim());
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
    
    /**
     * Returns a distinct filtered list of gene Ensembl references suitable 
     * for autocomplete sourcing.
     * 
     * @param filterTerm the filter term for the ensembl reference (used in sql LIKE clause)
     * @@return a <code>List&lt;String&gt;</code> of distinct ensembl references filtered
     * by <code>filterTerm</code> suitable for autocomplete sourcing.
     * */
    @RequestMapping(value = "/getEnsemblReferences"
                  , method = RequestMethod.GET
    )
    @ResponseBody
    public List<String> getEnsemblReferences(@RequestParam String filterTerm) {
        return genesManager.getEnsemblReferences(filterTerm.trim());
    }
    
    /**
     * Returns a [distinct], unfiltered list of all species suitable for autocomplete
     * sourcing.
     * 
     * @param filterTerm the filter term for the species (used in sql LIKE clause)
     * @return a [distinct], unfiltered list of all species suitable for autocomplete
     * sourcing.
     * */
    @RequestMapping(value = "/getSpecies"
                  , method = RequestMethod.GET)
    @ResponseBody
    public List<String> getSpecies(@RequestParam String filterTerm) {
        return genesManager.getSpecies(filterTerm.trim());
    }
}

