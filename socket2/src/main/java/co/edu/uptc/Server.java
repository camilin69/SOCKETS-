package co.edu.uptc;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;

public class Server extends Observable implements  Runnable {

    private int port;

    public Server(int port) {
        this.port = port;
    }
    @Override
    public void run() {
        int count = 0;

        ServerSocket serverSocket = null;
        Socket socket = null;
        DataInputStream in;
        DataOutputStream out;

        try {
            serverSocket = new ServerSocket(port);

            System.out.println("Listening on port " + port);

            while (true) {
                socket = serverSocket.accept();

                System.out.println("CLIENT IN (COUNTER = " + ++count + ")");
                if(socket != null){
                    in = new DataInputStream(socket.getInputStream());

                    String msg = in.readUTF();

                    System.out.println(msg);

                    this.setChanged();
                    this.notifyObservers(msg);
                    this.clearChanged();

                    socket.close();
                }
                System.out.println("CLIENT OUT");
            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
