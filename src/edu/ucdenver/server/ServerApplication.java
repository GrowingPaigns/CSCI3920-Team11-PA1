package edu.ucdenver.server;

import java.io.File;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApplication
{
    public static Server load ()
    {
        //Create a edu.ucdenver.server object and attempt to load a System object from a file
        //and initlalize the edu.ucdenver.server object. This function will return a Server object

        return null;
    }

    public static void start (Server server)
    {
        ExecutorService executorService = Executors.newCachedThreadPool();

        //Then we check if the Server object passed is null, if it is,
        //we initialize it like it's a brand new edu.ucdenver.server object.
        //If not, we continue.
    }

    public static void main (String[] args)
    {
        Server server = null;

        System.out.println("========== Welcome to Server Application ==========");
        System.out.println ("A) Load data from a file");
        System.out.println ("B) Start the edu.ucdenver.server");

        Scanner input = new Scanner(System.in);
        String choice = input.next();

        switch (choice)
        {
            case "A":
                //edu.ucdenver.server = load();
                break;
            case "B":
                start(server);
                break;
        }
    }
}
