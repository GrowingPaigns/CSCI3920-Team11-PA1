package edu.ucdenver.domain;

import java.time.LocalDate;
import java.util.ArrayList;

abstract public class Product
{
    private String id;
    private String name;
    private String brandName;
    private String description;
    private LocalDate dateAdded;
    private ArrayList<Category> categories;

    public Product (String id, String name, String brandName, String description, LocalDate date)
    {
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.description = description;
        this.dateAdded = date;
        categories = new ArrayList<Category>(10);
    }

    public Product (String id, String name, String brandName, String description, LocalDate date, ArrayList<Category> categories)
    {
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.description = description;
        this.dateAdded = date;
        this.categories = categories;
    }

    //Getters and setters
    public String getId() {return this.id;}
    public void SetId(String Id){this.id = Id;}

    public String getName(){return this.name;}
    public void setName(String name){this.name = name;}

    public String getBrandName(){return this.brandName;}
    public void setBrandName(String brandName){this.brandName = brandName;}

    public String getDescription(){return this.description;}
    public void setDescription(String description){this.description = description;}

    public LocalDate getDateAdded(){return this.dateAdded;}
    public void setDateAdded(LocalDate date) {this.dateAdded = date;}

    public void addCategory(Category category)
    {
        this.categories.add(category);
    }

    public void removeCategory (Category category)
    {
        this.categories.remove(category);
    }


}
