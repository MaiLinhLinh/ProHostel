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

    public Invoice(User guest, ArrayList<Booking> payedBookings, LocalDateTime paymentTime){
        this.guest = guest;
        this.payedBookings = payedBookings;
        this.paymentTime = paymentTime;
        totalPay = 0;
        isPay = false;
    }
    public double getTotalPay(){
        double sum = 0;
        for(Booking booking: payedBookings){
            sum += booking.caculatePrice();
        }
        return sum;
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
}
