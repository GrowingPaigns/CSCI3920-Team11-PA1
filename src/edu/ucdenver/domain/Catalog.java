package edu.ucdenver.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class Catalog implements Serializable
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
    public boolean setDefaultCategory(Category defaultCategory){
        boolean success = false;
        CategoryNode nodeToMakeDefault = this.categoryTree.search(this.categoryTree, defaultCategory);
        if (nodeToMakeDefault != null) {
            this.categoryTree.removeNode(nodeToMakeDefault.getData());
            this.categoryTree.setData(nodeToMakeDefault.getData());
            this.defaultCategory = categoryTree.getData();
            success = true;
        }
        return success;
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
        Product productToRemove = searchProduct(product);
        if (productToRemove != null) {
            success = true;
            this.products.remove(productToRemove);
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
                searchResults = p;
            }
        }

        return searchResults;
    }

    public CategoryNode getCategoryTree () {return this.categoryTree;}

    public void addCategory (Category category) {this.categoryTree.addChild(category);}
    public boolean addCategory (Category childCategory, Category parentCategory) {
        boolean success = false;
        CategoryNode parent = this.categoryTree.search(this.categoryTree, parentCategory);
        if (parent != null) {
            CategoryNode nodeToAdd = new CategoryNode(childCategory);
            parent.addChild(nodeToAdd);
            nodeToAdd.setParent(parent);
            success = true;
        }
        return success;
    }
    public boolean removeCategory (Category category) {
        boolean success = false;
        if (category.getId().equals("0"))
            return false;
        if (this.categoryTree.removeNode(category))
        {
            for (Product p: products)
            {
                boolean hasDefault = false;
                for (Category c: p.categories) {
                    if (c.getId().equals(category.getId()))
                        p.categories.remove(c);
                    if (c.getId().equals("0"))
                        hasDefault = true;
                }
                if (!hasDefault)
                    p.categories.add(this.defaultCategory);
            }
            success = true;
        }
        return success;
    }

    public ArrayList<Product> getProducts(){return this.products;}
}
