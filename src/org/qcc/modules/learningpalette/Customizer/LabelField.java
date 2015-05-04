/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.qcc.modules.learningpalette.Customizer;

import java.awt.GridBagLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Tony
 */
public class LabelField extends JPanel {

    public LabelField(String labelText, String labelTitle) {
        super();
        this.setLayout(new GridBagLayout());

        if (labelTitle.length() > 0) {
            this.setBorder(javax.swing.BorderFactory.createTitledBorder(labelTitle)); // NOI18N
        }
        labelText = labelText.replace("\\n", "<br>");
        JLabel myLabel = new JLabel("<html>" + labelText + "</html>"); //HTML tag will allow multi-line text.

        this.add(myLabel);
    }

}
