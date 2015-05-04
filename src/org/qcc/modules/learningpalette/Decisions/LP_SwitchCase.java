package org.qcc.modules.learningpalette.Decisions;

import org.qcc.modules.learningpalette.*;
import java.util.Map;

public class LP_SwitchCase implements IPaletteItemLogic {

    private Map<String, String> storedVarMap;

    @Override
    public String executeLogic(Map<String, String> codeMap, Map<String, String> variableMap) {

        storedVarMap = variableMap;
        String codeOutput = "";
        String comment = getUserInput("Comment");
        String variable = getUserInput("Variable");
        String caseNum = getUserInput("CaseCount");

        int caseCount = 0;

        try {
            caseCount = Integer.parseInt(caseNum);
        } catch (Exception ex) {
            caseCount = 2;
        }

        //Max value.
        if (caseCount <= 0) {
            caseCount = 2;
        } else if (caseCount > 10) {
            caseCount = 10;
        }

        if (comment.length() > 0) {
            codeOutput = "//" + comment + "\n";
        }

        String buildCases = "";
        String caseTemplate = codeMap.get("caseTemplate");
        for (int i = 0; i < caseCount; i++) {
            if (i > 0) {
                buildCases = buildCases + "\n";
            }
            buildCases = buildCases + caseTemplate.replace("$number", i + "");
        }
        

        codeOutput = codeOutput + codeMap.get("codeTemplate1");
        codeOutput = codeOutput.replace("$Variable", variable);
        codeOutput = codeOutput.replace("$Cases", buildCases);
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
