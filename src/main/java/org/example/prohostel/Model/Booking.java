package org.example.prohostel.Model;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

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
        LocalDateTime timeNow = LocalDateTime.now();
        if(timeNow.isBefore(checkout))
            checkout = timeNow;
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
        LocalDateTime timeNow = LocalDateTime.now();
        if(timeNow.isBefore(checkout))
            checkout = timeNow;
        long hours = ChronoUnit.HOURS.between(checkin, checkout);
        return (int)hours;
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.isSelected = new SimpleBooleanProperty(false); // Khởi tạo lại biến transient
    }
}

