package org.qcc.modules.learningpalette.ObjectsClassesMethods;

import org.qcc.modules.learningpalette.*;
import java.util.Map;

public class LP_NewClass implements IPaletteItemLogic {

    @Override
    public String executeLogic(Map<String, String> codeMap, Map<String, String> variableMap) {
        String codeOutput = "";
        String comment = variableMap.get("Comment");
        if (comment == null) {
            comment = "";
        }

        String modifierValue = variableMap.get("ModifierValue");
        if (modifierValue == null) {
            modifierValue = "";
        }

        String variableName = variableMap.get("VariableName");
        if (variableName == null) {
            variableName = "";
        }

        String parameterType = variableMap.get("ParameterType");
        if (parameterType == null) {
            parameterType = "";
        }

        String parameterName = variableMap.get("ParameterName");
        if (parameterName == null) {
            parameterName = "";
        }

        if (!comment.equals("")) {
            codeOutput = "//" + comment + "\n";
        }

        
        if ((modifierValue.equals("(None)")) && (parameterType.equals("(None)"))
            && (parameterName.equals("")) && (variableName.equals(""))) {
            codeOutput = codeOutput + codeMap.get("codeTemplate1");
  
        } else if ((!modifierValue.equals("(None)")) && (parameterType.equals("(None)"))
            && (parameterName.equals("")) && (variableName.equals(""))) {
            codeOutput = codeOutput + codeMap.get("codeTemplate1");

        } else if ((!modifierValue.equals("(None)")) && (parameterType.equals("(None)"))
            && (parameterName.equals(""))) {
            codeOutput = codeOutput + codeMap.get("codeTemplate2");

        } else if ((!modifierValue.equals("(None)")) && (!parameterType.equals("(None)")) 
            && (!parameterName.equals(""))) {
            codeOutput = codeOutput + codeMap.get("codeTemplate3");
        }

        //Translate the string into the proper language representation.
        if (modifierValue.equals("public")) {
            codeOutput = codeOutput.replace("$modType", codeMap.get("publicValue"));

        } else if (modifierValue.equals("private")) {
            codeOutput = codeOutput.replace("$modType", codeMap.get("privateValue"));
        }

        //Translate the string into the proper language representation.
        if (parameterType.equals("int")) {
            codeOutput = codeOutput.replace("$paramType", codeMap.get("integerValue"));

        } else if (parameterType.equals("double")) {
            codeOutput = codeOutput.replace("$paramType", codeMap.get("doubleValue"));

        } else if (parameterType.equals("String")) {
            codeOutput = codeOutput.replace("$paramType", codeMap.get("stringValue"));

        } else if (parameterType.equals("boolean")) {
            codeOutput = codeOutput.replace("$paramType", codeMap.get("booleanValue"));

        } else if (parameterType.equals("byte")) {
            codeOutput = codeOutput.replace("$paramType", codeMap.get("byteValue"));

        } else if (parameterType.equals("char")) {
            codeOutput = codeOutput.replace("$paramType", codeMap.get("charValue"));

        } else if (parameterType.equals("float")) {
            codeOutput = codeOutput.replace("$paramType", codeMap.get("floatValue"));

        } else if (parameterType.equals("long")) {
            codeOutput = codeOutput.replace("$paramType", codeMap.get("longValue"));

        } else if (parameterType.equals("short")) {
            codeOutput = codeOutput.replace("$paramType", codeMap.get("shortValue"));
        }

        codeOutput = codeOutput.replace("$paramName", parameterName);
        codeOutput = codeOutput.replace("$varName", variableName);
        

        return codeOutput;
    }
}
