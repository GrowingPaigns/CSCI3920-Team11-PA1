package edu.ucdenver.domain;

import java.time.LocalDate;

public class Electrionic extends Product {
    private String serial;
    private LocalDate warranty;

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
    }

    public LocalDate getWarranty(){
        return warranty;
    }

    public void setWarranty(LocalDate warranty) {
        this.warranty = warranty;
    }
}
