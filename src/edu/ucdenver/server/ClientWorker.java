package edu.ucdenver.server;

import java.io.*;
import java.net.Socket;
import edu.ucdenver.domain.System;
import edu.ucdenver.domain.User;


public class ClientWorker implements Runnable
{
    private final Socket clientConnection;
    private edu.ucdenver.domain.System system;
    private final int id;
    private PrintWriter output;
    private BufferedReader input;
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
            getOutputStream(clientConnection);
            getInputStream(clientConnection);
            objectOutputStream = new ObjectOutputStream(clientConnection.getOutputStream());

            while (this.keepRunningClient)
                processClientRequest();
        }
        catch(IOException ioe)
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

    private void processClientRequest() throws IOException
    {
        String clientMessage = this.input.readLine();
        displayMessage("CLIENT SAID>>>" + clientMessage);

        String[] arguments = clientMessage.split("\\|");
        String response = "";

        switch (arguments[0])
        {
            case "L":
                User user = system.loginUser(arguments[1], arguments[2]);
                if (user != null) {
                    response = "Login Successful";
                    objectOutputStream.writeObject(user);
                }
                else
                    response = "Incorrect Username or Password";

        }

        this.sendMessage(response);
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
