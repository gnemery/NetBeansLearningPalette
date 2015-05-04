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
public class Template_ExpressionEditor extends javax.swing.JPanel {

    private String customizerName;
    private int lastControlY = 0;
    private Map<String, Object> controlMap;
    private ArrayList<CustomizerControl> controls;
    private ArrayList<String> variableList;

    /**
     * Creates new form LP_Customizer
     */
    public Template_ExpressionEditor(String name, String description, ArrayList<CustomizerControl> controls, JTextComponent target) {

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
        conditionsLabel = new java.awt.Label();
        variableFirst = new javax.swing.JComboBox();
        comparator = new javax.swing.JComboBox();
        variableSecond = new javax.swing.JComboBox();
        addExpression = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        expressionList = new javax.swing.JList();
        addOR = new javax.swing.JButton();
        addAND = new javax.swing.JButton();
        deleteLastExpr = new javax.swing.JButton();
        mergeExpressionsToGroup = new javax.swing.JButton();

        expressionPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(org.openide.util.NbBundle.getMessage(Template_ExpressionEditor.class, "Template_ExpressionEditor.expressionPanel.border.title"))); // NOI18N
        expressionPanel.setToolTipText(org.openide.util.NbBundle.getMessage(Template_ExpressionEditor.class, "Template_ExpressionEditor.expressionPanel.toolTipText")); // NOI18N

        conditionsLabel.setText(org.openide.util.NbBundle.getMessage(Template_ExpressionEditor.class, "Template_ExpressionEditor.conditionsLabel.text")); // NOI18N

        variableFirst.setEditable(true);

        comparator.setEditable(true);

        variableSecond.setEditable(true);

        org.openide.awt.Mnemonics.setLocalizedText(addExpression, org.openide.util.NbBundle.getMessage(Template_ExpressionEditor.class, "Template_ExpressionEditor.addExpression.text")); // NOI18N
        addExpression.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addExpressionActionPerformed(evt);
            }
        });

        expressionList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(expressionList);

        org.openide.awt.Mnemonics.setLocalizedText(addOR, org.openide.util.NbBundle.getMessage(Template_ExpressionEditor.class, "Template_ExpressionEditor.addOR.text")); // NOI18N
        addOR.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addORActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(addAND, org.openide.util.NbBundle.getMessage(Template_ExpressionEditor.class, "Template_ExpressionEditor.addAND.text")); // NOI18N
        addAND.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addANDActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(deleteLastExpr, org.openide.util.NbBundle.getMessage(Template_ExpressionEditor.class, "Template_ExpressionEditor.deleteLastExpr.text")); // NOI18N
        deleteLastExpr.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteLastExprActionPerformed(evt);
            }
        });

        org.openide.awt.Mnemonics.setLocalizedText(mergeExpressionsToGroup, org.openide.util.NbBundle.getMessage(Template_ExpressionEditor.class, "Template_ExpressionEditor.mergeExpressionsToGroup.text")); // NOI18N
        mergeExpressionsToGroup.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mergeExpressionsToGroupActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout expressionPanelLayout = new javax.swing.GroupLayout(expressionPanel);
        expressionPanel.setLayout(expressionPanelLayout);
        expressionPanelLayout.setHorizontalGroup(
            expressionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(expressionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(expressionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(expressionPanelLayout.createSequentialGroup()
                        .addComponent(conditionsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(variableFirst, 0, 1, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comparator, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(variableSecond, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addExpression, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, expressionPanelLayout.createSequentialGroup()
                        .addComponent(deleteLastExpr)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mergeExpressionsToGroup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addOR)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addAND))))
        );
        expressionPanelLayout.setVerticalGroup(
            expressionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(expressionPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(expressionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(expressionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(variableFirst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comparator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(variableSecond, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(addExpression))
                    .addComponent(conditionsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(expressionPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addOR)
                    .addComponent(addAND)
                    .addComponent(deleteLastExpr)
                    .addComponent(mergeExpressionsToGroup))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(expressionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(expressionPanel, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(52, Short.MAX_VALUE))
        );

        expressionPanel.getAccessibleContext().setAccessibleName(org.openide.util.NbBundle.getMessage(Template_ExpressionEditor.class, "Template_ExpressionEditor.expressionPanel.AccessibleContext.accessibleName")); // NOI18N
    }// </editor-fold>//GEN-END:initComponents

    private void addExpressionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addExpressionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addExpressionActionPerformed

    private void deleteLastExprActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteLastExprActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deleteLastExprActionPerformed

    private void addORActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addORActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addORActionPerformed

    private void addANDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addANDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_addANDActionPerformed

    private void mergeExpressionsToGroupActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mergeExpressionsToGroupActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_mergeExpressionsToGroupActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addAND;
    private javax.swing.JButton addExpression;
    private javax.swing.JButton addOR;
    private javax.swing.JComboBox comparator;
    private java.awt.Label conditionsLabel;
    private javax.swing.JButton deleteLastExpr;
    private javax.swing.JList expressionList;
    private javax.swing.JPanel expressionPanel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton mergeExpressionsToGroup;
    private javax.swing.JComboBox variableFirst;
    private javax.swing.JComboBox variableSecond;
    // End of variables declaration//GEN-END:variables
}
