import java.util.*;
import java.io.*;
import java.net.*;

public class Client_42{
    public static void main(String[] args) {
        String[] files = {"input1.txt", "input2.txt"};
        String spreadingCode = "101";

        try {
            Socket socket = new Socket("10.33.27.156", 5100);
            System.out.println("Client connected to server.");

            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            for (String file : files) {
                FileInputStream fis = new FileInputStream(file);
                int ch;

                while ((ch = fis.read()) != -1) {
                    char character = (char) ch;
                    String binaryString = binary(Character.toString(character));
                    String spreadData = spread(spreadingCode, binaryString);
                    out.writeBytes(spreadData + "\n");
                }
                fis.close();
            }
            out.close();
            socket.close();
            System.out.println("Data sent and connection closed.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String spread(String a, String s) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            result.append(xor(s.charAt(i), a));
        }

        return result.toString();
    }

    public static String xor(char bit, String codeword) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < codeword.length(); i++) {
            result.append(bit == codeword.charAt(i) ? '0' : '1');
        }

        return result.toString();
    }

    public static String binary(String s) {
        StringBuilder binaryStr = new StringBuilder();

        for (char c : s.toCharArray()) {
            String bin = Integer.toBinaryString(c);
            while (bin.length() < 8) {
                bin = "0" + bin;
            }
            binaryStr.append(bin);
        }

        return binaryStr.toString();
    }
}
