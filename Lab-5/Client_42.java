import java.io.*;
import java.net.*;
import java.util.Scanner;

public class Client_42 {
    public static String binary(String s) {
        String a = "";
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int val = (int) c;
            String bin = "";
            
            while (val > 0) {
                bin = (val % 2) + bin;
                val = val / 2;
            }
            
            while (bin.length() < 8) {
                bin = "0" + bin;
            }

            a += bin;
        }
        return a;
    }


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
        try {
            Socket socket = new Socket("10.33.24.95", 5010);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            System.out.println("Client is Connected");

            BufferedReader fileReader = new BufferedReader(new FileReader("in.txt"));
            String fileData = fileReader.readLine().trim();
            fileReader.close();

            System.out.println("File Content: " + fileData);
            String binaryData = binary(fileData);
            System.out.println("Converted Binary Data: " + binaryData);

            Scanner sc = new Scanner(System.in);
            System.out.print("Enter Generator Polynomial (binary): ");
            String key = sc.nextLine();

            StringBuilder appendedData = new StringBuilder(binaryData);
            for (int i = 1; i < key.length(); i++) {
                appendedData.append('0');
            }

            String crc = div(appendedData.toString(), key);
            System.out.println("CRC Remainder: " + crc);

            String codeword = binaryData + crc;
            System.out.println("Transmitted Codeword to Server: " + codeword);

            out.writeUTF(codeword);
            out.writeUTF(key);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
