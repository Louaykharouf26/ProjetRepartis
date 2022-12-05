import java.io.*;
import java.net.*;
import java.net.Socket;
import java.net.ServerSocket;
import java.util.Scanner;
import java.lang.Runnable;
import java.util.ArrayList;
import java.util.Collections;
import java.lang.Integer;

public class Server
{
    public static ArrayList<AboutFile> ListFile = new ArrayList<AboutFile>();
    //Constructeur du serveur
    public Server() throws NumberFormatException, IOException
    {
        ServerSocket serverSocket=null;
        Socket socket = null;
        try{//Creation du socket pour le serveur
            serverSocket = new ServerSocket(7799);
            System.out.println("Serveur en fonction ");
            System.out.println(" ");
            System.out.println("En Attente de client !!");
        }
        catch(IOException e)
        { e.printStackTrace(); }
        while(true)
        { try{ // le serveur accepte la 1ere requete
            socket = serverSocket.accept();}
        catch(IOException e)
        {
            System.out.println("I/O error: " +e);
        }
            new ServerTestClass(socket,ListFile).start();
        }
    }
}

