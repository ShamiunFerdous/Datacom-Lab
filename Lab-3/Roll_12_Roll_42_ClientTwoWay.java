package Graph.Lab;

import java.io.*;
import java.net.*;

public class Roll_12_Roll_42_ClientTwoWay {
    public static String bitStuff(String input) {
        StringBuilder output = new StringBuilder();
        int cnt = 0;
        for (char c : input.toCharArray()) {
            output.append(c);
            if (c == '1') {
                cnt++;
                if (cnt == 5) {
                    output.append('0');
                    cnt = 0;
                }
            } else {
                cnt = 0;
            }
        }
        return output.toString();
    }

    public static String textToBinary(String text) {
        StringBuilder binary = new StringBuilder();
        for (char c : text.toCharArray()) {
            binary.append(String.format("%8s", Integer.toBinaryString(c)).replace(' ', '0'));
        }
        return binary.toString();
    }

    public static void main(String[] args) throws IOException {

        BufferedReader reader = new BufferedReader(new FileReader("input.txt"));
        StringBuilder originalText = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            originalText.append(line).append("\n");
        }
        reader.close();

        String binaryData = textToBinary(originalText.toString());
        String stuffed = bitStuff(binaryData);

        Socket s = new Socket("10.33.24.95", 5010); // change IP if needed
        System.out.println("Connected to server.");

        DataOutputStream output = new DataOutputStream(s.getOutputStream());
        DataInputStream input = new DataInputStream(s.getInputStream());

        output.writeUTF(stuffed);
        System.out.println("Stuffed binary sent:\n" + stuffed);

        String serverResponse = input.readUTF();
        System.out.println("Server Response:\n" + serverResponse);

        s.close();
    }
}
