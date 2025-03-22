package org.example.prohostel.Model;

public class SetAlert {
    public static void setAlert(String noti){
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Thông báo");
        alert.setHeaderText(null);
        alert.setContentText(noti);
        alert.showAndWait();
    }
}
