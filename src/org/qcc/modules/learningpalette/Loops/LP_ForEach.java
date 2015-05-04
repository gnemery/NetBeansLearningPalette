package org.qcc.modules.learningpalette.Loops;

import org.qcc.modules.learningpalette.*;
import java.util.Map;

public class LP_ForEach implements IPaletteItemLogic {

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
            
        String arrayName = variableMap.get("ArrayName");
            if (arrayName == null)
                arrayName = "";

            
        if (!comment.equals("")) {
            codeOutput = "//" + comment + "\n";
        }
        
        if (defaultValue.equals("(None)")) {
            //Not setting a default value.
            codeOutput = codeOutput + codeMap.get("codeTemplate1");
        } else {
            //Sets a default variable value.
            codeOutput = codeOutput + codeMap.get("codeTemplate2");

            //Translate the string into the proper language representation.
            if (defaultValue.equals("byte")) {
                codeOutput = codeOutput.replace("$value", codeMap.get("byteValue"));
                
            } else if (defaultValue.equals("char")){
                codeOutput = codeOutput.replace("$value", codeMap.get("charValue"));
                
            } else if (defaultValue.equals("double")){
                codeOutput = codeOutput.replace("$value", codeMap.get("doubleValue"));
                
            } else if (defaultValue.equals("int")){
                codeOutput = codeOutput.replace("$value", codeMap.get("intValue"));
                
            } else if (defaultValue.equals("string")){
                codeOutput = codeOutput.replace("$value", codeMap.get("stringValue"));
            }
        }
        
        codeOutput = codeOutput.replace("$varName", variableName);
        codeOutput = codeOutput.replace("$arrayName", arrayName);

        return codeOutput;
    }
}

