package edu.ucdenver.domain;

import edu.ucdenver.client.Client;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class System implements Serializable
{
    private ArrayList<User> users;
    private ArrayList<Order> orders;
    private Catalog catalog;
    public Client client = new Client();


    public System ()
    {
        users = new ArrayList<>();
        orders = new ArrayList<>();
        catalog = new Catalog();
        users.add(new User ("admin", "admin@admin.com", "admin", true));
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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //Save Method

    //For file save
    public static final String filename = "./server.ser";

    public boolean saveToFile(){
        boolean saved = false;
        ObjectOutputStream oos = null;

        try{
            oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(this);
            saved = true;

        }
        catch (IOException ioe){
            ioe.printStackTrace();
        }
        finally {
            if (oos != null) {
                try {
                    oos.close();
                }
                catch (IOException ioe){
                    ioe.printStackTrace();
                }
            }
        }
        return saved;
    }
}
