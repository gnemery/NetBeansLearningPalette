package org.qcc.modules.learningpalette;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;
import org.qcc.modules.learningpalette.Arrays.ArraysFileList;
import org.qcc.modules.learningpalette.CommentsJavaDoc.CommentsFileList;
import org.qcc.modules.learningpalette.Decisions.DecisionsFileList;
import org.qcc.modules.learningpalette.InputOutput.InputOutputFileList;
import org.qcc.modules.learningpalette.Loops.LoopsFileList;
import org.qcc.modules.learningpalette.ObjectsClassesMethods.OCMFileList;
import org.qcc.modules.learningpalette.Variables.VariablesFileList;

/**
 * This class handles loading of all palette items into memory, allowing for
 * insertion into palette. This palette manager is a singleton class, and may be
 * initialized by any number of languages defined by the learning palette.
 *
 * @author Tony DeLuca <adeluca6@qmail.qcc.edu>
 */
public class PaletteManager {

    private static PaletteManager instance = null;

    private ArrayList<PaletteItem> paletteItems = new ArrayList<>();
    private ArrayList<String> categoryList = new ArrayList<>();
    private ArrayList<String> languageList = new ArrayList<>();

    /**
     * *
     * Protected to prevent explicit external instantiation. Singleton class,
     * only one static instance is allowed.
     */
    protected PaletteManager() {
        reloadPaletteItems();
    }

    public void reloadPaletteItems() {
        
        //Clear the palette items array
        paletteItems.clear();
        
        //We now load all XMLs stored internally in the project:
        //Arrays
        ArrayList<String> arrays = (new ArraysFileList()).getFileList();
        for (String item : arrays) {
            try {
                InputStream in = this.getClass().getResourceAsStream("Arrays/" + item);
                PaletteItem newItem = new PaletteItem(in);
                if (newItem.wasLoadSuccessful()) {
                    paletteItems.add(newItem);
                }
                in.close();
            } catch (Exception ex) {
                //System.out.println(ex.toString());
            }
        }

        //Comments and JavaDoc
        ArrayList<String> comments = (new CommentsFileList()).getFileList();
        for (String item : comments) {
            try {
                InputStream in = this.getClass().getResourceAsStream("CommentsJavaDoc/" + item);
                PaletteItem newItem = new PaletteItem(in);
                if (newItem.wasLoadSuccessful()) {
                    paletteItems.add(newItem);
                }
                in.close();
            } catch (Exception ex) {
                //System.out.println(ex.toString());
            }
        }

        //Decisions
        ArrayList<String> decisions = (new DecisionsFileList()).getFileList();
        for (String item : decisions) {
            try {
                InputStream in = this.getClass().getResourceAsStream("Decisions/" + item);
                PaletteItem newItem = new PaletteItem(in);
                if (newItem.wasLoadSuccessful()) {
                    paletteItems.add(newItem);
                }
                in.close();
            } catch (Exception ex) {
                //System.out.println(ex.toString());
            }
        }

        //Input Output
        ArrayList<String> ioList = (new InputOutputFileList()).getFileList();
        for (String item : ioList) {
            try {
                InputStream in = this.getClass().getResourceAsStream("InputOutput/" + item);
                PaletteItem newItem = new PaletteItem(in);
                if (newItem.wasLoadSuccessful()) {
                    paletteItems.add(newItem);
                }
                in.close();
            } catch (Exception ex) {
                //System.out.println(ex.toString());
            }
        }

        //Loops
        ArrayList<String> loopsList = (new LoopsFileList()).getFileList();
        for (String item : loopsList) {
            try {
                InputStream in = this.getClass().getResourceAsStream("Loops/" + item);
                PaletteItem newItem = new PaletteItem(in);
                if (newItem.wasLoadSuccessful()) {
                    paletteItems.add(newItem);
                }
                in.close();
            } catch (Exception ex) {
                //System.out.println(ex.toString());
            }
        }
        
        //Objects Classes and Methods org.qcc.modules.learningpalette.ObjectsClassesMethods
        ArrayList<String> ocmList = (new OCMFileList()).getFileList();
        for (String item : ocmList) {
            try {
                InputStream in = this.getClass().getResourceAsStream("ObjectsClassesMethods/" + item);
                PaletteItem newItem = new PaletteItem(in);
                if (newItem.wasLoadSuccessful()) {
                    paletteItems.add(newItem);
                }
                in.close();
            } catch (Exception ex) {
                //System.out.println(ex.toString());
            }
        }
        //Variables
        ArrayList<String> variables = (new VariablesFileList()).getFileList();
        for (String item : variables) {
            try {
                InputStream in = this.getClass().getResourceAsStream("Variables/" + item);
                PaletteItem newItem = new PaletteItem(in);
                if (newItem.wasLoadSuccessful()) {
                    paletteItems.add(newItem);
                }
                in.close();
            } catch (Exception ex) {
                //System.out.println(ex.toString());
            }
        }

        //Load any user defined items.
        String saveDirectory = System.getProperty("user.dir") + "\\LearningPaletteXML";

        //Make the directory if necessary
        File newDirectory = new File(saveDirectory);
        if (!newDirectory.exists()) {
            newDirectory.mkdir();
        }

        //Load files in directory.
        File[] listOfFiles = newDirectory.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            File n = listOfFiles[i];
            if (n.isFile()) {
                String fileName = n.getName();

                if (fileName.startsWith("UserDefined_") && fileName.endsWith(".xml")) {
                    try {
                        //Read the file then parse it as an inputstream to the PaletteItem class.
                        String str = FileUtils.readFileToString(n);
                        InputStream stream = new ByteArrayInputStream(str.getBytes(StandardCharsets.UTF_8));
                        PaletteItem newItem = new PaletteItem(stream);

                        if (newItem.wasLoadSuccessful()) {
                            paletteItems.add(newItem);
                        }
                        //targetStream.close();
                    } catch (Exception ex) {
                        //This shouldn't happen.F
                    }
                }

            }
        }
    }

    /**
     * Initializes the singleton instance of this class if necessary and returns
     * it.
     *
     * @return A singleton instance of PaletteManager
     */
    public static PaletteManager getInstance() {
        if (instance == null) {
            instance = new PaletteManager();
        }
        return instance;
    }

    /**
     * Returns all palette items that have been successfully loaded from files.
     *
     * @return
     */
    public ArrayList<PaletteItem> getPaletteItems() {
        return paletteItems;
    }

}
