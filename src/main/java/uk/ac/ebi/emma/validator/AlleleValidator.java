/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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

package uk.ac.ebi.emma.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import uk.ac.ebi.emma.entity.Allele;
import uk.ac.ebi.emma.entity.Gene;
import uk.ac.ebi.emma.manager.GenesManager;
import uk.ac.ebi.emma.util.Utils;

/**
 *
 * @author mrelac
 */
@Component
public class AlleleValidator implements Validator {
    GenesManager genesManager = new GenesManager();
    
    /**
     * Required for Validator implementation.
     * @param clazz caller's class
     * @return true if caller's class is supported; false otherwise.
     */
    @Override
    public boolean supports(Class clazz) {
            return Gene.class.isAssignableFrom(clazz);
    }

    /**
     * Required for Validator implementation.
     * @param target target object to be validated
     * @param errors errors object
     */
    @Override
    @SuppressWarnings("empty-statement")
    public void validate(Object target, Errors errors) {
        Allele allele = (Allele)target;
        
        if ((allele.getName() != null) && (allele.getName().trim().length() == 0)) {
            errors.rejectValue("name", null, "Please provide a name.");
        }
        
        Utils.validateMaxFieldLengths(allele, "alleles", errors);
        
        // Make sure allele is bound to an existing gene.
        Integer pk = extractAndValidateGeneKey(allele);
        if (pk == null) {
            errors.rejectValue("gene.gene_key", null, "Please choose a valid gene.");
        }
    }
    
    // PRIVATE METHODS
    
    
    /**
     * Extract and validate gene key from allele. If gene key doesn't identify
     * a valid gene, null is returned; otherwise the key (always > 0) is returned.
     * @param allele the allele to query
     * @return the gene's primary key, if found and valid; null otherwise
     */
    private Integer extractAndValidateGeneKey(Allele allele) {
        if (allele.getGene() == null)
            return null;
        Integer gene_key = allele.getGene().getGene_key();
        if ((gene_key == null) || (gene_key <= 0))
            return null;
        Gene gene = genesManager.getGene(gene_key);
        if (gene == null)
            return null;
        
        return gene.getGene_key();
    }
}
