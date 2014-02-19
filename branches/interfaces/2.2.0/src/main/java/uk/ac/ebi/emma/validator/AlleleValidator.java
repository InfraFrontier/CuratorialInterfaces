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
import uk.ac.ebi.emma.util.Utils;

/**
 *
 * @author mrelac
 */
@Component
public class AlleleValidator implements Validator {

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
    public void validate(Object target, Errors errors) {
        Allele allele = (Allele)target;
        
        if ((allele.getName() != null) && (allele.getName().trim().length() == 0)) {
            errors.rejectValue("name", null, "Please provide a name.");
        }
        
        Utils.validateMaxFieldLengths(allele, "alleles", errors);
        
        // Make sure allele is bound to an existing gene.
        if (allele.getGen_id_gene() <= 0) {
            errors.rejectValue("gen_id_gene", null, "Please select a gene.");
        }
    }
}
