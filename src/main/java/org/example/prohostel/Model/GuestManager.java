package org.example.prohostel.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class GuestManager {
    private static final ArrayList<User> guests = new ArrayList<User>();


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
                newGuest.addGuestBooking(newBooking);
                room.setTimeBookings(newBooking);

               // roomManager.addBooking(newBooking);
            } else {
                Booking newBooking = new Booking(guest, room, checkinTime, checkoutTime);
                guest.addGuestBooking(newBooking);
                room.setTimeBookings(newBooking);
                //roomManager.addBooking(newBooking);
            }
            System.out.println("Dat phong thanh cong");
        }
        else{
            System.out.println("Phong da bi dat trong khoang thoi gian nay");
        }
    }

}
