package edu.ucdenver.domain;

import java.time.LocalDate;
import java.util.ArrayList;

public class Catalog
{
    private ArrayList<Product> products;
    private Category defaultCategory;
    private CategoryTree categoryTree;

    public Catalog()
    {
        products = new ArrayList<>();
        this.defaultCategory = new Category();
        categoryTree = new CategoryTree(this.defaultCategory);
    }

    public Catalog (Category defaultCategory)
    {
        products = new ArrayList<>();
        this.defaultCategory = defaultCategory;
        categoryTree = new CategoryTree(defaultCategory);
    }

    public Catalog (ArrayList<Product> products, Category defaultCategory)
    {
        this.products = products;
        this.defaultCategory = defaultCategory;
        categoryTree = new CategoryTree(defaultCategory);
    }

    public Category getDefaultCategory() {return this.defaultCategory;}
    public void setDefaultCategory(Category defaultCategory){
        this.defaultCategory = defaultCategory;
        this.categoryTree.setRoot(defaultCategory);
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

    public CategoryTree getCategoryTree () {return this.categoryTree;}

    public void addCategory (Category category) {this.categoryTree.addItem(category);}
    public void removeCategory (Category category) {this.categoryTree.removeItem(category);}

    public ArrayList<Product> getProducts(){return this.products;}
}
