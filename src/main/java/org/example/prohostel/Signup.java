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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import org.controlsfx.control.action.Action;
import org.example.prohostel.Model.UserAccountManager;

public class Signup {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private Label noti;

    @FXML
    private TextField password;

    @FXML
    private Button signup;

    @FXML
    private TextField username;

    @FXML
    private Button back;

    @FXML
    private TextField confirm;

    @FXML
    private Label noticonfirm;



    Scene scene;
    Stage stage;
    Parent root;

    @FXML
    void initialize() {
        noti.setVisible(false);
        noti.setTextFill(Color.RED);
        noticonfirm.setVisible(false);
        noticonfirm.setTextFill(Color.RED);
        signup.setOnAction(e -> signupAction());
        signup.setOnMouseEntered(e -> signup.setStyle("-fx-background-color: ForestGreen; -fx-background-radius: 10; -fx-text-fill: white;"));
        signup.setOnMouseExited(e -> signup.setStyle("-fx-background-color: green; -fx-background-radius: 10; -fx-text-fill: white;"));
        back.setOnAction(e->backAction(e));
        Image image = new Image(getClass().getResourceAsStream("/Image/img_1.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(30);
        imageView.setFitHeight(30);
        back.setGraphic(imageView);
        back.setOnMouseEntered(e -> back.setStyle("-fx-background-color: Gainsboro; -fx-background-radius: 30; -fx-text-fill: white;"));
        back.setOnMouseExited(e -> back.setStyle("-fx-background-color: transparent; -fx-background-radius: 30; -fx-text-fill: white;"));


    }
    public void signupAction(){
        String userName = username.getText();
        String pass = password.getText();
        String confirmPass = confirm.getText();
        if(!pass.equals(confirmPass)){
            noticonfirm.setVisible(true);
            return;
        }
        if (UserAccountManager.register(userName, pass)) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Đăng kí thành công");
            alert.setHeaderText(null);
            alert.setContentText("Bạn đã đăng ký thành công!");
            alert.showAndWait();
            return;
        }
        noti.setVisible(true);
    }
    public void backAction(ActionEvent actionEvent){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Login.fxml"));
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage)((Node) actionEvent.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
