import java.io.*;
import java.net.*;

public class Client_42 {
    public static void main(String[] args) throws IOException {
        String[] file = {"input1.txt", "input2.txt", "input3.txt"};
        int T = 2;

        Socket socket = new Socket("localhost", 5050);
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());
        System.out.println("Client connected to server.");

        BufferedReader[] readers = new BufferedReader[file.length];
        boolean[] vis = new boolean[file.length];
        int cnt = 0;

        for (int i = 0; i < file.length; i++) {
            readers[i] = new BufferedReader(new FileReader(file[i]));
        }

        int rn = 1;

        while (cnt < file.length) {
            StringBuilder packet = new StringBuilder();

            for (int t = 0; t < T; t++) {
                for (int i = 0; i < file.length; i++) {
                    if (!vis[i]) {
                        int ch = readers[i].read();
                        if (ch != -1) {
                            packet.append((char) ch);
                        } else {
                            packet.append('#');  // EOF marker for this file
                            vis[i] = true;
                            cnt++;
                        }
                    } else {
                        packet.append('#');  // Already done, pad with #
                    }
                }
            }

            System.out.println("Round " + rn + ": " + packet);
            out.write(packet.toString().getBytes());
            rn++;
        }

        // Close all readers and output stream
        for (BufferedReader reader : readers) {
            reader.close();
        }

        out.close();
        socket.close();
        System.out.println("Client finished sending data.");
    }
}
