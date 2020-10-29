package edu.ucdenver.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import edu.ucdenver.client.Client;
import edu.ucdenver.domain.System;

public class Server implements Runnable
{
    private final int port;
    private final int backlog;
    private int connectionCounter;
    ServerSocket serverSocket;
    edu.ucdenver.domain.System system;

    //This constructor is called if data was not loaded from file.
    public Server (int port, int backlog)
    {
        this.port = port;
        this.backlog = backlog;
        this.connectionCounter = 0;
        //This is where we initialize a new System object.
        system = new System();
    }

    public Server (int port, int backlog, System system)
    {
        this.port = port;
        this.backlog = backlog;
        this.connectionCounter = 0;
        //This is where we initialize a new System object.
        this.system = system;
    }

    public Socket waitForClientConnection() throws IOException
    {
        java.lang.System.out.println("Waiting for a connection...");
        Socket clientConnection = serverSocket.accept();
        this.connectionCounter++;
        java.lang.System.out.printf("Connection #%d accepted from %s %n", this.connectionCounter, clientConnection.getInetAddress().getHostName());

        return clientConnection;
    }

    @Override
    public void run()
    {
        ExecutorService executorService = Executors.newCachedThreadPool();

        try
        {
            this.serverSocket = new ServerSocket(this.port, this.backlog);

            while (true)
            {
                try
                {
                    Socket clientConnection = this.waitForClientConnection();

                    ClientWorker cw = new ClientWorker(clientConnection, this.system, connectionCounter);

                    executorService.execute(cw);
                }
                catch (IOException ioe)
                {
                    ioe.printStackTrace();
                }
            }
        }
        catch (IOException ioe)
        {
            executorService.shutdown();
        }
        finally
        {
            executorService.shutdownNow();
            closeConnection()
;        }
    }

    private void closeConnection()
    {
        try
        {
            this.serverSocket.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}
