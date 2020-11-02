package edu.ucdenver.client;

import apple.laf.JRSUIUtils;
import edu.ucdenver.domain.*;
import javafx.scene.control.TreeItem;

import java.io.*;
import java.lang.System;
import java.lang.reflect.Array;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Client
{
    private int serverPort;
    private String serverIP;
    private boolean isConnected;

    private Socket serverConnection;
    //private PrintWriter output = null;
    //private BufferedReader input = null;
    private ObjectOutputStream objectOutputStream = null;
    private ObjectInputStream objectInputStream = null;
    private User user;

    public Client (String ip, int port)
    {
        this.serverIP = ip;
        this.serverPort = port;
    }

    public Client ()
    {
        this.serverPort = 10001;
        this.serverIP = "localhost";
    }

    public int getServerPort(){return this.serverPort;}
    public void setServerPort(int serverPort){this.serverPort = serverPort;}

    public String getServerIP(){return this.serverIP;}
    public void setServerIP(String ip){this.serverIP = ip;}

    private void displayMessage(String message) {System.out.println(message);}

    public boolean isConnected() {return this.isConnected;}

    private PrintWriter getOutputStream() throws IOException
    {
        return new PrintWriter(this.serverConnection.getOutputStream(), true);
    }

    private BufferedReader getInputStream() throws IOException
    {
        return new BufferedReader(new InputStreamReader(this.serverConnection.getInputStream()));
    }

    public void connect()
    {
        displayMessage ("Attempting connection to Server");
        PrintWriter output = null;
        BufferedReader input = null;

        try
        {
            this.serverConnection = new Socket (this.serverIP, this.serverPort);
            this.isConnected = true;
            //this.output = this.getOutputStream();
            //this.input = this.getInputStream();
            this.objectOutputStream = new ObjectOutputStream(serverConnection.getOutputStream());
            this.objectInputStream = new ObjectInputStream(serverConnection.getInputStream());
        }
        catch (IOException e)
        {
            //this.input = null;
            //this.output = null;
            this.serverConnection = null;
            this.isConnected = false;
            this.objectOutputStream = null;
            this.objectInputStream = null;
            e.printStackTrace();
        }
    }

    public void disconnect()
    {
        displayMessage("\n>> Terminating Client Connection to Server");
        try
        {
            //this.input.close();
            this.objectOutputStream.close();
        }
        catch(IOException|NullPointerException e)
        {
            e.printStackTrace();
        }

        try
        {
            //this.output.close();
            this.objectInputStream.close();
        }
        catch(IOException | NullPointerException e)
        {
            e.printStackTrace();
        }

        try
        {
            this.serverConnection.close();
        }
        catch(IOException|NullPointerException e)
        {
            e.printStackTrace();
        }
    }

//    public void getServerInitialResponse() throws IOException
//    {
//        String srvResponse = this.input.readLine();
//        displayMessage("SERVER >> " + srvResponse);
//    }

    public Object sendRequest (Object request) throws IOException, ClassNotFoundException {
        //this.output.println(request);
        this.objectOutputStream.writeUnshared(request);
        objectOutputStream.flush();
        displayMessage("CLIENT >> " + request);
        //String srvResponse = this.input.readLine();
        Object srvResponse = this.objectInputStream.readUnshared();
        displayMessage("SERVER >> " + srvResponse);
        return srvResponse;
    }

    public boolean loginUser (String username, String password)
    {
        String request = String.format("L|%s|%s", username, password);
        Request request1 = new Request(request);
        User response = null;
        boolean success = false;
        try
        {
            response = (User) sendRequest(request1);
            if (response != null)
            {
                if (response.isAdmin()) {
                    this.user = response;
                    return success = true;
                }
            }
        }
        catch (IOException | ClassNotFoundException ioe)
        {
            ioe.printStackTrace();
        }
        return success;
    }

    public boolean createNewUser (String displayName, String email, String password, boolean admin)
    {
        boolean success = false;

        if (this.user.isAdmin())
        {
            String request = String.format("CU|%s|%s|%s|%s", displayName, email, password, admin);
            String response = null;

            try {
                response = (String) this.sendRequest(request);
            } catch (IOException | ClassNotFoundException ioe) {
                ioe.printStackTrace();
            }

            if (response.contains("Success"))
                success = true;
            else
                success = false;
        }
        return success;
    }

    public ArrayList<User> fetchUsers ()
    {
        ArrayList<User> users = null;
        Request request = new Request("FU");
        //String request = "FU";

        try
        {
            users = (ArrayList<User>)this.sendRequest(request);
        }
        catch(IOException | ClassNotFoundException ioe)
        {
            ioe.printStackTrace();
        }

        return users;
    }

    public CategoryNode getCategories()
    {
        CategoryNode categories = null;
        Request request = new Request("GC");
        //String request = "GC|";

        try
        {
            categories = (CategoryNode) this.sendRequest(request);
            System.out.println(categories.getData());
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return categories;
    }

    public boolean addHomeProduct(String id, String name, String brandName, String description, LocalDate date,
                                  ArrayList<TreeItem<Category>> categories, String location) {
        boolean success = false;
        Request request = new Request();

        if (this.user.isAdmin())
        {
            String action = String.format("AP|");
            request.setAction(action);
            boolean response = false;


            try
            {
                if (categories.size() == 0) {
                    request.setMessage(new HomeProduct(id, name, brandName, description, date, location));
                    response = (boolean) this.sendRequest(request);
                }
                else
                {
                    ArrayList arrayListCategories = new ArrayList();
                    for (TreeItem<Category> t: categories)
                        arrayListCategories.add(t.getValue());
                    request.setMessage(new HomeProduct(id, name, brandName, description, date,
                            arrayListCategories, location));
                    response = (boolean) this.sendRequest(request);
                }
            }
            catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
            }

            if (response)
                success = true;
        }
        return success;
    }

    public boolean addBook(String id, String name, String brandName, String description, LocalDate dateAdded,
                           ArrayList<TreeItem<Category>> categories, String title, String author,
                           LocalDate publicationDate, int numberOfPages) {
        boolean success = false;
        Request request = new Request();

        if (this.user.isAdmin())
        {
            String action = String.format("AP|");
            request.setAction(action);
            boolean response = false;


            try
            {
                if (categories.isEmpty()) {
                    request.setMessage(new Book(id, name, brandName, description, dateAdded, title, author,
                            publicationDate, numberOfPages));
                    response = (boolean) this.sendRequest(request);
                }
                else
                {
                    ArrayList arrayListCategories = new ArrayList();
                    for (TreeItem<Category> t: categories)
                        arrayListCategories.add(t.getValue());
                    request.setMessage(new Book(id, name, brandName, description, dateAdded,
                            arrayListCategories, title, author, publicationDate, numberOfPages));
                    response = (boolean) this.sendRequest(request);
                }
            }
            catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
            }

            if (response)
                success = true;
        }
        return success;
    }

    public boolean addElectronic(String id, String name, String brandName, String description, LocalDate dateAdded,
                                 ArrayList<TreeItem<Category>> categories, String serial,
                                 LocalDate[] warrantyPeriod) {
        boolean success = false;
        Request request = new Request();

        if (this.user.isAdmin())
        {
            String action = String.format("AP|");
            request.setAction(action);
            boolean response = false;


            try
            {
                if (categories.isEmpty()) {
                    request.setMessage(new Electronic(id, name, brandName, description, dateAdded,
                            serial, warrantyPeriod));
                    response = (boolean) this.sendRequest(request);
                }
                else
                {
                    ArrayList arrayListCategories = new ArrayList();
                    for (TreeItem<Category> t: categories)
                        arrayListCategories.add(t.getValue());
                    request.setMessage(new Electronic(id, name, brandName, description, dateAdded,
                            arrayListCategories, serial, warrantyPeriod));
                    response = (boolean) this.sendRequest(request);
                }
            }
            catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
            }

            if (response)
                success = true;
        }
        return success;
    }

    public boolean addComputer(String id, String name, String brandName, String description, LocalDate dateAdded,
                               ArrayList<TreeItem<Category>> categories, String serial, LocalDate[] warrantyPeriod,
                               String specs) {
        boolean success = false;
        Request request = new Request();

        if (this.user.isAdmin())
        {
            String action = String.format("AP|");
            request.setAction(action);
            boolean response = false;


            try
            {
                if (categories.isEmpty()) {
                    ArrayList<String> specifications = new ArrayList<>(Arrays.asList(specs.split("\\r?\\n")));
                    request.setMessage(new Computer(id, name, brandName, description, dateAdded,
                            serial, warrantyPeriod, specifications));
                    response = (boolean) this.sendRequest(request);
                }
                else
                {
                    ArrayList<String> specifications = new ArrayList<>(Arrays.asList(specs.split("\\r?\\n")));
                    ArrayList arrayListCategories = new ArrayList();
                    for (TreeItem<Category> t: categories)
                        arrayListCategories.add(t.getValue());
                    request.setMessage(new Computer(id, name, brandName, description, dateAdded,
                            arrayListCategories, serial, warrantyPeriod, specifications));
                    response = (boolean) this.sendRequest(request);
                }
            }
            catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
            }

            if (response)
                success = true;
        }
        return success;
    }

    public boolean addCellphone(String id, String name, String brandName, String description, LocalDate dateAdded,
                                ArrayList<TreeItem<Category>> categories, String serial, LocalDate[] warrantyPeriod,
                                String imei, String os) {
        boolean success = false;
        Request request = new Request();

        if (this.user.isAdmin())
        {
            String action = String.format("AP|");
            request.setAction(action);
            boolean response = false;

            Cellphone.eOperatingSystem operatingSystem = null;
            if (os.equals("iOS"))
                operatingSystem = Cellphone.eOperatingSystem.IOS;
            else if (os.equals("Android"))
                operatingSystem = Cellphone.eOperatingSystem.ANDROID;
            try
            {
                if (categories.isEmpty()) {
                    request.setMessage(new Cellphone(id, name, brandName, description, dateAdded,
                            serial, warrantyPeriod, imei, operatingSystem));
                    response = (boolean) this.sendRequest(request);
                }
                else
                {
                    ArrayList arrayListCategories = new ArrayList();
                    for (TreeItem<Category> t: categories)
                        arrayListCategories.add(t.getValue());
                    request.setMessage(new Cellphone(id, name, brandName, description, dateAdded,
                            arrayListCategories, serial, warrantyPeriod, imei, operatingSystem));
                    response = (boolean) this.sendRequest(request);
                }
            }
            catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
            }

            if (response)
                success = true;
        }
        return success;
    }

    public ArrayList<Product> fetchProducts() {
        ArrayList<Product> products = null;
        Request request = new Request("FP");
        //String request = "FP";

        try
        {
            products = (ArrayList<Product>)this.sendRequest(request);
        }
        catch(IOException | ClassNotFoundException ioe)
        {
            ioe.printStackTrace();
        }

        return products;
    }

    public boolean addCategory(String id, String name, String description, Category parent) {
        boolean success = false;
        Request request = new Request();

        if (this.user.isAdmin())
        {
            String action = String.format("AC|");
            request.setAction(action);
            boolean response = false;

            try
            {
                request.setMessage(new Category (id, name, description, parent));
                response = (boolean) this.sendRequest(request);
            }
            catch (IOException | ClassNotFoundException e)
            {
                e.printStackTrace();
            }

            if (response)
                success = true;
        }
        return success;
    }
}
