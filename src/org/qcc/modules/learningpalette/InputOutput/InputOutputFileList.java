/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.qcc.modules.learningpalette.InputOutput;

import java.util.ArrayList;
import org.qcc.modules.learningpalette.FileList;

/**
 *
 * @author Tony
 */
public class InputOutputFileList extends FileList {
    
    @Override
    public ArrayList<String> getFileList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("LP_SendTextToConsole.xml");
        list.add("LP_ReadTextFromConsole.xml");
        list.add("LP_TryCatch.xml");
        return list;
    }
}