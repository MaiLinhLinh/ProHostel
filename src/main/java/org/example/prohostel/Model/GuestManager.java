package org.example.prohostel.Model;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class GuestManager {
    private static final ArrayList<User> guests = new ArrayList<User>();

    static{
        LocalDateTime checkin1 = LocalDateTime.of(2025, 3, 15, 14, 0);
        LocalDateTime checkout1 = LocalDateTime.of(2025, 3, 15, 12, 0);
        User guest = new User("an", "12", "nu", "123", "122", "na", "vn");
        guests.add(guest);
        RoomManager roomManager = new RoomManager();
        ArrayList<Room> rooms = roomManager.getRooms();
        Room selectedRoom = null;
        for(Room room: rooms){
            selectedRoom = room;
            break;
        }
        Booking booking = new Booking(guest, selectedRoom, checkin1, checkout1);
        guest.addGuestBooking(booking);
        for(Booking book: guest.getGuestBooking()){
            System.out.println(book.getRoom().getRoomID());
        }
        System.out.println("Da co 1 khach dat phong");
    }


    // tim khach hang theo CCCD
    public User getGuest(String IDcard){
        for(User guest: guests){
            if(guest.getIDCard().equals(IDcard))
                return guest;
        }
        return null;
    }
    // them khach hang va dat phong
    public void addGuest(String name, String birthday,String sex, String numberPhone, String IDCard, String address, String national, Room room, LocalDateTime checkinTime, LocalDateTime checkoutTime){
        User guest = getGuest(IDCard);
        if(room.isBooking(checkinTime, checkoutTime) == false) {
            if (guest == null) {
                User newGuest = new User(name, birthday, sex, numberPhone, IDCard, address, national);
                guests.add(newGuest);
                Booking newBooking = new Booking(newGuest, room, checkinTime, checkoutTime);
                room.setTimeBookings(newBooking);
                newGuest.addGuestBooking(newBooking);

            } else {
                Booking newBooking = new Booking(guest, room, checkinTime, checkoutTime);
                room.setTimeBookings(newBooking);
                guest.addGuestBooking(newBooking);
            }
            System.out.println("Dat phong thanh cong");
        }
        else{
            System.out.println("Phong da bi dat trong khoang thoi gian nay");
        }
    }

    public ArrayList<User> getListGuests() {
        return guests;
    }
}
