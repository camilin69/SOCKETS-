package co.edu.uptc;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

public class Server extends Observable implements Runnable{


    public static final int port = 1;
    public static final String ip = "192.168.56.1";

    private ArrayList<Fruit> fruits;



    public Server(ArrayList<Fruit> fruits){
        this.fruits = fruits;
    }


    @Override
    public void run() {
        int counter = 0;
        ServerSocket serverSocket = null;
        Socket socket = null;

        try {
            serverSocket = new ServerSocket(port);

            System.out.println("Listening on port " + port);

            while (true) {

                socket = serverSocket.accept();
                if(socket != null){
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    out.writeObject(fruits);

                    socket.close();
                }

                System.out.println("COUNTER " + ++counter + " OF SUCCESS REQUEST");

            }



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public ArrayList<Fruit> getFruits() {
        return fruits;
    }

    public void setFruits(ArrayList<Fruit> fruits) {
        this.fruits = fruits;
    }
}
