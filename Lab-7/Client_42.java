import java.util.*;
import java.io.*;
import java.net.*;

public class Client_42{
    public static void main(String[] args) {
        String[] files = {"input1.txt", "input2.txt"};
        String code = "101";

        try {
            Socket socket = new Socket("localhost", 5100);
            System.out.println("Client connected to server.");

            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            
            StringBuilder allSpreadData = new StringBuilder();
            int firstFileLength = 0;

            // Process first file
            FileInputStream fis1 = new FileInputStream(files[0]);
            StringBuilder firstFileData = new StringBuilder();
            int ch;
            while ((ch = fis1.read()) != -1) {
                char character = (char) ch;
                String binaryString = binary(Character.toString(character));
                String spreadData = spread(code, binaryString);
                firstFileData.append(spreadData);
            }
            fis1.close();
            firstFileLength = firstFileData.length();
            allSpreadData.append(firstFileData);

            // Process second file
            FileInputStream fis2 = new FileInputStream(files[1]);
            while ((ch = fis2.read()) != -1) {
                char character = (char) ch;
                String binaryString = binary(Character.toString(character));
                String spreadData = spread(code, binaryString);
                allSpreadData.append(spreadData);
            }
            fis2.close();

            out.println(allSpreadData.toString());
            out.println(firstFileLength);
            
            out.close();
            socket.close();
            System.out.println("Data sent and connection closed.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String spread(String a, String s) {
        String result = "";

        for (int i = 0; i < s.length(); i++) {
            result += xor(s.charAt(i), a);
        }

        return result;
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
