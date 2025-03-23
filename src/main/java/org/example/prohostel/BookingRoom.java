package org.example.prohostel;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.StringConverter;
import org.example.prohostel.Model.*;

public class BookingRoom {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField IDCard;

    @FXML
    private TextField address;

    @FXML
    private DatePicker birthday;


    @FXML
    private DatePicker checkinDate;

    @FXML
    private DatePicker checkoutDate;

    @FXML
    private ComboBox<String> checkinTime;

    @FXML
    private ComboBox<String> checkoutTime;

    @FXML
    private TextField fullName;

    @FXML
    private TextField nation;

    @FXML
    private TextField phoneNumber;

    @FXML
    private Button save;

    @FXML
    private ComboBox<String> sex;

    @FXML
    private TableColumn<Room, String> availableRoomID;

    @FXML
    private TableColumn<Room, String> availableRoomType;

    @FXML
    private TableView<Room> availableRoomTable;

    @FXML
    private TableColumn<Room, Boolean> pick;

    @FXML
    private TableView<Room> selectedRoomTable;

    @FXML
    private TableColumn<Room, String> selectedRoomID;

    @FXML
    private Button ok;

    @FXML
    private TableColumn<Room, String> selectedRoomType;

    @FXML
    private TableColumn<Room, Double> price;

    private GuestManager guestManager;
    private RoomManager roomManager;
    private ArrayList<Booking> bookings = new ArrayList<>();
    private ObservableList<Room> availabelRoom = FXCollections.observableArrayList();
    private ObservableList<Room> selectdRoom = FXCollections.observableArrayList();
    private LocalDateTime checkinDateTime;
    private LocalDateTime checkoutDateTime;

    @FXML
    void initialize() {
        this.roomManager = new RoomManager();
        this.guestManager = new GuestManager();


        // Dinh dang ngay
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        StringConverter<LocalDate> converter = new StringConverter<>() {
            @Override
            public String toString(LocalDate localDate) {
                return (localDate != null) ? localDate.format(dateFormatter) : "";
            }

            @Override
            public LocalDate fromString(String s) {
                return (s != null && !s.isEmpty()) ? LocalDate.parse(s, dateFormatter): null;
            }
        };
        checkinDate.setConverter(converter);
        checkoutDate.setConverter(converter);

        // dinh dang gio
       DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
       // tao danh sach gio tu 12:00AM den 11:59PM
        ArrayList<String> timeList = new ArrayList<>();
        for(int hour = 0; hour < 24; hour++){
            for(int min = 0; min < 60; min += 30){ // tao buoc 30p
                timeList.add(LocalTime.of(hour, min).format(timeFormatter));
            }
        }
        // dua danh sach vao combobox

        checkinTime.getItems().addAll(timeList);
        checkoutTime.getItems().addAll(timeList);
        // mac dinh hien thi 12:00AM
        checkinTime.setValue("12:00 AM");
        checkoutTime.setValue("04:00 PM");

        checkinDate.valueProperty().addListener((obs, oldValue, newValue) -> updateAvailableRooms());
        checkoutDate.valueProperty().addListener((obs, oldValue, newValue) -> updateAvailableRooms());

        checkinTime.valueProperty().addListener((obs, oldValue, newValue) -> updateAvailableRooms());
        checkoutTime.valueProperty().addListener((obs, oldValue, newValue) -> updateAvailableRooms());

        save.setOnMouseEntered(e -> save.setStyle("-fx-background-color: ForestGreen; -fx-background-radius: 10; -fx-text-fill: white;"));
        save.setOnMouseExited(e -> save.setStyle("-fx-background-color: green; -fx-background-radius: 10; -fx-text-fill: white;"));

        sex.getItems().addAll("Nam", "Nữ");

        ok.setOnAction(e -> okAction());
        ok.setOnMouseEntered(e -> ok.setStyle("-fx-background-color: ForestGreen; -fx-text-fill: white;"));
        ok.setOnMouseExited(e -> ok.setStyle("-fx-background-color: green; -fx-text-fill: white;"));
        save.setOnAction(e -> saveAction());

    }


    public void saveAction() {
        String name = fullName.getText();
        String phone = phoneNumber.getText();
        LocalDate date = birthday.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String sexx = sex.getValue();
        String card = IDCard.getText();
        String national = nation.getText();
        String Address = address.getText();
        if(name == "" || phone == "" || date == null || sexx == "" || card == "" || national == "" || Address == ""){
            SetAlert.setAlert("Vui lòng điền đầy đủ thông tin!");
            return;
        }
        String dateOfBirth = date.format(formatter);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn đặt phòng đã chọn ?");
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                for (Room room : availabelRoom) {
                    if (room.isSelected()) {
                        UserAccount currentAccount = SessionManager.getCurrentAccount();
                        guestManager.addGuest(currentAccount, name, dateOfBirth, sexx, phone, card, Address, national, room, checkinDateTime, checkoutDateTime);
                        System.out.println("khach hang " + name + " da thue phong " + room.getRoomID());
                    }
                }

                SetAlert.setAlert("Đăng kí phòng thành công!");
            }
        });


    }
    public void updateAvailableRooms(){
        RoomManager.loadRoomsFromFile();
        availabelRoom.clear();
        availableRoomID.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        availableRoomID.setStyle("-fx-alignment: CENTER;");
        availableRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        availableRoomType.setStyle("-fx-alignment: CENTER;");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));
        price.setStyle("-fx-alignment: CENTER;");

        String getCheckinTime = checkinTime.getValue();
        String getCheckoutTime = checkoutTime.getValue();
        LocalDate getCheckinDate = checkinDate.getValue();
        LocalDate getCheckoutDate = checkoutDate.getValue();

        pick.setCellValueFactory(cellData -> cellData.getValue().isSelectedProperty());
        pick.setStyle("-fx-alignment: CENTER;");

        pick.setCellFactory(col -> new TableCell<Room, Boolean>() {
            private final CheckBox checkBox = new CheckBox();

            {
                checkBox.setOnAction(event -> {
                    Room room = getTableView().getItems().get(getIndex());
                    room.setSelected(checkBox.isSelected());
                });

            }

            @Override
            protected void updateItem(Boolean item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                    setGraphic(null);
                    return;
                }
                checkBox.setSelected(item);
                setGraphic(checkBox);
            }
        });

        // cho phep chinh sua checkbox
        availableRoomTable.setEditable(true);
        pick.setEditable(true);

        // chuyen dinh dang
        if(getCheckinDate == null || getCheckoutDate == null ||getCheckinTime == null || getCheckoutTime == null || (getCheckinDate.isAfter(getCheckoutDate))){
            availableRoomTable.getItems().clear();
        }
        else {
            LocalTime parsedCheckinTime = LocalTime.parse(getCheckinTime, DateTimeFormatter.ofPattern("hh:mm a"));
            checkinDateTime = LocalDateTime.of(getCheckinDate, parsedCheckinTime);
            System.out.println("Check-in DateTime: " + checkinDateTime);
            LocalTime parsedCheckoutTime = LocalTime.parse(getCheckoutTime, DateTimeFormatter.ofPattern("hh:mm a"));
            checkoutDateTime = LocalDateTime.of(getCheckoutDate, parsedCheckoutTime);
            System.out.println("Check-out DateTime: " + checkoutDateTime);
            ArrayList<Room> rooms = roomManager.getRoomAvailable(checkinDateTime, checkoutDateTime);
            for(Room room : rooms){
                availabelRoom.add(room);
            }
        }
        availableRoomTable.setItems(availabelRoom);


    }
    public void okAction(){
        selectdRoom.clear();
        for(Room room: availabelRoom){
            System.out.println("Phòng " + room.getRoomID() + " isSelected = " + room.isSelected());
            if(room.isSelected()){
                System.out.println("Phong " + room.getRoomID() + " da duoc chon");
                selectdRoom.add(room);
            }
        }
        selectedRoomID.setCellValueFactory(new PropertyValueFactory<>("roomID"));
        selectedRoomID.setStyle("-fx-alignment: CENTER;");
        selectedRoomType.setCellValueFactory(new PropertyValueFactory<>("roomType"));
        selectedRoomType.setStyle("-fx-alignment: CENTER;");
        if(selectdRoom.size() == 0){
            SetAlert.setAlert("Vui lòng chọn ít nhất một phòng!");
            return;
        }
        selectedRoomTable.setItems(selectdRoom);
    }

}
