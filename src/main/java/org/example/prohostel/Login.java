package org.example.prohostel;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.prohostel.Model.GuestManager;
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


    Scene scene;
    Stage stage;
    Parent root;


    @FXML
    void initialize() {
        noti.setVisible(false);
        noti.setTextFill(Color.RED);
        login.setOnAction(e -> loginAction(e));
        signup.setOnMouseClicked(e -> setSignup(e));

    }
    public void loginAction(ActionEvent actionEvent){
        String userName = username.getText();
        String pass = password.getText();
        UserAccount user = UserAccountManager.loginCheck(userName, pass);
        if(user != null){
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
                scene = new Scene(root,1300, 650);
                //stage.setScene(scene);
//                stage.setResizable(false);
//                stage.setWidth(1300);
//                stage.setHeight(650);

                stage.setScene(scene);
                stage.sizeToScene();
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
                scene = new Scene(root);
                stage.setScene(scene);
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
            stage.show();
    }


}

