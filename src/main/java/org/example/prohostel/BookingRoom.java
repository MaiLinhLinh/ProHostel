package org.example.prohostel;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
    private TextField birthday;

    @FXML
    private Button cancel;

    @FXML
    private Button exit;
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
    private TextField sex;

    @FXML
    private TableColumn<Room, String> availableRoomID;

    @FXML
    private TableColumn<Room, String> availableRoomType;

    @FXML
    private TableView<Room> availableRooms;

    @FXML
    private TableColumn<Room, Boolean> pick;

    @FXML
    private TableView<Room> selectedRoom;

    @FXML
    private TableColumn<Room, String> selectedRoomID;

    @FXML
    private TableColumn<Room, String> selectedRoomType;
    private GuestManager guestManager;
    private RoomManager roomManager;
    private ArrayList<Room> rooms = new ArrayList<Room>();

    @FXML
    void initialize() {
        Room room01 = new Room("101", "Phong don");
        Room room03 = new Room("102", "Phong doi");
        rooms.add(room01);
        rooms.add(room03);
        roomManager = new RoomManager(rooms);
        this.guestManager = new GuestManager();


        LocalDate date = LocalDate.of(2025, 3, 9); // Ngày cụ thể
        LocalTime time = LocalTime.of(00, 00); // 14:30 (2:30 PM)

        LocalDateTime checkinDateTime = LocalDateTime.of(date, time);

        LocalDate cdate = LocalDate.of(2025, 3, 9); // Ngày cụ thể
        LocalTime ctime = LocalTime.of(16, 00); // 14:30 (2:30 PM)

        LocalDateTime checkoutDateTime = LocalDateTime.of(cdate, ctime);

        guestManager.addGuest("an", "01", "02", "01", "vn", "na", room01, checkinDateTime, checkoutDateTime);

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



        exit.setOnAction(e -> exitAction());
        save.setOnAction(e -> saveAction());

    }
    public void exitAction(){
        System.out.println("Quay ve trang chu");
    }

    public void saveAction(){
        String name = fullName.getText();
        String phone = phoneNumber.getText();
        String dateOfBirth = birthday.getText();
        String sexx = sex.getText();
        String card = IDCard.getText();
        String national = nation.getText();
        String Address = address.getText();
        String getCheckinTime = checkinTime.getValue();
        String getCheckoutTime = checkoutTime.getValue();
        LocalDate getCheckinDate = checkinDate.getValue();
        LocalDate getCheckoutDate = checkoutDate.getValue();

        LocalDateTime checkinDateTime = null;
        LocalDateTime checkoutDateTime = null;

        // chuyen ve dinh dang DateTime de so sanh
        if (getCheckinDate != null && getCheckinTime != null) {
            LocalTime parsedTime = LocalTime.parse(getCheckinTime, DateTimeFormatter.ofPattern("hh:mm a"));
            checkinDateTime = LocalDateTime.of(getCheckinDate, parsedTime);
            System.out.println("Check-in DateTime: " + checkinDateTime);
        } else {
            System.out.println("Vui lòng chọn đầy đủ ngày và giờ!");
        }
        if (getCheckoutDate != null && getCheckoutTime != null) {
            LocalTime parsedTime = LocalTime.parse(getCheckoutTime, DateTimeFormatter.ofPattern("hh:mm a"));
            checkoutDateTime = LocalDateTime.of(getCheckoutDate, parsedTime);
            System.out.println("Check-out DateTime: " + checkoutDateTime);
        } else {
            System.out.println("Vui lòng chọn đầy đủ ngày và giờ!");
        }

        System.out.println("----- Trước khi đặt phòng -----");
        for (Room room : rooms) {
            System.out.println("Phòng: " + room.getRoomID());
            for (Booking booking : room.getBookings()) {
                System.out.println("   Đã đặt bởi: " + booking.getGuest().getName() +
                        " | Check-in: " + booking.getCheckin() +
                        " | Check-out: " + booking.getCheckout());
            }
        }
        Room selectedRoom = null;
        for(Room room: rooms){
            selectedRoom = room;
            break;
        }
        //System.out.println(selectedRoom.isBooking(checkinDateTime, checkoutDateTime));
        guestManager.addGuest(name, dateOfBirth, phone, card, Address, national, selectedRoom, checkinDateTime, checkoutDateTime);

        System.out.println("----- Sau khi đặt phòng -----");
        for (Room room : rooms) {
            System.out.println("Phòng: " + room.getRoomID());
            for (Booking booking : room.getBookings()) {
                System.out.println("   Đã đặt bởi: " + booking.getGuest().getName() +
                        " | Check-in: " + booking.getCheckin() +
                        " | Check-out: " + booking.getCheckout());
            }
        }

    }
}
