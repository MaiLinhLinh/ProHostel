package org.example.prohostel;

import java.io.*;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleObjectProperty;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.prohostel.Model.*;

public class GuestHome {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button bookingRoom;

    @FXML
    private Button bookingRoomHistory;

    @FXML
    private AnchorPane contentPane;

    @FXML
    private Button edit;

    @FXML
    private Button exit;

    @FXML
    private ImageView imageView;

    @FXML
    private Button pay;

    @FXML
    private Label role;

    @FXML
    private Label username;

    @FXML
    private TableColumn<Booking, String> cccd;

    @FXML
    private TableColumn<Booking, String> checkin;

    @FXML
    private TableColumn<Booking, String> checkout;

    @FXML
    private TableColumn<Booking, String> diachi;

    @FXML
    private TableColumn<Booking, String> gioitinh;

    @FXML
    private TableColumn<Booking, String> sdt;


    @FXML
    private TableColumn<Booking, String> loaiphong;

    @FXML
    private TableColumn<Booking, String> sophong;

    @FXML
    private TableColumn<Booking, String> tenkhachhang;

    @FXML
    private ComboBox<String> loc;

    @FXML
    private TableColumn<Booking, String> ngaysinh;

    @FXML
    private TableColumn<Booking, String> quoctich;

    @FXML
    private TableColumn<Booking, Double> tongtien;

    @FXML
    private TableView<Booking> listRoomTable;

    @FXML
    private AnchorPane listRoomPane;

    @FXML
    private TableColumn<Booking, String> trangthai;

    @FXML
    private TableColumn<Booking, Integer> stt;

    Scene scene;
    Stage stage;
    Parent root;

    private String userName;
    private ObservableList<Booking> bookings = FXCollections.observableArrayList();
    private RoomManager roomManager;


    @FXML
    void initialize() {
        this.roomManager = new RoomManager();
        listRoomPane.setVisible(false);
        bookingRoom.setOnAction(e -> bookingAction(e));
        bookingRoomHistory.setOnAction(e -> bookingRoomHistoryAction());
        exit.setOnAction(e -> signoutAction(e));
        edit.setOnAction(e -> editAction(e));
        pay.setOnAction(e -> payAction());

        // hien thi stt tu dong
        stt.setCellFactory(col -> new TableCell<>() {
            protected void updateItem(Integer item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || getTableRow() == null) {
                    setText(null);
                } else {
                    setText(String.valueOf(getIndex() + 1));
                    setStyle("-fx-alignment: CENTER;");
                }
            }
        });

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
        checkin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCheckin().format(formatter)));
        checkout.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCheckout().format(formatter)));
        sophong.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoom().getRoomID()));
        loaiphong.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoom().getRoomType()));
        cccd.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGuest().getIDCard()));
        tenkhachhang.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGuest().getName()));
        sdt.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGuest().getNumberPhone()));
        ngaysinh.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGuest().getBirthday()));
        gioitinh.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGuest().getSex()));
        diachi.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGuest().getAddress()));
        quoctich.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGuest().getNational()));
        tongtien.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().caculatePrice(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), false)));
        trangthai.setCellValueFactory(cellData -> {
            Booking booking = cellData.getValue();
            System.out.println(booking.getIsPay());
            return new SimpleStringProperty(booking.getIsPay() ? "Đã thanh toán": "Chưa thanh toán");
        });

    }

    public void bookingAction(ActionEvent actionEvent){
        listRoomPane.setVisible(false);
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
        username.setText(userName);
        this.userName = userName;
        loadAvatar(userName, imageView);
    }
    public void setRole(String Role){
        role.setText(Role);
    }
    public void bookingRoomHistoryAction(){
        contentPane.setVisible(false);
        listRoomPane.setVisible(true);
        bookings.clear();
        UserAccount currentAccount = SessionManager.getCurrentAccount();
        System.out.println("tai khoan hien tai " + currentAccount.getUserName());

        for(Room room : RoomManager.getRooms()){
            for(Booking booking: room.getBookings()){
                System.out.println(booking.getUserAccount().getUserName());
                if(booking.getUserAccount().getUserName().equals(currentAccount.getUserName()) && booking.getUserAccount().getPassword().equals(currentAccount.getPassword())){
                    System.out.println("co booking");
                    bookings.add(booking);
                }
            }
        }
        listRoomTable.setItems(bookings);

    }
    public void payAction(){
        contentPane.getChildren().clear();
        contentPane.setVisible(true);
        listRoomPane.setVisible(false);
        try {
            root = FXMLLoader.load(getClass().getResource("CheckoutPayment.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        contentPane.getChildren().add(root);
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
        String currentName = userName;
        System.out.println("ten hien tai " + currentName);
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
            saveAvatarPath(currentName, imagePath);
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
}
