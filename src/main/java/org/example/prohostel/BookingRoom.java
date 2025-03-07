package org.example.prohostel;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class BookingRoom {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField IDCard;

    @FXML
    private TextField address;

    @FXML
    private TextField birthday;

    @FXML
    private Button cancel;

    @FXML
    private Button exit;

    @FXML
    private TextField fullName;

    @FXML
    private TextField nation;

    @FXML
    private TextField phoneNumber;

    @FXML
    private Button save;

    @FXML
    private TextField sex;

    @FXML
    void initialize() {
        exit.setOnAction(e -> exitAction());

    }
    public void exitAction(){
        System.out.println("Quay ve trang chu");
    }
    public void saveAction(){
        String name = fullName.getText();
    }
}
