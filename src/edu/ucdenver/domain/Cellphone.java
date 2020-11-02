package edu.ucdenver.domain;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cellphone extends Electronic
{
    public enum eOperatingSystem{IOS, ANDROID}

    //variables
    private String imei;
    private eOperatingSystem os;
    
    //constructor
    public Cellphone(String id, String name, String brandName, String description, LocalDate dateAdded,
                     String serial, LocalDate[] warranty, String imei, eOperatingSystem os)
    {
        super (id, name, brandName, description, dateAdded, serial, warranty);
        this.imei = imei;
        this.os = os;
    }

    //constructor
    public Cellphone(String id, String name, String brandName, String description, LocalDate dateAdded,
                     ArrayList<Category> categories, String serial, LocalDate[] warranty, String imei, eOperatingSystem os)
    {
        super (id, name, brandName, description, dateAdded, categories, serial, warranty);
        this.imei = imei;
        this.os = os;
    }

    //getters and setters
    public String getImei() {
        return imei;
    }
    public void setImei(String imei) {
        this.imei = imei;
    }

    public eOperatingSystem getOs() {
        return os;
    }
    public void setOs(eOperatingSystem os) {
        this.os = os;
    }

    @Override
    public String toString ()
    {
        return super.toString() + String.format("IMEI: %s, OS: %s", imei, os.toString());
    }
}
