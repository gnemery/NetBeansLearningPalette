/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.qcc.modules.learningpalette.Customizer;

import java.awt.Dialog;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.swing.text.Utilities;

import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.qcc.modules.learningpalette.CodeParser;
//from the "Java Source UI" module

/**
 *
 * @author Tony
 */
public class Template_Text extends javax.swing.JPanel {

    private String customizerName;
    private int lastControlY = 0;
    private Map<String, Object> controlMap;
    private ArrayList<CustomizerControl> controls;
    private ArrayList<String> variableList;

    /**
     * Creates new form LP_Customizer
     */
    public Template_Text(String name, String description, ArrayList<CustomizerControl> controls, JTextComponent target) {

        initComponents();

        this.customizerName = name;
        this.controls = controls;
        this.controlMap = new HashMap<>();
        ;

        //this.descriptionLabel.setText(description);
        //Create a layout for this form
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();

        JLabel formLabel = new JLabel(description);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = 0;
        c.ipady = 10;

        this.add(formLabel, c);

        int row = 0;
        for (CustomizerControl control : controls) {

            if (control.getClass().isAssignableFrom(CustomizerTextField.class)) {
                //Text Control
                row += 1;

                //Add the label first.
                JLabel newLabel = new JLabel(control.getLabel());
                c = new GridBagConstraints(); //Reset constraints
                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 0.5;
                c.gridx = 0;
                c.gridwidth = 1;
                c.gridy = row;
                c.ipady = 10;
                this.add(newLabel, c);

                JTextField newTextField = new JTextField((String) control.getValue());
                c = new GridBagConstraints(); //Reset constraints
                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 0.5;
                c.gridx = 1;
                c.gridwidth = 2;
                c.gridy = row;
                controlMap.put(control.getName(), newTextField);
                this.add(newTextField, c);

            } else if (control.getClass().isAssignableFrom(CustomizerComboField.class)) {
                //Combo Control
                row += 1;
                //Add the label first.
                JLabel newLabel = new JLabel(control.getLabel());
                c = new GridBagConstraints(); //Reset constraints
                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 0.5;
                c.gridx = 0;
                c.gridwidth = 1;
                c.gridy = row;
                c.ipady = 10;
                this.add(newLabel, c);

                //Convert ArrayList to Array
                ArrayList<String> controlValues = (ArrayList<String>) control.getValue();
                String[] valueArray = new String[controlValues.size()];
                valueArray = controlValues.toArray(valueArray);

                JComboBox newComboField = new JComboBox(valueArray);
                c = new GridBagConstraints(); //Reset constraints
                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 0.5;
                c.gridx = 1;
                c.gridwidth = 2;
                c.gridy = row;
                controlMap.put(control.getName(), newComboField);
                this.add(newComboField, c);

            } else if (control.getClass().isAssignableFrom(CustomizerVariableField.class)) {
                //Variable Field

                //Populate variables from code if it hasn't been done already
                if (variableList == null) {
                    String code = target.getText();
                    JEditorPane codeEditor = (JEditorPane) target;
                    String language = codeEditor.getContentType();
                    CodeParser parser = new CodeParser(code, language);
                    variableList = parser.getVariableList();
                }

                //Build the control, it is just a dropdown with the variable list
                //Combo Control
                row += 1;
                //Add the label first.
                JLabel newLabel = new JLabel(control.getLabel());
                c = new GridBagConstraints(); //Reset constraints
                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 0.5;
                c.gridx = 0;
                c.gridwidth = 1;
                c.gridy = row;
                c.ipady = 10;
                this.add(newLabel, c);

                //Convert ArrayList to Array
                String[] valueArray = new String[variableList.size()];
                valueArray = variableList.toArray(valueArray);

                JComboBox newComboField = new JComboBox(valueArray);
                newComboField.setEditable(true);
                c = new GridBagConstraints(); //Reset constraints
                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 0.5;
                c.gridx = 1;
                c.gridwidth = 2;
                c.gridy = row;
                controlMap.put(control.getName(), newComboField);
                this.add(newComboField, c);

            } else if (control.getClass().isAssignableFrom(CustomizerExpressionField.class)) {

            }
        }

        //Check if we can assign the target JTextComponent to a JEditorPane.
        //row += 1;
        //int lineNum = getRow(target.getCaretPosition(), target);
        //int columnNum = getColumn(target.getCaretPosition(), target);
        this.validate();
        this.repaint();

    }

    public static int getRow(int pos, JTextComponent editor) {
        int rn = (pos == 0) ? 1 : 0;
        try {
            int offs = pos;
            while (offs > 0) {
                offs = Utilities.getRowStart(editor, offs) - 1;
                rn++;
            }
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        return rn;
    }

    public static int getColumn(int pos, JTextComponent editor) {
        try {
            return pos - Utilities.getRowStart(editor, pos) + 1;
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        return -1;
    }

    private Dialog dialog = null;
    private DialogDescriptor descriptor = null;
    private boolean dialogOK = false;

    public boolean showDialog() {
        dialogOK = false;

        descriptor = new DialogDescriptor(this, this.customizerName, true,
                DialogDescriptor.OK_CANCEL_OPTION, DialogDescriptor.CANCEL_OPTION,
                new ActionListener() {
                    @Override
                    public void actionPerformed(java.awt.event.ActionEvent e) {
                        if (!descriptor.getValue().equals(DialogDescriptor.CANCEL_OPTION)) {
                            dialogOK = true;
                        }
                        dialog.dispose();

                    }
                });

        dialog = DialogDisplayer.getDefault().createDialog(descriptor);

        dialog.setVisible(true);
        repaint();

        return dialogOK;

    }

    public Map<String, String> getVariableMap() {
        Map<String, String> variableMap = new HashMap<String, String>();

        for (CustomizerControl control : controls) {
            Object controlObject = controlMap.get(control.getName());
            if (controlObject != null) {
                if (control.getClass().isAssignableFrom(CustomizerTextField.class)) {
                    //Text Field
                    JTextField textField = (JTextField) controlObject;
                    variableMap.put(control.getName(), textField.getText());
                } else if (control.getClass().isAssignableFrom(CustomizerComboField.class)) {
                    //Combo Field
                    JComboBox comboField = (JComboBox) controlObject;
                    variableMap.put(control.getName(), comboField.getSelectedItem().toString());
                } else if (control.getClass().isAssignableFrom(CustomizerVariableField.class)) {
                    //Combo Field
                    JComboBox comboField = (JComboBox) controlObject;
                    variableMap.put(control.getName(), comboField.getSelectedItem().toString());
                } else if (control.getClass().isAssignableFrom(CustomizerExpressionField.class)) {
                }
            }
        }
        return variableMap;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        expressionPanel = new javax.swing.JPanel();
        textLabel = new java.awt.Label();
        textField = new javax.swing.JTextField();

        expressionPanel.setToolTipText(org.openide.util.NbBundle.getMessage(Template_Text.class, "Template_Text.expressionPanel.toolTipText")); // NOI18N

        textLabel.setText(org.openide.util.NbBundle.getMessage(Template_Text.class, "Template_Text.textLabel.text")); // NOI18N

        textField.setText(org.openide.util.NbBundle.getMessage(Template_Text.class, "Template_Text.textField.text")); // NOI18N

        javax.swing.GroupLayout expressionPanelLayout = new javax.swing.GroupLayout(expressionPanel);
        expressionPanel.setLayout(expressionPanelLayout);
        expressionPanelLayout.setHorizontalGroup(
            expressionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(expressionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(textLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 86, Short.MAX_VALUE)
                .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        expressionPanelLayout.setVerticalGroup(
            expressionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(textLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(textField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(expressionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(expressionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(14, Short.MAX_VALUE))
        );

        expressionPanel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(Template_Text.class, "Template_Text.expressionPanel.AccessibleContext.accessibleName")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel expressionPanel;
    private javax.swing.JTextField textField;
    private java.awt.Label textLabel;
    // End of variables declaration//GEN-END:variables
}
