package edu.ucdenver.domain;

import java.time.LocalDate;
import java.util.ArrayList;

public class Computer extends Electronic
{
    private ArrayList<String> specs;

    public Computer (String id, String name, String brandName, String description, LocalDate dateAdded, String serial,
                       LocalDate[] warranty, ArrayList<String> specs)
    {
        super (id, name, brandName, description, dateAdded, serial, warranty);
        this.specs = specs;
    }

    public Computer (String id, String name, String brandName, String description, LocalDate dateAdded,
                     ArrayList<Category> categories, String serial, LocalDate[] warranty, ArrayList<String> specs)
    {
        super (id, name, brandName, description, dateAdded, categories, serial, warranty);
        this.specs = specs;
    }

    public ArrayList<String> getSpecs() {
        return specs;
    }
    public void setSpecs(ArrayList<String> specs) {
        this.specs = specs;
    }

    public void addSpec (String spec)
    {
        this.specs.add(spec);
    }

    public void removeSpec(String spec)
    {
        this.specs.remove(spec);
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString() + String.format("SPECS:%n"));

        for (String string: specs)
        {
            sb.append(string);
            sb.append("\n");
        }

        return sb.toString();
    }
}
