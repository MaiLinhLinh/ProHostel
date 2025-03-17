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



    @FXML
    void initialize() {

        this.guestManager = new GuestManager();
        this.invoiceManager = new InvoiceManager();


        invoicePane.setVisible(false);

        search.setOnAction(e -> searchAction());

        pay.setOnAction(e -> payAction());
        invoice.setOnAction(e -> invoiceAction());

    }

    public void searchAction() {
        check = false;
        searchBookings.clear();
        String getID = IDcardSearch.getText();
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
        roomID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoom().getRoomID()));
        roomType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoom().getRoomType()));
        guestIDCard.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGuest().getIDCard()));
        guestName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getGuest().getName()));

        // thiet lap checkbox cho cot chon
        payment.setCellValueFactory(cellData -> cellData.getValue().isSelectedProperty());
        payment.setCellFactory(CheckBoxTableCell.forTableColumn(payment));
        // cho phep chinh sua checkbox
        listBooking.setEditable(true);
        payment.setEditable(true);

        int ok = 0;
        for (User g : guestManager.getListGuests()) {
            if (g.getIDCard().equals(getID)) {
                ok = 1;
                LocalDateTime nowTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
                for (Booking booking : g.getGuestBooking()) {
                    if (booking.getCheckout().isAfter(nowTime)) {
                        guest = booking.getGuest();
                        searchBookings.add(booking);
                    }
                }

            }
        }
        if (ok == 0) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Không tồn tại khách hàng này!");
            alert.showAndWait();
            return;
        }
        else if(ok == 1 && searchBookings.size() == 0){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Khách hàng này đã thanh toán hết phòng!");
            alert.showAndWait();
            return;

        }
        listBooking.setItems(searchBookings);
//        listBooking.refresh();

    }


    public void payAction() {

        selectedBookings.clear();
        initInvoice();

        ArrayList<Booking> payedBookings = new ArrayList<Booking>();
        for (Booking booking : searchBookings) {
            if (booking.isSelected()) {
                selectedBookings.add(booking);
                payedBookings.add(booking);
            }
        }

        if (selectedBookings.size() == 0) {
            Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
            errorAlert.setTitle("Thông báo");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Vui lòng chọn ít nhất một mục để thanh toán!");
            errorAlert.showAndWait();
            return;
        }
        invoicePane.setVisible(true);
        noti.setVisible(false);
        payInvoice.setVisible(true);


        String currentAdmin = SessionManager.getCurrentAccount().getUserName();
        Invoice newInvoice = new Invoice(guest, currentAdmin, payedBookings, LocalDateTime.now());
        boolean isPay = newInvoice.isPay();
        double totalAmount = newInvoice.getTotalPay();
        totalPrice.setText(String.format("%,.2f VND", totalAmount));
        if (isPay == false) {
            payInvoice.setDisable(false);
            System.out.println("Da vao chua thanh toan");
            payInvoice.setOnAction(e -> {
                newInvoice.setPay(true);
                check = true;
                for (Booking booking : payedBookings) {
                    //booking.caculatePrice();
                    System.out.println("checkout: " + booking.getCheckout());
                    booking.setPay(true);
                    User guest = booking.getGuest();
                    guestManager.delateGuest(guest);
                }
                invoiceManager.addInvoices(newInvoice);
                System.out.println("da luu 1 hoa don");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Thanh toán thành công");
                alert.setHeaderText(null);
                alert.setContentText("Thanh toán thành công!");
                alert.showAndWait();
                payInvoice.setDisable(true);

            });

        } else {
            payInvoice.setDisable(true); // vo hieu hoa nut
        }
    }


    public void invoiceAction(){
        initInvoice();
        if(check == true){
            noti.setText("Đã thanh toán");
        }
        else
            noti.setText("Chưa thanh toán");

        selectedBookings.clear();
        for (Booking booking : searchBookings) {
            if (booking.isSelected()) {
                selectedBookings.add(booking);
            }
        }
        if (selectedBookings.size() == 0) {
            Alert errorAlert = new Alert(Alert.AlertType.INFORMATION);
            errorAlert.setTitle("Thông báo");
            errorAlert.setHeaderText(null);
            errorAlert.setContentText("Vui lòng chọn ít nhất một mục để xem hoá đơn!");
            errorAlert.showAndWait();
            return;
        }
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
        roomIDInvoice.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoom().getRoomID()));
        priceInvoice.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRoom().getPrice()));
        numberDay.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().numberHour()));
        roomTotal.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().caculatePrice()));

        invoiceTable.setItems(selectedBookings);

        name.setText(guest.getName());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
        String now = LocalDateTime.now().format(formatter);
        nowtime.setText(now);
        exitInvoice.setOnAction(e -> {
            invoicePane.setVisible(false);
        });
    }


}
