package org.qcc.modules.learningpalette;

import java.util.ArrayList;
import java.util.List;
import org.openide.nodes.AbstractNode;
import org.openide.nodes.ChildFactory;
import org.openide.nodes.Children;
import org.openide.nodes.Node;

public class CategoryChildFactory extends ChildFactory<Category> {

    ArrayList<PaletteItem> paletteItems;
    String currentLanguage;

    public CategoryChildFactory(PaletteManager paletteItems, String language) {
        this.currentLanguage = language;
        this.paletteItems = paletteItems.getPaletteItems();
    }

    public void refreshNow() {
        this.refresh(true);
    }

    @Override
    protected boolean createKeys(List<Category> list) {
        ArrayList<String> categoryList = new ArrayList<>();
        //Build a list of categories.
        for (PaletteItem item : paletteItems) {
            if (item.isLanguageSupported(currentLanguage)) {
                boolean foundDuplicate = false;
                for (int i = 0; i < categoryList.size(); i++) {
                    if (categoryList.get(i).equals(item.getCategory())) {
                        foundDuplicate = true;
                        break;
                    }
                }

                if (foundDuplicate == false) {
                    categoryList.add(item.getCategory());
                }
            }
        }

        for (String item : categoryList) {
            list.add(new Category(item));
        }
        return true;
    }

    @Override
    protected Node createNodeForKey(Category category) {
        return new CategoryNode(category);
    }

    public class CategoryNode extends AbstractNode {

        public CategoryNode(Category category) {
            super(Children.create(new PaletteItemFactory(category, paletteItems, currentLanguage), true));
            setDisplayName(category.getName());
        }
    }

}
