package co.edu.uptc;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client implements Runnable{

    private String ip;
    private int port;
    private String msg;

    public Client(String ip,int port, String msg) {
        this.ip = ip;
        this.port = port;
        this.msg = msg;
    }

    @Override
    public void run() {
        Socket socket = null;
        try {
            socket = new Socket(ip, port);
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());

            out.writeUTF(msg);


            socket.close();


        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
