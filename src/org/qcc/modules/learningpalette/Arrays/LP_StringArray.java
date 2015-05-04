package org.qcc.modules.learningpalette.Arrays;

import java.util.ArrayList;
import java.util.List;
import org.qcc.modules.learningpalette.*;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LP_StringArray implements IPaletteItemLogic {

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

        if (initValue.length() > 0) {
            //An initializion value was received.
            codeOutput += codeMap.get("codeTemplate3");
            String[] splitInit = parseCommand(initValue);
            String outputInit = "";
            for (String s : splitInit) {
                String intVal = s.trim();
                if (outputInit.length() > 0) {
                    outputInit = outputInit + ", ";
                }
                outputInit = outputInit + intVal;
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

    private static String[] parseCommand(String cmd) {
        if (cmd == null || cmd.length() == 0) {
            return new String[]{};
        }

        cmd = cmd.trim();
        String regExp = "\"(\\\"|[^\"])*?\"|[^ ]+";
        Pattern pattern = Pattern.compile(regExp, Pattern.MULTILINE | Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(cmd);
        List< String> matches = new ArrayList<String>();
        while (matcher.find()) {
            matches.add(matcher.group());
        }
        String[] parsedCommand = matches.toArray(new String[]{});
        return parsedCommand;
    }

}
