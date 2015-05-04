/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.qcc.modules.learningpalette;

import java.util.Map;

/**
 * The basic input class handles the Simple Paste/Basic Input user customized
 * inputs.
 *
 * @author Tony
 */
public class LP_BasicInput implements IPaletteItemLogic {

    @Override
    public String executeLogic(Map<String, String> codeMap, Map<String, String> variableMap) {
        String codeTemplate = codeMap.get("codeTemplate");

        //Populate variableKeys
        for (String key : variableMap.keySet()) {
            codeTemplate = codeTemplate.replace(key, variableMap.get(key));
        }

        return codeTemplate;
    }
}
