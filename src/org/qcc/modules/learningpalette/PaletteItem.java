package org.qcc.modules.learningpalette;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.text.BadLocationException;
import javax.swing.text.JTextComponent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.openide.text.ActiveEditorDrop;
import org.qcc.modules.learningpalette.Customizer.CustomizerComboField;
import org.qcc.modules.learningpalette.Customizer.CustomizerControl;
import org.qcc.modules.learningpalette.Customizer.CustomizerExpressionField;
import org.qcc.modules.learningpalette.Customizer.CustomizerTextField;
import org.qcc.modules.learningpalette.Customizer.CustomizerVariableField;
import org.qcc.modules.learningpalette.Customizer.LP_Customizer;
import org.qcc.modules.learningpalette.InMemoryJavaCompiler.InMemoryJavaCompiler;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import sun.security.ssl.Debug;

/**
 * This class defines a palette item that can be loaded into the NetBeans
 * Palette.
 *
 * @author Tony
 */
public class PaletteItem implements ActiveEditorDrop {

    private String category;
    private String displayName;
    private String toolTip;
    private String icon;
    private String fileName; //used for user defined files.
    private String example;

    private Map<String, Map<String, String>> languageDefinitions;
    private IPaletteItemLogic itemLogic;
    private ArrayList<CustomizerControl> customizerControls;

    private boolean loadSucessful = false; //Set to true once loading completes.

    public PaletteItem(InputStream xmlFile) {

        languageDefinitions = new HashMap<String, Map<String, String>>();
        customizerControls = new ArrayList<>();

        //First load all the basic details for this palette item.
        //Prepare DOM
        try {

            //Load the XML File
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = docBuilder.parse(xmlFile);

            //Initialize XPath for this DOM object.
            XPath xPath = XPathFactory.newInstance().newXPath();

            //Pull required information:
            category = ((Node) xPath.evaluate("//PaletteItem/Category", doc, XPathConstants.NODE)).getTextContent();
            displayName = ((Node) xPath.evaluate("//PaletteItem/DisplayName", doc, XPathConstants.NODE)).getTextContent();
            toolTip = ((Node) xPath.evaluate("//PaletteItem/Tooltip", doc, XPathConstants.NODE)).getTextContent();
            //Pull optional icon
            try {
                icon = ((Node) xPath.evaluate("//PaletteItem/Icon", doc, XPathConstants.NODE)).getTextContent();
            } catch (Exception ex) {
                //This isn't an exception.
                icon = "";
            }

            //Pull optional example
            Node exampleNode = (Node) xPath.evaluate("//PaletteItem/Example", doc, XPathConstants.NODE);
            if (exampleNode != null) {
                example = exampleNode.getTextContent();
            } else {
                example = "";
            }

            //If "PaletteLogicClass" exists, we reference that already existing class for our logic.
            //Otherwise we compile the provided "CreateBodyLogic"
            Node paletteLogicClass = (Node) xPath.evaluate("//PaletteItem/PaletteLogicClass", doc, XPathConstants.NODE);
            if (paletteLogicClass == null) {
                return;

            } else {
                //A class has been referenced, lets load an instance of it.
                String fullClassName = paletteLogicClass.getTextContent();
                IPaletteItemLogic newObject = (IPaletteItemLogic) Class.forName(fullClassName).newInstance();
                itemLogic = newObject;
            }

            //Pull list of languages for this palette item
            //Generate a hashmap <String,String> for each language.
            NodeList languages = (NodeList) xPath.evaluate("//PaletteItem/Languages/Language", doc, XPathConstants.NODESET);

            //Add all Key/Value pairs to the codeMap.
            for (int i = 0; i < languages.getLength(); i++) {
                Node n = languages.item(i);

                //Get the specified language id.
                String language = n.getAttributes().getNamedItem("id").getTextContent();

                //Build a HashMap for this language.
                Map<String, String> codeMap = new HashMap<String, String>();

                //Get child nodes, which are the key/value pairs for this hashmap.
                NodeList codeItems = n.getChildNodes();
                //Add all key/value pairs to the codeMap hashmap.
                for (int j = 0; j < codeItems.getLength(); j++) {
                    Node codeItem = codeItems.item(j);

                    if (codeItem.getNodeType() != 1) {
                        continue;
                    }

                    String key = codeItem.getNodeName();
                    String value = codeItem.getTextContent();
                    codeMap.put(key, value);
                }

                //Finally, add this language to the stored languageDefinitions.
                languageDefinitions.put(language, codeMap);

            }

            //Load all customizer controls for this palette item.
            NodeList customizerControlList = (NodeList) xPath.evaluate("//PaletteItem/Customizer/Item", doc, XPathConstants.NODESET);
            for (int i = 0; i < customizerControlList.getLength(); i++) {
                {
                    Node n = customizerControlList.item(i);
                    String controlType = n.getAttributes().getNamedItem("type").getTextContent();
                    String controlName = n.getAttributes().getNamedItem("name").getTextContent();
                    String controlLabel = n.getAttributes().getNamedItem("label").getTextContent();
                    String controlValue = n.getAttributes().getNamedItem("value").getTextContent();

                    if (controlType.equals("Text")) {
                        CustomizerTextField newControl = new CustomizerTextField(controlName, controlLabel, controlValue);
                        customizerControls.add(newControl);
                    } else if (controlType.equals("Combo")) {
                        //First we need to create an ArrayList<String> of values for this. 
                        ArrayList<String> comboValueList = new ArrayList<>();

                        NodeList comboValues = n.getChildNodes();
                        for (int j = 0; j < comboValues.getLength(); j++) {
                            Node comboValue = comboValues.item(j);
                            if (comboValue.getNodeType() == 1) {
                                comboValueList.add(comboValue.getTextContent());
                            }
                        }
                        CustomizerComboField newControl = new CustomizerComboField(controlName, controlLabel, comboValueList);
                        customizerControls.add(newControl);
                    } else if (controlType.equals("Variable")) {
                        CustomizerVariableField newControl = new CustomizerVariableField(controlName, controlLabel, controlValue);
                        customizerControls.add(newControl);
                    } else if (controlType.equals("Expression")) {
                        CustomizerExpressionField newControl = new CustomizerExpressionField(controlName, controlLabel, controlValue);
                        customizerControls.add(newControl);
                    }
                }
            }

            //Flag that loading of this palette item was sucessful, allowing it to be displayed.
            loadSucessful = true;

        } catch (Exception ex) {
            //TODO: Exception handling. Find a way to notify the end-user that the palette item failed to load.
            Debug.println(ex.toString(), ex.toString());
        }

    }

    /**
     * Constructor used to create a basic palette item in the options menu.
     */
    public PaletteItem() {
        customizerControls = new ArrayList<>();
        category = "User Defined";
        displayName = "";
        toolTip = "Description";

        this.loadSucessful = true;
    }

    public void setName(String displayName) {
        this.displayName = displayName;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setToolTip(String toolTip) {
        this.toolTip = toolTip;
    }

    public boolean wasLoadSuccessful() {
        return loadSucessful;
    }

    @Override
    public boolean handleTransfer(JTextComponent targetComponent) {

        //Step 1: Get the language of the target editor.
        String language = LearningPaletteUtilities.getLanguage(targetComponent);

        //Step 2: Get all coding information for this template. 
        Map<String, String> codeMap = getCodeMap(language);

        //Step 3: If a customizer is necessary, we generate a variableMap.
        //This stores all values from a customizer in a K,V Map.
        Map<String, String> variableMap = new HashMap<String, String>();

        //Create a customizer if necessary.
        LP_Customizer myCustomizer;
        if (customizerControls.size() > 0) {
            myCustomizer = new LP_Customizer(this.displayName, this.toolTip, this.example, customizerControls, targetComponent);
            boolean accept = myCustomizer.showDialog();
            if (accept == false) {
                return false;
            }

            variableMap = myCustomizer.getVariableMap();
        }

        //First generate the body to send
        String body = itemLogic.executeLogic(codeMap, variableMap);

        try {
            LearningPaletteUtilities.insert(body, targetComponent);
            return true;
        } catch (BadLocationException ex) {
            return false;
        }
    }

    /**
     * Returns if this palette item supports the given language.
     *
     * @param string The language to check if it is supported or not.
     * @return true or false if the item is supported or not.
     */
    public boolean isLanguageSupported(String language) {

        Map<String, String> languageCheck = languageDefinitions.get(language);
        return languageCheck != null;

    }

    /**
     * Gets the category for the given palette item
     *
     * @return
     */
    public String getCategory() {
        return category;
    }

    /**
     * Gets the display name for the given palette item.
     *
     * @return
     */
    public String getItemName() {
        return displayName;
    }

    /**
     * Gets the tooltip for the given palette item.
     *
     * @return
     */
    public String getToolTip() {
        return toolTip;
    }

    public String getIcon() {
        return this.icon;
    }

    public String getExample() {
        return this.example;
    }

    public Map<String, String> getCodeMap(String language) {
        return languageDefinitions.get(language);
    }

    private static void appendLine(StringBuilder sb, String line) {
        sb.append(line);
        sb.append("\n");
    }

    @Override
    public String toString() {
        return this.getItemName();
    }

    public ArrayList<CustomizerControl> getCustomizerControls() {
        return this.customizerControls;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileName() {
        return this.fileName;
    }

}
