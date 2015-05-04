package org.qcc.modules.learningpalette.Variables;

import org.qcc.modules.learningpalette.*;
import java.util.Map;

public class LP_Float implements IPaletteItemLogic {

    @Override
    public String executeLogic(Map<String, String> codeMap, Map<String, String> variableMap) {
        String codeOutput = "";
        String comment = variableMap.get("Comment");
        if (comment == null)
            comment = "";
        String defaultValue = variableMap.get("DefaultValue");
            if (defaultValue == null)
                defaultValue = "";
            
        String variableName = variableMap.get("VariableName");
            if (variableName == null)
                variableName = "";
            
        if (!comment.equals("")) {
            codeOutput = "//" + comment + "\n";
        }
        
        if (defaultValue.equals("")) {
            //Not setting a default value.
            codeOutput = codeOutput + codeMap.get("codeTemplate1");
        } else {
            //Sets a default variable value.
            codeOutput = codeOutput + codeMap.get("codeTemplate2");

            codeOutput = codeOutput.replace("$value", defaultValue);
        }
        codeOutput = codeOutput.replace("$varName", variableName);

        return codeOutput;
    }
}