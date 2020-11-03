package edu.ucdenver.domain;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;

public class System implements Serializable
{
    private ArrayList<User> users;
    private ArrayList<Order> orders;
    private Catalog catalog;

    public System ()
    {
        users = new ArrayList<>();
        orders = new ArrayList<>();
        catalog = new Catalog();
        users.add(new User ("admin", "admin@admin.com", "admin", true));
        users.get(0).createNewOrder(users.get(0));
        users.get(0).getOrders().get(0).setStatus(Order.eStatus.FINALIZED);
        orders.add(users.get(0).getOrders().get(0));
    }

    public System (ArrayList<User> users, ArrayList<Order> orders, Catalog catalog)
    {
        this.users = users;
        this.orders = orders;
        this.catalog = catalog;
    }

    public String createNewUser (String displayName, String email, String password, boolean admin)
    {
        String response ="";
        if (searchUser(displayName) == null && searchUser(email) == null)
        {
            users.add(new User (displayName, email, password, admin));
            response = "Successfully added user.";
        }
        else
            response = "That username or email already exists.";

        return response;
    }

    public User searchUser (String searchTerm)
    {
        User search = null;

        for (User user: users)
        {
            if (user.getDisplayName().equals(searchTerm) || user.getEmail().equals(searchTerm))
                search = user;
        }

        return search;
    }

    public void cancelOrder(Order order)
    {
        for (Order o: orders)
        {
            if (order.getOrderNumber() == o.getOrderNumber() && o.getStatus() == Order.eStatus.IN_PROCESS)
            {
                orders.remove(o);
                o.getUser().cancelOrder(o);
            }
        }
    }

    public User loginUser(String username, String password)
    {
        User user = searchUser(username);
        if (user != null && user.getPassword().equals(password))
            return user;
        else
            return null;
    }

    public ArrayList<User> getUsers()
    {
        return this.users;
    }

    public Catalog getCatalog () {return this.catalog;}

    public ArrayList<Order> getFinalizedOrders ()
    {
        ArrayList<Order> finalizedOrders = new ArrayList<>();

        for (Order o: orders)
        {
            if (o.getStatus() == Order.eStatus.FINALIZED)
                finalizedOrders.add(o);
        }
        return finalizedOrders;
    }

    public ArrayList<Order> getFinalizedOrders (LocalDate[] period)
    {
        ArrayList<Order> finalizedOrders = new ArrayList<>();

        for (Order o: orders)
        {
            if (o.getStatus() == Order.eStatus.FINALIZED && o.getDateFinalized().isAfter(period[0]) &&
                    o.getDateFinalized().isBefore(period[1]))
                finalizedOrders.add(o);
        }
        return finalizedOrders;
    }
}
