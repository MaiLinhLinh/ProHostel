package org.example.prohostel.Model;

import javafx.scene.control.Alert;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Locale;

public class RoomManager {
    private static ArrayList<Room> rooms = new ArrayList<Room>();

    public RoomManager(){
        loadRoomsFromFile();
    }
    static{
        Room room01 = new Room("101", "Phong don", 1000);
        Room room03 = new Room("102", "Phong doi", 2000);
        rooms.add(room01);
        System.out.println("Da luu 1 phong");
        rooms.add(room03);
        System.out.println("Da luu 1 phong");
        saveRoomsToFile();
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


    public static ArrayList<Room> getRooms() {
        return rooms;
    }



    public static void addRooms(Room room) {
        rooms.add(room);
        saveRoomsToFile();

    }

    public static void saveRoomsToFile() {
        try {
            FileOutputStream fileRoom = new FileOutputStream("Rooms.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fileRoom);
            oos.writeObject(rooms);
            oos.close();
            fileRoom.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void loadRoomsFromFile(){
        File file = new File("Rooms.dat");
        if (!file.exists() || file.length() == 0) {
            return;
        }
        try{
            FileInputStream fileRoom = new FileInputStream("Rooms.dat");
            ObjectInputStream ois = new ObjectInputStream(fileRoom);
            rooms = (ArrayList<Room>) ois.readObject();
            ois.close();
            fileRoom.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }

    }
//public void loadRoomsFromFile() { // day la cach load file lay transient thu cong
//    File file = new File("rooms.dat");
//    if (!file.exists()) return;
//
//    try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
//        rooms = (ArrayList<Room>) ois.readObject();
//        for (Room room : rooms) {
//            room.restoreTransientFields(); // Khôi phục BooleanProperty
//            System.out.println("Phòng " + room.getRoomID() + " có " + room.getBookings().size() + " lượt đặt phòng.");
//        }
//    } catch (IOException | ClassNotFoundException e) {
//        e.printStackTrace();
//    }
//}





}
