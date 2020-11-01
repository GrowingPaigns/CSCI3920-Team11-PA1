package edu.ucdenver.domain;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.time.LocalDate;
import java.util.ArrayList;

public class HomeProduct extends Product implements Serializable
{
    private String location;
    public String getLocation() {
        return this.location;
    }
    public void setLocation(String loc) {
        this.location = loc;
    }

    public HomeProduct (String id, String name, String brandName, String description, LocalDate dateAdded,
                        String location)
    {
        super (id, name, brandName, description, dateAdded);
        this.location = location;
    }

    public HomeProduct (String id, String name, String brandName, String description, LocalDate dateAdded,
                        ArrayList<Category> categories, String location)
    {
        super (id, name, brandName, description, dateAdded, categories);
        this.location = location;
    }

    @Override
    public String toString()
    {
        return super.toString() + String.format("HOME LOCATION: %s", location);
    }
}
