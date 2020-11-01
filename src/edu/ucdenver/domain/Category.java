package edu.ucdenver.domain;


import java.lang.reflect.Array;
import java.util.ArrayList;

public class Category
{
    //variables for category, eg categoryName, categoryDesc, ....
    private String id;
    private String name;
    private String description;
    //private ArrayList<Product> products;

    //This is the default constructor for Category and so will instantiate THE default category.
    public Category()
    {
        id = "0";
        name = "Default Category";
        description = "This is the default category. It encompasses all products.";
//        parentCategory = null;
//        childCategories = new ArrayList<>();
        //this.products = new ArrayList<>();
    }

    //If we want to make category but only know the id, name, and description.
    public Category (String id, String name, String description)
    {
        this.id = id;
        this.name = name;
        this.description = description;
//        this.parentCategory = null;
//        this.childCategories = new ArrayList<>();
        //this.products = new ArrayList<>();
    }

//    public Category (String id, String name, String description, ArrayList<Product> products)
//    {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.products = products;
//    }

    //If we know id, name, description, parent.
//    public Category (String id, String name, String description, Category parent)
//    {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.parentCategory = parent;
//        parent.childCategories.add(this);
//        this.childCategories = new ArrayList<>();
//        this.products = new ArrayList<>();
//    }

    //If we know id, nae, description, parent, and children.
//    public Category (String id, String name, String description, Category parent, ArrayList<Category> children)
//    {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.parentCategory = parent;
//        parent.childCategories.add(this);
//        this.childCategories = children;
//        this.products = new ArrayList<>();
//    }

//    //If we know id, nae, description, parent, children, and products.
//    public Category (String id, String name, String description, Category parent, ArrayList<Category> children,
//                     ArrayList<Product> products)
//    {
//        this.id = id;
//        this.name = name;
//        this.description = description;
//        this.parentCategory = parent;
//        parent.childCategories.add(this);
//        this.childCategories = children;
//        this.products = products;
//    }

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

//    public Category getParentCategory() {
//        return parentCategory;
//    }
//    public void setParentCategory(Category parentCategory) {
//        this.parentCategory = parentCategory;
//    }
//
//    public void addChildCategory(Category childCategory) {
//        childCategories.add(childCategory);
//    }
//    //if im thinking of this right, this can just be set to null, as it will only take effect
//    // on the selected category in the GUI, rather than setting all child categories = null(?)
//    public void removeChildCategory(Category childCategory) {
//        childCategories.remove(childCategory);
//    }

//    public void addProducts(ArrayList<Product> products)
//    {
//        this.products = products;
//    }
//
//    public void addProduct(Product product)
//    {
//        this.products.add(product);
//    }
//
//    public void removeProduct (Product product)
//    {
//        this.removeProduct(product);
//    }
}
