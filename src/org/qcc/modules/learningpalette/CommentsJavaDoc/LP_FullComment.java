package org.qcc.modules.learningpalette.CommentsJavaDoc;

import org.qcc.modules.learningpalette.*;
import java.util.Map;

public class LP_FullComment implements IPaletteItemLogic {

    @Override
    public String executeLogic(Map<String, String> codeMap, Map<String, String> variableMap) {
        String codeOutput = "";
        String comment = variableMap.get("Comment");
        if (comment == null) {
            comment = "";
        }
        comment = comment.trim();

        codeOutput = codeOutput + codeMap.get("codeTemplate1");
        codeOutput = codeOutput.replace("$Comment", comment);
        return codeOutput;
    }
}
