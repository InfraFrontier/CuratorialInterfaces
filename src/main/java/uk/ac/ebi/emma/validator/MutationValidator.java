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
import uk.ac.ebi.emma.entity.Mutation;
import uk.ac.ebi.emma.manager.AllelesManager;
import uk.ac.ebi.emma.util.Utils;

/**
 *
 * @author mrelac
 */
@Component
public class MutationValidator implements Validator {
    AllelesManager allelesManager = new AllelesManager();
    
    /**
     * Required for Validator implementation.
     * @param clazz caller's class
     * @return true if caller's class is supported; false otherwise.
     */
    @Override
    public boolean supports(Class clazz) {
            return Mutation.class.isAssignableFrom(clazz);
    }

    /**
     * Required for Validator implementation.
     * @param target target object to be validated
     * @param errors errors object
     */
    @Override
    @SuppressWarnings("empty-statement")
    public void validate(Object target, Errors errors) {
        Mutation mutation = (Mutation)target;

        Utils.validateMaxFieldLengths(mutation, "mutations", errors);
        
        // Make sure mutation is bound to an existing allele.
        Integer pk = extractAndValidateAlleleKey(mutation.getAllele_key());
        if (pk == null) {
            errors.rejectValue("mutation.allele_key", null, "Please choose a valid allele.");
        }
    }
    
    
    // PRIVATE METHODS
    
    
    /**
     * Extract and validate allele key. If allele key doesn't identify a valid
     * allele, null is returned; otherwise the key (always > 0) is returned.
     * @param allele_key the allele primary key to query
     * @return the allele key, if found and valid; null otherwise
     */
    private Integer extractAndValidateAlleleKey(Integer allele_key) {
        if ((allele_key == null) || (allele_key <= 0))
            return null;
        Allele allele = allelesManager.getAllele(allele_key);
        if (allele == null)
            return null;
        
        return allele.getAllele_key();
    }
}
