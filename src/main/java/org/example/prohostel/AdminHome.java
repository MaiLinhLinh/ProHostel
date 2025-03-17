package org.example.prohostel;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

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
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.prohostel.Model.*;

public class AdminHome {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addRoom;

    @FXML
    private Button back;

    @FXML
    private Button bookRoom;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private Button delateRoom;

    @FXML
    private TableColumn<Room, Double> dongia;

    @FXML
    private Button edit;

    @FXML
    private Button guestList;

    @FXML
    private TableColumn<Room, String> hoatdong;

    @FXML
    private TableColumn<Room, String> loaiphong;

    @FXML
    private Label name;

    @FXML
    private TextField price;

    @FXML
    private Label role;

    @FXML
    private TextField roomID;

    @FXML
    private Button roomList;

    @FXML
    private TableView<Room> roomTable;

    @FXML
    private ComboBox<String> roomType;

    @FXML
    private Button setAdmin;

    @FXML
    private Button showInvoice;

    @FXML
    private TableColumn<Room, String> sophong;

    @FXML
    private TableColumn<Room, Integer> stt;

    @FXML
    private TableColumn<Room, String> trangthai;

    @FXML
    private Button updateRoom;

    @FXML
    private AnchorPane roomListPane;

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



    private RoomManager roomManager;
    private ObservableList<Room> listRoom = FXCollections.observableArrayList();


    Scene scene;
    Stage stage;
    Parent root;

    @FXML
    void initialize() {
        roomManager = new RoomManager();


        contentPane.setVisible(true);
        roomListPane.setVisible(false);
        acceptAdminPane.setVisible(false);
        roomType.getItems().addAll("Phòng đơn", "Phòng đôi");

        addRoom.setOnAction(e -> addRoomAction());
        roomList.setOnAction(e -> roomListAction());
        guestList.setOnAction(e -> guestListAction());
        pay.setOnAction(e -> payAction());
        showInvoice.setOnAction(e-> showInvoiceAction());

        bookRoom.setOnAction(e -> bookRoomAction());
        setAdmin.setOnAction(e -> setAdminAction());
        signout.setOnAction(e -> signoutAction(e));
        edit.setOnAction(e -> editAction(e));
        loadAvatar(name.getText(), imageView);


    }
    public void bookRoomAction(){
        roomListPane.setVisible(false);
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
        name.setText(userName);
    }
    public void setRole(String Role){
        role.setText(Role);
    }

    public void roomListAction(){
        contentPane.setVisible(false);
        acceptAdminPane.setVisible(false);
        roomTable.refresh();
        roomListPane.setVisible(true);
        listRoom.clear();
        System.out.println("check load file trong danh sach");
        roomManager.loadRoomsFromFile();
        for(Room room: roomManager.getRooms()){
            //System.out.println("phong " + room.getRoomID() + "co " + room.getBookings().size() + " luot dat phong");
            listRoom.add(room);
        }
        for (Room room : roomManager.getRooms()) {
            for (Booking booking : room.getBookings()) {
                System.out.println("Phòng: " + room.getRoomID() +
                        ", Checkout mới: " + booking.getCheckout());
            }
        }
        trangthai.setCellValueFactory(cellData -> {
            Room room = cellData.getValue();
            boolean isOccupied = room.isOccupied();
            System.out.println("trang thai " + room.isOccupied());
            return new SimpleStringProperty(isOccupied ? "Đang cho thuê" : "Trống");
        });


        roomTable.setStyle("-fx-alignment: CENTER;");
        stt.setCellFactory(col -> new TableCell<>(){
            protected void updateItem(Integer item, boolean empty){
                super.updateItem(item, empty);
                if(empty || getTableRow() == null){
                    setText(null);
                }else{
                    setText(String.valueOf(getIndex()+1));
                    setStyle("-fx-alignment: CENTER;");
                }
            }
        });
        sophong.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        sophong.setStyle("-fx-alignment: CENTER;");
        loaiphong.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        loaiphong.setStyle("-fx-alignment: CENTER;");
        dongia.setCellValueFactory(new PropertyValueFactory<>("price"));
        dongia.setStyle("-fx-alignment: CENTER;");

        hoatdong.setCellFactory(col -> new TableCell<>() {
            private final ComboBox<String> comboBox = new ComboBox<>();

            {
                comboBox.getItems().addAll("Chỉnh sửa", "Xóa");
                comboBox.setOnAction(e -> {
                    String selectedAction = comboBox.getValue();
                    int index = getIndex();
                    Room room = getTableView().getItems().get(index);
                    if (selectedAction.equals("Chỉnh sửa")) {
                        System.out.println("chuyen man chinh sua");
                    } else {
                        System.out.println("thuc hien xoa phong");
                    }
                    comboBox.getSelectionModel().clearSelection();
                });
            }
            protected void updateItem(String item, boolean empty){
                super.updateItem(item, empty);
                if(empty){
                    setGraphic(null);
                }
                else{
                    setGraphic(comboBox);
                }
            }
        });


        roomTable.setItems(listRoom);
    }

    public void addRoomAction(){
        String ID = roomID.getText();
        double roomPrice = 0.0;

        try {
            roomPrice = Double.parseDouble(price.getText());
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Giá phòng không hợp lệ!");
            alert.showAndWait();
        }
        String type = roomType.getValue();
        if(sophong == null || roomPrice == 0.0 || type == null){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Bạn cần điền đủ thông tin phòng!");
            alert.showAndWait();
            return;
        }
        Room room = new Room(ID, type, roomPrice);
        listRoom.add(room);
        roomManager.addRooms(room);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thêm phòng thành công");
        alert.setHeaderText(null);
        alert.setContentText("Thêm phòng thành công");
        alert.showAndWait();

    }
    public void guestListAction(){
        contentPane.getChildren().clear();
        roomListPane.setVisible(false);
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
        contentPane.getChildren().clear();
        roomListPane.setVisible(false);
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
        contentPane.getChildren().clear();
        roomListPane.setVisible(false);
        acceptAdminPane.setVisible(false);
        contentPane.setVisible(true);
        try {
            root = FXMLLoader.load(getClass().getResource("ShowInvoiceList.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        contentPane.getChildren().add(root);
    }

    public void setAdminAction(){
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
        if(user.getRole().equals("Admin")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Tài khoản này hiện đang là Admin!");
            alert.showAndWait();
            return;
        }
        if(user != null){
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
        if(user.getRole().equals("Guest")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Thông báo");
            alert.setHeaderText(null);
            alert.setContentText("Tài khoản này hiện không phải là Admin!");
            alert.showAndWait();
            return;
        }
        if(user != null){
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
        stage.show();
    }

    public void editAction(ActionEvent event){
        String currentUserName = name.getText();
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Hình ảnh", "*.png", "*.jpg", "*.jpeg", "*.gif"));
        Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            String imagePath = file.getAbsolutePath(); // Lấy đường dẫn ảnh
            saveAvatarPath(currentUserName, imagePath);
            imageView.setImage(new Image(file.toURI().toString())); // hien thi anh
        }
    }

    public void loadAvatar(String username, ImageView imageView) {
        String avatarPath = getAvatarPath(username);
        imageView.setImage(new Image(new File(avatarPath).toURI().toString())); // Hiển thị ảnh
    }
    private String getAvatarPath(String username) {
        File file = new File("avatar_sources.txt");
        if (!file.exists()) return "/Image/img_2.png"; // Nếu file chưa có, dùng ảnh mặc định

        try (BufferedReader reader = new BufferedReader(new FileReader("avatar_sources.txt"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2 && parts[0].equals(username)) {
                    return parts[1]; // Trả về đường dẫn ảnh của username
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/Image/img_2.png" ; // Nếu không tìm thấy username, dùng ảnh mặc định
    }


    // Ghi đường dẫn ảnh vào file TXT
    private void saveAvatarPath(String username, String path) {
        File file = new File("avatar_sources.txt");
        Map<String, String> avatars = new HashMap<>();

        // Đọc file hiện tại
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split("=", 2);
                    if (parts.length == 2) {
                        avatars.put(parts[0], parts[1]); // Lưu vào Map
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // Cập nhật ảnh cho user
        avatars.put(username, path);

        // Ghi lại file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("avatar_sources.txt"))) {
            for (Map.Entry<String, String> entry : avatars.entrySet()) {
                writer.write(entry.getKey() + "=" + entry.getValue() + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



}

