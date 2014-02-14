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

import java.util.ArrayList;
import java.util.List;
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
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations="/applicationContext.xml")
public class GeneManagementListControllerTest {
    
    public GeneManagementListControllerTest() {
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
     * Test of go method, of class GeneManagementListController.
     */
    @Test
    public void testGo() {
        System.out.println("go");
        String geneId = "123";
        String geneName = "test gene name";
        String geneSymbol = "test gene symbol";
        String chromosome = "test chromosome";
        String mgiReference = "test mgi reference";
        Model model = new BindingAwareModelMap();
        GeneManagementListController instance = new GeneManagementListController();
        String expResult = "geneManagementList";
        String result = instance.go(geneId, geneName, geneSymbol, chromosome, mgiReference, model);
        assertEquals(expResult, result);

        Map modelMap = model.asMap();
        
        // Check filter.
        Filter filter = (Filter)modelMap.get("filter");
        assertEquals(geneId, filter.getGeneId());
        assertEquals(geneName, filter.getGeneName());
        assertEquals(geneSymbol, filter.getGeneSymbol());
        assertEquals(chromosome, filter.getChromosome());
        assertEquals(mgiReference, filter.getMgiReference());
        
        // Check filteredGenesList.
        List<Gene> filteredGenesList = (List<Gene>)modelMap.get("filteredGenesList");
        assertTrue(filteredGenesList.size() >= 0);
        
        // Check showResultsForm.
        boolean showResultsForm = (boolean)modelMap.get("showResultsForm");
        assertTrue(showResultsForm);
        
        // Check resultsCount.
        int resultsCount = (int)modelMap.get("resultsCount");
        assertEquals(filteredGenesList.size(), resultsCount);
    }

//    /**
//     * Test of deleteGene method, of class GeneManagementListController.
//     */
//    @Test
//    public void testDeleteGene() {
//        System.out.println("deleteGene");
//        int id_gene = 0;
//        GeneManagementListController instance = new GeneManagementListController();
//        ResponseEntity<String> expResult = null;
//        ResponseEntity<String> result = instance.deleteGene(id_gene);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }

    /**
     * Test of showFilter method, of class GeneManagementListController.
     */
    @Test
    public void testShowFilter() {
        System.out.println("showFilter");
        String geneId = "123";
        String geneName = "test gene name";
        String geneSymbol = "test gene symbol";
        String chromosome = "test chromosome";
        String mgiReference = "test mgi reference";
        Model model = new BindingAwareModelMap();
        GeneManagementListController instance = new GeneManagementListController();
        String expResult = "geneManagementList";
        String result = instance.showFilter(geneId, geneName, geneSymbol, chromosome, mgiReference, model);
        assertEquals(expResult, result);

        Map modelMap = model.asMap();
        // Check filter.
        Filter filter = (Filter)modelMap.get("filter");
        assertEquals(geneId, filter.getGeneId());
        assertEquals(geneName, filter.getGeneName());
        assertEquals(geneSymbol, filter.getGeneSymbol());
        assertEquals(chromosome, filter.getChromosome());
        assertEquals(mgiReference, filter.getMgiReference());

        // Check showResultsForm.
        boolean showResultsForm = (boolean)modelMap.get("showResultsForm");
        assertTrue(showResultsForm);
    }

    /**
     * Test of getChromosomes method, of class GeneManagementListController.
     */
    @Test
    public void testGetChromosomes() {
        System.out.println("getChromosomes - has no filterTerm.");
        GeneManagementListController instance = new GeneManagementListController();
        List<String> result = instance.getChromosomes();
        assert(result.size() > 0);
    }

    /**
     * Test of getGeneIds method, of class GeneManagementListController.
     */
    @Test
    public void testGetGeneIds() {
        System.out.println("getGeneIds - has no filterTerm.");
        GeneManagementListController instance = new GeneManagementListController();
        List<String> result = instance.getGeneIds();
        assert(result.size() > 0);
    }

    /**
     * Test of getGeneNames method, of class GeneManagementListController.
     */
    @Test
    public void testGetGeneNames() {
        System.out.println("getGeneNames - filterTerm: no match expect NULL");
        String filterTerm = "A very unlikely term for a match.";
        GeneManagementListController instance = new GeneManagementListController();
        List<String> expResult = new ArrayList();
        List<String> result = instance.getGeneNames(filterTerm);
        assertEquals(expResult, result);
        
        System.out.println("getGeneNames - filterTerm: '1' - expect some results.");
        filterTerm = "1";
        instance = new GeneManagementListController();
        result = instance.getGeneNames(filterTerm);
        assert(result.size() > 0);
    }

    /**
     * Test of getGeneSymbols method, of class GeneManagementListController.
     */
    @Test
    public void testGetGeneSymbols() {
        System.out.println("getGeneSymbols - filterTerm: no match expect NULL");
        String filterTerm = "A very unlikely term for a match.";
        GeneManagementListController instance = new GeneManagementListController();
        List<String> expResult = new ArrayList();
        List<String> result = instance.getGeneSymbols(filterTerm);
        assertEquals(expResult, result);
        
        System.out.println("getGeneSymbols - filterTerm: '1' - expect some results.");
        filterTerm = "1";
        instance = new GeneManagementListController();
        result = instance.getGeneSymbols(filterTerm);
        assert(result.size() > 0);
    }
    
    /**
     * Test of getMGIReferences method, of class GeneManagementListController.
     */
    @Test
    public void testGetMGIReferences() {
        System.out.println("getMGIReferences - filterTerm: no match expect NULL");
        String filterTerm = "A very unlikely term for a match.";
        GeneManagementListController instance = new GeneManagementListController();
        List<String> expResult = new ArrayList();
        List<String> result = instance.getMGIReferences(filterTerm);
        assertEquals(expResult, result);
        
        System.out.println("getMGIReferences - filterTerm: '1' - expect some results.");
        filterTerm = "1";
        instance = new GeneManagementListController();
        result = instance.getMGIReferences(filterTerm);
        assert(result.size() > 0);
    }
    
}
