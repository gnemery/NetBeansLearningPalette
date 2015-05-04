/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.qcc.modules.learningpalette;

import java.util.Map;

/**
 *
 * @author Tony
 */
public interface IPaletteItemLogic  {
    public String executeLogic(Map<String, String> codeMap, Map<String, String> variableMap);
}
