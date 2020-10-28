package edu.ucdenver.domain;

import java.util.ArrayList;

public class Catalog
{
    private ArrayList<Product> products;
    private Category defaultCategory;

    public Catalog()
    {
        products = new ArrayList<>();
        this.defaultCategory = new Category();
    }

    public Catalog (Category defaultCategory)
    {
        products = new ArrayList<>();
        this.defaultCategory = defaultCategory;
    }

    public Catalog (ArrayList<Product> products, Category defaultCategory)
    {
        this.products = products;
        this.defaultCategory = defaultCategory;
    }

    public Category getDefaultCategory() {return this.defaultCategory;}
    public void setDefaultCategory(Category defaultCategory){this.defaultCategory = defaultCategory;}

    public void addProduct(Product product)
    {
        this.products.add(product);
    }

    public void removeProduct (Product product)
    {
        this.products.remove(product);
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
}
