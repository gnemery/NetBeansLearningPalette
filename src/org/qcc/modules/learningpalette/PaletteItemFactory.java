package org.qcc.modules.learningpalette;

import java.awt.datatransfer.Transferable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;
import org.openide.text.ActiveEditorDrop;
import org.openide.util.Lookup;
import org.openide.util.datatransfer.ExTransferable;

public class PaletteItemFactory extends ChildFactory<PaletteItem> {

    private final Category category;
    private ArrayList<PaletteItem> paletteItems;
    private String language;

    PaletteItemFactory(Category category, ArrayList<PaletteItem> paletteItems, String language) {
        this.category = category;
        this.paletteItems = paletteItems;
        this.language = language;
    }

    @Override
    protected boolean createKeys(List<PaletteItem> list) {
        for (PaletteItem item : paletteItems) {
            if (item.isLanguageSupported(language) && item.getCategory().equals(category.getName())) {
                list.add(item);
            }
        }
        return true;
    }

    @Override
    protected Node createNodeForKey(PaletteItem key) {
        return new PaletteItemNode(key);
    }

    public class PaletteItemNode extends AbstractNode {

        private PaletteItem nodeItem;

        public PaletteItemNode(PaletteItem key) {
            super(Children.LEAF);
            nodeItem = key;
            setName("" + this);
            setDisplayName(key.getItemName());
            if (key.getIcon().equals("") == false) {
                setIconBaseWithExtension(key.getIcon());
            }
            //setIconBaseWithExtension(key.getImage());
            //setIconBaseWithExtension("org/qcc/modules/learningpalette/image3.png");

        }

        @Override
        public Transferable clipboardCopy() throws IOException {

            ExTransferable t = ExTransferable.create(super.clipboardCopy());

            Lookup lookup = getLookup();

            //We need to find the PaletteItem associated with this node.
            ActiveEditorDrop drop = (ActiveEditorDrop) nodeItem;//lookup.lookup(ActiveEditorDrop.class);
            ActiveEditorDropTransferable s = new ActiveEditorDropTransferable(drop);
            t.put(s);

            return t;
        }

        @Override
        public Transferable drag() throws IOException {
            return clipboardCopy();
        }

    }

    private static class ActiveEditorDropTransferable extends ExTransferable.Single {

        private ActiveEditorDrop drop;

        ActiveEditorDropTransferable(ActiveEditorDrop drop) {
            super(ActiveEditorDrop.FLAVOR);

            this.drop = drop;
        }

        public Object getData() {
            return drop;
        }

    }

}
