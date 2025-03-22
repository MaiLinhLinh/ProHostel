package org.example.prohostel;

import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Comparator;
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
import org.example.prohostel.Model.*;

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
    private ObservableList<Invoice> invoices = FXCollections.observableArrayList();
    private ObservableList<Booking> payedBookings = FXCollections.observableArrayList();




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
        Image image = new Image(getClass().getResourceAsStream("/Image/img_5.png"));
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(22);
        imageView.setFitHeight(22);
        searchInvoiceButton.setGraphic(imageView);
        searchInvoiceButton.setOnMouseEntered(e -> searchInvoiceButton.setStyle("-fx-background-color: Gainsboro; -fx-background-radius: 30; -fx-text-fill: white;"));
        searchInvoiceButton.setOnMouseExited(e -> searchInvoiceButton.setStyle("-fx-background-color: transparent; -fx-background-radius: 30; -fx-text-fill: white;"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");
        date.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getPaymentTime().format(formatter)));
        date.setStyle("-fx-alignment: CENTER;");
        date.setSortable(true); // Cho phép sắp xếp
        date.setComparator((d1, d2) -> {
            // Tách chuỗi theo dd/MM/yyyy hh:mm a
            String[] parts1 = d1.split(" ");
            String[] parts2 = d2.split(" ");

            if (parts1.length == 3 && parts2.length == 3) {
                // Tách ngày, tháng, năm
                String[] dateParts1 = parts1[0].split("/");
                String[] dateParts2 = parts2[0].split("/");

                // Tách giờ phút
                String[] timeParts1 = parts1[1].split(":");
                String[] timeParts2 = parts2[1].split(":");

                // Xử lý AM/PM
                String amPm1 = parts1[2];
                String amPm2 = parts2[2];

                if (dateParts1.length == 3 && dateParts2.length == 3
                        && timeParts1.length == 2 && timeParts2.length == 2) {
                    int hour1 = Integer.valueOf(timeParts1[0]);
                    int hour2 = Integer.valueOf(timeParts2[0]);
                    // yyyyMMddHHmm với xử lý AM/PM
                    String dateTime1 = dateParts1[2] + dateParts1[1] + dateParts1[0] +
                            ((amPm1.equals("PM") && hour1 != 12) ? Integer.parseInt(timeParts1[0]) + 12 : timeParts1[0]) +
                            timeParts1[1];

                    String dateTime2 = dateParts2[2] + dateParts2[1] + dateParts2[0] +
                            ((amPm2.equals("PM") && hour2 != 12) ? Integer.parseInt(timeParts2[0]) + 12 : timeParts2[0]) +
                            timeParts2[1];

                    // So sánh chuỗi yyyyMMddHHmm
                    return dateTime2.compareTo(dateTime1); // Mới nhất trước
                }
            }
            return 0;
        });
        invoiceTable.getSortOrder().add(date);
        account.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAccount().getUserName()));
        account.setStyle("-fx-alignment: CENTER;");
        totalMoney.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTotalPay(false)));
        totalMoney.setStyle("-fx-alignment: CENTER;");
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
        delate.setStyle("-fx-alignment: CENTER;");
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
        detail.setStyle("-fx-alignment: CENTER;");

        invoiceTable.setItems(invoices);

        invoiceTable.getSortOrder().add(date); // Áp dụng sắp xếp
        exitDetail.setOnAction(e -> ExitDetailAction());
        exitDetail.setOnMouseEntered(e -> exitDetail.setStyle("-fx-background-color: red; -fx-text-fill: white;"));
        exitDetail.setOnMouseExited(e -> exitDetail.setStyle("-fx-background-color: Gainsboro; -fx-text-fill: black;"));
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
        payedBookings.clear();
        invoiceDetailPane.setVisible(true);
        nameGuest.setText(invoice.getGuest().getName());
        adminAccount.setText(invoice.getAccount().getUserName());
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
        roomID.setStyle("-fx-alignment: CENTER;");
        roomType.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getRoom().getRoomType()));
        roomType.setStyle("-fx-alignment: CENTER;");
        price.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRoom().getPrice()));
        price.setStyle("-fx-alignment: CENTER;");
        totalPrice.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().caculatePrice(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), false)));
        totalPrice.setStyle("-fx-alignment: CENTER;");
        totalHour.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().numberHour(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), false)));
        totalHour.setStyle("-fx-alignment: CENTER;");

        totalPay.setText(String.valueOf(invoice.getTotalPay(false)) + " VNĐ");
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
        if(account.equals("")){
            SetAlert.setAlert("Vui lòng nhập thông tin để tra cứu!");
            return;
        }
        invoices.clear();
        for(Invoice invoice: invoiceManager.getInvoices()){
            if(invoice.getAccount().equals(account)){
                invoices.add(invoice);
            }
        }
        invoiceTable.setItems(invoices);
    }


}
