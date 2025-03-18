package org.example.prohostel;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.example.prohostel.Model.Booking;
import org.example.prohostel.Model.Invoice;
import org.example.prohostel.Model.Room;
import org.example.prohostel.Model.RoomManager;

public class ShowRoomList {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button addRoom;

    @FXML
    private TableColumn<?, ?> dongia;

    @FXML
    private TableColumn<?, ?> loaiphong;

    @FXML
    private TextField price;

    @FXML
    private TextField roomID;

    @FXML
    private ComboBox<String> roomType;

    @FXML
    private TableColumn<Room, String> sophong;

    @FXML
    private TableColumn<Room, Integer> stt;

    @FXML
    private TableColumn<Room, Void> sua;

    @FXML
    private TableColumn<Room, String> trangthai;

    @FXML
    private TableColumn<Room, Void> xoa;

    @FXML
    private TableView<Room> roomTable;

    @FXML
    private TextField newRoomPrice;

    @FXML
    private ComboBox<String> newRoomType;
    @FXML
    private Button cancelnewIF;

    @FXML
    private Label IDRoom;

    @FXML
    private Button savenewIF;

    @FXML
    private AnchorPane fixIFPane;

    private RoomManager roomManager;
    private ObservableList<Room> listRoom = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        this.roomManager = new RoomManager();
        fixIFPane.setVisible(false);
        roomTable.refresh();
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
        xoa.setCellFactory(col -> new TableCell<>(){
            private final Button editButton = new Button();
            {
                ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/Image/img_6.png")));
                imageView.setFitWidth(16); // Đặt kích thước hình ảnh
                imageView.setFitHeight(16);
                editButton.setGraphic(imageView);
                editButton.setStyle("-fx-background-color: transparent;");// xoa nen button
                editButton.setOnAction(e ->{
                    int index = getIndex();
                    Room room = getTableView().getItems().get(index);
                    delateRoom(room, getTableView().getItems());
                });

            }
            protected void updateItem (Void item, boolean empty){ // phuong thuc cap nhat du lieu cho tung dong
                super.updateItem(item, empty);
                setGraphic(empty? null: editButton);
            }

        });
        xoa.setStyle("-fx-alignment: CENTER;");

//        cancelnewIF.setOnAction(e -> {
//            fixIFPane.setVisible(false);
//        });
        sua.setCellFactory(col -> new TableCell<>(){
            private final Button editButton = new Button();
            {
                ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/Image/img_4.png")));
                imageView.setFitWidth(16); // Đặt kích thước hình ảnh
                imageView.setFitHeight(16);
                editButton.setGraphic(imageView);
                editButton.setStyle("-fx-background-color: transparent;");// xoa nen button
                editButton.setOnAction(e ->{
                    int index = getIndex();
                    Room room = getTableView().getItems().get(index);
                    if(room.isOccupied()== true){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Error");
                        alert.setHeaderText(null);
                        alert.setContentText("Không thể thay đổi thông tin phòng đang cho thuê!");
                        alert.showAndWait();
                        return;
                    }
                    fixIFPane.setVisible(true);
                    savenewIF.setOnAction(event -> fixRoomAction(room, getTableView().getItems()));
                    cancelnewIF.setOnAction(event -> {
                        fixIFPane.setVisible(false);
                    });
                });
            }
            protected void updateItem (Void item, boolean empty){ // phuong thuc cap nhat du lieu cho tung dong
                super.updateItem(item, empty);
                setGraphic(empty? null: editButton);
            }

        });
        sua.setStyle("-fx-alignment: CENTER;");
        sophong.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        sophong.setStyle("-fx-alignment: CENTER;");
        loaiphong.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        loaiphong.setStyle("-fx-alignment: CENTER;");
        dongia.setCellValueFactory(new PropertyValueFactory<>("price"));
        dongia.setStyle("-fx-alignment: CENTER;");
        roomType.getItems().addAll("Phòng đơn", "Phòng đôi");
        roomType.setStyle("-fx-alignment: CENTER;");
        roomTable.setItems(listRoom);
        addRoom.setOnAction(e -> addRoomAction());

        newRoomType.getItems().addAll("Phòng đơn", "Phòng đôi");
        newRoomType.setStyle("-fx-alignment: CENTER;");
    }

    public void addRoomAction() {
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
        if (sophong == null || roomPrice == 0.0 || type == null) {
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
    public void delateRoom(Room room, ObservableList<Room> listRoom){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn xoá phòng này?");
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                listRoom.remove(room);
                roomManager.delateRoom(room);
                Alert succesAlert = new Alert(Alert.AlertType.INFORMATION);
                succesAlert.setTitle("Thông báo");
                succesAlert.setHeaderText(null);
                succesAlert.setContentText("Đã xoá phòng thành công!");
                succesAlert.showAndWait();
            }
        });
    }
    public void fixRoomAction(Room room, ObservableList<Room> listRoom){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn thay đổi thông tin phòng này?");
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK) {
                String newType = newRoomType.getValue();
                Double newPrice = Double.valueOf(newRoomPrice.getText());
                room.setRoomType(newType);
                room.setPrice(newPrice);
                for (int i = 0; i < listRoom.size(); i++) {
                    if (room.getRoomID().equals(listRoom.get(i).getRoomID())) {
                        listRoom.set(i, room);
                        break;
                    }
                }
                for (int i = 0; i < RoomManager.getRooms().size(); i++) {
                    if (room.getRoomID().equals(RoomManager.getRooms().get(i).getRoomID())) {
                        RoomManager.getRooms().set(i, room);
                        break;
                    }
                    RoomManager.saveRoomsToFile();
                }
                fixIFPane.setVisible(false);
                Alert succesAlert = new Alert(Alert.AlertType.INFORMATION);
                succesAlert.setTitle("Thông báo");
                succesAlert.setHeaderText(null);
                succesAlert.setContentText("Chỉnh sửa thông tin thành công!");
                succesAlert.showAndWait();
            }
        });
    }


}
