package org.example.prohostel.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Invoice implements Serializable {
    private User guest;
    private LocalDateTime paymentTime;
    private ArrayList<Booking> payedBookings;
    private double totalPay;
    private boolean isPay;
    private UserAccount account;

    public Invoice(User guest, UserAccount account, ArrayList<Booking> payedBookings, LocalDateTime paymentTime){
        this.guest = guest;
        this.account = account;
        this.payedBookings = payedBookings;
        this.paymentTime = paymentTime;
        totalPay = 0;
        isPay = false;
    }
    public double getTotalPay(boolean adminPay){
        totalPay = 0;
        for(Booking booking: payedBookings){
            totalPay += booking.caculatePrice(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), adminPay);
        }
        return totalPay;
    }

    public User getGuest() {
        return guest;
    }

    public ArrayList<Booking> getPayedBookings() {
        return payedBookings;
    }

    public void setPay(boolean pay) {
        isPay = pay;
    }

    public boolean isPay() {
        return isPay;
    }

    public LocalDateTime getPaymentTime() {
        return paymentTime;
    }

    public UserAccount getAccount() {
        return account;
    }
}
