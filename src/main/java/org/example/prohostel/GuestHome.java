package org.example.prohostel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.prohostel.Model.UserAccount;

public class GuestHome {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button bookingRoom;

    @FXML
    private Button bookingRoomHistory;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private Label role;

    @FXML
    private Button showInvoice;

    @FXML
    private Label username;

    Scene scene;
    Stage stage;
    Parent root;


    @FXML
    void initialize() {
        bookingRoom.setOnAction(e -> bookingAction(e));


    }

    public void bookingAction(ActionEvent actionEvent){
        try {
            root = FXMLLoader.load(getClass().getResource("BookingRoom.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        contentPane.getChildren().clear();
        contentPane.getChildren().add(root);

    }

    public void setUserName(String userName){
        username.setText(userName);
    }
    public void setRole(String Role){
        role.setText(Role);
    }
}
