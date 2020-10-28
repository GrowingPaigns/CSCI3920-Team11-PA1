package edu.ucdenver.domain;

import java.util.ArrayList;

public class Category {
    //variables for category, eg categoryName, categoryDesc, ....
    private String id;
    private String name;
    private String description;
    private Category parentCategory;
    private ArrayList<Category> childCategories;

    public String getId(){
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getParentCategory() {
        return parentCategory;
    }

    public void setParentCategory(Category parentCategory) {
        this.parentCategory = parentCategory;
    }

    public void addChildCategory(Category childCategory) {
        childCategories.add(childCategory);
    }

    //if im thinking of this right, this can just be set to null, as it will only take effect
    // on the selected category in the GUI, rather than setting all child categories = null(?)
    public void removeChildCategory(Category childCategory) {
        childCategories.remove(childCategory);
    }
}
