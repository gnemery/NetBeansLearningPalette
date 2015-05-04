/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.qcc.modules.learningpalette.Customizer;

/**
 * A customizer control is a control that will be placed on a JPanel frame.
 *
 * @author Tony
 */
public class CustomizerControl {

    private String name;
    private String label;
    private Object value;

    public CustomizerControl(String name, String label, Object value) {
        this.name = name;
        this.label = label;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getLabel() {
        return label;
    }

    public Object getValue() {
        return value;
    }
    
    /**
     * This should be overridden by implementing classes.
     * @return The type of control.
     */
    public String getType() {
        return "Generic";
    }

    @Override
    public String toString() {
        return this.name;
    }
}
