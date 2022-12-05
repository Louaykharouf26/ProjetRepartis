import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Main {
    public static void main(String args[]) throws Exception{
//Demander à l'utilisateur de se loger en tant que client ou serveur
        System.out.println("Se Connecter En tant Que ");
        System.out.println("1. Serveur ");
        System.out.println("2. Client");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int choix = Integer.parseInt(br.readLine());

        if(choix == 1){ //Se connecter en tant que serveur
            Server s = new Server();
        }
        else if(choix == 2){ //Se connecter en tant que client
            Client c = new Client();
        }
        else{
            System.out.println("Invalide :( ");
        }
    }
}
