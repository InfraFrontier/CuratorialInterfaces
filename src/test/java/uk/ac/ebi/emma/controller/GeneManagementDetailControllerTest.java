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

import java.util.HashMap;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import uk.ac.ebi.emma.entity.Gene;

/**
 *
 * @author mrelac
 */
public class GeneManagementDetailControllerTest {
    
    public GeneManagementDetailControllerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
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

//    /**
//     * Test of editGene method, of class GeneManagementDetailController.
//     */
//    @Test
//    public void testEditGene() {
//        System.out.println("editGene");
//        Integer id_gene = null;
//        String filterGeneId = "";
//        String filterGeneName = "";
//        String filterGeneSymbol = "";
//        String filterChromosome = "";
//        String filterMGIReference = "";
//        Model model = null;
//        GeneManagementDetailController instance = new GeneManagementDetailController();
//        String expResult = "";
//        String result = instance.editGene(id_gene, filterGeneId, filterGeneName, filterGeneSymbol, filterChromosome, filterMGIReference, model);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
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

    /**
     * Test of getFieldLengths method, of class GeneManagementDetailController.
     */
    @Test
    public void testGetFieldLengths() {
        System.out.println("getFieldLengths - genes");
        String tablename = "genes";
        GeneManagementDetailController instance = new GeneManagementDetailController();
        HashMap<String, Integer> result = instance.getFieldLengths(tablename);
        assertEquals(300, (int)result.get("name"));
        assertEquals(100, (int)result.get("symbol"));
        assertEquals(2, (int)result.get("chromosome"));
        assertEquals(15, (int)result.get("cytoband"));
        assertEquals(20, (int)result.get("species"));
        assertEquals(20, (int)result.get("mgi_ref"));
        assertEquals(20, (int)result.get("username"));
        assertEquals(150, (int)result.get("promoter"));
        assertEquals(150, (int)result.get("founder_line_number"));
        assertEquals(150, (int)result.get("plasmid_construct"));
        assertEquals(18, (int)result.get("ensembl_ref"));

        System.out.println("getFieldLengths - syn_genes");
        tablename = "syn_genes";
        result = instance.getFieldLengths(tablename);
        assertEquals(255, (int)result.get("name"));
        assertEquals(255, (int)result.get("symbol"));
        assertEquals(255, (int)result.get("username"));
    }
    
}
