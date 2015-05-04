/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.qcc.modules.learningpalette.Variables;

import java.util.ArrayList;
import org.qcc.modules.learningpalette.FileList;

/**
 * List of XML files that should be loaded.
 * @author Tony
 */
public class VariablesFileList extends FileList {
    
    @Override
    public ArrayList<String> getFileList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("LP_Boolean.xml");
        list.add("LP_Byte.xml");
        list.add("LP_Char.xml");
        list.add("LP_Double.xml");
        list.add("LP_Float.xml");
        list.add("LP_Int.xml");
        list.add("LP_Long.xml");
        list.add("LP_Short.xml");
        list.add("LP_String.xml");
        return list;
    }
}
