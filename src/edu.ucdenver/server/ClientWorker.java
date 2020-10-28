package edu.ucdenver.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientWorker implements Runnable
{
    private final Socket clientConnection;
    //private final System system;
    private final int id;
    private PrintWriter output;
    private BufferedReader input;
    private boolean keepRunningClient;

    public ClientWorker (Socket clientConnection, /*System system*/ int id)
    {
        this.clientConnection = clientConnection;
        this.id = id;
        //this.system = system;
        keepRunningClient = true;
    }

    @Override
    public void run()
    {

    }

    private void getOutputStream (Socket clientConnection) throws IOException
    {
        this.output = new PrintWriter(clientConnection.getOutputStream(), true);
    }

    private void getInputStream(Socket clientConnection) throws IOException
    {
        this.input = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
    }

    private void displayMessage(String message) {System.out.printf("CLIENT[%d] >> %s%n", this.id, message);}

    private void sendMessage (String message)
    {
        displayMessage("SERVER >> "+ message);
        this.output.println(message);
    }

    private void processClientRequest() throws IOException
    {

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
