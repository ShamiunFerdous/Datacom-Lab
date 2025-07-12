import java.io.*;
import java.net.*;

public class Server_12 {
    public static void main(String[] args) {
        String chipSequence = "101";
        String[] recoveredFiles = { "recovered_input1.txt", "recovered_input2.txt" };

        try {

            ServerSocket serverSocket = new ServerSocket(5100);
            System.out.println("Server is waiting for client...");

            Socket socket = serverSocket.accept();
            System.out.println("Client connected.");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));


            String receivedBits = in.readLine();
            
            
            int n1 = Integer.parseInt(in.readLine());

            
            String part1 = receivedBits.substring(0, n1);
            String part2 = receivedBits.substring(n1);

            writeToFile(recoveredFiles[0], chipSequence, part1);

            writeToFile(recoveredFiles[1], chipSequence, part2);

            in.close();
            socket.close();
            serverSocket.close();

            System.out.println("Recovered data has been written to the files.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(String fileName, String chipSequence, String data) throws IOException {
        FileWriter fw = new FileWriter(fileName);
        String binary = despread(chipSequence, data);
        for (int i = 0; i <= binary.length() - 8; i += 8) {
            String byteStr = binary.substring(i, i + 8);
            int charCode = Integer.parseInt(byteStr, 2);
            fw.write((char) charCode);
        }
        fw.close();
        System.out.println("Recovered: " + fileName);
    }

    public static String despread(String chipSequence, String receivedSignal) {
        StringBuilder rec = new StringBuilder();
        for (int i = 0; i <= receivedSignal.length() - 3; i += 3) {
            String temp = receivedSignal.substring(i, i + 3);
            String res = xor(chipSequence, temp);

            int cnt = 0;
            for (int j = 0; j < res.length(); j++) {
                if (res.charAt(j) == '1') {
                    cnt++;
                }
            }
            if (cnt >= 2) {
                rec.append("1");
            } else {
                rec.append("0");
            }
        }
        return rec.toString();
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
}
