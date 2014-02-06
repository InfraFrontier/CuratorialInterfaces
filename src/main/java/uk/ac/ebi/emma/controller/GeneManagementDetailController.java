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

import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import uk.ac.ebi.emma.entity.Gene;
import uk.ac.ebi.emma.entity.GeneSynonym;
import uk.ac.ebi.emma.manager.GenesManager;
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
     * @param model the model
     * @return the view to show
     */
    @RequestMapping(value="/editGene", method=RequestMethod.GET)
    public String editGene(
            @RequestParam(value="id_gene", required=false) int id_gene
          , Model model)
    {
        Gene gene = genesManager.getGene(id_gene);
        if (gene == null)
            gene = new Gene();
        model.addAttribute("gene", gene);
    
        return "geneManagementDetail";
    }    

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
          , @RequestParam(value = "synonymIds", required=false) String[] synonymIds
          , @RequestParam(value = "synonymNames", required=false) String[] synonymNames
          , @RequestParam(value = "synonymSymbols", required=false) String[] synonymSymbols
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
                geneSynonymSet.add(geneSynonym);
            }
            gene.setSynonyms(geneSynonymSet);
        }
        
        genesManager.save(gene);
        
        return "redirect:/interfaces/geneManagementDetail/editGene?id_gene=" + gene.getId_gene();
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
//    
//    // PRIVATE METHODS
//    
//    
//    // GETTERS AND SETTERS
    

}

