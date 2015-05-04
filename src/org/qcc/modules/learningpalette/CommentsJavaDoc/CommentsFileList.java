/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.qcc.modules.learningpalette.CommentsJavaDoc;

import java.util.ArrayList;
import org.qcc.modules.learningpalette.FileList;

/**
 *
 * @author Tony
 */
public class CommentsFileList extends FileList {
    
    @Override
    public ArrayList<String> getFileList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("LP_FullComment.xml");
        list.add("LP_InlineComment.xml");
        return list;
    }
}
