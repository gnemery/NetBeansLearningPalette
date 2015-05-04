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
import org.netbeans.modules.editor.NbEditorUtilities;

import org.openide.DialogDescriptor;
import org.openide.DialogDisplayer;
import org.qcc.modules.learningpalette.CodeParser;
//from the "Java Source UI" module

/**
 *
 * @author Tony
 */
public class LP_Customizer extends javax.swing.JPanel {

    private String customizerName;
    private int lastControlY = 0;
    private Map<String, Object> controlMap;
    private ArrayList<CustomizerControl> controls;
    private ArrayList<String> variableList;

    /**
     * Creates new form LP_Customizer
     */
    public LP_Customizer(String name, String description, String example, ArrayList<CustomizerControl> controls, JTextComponent target) {

        initComponents();

        this.customizerName = name;
        this.controls = controls;
        this.controlMap = new HashMap<>();

        //this.descriptionLabel.setText(description);
        //Create a layout for this form
        this.setLayout(new GridBagLayout());
        int row = 0;

        GridBagConstraints c = new GridBagConstraints();
        LabelField formLabel = new LabelField(description, "Description");
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 0.5;
        c.gridx = 0;
        c.gridy = row;
        c.ipadx = 5;
        c.ipady = 5;
        this.add(formLabel, c);

        //Finally, if an example description was included, add that as well.
        if (example.length() > 0) {
            row += 1;
            c = new GridBagConstraints();
            LabelField exampleLabel = new LabelField(example, "Example");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 0.5;
            c.gridx = 0;
            c.gridy = row;
            c.ipadx = 5;
            c.ipady = 5;
            this.add(exampleLabel, c);
        }

        for (CustomizerControl control : controls) {

            if (control.getClass().isAssignableFrom(CustomizerTextField.class)) {
                //Text Control
                row += 1;

                TextField newTextField = new TextField(control.getName(), control.getLabel(), (String) control.getValue());
                c = new GridBagConstraints(); //Reset constraints
                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 0.5;
                c.gridx = 0;
                c.gridwidth = 3;
                c.gridy = row;
                c.ipadx = 5;
                c.ipady = 5;
                controlMap.put(control.getName(), newTextField);
                this.add(newTextField, c);

            } else if (control.getClass().isAssignableFrom(CustomizerComboField.class)) {
                //Combo Control
                row += 1;

                //Convert ArrayList to Array
                ArrayList<String> controlValues = (ArrayList<String>) control.getValue();

                ComboDropdown newComboField = new ComboDropdown(control.getName(), control.getLabel(), controlValues);
                c = new GridBagConstraints(); //Reset constraints
                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 0.5;
                c.gridx = 0;
                c.gridwidth = 3;
                c.gridy = row;
                c.ipadx = 5;
                c.ipady = 5;
                controlMap.put(control.getName(), newComboField);
                this.add(newComboField, c);

            } else if (control.getClass().isAssignableFrom(CustomizerVariableField.class)) {
                //Variable Field
                populateVariableList(target);

                //Build the control, it is just a dropdown with the variable list
                //Combo Control
                row += 1;

                VariableDropdown newComboField = new VariableDropdown(control.getName(), control.getLabel(), variableList);
                c = new GridBagConstraints(); //Reset constraints
                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 0.5;
                c.gridx = 0;
                c.gridwidth = 3;
                c.gridy = row;
                c.ipadx = 5;
                c.ipady = 5;
                controlMap.put(control.getName(), newComboField);
                this.add(newComboField, c);

            } else if (control.getClass().isAssignableFrom(CustomizerExpressionField.class)) {
                //Combo Control
                row += 1;
                populateVariableList(target);
                String mimeExtension = NbEditorUtilities.getMimeType(target);
                ExpressionEditor exprEditor = new ExpressionEditor(control.getLabel(), mimeExtension, variableList);

                c = new GridBagConstraints(); //Reset constraints
                c.fill = GridBagConstraints.HORIZONTAL;
                c.weightx = 0.5;
                c.gridx = 0;
                c.gridwidth = 3;
                c.gridy = row;
                c.ipadx = 5;
                c.ipady = 5;
                controlMap.put(control.getName(), exprEditor);
                this.add(exprEditor, c);
            }
        }

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
        this.setMinimumSize(this.getSize());
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
                    TextField textField = (TextField) controlObject;
                    variableMap.put(control.getName(), textField.getControlValue());
                } else if (control.getClass().isAssignableFrom(CustomizerComboField.class)) {
                    //Combo Field
                    ComboDropdown comboField = (ComboDropdown) controlObject;
                    variableMap.put(control.getName(), comboField.getControlValue());
                } else if (control.getClass().isAssignableFrom(CustomizerVariableField.class)) {
                    //Combo Field
                    VariableDropdown comboField = (VariableDropdown) controlObject;
                    variableMap.put(control.getName(), comboField.getControlValue());
                } else if (control.getClass().isAssignableFrom(CustomizerExpressionField.class)) {
                    ExpressionEditor exprEditor = (ExpressionEditor) controlObject;
                    variableMap.put(control.getName(), exprEditor.getExpressionValue());
                }
            }
        }
        return variableMap;
    }

    private void populateVariableList(JTextComponent target) {
        //Populate variables from code if it hasn't been done already
        if (variableList == null) {
            String code = target.getText();
            JEditorPane codeEditor = (JEditorPane) target;
            String language = codeEditor.getContentType();
            CodeParser parser = new CodeParser(code, language);
            variableList = parser.getVariableList();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 413, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 218, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
