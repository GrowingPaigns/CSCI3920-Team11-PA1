package edu.ucdenver.TestApp;

import java.nio.file.Paths;

public class TestMain
{
    public static void main (String[] args)
    {
        System.out.print(Paths.get(".").toAbsolutePath().normalize().toString());
    }
}
