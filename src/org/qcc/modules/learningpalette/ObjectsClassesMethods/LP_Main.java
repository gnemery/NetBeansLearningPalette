package org.qcc.modules.learningpalette.ObjectsClassesMethods;

import org.qcc.modules.learningpalette.*;
import java.util.Map;

public class LP_Main implements IPaletteItemLogic {

    @Override
    public String executeLogic(Map<String, String> codeMap, Map<String, String> variableMap) {
        String codeOutput = "";
        String comment = variableMap.get("Comment");
        if (comment == null)
            comment = "";

            
        String variableName = variableMap.get("VariableName");
            if (variableName == null)
                variableName = "";
            
        if (!comment.equals("")) {
            codeOutput = "//" + comment + "\n";
        }

        codeOutput = codeOutput + codeMap.get("codeTemplate1");
       

        codeOutput = codeOutput.replace("$varName", variableName);

        return codeOutput;
    }
}
