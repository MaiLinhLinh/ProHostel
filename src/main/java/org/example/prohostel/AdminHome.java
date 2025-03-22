package org.example.prohostel;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.prohostel.Model.*;

public class AdminHome {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;


    @FXML
    private Button back;

    @FXML
    private Button bookRoom;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private Button edit;

    @FXML
    private Button guestList;

    @FXML
    private Label name;

    @FXML
    private Label role;

    @FXML
    private Button roomList;

    @FXML
    private Button setAdmin;

    @FXML
    private Button showInvoice;

    @FXML
    private Button pay;

    @FXML
    private AnchorPane acceptAdminPane;

    @FXML
    private TextField userAccount;

    @FXML
    private TextField passwordAccount;

    @FXML
    private Button accept;

    @FXML
    private Label noti;

    @FXML
    private Button exitacceptPane;

    @FXML
    private Button signout;

    @FXML
    private ImageView imageView;

    @FXML
    private Button removeAdmin;

    @FXML
    private Region bookRoomline;

    @FXML
    private Region invoiceLine;
    @FXML
    private Region roomListLine;
    @FXML
    private Region guestListLine;
    @FXML
    private Region payLine;
    @FXML
    private Region fixLine;

    private RoomManager roomManager;
    private ObservableList<Room> listRoom = FXCollections.observableArrayList();
    private String username;

    private Button selectedButton = null;
    private Region selectRegion = null;


    Scene scene;
    Stage stage;
    Parent root;

    @FXML
    void initialize() {
        roomManager = new RoomManager();

        contentPane.setVisible(true);
        acceptAdminPane.setVisible(false);

        roomList.setOnAction(e -> roomListAction());
        guestList.setOnAction(e -> guestListAction());
        pay.setOnAction(e -> payAction());
        showInvoice.setOnAction(e-> showInvoiceAction());

        bookRoom.setOnAction(e -> bookRoomAction());

        buttonclickMouseColor(bookRoom);
        buttonclickMouseColor(showInvoice);
        buttonclickMouseColor(setAdmin);
        buttonclickMouseColor(roomList);
        buttonclickMouseColor(guestList);
        buttonclickMouseColor(pay);

        setAdmin.setOnAction(e -> setAdminAction());
        signout.setOnAction(e -> signoutAction(e));

        edit.setOnAction(e -> editAction(e));
        edit.setOnMouseEntered(e -> edit.setStyle("-fx-background-color: green;  -fx-background-radius: 10; -fx-text-fill: white;"));
        edit.setOnMouseExited(e -> edit.setStyle("-fx-background-color: #E75480;  -fx-background-radius: 10; -fx-text-fill: white;"));

        // chinh mau
        exitacceptPane.setOnMouseEntered(e -> exitacceptPane.setStyle("-fx-background-color: red; -fx-text-fill: white;"));
        exitacceptPane.setOnMouseExited(e -> exitacceptPane.setStyle("-fx-background-color: Gainsboro; -fx-text-fill: black;"));
        buttonClickAction(accept);
        buttonClickAction(removeAdmin);
        buttonClickAction(signout);


    }
    public void bookRoomAction(){
        buttonClickAction(bookRoom,bookRoomline);
        acceptAdminPane.setVisible(false);
        contentPane.setVisible(true);
        try {
            root = FXMLLoader.load(getClass().getResource("BookingRoom.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        contentPane.getChildren().clear();
        contentPane.getChildren().add(root);
    }
    public void setUserName(String userName){
        this.username = userName;
        name.setText(userName);
        loadAvatar(username, imageView);

    }
    public void setRole(String Role){
        role.setText(Role);
    }

    public void roomListAction(){
        buttonClickAction(roomList, roomListLine);
        contentPane.setVisible(true);
        acceptAdminPane.setVisible(false);
        try {
            root = FXMLLoader.load(getClass().getResource("ShowRoomList.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        contentPane.getChildren().add(root);
    }


    public void guestListAction(){
        buttonClickAction(guestList, guestListLine);
        contentPane.getChildren().clear();

        acceptAdminPane.setVisible(false);
        contentPane.setVisible(true);
        try {
            root = FXMLLoader.load(getClass().getResource("ShowGuestList.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        contentPane.getChildren().add(root);
    }
    public void payAction(){
        buttonClickAction(pay, payLine);
        contentPane.getChildren().clear();

        acceptAdminPane.setVisible(false);
        contentPane.setVisible(true);
        try {
            root = FXMLLoader.load(getClass().getResource("CheckoutPayment.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        contentPane.getChildren().add(root);
    }
    public void showInvoiceAction(){
        buttonClickAction(showInvoice,invoiceLine);
        contentPane.getChildren().clear();
        acceptAdminPane.setVisible(false);
        contentPane.setVisible(true);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ShowInvoiceList.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        contentPane.getChildren().add(root);
    }

    public void setAdminAction(){
        buttonClickAction(setAdmin, fixLine);
        acceptAdminPane.setVisible(true);
        noti.setVisible(false);
        accept.setOnAction(e -> acceptAdminAction());
        removeAdmin.setOnAction(e -> removeAdminAction());
        exitacceptPane.setOnAction(e -> {
            acceptAdminPane.setVisible(false);
        });
    }
    public void acceptAdminAction(){

        String name = userAccount.getText();
        String pass = passwordAccount.getText();
        UserAccount user = UserAccountManager.loginCheck(name, pass);
        if(user != null){
            if(user.getRole().equals("Admin")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Tài khoản này hiện đang là Admin!");
                alert.showAndWait();
                return;
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Bạn chắc chắn muốn nâng cấp tài khoản này thành Admin?");
            alert.showAndWait().ifPresent(response -> {
                if(response == ButtonType.OK){
                    user.setRole("Admin");
                    for(int i = 0; i < UserAccountManager.getAccounts().size(); i++){
                        UserAccount account = UserAccountManager.getAccounts().get(i);
                        if(user.getUserName().equals(account.getUserName()) && user.getPassword().equals(account.getPassword())){
                            UserAccountManager.getAccounts().set(i, user);
                        }
                    }
                    UserAccountManager.saveAccountsToFile();
                    Alert succesAlert = new Alert(Alert.AlertType.INFORMATION);
                    succesAlert.setTitle("Thông báo");
                    succesAlert.setHeaderText(null);
                    succesAlert.setContentText("Đã nâng cấp tài khoản thành công!");
                    succesAlert.showAndWait();
                }
            });
        }else{
            noti.setVisible(true);
            noti.setTextFill(Color.RED);
        }
    }
    public void removeAdminAction(){
        String name = userAccount.getText();
        String pass = passwordAccount.getText();
        UserAccount user = UserAccountManager.loginCheck(name, pass);
        if(user != null){
            if(user.getRole().equals("Guest")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thông báo");
                alert.setHeaderText(null);
                alert.setContentText("Tài khoản này hiện không phải là Admin!");
                alert.showAndWait();
                return;
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Bạn chắc chắn muốn xoá quyền Admin của tài khoản này?");
            alert.showAndWait().ifPresent(response -> {
                if(response == ButtonType.OK){
                    user.setRole("Guest");
                    for(int i = 0; i < UserAccountManager.getAccounts().size(); i++){
                        UserAccount account = UserAccountManager.getAccounts().get(i);
                        if(user.getUserName().equals(account.getUserName()) && user.getPassword().equals(account.getPassword())){
                            UserAccountManager.getAccounts().set(i, user);
                        }
                    }
                    UserAccountManager.saveAccountsToFile();
                    Alert succesAlert = new Alert(Alert.AlertType.INFORMATION);
                    succesAlert.setTitle("Thông báo");
                    succesAlert.setHeaderText(null);
                    succesAlert.setContentText("Đã xoá vai trò Admin thành công!");
                    succesAlert.showAndWait();
                }
            });
        }else{
            noti.setVisible(true);
            noti.setTextFill(Color.RED);
        }

    }
    public void signoutAction(ActionEvent event){
        try {
            root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root, 500, 300);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void editAction(ActionEvent event){
        String currentUserName = username;
        System.out.println("ten hien tai " + currentUserName);
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Hình ảnh", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            String imagePath = file.getAbsolutePath(); // Lấy đường dẫn ảnh
            Image image = new Image("file:///" + imagePath, false);
            if (image.isError()) {
                System.out.println("⚠️ Lỗi khi tải ảnh: " + imagePath);
            } else {
                System.out.println("✅ Ảnh đã được tải thành công!");
            }
            saveAvatarPath(currentUserName, imagePath);
            imageView.setImage(new Image(file.toURI().toString())); // hien thi anh
        }
    }

    public void loadAvatar(String username, ImageView imageView) {
        String avatarPath = getAvatarPath(username);
        imageView.setImage(new Image(avatarPath));
        imageView.setFitWidth(150);       // Khung ImageView cố định chiều rộng
        imageView.setFitHeight(150);      // Khung ImageView cố định chiều cao
        imageView.setPreserveRatio(false); // Giữ tỷ lệ của ảnh
        imageView.setSmooth(true);
        Circle clip = new Circle(75, 75, 75);
        imageView.setClip(clip);

    }
    private String getAvatarPath(String username) {
        File file = new File("avatar_sources.txt");
        if (!file.exists()) {
            return getClass().getResource("/Image/img_2.png").toExternalForm();
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("=", 2);
                System.out.println(parts[0].trim().equals(username));
                if (parts.length == 2 && parts[0].trim().equals(username)) {
                    String avatarPath = parts[1].replace("\\", "/");
                    File avatarFile = new File(avatarPath);
                    if (!avatarFile.exists()) {
                        System.out.println("⚠️ Ảnh không tồn tại: " + avatarPath);
                    } else {
                        System.out.println("✅ Ảnh tồn tại: " + avatarPath);
                        return avatarFile.toURI().toString();
                    }
                }
                else {
                    // Debug xem vì sao không khớp
                    System.out.println("Không match username. parts[0] = '"
                            + (parts.length > 0 ? parts[0] : "") + "'");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return getClass().getResource("/Image/img_2.png").toExternalForm();
    }


    // Ghi đường dẫn ảnh vào file TXT
    private void saveAvatarPath(String username, String path) {
        System.out.println("Da vao luu duong dan anh");
        File file = new File("avatar_sources.txt");
        Map<String, String> avatars = new HashMap<>();

        // Đọc file hiện tại
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                System.out.println("da vao doc file hien tai");
                String line;
                while ((line = reader.readLine()) != null) {
                    System.out.println("vao doc tung dong");
                    String[] parts = line.split("=", 2);
                    if (parts.length == 2) {
                        System.out.println("luu avatar cu");
                        avatars.put(parts[0], parts[1]); // Lưu vào Map
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Cập nhật ảnh cho user
        avatars.put(username, path);
        System.out.println("check map");
        for (Map.Entry<String, String> entry : avatars.entrySet()) {
            System.out.println(entry.getKey() + "=" + entry.getValue() + "\n");

        }

        // Ghi lại file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("avatar_sources.txt"))) {
            for (Map.Entry<String, String> entry : avatars.entrySet()) {
                System.out.println(entry.getKey() + "=" + entry.getValue() + "\n");
                writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
                System.out.println("da vao ghi lai file");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void buttonClickAction(Button button, Region region){
        if(selectRegion != null){
            selectRegion.setOpacity(0);
        }
        if(selectedButton != null)
            selectedButton.setStyle("-fx-background-color:  #E75480; -fx-background-radius: 20; -fx-text-fill: white;");
        selectRegion = region;
        selectedButton = button;
        selectedButton.setStyle("-fx-background-color: White; -fx-background-radius: 20; -fx-text-fill: black;");
        selectRegion.setOpacity(1);

    }
    private void buttonclickMouseColor(Button button){
        button.setOnMouseEntered(e -> {
            if(selectedButton != button)
                button.setStyle("-fx-background-color:  #EE799F; -fx-background-radius: 20; -fx-text-fill: white;");
        });
        button.setOnMouseExited(e -> {
            if(selectedButton != button)
            button.setStyle("-fx-background-color: #E75480; -fx-background-radius: 20; -fx-text-fill: white;");
        });

    }
    private void buttonClickAction(Button button){
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: ForestGreen; -fx-background-radius: 10; -fx-text-fill: white;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: green; -fx-background-radius: 10; -fx-text-fill: white;"));

    }



}

