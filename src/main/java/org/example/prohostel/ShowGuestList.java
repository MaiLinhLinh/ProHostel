package org.example.prohostel;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.example.prohostel.Model.*;
import org.example.prohostel.Model.SetAlert;


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
    private TableView<Booking> phongthueTable;

    @FXML
    private TableColumn<Booking, Double> price;

    @FXML
    private TableColumn<Booking, String> roomID;

    @FXML
    private TableColumn<Booking, String> roomType;

    @FXML
    private TableColumn<Booking, Integer> sttchitiet;

    @FXML
    private TableColumn<Booking, String> checkin;

    @FXML
    private TableColumn<Booking, String> checkout;


    @FXML
    private AnchorPane inforPane;

    @FXML
    private Button exitInfor;

    @FXML
    private Label totalNumber;

    @FXML
    private TableColumn<Booking, String> status;

    @FXML
    private Button search;


    private ObservableList<User> listGuest = FXCollections.observableArrayList();
    private ObservableList<Booking> listBooking = FXCollections.observableArrayList();
    private GuestManager guestManager;


    @FXML
    void initialize() {

        this.guestManager = new GuestManager();
        System.out.println("check load file guest");
        for(User guest: guestManager.getListGuests()){
            System.out.println(guest.getName() + " da o trong danh sach file");
        }
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
        Image image = new Image(getClass().getResourceAsStream("/Image/img_5.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(22);
        imageView.setFitHeight(22);
        search.setGraphic(imageView);
        search.setOnMouseEntered(e -> search.setStyle("-fx-background-color: Gainsboro; -fx-background-radius: 30; -fx-text-fill: white;"));
        search.setOnMouseExited(e -> search.setStyle("-fx-background-color: transparent; -fx-background-radius: 30; -fx-text-fill: white;"));

        exitInfor.setOnMouseEntered(e -> exitInfor.setStyle("-fx-background-color: red; -fx-text-fill: white;"));
        exitInfor.setOnMouseExited(e -> exitInfor.setStyle("-fx-background-color: Gainsboro; -fx-text-fill: black;"));

        for(User guest: guestManager.getListGuests()){
            listGuest.add(guest);
        }


        information.setCellFactory(col -> new TableCell<>(){
            private final Button editButton = new Button();
            {
                ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/Image/img_7.png")));
                imageView.setFitWidth(16); // Đặt kích thước hình ảnh
                imageView.setFitHeight(16);
                editButton.setGraphic(imageView);
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
        information.setStyle("-fx-alignment: CENTER;");

        guestTable.setStyle("-fx-alignment: CENTER;");
        guestTable.setItems(listGuest);
        nameGuest.setSortable(true); // Cho phép sắp xếp
        nameGuest.setComparator(Comparator.naturalOrder()); // Sắp xếp từ A đến Z
        guestTable.getSortOrder().add(nameGuest); // Áp dụng sắp xếp
        search.setOnAction(e -> searchAction());


    }


    public void initalizeRoomTable(User guest){

        listBooking.clear();
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

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
        checkin.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCheckin().format(formatter)));
        checkin.setStyle("-fx-alignment: CENTER;");
        checkout.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCheckout().format(formatter)));
        checkout.setStyle("-fx-alignment: CENTER;");
        roomID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoom().getRoomID()));
        roomID.setStyle("-fx-alignment: CENTER;");
        roomType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoom().getRoomType()));
        roomType.setStyle("-fx-alignment: CENTER;");
        price.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().caculatePrice(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), false)));
        price.setStyle("-fx-alignment: CENTER;");
        status.setCellValueFactory(cellData -> {
            Booking booking = cellData.getValue();
            System.out.println(booking.getIsPay());
            return new SimpleStringProperty(booking.getIsPay() ? "Đã thanh toán": "Chưa thanh toán");
        });
        status.setStyle("-fx-alignment: CENTER;");


        for(Booking booking: guest.getGuestBooking()){
            listBooking.add(booking);
        }
        phongthueTable.setStyle("-fx-alignment: CENTER;");
        phongthueTable.setItems(listBooking);
        totalNumber.setText(String.valueOf(phongthueTable.getItems().size()));
        exitInfor.setOnAction(e -> {
            inforPane.setVisible(false);
        });
    }
    public void searchAction(){
        String getGuestName = findGuest.getText();
        if(getGuestName.equals("")){
            SetAlert.setAlert("Vui lòng nhập tên khách hàng để tra cứu!");
            return;
        }
        listGuest.clear();
        for(User guest: guestManager.getListGuests()){
            if(guest.getName().equals(getGuestName)){
                listGuest.add(guest);
            }
        }
        guestTable.setItems(listGuest);

    }



}
