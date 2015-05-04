/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.qcc.modules.learningpalette.Decisions;

import java.util.ArrayList;
import org.qcc.modules.learningpalette.FileList;

/**
 *
 * @author Tony
 */
public class DecisionsFileList extends FileList {
    
    @Override
    public ArrayList<String> getFileList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("LP_Else.xml");
        list.add("LP_ElseIf.xml");
        list.add("LP_Expression.xml");
        list.add("LP_If.xml");
        list.add("LP_SwitchCase.xml");
        return list;
    }
}