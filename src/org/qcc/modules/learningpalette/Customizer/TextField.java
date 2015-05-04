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
public class TextField extends JPanel {

    private javax.swing.JTextField textField;
    private java.awt.Label textLabel;

    public TextField(String controlName, String controlLabel, String defaultValue) {
        super();

        textLabel = new java.awt.Label();
        textField = new javax.swing.JTextField();

        this.setToolTipText(controlLabel); // NOI18N

        textLabel.setText(controlLabel); // NOI18N

        textField.setText(defaultValue); // NOI18N

        javax.swing.GroupLayout thisLayout = new javax.swing.GroupLayout(this);
        this.setLayout(thisLayout);
        thisLayout.setHorizontalGroup(
                thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(thisLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(textLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
                        .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
        );
        thisLayout.setVerticalGroup(
                thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(textLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
    }

    /**
     * Returns the value of the variable dropdown control.
     *
     * @return
     */
    public String getControlValue() {
        return this.textField.getText();
    }

}
