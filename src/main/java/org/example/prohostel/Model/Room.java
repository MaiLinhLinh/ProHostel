package org.example.prohostel.Model;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class Room implements Serializable{
    private String roomID;
    private String roomType;
    private double price;
    private ArrayList<Booking> timeBookings; // danh sach dat phong cua phong nay: vi du nguoi A dat tu 10h den 12h
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
    public boolean isOccupied(String roomID){
        LocalDateTime timeNow = LocalDateTime.now();
        for(Booking booking: timeBookings){
            if(booking.getRoom().getRoomID().equals(roomID) && timeNow.isBefore(booking.getCheckout()))
                return true;
        }
        return false;
    }
}
