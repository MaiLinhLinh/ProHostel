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
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.example.prohostel.Model.User;
import org.example.prohostel.Model.UserManager;
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
        login.setOnAction(e -> loginAction());
        signup.setOnMouseClicked(e -> setSignup(e));
    }
    public void loginAction(){
        String userName = username.getText();
        String pass = password.getText();
        User user = UserManager.loginCheck(userName, pass);
        if(user != null){
            String role = user.getRole();
            if(role.equals("ADMIN")){
                System.out.println("Admin");
            }
            else{
                System.out.println("user");
            }
            return;
        }
        noti.setVisible(true);
    }
//    public void setSignup(MouseEvent mouseEvent) throws IOException {
//        root = FXMLLoader.load(Signup.class.getResource("Signup.fxml"));
//        stage = (Stage) (Node) mouseEvent.getSource().getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }
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

