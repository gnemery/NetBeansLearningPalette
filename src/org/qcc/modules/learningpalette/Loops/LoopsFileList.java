/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.qcc.modules.learningpalette.Loops;

import java.util.ArrayList;
import org.qcc.modules.learningpalette.FileList;

/**
 *
 * @author Tony
 */
public class LoopsFileList extends FileList {
    
    @Override
    public ArrayList<String> getFileList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("LP_DoWhile.xml");
        list.add("LP_Expression.xml");
        list.add("LP_ForEach.xml");
        list.add("LP_ForLoop.xml");
        list.add("LP_While.xml");
        return list;
    }
}