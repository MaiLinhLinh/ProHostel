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

//    public void loadAvatar(String username, ImageView imageView) {
//        String avatarPath = getAvatarPath(username);
//        imageView.setImage(new Image(new File(avatarPath).toURI().toString())); // Hiển thị ảnh
//    }
//    private String getAvatarPath(String username) {
//        File file = new File("avatar_sources.txt");
//        if (!file.exists()) return "/Image/img_2.png"; // Nếu file chưa có, dùng ảnh mặc định
//
//        try (BufferedReader reader = new BufferedReader(new FileReader("avatar_sources.txt"))) {
//            String line;
//            while ((line = reader.readLine()) != null) {
//                String[] parts = line.split("=", 2);
//                if (parts.length == 2 && parts[0].equals(username)) {
//                    return parts[1]; // Trả về đường dẫn ảnh của username
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return "/Image/img_2.png" ; // Nếu không tìm thấy username, dùng ảnh mặc định
//    }


}

