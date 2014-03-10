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

package uk.ac.ebi.emma.controller;

import java.util.HashMap;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.emma.entity.Allele;
import uk.ac.ebi.emma.entity.Background;
import uk.ac.ebi.emma.entity.Gene;
import uk.ac.ebi.emma.entity.Strain;
import uk.ac.ebi.emma.manager.AllelesManager;
import uk.ac.ebi.emma.manager.BackgroundsManager;
import uk.ac.ebi.emma.manager.GenesManager;
import uk.ac.ebi.emma.manager.StrainsManager;
import uk.ac.ebi.emma.util.DBUtils;

/**
 * This class is intended to be a common backing resource for restful requests
 * for data not specific to a particular controller or form.
 * 
 * @author mrelac
 */
@Controller
@RequestMapping("/util")
public class UtilController {
    protected final Logger logger = Logger.getLogger(this.getClass());
    
    @Autowired
    private final AllelesManager allelesManager = new AllelesManager();
    @Autowired
    private final BackgroundsManager backgroundsManager = new BackgroundsManager();
    @Autowired
    private final GenesManager genesManager = new GenesManager();
    @Autowired
    private final StrainsManager strainsManager = new StrainsManager();
    
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
    
    /**
     * Returns a gene instance matching <b>gene_key</b> if found; null otherwise.
     * 
     * @param gene_key the primary key of the desired gene instance
     * @@return a gene instance matching <b>gene_key</b> if found; null otherwise.
     * */
    @RequestMapping(value = "/getGene"
                  , method = RequestMethod.GET)
    @ResponseBody
    public Gene getGene(@RequestParam int gene_key) {
        Gene gene = genesManager.getGene(gene_key);
        if (gene != null) {
            // Null out collection, as jackson throws infinite recursion exception trying to map parent <--> child references.
            gene.setAlleles(null);
        }
        
        return gene;
    }    
    /**
     * Returns an allele instance matching <b>allele_key</b> if found; null otherwise.
     * 
     * @param allele_key the primary key of the desired allele instance
     * @@return an allele instance matching <b>allele_key</b> if found; null otherwise.
     * */
    @RequestMapping(value = "/getAllele"
                  , method = RequestMethod.GET)
    @ResponseBody
    public Allele getAllele(@RequestParam int allele_key) {
        Allele allele = allelesManager.getAlleleName(allele_key);
        if (allele != null) {
            logger.debug("Returning allele id (name) " + allele.getAllele_key() + " (" + allele.getName() + ")");
            // Null out all collections and class instances, as jackson throws infinite recursion exception trying to map parent <--> child references.
            allele.setGene(null);
            allele.setMutations(null);
            
            allele.setGene(null);
        }
        else {
            logger.debug("No allele found matching allele_key " + allele_key);
        }
        
        return allele;
    }
    
    /**
     * Returns a background instance matching <b>background_key</b> if found; null otherwise.
     * 
     * @param background_key the primary key of the desired background instance
     * @@return a background instance matching <b>background_key</b> if found; null otherwise.
     * */
    @RequestMapping(value = "/getBackground"
                  , method = RequestMethod.GET)
    @ResponseBody
    public Background getBackground(@RequestParam int background_key) {
        Background background = backgroundsManager.getBackgroundName(background_key);
        if (background != null) {
            logger.debug("Returning background id (name) " + background.getBackground_key() + " (" + background.getName() + ")");
        } else {
            logger.debug("No background found matching background_key " + background_key);
        }
         
        return background;
    }
    
    /**
     * Returns a strain instance matching <b>strain_key</b> if found; null otherwise.
     * Collections are set to null to avoid infinite recursion by jackson JSON.
     * 
     * @param strain_key the primary key of the desired strain instance
     * @@return a strain instance matching <b>strain_key</b> if found; null otherwise.
     * */
    @RequestMapping(value = "/getStrain"
                  , method = RequestMethod.GET)
    @ResponseBody
    public Strain getStrain(@RequestParam int strain_key) {
        Strain strain = strainsManager.getStrainName(strain_key);
        if (strain != null) {
            logger.debug("Returning strain id (name) " + strain.getBackground_key() + " (" + strain.getName() + ")");
            // Null out all collections and class instances, as jackson throws infinite recursion exception trying to map parent <--> child references.
            strain.setAvailabilitiesStrains(null);
            strain.setBibliosStrains(null);
            strain.setCategoriesStrains(null);
            strain.setMutationsStrains(null);
            strain.setProjectsStrains(null);
            strain.setRtoolsStrains(null);
            strain.setSourcesStrains(null);
            strain.setSynonymsStrains(null);
            strain.setWebRequests(null);
            
            strain.setArchive(null);
            strain.setBackground(null);
            strain.setCreator(null);
            strain.setResidue(null);
            strain.setShippingContact(null);
            strain.setSubmitter(null);
        }
        else {
            logger.debug("No strain found matching strain_key " + strain_key);
        }
        
        return strain;
    }
    
}
