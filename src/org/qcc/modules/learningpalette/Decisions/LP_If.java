package org.qcc.modules.learningpalette.Decisions;

import org.qcc.modules.learningpalette.*;
import java.util.Map;

public class LP_If implements IPaletteItemLogic {

    @Override
    public String executeLogic(Map<String, String> codeMap, Map<String, String> variableMap) {
        String codeOutput = "";
        String comment = variableMap.get("Comment");
        if (comment == null) {
            comment = "";
        }
        String conditionalValue = variableMap.get("ConditionalValue");
        if (conditionalValue == null) {
            conditionalValue = "";
        }
        if (comment.length() > 0) {
            codeOutput = "//" + comment + "\n";
        }
        codeOutput = codeOutput + codeMap.get("codeTemplate1");
        codeOutput = codeOutput.replace("$Expression", conditionalValue);
        return codeOutput;
    }
}
