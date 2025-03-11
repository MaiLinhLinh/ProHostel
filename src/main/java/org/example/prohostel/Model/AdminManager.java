package org.example.prohostel.Model;

import java.util.ArrayList;

public class AdminManager {

    private RoomManager roomManager = new RoomManager();


    // them phong
    public void addRoom(Room room){
        roomManager.getRooms().add(room);
        System.out.println("Them phong thanh cong");
    }
    private Room getRoom(String roomID, String roomType){
        for(Room room: roomManager.getRooms()){
            if(room.getRoomID().equals(roomID) && room.getRoomType().equals(roomType));
                return room;
        }
        return null;
    }

    public void delateRoom(String roomID, String roomType){
        Room room = getRoom(roomID, roomType);
        if(room != null){
            roomManager.getRooms().remove(room);
            System.out.println("Xoa sach thanh cong");
            return;
        }
        System.out.println("Khong tim thay sach");
    }

    public void updateRoom(Room room, String newRoomID, String newRoomType){
        room.setRoomID(newRoomID);
        room.setRoomType(newRoomType);
        System.out.println("Chinh sua sach thanh cong");
    }

}
