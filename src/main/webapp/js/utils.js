/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 /**
 * Copyright © 2013 EMBL - European Bioinformatics Institute
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
 * 
 * This file is intended to hold general javascript utility functions to be
 * shared amongst all programs.
 */

/**
 * Tests <code>value</code> to see if it is an integer, returning true if it is,
 * false otherwise. a null value returns false.
 * 
 * @param {Object} value the value to be tested
 * @returns {Boolean} true if <code>value</code> is an integer; false otherwise
 */
function isInteger(value) {
    var intRegex = /^\d+$/;
    return intRegex.test(value);
}


