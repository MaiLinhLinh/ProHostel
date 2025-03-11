package org.example.prohostel;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.example.prohostel.Model.Booking;
import org.example.prohostel.Model.GuestManager;
import org.example.prohostel.Model.Room;
import org.example.prohostel.Model.User;


public class ShowGuestList {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<User, String> IDCard;

    @FXML
    private TableColumn<User, String> address;

    @FXML
    private TableColumn<User, String> birthday;

    @FXML
    private TableColumn<User, String> numberPhone;

    @FXML
    private TextField findGuest;

    @FXML
    private TableColumn<User, Void> information;

    @FXML
    private TableView<User> guestTable;

    @FXML
    private ComboBox<User> locKhach;

    @FXML
    private TableColumn<User, String> nameGuest;

    @FXML
    private TableColumn<User, String> nation;

    @FXML
    private TableColumn<User, String> sex;


    @FXML
    private TableColumn<User, Integer> stt;

    @FXML
    private Label cccdLabel;

    @FXML
    private Label hotenLabel;

    @FXML
    private TableView<Room> phongthueTable;

    @FXML
    private TableColumn<Room, Double> price;

    @FXML
    private TableColumn<Room, String> roomID;

    @FXML
    private TableColumn<Room, String> roomType;

    @FXML
    private TableColumn<Room, Integer> sttchitiet;

    @FXML
    private AnchorPane inforPane;

    @FXML
    private Button exitInfor;

    @FXML
    private Label totalNumber;





    private ObservableList<User> listGuest = FXCollections.observableArrayList();
    private ObservableList<Room> listRoom = FXCollections.observableArrayList();
    private GuestManager guestManager;

    @FXML
    void initialize() {

        this.guestManager = new GuestManager();
        inforPane.setVisible(false);
        // hien thi stt tu dong
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
        nameGuest.setCellValueFactory(new PropertyValueFactory<>("name"));
        nameGuest.setStyle("-fx-alignment: CENTER;");
        nation.setCellValueFactory(new PropertyValueFactory<>("national"));
        nation.setStyle("-fx-alignment: CENTER;");
        sex.setCellValueFactory(new PropertyValueFactory<>("sex"));
        sex.setStyle("-fx-alignment: CENTER;");
        birthday.setCellValueFactory(new PropertyValueFactory<>("birthday"));
        birthday.setStyle("-fx-alignment: CENTER;");
        IDCard.setCellValueFactory(new PropertyValueFactory<>("IDCard"));
        IDCard.setStyle("-fx-alignment: CENTER;");
        numberPhone.setCellValueFactory(new PropertyValueFactory<>("numberPhone"));
        numberPhone.setStyle("-fx-alignment: CENTER;");
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        address.setStyle("-fx-alignment: CENTER;");

        for(User guest: guestManager.getListGuests()){
            listGuest.add(guest);
        }

        information.setCellFactory(col -> new TableCell<>(){
            private final Button editButton = new Button();
            {
                editButton.setText("Xem chi tiết");
                editButton.setStyle("-fx-background-color: transparent;");// xoa nen button
                editButton.setOnAction(e ->{
                    int index = getIndex();
                    User guest = getTableView().getItems().get(index);
                    initalizeRoomTable(guest);
                });

            }
            protected void updateItem (Void item, boolean empty){ // phuong thuc cap nhat du lieu cho tung dong
                super.updateItem(item, empty);
                setGraphic(empty? null: editButton);
            }


        });





        guestTable.setStyle("-fx-alignment: CENTER;");
        guestTable.setItems(listGuest);





    }

    public void initalizeRoomTable(User guest){
        inforPane.setVisible(true);
        hotenLabel.setText(guest.getName());
        hotenLabel.setStyle("-fx-alignment: CENTER;");
        cccdLabel.setText(guest.getIDCard());
        cccdLabel.setStyle("-fx-alignment: CENTER;");


        sttchitiet.setCellFactory(col -> new TableCell<>(){
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
        roomID.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        roomID.setStyle("-fx-alignment: CENTER;");
        roomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        roomType.setStyle("-fx-alignment: CENTER;");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        price.setStyle("-fx-alignment: CENTER;");
        LocalDateTime nowTime = LocalDateTime.now();
        for(Booking booking: guest.getGuestBooking()){
            if(booking.getCheckout().isAfter(nowTime)){
                listRoom.add(booking.getRoom());
            }
        }
        phongthueTable.setStyle("-fx-alignment: CENTER;");
        phongthueTable.setItems(listRoom);
        totalNumber.setText(String.valueOf(phongthueTable.getItems().size()));
        exitInfor.setOnAction(e -> {
            inforPane.setVisible(false);
        });
    }



}
