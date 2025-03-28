package org.example.prohostel;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.prohostel.Model.GuestManager;
import org.example.prohostel.Model.SessionManager;
import org.example.prohostel.Model.UserAccount;
import org.example.prohostel.Model.UserAccountManager;
public class Login {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label fogotnoti;

    @FXML
    private Button login;

    @FXML
    private Label noti;

    @FXML
    private TextField password;

    @FXML
    private Label signup;

    @FXML
    private TextField username;
    private UserAccountManager userAccountManager;


    Scene scene;
    Stage stage;
    Parent root;


    @FXML
    void initialize() {
        this.userAccountManager = new UserAccountManager();
        noti.setVisible(false);
        noti.setTextFill(Color.RED);
        login.setOnAction(e -> loginAction(e));
        signup.setOnMouseClicked(e -> setSignup(e));
        login.setOnMouseEntered(e -> login.setStyle("-fx-background-color: ForestGreen; -fx-background-radius: 10; -fx-text-fill: white;"));
        login.setOnMouseExited(e -> login.setStyle("-fx-background-color: green; -fx-background-radius: 10; -fx-text-fill: white;"));
        signup.setOnMouseEntered(event -> {
            signup.setUnderline(true); // Gạch chân khi di chuột vào
        });

        signup.setOnMouseExited(event -> {
            signup.setUnderline(false); // Bỏ gạch chân khi chuột rời đi
        });

    }

    public void loginAction(ActionEvent actionEvent){
        String userName = username.getText();
        String pass = password.getText();
        UserAccount user = UserAccountManager.loginCheck(userName, pass);
        if(user != null){
            SessionManager.setCurrentAccount(user);
            String userRole = user.getRole();
            if(userRole.equals("Guest")){
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("GuestHome.fxml"));
                try {
                    root = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                GuestHome guestHome =fxmlLoader.getController();
                guestHome.setUserName(userName);
                guestHome.setRole(userRole);
                stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root,1365, 650);

                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();
            }
            else{
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("AdminHome.fxml"));
                try {
                    root = fxmlLoader.load();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                AdminHome adminHome =fxmlLoader.getController();
                adminHome.setUserName(userName);
                adminHome.setRole(userRole);
                stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
                scene = new Scene(root, 1365, 650);
                stage.setScene(scene);
                stage.centerOnScreen();
                stage.show();

            }
            return;
        }
        noti.setVisible(true);
    }

    public void setSignup(MouseEvent event) {
        try {
            root = FXMLLoader.load(getClass().getResource("Signup.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }



}

