package edu.ucdenver.domain;

public class Cellphone {
    enum eOperatingSystem{IOS, ANDROID}

    //variables
    private String imei;
    private eOperatingSystem os;
    
    //constructor
    public Cellphone(String imei, eOperatingSystem os) {
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
}
