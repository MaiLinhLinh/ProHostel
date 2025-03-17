package org.example.prohostel.Model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Objects;

// luu thong tin 1 lan dat phong
// moi lan dat co thong tin khach hang, phong, thoi gian dat
public class Booking implements Serializable {
    private User guest;
    private Room room;
    private LocalDateTime checkin;
    private LocalDateTime checkout;
    private transient BooleanProperty isSelected = new SimpleBooleanProperty(false);
    private boolean isPay;


    public Booking(User guest, Room room, LocalDateTime checkinTime, LocalDateTime checkoutTime){
        this.guest = guest;
        this.room = room;
        this.checkin = checkinTime;
        this.checkout = checkoutTime;
        isPay = false;

    }

    public Room getRoom() {
        return room;
    }

    public User getGuest() {
        return guest;
    }

    public LocalDateTime getCheckout() {
        return checkout;
    }

    public LocalDateTime getCheckin() {
        return checkin;
    }

    public boolean getIsPay() {
        return isPay;
    }

    public void setPay(boolean pay) {
        isPay = pay;
    }
    public double caculatePrice(){
        LocalDateTime timeNow = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        if(timeNow.isBefore(checkout)) {
            System.out.println("Da thay doi thoi gian checkout");
            checkout = timeNow;
            System.out.println("checkout: " + checkout);

//            // Cập nhật booking trong danh sách của RoomManager
//            for (Room r : RoomManager.getRooms()) {
//                // Tìm room có cùng ID với booking hiện tại
//                if (r.getRoomID().equals(room.getRoomID())) {
//                    // Duyệt qua danh sách bookings của room đó
//                    for (int i = 0; i < r.getBookings().size(); i++) {
//                        Booking b = r.getBookings().get(i);
//                        // Giả sử checkin là duy nhất cho mỗi booking,
//                        // nếu tìm thấy booking có checkin trùng với booking hiện tại thì thay thế bằng "this"
//                        if(b.getCheckin().equals(this.getCheckin())) {
//                            r.getBookings().set(i, this);
//                            System.out.println("Đã cập nhật booking trong room " + r.getRoomID());
//                            break;
//                        }
//                    }
//                    break;
//                }
//            }

//            RoomManager.saveRoomsToFile();
//            GuestManager.saveGuestsToFile();
//            RoomManager.loadRoomsFromFile();
//            System.out.println("check danh sach sau khi doi checkout");
//            for (Room room : RoomManager.getRooms()) {
//                for (Booking booking : room.getBookings()) {
//                    System.out.println("Phòng: " + room.getRoomID() +
//                            ", Checkout mới: " + booking.getCheckout());
//                }
//            }
        }
        long hours = ChronoUnit.HOURS.between(checkin, checkout);
        return hours * room.getPrice();

    }

    public boolean isSelected() {
        return isSelected.get();
    }

    public void setSelected( boolean selected) {
        this.isSelected.set(selected);
    }
    public BooleanProperty isSelectedProperty(){
        return isSelected;
    }
    public int numberHour(){
        LocalDateTime timeNow = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        if(timeNow.isBefore(checkout)) {
            checkout = timeNow;
        }
        long hours = ChronoUnit.HOURS.between(checkin, checkout);
        return (int)hours;
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.isSelected = new SimpleBooleanProperty(false); // Khởi tạo lại biến transient
    }



}

