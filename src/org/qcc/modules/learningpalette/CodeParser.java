/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.qcc.modules.learningpalette;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * This class parses the code for a given text file, returning a list of all
 * declared variables.
 *
 * @author adeluca
 */
public class CodeParser {

    private final Map<String, String> variableList;
    private final String language;

    public CodeParser(String code, String language) {
        variableList = new HashMap<>();
        this.language = language;
        parseCode(code);
    }

    private void parseCode(String code) {
        if (code == null) {
            return;
        }

        String[] codeArray = code.split(" ");

        for (int i = 0; i < codeArray.length; i++) {
            if (matchesKeyword(codeArray[i])) {
                //The next item is the variable name.
                if (i == codeArray.length - 1) {
                    return;
                }

                String varName = codeArray[i + 1];
                varName = isValidVariableName(varName);
                if (varName.equals("") == false) {
                    variableList.put(varName, ""); //can potentially specify the type here instead of "" later on.
                }
            }
        }
    }

    private boolean matchesKeyword(String codeSegment) {
        ArrayList<String> keywordArray = new ArrayList<>();

        if (this.language.equals("text/x-c++")) {
            //Boolean Byte Char Double Float Integer Long Short String
            keywordArray.add("\\bbool\\b");
            keywordArray.add("bool\\[.*]");
            keywordArray.add("\\bbyte\\b");
            keywordArray.add("byte\\[.*]");
            keywordArray.add("\\bchar\\b");
            keywordArray.add("char\\[.*]");
            keywordArray.add("\\bdouble\\b");
            keywordArray.add("double\\[.*]");
            keywordArray.add("\\bfloat\\b");
            keywordArray.add("float\\[.*]");
            keywordArray.add("\\bint\\b");
            keywordArray.add("int\\[.*]");
            keywordArray.add("\\blong\\b");
            keywordArray.add("long\\[.*]");
            keywordArray.add("\\bshort\\b");
            keywordArray.add("short\\[.*]");
            keywordArray.add("\\bstring\\b");
            keywordArray.add("string\\[.*]");

        } else { //Java is the default language
            //Boolean Byte Char Double Float Integer Long Short String
            keywordArray.add("\\bboolean\\b");
            keywordArray.add("boolean\\[.*]");
            keywordArray.add("\\bbyte\\b");
            keywordArray.add("byte\\[.*]");
            keywordArray.add("\\bchar\\b");
            keywordArray.add("char\\[.*]");
            keywordArray.add("\\bdouble\\b");
            keywordArray.add("double\\[.*]");
            keywordArray.add("\\bfloat\\b");
            keywordArray.add("float\\[.*]");
            keywordArray.add("\\bint\\b");
            keywordArray.add("int\\[.*]");
            keywordArray.add("\\blong\\b");
            keywordArray.add("long\\[.*]");
            keywordArray.add("\\bshort\\b");
            keywordArray.add("short\\[.*]");
            keywordArray.add("\\bString\\b");
            keywordArray.add("String\\[.*]");
        }

        for (int i = 0; i < keywordArray.size(); i++) {
            if (codeSegment.matches(keywordArray.get(i))) {
                return true;
            }
        }

        return false;
    }

    private String isValidVariableName(String codeSegment) {
        //Remove new lines, ending semicolons, parenthesis.
        codeSegment = codeSegment.replace("\r", " ");
        codeSegment = codeSegment.replace("\n", " ");
        codeSegment = codeSegment.replace("\t", " ");
        codeSegment = codeSegment.trim();

        if (codeSegment.endsWith(";")) {
            codeSegment = replaceLast(codeSegment, ";", " ");
        }

        if (codeSegment.startsWith("(")) {
            codeSegment = codeSegment.replaceFirst("(", " ");
        }
        codeSegment = codeSegment.trim();

        if (codeSegment.matches("^[a-zA-Z][a-zA-Z0-9]*?$")) {
            return codeSegment;
        }
        return "";

    }

    public static String replaceLast(String text, String regex, String replacement) {
        return text.replaceFirst("(?s)" + regex + "(?!.*?" + regex + ")", replacement);
    }

    public ArrayList<String> getVariableList() {
        //We convert the map to an arraylist. We are using a map simply because it will ignore duplicate entries, making our life easier.
        //since we will not have to loop through to verify an entry does not already exist.
        ArrayList<String> variables = new ArrayList<>();

        Iterator it = variableList.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            variables.add((String) pair.getKey());
            it.remove(); // avoids a ConcurrentModificationException
        }

        Collections.sort(variables);

        return variables;
    }

}
