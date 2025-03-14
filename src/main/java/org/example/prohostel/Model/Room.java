package org.example.prohostel.Model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Room implements Serializable{
    private String roomID;
    private String roomType;
    private double price;
    private ArrayList<Booking> timeBookings;// danh sach dat phong cua phong nay: vi du nguoi A dat tu 10h den 12h
    private transient  BooleanProperty isSelected = new SimpleBooleanProperty(false);
    public Room(String roomID, String roomType,double price){
        this.roomID = roomID;
        this.roomType = roomType;
        this.timeBookings = new ArrayList<Booking>();
        this.price = price;
    }

    public boolean isBooking(LocalDateTime checkinTime, LocalDateTime checkoutTime){
        for(Booking timeBooking: timeBookings){
            LocalDateTime existCheckin = timeBooking.getCheckin();
            LocalDateTime existCheckout = timeBooking.getCheckout();
            if(checkinTime.isBefore(existCheckout) && checkoutTime.isAfter(existCheckin)){
                System.out.println("🚨 Phòng đã bị đặt bởi " + timeBooking.getGuest().getName() +
                    " từ " + existCheckin + " đến " + existCheckout);
                return true; // phong da bi dat trong thoi gian nay
            }
        }
        return false; // khoang thoi gian nay phong trong
    }

    public void setTimeBookings(Booking booking) {
        timeBookings.add(booking);
        RoomManager.saveRoomsToFile();
    }

    public ArrayList<Booking> getBookings() {
        return timeBookings;
    }

    public String getRoomID() {
        return roomID;
    }

    public void setRoomID(String roomID) {
        this.roomID = roomID;
    }

    public String getRoomType() {
        return roomType;
    }

    public void setRoomType(String roomType) {
        this.roomType = roomType;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

//    public boolean isOccupied(String roomID){
//        LocalDateTime timeNow = LocalDateTime.now();
//        for(Booking booking: timeBookings){
//            if(booking.getRoom().getRoomID().equals(roomID) && timeNow.isBefore(booking.getCheckout()))
//                return true;
//        }
//        return false;
//    }
public boolean isOccupied() {
    LocalDateTime timeNow = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);;
    System.out.println("🔍 Kiểm tra trạng thái phòng " + roomID + " tại " + timeNow);

    for (Booking booking : timeBookings) {
        LocalDateTime checkin = booking.getCheckin();
        LocalDateTime checkout = booking.getCheckout();
        System.out.println("  - Đặt phòng từ " + checkin + " đến " + checkout);

        System.out.println("timenow " + timeNow);
        System.out.println(timeNow.isBefore(checkout));
        if (timeNow.isBefore(checkout)) {
            System.out.println("✅ Phòng này đang được thuê.");
            return true;
        }
    }
    System.out.println("❌ Phòng này đang trống.");
    return false;
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

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.isSelected = new SimpleBooleanProperty(false); // Khởi tạo lại biến transient
    } //day la cach khoi phuc transient tu dong


//    public void restoreTransientFields() {
//        if (this.isSelected == null) {
//            this.isSelected = new SimpleBooleanProperty(false);
//        }
//    } // khoi phuc transient thu cong


}

