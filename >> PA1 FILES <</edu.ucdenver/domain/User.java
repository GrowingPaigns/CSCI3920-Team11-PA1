package edu.ucdenver.domain;

import java.time.LocalDate;
import java.util.ArrayList;

public class User {
    //variables
    private String displayName;
    private String email;
    private String password;
    private boolean admin;
    private ArrayList<Order> orders;

    //constructors
    public User(String displayName, String email, String password, boolean admin) {
        this.displayName = displayName;
        this.email = email;
        this.password = password;
        this.admin = admin;
    }

    //methods

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public ArrayList<Order> getFinalizedOrders() {
        //returns arraylist of all finalized orders
        ArrayList<Order> finalizedOrders = new ArrayList<Order>();
        for(int i = 0; i < orders.size(); i++) {
            if(orders.get(i).getStatus() == Order.eStatus.FINALIZED) {
                finalizedOrders.add(orders.get(i));
            }
        }
        return finalizedOrders;
    }

    public ArrayList<Order> getFinalizedOrders(LocalDate dateFinalized) {
        //returns arraylist of all finalized orders on specific date
        ArrayList<Order> finalizedOrders = new ArrayList<Order>();
        for(int i = 0; i < orders.size(); i++) {
            if(orders.get(i).getStatus() == Order.eStatus.FINALIZED
                    & orders.get(i).getDateFinalized() == dateFinalized) {
                finalizedOrders.add(orders.get(i));
            }
        }
        return finalizedOrders;
    }

    public void createNewOrder() {
        //TODO User.java: createNewOrder()
    }
}
