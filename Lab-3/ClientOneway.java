package Graph.Lab;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.DataOutputStream;
import java.net.*;


public class ClientOneway {
    public static void main(String[] args) throws IOException {
        Socket s = new Socket("localhost", 5000);
        System.out.println("Connected to server at port: " + s.getPort());

        DataInputStream input = new DataInputStream(s.getInputStream());
        DataOutputStream output = new DataOutputStream(s.getOutputStream());
        BufferedReader console = new BufferedReader(new InputStreamReader(System.in));

        String str = "";
        String str2 = "";

        while (!str.equals("stop") && !str2.equals("stop")) {
            System.out.print("Client: ");
            str2 = console.readLine();
            output.writeUTF(str2);

            if (str2.equals("stop")) break;

            str = input.readUTF();
            System.out.println("Server: " + str);
        }

        s.close();
    }
}
