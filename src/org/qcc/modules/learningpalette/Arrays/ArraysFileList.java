/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.qcc.modules.learningpalette.Arrays;

import java.util.ArrayList;
import org.qcc.modules.learningpalette.FileList;

/**
 * List of XML files that should be loaded.
 * @author Tony
 */
public class ArraysFileList extends FileList {
    
    @Override
    public ArrayList<String> getFileList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("LP_ByteArray.xml");
        list.add("LP_CharArray.xml");
        list.add("LP_DoubleArray.xml");
        list.add("LP_IntArray.xml");
        list.add("LP_StringArray.xml");
        return list;
    }
}
