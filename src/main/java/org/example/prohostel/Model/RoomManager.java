package org.example.prohostel.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Locale;

public class RoomManager {
    private ArrayList<Room> rooms;
    private static ArrayList<Booking> bookings;// danh sach dat phong

    public RoomManager(ArrayList<Room> rooms){
        this.rooms = rooms;
        this.bookings = new ArrayList<Booking>();
    }

    // lay danh sach phong con trong trong khoang thoi gian
    public ArrayList<Room> getRoomAvailable(LocalDateTime checkinTime, LocalDateTime checkoutTime){
        ArrayList<Room> availableRooms = new ArrayList<Room>();
       for(Room room: rooms){
           if(room.isBooking(checkinTime, checkoutTime) == false)
               availableRooms.add(room);
       }
       return availableRooms;
    }

    public void addBooking(Booking booking){
        bookings.add(booking);
    }
    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public static ArrayList<Booking> getBookings() {
        return bookings;
    }
}
