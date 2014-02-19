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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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
    
}
