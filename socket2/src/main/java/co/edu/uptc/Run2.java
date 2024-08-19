package co.edu.uptc;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

public class Run2 extends Application implements Observer {
    private VBox v1;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        AnchorPane root = new AnchorPane();
        root.setPrefSize(500, 500);
        buildWindow(root);
        Scene scene1 = new Scene(root, 500, 500);
        stage.setScene(scene1);
        stage.setX(2500);
        stage.setTitle("CHAT 2");
        stage.show();

        Server s = new Server(1);
        s.addObserver(this);
        Thread t = new Thread(s);
        t.start();
    }


    private void buildWindow(AnchorPane root) {
        VBox v = new VBox(10);
        v.setPrefSize(500, 500);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(500, 400);
        v1 = new VBox();
        v1.setPrefSize(480, 400);
        scrollPane.setContent(v1);

        TextField t = new TextField();
        t.setPromptText("Enter text");
        t.setPrefSize(400, 100);

        Button b = new Button();
        b.setText("Enter");
        b.setPrefSize(100, 100);
        b.setOnAction(e -> {
            Label msgRequest = new Label(t.getText());
            msgRequest.setPrefSize(500, 50);
            msgRequest.setAlignment(Pos.CENTER_RIGHT);
            v1.getChildren().add(msgRequest);

            Client c = new Client("192.168.27.3",2, "CHAT 2: " + msgRequest.getText());
            Thread thread = new Thread(c);
            thread.start();

        });


        v.getChildren().addAll(scrollPane, t, b);
        root.getChildren().add(v);
    }

    @Override
    public void update(Observable o, Object arg) {
        Platform.runLater(() -> {
            Label l = new Label((String) arg);
            l.setPrefSize(500, 50);
            l.setAlignment(Pos.CENTER_LEFT);
            v1.getChildren().add(l);
        });

    }
}
