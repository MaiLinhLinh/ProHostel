package org.example.prohostel;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import org.example.prohostel.Model.Booking;
import org.example.prohostel.Model.Invoice;
import org.example.prohostel.Model.InvoiceManager;
import org.example.prohostel.Model.User;

public class ShowInvoiceList {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TableColumn<Invoice, String> account;

    @FXML
    private TableColumn<Invoice, String> date;

    @FXML
    private TableColumn<Invoice, Void> delate;

    @FXML
    private TableColumn<Invoice, Void> detail;


    @FXML
    private TableView<Invoice> invoiceTable;

    @FXML
    private TextField searchInvoice;

    @FXML
    private TableColumn<Invoice, Integer> stt;

    @FXML
    private TableColumn<Invoice, Double> totalMoney;

    @FXML
    private TableColumn<Booking, Double> price;

    @FXML
    private TableColumn<Booking, String> roomID;

    @FXML
    private TableColumn<Booking, String> roomType;

    @FXML
    private AnchorPane invoiceDetailPane;

    @FXML
    private TableView<Booking> invoiceDetailTable;
    @FXML
    private TableColumn<Booking, Integer> sttInvoice;

    @FXML
    private TableColumn<Booking, Integer> totalHour;

    @FXML
    private TableColumn<Booking, Double> totalPrice;

    @FXML
    private Label Date;

    @FXML
    private Label nameGuest;

    @FXML
    private Label adminAccount;
    @FXML
    private Label totalPay;

    @FXML
    private Button exitDetail;

    @FXML
    private Button searchInvoiceButton;

    private InvoiceManager invoiceManager;
    ObservableList<Invoice> invoices = FXCollections.observableArrayList();
    ObservableList<Booking> payedBookings = FXCollections.observableArrayList();

    @FXML
    void initialize() {
        this.invoiceManager = new InvoiceManager();
        invoiceDetailPane.setVisible(false);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
        date.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaymentTime().format(formatter)));
        account.setCellValueFactory(new PropertyValueFactory<>("account"));
        totalMoney.setCellValueFactory(new PropertyValueFactory<>("totalPay"));
        for(Invoice invoice: invoiceManager.getInvoices()){
            invoices.add(invoice);
        }
        delate.setCellFactory(col -> new TableCell<>(){
            private final Button editButton = new Button();
            {
                ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/Image/img_6.png")));
                imageView.setFitWidth(16); // Đặt kích thước hình ảnh
                imageView.setFitHeight(16);
                editButton.setGraphic(imageView);
                editButton.setStyle("-fx-background-color: transparent;");// xoa nen button
                editButton.setOnAction(e ->{
                    int index = getIndex();
                    Invoice invoice = getTableView().getItems().get(index);
                    delateInvoice(invoice, getTableView().getItems());
                });

            }
            protected void updateItem (Void item, boolean empty){ // phuong thuc cap nhat du lieu cho tung dong
                super.updateItem(item, empty);
                setGraphic(empty? null: editButton);
            }

        });
        detail.setCellFactory(col -> new TableCell<>(){
            private final Button editButton = new Button();
            {
                ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/Image/img_7.png")));
                imageView.setFitWidth(16); // Đặt kích thước hình ảnh
                imageView.setFitHeight(16);
                editButton.setGraphic(imageView);
                editButton.setStyle("-fx-background-color: transparent;");// xoa nen button
                editButton.setOnAction(e ->{
                    int index = getIndex();
                    Invoice invoice = getTableView().getItems().get(index);
                    detailInvoice(invoice);
                });

            }
            protected void updateItem (Void item, boolean empty){ // phuong thuc cap nhat du lieu cho tung dong
                super.updateItem(item, empty);
                setGraphic(empty? null: editButton);
            }

        });



        invoiceTable.setItems(invoices);
        exitDetail.setOnAction(e -> ExitDetailAction());
        searchInvoiceButton.setOnAction(e -> SearchInvoiceButtonAction());



    }
    public void delateInvoice(Invoice invoice, ObservableList<Invoice> invoices){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText("Bạn có chắc chắn muốn xoá hoá đơn này?");
        alert.showAndWait().ifPresent(response -> {
            if(response == ButtonType.OK){
                invoices.remove(invoice);
                invoiceManager.removeInvoice(invoice);
                Alert succesAlert = new Alert(Alert.AlertType.INFORMATION);
                succesAlert.setTitle("Thông báo");
                succesAlert.setHeaderText(null);
                succesAlert.setContentText("Đã xoá hoá đơn thành công!");
                succesAlert.showAndWait();
            }
        });

    }
    public void detailInvoice(Invoice invoice){
        invoiceDetailPane.setVisible(true);
        nameGuest.setText(invoice.getGuest().getName());
        adminAccount.setText(invoice.getAccount());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
        String time = invoice.getPaymentTime().format(formatter);
        Date.setText(time);
        // hien thi stt tu dong
        sttInvoice.setCellFactory(col -> new TableCell<>(){
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
        roomID.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoom().getRoomID()));
        roomType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoom().getRoomType()));
        price.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRoom().getPrice()));
        totalPrice.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().caculatePrice()));
        totalHour.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().numberHour()));
        totalPay.setText(String.valueOf(invoice.getTotalPay()) + " VNĐ");
        for(Booking booking: invoice.getPayedBookings()){
            payedBookings.add(booking);
        }
        invoiceDetailTable.setItems(payedBookings);

    }
    public void ExitDetailAction(){
        invoiceDetailPane.setVisible(false);
    }
    public void SearchInvoiceButtonAction(){
        String account = searchInvoice.getText();
        invoices.clear();
        for(Invoice invoice: invoiceManager.getInvoices()){
            if(invoice.getAccount().equals(account)){
                invoices.add(invoice);
            }
        }
        invoiceTable.setItems(invoices);
    }


}
