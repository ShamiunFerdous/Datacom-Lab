import java.io.*;
import java.net.*;

public class Server_12 {
    public static void main(String[] args) throws IOException {
        int n = 3;
        int T = 2;
        char garbageChar = '#';

        ServerSocket serverSocket = new ServerSocket(5050);
        System.out.println("Server is listening on port 5050");

        Socket socket = serverSocket.accept();
        System.out.println("Client connected.");

        DataInputStream in = new DataInputStream(socket.getInputStream());

        BufferedWriter[] writers = new BufferedWriter[n];
        for (int i = 0; i < n; i++) {
            writers[i] = new BufferedWriter(new FileWriter("output" + (i + 1) + ".txt"));
        }

        int packetSize = T * n;
        byte[] b = new byte[packetSize];
        // Track EOF per file to stop writing after EOF for that file
        boolean[] fileEOF = new boolean[n];

        int bytesRead;
        while ((bytesRead = in.readNBytes(b, 0, packetSize)) == packetSize) {
            for (int i = 0; i < packetSize; i++) {
                int fileIndex = i % n;
                if (!fileEOF[fileIndex]) {
                    if (b[i] == (byte) garbageChar) {
                        fileEOF[fileIndex] = true;  // EOF marker found
                    } else {
                        writers[fileIndex].write((char) b[i]);
                    }
                }
            }
        }

        for (BufferedWriter writer : writers) {
            writer.close();
        }

        in.close();
        socket.close();
        serverSocket.close();
        System.out.println("Server finished receiving and writing data.");
    }
}
