package org.example.prohostel.Model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;

public class RoomManager {
    private ArrayList<Room> rooms;

    public RoomManager(ArrayList<Room> rooms){
        this.rooms = rooms;
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


    public ArrayList<Room> getRooms() {
        return rooms;
    }

    public void addRooms(Room room) {
        rooms.add(room);
    }


}
