package org.example.prohostel.Model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class GuestManager {
    private static final ArrayList<Guest> guests = new ArrayList<Guest>();


    // tim khach hang theo CCCD
    public Guest getGuest(String IDcard){
        for(Guest guest: guests){
            if(guest.getIDCard().equals(IDcard))
                return guest;
        }
        return null;
    }
    // them khach hang va dat phong
    public void addGuest(String name, String birthday, String numberPhone, String IDCard, String address, String national, Room room, LocalDateTime checkinTime, LocalDateTime checkoutTime){
        Guest guest = getGuest(IDCard);
        if(room.isBooking(checkinTime, checkoutTime) == false) {
            if (guest == null) {
                Guest newGuest = new Guest(name, birthday, numberPhone, IDCard, address, national);
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
