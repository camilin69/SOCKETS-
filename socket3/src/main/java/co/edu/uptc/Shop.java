package co.edu.uptc;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.ArrayList;

public class Shop implements Runnable{

    private Stage stage;
    private ScrollPane root;
    private Scene scene;


    private String name;
    private String hostIp;
    private int port;

    private ArrayList<Fruit> fruits;
    private TableView<Fruit> infoTable;

    public Shop(String name, int port, String hostIp, ArrayList<Fruit> fruits){
        this.name = name;
        this.hostIp = hostIp;
        this.port = port;
        this.fruits = fruits;
        this.infoTable = new TableView<>();
        buildWindow();
        Platform.runLater(() -> {HandlingFruit.setupTable(fruits, infoTable);});

    }

    public void buildWindow(){
        stage = new Stage();
        root = new ScrollPane(infoTable);
        Scene scene = new Scene(root, 600, 200);
        stage.setScene(scene);
        stage.setTitle("Client: " + name + " Shop");
        stage.show();
    }



    @Override
    public void run() {

        try {
            System.out.print("ENTER " + name + " AS SHOP CLIENT");
            Socket s = new Socket(hostIp, port);

            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());

            fruits = (ArrayList<Fruit>) ois.readObject();

            Platform.runLater(() -> {
                HandlingFruit.setupTable(fruits, infoTable);
            });

            s.close();

            System.out.print("LEAVING " + name + " AS SHOP CLIENT");

        }catch(IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}
