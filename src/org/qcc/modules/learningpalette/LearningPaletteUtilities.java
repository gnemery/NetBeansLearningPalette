package org.qcc.modules.learningpalette;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import javax.swing.text.BadLocationException;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.JTextComponent;
import javax.swing.text.StyledDocument;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import org.netbeans.modules.editor.NbEditorUtilities;
import org.netbeans.modules.editor.indent.api.Reformat;
import org.openide.text.NbDocument;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * *
 * This class provides support to the palette items, and inserts the code into
 * an editor.
 *
 * @author Tony
 */
public class LearningPaletteUtilities {

    public static void insert(final String s, final JTextComponent target) throws BadLocationException {

        final StyledDocument doc = (StyledDocument) target.getDocument();

        class AtomicChange implements Runnable {

            @Override
            public void run() {
                Document value = target.getDocument();
                if (value == null) {
                    return;
                }
                try {
                    insert(s, target, doc);
                    formatDocument(doc);
                } catch (BadLocationException e) {
                }
            }
        }

        try {
            NbDocument.runAtomicAsUser(doc, new AtomicChange());
        } catch (BadLocationException ex) {
        }

    }

    private static int insert(String s, JTextComponent target, Document doc) throws BadLocationException {

        int start = -1;

        try {

            //firstly, find selected text range:
            Caret caret = target.getCaret();
            int p0 = Math.min(caret.getDot(), caret.getMark());
            int p1 = Math.max(caret.getDot(), caret.getMark());
            doc.remove(p0, p1 - p0);

            //then, replace selected text range with the inserted one:
            start = caret.getDot();
            doc.insertString(start, s, null);
            formatDocument(doc);

        } catch (BadLocationException ble) {
        }

        return start;

    }

    /**
     * Code beautification engine built into NetBeans
     *
     * @param doc
     */
    private static void formatDocument(Document doc) {
        Reformat reformat = Reformat.get(doc);
        reformat.lock();
        try {

            try {
                reformat.reformat(0, doc.getLength() - 1);
            } catch (Exception ex) {
            }
        } finally {
            reformat.unlock();
        }
    }

    /**
     * *
     * A function to return the language of the editor based on the file being
     * edited.
     *
     * @param editorName The title of the editor.
     * @return The language being used.
     */
    public static String getLanguage(JTextComponent targetComponent) {

        String mimeExtension = NbEditorUtilities.getMimeType(targetComponent);

        if (mimeExtension.equals("text/x-java")) {
            return "JAVA";
        } else if (mimeExtension.equals("text/x-c++")) {
            return "CPP";
        } else {
            return "";
        }

    }

    /**
     * *
     * Returns the code template for the given palette item for the given
     * language.
     *
     * @param button The button that was used for this request.
     * @param language The language used for this request. "CPP", "JAVA" etc.
     * @return The code template to use.
     */
    public static Map<String, String> getCodeMap(Object callingClass, String language) {

        //Read the XML File.
        //First parse out the class name without package information.
        String className = callingClass.getClass().getName();
        String packageName = "";
        if (className.contains(".")) {
            //Grab everything before the last ".", aka the package name.
            packageName = className.substring(0, className.lastIndexOf("."));

            //Grab everything after the last "." in the classes name.
            className = className.substring(className.lastIndexOf(".") + 1);
        }

        //Grab codeDefinitions for specified lookup.
        String xmlPath = packageName.replace(".", "/");
        xmlPath = xmlPath + "/codeDefinitions.xml";

        ClassLoader classLoader = callingClass.getClass().getClassLoader();
        InputStream in = classLoader.getResourceAsStream(xmlPath);

        //Create codeMap hash map.
        Map<String, String> codeMap = new HashMap<String, String>();

        //Prepare DOM
        try {
            DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
            org.w3c.dom.Document doc = docBuilder.parse(in);

            XPath xPath = XPathFactory.newInstance().newXPath();
            NodeList nodes = (NodeList) xPath.evaluate("//code/" + className + "/" + language + "/*", doc, XPathConstants.NODESET);

            //Add all Key/Value pairs to the codeMap.
            for (int i = 0; i < nodes.getLength(); i++) {
                Node n = nodes.item(i);
                String key = n.getNodeName();
                String value = n.getTextContent();
                codeMap.put(key, value);
            }

        } catch (Exception ex) {
            //TODO: Exception handling.
        }

        return codeMap;
    }

}
