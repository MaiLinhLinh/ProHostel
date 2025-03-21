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
    private UserAccount userAccount;


    public Booking(UserAccount userAccount,User guest, Room room, LocalDateTime checkinTime, LocalDateTime checkoutTime){
        this.userAccount = userAccount;
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
    public double caculatePrice(LocalDateTime now, boolean isPaying){
        LocalDateTime effectiveCheckout = this.checkout;
        if(isPaying && now.isBefore(this.checkout)) {
            System.out.println("Da thay doi thoi gian checkout");
            effectiveCheckout = now;
            System.out.println("checkout: " + effectiveCheckout);

        }
        long hours = ChronoUnit.HOURS.between(this.checkin, effectiveCheckout);
        if(hours < 1)
            hours = 1;
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
    public int numberHour(LocalDateTime now, boolean isPaying){
        LocalDateTime currentCheckout = this.checkout;
        if(isPaying && now.isBefore(checkout)) {
            System.out.println("Admin dang thanh toan");
            currentCheckout = now;
        }
        long hours = ChronoUnit.HOURS.between(this.checkin, currentCheckout);
        if(hours < 1)
            hours = 1;
        return (int)hours;
    }
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        this.isSelected = new SimpleBooleanProperty(false); // Khởi tạo lại biến transient
    }

    public UserAccount getUserAccount() {
        return userAccount;
    }

    public void setCheckout(LocalDateTime checkout) {
        this.checkout = checkout;
    }
}

