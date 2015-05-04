package org.qcc.modules.learningpalette.InputOutput;

import org.qcc.modules.learningpalette.*;
import java.util.Map;

public class LP_TryCatch implements IPaletteItemLogic {

    private Map<String, String> storedVarMap;

    @Override
    public String executeLogic(Map<String, String> codeMap, Map<String, String> variableMap) {
        storedVarMap = variableMap;
        String codeOutput = "";
        String comment = getUserInput("Comment");
        String exceptionType = getUserInput("ExceptionType");

        if (!comment.equals("")) {
            codeOutput = "//" + comment + "\n";
        }
        
        codeOutput = codeMap.get("codeTemplate1");
        codeOutput = codeOutput.replace("$ExceptionType", exceptionType);

        return codeOutput;
    }

    private String getUserInput(String variable) {
        String returnVal = storedVarMap.get(variable);
        if (returnVal == null) {
            returnVal = "";
        }
        returnVal = returnVal.trim();

        return returnVal;
    }
}
