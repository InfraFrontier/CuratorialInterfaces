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
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.emma.entity.Gene;
import uk.ac.ebi.emma.entity.GeneSynonym;
import uk.ac.ebi.emma.manager.GenesManager;
import uk.ac.ebi.emma.util.DBUtils;
import uk.ac.ebi.emma.util.Filter;
import uk.ac.ebi.emma.util.Utils;

/**
 *
 * @author mrelac
 */
@Controller
@RequestMapping("/geneManagementDetail")
public class GeneManagementDetailController implements Validator {
    @Autowired
    private GenesManager genesManager;

    
    /**
     * 'Edit Gene' icon implementation
     * 
     * @param id_gene the gene ID being edited (a value of 0 indicates a new
     *                gene is to be added).
     * @param geneId the Gene Id part of the filter
     * @param geneName the Gene name part of the filter
     * @param geneSymbol the Gene symbol part of the filter
     * @param chromosome the chromosome part of the filter
     * @param mgiReference the MGI reference part of the filter
     * @param model the model
     * @return the view to show
     */
    @RequestMapping(value="/editGene", method=RequestMethod.GET)
    public String editGene(
            @RequestParam(value="id_gene", required=false) Integer id_gene
            
          , @RequestParam(value="geneId", required=false) String geneId
          , @RequestParam(value="geneName", required=false) String geneName
          , @RequestParam(value="geneSymbol", required=false) String geneSymbol
          , @RequestParam(value="chromosome", required=false) String chromosome
          , @RequestParam(value="mgiReference", required=false) String mgiReference
            
          , Model model)
    {
        // Save the filter info, to be resored when save() is called.
        Filter filter = new Filter();
        filter.setGeneId(geneId != null ? geneId : "");
        filter.setGeneName(geneName != null ? geneName : "");
        filter.setGeneSymbol(geneSymbol != null ? geneSymbol : "");
        filter.setChromosome(chromosome != null ? chromosome : "");
        filter.setMgiReference(mgiReference != null ? mgiReference : "");
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
     * 'New Gene' icon implementation
     * 
     * @param geneId the Gene Id part of the filter
     * @param geneName the Gene name part of the filter
     * @param geneSymbol the Gene symbol part of the filter
     * @param chromosome the chromosome part of the filter
     * @param mgiReference the MGI reference part of the filter
     * @param model the model
     * @return the view to show
     */
    @RequestMapping(value="/newGene", method=RequestMethod.GET)
    public String newGene(
            @RequestParam(value="geneId", required=false) String geneId
          , @RequestParam(value="geneName", required=false) String geneName
          , @RequestParam(value="geneSymbol", required=false) String geneSymbol
          , @RequestParam(value="chromosome", required=false) String chromosome
          , @RequestParam(value="mgiReference", required=false) String mgiReference
            
          , Model model)
    {
        // Save the filter info, to be resored when save() is called.
        Filter filter = new Filter();
        filter.setGeneId(geneId != null ? geneId : "");
        filter.setGeneName(geneName != null ? geneName : "");
        filter.setGeneSymbol(geneSymbol != null ? geneSymbol : "");
        filter.setChromosome(chromosome != null ? chromosome : "");
        filter.setMgiReference(mgiReference != null ? mgiReference : "");
        model.addAttribute(filter);
        
        String loggedInUser = SecurityContextHolder.getContext().getAuthentication().getName();
        model.addAttribute("loggedInUser", loggedInUser);
        
        Gene gene = new Gene();
        model.addAttribute("gene", gene);
    
        return "geneManagementDetail";
    }
    
    /**
     * Save the form data.
     * 
     * @param geneId
     * @param mgiReference
     * @param geneName
     * @param ensemblReference
     * @param geneSymbol
     * @param promoter
     * @param chromosome
     * @param founderLineNumber
     * @param species
     * @param plasmidConstruct
     * @param centimorgan
     * @param cytoband
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
    @RequestMapping(value="save", method=RequestMethod.POST)
    public String save(
            @RequestParam(value = "geneId") String geneId
          , @RequestParam(value = "mgiReference") String mgiReference
          , @RequestParam(value = "geneName") String geneName
          , @RequestParam(value = "ensemblReference") String ensemblReference
          , @RequestParam(value = "geneSymbol") String geneSymbol
          , @RequestParam(value = "promoter") String promoter
          , @RequestParam(value = "chromosome") String chromosome
          , @RequestParam(value = "founderLineNumber") String founderLineNumber
          , @RequestParam(value = "species") String species
          , @RequestParam(value = "plasmidConstruct") String plasmidConstruct
          , @RequestParam(value = "centimorgan") String centimorgan
          , @RequestParam(value = "cytoband") String cytoband
            
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
        Gene gene = null;
        Integer id_gene = Utils.tryParseInt(geneId);
        if (id_gene != null)
            gene = genesManager.getGene(id_gene);
        if (gene == null)
            gene = new Gene();
        gene.setMgi_ref(mgiReference);
        gene.setName(geneName);
        gene.setEnsembl_ref(ensemblReference);
        gene.setSymbol(geneSymbol);
        gene.setPromoter(promoter);
        gene.setChromosome(chromosome);
        gene.setFounder_line_number(founderLineNumber);
        gene.setSpecies(species);
        gene.setPlasmid_construct(plasmidConstruct);
        gene.setCentimorgan(centimorgan);
        gene.setCytoband(cytoband);
        
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
        
        genesManager.save(gene);
        
        return "redirect:/interfaces/geneManagementList/go"
                + "?geneId=" + filterGeneId
                + "&geneName=" + filterGeneName
                + "&geneSymbol=" + filterGeneSymbol
                + "&chromosome=" + filterChromosome
                + "&mgiReference=" + filterMGIReference;
    }

    /**
     * Required for Validator implementation.
     * @param clazz caller's class
     * @return true if caller's class is supported; false otherwise.
     */
    @Override
    public boolean supports(Class clazz) {
            return GeneSynonym.class.isAssignableFrom(clazz);
    }

    /**
     * Required for Validator implementation.
     * @param target target object to be validated
     * @param errors errors object
     */
    @Override
    public void validate(Object target, Errors errors) {
        Gene gene = (Gene)target;
        
        // Centimorgan, if supplied, must be an integer.
        if ((gene.getCentimorgan() != null) && ( ! gene.getCentimorgan().isEmpty())) {
            Integer centimorgan = Utils.tryParseInt(gene.getCentimorgan());
            if (centimorgan == null) {
                errors.rejectValue("centimorgan", null, "Please enter an integer.");
            }
        }
        if ((gene.getName() != null) && (gene.getName().trim().length() == 0)) {
            errors.rejectValue("name", null, "Please provide a name for the gene.");
        }
        
        Utils.validateMaxFieldLengths(gene, "genes", errors);                   // Validate 'gene' max String lengths
        
        // Validate that Syn_GenesDAO String data doesn't exceed maximum varchar lengths.
        if (gene.getSynonyms() != null) {                                       // Validate each 'synGenes' instance's max String lengths
            Set<GeneSynonym> geneSynonyms = gene.getSynonyms();
            Iterator<GeneSynonym> synGenesIterator = geneSynonyms.iterator();
            int i = 0;
            while (synGenesIterator.hasNext()) {
                GeneSynonym geneSynonym = (GeneSynonym)synGenesIterator.next();
                errors.pushNestedPath("synonyms[" + Integer.toString(i) + "]");
                Utils.validateMaxFieldLengths(geneSynonym, "syn_genes", errors);
                errors.popNestedPath();
                i++;
            }
        }
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
    
    
    // GETTERS AND SETTERS
    

}

