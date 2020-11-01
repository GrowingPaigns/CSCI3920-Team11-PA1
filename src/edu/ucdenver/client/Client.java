package edu.ucdenver.client;

import apple.laf.JRSUIUtils;
import edu.ucdenver.domain.*;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import java.io.*;
import java.lang.System;
import java.lang.reflect.Array;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;
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
        User response = null;
        boolean success = false;
        try
        {
            response = (User) sendRequest(request);
            if (response != null)
            {
                this.user = response;
                return success = true;
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

        String request = "FU";

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

    public CategoryTree getCategories()
    {
        CategoryTree categories = null;
        String request = "GC|";

        try
        {
            categories = (CategoryTree) this.sendRequest(request);
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

        return categories;
    }

    public boolean addProduct(String id, String name, String brandName, String description, LocalDate date) {
        boolean success = false;

        if (this.user.isAdmin())
        {
            String request = String.format("AP|");
            boolean response = false;

            try
            {
                objectOutputStream.writeUnshared(new HomeProduct(id, name, brandName, description, date, "Home"));
                response = (boolean)this.sendRequest(request);
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
