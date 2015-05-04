/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.qcc.modules.learningpalette.Customizer;

import java.util.ArrayList;

/**
 *
 * @author Tony
 */
public class CustomizerComboField extends CustomizerControl {

    /**
     * Build a customizer text field control.
     */
    public CustomizerComboField(String name, String label, ArrayList<String> value) {
        super(name, label, value);
    }

    @Override
    public String getType() {
        return "Combo";
    }
}
