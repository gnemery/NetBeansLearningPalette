package org.qcc.modules.learningpalette.Loops;

import org.qcc.modules.learningpalette.*;
import java.util.Map;

public class LP_DoWhile implements IPaletteItemLogic {

    @Override
    public String executeLogic(Map<String, String> codeMap, Map<String, String> variableMap) {
        String codeOutput = "";
        String comment = variableMap.get("Comment");
        if (comment == null) {
            comment = "";
        }

        String expression = variableMap.get("Expression");
        if (expression == null) {
            expression = "";
        }
        if (comment.length() > 0) {
            codeOutput = "//" + comment + "\n";
        }
        codeOutput = codeMap.get("codeTemplate1");
        codeOutput = codeOutput.replace("$Expression", expression);

        return codeOutput;
    }
}
