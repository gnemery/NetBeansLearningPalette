package org.qcc.modules.learningpalette.Arrays;

import org.qcc.modules.learningpalette.*;
import java.util.Map;

public class LP_CharArray implements IPaletteItemLogic {

    private Map<String, String> storedVarMap;

    @Override
    public String executeLogic(Map<String, String> codeMap, Map<String, String> variableMap) {

        storedVarMap = variableMap;

        String codeOutput = "";
        String varName = getUserInput("VariableName");
        String arraySize = getUserInput("ArraySize");
        String initValue = getUserInput("InitializeValue");
        String comment = getUserInput("Comment");

        if (!comment.equals("")) {
            codeOutput = "//" + comment + "\n";
        }
        //<codeTemplate1>char $varName[$size];</codeTemplate1>
        //<codeTemplate2>char $varName[] = {$initializeValue};</codeTemplate2>
        if (initValue.length() > 0) {
            //An initializion value was received.
            codeOutput += codeMap.get("codeTemplate3");
            String[] splitInit = initValue.split("(?<!^)");
            String outputInit = "";
            for (String s : splitInit) {
                String charVal = s.trim();
                if (charVal.length() > 0) {
                    if (outputInit.length() > 0) {
                        outputInit = outputInit + ", ";
                    }
                    outputInit = outputInit + "'" + charVal + "'";
                }
            }

            codeOutput = codeOutput.replace("$initializeValue", outputInit);

        } else {
            //A Size (Or no size) was received.
            if (arraySize.length() > 0) {
                codeOutput += codeMap.get("codeTemplate2");
            } else {
                codeOutput += codeMap.get("codeTemplate1");
            }

            codeOutput = codeOutput.replace("$size", arraySize);
        }

        codeOutput = codeOutput.replace("$varName", varName);

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
