package org.example.prohostel;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.example.prohostel.Model.UserManager;

public class Signup {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField admin;

    @FXML
    private Label noti;

    @FXML
    private TextField password;

    @FXML
    private Button signup;

    @FXML
    private TextField username;

    @FXML
    void initialize() {
        noti.setVisible(false);
        noti.setTextFill(Color.RED);
        signup.setOnAction(e -> signupAction());

    }
    public void signupAction(){
        String userName = username.getText();
        String pass = password.getText();
        String role = admin.getText();
        if(role.equals("admin"))
            role = "ADMIN";
        else if(role == null)
            role = "USER";

        if(UserManager.register(userName, pass,role)){
            System.out.println("Dang ki thanh cong");
            return;
        }
        noti.setVisible(true);
    }
}
