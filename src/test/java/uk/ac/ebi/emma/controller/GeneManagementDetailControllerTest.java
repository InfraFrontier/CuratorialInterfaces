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

package uk.ac.ebi.emma.controller;

import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.support.BindingAwareModelMap;
import uk.ac.ebi.emma.entity.Gene;
import uk.ac.ebi.emma.util.Filter;

/**
 *
 * @author mrelac
 */
public class GeneManagementDetailControllerTest {
    
    public GeneManagementDetailControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
        SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken("test", ""));
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of edit method, of class GeneManagementDetailController.
     */
    @Test
    public void testEditGene() {
        System.out.println("edit");
        int id_gene = 0;
        String filterGeneId = "0";
        String filterGeneName = "test gene name";
        String filterGeneSymbol = "test gene symbol";
        String filterChromosome = "test chromosome";
        String filterMGIReference = "test mgi reference";
        Model model = new BindingAwareModelMap();
        
        GeneManagementDetailController instance = new GeneManagementDetailController();
        String expResult = "geneManagementDetail";
        String result = instance.edit(id_gene, filterGeneId, filterGeneName, filterGeneSymbol, filterChromosome, filterMGIReference, model);
        assertEquals(expResult, result);                                        // Verify function return.
        
        Map modelMap = model.asMap();
        // Check filter.
        Filter filter = (Filter)modelMap.get("filter");
        assertEquals(filterGeneId, filter.getGeneId());
        assertEquals(filterGeneName, filter.getGeneName());
        assertEquals(filterGeneSymbol, filter.getGeneSymbol());
        assertEquals(filterChromosome, filter.getChromosome());
        assertEquals(filterMGIReference, filter.getGeneMgiReference());
        
        Gene gene = (Gene)modelMap.get("gene");
        // The gene's components are null, so there is no more checking that can be done.
    }

    /**
     * Test of showList method, of class GeneManagementDetailController.
     */
    @Test
    public void testShowList() {
        System.out.println("showList");
        String filterGeneId = "0";
        String filterGeneName = "test gene name";
        String filterGeneSymbol = "test gene symbol";
        String filterChromosome = "test chromosome";
        String filterMGIReference = "test mgi reference";
        Model model = new BindingAwareModelMap();
        
        GeneManagementDetailController instance = new GeneManagementDetailController();
        String expResult = "redirect:/curation/geneManagementList/go?geneId=0&geneName=test gene name&geneSymbol=test gene symbol&chromosome=test chromosome&geneMgiReference=test mgi reference";
        String result = instance.showList(filterGeneId, filterGeneName, filterGeneSymbol, filterChromosome, filterMGIReference, model);
        assertEquals(expResult, result);                                        // Verify function return.
    }

//    /**
//     * Test of save method, of class GeneManagementDetailController.
//     */
//    @Test
//    public void testSave() {
//        System.out.println("save");
//        Gene gene = null;
//        BindingResult bindingResult = null;
//        int id_gene = 0;
//        String[] hidSeedValues = null;
//        String[] synonymsAreDirty = null;
//        String[] synonymIds = null;
//        String[] synonymNames = null;
//        String[] synonymSymbols = null;
//        String filterGeneId = "";
//        String filterGeneName = "";
//        String filterGeneSymbol = "";
//        String filterChromosome = "";
//        String filterMGIReference = "";
//        Model model = null;
//        GeneManagementDetailController instance = new GeneManagementDetailController();
//        String expResult = "";
//        String result = instance.save(gene, bindingResult, id_gene, hidSeedValues, synonymsAreDirty, synonymIds, synonymNames, synonymSymbols, filterGeneId, filterGeneName, filterGeneSymbol, filterChromosome, filterMGIReference, model);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
