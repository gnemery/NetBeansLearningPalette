package org.qcc.modules.learningpalette.CommentsJavaDoc;

import org.qcc.modules.learningpalette.*;
import java.util.Map;

public class LP_InlineComment implements IPaletteItemLogic {

    @Override
    public String executeLogic(Map<String, String> codeMap, Map<String, String> variableMap) {
        String codeOutput = "";
        String comment = variableMap.get("Comment");
        if (comment == null)
            comment = "";

        if (!comment.equals("")) {
            codeOutput = "//" + comment + "\n";
        }

        return codeOutput;
    }
}