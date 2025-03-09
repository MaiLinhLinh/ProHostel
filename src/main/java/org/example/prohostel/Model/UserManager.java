package org.example.prohostel.Model;

import java.util.ArrayList;

public class UserManager {
    private static final ArrayList<User> users = new ArrayList<User>();
    // Them tai khoan mac dinh
    static {
        users.add(new User("guest", "guest", "Guest"));
        users.add(new User("admin", "admin", "Admin"));
    }
    // tim tai khoan trong danh sach de dang ki
    private static User getUser(String userName, String password, String role){
        for(User user: users){
            if(user.getUserName().equals(userName) && user.getPassword().equals(password) && user.getRole().equals(role))
                return user;
        }
        return null;
    }


    // dang ki tai khoan moi
    public static boolean register(String userName, String password, String role){
        if(getUser(userName, password, role) == null) {
            users.add(new User(userName, password, role));
            return true;
        }
        return false;

    }
    // tim tai khoan dang nhap
    private static User getUser(String userName){
        for(User user: users){
            if(user.getUserName().equals(userName))
                return user;
        }
        return null;
    }
    // kiem tra dang nhap
    public static User loginCheck(String userName, String password, String role){
        if(getUser(userName) != null){
            User user = getUser(userName);
            if(user.getPassword().equals(password) && user.getRole().equals(role))
                    return user;
        }
        return null;
    }

}
