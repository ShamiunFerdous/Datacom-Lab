package Graph.Lab;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.net.*;


public class ServerOneWay {
    public static void main(String[] args) throws IOException {
        ServerSocket ss = new ServerSocket(5000);
        System.out.println("Server started on port: " + ss.getLocalPort());
        System.out.println("Waiting for client...");

        Socket s = ss.accept();
        System.out.println("Client connected from port: " + s.getPort());

        DataInputStream input = new DataInputStream(s.getInputStream());
        DataOutputStream output = new DataOutputStream(s.getOutputStream());
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        String str2 = "";
        String str = "";

        while (!str2.equals("stop") && !str.equals("stop")) {
            // Read from client
            str2 = input.readUTF();
            System.out.println("Client: " + str2);

            if (str2.equals("stop")) break;

            // Write to client
            System.out.print("Server: ");
            str = console.readLine();
            output.writeUTF(str);
        }

        s.close();
        ss.close();
    }
}
