/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.qcc.modules.learningpalette.Customizer;

import java.util.ArrayList;
import javax.swing.JPanel;

/**
 *
 * @author Tony
 */
public class VariableDropdown extends JPanel {

    private java.awt.Label conditionsLabel;
    private javax.swing.JComboBox variableFirst;

    public VariableDropdown(String controlName, String controlLabel, ArrayList<String> variableList) {
        super();
        conditionsLabel = new java.awt.Label();
        variableFirst = new javax.swing.JComboBox();

        this.setToolTipText(controlName); // NOI18N

        conditionsLabel.setText(controlLabel);

        variableFirst.setEditable(true);
        variableList.add(0, "");
        for (String variableList1 : variableList) {
            variableFirst.addItem(variableList1);
        }
        variableFirst.setSelectedIndex(0);

        javax.swing.GroupLayout thisLayout = new javax.swing.GroupLayout(this);
        this.setLayout(thisLayout);
        thisLayout.setHorizontalGroup(
            thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thisLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(conditionsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                .addComponent(variableFirst, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        thisLayout.setVerticalGroup(
            thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(conditionsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(variableFirst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        
    }

    /**
     * Returns the value of the variable dropdown control.
     *
     * @return
     */
    public String getControlValue() {
        String returnValue = "";

        if (variableFirst.getSelectedItem() != null) {
            returnValue = variableFirst.getSelectedItem().toString().trim();
        }

        return returnValue;
    }

}
