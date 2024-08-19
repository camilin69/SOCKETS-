package co.edu.uptc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client implements Runnable{

    private int port;
    private String msg;

    public Client(int port, String msg) {
        this.port = port;
        this.msg = msg;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            socket = new Socket("localhost", port);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            out.writeUTF(msg);


            socket.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
