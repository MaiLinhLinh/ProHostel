package org.example.prohostel.Model;

import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

public class GuestManager {
    private static ArrayList<User> guests = new ArrayList<User>();

    private static RoomManager roomManager = new RoomManager();
    public GuestManager(){
        loadGuestFromFile();
    }

    static {
        LocalDateTime checkin1 = LocalDateTime.of(2025, 3, 17, 14, 0);
        LocalDateTime checkout1 = LocalDateTime.of(2025, 3, 19, 12, 0);
        User guest = new User("an", "12", "nu", "123", "122", "na", "vn");
        guests.add(guest);
        ArrayList<Room> rooms = roomManager.getRooms();
        Room selectedRoom = null;
        for (Room room : rooms) {
            selectedRoom = room;
            break;
        }
        Booking booking = new Booking(guest, selectedRoom, checkin1, checkout1);
        selectedRoom.setTimeBookings(booking);
        System.out.println("phong " + selectedRoom.getRoomID() + " co " + selectedRoom.getBookings().size() + " luot dat phong");

        guest.addGuestBooking(booking);
        saveGuestsToFile();
//        for(Booking book: guest.getGuestBooking()){
//            System.out.println(book.getRoom().getRoomID());
//        }
        System.out.println("Da co 1 khach dat phong");
    }


    // tim khach hang theo CCCD
    public User getGuest(String IDcard) {
        for (User guest : guests) {
            if (guest.getIDCard().equals(IDcard))
                return guest;
        }
        return null;
    }

    // them khach hang va dat phong
    public void addGuest(String name, String birthday, String sex, String numberPhone, String IDCard, String address, String national, Room room, LocalDateTime checkinTime, LocalDateTime checkoutTime) {
        User guest = getGuest(IDCard);

        if (room.isBooking(checkinTime, checkoutTime) == false) {
            if (guest == null) {
                User newGuest = new User(name, birthday, sex, numberPhone, IDCard, address, national);
                guests.add(newGuest);
                Booking newBooking = new Booking(newGuest, room, checkinTime, checkoutTime);
                room.setTimeBookings(newBooking);
                System.out.println("phong " + room.getRoomID() + " co " + room.getBookings().size() + " luot dat phong");
                newGuest.addGuestBooking(newBooking);
                saveGuestsToFile();

            } else {
                Booking newBooking = new Booking(guest, room, checkinTime, checkoutTime);
                room.setTimeBookings(newBooking);
                System.out.println("phong " + room.getRoomID() + " co " + room.getBookings().size() + " luot dat phong");
                guest.addGuestBooking(newBooking);
                saveGuestsToFile();
            }
            System.out.println("Dat phong thanh cong");
            for (Booking book : room.getBookings()) {
                System.out.println("so phong " + book.getRoom().getRoomID() + " checkin " + book.getCheckin() + " checkout " + book.getCheckout());
            }

        } else {
            System.out.println("Phong da bi dat trong khoang thoi gian nay");
        }

    }

    public ArrayList<User> getListGuests() {
        return guests;
    }


    public static void saveGuestsToFile() {
//        for(Room room: rooms){
//            System.out.println("so phong "+ room.getRoomID()+ "loai phong " + room.getRoomType() + "gia tien " + room.getPrice());
//        }
        try {
            FileOutputStream fileGuest = new FileOutputStream("Guests.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fileGuest);
            oos.writeObject(guests);
            oos.close();
            fileGuest.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void loadGuestFromFile() {
        File file = new File("Rooms.dat");
        if (!file.exists() || file.length() == 0) {
            return;
        }
        try {
            FileInputStream fileGuest = new FileInputStream("Guests.dat");
            ObjectInputStream ois = new ObjectInputStream(fileGuest);
            guests = (ArrayList<User>) ois.readObject();
            System.out.println("check load file");
//            for (Room room : guests) {
//                System.out.println("Phòng " + room.getRoomID() + " có " + room.getBookings().size() + " lượt đặt phòng.");
//            }
            ois.close();
            fileGuest.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void delateGuest(User guest){
        guests.remove(guest);
        saveGuestsToFile();
    }
}
