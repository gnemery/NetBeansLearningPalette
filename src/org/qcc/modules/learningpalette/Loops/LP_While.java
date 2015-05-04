package org.qcc.modules.learningpalette.Loops;

import org.qcc.modules.learningpalette.*;
import java.util.Map;

public class LP_While implements IPaletteItemLogic {

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
            
        String conditionalValue = variableMap.get("ConditionalValue");
            if (conditionalValue == null)
                conditionalValue = "";
            
        if (!comment.equals("")) {
            codeOutput = "//" + comment + "\n";
        }

        if (conditionalValue.equals("(None)")) {
            //Not setting a default value.
            codeOutput = codeOutput + codeMap.get("codeTemplate1");
        } else {
            //Sets a default variable value.
            codeOutput = codeOutput + codeMap.get("codeTemplate2");

            //Translate the string into the proper language representation.
            if (conditionalValue.equals("==")) {
                codeOutput = codeOutput.replace("$condValue", codeMap.get("equalEqualValue"));
                
            } else if (conditionalValue.equals("!=")){
                codeOutput = codeOutput.replace("$condValue", codeMap.get("notEqualValue"));
                
            } else if (conditionalValue.equals(">")){
                codeOutput = codeOutput.replace("$condValue", codeMap.get("greaterValue"));
                
            } else if (conditionalValue.equals("<")){
                codeOutput = codeOutput.replace("$condValue", codeMap.get("lessValue"));
                
            } else if (conditionalValue.equals(">=")){
                codeOutput = codeOutput.replace("$condValue", codeMap.get("greaterEqualValue"));
                
            } else if (conditionalValue.equals("<=")){
                codeOutput = codeOutput.replace("$condValue", codeMap.get("lessEqualValue"));
            }
            
        }
        
        codeOutput = codeOutput.replace("$varName", variableName);
        codeOutput = codeOutput.replace("$value", defaultValue);

        return codeOutput;
    }
}

