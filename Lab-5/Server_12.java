import java.io.*;
import java.net.*;

public class Server_12 {

    public static String xor(String a, String b) {

        char[] result = new char[b.length()];

        for (int i = 0; i < b.length(); i++) {
            if (a.charAt(i) == b.charAt(i)) {
                result[i] = '0';
            } else {
                result[i] = '1';
            }
        }

        return new String(result);
    }

    public static String div(String dividend, String divisor) {
        int l = divisor.length();
        String temp = dividend.substring(0, l);

        while (l < dividend.length()) {
            if (temp.charAt(0) == '1') {
                temp = xor(divisor, temp) + dividend.charAt(l);
            } else {
                temp = xor("0".repeat(l), temp) + dividend.charAt(l);
            }
            temp = temp.substring(1);
            l++;
        }

        if (temp.charAt(0) == '1') {
            temp = xor(divisor, temp);
        } else {
            temp = xor("0".repeat(l), temp);
        }

        return temp.substring(1);
    }

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(5010)) {
            System.out.println("Server is connected at port no: 5010");
            try (Socket socket = serverSocket.accept()) {
                DataInputStream inputStream = new DataInputStream(socket.getInputStream());
                String codeword = inputStream.readUTF();
                String generator = inputStream.readUTF();

                // char[] codewordArray = codeword.toCharArray();
                // if (codewordArray[5] == '1') {
                // codewordArray[5] = '0';
                // } else {
                // codewordArray[5] = '1';
                // }
                // codeword = new String(codewordArray);

                System.out.println("Received Codeword: " + codeword);

                String remainder = div(codeword, generator);
                System.out.println("Calculated Remainder: " + remainder);

                if (remainder.contains("1")) {
                    System.out.println("Error detected in transmission!");
                } else {
                    System.out.println("No error detected in transmission.");
                }
            }
        } catch (IOException e) {
            System.out.println("Connection error occurred.");
        }
    }
}
