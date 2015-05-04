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
public class ComboDropdown extends JPanel {

    private final javax.swing.JComboBox comboDropdown;
    private final java.awt.Label comboLabel;

    public ComboDropdown(String controlName, String controlLabel, ArrayList<String> comboValues) {
        super();
        comboLabel = new java.awt.Label();
        comboDropdown = new javax.swing.JComboBox();

        for (String comboValue : comboValues) {
            comboDropdown.addItem(comboValue);
        }
        if (comboValues.size() > 0) {
            comboDropdown.setSelectedIndex(0);
        }

        this.setToolTipText(controlLabel); // NOI18N

        comboLabel.setText(controlLabel); // NOI18N

        javax.swing.GroupLayout thisLayout = new javax.swing.GroupLayout(this);
        this.setLayout(thisLayout);
        thisLayout.setHorizontalGroup(
                thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(thisLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(comboLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 63, Short.MAX_VALUE)
                        .addComponent(comboDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
        );
        thisLayout.setVerticalGroup(
                thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(comboLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(comboDropdown, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }

    /**
     * Returns the value of the variable dropdown control.
     *
     * @return
     */
    public String getControlValue() {
        String returnValue = "";

        if (comboDropdown.getSelectedItem() != null) {
            returnValue = comboDropdown.getSelectedItem().toString().trim();
        }

        return returnValue;
    }

}
