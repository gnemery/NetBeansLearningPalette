package org.qcc.modules.learningpalette;

import java.lang.reflect.Method;
import javax.swing.Action;
import org.netbeans.api.editor.mimelookup.MimeRegistration;
import org.netbeans.api.editor.mimelookup.MimeRegistrations;
import org.netbeans.spi.palette.DragAndDropHandler;
import org.netbeans.spi.palette.PaletteActions;
import org.netbeans.spi.palette.PaletteController;
import org.netbeans.spi.palette.PaletteFactory;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.Children;
import org.openide.util.Exceptions;
import org.openide.util.Lookup;
import org.openide.util.datatransfer.ExTransferable;

/**
 * Factory class that builds a palette and all items associated with it.
 *
 * @author Tony
 */
public class SourceFilePaletteFactory {

    public static AbstractNode paletteRoot;
    public static PaletteController paletteController;
    public static CategoryChildFactory catChildFactory;

    /**
     * createPalette generates a palette controller for this learning palette.
     *
     * @return The controller object.
     */
    @MimeRegistrations({
        @MimeRegistration(mimeType = "text/x-java", service = PaletteController.class),
        @MimeRegistration(mimeType = "text/x-c++", service = PaletteController.class),})
    public static PaletteController createPalette() {

        try {

            //Load palette items into memory if necessary.
            PaletteManager loadPalette = PaletteManager.getInstance();

            //Build a palette for each language we have loaded.
            if (paletteRoot == null) {
                catChildFactory = new CategoryChildFactory(loadPalette, "JAVA");
                paletteRoot = new AbstractNode(Children.create(catChildFactory, true));
                paletteRoot.setName("LearningPalette");
            }

            if (paletteController == null) {
                paletteController = PaletteFactory.createPalette(paletteRoot, new MyActions(), null,
                        //Drag and Drop Handler:  
                        new DragAndDropHandler(true) {
                            @Override
                            public void customize(ExTransferable et, Lookup lkp) {

                            }
                        });
            } else {
                //We assign the paletteRoot to the existing controller.
                paletteController.refresh();
            }

            return paletteController;
        } catch (Exception ex) {
            Exceptions.printStackTrace(ex);
        }
        return null;
    }

    private static class MyActions extends PaletteActions {

        @Override
        public Action[] getImportActions() {
            return null;
        }

        @Override
        public Action[] getCustomPaletteActions() {
            return null;
        }

        @Override
        public Action[] getCustomCategoryActions(Lookup lookup) {
            return null;
        }

        @Override
        public Action[] getCustomItemActions(Lookup lookup) {
            return null;
        }

        @Override
        public Action getPreferredAction(Lookup lookup) {
            return null;
        }
    }
}
