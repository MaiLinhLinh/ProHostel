package org.example.prohostel.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
// luu thong tin 1 lan dat phong
// moi lan dat co thong tin khach hang, phong, thoi gian dat
public class Booking implements Serializable {
    private User guest;
    private Room room;
    private LocalDateTime checkin;
    private LocalDateTime checkout;
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
}

