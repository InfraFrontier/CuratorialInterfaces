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

package uk.ac.ebi.emma.util;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.Properties;
import javax.mail.Address;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.springframework.validation.Errors;

/**
 *
 * @author mrelac
 */
public class UtilsTest {
    
    public UtilsTest() {
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
//     * Test of getProperty method, of class Utils.
//     */
//    @Test
//    public void testGetProperty_String_String() {
//        System.out.println("getProperty");
//        String propertiesFile = "";
//        String property = "";
//        String expResult = "";
//        String result = Utils.getProperty(propertiesFile, property);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getProperties method, of class Utils.
//     */
//    @Test
//    public void testGetProperties() {
//        System.out.println("getProperties");
//        String propertiesFile = "";
//        Properties expResult = null;
//        Properties result = Utils.getProperties(propertiesFile);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getPropertiesFromClasspath method, of class Utils.
//     */
//    @Test
//    public void testGetPropertiesFromClasspath() {
//        System.out.println("getPropertiesFromClasspath");
//        String propertiesFile = "";
//        Properties expResult = null;
//        Properties result = Utils.getPropertiesFromClasspath(propertiesFile);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getProperty method, of class Utils.
//     */
//    @Test
//    public void testGetProperty_3args() {
//        System.out.println("getProperty");
//        String propertiesFile = "";
//        String property = "";
//        String delimiter = "";
//        String[] expResult = null;
//        String[] result = Utils.getProperty(propertiesFile, property, delimiter);
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of toAddress method, of class Utils.
//     */
//    @Test
//    public void testToAddress() {
//        System.out.println("toAddress");
//        String[] values = null;
//        Address[] expResult = null;
//        Address[] result = Utils.toAddress(values);
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of join method, of class Utils.
//     */
//    @Test
//    public void testJoin() {
//        System.out.println("join");
//        Object[] array1 = null;
//        Object[] array2 = null;
//        Object[] expResult = null;
//        Object[] result = Utils.join(array1, array2);
//        assertArrayEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getDbValue method, of class Utils.
//     */
//    @Test
//    public void testGetDbValue() {
//        System.out.println("getDbValue");
//        ResultSet rs = null;
//        String columnName = "";
//        String expResult = "";
//        String result = Utils.getDbValue(rs, columnName);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of tryParseDouble method, of class Utils.
//     */
//    @Test
//    public void testTryParseDouble() {
//        System.out.println("tryParseDouble");
//        Object o = null;
//        Double expResult = null;
//        Double result = Utils.tryParseDouble(o);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of tryParseInt method, of class Utils.
//     */
//    @Test
//    public void testTryParseInt() {
//        System.out.println("tryParseInt");
//        Object o = null;
//        Integer expResult = null;
//        Integer result = Utils.tryParseInt(o);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of tryParseToDbDate method, of class Utils.
//     */
//    @Test
//    public void testTryParseToDbDate() {
//        System.out.println("tryParseToDbDate");
//        String dateString = "";
//        Date expResult = null;
//        Date result = Utils.tryParseToDbDate(dateString);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of wrap method, of class Utils.
//     */
//    @Test
//    public void testWrap() {
//        System.out.println("wrap");
//        String value = "";
//        String wrapper = "";
//        String expResult = "";
//        String result = Utils.wrap(value, wrapper);
//        assertEquals(expResult, result);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of validateMaxFieldLengths method, of class Utils.
//     */
//    @Test
//    public void testValidateMaxFieldLengths() {
//        System.out.println("validateMaxFieldLengths");
//        Object instance_2 = null;
//        String tablename = "";
//        Errors errors = null;
//        Utils.validateMaxFieldLengths(instance_2, tablename, errors);
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
    
    
    /**
     * Test of cleanIntArray method, of class Utils.
     */
    @Test
    public void testCleanIntArray() {
        System.out.println("cleanIntArray - null");
        String intArray = null;
        String expResult = "";
        String result = Utils.cleanIntArray(intArray);
        assertEquals(expResult, result);
        
        System.out.println("cleanIntArray - empty, with whitespace");
        intArray = "    ";
        expResult = "";
        result = Utils.cleanIntArray(intArray);
        assertEquals(expResult, result);
        
        System.out.println("cleanIntArray - only commas, with whitespace");
        intArray = " , ,  ,";
        expResult = "";
        result = Utils.cleanIntArray(intArray);
        assertEquals(expResult, result);
        
        System.out.println("cleanIntArray - non-numeric single, with whitespace");
        intArray = "    test      ";
        expResult = "test";
        result = Utils.cleanIntArray(intArray);
        assertEquals(expResult, result);
        
        System.out.println("cleanIntArray - non-numeric multiple");
        intArray = "    test1,  test2  ,    ";
        expResult = "test1,test2,";
        result = Utils.cleanIntArray(intArray);
        assertEquals(expResult, result);
        
        System.out.println("cleanIntArray - multiple valid values with leading whitespace");
        intArray = "    3 , 4  ,    5 ";
        expResult = "3,4,5";
        result = Utils.cleanIntArray(intArray);
        assertEquals(expResult, result);
        
        System.out.println("cleanIntArray - multiple valid values with trailing whitespace");
        intArray = "3 , 4  ,    5 ";
        expResult = "3,4,5";
        result = Utils.cleanIntArray(intArray);
        assertEquals(expResult, result);
    }
    
    /**
     * Test of isValidInt method, of class Utils.
     */
    @Test
    public void testIsValidInt() {
        System.out.println("isValidInt - null");
        String intArray = null;
        boolean expResult = false;
        boolean result = Utils.isValidIntArray(intArray);
        assertEquals(expResult, result);
        
        System.out.println("isValidInt - empty");
        intArray = "";
        expResult = false;
        result = Utils.isValidIntArray(intArray);
        assertEquals(expResult, result);
        
        System.out.println("isValidInt - only commas");
        intArray = ",,,,,";
        expResult = false;
        result = Utils.isValidIntArray(intArray);
        assertEquals(expResult, result);
        
        System.out.println("isValidInt - non-numeric single");
        intArray = "Invalid integer";
        expResult = false;
        result = Utils.isValidIntArray(intArray);
        assertEquals(expResult, result);
        
        System.out.println("isValidInt - non-numeric multiple");
        intArray = "Invalid integer, invalid integer";
        expResult = false;
        result = Utils.isValidIntArray(intArray);
        assertEquals(expResult, result);
        
        System.out.println("isValidInt - float");
        intArray = "3.141598657";
        expResult = false;
        result = Utils.isValidIntArray(intArray);
        assertEquals(expResult, result);
        
        System.out.println("isValidInt - float");
        intArray = "3.141592653";
        expResult = false;
        result = Utils.isValidIntArray(intArray);
        assertEquals(expResult, result);
        
        System.out.println("isValidInt - single valid value");
        intArray = "3";
        expResult = true;
        result = Utils.isValidIntArray(intArray);
        assertEquals(expResult, result);
        
        System.out.println("isValidInt - multiple valid values");
        intArray = "3,4,5";
        expResult = true;
        result = Utils.isValidIntArray(intArray);
        assertEquals(expResult, result);
        
        System.out.println("isValidInt - multiple valid values with leading whitespace");
        intArray = "    3 , 4  ,    5 ";
        expResult = false;
        result = Utils.isValidIntArray(intArray);
        assertEquals(expResult, result);
        
        System.out.println("isValidInt - multiple valid values with trailing whitespace");
        intArray = "3 , 4  ,    5 ";
        expResult = false;
        result = Utils.isValidIntArray(intArray);
        assertEquals(expResult, result);
    }
    
}
