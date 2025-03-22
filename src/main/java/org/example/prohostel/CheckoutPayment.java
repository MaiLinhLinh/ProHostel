package org.example.prohostel;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.example.prohostel.Model.*;

public class CheckoutPayment {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField IDcardSearch;

    @FXML
    private TableColumn<Booking, String> checkin;

    @FXML
    private TableColumn<Booking, String> checkout;

    @FXML
    private TableColumn<Booking, String> guestIDCard;

    @FXML
    private TableColumn<Booking, String> guestName;

    @FXML
    private Button invoice;

    @FXML
    private TableView<Booking> listBooking;

    @FXML
    private Button pay;

    @FXML
    private TableColumn<Booking, Boolean> payment;

    @FXML
    private TableColumn<Booking, String> roomID;

    @FXML
    private TableColumn<Booking, String> roomType;

    @FXML
    private Button search;

    @FXML
    private TableColumn<Booking, Integer> stt;

    @FXML
    private AnchorPane invoicePane;

    @FXML
    private Label name;

    @FXML
    private Label nowtime;

    @FXML
    private TableColumn<Booking, Integer> numberDay;

    @FXML
    private Button payInvoice;

    @FXML
    private TableColumn<Booking, Double> priceInvoice;

    @FXML
    private TableColumn<Booking, String> roomIDInvoice;

    @FXML
    private TableColumn<Booking, Double> roomTotal;

    @FXML
    private TableColumn<Booking, String> roomTypeInvoice;

    @FXML
    private TableColumn<Booking, Integer> sttinvoice;

    @FXML
    private TableView<Booking> invoiceTable;

    @FXML
    private Label totalPrice;

    @FXML
    private Button exitInvoice;

    @FXML
    private Label noti;

    private GuestManager guestManager;
    private ObservableList<Booking> searchBookings = FXCollections.observableArrayList();
    private ObservableList<Booking> selectedBookings = FXCollections.observableArrayList();
    private User guest;
    private InvoiceManager invoiceManager;
    private boolean check = false;
    private UserAccount account = SessionManager.getCurrentAccount();
    private String role = account.getRole();




    @FXML
    void initialize() {

        this.guestManager = new GuestManager();

        this.invoiceManager = new InvoiceManager();

        Image image = new Image(getClass().getResourceAsStream("/Image/img_5.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(22);
        imageView.setFitHeight(22);
        search.setGraphic(imageView);
        search.setOnMouseEntered(e -> search.setStyle("-fx-background-color: Gainsboro; -fx-background-radius: 30; -fx-text-fill: white;"));
        search.setOnMouseExited(e -> search.setStyle("-fx-background-color: transparent; -fx-background-radius: 30; -fx-text-fill: white;"));
        exitInvoice.setOnMouseEntered(e -> exitInvoice.setStyle("-fx-background-color: red; -fx-text-fill: white;"));
        exitInvoice.setOnMouseExited(e -> exitInvoice.setStyle("-fx-background-color: Gainsboro; -fx-text-fill: black;"));
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
        checkin.setStyle("-fx-alignment: CENTER;");
        checkout.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCheckout().format(formatter)));
        checkout.setStyle("-fx-alignment: CENTER;");
        roomID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoom().getRoomID()));
        roomID.setStyle("-fx-alignment: CENTER;");
        roomType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoom().getRoomType()));
        roomType.setStyle("-fx-alignment: CENTER;");
        guestIDCard.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGuest().getIDCard()));
        guestIDCard.setStyle("-fx-alignment: CENTER;");
        guestName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGuest().getName()));
        guestName.setStyle("-fx-alignment: CENTER;");

        // thiet lap checkbox cho cot chon
        payment.setCellValueFactory(cellData -> cellData.getValue().isSelectedProperty());
        payment.setCellFactory(CheckBoxTableCell.forTableColumn(payment));
        // cho phep chinh sua checkbox
        listBooking.setEditable(true);
        payment.setEditable(true);
        payment.setStyle("-fx-alignment: CENTER;");

        if(role.equals("Admin")) {
            for (User g : guestManager.getListGuests()) {
                LocalDateTime nowTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
                for (Booking booking : g.getGuestBooking()) {
                    if (booking.getIsPay() == false && booking.getCheckout().isAfter(nowTime)) {
                        guest = booking.getGuest();
                        searchBookings.add(booking);
                    }
                }

            }
        }
        else{
            for (User g : guestManager.getListGuests()) {
                LocalDateTime nowTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
                for (Booking booking : g.getGuestBooking()) {
                    if (booking.getIsPay() == false && booking.getUserAccount().getUserName().equals(account.getUserName()) && booking.getUserAccount().getPassword().equals(account.getPassword()) && booking.getCheckout().isAfter(nowTime)) {
                        guest = booking.getGuest();
                        searchBookings.add(booking);
                    }
                }

            }

        }
        listBooking.setItems(searchBookings);
        setStyle(pay);
        setStyle(payInvoice);
        setStyle(invoice);


        invoicePane.setVisible(false);

        search.setOnAction(e -> searchAction());

        pay.setOnAction(e -> payAction());
        invoice.setOnAction(e -> invoiceAction());

    }

    public void searchAction() {
        check = false;
        searchBookings.clear();
        String getID = IDcardSearch.getText();
        if(getID.equals("")){
            SetAlert.setAlert("Vui lòng nhập CCCD để tra cứu!");
            return;
        }
        int ok = 0;
        if(role.equals("Admin")) {
            for (User g : guestManager.getListGuests()) {
                if (g.getIDCard().equals(getID)) {
                    ok = 1;
                    LocalDateTime nowTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
                    for (Booking booking : g.getGuestBooking()) {
                        if (booking.getIsPay() == false && booking.getCheckout().isAfter(nowTime)) {
                            guest = booking.getGuest();
                            searchBookings.add(booking);
                        }
                    }

                }
            }
        }else{
            for (User g : guestManager.getListGuests()) {
                if (g.getIDCard().equals(getID)) {
                    LocalDateTime nowTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
                    for (Booking booking : g.getGuestBooking()) {
                        if (booking.getIsPay() == false && booking.getUserAccount().getUserName().equals(account.getUserName()) && booking.getUserAccount().getPassword().equals(account.getPassword()) && booking.getCheckout().isAfter(nowTime)) {
                            ok = 2;
                            guest = booking.getGuest();
                            searchBookings.add(booking);
                        }
                    }
                }
            }
        }
        if (ok == 0) {
            SetAlert.setAlert("Không tồn tại khách hàng này!");
            return;
        }
        if(ok == 1 && searchBookings.size() == 0 && role.equals("Admin")){
            SetAlert.setAlert("Khách hàng này đã thanh toán hết phòng!");
            return;

        }
        if(ok == 2 && searchBookings.size() == 0 && role.equals("Guest")){
            SetAlert.setAlert("Khách hàng này đã thanh toán hết phòng!");
            return;
        }

        listBooking.setItems(searchBookings);


    }


    public void payAction() {
        if(searchBookings.size() == 0){
            SetAlert.setAlert("Không có khách hàng nào chưa thanh toán!");
            return;
        }
        selectedBookings.clear();
        LocalDateTime timeNow = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        if(role.equals("Admin")) {
            roomTotal.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().caculatePrice(timeNow, true)));
            roomTotal.setStyle("-fx-alignment: CENTER;");
            numberDay.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().numberHour(timeNow, true)));
            numberDay.setStyle("-fx-alignment: CENTER;");
        }
        else{
            roomTotal.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().caculatePrice(timeNow, false)));
            roomTotal.setStyle("-fx-alignment: CENTER;");
            numberDay.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().numberHour(timeNow, false)));
            numberDay.setStyle("-fx-alignment: CENTER;");
        }
        initInvoice();

        ArrayList<Booking> payedBookings = new ArrayList<Booking>();
        for (Booking booking : searchBookings) {
            if (booking.isSelected()) {
                selectedBookings.add(booking);
                payedBookings.add(booking);
            }
        }

        if (selectedBookings.size() == 0) {
            SetAlert.setAlert("Vui lòng chọn ít nhất một mục để thanh toán!");
            return;
        }
        invoicePane.setVisible(true);
        noti.setVisible(false);
        payInvoice.setVisible(true);


        UserAccount currentAdmin = SessionManager.getCurrentAccount();
        Invoice newInvoice = new Invoice(guest, currentAdmin, payedBookings, LocalDateTime.now());
        boolean isPay = newInvoice.isPay();
        double totalAmount;
        if(role.equals("Admin"))
            totalAmount = newInvoice.getTotalPay(true);
        else
            totalAmount = newInvoice.getTotalPay(false);
        totalPrice.setText(String.format("%,.2f VND", totalAmount));
        if (isPay == false) {
            payInvoice.setDisable(false);
            System.out.println("Da vao chua thanh toan");
            payInvoice.setOnAction(e -> {
                newInvoice.setPay(true);
                check = true;
                LocalDateTime currentCheckout = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
                for (Booking booking : payedBookings) {
                    if(role.equals("Admin")) {
                        booking.setCheckout(currentCheckout);
                    }
                    booking.setPay(true);
                    RoomManager.updateRoom(booking.getRoom());
                    RoomManager.saveRoomsToFile();
                    GuestManager.saveGuestsToFile();

                    searchBookings.remove(booking);
                    System.out.println("checkout: " + booking.getCheckout());
                    System.out.println(booking.getIsPay());
                    System.out.println("Da set da thanh toan");
                }

                invoiceManager.addInvoices(newInvoice);
                System.out.println("da luu 1 hoa don");
                SetAlert.setAlert("Thanh toán thành công!");
                payInvoice.setDisable(true);

            });

        } else {
            payInvoice.setDisable(true); // vo hieu hoa nut
        }
    }


    public void invoiceAction(){
        if(searchBookings.size() == 0){
            SetAlert.setAlert("Không có khách hàng nào chưa thanh toán!");
            return;
        }
        LocalDateTime timeNow = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        roomTotal.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().caculatePrice(timeNow, false)));
        roomTotal.setStyle("-fx-alignment: CENTER;");
        numberDay.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().numberHour(timeNow, false)));
        numberDay.setStyle("-fx-alignment: CENTER;");

        initInvoice();
        if(check == true){
            noti.setText("Đã thanh toán");
        }
        else
            noti.setText("Chưa thanh toán");

        selectedBookings.clear();
        ArrayList<Booking> payedBookings = new ArrayList<Booking>();
        for (Booking booking : searchBookings) {
            if (booking.isSelected()) {
                selectedBookings.add(booking);
                payedBookings.add(booking);
            }
        }
        if (selectedBookings.size() == 0) {
            SetAlert.setAlert("Vui lòng chọn ít nhất một mục để xem hoá đơn!");
            return;
        }
        UserAccount currentAdmin = SessionManager.getCurrentAccount();
        Invoice newInvoice = new Invoice(guest, currentAdmin, payedBookings, LocalDateTime.now());
        double totalAmount;
        if(role.equals("Admin"))
            totalAmount = newInvoice.getTotalPay(true);
        else
            totalAmount = newInvoice.getTotalPay(false);
        totalPrice.setText(String.format("%,.2f VND", totalAmount));
        invoicePane.setVisible(true);
        noti.setVisible(true);
        payInvoice.setVisible(false);
    }
    private void initInvoice() {
        sttinvoice.setCellFactory(col -> new TableCell<>() {
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
        roomTypeInvoice.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoom().getRoomType()));
        roomTypeInvoice.setStyle("-fx-alignment: CENTER;");
        roomIDInvoice.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoom().getRoomID()));
        roomIDInvoice.setStyle("-fx-alignment: CENTER;");
        priceInvoice.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRoom().getPrice()));
        priceInvoice.setStyle("-fx-alignment: CENTER;");

        invoiceTable.setItems(selectedBookings);

        name.setText(guest.getName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
        String now = LocalDateTime.now().format(formatter);
        nowtime.setText(now);
        exitInvoice.setOnAction(e -> {
            invoicePane.setVisible(false);
        });
    }
    private void setStyle(Button button){
        button.setOnMouseEntered(e -> button.setStyle("-fx-background-color: ForestGreen; -fx-background-radius: 10; -fx-text-fill: white;"));
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: green; -fx-background-radius: 10; -fx-text-fill: white;"));
    }

}
