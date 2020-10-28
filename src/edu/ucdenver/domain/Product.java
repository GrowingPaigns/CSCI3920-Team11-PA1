package edu.ucdenver.domain;

import java.time.LocalDate;
import java.util.ArrayList;

public abstract class Product
{
    protected String id;
    protected String name;
    protected String brandName;
    protected String description;
    protected LocalDate dateAdded;

    public Product (String id, String name, String brandName, String description, LocalDate date)
    {
        this.id = id;
        this.name = name;
        this.brandName = brandName;
        this.description = description;
        this.dateAdded = date;
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

    @Override
    public String toString()
    {
        return String.format("NAME: %s, BRAND: %s, DESCRIPTION: %s, DATE ADDED: %s, ID: %s", name,
                brandName, description, this.dateAdded.toString(), id);
    }
}
