/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.qcc.modules.learningpalette.ObjectsClassesMethods;

import java.util.ArrayList;
import org.qcc.modules.learningpalette.FileList;

/**
 *
 * @author Tony
 */
public class OCMFileList extends FileList {
    
    @Override
    public ArrayList<String> getFileList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("LP_Main.xml");
        list.add("LP_NewClass.xml");
        list.add("LP_ExternalClass.xml");
        list.add("LP_NewMethod.xml");
        list.add("LP_InstantiateNewObject.xml");
        return list;
    }
}