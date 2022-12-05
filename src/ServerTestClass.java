import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;


// Cette classe est utilisé pour definir le bon fonctionnement du serveur deja crée
class ServerTestClass extends Thread
{   protected Socket socket;
    ArrayList<AboutFile> FileList; //les fichiers stocké dans le serveur
    //Constructeur
    public ServerTestClass(Socket clientSocket,ArrayList<AboutFile> globalArray)
    {
        this.socket=clientSocket;
        this.FileList=globalArray;
    }
    // liste de fichier au niveau du serveur
    ArrayList<AboutFile> filesList=new ArrayList<AboutFile>();
    ObjectOutputStream oos;
    ObjectInputStream ois;
    String Filesearch;  // chaine a recherché (nom du fichier à telecharger )
    int indexoffile;// va representer l'indice du fichier

    // Procedure qui assure le fonctionnement du serveur
    // lecture et stockage des fichier à partir du lien donnée au niveau du client
    public void run()
    {
        try
        {
            InputStream is=socket.getInputStream();
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(is);
            // obtenir les fichier au niveau du serveur
            filesList=(ArrayList<AboutFile>)ois.readObject();
            System.out.println("Les fichiers du dossier passé au niveau du client sont disponible au niveau serveur !");
            //boucle pour ajouter les fichier au niveau du serveur
            for(int i=0;i<filesList.size() ;i++)
            {
                FileList.add(filesList.get(i));
            }
            System.out.println("Nombre total des fichier disponible au niveau du serveur par tous les clients connectés est : " +FileList.size());
        }

        catch(IndexOutOfBoundsException e){
            System.out.println("Index out of bounds exception");
        }
        catch(IOException e){
            System.out.println("I/O exception");
        }
        catch(ClassNotFoundException e){
            System.out.println("Class not found exception");
        }

        try {
            Filesearch = (String) ois.readObject(); // represente la chaine du nom du fichier a recherché
        }
        catch (IOException | ClassNotFoundException ex) {

        }
// partie pour la recherche du fichier
        ArrayList<AboutFile> sendingPeers = new ArrayList<AboutFile>();
        System.out.println("Recherche du fichier en cours ...!!!");

        for(int j=0;j<FileList.size();j++) // parcour du liste de fichier dans le serveur
        {
            AboutFile fileInfo=FileList.get(j);
            Boolean tf=fileInfo.fileName.equals(Filesearch);
            if(tf)
            {
                indexoffile = j;
                sendingPeers.add(fileInfo);// fichier trouvé
            }
        }

        try {
            oos.writeObject(sendingPeers);// envoie du fichier au pair
        }
        catch (IOException ex) {

        }
    }
}




