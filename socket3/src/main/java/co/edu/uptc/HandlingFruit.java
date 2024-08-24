package co.edu.uptc;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.Optional;

public class HandlingFruit {

    @FXML
    private AnchorPane updateBox;
    @FXML
    private VBox infoBox;
    @FXML
    private TableView<Fruit> infoTable;


    private ArrayList<Fruit> fruits;

    private Server server;

    private ArrayList<Shop> shops;


    public static void setupFruits(ArrayList<Fruit> fruits){
        fruits.add(new Fruit("1", "Banana", 500.0d));
        fruits.add(new Fruit("2", "Apple", 1000.0d));
        fruits.add(new Fruit("3", "Orange", 1500.0d));
        fruits.add(new Fruit("4", "Onion", 800.0d));
        fruits.add(new Fruit("5", "Tomato", 1300.0d));

    }

    public static void setupTable(ArrayList<Fruit> fruits, TableView<Fruit> infoTable){
        infoTable.getColumns().clear();
        TableColumn<Fruit, String> idCol = new TableColumn<>("ID");
        idCol.setPrefWidth(200);
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        TableColumn<Fruit, String> nameCol = new TableColumn<>("NAME");
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameCol.setPrefWidth(200);
        TableColumn<Fruit, Double> priceCol = new TableColumn<>("PRICE");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        priceCol.setPrefWidth(200);

        infoTable.getColumns().addAll(idCol, nameCol, priceCol);

        ObservableList<Fruit> fruitsObs = FXCollections.observableArrayList(fruits);
        infoTable.setItems(fruitsObs);
    }


    public void initialize() throws IOException {
        fruits = new ArrayList<>();
        setupFruits(fruits);
        Platform.runLater(() -> {setupTable(fruits, infoTable);});

        shops = new ArrayList<>();
        server = new Server(fruits);
        Thread t = new Thread(server);
        t.start();
    }

    @FXML
    void addFruit(ActionEvent event) {
        String id = ((TextField) infoBox.getChildren().get(0)).getText();
        String name = ((TextField) infoBox.getChildren().get(1)).getText();
        String price = ((TextField) infoBox.getChildren().get(2)).getText();
        if(id.isEmpty() || name.isEmpty() || price.isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please fulfill the fields");
            alert.showAndWait();
        }else{
            fruits.add(new Fruit(id, name, Double.parseDouble(price)));
            Platform.runLater(() -> {setupTable(fruits, infoTable);});
            Platform.runLater(() -> {
                shops.forEach(s -> {
                    Thread t = new Thread(s);
                    t.start();
                });
            });
        }

    }

    @FXML
    void addShop(ActionEvent event) {
        Stage stage = new Stage();
        stage.setTitle("Add Shop");
        stage.initModality(Modality.APPLICATION_MODAL);
        TextField shopName = new TextField();
        Button b = new Button("ADD");
        b.setOnAction(e -> {
            Platform.runLater(() -> {
                shops.add(new Shop(shopName.getText(), Server.port, Server.ip, fruits));
            });

            stage.close();
        });
        VBox root = new VBox(shopName, b);
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.showAndWait();
    }

    @FXML
    void updateFruit(ActionEvent event) {
        String id = ((TextField) updateBox.getChildren().get(0)).getText();
        String name = ((TextField) updateBox.getChildren().get(1)).getText();
        String price = ((TextField) updateBox.getChildren().get(2)).getText();

        Optional<Fruit> fruit = fruits.stream().filter(f -> f.getId().equals(id)).findAny();

        if(fruit.isPresent()){
            fruit.get().setName(name);
            fruit.get().setPrice(Double.parseDouble(price));

            Platform.runLater(() -> {
                shops.forEach(s -> {
                    Thread t = new Thread(s);
                    t.start();
                });
            });


        }else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Please enter a valid fruit ID");
            alert.showAndWait();
        }


    }

    @FXML
    void switchIt(ActionEvent event) {
        if (((Toggle) event.getSource()).isSelected()) {
            updateBox.setVisible(true);
            infoBox.setVisible(false);
        } else {
            updateBox.setVisible(false);
            infoBox.setVisible(true);
        }
    }


}
