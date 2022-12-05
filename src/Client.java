import java.io.*;
import java.net.*;
import java.util.*;
import java.util.Scanner;

public class Client
{ //Constructeur (creation du client)
    public Client()
    {
        Socket socket;
        ArrayList al;
        ArrayList<AboutFile> arrList = new ArrayList<AboutFile>();
        Scanner scanner = new Scanner(System.in);
        ObjectInputStream ois ;
        ObjectOutputStream oos ;
        String string;
        Object o,b;
        String PathToFolder=null;
        int ServerPort=0;
        try
        {   BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            System.out.println("Bienvenue !!");
            System.out.println(" ");
            System.out.println("Donner le chemin du dossier des fichier !!");
            PathToFolder=br.readLine();
            System.out.println("Donner le numero de port ou le pair va se connecter tant que serveur :");
            ServerPort=Integer.parseInt(br.readLine());//lecture du port du serveur
//creation du serveur de telechargement
            ServerDownload objServerDownload = new ServerDownload(ServerPort,PathToFolder);
            objServerDownload.start();
//creation d'un processus pour les clients
            Socket clientThread = new Socket("localhost",7799);
//association des parametres au client
            ObjectOutputStream objOutStream = new ObjectOutputStream(clientThread.getOutputStream());
            ObjectInputStream objInStream = new ObjectInputStream(clientThread.getInputStream());

            al = new ArrayList();

            socket = new Socket("localhost",7799);
            System.out.println("Client Connecte !!");

            ois = new ObjectInputStream(socket.getInputStream());
            oos = new ObjectOutputStream(socket.getOutputStream());

            System.out.println("Donner l'Id du pair associe au dossier !!");
            int readpid=Integer.parseInt(br.readLine());

            File folder = new File(PathToFolder); //creation du dossier
            File[] listofFiles = folder.listFiles();//association des fichier du dossier
            AboutFile currentFile;
            File file;
//remplissage des données relatives aux fichiers contenu dans le dossier
            for (int i = 0; i < listofFiles.length; i++) {
                currentFile= new AboutFile();
                file = listofFiles[i];
                currentFile.fileName=file.getName();
                currentFile.peerid=readpid;
                currentFile.portNumber=ServerPort;
                arrList.add(currentFile);
            }
            oos.writeObject(arrList);
            System.out.println("Donner le nom du fichier a telecharge a partir du serveur");
            String fileNameToDownload = br.readLine();
            oos.writeObject(fileNameToDownload);//associé le fichier à telecharger
            System.out.println("Attente du resultat du serveur !!");
//avoir les details du fichier au niveau du pair
            ArrayList<AboutFile> peers= new ArrayList<AboutFile>();
            peers = (ArrayList<AboutFile>)ois.readObject();
//enregistrer le fichier au niveau du pair
            for(int i=0;i<peers.size();i++)
            {
                int result = peers.get(i).peerid;
                int port = peers.get(i).portNumber;
                System.out.println("Fichier enregistre au niveau du pair dont l'id est  " +result+ " sur le port "+port);
            }
            System.out.println("Donner le port du pair precedent :");
            int clientAsServerPortNumber = Integer.parseInt(br.readLine());
            System.out.println("Donner l'id du pair a partir du quel on veut telecharger le fichier :");
            int clientAsServerPeerid = Integer.parseInt(br.readLine());
            clientAsServer(clientAsServerPeerid,clientAsServerPortNumber,fileNameToDownload,PathToFolder);
        }
        catch(Exception e)
        {
            { System.out.println("Erreur lors de la creation de la connection serveur - client !! Verifier le numero du port ou l'adresse ! "); }
        }
    }
    //Creation d'un client comme etant un serveur
    public static void clientAsServer(int clientAsServerPeerid, int clientAsServerPortNumber, String fileNamedwld, String directoryPath) throws ClassNotFoundException
    {
        try {
            //Creation du socket les stream de l'entrée et sortie
            Socket clientAsServersocket = new Socket("localhost",clientAsServerPortNumber);
            ObjectOutputStream clientAsServerOOS = new ObjectOutputStream(clientAsServersocket.getOutputStream());
            ObjectInputStream clientAsServerOIS = new ObjectInputStream(clientAsServersocket.getInputStream());
            //Association du fichier avec son path à telecharger au niveau du stream d'entrée en tant que bits
            clientAsServerOOS.writeObject(fileNamedwld);
            int readBytes=(int) clientAsServerOIS.readObject();
            byte[] b=new byte[readBytes];
            clientAsServerOIS.readFully(b);
            OutputStream  fileOPstream = new FileOutputStream(directoryPath+"//"+fileNamedwld);
            //telechargement du fichier
            BufferedOutputStream BOS = new BufferedOutputStream(fileOPstream);
            BOS.write(b, 0,(int) readBytes);

            System.out.println("Le fichier demandé -"+fileNamedwld+ ", est telecharge dans le dossier precise "+directoryPath);
            System.out.println(" ");
            System.out.println("Le fichier est : "+fileNamedwld);

            BOS.flush();
        }
        catch (IOException ex) { }
    }
}

