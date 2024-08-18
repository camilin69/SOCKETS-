package co.edu.uptc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ClientTCP {
    public static final String HOST = "";

    public static void main(String[] args) throws IOException {
        Socket socket = new Socket(HOST, ServerTCP.PORT);

        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        out.writeUTF("CLIENT: HELLO NIGGA IM THE CLIENT");

        String msg = in.readUTF();
        System.out.println(msg);

        socket.close();

    }
}
