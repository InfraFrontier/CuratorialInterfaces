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

/**
 *
 * @author mrelac
 */
public class UtilControllerTest {
    
    public UtilControllerTest() {
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
     * Test of getFieldLengths method, of class UtilController.
     */
    @Test
    public void testGetFieldLengths() {
        System.out.println("getFieldLengths - genes");
        String tablename = "genes";
        UtilController instance = new UtilController();
        HashMap<String, Integer> result = instance.getFieldLengths(tablename);
        assertEquals("gene.name: ", 300, (int)result.get("name"));
        assertEquals("gene.symbol: ", 100, (int)result.get("symbol"));
        assertEquals("gene.chromosome: ", 2, (int)result.get("chromosome"));
        assertEquals("gene.cytoband: ", 15, (int)result.get("cytoband"));
        assertEquals("gene.species: ", 20, (int)result.get("species"));
        assertEquals("gene.mgi_ref: ", 20, (int)result.get("mgi_ref"));
        assertEquals("gene.username: ", 20, (int)result.get("username"));
        assertEquals("gene.promoter: ", 150, (int)result.get("promoter"));
        assertEquals("gene.founder_line_number: ", 150, (int)result.get("founder_line_number"));
        assertEquals("gene.plasmid_construct: ", 150, (int)result.get("plasmid_construct"));
        assertEquals("gene.ensembl_ref: ", 18, (int)result.get("ensembl_ref"));

        System.out.println("getFieldLengths - syn_genes");
        tablename = "syn_genes";
        result = instance.getFieldLengths(tablename);
        assertEquals("syn_genes.name: ", 50, (int)result.get("name"));
        assertEquals("syn_genes.symbol: ", 50, (int)result.get("symbol"));
        assertEquals("syn_genes.username: ", 20, (int)result.get("username"));
        
        
        
        System.out.println("getFieldLengths - alleles");
        tablename = "alleles";
        result = instance.getFieldLengths(tablename);
        assertEquals("allele.name: ", 200, (int)result.get("name"));
        assertEquals("allele.symbol: ", 100, (int)result.get("alls_form"));
        assertEquals("allele.mgi_ref: ", 20, (int)result.get("mgi_ref"));
        assertEquals("allele.username: ", 20, (int)result.get("username"));
    }
    
}
