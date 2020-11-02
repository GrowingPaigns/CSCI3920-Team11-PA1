package edu.ucdenver.domain;

import java.time.LocalDate;
import java.util.ArrayList;

public class Catalog
{
    private ArrayList<Product> products;
    private CategoryNode categoryTree;
    private Category defaultCategory;

    public Catalog()
    {
        products = new ArrayList<>();
        categoryTree = new CategoryNode(new Category());
        this.defaultCategory = categoryTree.getData();
    }

    public Catalog (Category defaultCategory)
    {
        products = new ArrayList<>();
        categoryTree = new CategoryNode(defaultCategory);
        this.defaultCategory = categoryTree.getData();
    }

    public Catalog (ArrayList<Product> products, Category defaultCategory)
    {
        this.products = products;
        categoryTree = new CategoryNode(defaultCategory);
        this.defaultCategory = categoryTree.getData();
    }

    public Category getDefaultCategory() {return this.defaultCategory;}
    public void setDefaultCategory(Category defaultCategory){
        this.categoryTree.setParent(defaultCategory);
        this.categoryTree = this.categoryTree.getParent();
        this.defaultCategory = categoryTree.getData();
    }

    public boolean addProduct(Product product)
    {
        boolean success = false;

        if (searchProduct(product) == null) {
            this.products.add(product);
            success = true;
        }
        return success;
    }

    public boolean removeProduct (Product product)
    {
        boolean success = false;
        if (searchProduct(product) != null) {
            success = true;
            this.products.remove(product);
        }
        return success;
    }

    public ArrayList<Product> searchProduct(String searchTerm)
    {
        ArrayList<Product> searchResults = new ArrayList<>();
        for (Product product: products)
        {
            if (product.getName().contains(searchTerm) || product.getBrandName().contains(searchTerm)
                || product.getDescription().contains(searchTerm))
            {
                searchResults.add(product);
            }
        }

        return searchResults;
    }

    public Product searchProduct(Product product)
    {
        Product searchResults = null;
        for (Product p: products)
        {
            if (p.getId().equals(product.getId()))
            {
                searchResults = product;
            }
        }

        return searchResults;
    }

    public CategoryNode getCategoryTree () {return this.categoryTree;}

    public void addCategory (Category category) {this.categoryTree.addChild(category);}
    public boolean addCategory (Category parentCategory, Category childCategory) {
        boolean success = false;
        CategoryNode parent = this.categoryTree.search(this.categoryTree, parentCategory);
        if (parent != null) {
            parent.addChild(childCategory);
            success = true;
        }
        return success;
    }
    public boolean removeCategory (Category category) {
        boolean success = false;
        CategoryNode nodeToRemove = this.categoryTree.search(this.categoryTree, category);
        if (nodeToRemove != null) {
            for (CategoryNode node: nodeToRemove.getChildren())
                node.setParent(node.getParent());
            nodeToRemove.removeParent();
            success = true;
        }
        return success;
    }

    public ArrayList<Product> getProducts(){return this.products;}
}
