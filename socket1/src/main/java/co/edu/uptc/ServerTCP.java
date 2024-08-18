package co.edu.uptc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTCP {
    public static final int PORT = 1234;

    public static void main(String[] args) throws IOException {
        int count = 0;

        ServerSocket serverSocket = null;
        Socket socket = null;
        DataInputStream in;
        DataOutputStream out;

        serverSocket = new ServerSocket(PORT);

        System.out.println("Listening on port " + PORT);
        while (true) {
            socket = serverSocket.accept();

            System.out.println("CLIENT IN (COUNTER = " + ++count + ")");
            if(socket != null){
                in = new DataInputStream(socket.getInputStream());
                out = new DataOutputStream(socket.getOutputStream());

                String msg = in.readUTF();

                System.out.println(msg);

                out.writeUTF("SERVER: WASSUP NIGGA");
                socket.close();
            }
            System.out.println("CLIENT OUT");
        }
    }
}
