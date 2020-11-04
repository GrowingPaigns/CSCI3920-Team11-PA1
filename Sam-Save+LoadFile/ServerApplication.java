package edu.ucdenver.server;


import java.io.*;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.nio.file.Paths;

public class ServerApplication implements Serializable
{
    public static final String FILEPATH = Paths.get(".").toAbsolutePath().normalize().toString() + "system_data";

    public static Server load()
    {
        //Create a edu.ucdenver.server object and attempt to load a System object from a file
        //and initlalize the edu.ucdenver.server object. This function will return a Server object
        Server server = null;
        FileInputStream inputStream = null;
        ObjectInputStream objectInputStream = null;
        try
        {
            //Opening the file
            inputStream = new FileInputStream(FILEPATH);
            //Creating object stream from file stream.
            objectInputStream = new ObjectInputStream(inputStream);
            //Instantiating new server object
            server = new Server(10001, 5, (edu.ucdenver.domain.System) objectInputStream.readObject());
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                inputStream.close();
            }
            catch(IOException | NullPointerException e)
            {
                e.printStackTrace();
            }

            try
            {
                objectInputStream.close();
            }
            catch(IOException | NullPointerException e)
            {
                e.printStackTrace();
            }
        }

        return server;
    }

    public static void start (Server server)
    {
        ExecutorService executorService = Executors.newCachedThreadPool();

        //Then we check if the Server object passed is null, if it is,
        //we initialize it like it's a brand new edu.ucdenver.server object.
        //If not, we continue.

        if (server == null)
        {
            server = new Server(10001, 5);
        }

        try
        {
            executorService.execute(server);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public static void main (String[] args)
    {
        Server server = null;

        System.out.println("========== Welcome to Server Application ==========");
        System.out.println ("A) Load data from a file");
        System.out.println ("B) Start the edu.ucdenver.server");

        Scanner input = new Scanner(System.in);
        String choice = input.next().toUpperCase();

        switch (choice)
        {
            case "A":
                server = load();
                start(server);
                break;
            case "B":
                start(server);
                break;
        }
        saveSystem(server);
    }

    private static void saveSystem(Server server)
    {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;

        try
        {
            fileOutputStream = new FileOutputStream(FILEPATH);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);
            objectOutputStream.writeObject(server.system);
            objectOutputStream.writeObject(server);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try
            {
                fileOutputStream.close();
            }
            catch (IOException | NullPointerException e)
            {
                e.printStackTrace();
            }

            try
            {
                objectOutputStream.close();
            }
            catch (IOException | NullPointerException e)
            {
                e.printStackTrace();
            }
        }
    }
}
