package edu.ucdenver.domain;

import javafx.collections.ObservableListBase;
import javafx.scene.control.TreeItem;


public class CategoryTree
{
    private javafx.scene.control.TreeItem<Category> root;

    public CategoryTree(Category rootData) {
        root = new TreeItem<>();
        root.setValue(rootData);
    }

    public boolean addItem (Category item)
    {
        boolean success = false;

        if (search(this.root, item) == null) {
            TreeItem<Category> newItem = new TreeItem<Category>();
            newItem.setValue(item);
            root.getChildren().add(newItem);
            success = true;
        }

        return success;
    }

    public boolean addItemUnderItem (Category newItem, Category item)
    {
        boolean success = false;
        javafx.scene.control.TreeItem<Category> itemToAdd = search(this.root,newItem);
        javafx.scene.control.TreeItem<Category> itemParent = search(this.root, item);

        if (itemToAdd == null && itemParent != null)
        {
            itemToAdd = new TreeItem<>();
            itemToAdd.setValue(newItem);
            itemParent.getChildren().add(itemToAdd);
            success = true;
        }

        return success;
    }

    public boolean removeItem (Category item)
    {
        boolean success = false;
        javafx.scene.control.TreeItem<Category> itemToRemove = search(this.root, item);

        if(itemToRemove!= null)
        {
            itemToRemove.getParent().getChildren().addAll(itemToRemove.getChildren());
            itemToRemove.getParent().getChildren().remove(itemToRemove);
            success = true;
        }
        return success;
    }

    public TreeItem<Category> search (TreeItem<Category> root, Category item)
    {
        javafx.scene.control.TreeItem<Category> result = null;

        if (root.getValue().getId().equals(item.getId()))
            result = root;
        else
        {
            for (javafx.scene.control.TreeItem<Category> each: root.getChildren())
                result = search(each, item);
        }
        return result;
    }

    public TreeItem<Category> getRoot()
    {
        return this.root;
    }

    public void setRoot (Category rootData)
    {
        this.root.setValue(rootData);
    }

//    public static class javafx.scene.control.TreeItem<Category> {
//        private Category data;
//        private TreeItem<Category> parent;
//        private List<TreeItem<Category>> children;
//    }
}
