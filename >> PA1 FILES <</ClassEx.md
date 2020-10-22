package edu.cudenver.objectx;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ObjectServer {
    public static void main (String[] args) {
        /*
        Binding & Listening    X (Done)
        Accepting connections  X
        Send text              X
        Receive Text           X
         */

        try{
            ServerSocket serverSocket = new ServerSocket(10001, 5); // Open server socket and start listening
            Socket clientConnection = null;
            ObjectOutputStream output = null;                // Declare Variables
            ObjectInputStream input = null;

            while (true){
                try{
                    System.out.println("Waiting for a connection.....");
                    clientConnection = serverSocket.accept();  // When we accept the client....
                    System.out.println("Connection accepted from " + clientConnection.getInetAddress().getHostName());  // We print the client connection

                    System.out.println("Getting Data Streams.....");
                    output = new ObjectOutputStream(clientConnection.getOutputStream());
                    input = new ObjectInputStream(clientConnection.getInputStream());      // Get Input (receive) and Output (send) Data Streams

                    Thread.sleep(3000); //3 seconds

                    try {
                        output.writeObject("Connected to object Java Server");
                        output.flush();

                        Person person = (Person) input.readObject(); //converts the bytes into a Person
                        System.out.println("Received from Client >>> \n");
                        System.out.println(person);

                        //Modify Person
                        person.setName("Jane");
                        person.setAge(25);
                        person.addLivedInCity("Boulder");

                        System.out.println("\n\nSending >>> \n");
                        System.out.println(person);

                        //Send it back
                        output.writeObject(person); //convert to a stream of bytes
                        output.flush();

                    }
                    catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
                catch (InterruptedException ie){
                    ie.printStackTrace();
                }
                finally{
                    try{
                        input.close();
                        output.close();
                        clientConnection.close();
                        serverSocket.close();
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
        catch (IOException ioe){
            System.out.println("\n+++++ Cannot Open Server +++++\n");
            ioe.printStackTrace();

        }
    }
}
