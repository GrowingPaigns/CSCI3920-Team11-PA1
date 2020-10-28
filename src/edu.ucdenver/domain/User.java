package edu.ucdenver.domain;

import java.time.LocalDate;
import java.util.ArrayList;

public class User {
    //attributes
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
        for (Order order : orders) {
            if (order.getStatus() == Order.eStatus.FINALIZED) {
                finalizedOrders.add(order);
            }
        }
        return finalizedOrders;
    }

    public ArrayList<Order> getFinalizedOrders(LocalDate dateFinalized) {
        //returns arraylist of all finalized orders on specific date
        ArrayList<Order> finalizedOrders = new ArrayList<Order>();
        for (Order order : orders) {
            if (order.getStatus() == Order.eStatus.FINALIZED
                    & order.getDateFinalized() == dateFinalized) {
                finalizedOrders.add(order);
            }
        }
        return finalizedOrders;
    }

    public void createNewOrder() {
        orders.add(new Order()); //order number is automatically set in Order constructor
    }
}
