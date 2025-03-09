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

    @FXML
    private ComboBox<String> role;


    Scene scene;
    Stage stage;
    Parent root;


    @FXML
    void initialize() {
        role.setItems(FXCollections.observableArrayList("Admin", "Guest"));
        noti.setVisible(false);
        noti.setTextFill(Color.RED);
        login.setOnAction(e -> loginAction(e));
        signup.setOnMouseClicked(e -> setSignup(e));
    }
    public void loginAction(ActionEvent actionEvent){
        String userName = username.getText();
        String pass = password.getText();
        String userRole = role.getValue();
        UserAccount user = UserAccountManager.loginCheck(userName, pass, userRole);
        if(user != null){
            if(user.getRole() == "Guest"){
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
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            System.out.println("Lỗi khi tải Signup.fxml: " + e.getMessage());
            e.printStackTrace();
        }
    }

}

