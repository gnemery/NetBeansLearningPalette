package org.qcc.modules.learningpalette.ObjectsClassesMethods;

import org.qcc.modules.learningpalette.*;
import java.util.Map;

public class LP_NewMethod implements IPaletteItemLogic {

    @Override
    public String executeLogic(Map<String, String> codeMap, Map<String, String> variableMap) {
        String codeOutput = "";
        String comment = variableMap.get("Comment");
        if (comment == null)
            comment = "";
        
        String modifierValue = variableMap.get("ModifierValue");
            if (modifierValue == null)
                modifierValue = "";
            
        String returnValue = variableMap.get("ReturnValue");
            if (returnValue == null)
                returnValue = "";
            
        String variableName = variableMap.get("VariableName");
            if (variableName == null)
                variableName = "";
            
        String parameterType = variableMap.get("ParameterType");
            if (parameterType == null)
                parameterType = "";
            
        String parameterName = variableMap.get("ParameterName");
            if (parameterName == null)
                parameterName = "";    
            
        if (!comment.equals("")) {
            codeOutput = "//" + comment + "\n";
        }

        //Translate the string into the proper language representation.
        if ((modifierValue.equals("(None)")) || (returnValue.equals("(None)"))) {
            //Not setting a default value.
            codeOutput = codeOutput + codeMap.get("codeTemplate1");
            
        } else if ((returnValue.equals("void")) && ((!modifierValue.equals("")))) {
                codeOutput = codeOutput + codeMap.get("codeTemplate2");
                
        } else if ((returnValue.equals("String")) && ((!modifierValue.equals("")))) {
            codeOutput = codeOutput + codeMap.get("codeTemplate3");
            
        } else if ((returnValue.equals("boolean")) && ((!modifierValue.equals("")))) {
            codeOutput = codeOutput + codeMap.get("codeTemplate4");
                
        } else {
            //Sets a default variable value.
            codeOutput = codeOutput + codeMap.get("codeTemplate5");
        }

        //Translate the string into the proper language representation.
        if (returnValue.equals("int")) {
            codeOutput = codeOutput.replace("$returnType", codeMap.get("integerValue"));

        } else if (returnValue.equals("double")) {
            codeOutput = codeOutput.replace("$returnType", codeMap.get("doubleValue"));

        } else if (returnValue.equals("String")) {
            codeOutput = codeOutput.replace("$returnType", codeMap.get("stringValue"));

        } else if (returnValue.equals("boolean")) {
            codeOutput = codeOutput.replace("$returnType", codeMap.get("booleanValue"));

        } else if (returnValue.equals("byte")) {
            codeOutput = codeOutput.replace("$returnType", codeMap.get("byteValue"));

        } else if (returnValue.equals("char")) {
            codeOutput = codeOutput.replace("$returnType", codeMap.get("charValue"));

        } else if (returnValue.equals("float")) {
            codeOutput = codeOutput.replace("$returnType", codeMap.get("floatValue"));

        } else if (returnValue.equals("long")) {
            codeOutput = codeOutput.replace("$returnType", codeMap.get("longValue"));

        } else if (returnValue.equals("short")) {
            codeOutput = codeOutput.replace("$returnType", codeMap.get("shortValue"));
            
        } else if (returnValue.equals("void")) {
            codeOutput = codeOutput.replace("$returnType", codeMap.get("voidValue"));
        }
    
        //Translate the string into the proper language representation.
        if (modifierValue.equals("public")) {
            codeOutput = codeOutput.replace("$modType", codeMap.get("publicValue"));

        } else if (modifierValue.equals("private")) {
            codeOutput = codeOutput.replace("$modType", codeMap.get("privateValue"));
        }
        

        //Translate the string into the proper language representation.
        if (parameterType.equals("(None)")) {
            codeOutput = codeOutput.replace("$paramType", codeMap.get("noValue"));
        
        } else if (parameterType.equals("int")) {
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

        codeOutput = codeOutput.replace("$varName", variableName);
        codeOutput = codeOutput.replace("$paramName", parameterName);
        
        return codeOutput;
    }
}
