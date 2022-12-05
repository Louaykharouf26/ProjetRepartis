import java.io.*;
import java.net.Socket;

class DownloadThread extends Thread
{
    Socket DownloadThreadSocket;
    String Path;
    //Constructeur pour la creation du socket pour le telechargement
    public DownloadThread(Socket ThreadSocket,String directoryPath)
    {
        this.DownloadThreadSocket=ThreadSocket;
        this.Path=directoryPath;
    }
    //Methode responsable du fonctionnement du processus de telechargement
    public void run()
    {
        try
        {  //Declarations des stream , fichier à telecharger , localisation du fichier
            ObjectOutputStream objOS = new ObjectOutputStream(DownloadThreadSocket.getOutputStream());
            ObjectInputStream objIS = new ObjectInputStream(DownloadThreadSocket.getInputStream());
            String fileName = (String)objIS.readObject(); // fichier à telecharger
            String fileLocation;//Localiser le fichier (Path)
            while(true)
            {
                File myFile = new File(Path+"//"+fileName); //obtention du Path du fichier
                long length = myFile.length();
                //Association du fichier avec son path à telecharger au niveau du stream d'entrée en tant que bits
                byte [] byte_arr = new byte[(int)length];
                objOS.writeObject((int)myFile.length());
                objOS.flush();
                //Telechargement du fichier
                FileInputStream FIS=new FileInputStream(myFile);
                BufferedInputStream objBIS = new BufferedInputStream(FIS);
                objBIS.read(byte_arr,0,(int)myFile.length());
                objOS.write(byte_arr,0,byte_arr.length);
                objOS.flush();
            }
        }
        catch(Exception e)
        {

        }
    }
}