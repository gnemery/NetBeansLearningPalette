package org.qcc.modules.learningpalette.Decisions;

import org.qcc.modules.learningpalette.*;
import java.util.Map;

public class LP_Else implements IPaletteItemLogic {

    @Override
    public String executeLogic(Map<String, String> codeMap, Map<String, String> variableMap) {
        String codeOutput = "";
        String comment = variableMap.get("Comment");
        if (comment == null) {
            comment = "";
        }
        comment = comment.trim();

        String conditionalValue = variableMap.get("ConditionalValue");
        if (conditionalValue == null) {
            conditionalValue = "";
        }

        codeOutput = codeOutput + codeMap.get("codeTemplate1");
        if (comment.length() > 0) {
            comment = "//" + comment;
        }
        codeOutput = codeOutput.replace("$Comment", comment);
        return codeOutput;
    }
}
