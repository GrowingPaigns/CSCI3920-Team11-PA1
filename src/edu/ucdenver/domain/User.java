package edu.ucdenver.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class User implements Serializable {
    //attributes
    private String displayName;
    private String email;
    private String password;
    private boolean admin;
    private ArrayList<Order> orders;

    //constructors
    public User(String displayName, String email, String password, boolean admin)
    {
        this.displayName = displayName;
        this.email = email;
        this.password = password;
        this.admin = admin;
        this.orders = new ArrayList<>();
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
    public String getPassword() {return this.password;}

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

    public ArrayList<Order> getFinalizedOrders(LocalDate[] datesFinalized)
    {
        if (datesFinalized.length !=2)
            throw new IllegalArgumentException("Attempted to check date range with less than or more than 2 dates");
        else if (!datesFinalized[0].isBefore(datesFinalized[1]))
            throw new IllegalArgumentException("Attempted to check date range where first date came after second");

        //returns arraylist of all finalized orders on specific date
        ArrayList<Order> finalizedOrders = new ArrayList<Order>();
        for (Order order : orders)
        {
            if (order.getStatus() == Order.eStatus.FINALIZED
                    & order.getDateFinalized().isAfter(datesFinalized[0]) && order.getDateFinalized().isBefore(datesFinalized[1]))
            {
                finalizedOrders.add(order);
            }
        }
        return finalizedOrders;
    }

    public void createNewOrder(User user)
    {
        orders.add(new Order(this)); //order number is automatically set in Order constructor
    }

    public void createNewOrder(User user, ArrayList<Product> products)
    {
        orders.add(new Order(this, products)); //order number is automatically set in Order constructor
    }

    public void cancelOrder(Order order)
    {
        for (Order o: orders)
        {
            if (order.getOrderNumber() == o.getOrderNumber())
            {
                orders.remove(o);
            }
        }
    }
}
