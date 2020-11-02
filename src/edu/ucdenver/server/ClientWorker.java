package edu.ucdenver.server;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

import edu.ucdenver.domain.HomeProduct;
import edu.ucdenver.domain.Product;
import edu.ucdenver.domain.System;
import edu.ucdenver.domain.User;


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
        //String clientMessage = this.input.readLine();
        ArrayList<Object> clientMessage = new ArrayList<>();
        if (clientMessage.size() == 0)
            return;
        boolean cont = true;
        while (cont)
        {
            Object obj = objectInputStream.readUnshared();
            if (obj != null)
            {
                clientMessage.add(obj);
            }
            else {
                cont = false;
            }
        }

        displayMessage("CLIENT SAID>>>" + clientMessage);
        String[] arguments =  ((String) clientMessage.get(0)).split("\\|");
        String response = "";

        switch (arguments[0]) {
            case "L":
                User user = system.loginUser(arguments[1], arguments[2]);
                if (user != null) {
                    response = "Login Successful";
                    objectOutputStream.writeUnshared(user);
                    objectOutputStream.flush();
                } else
                    response = "Incorrect Username or Password";
                break;
            case "CU":
                response = this.system.createNewUser(arguments[1], arguments[2], arguments[3], Boolean.parseBoolean(arguments[4]));
                objectOutputStream.writeUnshared(response);
                objectOutputStream.flush();
                break;
            case "FU":
                ArrayList<User> users = this.system.getUsers();
                objectOutputStream.writeUnshared(users);
                objectOutputStream.flush();
                break;
            case "GC":
                objectOutputStream.writeUnshared(this.system.getCatalog().getCategoryTree());
                objectOutputStream.flush();
                break;
            case "AP":
                objectOutputStream.writeUnshared(this.system.getCatalog().addProduct((Product) clientMessage.get(1)));
                objectOutputStream.flush();
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
