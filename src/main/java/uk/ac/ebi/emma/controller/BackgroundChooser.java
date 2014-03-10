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

import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import uk.ac.ebi.emma.entity.Background;
import uk.ac.ebi.emma.manager.BackgroundsManager;

/**
 *
 * @author mrelac
 */
@Controller
@RequestMapping("/backgroundChooser")
public class BackgroundChooser {
    protected BackgroundsManager backgroundsManager = new BackgroundsManager();
        
    /**
     * Return the full list of backgrounds
     * @param model the Backgrounds list data model
     * 
     * @return the full list of backgrounds
     */
    @RequestMapping(method=RequestMethod.GET)
    @ModelAttribute
    public String initialize(Model model)
    {
        List<Background> backgroundsList = backgroundsManager.getBackgroundNames();
        model.addAttribute("backgroundsList", backgroundsList);
        
        return "backgroundChooser";
    }
}
