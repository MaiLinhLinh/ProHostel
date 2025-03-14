package org.example.prohostel.Model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Invoice implements Serializable {
    private User guest;
    private LocalDateTime paymentTime;
    private ArrayList<Booking> payedBookings;
    private double totalPay;
    private boolean isPay;
    private String account;

    public Invoice(User guest, String account, ArrayList<Booking> payedBookings, LocalDateTime paymentTime){
        this.guest = guest;
        this.account = account;
        this.payedBookings = payedBookings;
        this.paymentTime = paymentTime;
        totalPay = 0;
        isPay = false;
    }
    public double getTotalPay(){
        totalPay = 0;
        for(Booking booking: payedBookings){
            totalPay += booking.caculatePrice();
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

    public String getAccount() {
        return account;
    }
}
