
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class ServerDownload extends Thread
{ //Initialisation des ports et des chemins
    int ServerPort;
    String  Path=null;
    ServerSocket DownloadServerSocket;
    Socket DwonloadSocket=null;
    //Constructeur
    ServerDownload(int Port,String Path) {
        this.ServerPort=Port;
        this.Path=Path;
    }
    //Methode permettant de lancer le telechargement au niveau du serveur
    public void run(){
        try {
            //creation du socket responsable du telechargement du serveur
            DownloadServerSocket = new ServerSocket(ServerPort);
            DwonloadSocket = DownloadServerSocket.accept();
            new DownloadThread(DwonloadSocket,Path).start();
        }
        catch (IOException ex) {}
    }
}
