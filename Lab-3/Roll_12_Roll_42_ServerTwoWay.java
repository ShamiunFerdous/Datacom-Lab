import java.io.*;
import java.net.*;

public class Roll_12_Roll_42_ServerTwoWay {

    public static String bitDeStuff(String input) {
        StringBuilder destuffed = new StringBuilder();
        int count = 0;

        for (int i = 0; i < input.length(); i++) {
            char bit = input.charAt(i);
            destuffed.append(bit);

            if (bit == '1') {
                count++;
                if (count == 5 && i + 1 < input.length() && input.charAt(i + 1) == '0') {
                    i++; // Skip stuffed 0
                    count = 0;
                }
            } else {
                count = 0;
            }
        }
        return destuffed.toString();
    }

    public static String binaryToText(String binary) {
        StringBuilder text = new StringBuilder();
        for (int i = 0; i + 7 < binary.length(); i += 8) {
            String byteStr = binary.substring(i, i + 8);
            int ascii = Integer.parseInt(byteStr, 2);
            text.append((char) ascii);
        }
        return text.toString();
    }

    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(5010);
        System.out.println("Server started. Waiting for client...");

        Socket s = ss.accept();
        System.out.println("Client connected.");

        DataInputStream input = new DataInputStream(s.getInputStream());
        DataOutputStream output = new DataOutputStream(s.getOutputStream());

        String stuffed = input.readUTF();
        System.out.println("Received Stuffed Binary:\n" + stuffed);

        String destuffed = bitDeStuff(stuffed);
        System.out.println("De-Stuffed Binary:\n" + destuffed);

        String message = binaryToText(destuffed);
        System.out.println("Original Message:\n" + message);

        output.writeUTF("Server received and de-stuffed the message:\n" + message);

        s.close();
        ss.close();
    }
}
