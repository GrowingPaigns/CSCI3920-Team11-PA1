package edu.ucdenver.server;

import java.io.*;
import java.lang.System;
import java.net.Socket;
import java.time.LocalDate;
import java.util.ArrayList;

import edu.ucdenver.client.Request;
import edu.ucdenver.domain.*;


public class ClientWorker implements Runnable
{
    private final Socket clientConnection;
    private edu.ucdenver.domain.System system;
    private final int id;
    private PrintWriter output;
    private BufferedReader input;
    private ObjectInputStream objectInputStream;
    private ObjectOutputStream objectOutputStream;
    private boolean keepRunningClient;

    public ClientWorker (Socket clientConnection, edu.ucdenver.domain.System system, int id)
    {
        this.clientConnection = clientConnection;
        this.id = id;
        this.system = system;
        keepRunningClient = true;
    }

    @Override
    public void run()
    {
        try
        {
            //getOutputStream(clientConnection);
            //getInputStream(clientConnection);
            objectOutputStream = new ObjectOutputStream(clientConnection.getOutputStream());
            objectInputStream = new ObjectInputStream(clientConnection.getInputStream());

            while (this.keepRunningClient)
                processClientRequest();
        }
        catch(IOException | ClassNotFoundException ioe)
        {
            ioe.printStackTrace();
        }
        finally {
            closeClientConnection();
        }
    }

    private void getOutputStream (Socket clientConnection) throws IOException
    {
        this.output = new PrintWriter(clientConnection.getOutputStream(), true);
    }

    private void getInputStream(Socket clientConnection) throws IOException
    {
        this.input = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
    }

    private void displayMessage(String message) {java.lang.System.out.printf("CLIENT[%d] >> %s%n", this.id, message);}

    private void sendMessage (String message)
    {
        displayMessage("SERVER >> "+ message);
        this.output.println(message);
    }

    private void processClientRequest() throws IOException, ClassNotFoundException
    {
        Request clientMessage = (Request) this.objectInputStream.readUnshared();
        displayMessage("CLIENT SAID>>>" + clientMessage);
        String[] arguments =  (clientMessage.getAction()).split("\\|");
        String response = "";

        switch (arguments[0]) {
            case "L":
                User user = system.loginUser(arguments[1], arguments[2]);
                if (user != null) {
                    response = "Login Successful";
                    objectOutputStream.writeUnshared(user);
                    objectOutputStream.flush();
                    objectOutputStream.reset();
                } else
                    response = "Incorrect Username or Password";
                break;
            case "CU":
                response = this.system.createNewUser(arguments[1], arguments[2], arguments[3], Boolean.parseBoolean(arguments[4]));
                objectOutputStream.writeUnshared(response);
                objectOutputStream.flush();
                objectOutputStream.reset();
                break;
            case "FU":
                ArrayList<User> users = this.system.getUsers();
                objectOutputStream.writeUnshared(users);
                objectOutputStream.flush();
                objectOutputStream.reset();
                break;
            case "FP":
                ArrayList<Product> products = this.system.getCatalog().getProducts();
                objectOutputStream.writeUnshared(products);
                objectOutputStream.flush();
                objectOutputStream.reset();
                break;
            case "GC":
                CategoryNode categories = this.system.getCatalog().getCategoryTree();
                ArrayList<Category> arrayList = categories.toArrayList(categories);
                for (Category c: arrayList)
                    System.out.println(c);
                objectOutputStream.writeUnshared(this.system.getCatalog().getCategoryTree());
                objectOutputStream.flush();
                objectOutputStream.reset();
                break;
            case "AP":
                objectOutputStream.writeUnshared(this.system.getCatalog().addProduct((Product) clientMessage.getMessage()));
                objectOutputStream.flush();
                objectOutputStream.reset();
                break;
            case "RP":
                objectOutputStream.writeUnshared(this.system.getCatalog().removeProduct((Product)clientMessage.getMessage()));
                objectOutputStream.flush();
                objectOutputStream.reset();
                break;
            case "AC":
                objectOutputStream.writeUnshared(this.system.getCatalog().addCategory((Category)clientMessage.getMessage(),
                        ((Category) clientMessage.getMessage()).getParentCategory()));
                objectOutputStream.flush();
                objectOutputStream.reset();
                break;
            case "RC":
                objectOutputStream.writeUnshared(this.system.getCatalog().removeCategory((Category) clientMessage.getMessage()));
                objectOutputStream.flush();
                objectOutputStream.reset();
                break;
            case "SD":
                objectOutputStream.writeUnshared(this.system.getCatalog().setDefaultCategory((Category) clientMessage.getMessage()));
                objectOutputStream.flush();
                objectOutputStream.reset();
                break;
            case "FOD":
                objectOutputStream.writeUnshared(this.system.getFinalizedOrders((LocalDate[]) clientMessage.getMessage()));
                objectOutputStream.flush();
                objectOutputStream.reset();
                break;
            case "SA":
                objectOutputStream.writeUnshared(this.system.saveToFile());
                objectOutputStream.flush();
                objectOutputStream.reset();
                break;
            case "FPC":
                objectOutputStream.writeUnshared(this.system.getCatalog().getProductsUnderCategory((Category) clientMessage.getMessage()));
                objectOutputStream.flush();
                objectOutputStream.reset();
                break;
         }
    }


    private void closeClientConnection ()
    {
        try
        {
            this.input.close();
        }
        catch(IOException|NullPointerException e)
        {
            e.printStackTrace();
        }

        try
        {
            this.output.close();
        }
        catch(NullPointerException e)
        {
            e.printStackTrace();
        }

        try
        {
            this.clientConnection.close();
        }
        catch(IOException|NullPointerException e)
        {
            e.printStackTrace();
        }
    }
}
