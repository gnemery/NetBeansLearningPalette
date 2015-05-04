/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.qcc.modules.learningpalette.Customizer;

import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Tony
 */
public class ExpressionEditor extends JPanel {

    private javax.swing.JButton addAND;
    private javax.swing.JButton addExpression;
    private javax.swing.JButton addOR;
    private javax.swing.JComboBox comparator;
    private java.awt.Label conditionsLabel;
    private javax.swing.JButton deleteLastExpr;
    private javax.swing.JList expressionList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox variableFirst;
    private javax.swing.JComboBox variableSecond;
    private javax.swing.JButton mergeExpressionsToGroup;

    private ArrayList<String> expression;
    private String language;

    /**
     * Generate a JPanel with expression editor controls.
     */
    public ExpressionEditor(String controlLabel, String language, ArrayList<String> variableList) {
        super(); //Call the parent constructor.
        conditionsLabel = new java.awt.Label();
        variableFirst = new javax.swing.JComboBox();
        comparator = new javax.swing.JComboBox();
        variableSecond = new javax.swing.JComboBox();
        addExpression = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        expression = new ArrayList<>();
        expressionList = new javax.swing.JList(expression.toArray());
        addOR = new javax.swing.JButton();
        addAND = new javax.swing.JButton();
        deleteLastExpr = new javax.swing.JButton();
        mergeExpressionsToGroup = new javax.swing.JButton();

        this.setBorder(javax.swing.BorderFactory.createTitledBorder(controlLabel)); // NOI18N
        this.setToolTipText(controlLabel); // NOI18N

        //Setup Conditions label
        conditionsLabel.setText("Conditional:");

        //Setup Variable Select/Comparator (TODO: extend variable editor into a class)
        variableFirst.setEditable(true);
        comparator.setEditable(true);
        variableSecond.setEditable(true);

        variableList.add(0, "");
        for (int i = 0; i < variableList.size(); i++) {
            variableFirst.addItem(variableList.get(i));
            variableSecond.addItem(variableList.get(i));
        }
        variableFirst.setSelectedIndex(0);
        variableSecond.setSelectedIndex(0);

        // == , !=, <, <=, >, >=
        String[] comparators = {"==", "!=", "<", "<=", ">", ">="};
        for (int i = 0; i < comparators.length; i++) {
            comparator.addItem(comparators[i]);
        }
        comparator.setSelectedIndex(0); //Select == by default

        //Setup "Add" Button
        addExpression.setText("Add");
        addExpression.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addExpressionActionPerformed(evt);
            }
        });

        //Setup "Expression" list
        expressionList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        jScrollPane1.setViewportView(expressionList);

        //Setup "Delete Last" button
        deleteLastExpr.setText("Delete Last");
        deleteLastExpr.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteLastExprActionPerformed(evt);
            }
        });

        //Setup "Add AND" Button
        addAND.setText("Add AND");
        addAND.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addANDActionPerformed(evt);
            }
        });

        //Setup "Add OR" Button
        addOR.setText("Add OR");
        addOR.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addORActionPerformed(evt);
            }
        });

        //Setup "Merge Expression" button
        mergeExpressionsToGroup.setText("Merge Expressions to Group");
        mergeExpressionsToGroup.addActionListener(new java.awt.event.ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mergeExpressionsToGroupActionPerformed(evt);
            }
        });

        updateFormState();



        javax.swing.GroupLayout thisLayout = new javax.swing.GroupLayout(this);
        this.setLayout(thisLayout);
        thisLayout.setHorizontalGroup(
            thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(thisLayout.createSequentialGroup()
                        .addComponent(conditionsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(variableFirst, 0, 1, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(comparator, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(variableSecond, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addExpression, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, thisLayout.createSequentialGroup()
                        .addComponent(deleteLastExpr)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(mergeExpressionsToGroup, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addOR)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addAND))))
        );
        thisLayout.setVerticalGroup(
            thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(thisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(variableFirst, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(comparator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(variableSecond, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(addExpression))
                    .addComponent(conditionsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(thisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addOR)
                    .addComponent(addAND)
                    .addComponent(deleteLastExpr)
                    .addComponent(mergeExpressionsToGroup))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26))
        );
        
        this.language = language;

    }

    /**
     * Returns the final expression value of this control.
     *
     * @return
     */
    public String getExpressionValue() {

        //Generate a string to return
        String expressionReturn = "";
        for (int i = 0; i < expression.size(); i++) {
            String temp = expression.get(i);
            if (i == 0) {
                //First entry is always an expression
                expressionReturn = temp;
            } else {
                boolean andOr = false;
                if (temp.equals("AND")) {
                    temp = "&&";
                    andOr = true;
                }
                if (temp.equals("OR")) {
                    temp = "||";
                    andOr = true;
                }

                if (i == expression.size() - 1 && andOr == true) {
                    //Do not add, user ended on an AND/OR statement.
                } else {
                    expressionReturn = expressionReturn + " " + temp;
                }
            }
        }
        return expressionReturn;
    }

    /**
     * Add an expression to the current conditional.
     */
    private void addExpressionActionPerformed(java.awt.event.ActionEvent evt) {

        //Verify we have a first variable, comparator, and second variable selected.
        String firstVar = "";
        if (variableFirst.getSelectedItem() != null) {
            firstVar = variableFirst.getSelectedItem().toString().trim();
        }

        String secondVar = "";
        if (variableSecond.getSelectedItem() != null) {
            secondVar = variableSecond.getSelectedItem().toString().trim();
        }

        String compare = comparator.getSelectedItem().toString();

        String errorString = "";
        if (firstVar.length() == 0) {
            errorString = errorString + "\n" + "Nothing was selected to compare for selection 1.";
        }

        if (secondVar.length() == 0) {
            errorString = errorString + "\n" + "Nothing was selected to compare for selection 2.";
        }

        if (errorString.length() > 0) {
            JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), "Unable to add expression: " + errorString);
            return;
        }

        //Special case for string handling in Java:
        if (secondVar.startsWith("\"") && secondVar.endsWith("\"") && language.equals("text/x-java")) {
            expression.add(firstVar + ".equals(" + secondVar + ")");
        } else {
            expression.add(firstVar + " " + compare + " " + secondVar);
        }

        updateFormState();
    }

    /**
     * Remove the last conditional.
     *
     * @param evt
     */
    private void deleteLastExprActionPerformed(java.awt.event.ActionEvent evt) {
        if (expression.size() > 0) {
            expression.remove(expression.size() - 1);
        }
        updateFormState();
    }

    /**
     * Add an "OR" statement to the current conditional.
     *
     * @param evt
     */
    private void addORActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        expression.add("OR");
        variableFirst.setSelectedIndex(0);
        variableSecond.setSelectedIndex(0);
        comparator.setSelectedIndex(0);
        updateFormState();
    }

    /**
     * Add an "AND" statement to the current conditional.
     *
     * @param evt
     */
    private void addANDActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
        expression.add("AND");
        variableFirst.setSelectedIndex(0);
        variableSecond.setSelectedIndex(0);
        comparator.setSelectedIndex(0);
        updateFormState();
    }

    /**
     * Updates the state of the form, disable controls, etc.
     */
    private void updateFormState() {
        //The expression list is formatted strictly as follows:
        // expression -> AND/OR -> expression -> AND/OR ... 

        int listCount = expression.size();

        //Delete Last Expression
        if (listCount == 0) {
            deleteLastExpr.setEnabled(false);
        } else {
            deleteLastExpr.setEnabled(true);
        }

        //Merge should only be displayed when list count >= 3;
        if (listCount >= 3) {
            mergeExpressionsToGroup.setEnabled(true);
        } else {
            mergeExpressionsToGroup.setEnabled(false);
        }

        //If there are an even number of controls on the list,
        //We do not allow addition of AND/OR, and vice versa.
        if (listCount % 2 == 0) {
            //Even
            addExpression.setEnabled(true);
            variableFirst.setEnabled(true);
            variableSecond.setEnabled(true);
            comparator.setEnabled(true);
            addAND.setEnabled(false);
            addOR.setEnabled(false);
        } else {
            //Odd
            addExpression.setEnabled(false);
            variableFirst.setEnabled(false);
            variableSecond.setEnabled(false);
            comparator.setEnabled(false);
            addAND.setEnabled(true);
            addOR.setEnabled(true);
        }

        //Redraw the expressionList.
        expressionList.setListData(expression.toArray());
    }

    private void mergeExpressionsToGroupActionPerformed(java.awt.event.ActionEvent evt) {
        //If this is performed, all expressions will be merged into a single grouping.
        if (expression.size() < 3) {
            return;
        }

        String newExpression = "(" + this.getExpressionValue() + ")";
        expression = new ArrayList<>();
        expression.add(newExpression);

        updateFormState();
    }

}
