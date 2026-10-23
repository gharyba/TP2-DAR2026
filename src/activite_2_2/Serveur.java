import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Serveur {
    public static void main(String[] args) {
        try (ServerSocket serveur = new ServerSocket(5000)) {
            System.out.println("Serveur en attente...");

            try (Socket socket = serveur.accept();
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                System.out.println("Client connecte !");

                while (true) {
                    Operation operation;
                    try {
                        operation = (Operation) in.readObject();
                    } catch (EOFException e) {
                        break;
                    }

                    int x = operation.getX();
                    int y = operation.getY();
                    String resultat;

                    switch (operation.getOperateur()) {
                        case '+':
                            resultat = String.valueOf(x + y);
                            break;
                        case '-':
                            resultat = String.valueOf(x - y);
                            break;
                        case '*':
                            resultat = String.valueOf(x * y);
                            break;
                        case '/':
                            resultat = y == 0 ? "Erreur : division par zero" : String.valueOf((double) x / y);
                            break;
                        default:
                            resultat = "Erreur : operateur invalide";
                    }

                    System.out.println(x + " " + operation.getOperateur() + " " + y + " = " + resultat);
                    out.writeObject(resultat);
                    out.flush();
                }
            } catch (ClassNotFoundException e) {
                System.err.println("Objet inconnu : " + e.getMessage());
            }

            System.out.println("Connexion fermee.");
        } catch (IOException e) {
            System.err.println("Erreur reseau : " + e.getMessage());
        }
    }
}
