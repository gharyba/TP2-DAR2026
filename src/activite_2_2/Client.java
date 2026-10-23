import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("(-?\\d+)\\s*([+*/-])\\s*(-?\\d+)");

        try (Socket socket = new Socket("localhost", 5000);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
             Scanner clavier = new Scanner(System.in)) {

            System.out.println("Connecte au serveur !");

            while (true) {
                System.out.print("Operation : ");
                if (!clavier.hasNextLine()) {
                    break;
                }

                String expression = clavier.nextLine().trim();
                if (expression.equalsIgnoreCase("exit")) {
                    break;
                }

                Matcher matcher = pattern.matcher(expression);
                if (!matcher.matches()) {
                    System.out.println("Expression invalide.");
                    continue;
                }

                try {
                    int x = Integer.parseInt(matcher.group(1));
                    char operateur = matcher.group(2).charAt(0);
                    int y = Integer.parseInt(matcher.group(3));

                    out.writeObject(new Operation(x, operateur, y));
                    out.flush();

                    String resultat = (String) in.readObject();
                    System.out.println("Resultat : " + resultat);
                } catch (NumberFormatException e) {
                    System.out.println("Expression invalide.");
                }
            }

            System.out.println("Connexion fermee.");
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erreur : " + e.getMessage());
        }
    }
}
