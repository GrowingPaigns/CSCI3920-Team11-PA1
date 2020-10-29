package edu.ucdenver.domain;

import java.util.ArrayList;

public class System
{
    private ArrayList<User> users;
    private ArrayList<Order> orders;
    private Catalog catalog;

    public System ()
    {
        users = new ArrayList<>();
        orders = new ArrayList<>();
        catalog = new Catalog();
    }

    public System (ArrayList<User> users, ArrayList<Order> orders, Catalog catalog)
    {
        this.users = users;
        this.orders = orders;
        this.catalog = catalog;
    }

    public void createNewUser (String displayName, String email, String password, boolean admin)
    {
        users.add(new User (displayName, email, password, admin));
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
}
