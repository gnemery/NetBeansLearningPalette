package org.qcc.modules.learningpalette.InputOutput;

import org.qcc.modules.learningpalette.*;
import java.util.Map;

public class LP_ReadTextFromConsole implements IPaletteItemLogic {

    private Map<String, String> storedVarMap;

    @Override
    public String executeLogic(Map<String, String> codeMap, Map<String, String> variableMap) {

        storedVarMap = variableMap;
        String codeOutput = "";
        String comment = getUserInput("Comment");
        String variable = getUserInput("Variable");
        String prompt = getUserInput("Prompt");

        if (comment.length() > 0) {
            codeOutput = "//" + comment + "\n";
        }

        codeOutput = codeOutput + codeMap.get("codeTemplate1");
        codeOutput = codeOutput.replace("$Prompt", prompt);
        codeOutput = codeOutput.replace("$Variable", variable);

        return codeOutput;
    }

    /**
     * Returns the user input for a given field.
     *
     * @param variable
     * @return
     */
    private String getUserInput(String variable) {
        String returnVal = storedVarMap.get(variable);
        if (returnVal == null) {
            returnVal = "";
        }
        returnVal = returnVal.trim();

        return returnVal;
    }
}
