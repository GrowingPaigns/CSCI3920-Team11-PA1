package edu.ucdenver.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client
{
    private final int serverPort;
    private final String serverIP;
    private boolean isConnected;

    private Socket serverConnection;
    private PrintWriter output = null;
    private BufferedReader input = null;

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
            this.output = this.getOutputStream();
            this.input = this.getInputStream();

            getServerInitialResponse();
        }
        catch (IOException e)
        {
            this.input = null;
            this.output = null;
            this.serverConnection = null;
            this.isConnected = false;
            e.printStackTrace();
        }
    }

    public void disconnect()
    {
        displayMessage("\n>> Terminating Client Connection to Server");
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
            this.serverConnection.close();
        }
        catch(IOException|NullPointerException e)
        {
            e.printStackTrace();
        }
    }

    public void getServerInitialResponse() throws IOException
    {
        String srvResponse = this.input.readLine();
        displayMessage("SERVER >> " + srvResponse);
    }

    public String sendRequest (String request) throws IOException
    {
        this.output.println(request);
        displayMessage("CLIENT >> " + request);
        String srvResponse = this.input.readLine();
        displayMessage("SERVER >> " + srvResponse);
        return srvResponse;
    }
}
