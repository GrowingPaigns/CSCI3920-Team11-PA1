package edu.ucdenver.domain;

import java.io.Serializable;
import java.util.ArrayList;


public class CategoryNode implements Serializable{
    private Category data = null;
    private CategoryNode parent = null;
    private ArrayList<CategoryNode> children = new ArrayList<>();

    public CategoryNode(Category data)
    {
        this.data = data;
    }

    public CategoryNode(Category data, CategoryNode parent)
    {
        this.data = data;
        this.parent = parent;
    }

    public ArrayList<CategoryNode> getChildren(){return this.children;}

    public void addChild (CategoryNode child)
    {
        child.setParent(this);
        this.children.add(child);
    }
    public void addChild (Category data)
    {
        CategoryNode child = new CategoryNode(data);
        child.setParent(this);
        this.children.add(child);
    }

    public Category getData(){return this.data;}
    public void setData(Category data){this.data = data;}

    public boolean isRoot(){return (this.parent == null);}

    public CategoryNode getParent(){return this.parent;}
    public void setParent(CategoryNode parent){
        parent.addChild(this);
        this.parent = parent;
    }

    public void setParent(Category parentData){
        CategoryNode parent = new CategoryNode(parentData);
        parent.addChild(this);
        this.parent = parent;
    }

    public boolean isLeaf(){
        return this.children.isEmpty();
    }

    public void removeParent() {
        this.parent = null;
    }

    public CategoryNode search (CategoryNode root, Category child)
    {
        CategoryNode result = null;
        if (root.getData().getId().equals(child.getId()))
             result = root;
        else {
            for (CategoryNode node : this.children)
                result = search(node, child);
        }
        return result;
    }

    public ArrayList<Category> toArrayList (CategoryNode tree)
    {
        ArrayList<Category> arrayList = new ArrayList<>();
        arrayList.add(tree.getData());

        if (!tree.isLeaf())
        {
            for (CategoryNode node: tree.getChildren())
                arrayList.addAll(toArrayList(node));
            return arrayList;
        }
        else
            return arrayList;
    }
}

