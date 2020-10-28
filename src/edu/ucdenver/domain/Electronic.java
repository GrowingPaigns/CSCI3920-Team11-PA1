package edu.ucdenver.domain;

import java.time.LocalDate;

public class Electronic extends Product
{
    protected String serial;
    protected LocalDate[] warranty;

    public Electronic (String id, String name, String brandName, String description, LocalDate dateAdded,
                       String serial, LocalDate[] warranty) throws IllegalArgumentException
    {
        super (id, name, brandName, description, dateAdded);
        this.serial = serial;
        if (warranty.length != 2)
            throw new IllegalArgumentException("Tried to set warranty without exactly 2 dates.");
        else if (!warranty[0].isBefore(warranty[1]))
            throw new IllegalArgumentException("The first date entered comes after the second date. Please order correctly.");
        else
            this.warranty = warranty;
    }

    public String getSerial() {
        return serial;
    }
    public void setSerial(String serial) {
        this.serial = serial;
    }

    public LocalDate[] getWarranty()
    {
        return warranty;
    }
    public void setWarranty(LocalDate[] warranty) throws IllegalArgumentException
    {
        if (warranty.length != 2)
            throw new IllegalArgumentException("Tried to set warranty without exactly 2 dates.");
        else if (!warranty[0].isBefore(warranty[1]))
            throw new IllegalArgumentException("The first date entered comes after the second date. Please order correctly.");
        else
            this.warranty = warranty;
    }

    @Override
    public String toString()
    {
        return super.toString() + String.format("SERIAL #: %s, WARRANTY PERIOD: %s - %s", serial, warranty[0].toString(),
                warranty[1].toString());
    }
}
