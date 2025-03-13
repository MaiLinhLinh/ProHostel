package org.example.prohostel;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.prohostel.Model.Room;
import org.example.prohostel.Model.RoomManager;

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
        roomType.getItems().addAll("Phòng đơn", "Phòng đôi");

        addRoom.setOnAction(e -> addRoomAction());
        roomList.setOnAction(e -> roomListAction());
        guestList.setOnAction(e -> guestListAction());
        pay.setOnAction(e -> payAction());

        bookRoom.setOnAction(e -> bookRoomAction());


    }
    public void bookRoomAction(){
        roomListPane.setVisible(false);
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
        roomListPane.setVisible(true);
        listRoom.clear();
        System.out.println("check load file trong danh sach");
        roomManager.loadRoomsFromFile();
        trangthai.setCellValueFactory(cellData -> {
            Room room = cellData.getValue();
            boolean isOccupied = room.isOccupied();
            System.out.println("trang thai " + room.isOccupied());
            return new SimpleStringProperty(isOccupied ? "Đang cho thuê" : "Trống");
        });


        for(Room room: roomManager.getRooms()){
            System.out.println("phong " + room.getRoomID() + "co " + room.getBookings().size() + " luot dat phong");
            listRoom.add(room);
        }
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
        contentPane.setVisible(true);
        try {
            root = FXMLLoader.load(getClass().getResource("CheckoutPayment.fxml"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        contentPane.getChildren().add(root);
    }




}

