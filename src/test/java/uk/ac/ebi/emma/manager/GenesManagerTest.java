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

package uk.ac.ebi.emma.manager;

import java.util.List;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import uk.ac.ebi.emma.entity.Allele;
import uk.ac.ebi.emma.entity.Gene;
import uk.ac.ebi.emma.entity.GeneSynonym;
import uk.ac.ebi.emma.util.Filter;

/**
 *
 * @author mrelac
 */
public class GenesManagerTest {
    protected final Logger logger = Logger.getLogger(this.getClass());
    
    public GenesManagerTest() {
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

    /**
     * Test of getGenes method, of class GenesManager.
     */
    @Test
    public void testGetGenes() {
        logger.info("getGenes");
        GenesManager instance = new GenesManager();
        List<Gene> result = instance.getGenes();
        assertNotNull(result);
        logger.info("Successfully fetched " + result.size() + " results.");
        
    }

//    /**
//     * Test of save method, of class GenesManager.
//     */
//    @Test
//    public void testSave() {
//        System.out.println("save");
//        Gene gene = null;
//        GenesManager instance = new GenesManager();
//        instance.save(gene);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of delete method, of class GenesManager.
//     */
//    @Test
//    public void testDelete() {
//        System.out.println("delete");
//        Gene gDAO = null;
//        GenesManager instance = new GenesManager();
//        instance.delete(gDAO);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getGeneIds method, of class GenesManager.
//     */
//    @Test
//    public void testGetGeneIds() {
//        System.out.println("getGeneIds");
//        GenesManager instance = new GenesManager();
//        List<String> expResult = null;
//        List<String> result = instance.getGeneIds();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getNames method, of class GenesManager.
//     */
//    @Test
//    public void testGetNames() {
//        System.out.println("getNames");
//        GenesManager instance = new GenesManager();
//        List<String> expResult = null;
//        List<String> result = instance.getNames();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getSymbols method, of class GenesManager.
//     */
//    @Test
//    public void testGetSymbols() {
//        System.out.println("getSymbols");
//        GenesManager instance = new GenesManager();
//        List<String> expResult = null;
//        List<String> result = instance.getSymbols();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getChromosomes method, of class GenesManager.
//     */
//    @Test
//    public void testGetChromosomes() {
//        System.out.println("getChromosomes");
//        GenesManager instance = new GenesManager();
//        List<String> expResult = null;
//        List<String> result = instance.getChromosomes();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getMGIReferences method, of class GenesManager.
//     */
//    @Test
//    public void testGetMGIReferences() {
//        System.out.println("getMGIReferences");
//        GenesManager instance = new GenesManager();
//        List<String> expResult = null;
//        List<String> result = instance.getMGIReferences();
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getGene method, of class GenesManager.
//     */
//    @Test
//    public void testGetGene_int() {
//        System.out.println("getGene");
//        int id_gene = 0;
//        GenesManager instance = new GenesManager();
//        Gene expResult = null;
//        Gene result = instance.getGene(id_gene);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getGene method, of class GenesManager.
//     */
//    @Test
//    public void testGetGene_String() {
//        System.out.println("getGene");
//        String name = "";
//        GenesManager instance = new GenesManager();
//        Gene expResult = null;
//        Gene result = instance.getGene(name);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getFilteredGenesList method, of class GenesManager.
//     */
//    @Test
//    public void testGetFilteredGenesList() {
//        System.out.println("getFilteredGenesList");
//        Filter filter = null;
//        GenesManager instance = new GenesManager();
//        List<Gene> expResult = null;
//        List<Gene> result = instance.getFilteredGenesList(filter);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of toJSON method, of class GenesManager.
//     */
//    @Test
//    public void testToJSON() {
//        System.out.println("toJSON");
//        List<Gene> genesDAOList = null;
//        String expResult = "";
//        String result = GenesManager.toJSON(genesDAOList);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of toJson method, of class GenesManager.
//     */
//    @Test
//    public void testToJson() {
//        System.out.println("toJson");
//        Gene genesDAO = null;
//        String expResult = "";
//        String result = GenesManager.toJson(genesDAO);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getBoundAlleles method, of class GenesManager.
//     */
//    @Test
//    public void testGetBoundAlleles() {
//        System.out.println("getBoundAlleles");
//        int id_gene = 0;
//        GenesManager instance = new GenesManager();
//        List<Allele> expResult = null;
//        List<Allele> result = instance.getBoundAlleles(id_gene);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of findGeneSynonym method, of class GenesManager.
//     */
//    @Test
//    public void testFindSyn_genesDAO() {
//        System.out.println("findGeneSynonym");
//        Gene genesDAO = null;
//        int id_syn = 0;
//        GeneSynonym expResult = null;
//        GeneSynonym result = GenesManager.findGeneSynonym(genesDAO, id_syn);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of addSynonym method, of class GenesManager.
//     */
//    @Test
//    public void testAddSynonym() {
//        System.out.println("addSynonym");
//        Gene gene = null;
//        GenesManager instance = new GenesManager();
//        GeneSynonym expResult = null;
//        GeneSynonym result = instance.addSynonym(gene);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of deleteSynonym method, of class GenesManager.
//     */
//    @Test
//    public void testDeleteSynonym() {
//        System.out.println("deleteSynonym");
//        Gene gene = null;
//        int id_syn = 0;
//        GenesManager instance = new GenesManager();
//        instance.deleteSynonym(gene, id_syn);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
