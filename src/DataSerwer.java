import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

public class DataSerwer {
    public static int sPort = 8889;
    public static int kPort = 888;
    public static int bufferSize = 1024;
    public static DatagramSocket gniazdo;

    public static void main(String[] args) throws Exception {
        gniazdo = new DatagramSocket(sPort);
        Serw();
    }

    static String Polibiusz(char s) {
        s = Character.toLowerCase(s);
        int row, col;

        row = ((s - 'a') / 5) + 1;
        col = ((s - 'a') % 5) + 1;

        if (s == 'k') {
            row = row - 1;
            col = 5 - col + 1;
        } else if (s >= 'j') {
            if (col == 1) {
                col = 6;
                row = row - 1;
            }
            col = col - 1;
        }

        return row + "" + col;
    }

    public static void Serw() throws Exception {
        System.out.println("Serwer");
        System.out.println("Host:" + InetAddress.getLocalHost());
        System.out.println("Wiadomosc");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.print("Wprowadź wiadomość: ");
            String message = scanner.nextLine();

            if (message.equals("exit")) {
                break;
            }

            String encryptedMessage = encryptMessage(message);
            gniazdo.send(new DatagramPacket(encryptedMessage.getBytes(), encryptedMessage.length(), InetAddress.getLocalHost(), kPort));
            System.out.println("Wysłano zaszyfrowaną wiadomość: " + encryptedMessage);
        }

        gniazdo.close();
        System.out.println("Serwer stop");
    }

    private static String encryptMessage(String message) {
        StringBuilder encryptedMessage = new StringBuilder();

        for (char character : message.toCharArray()) {
            if (character != ' ') {
                encryptedMessage.append(Polibiusz(character));
                encryptedMessage.append(' ');
            }
        }

        return encryptedMessage.toString().trim();
    }
}
