package org.example.prohostel.Model;

import java.util.ArrayList;

public class UserAccountManager {
    private static final ArrayList<UserAccount> users = new ArrayList<UserAccount>();
    // Them tai khoan mac dinh
    static {
        users.add(new UserAccount("guest", "guest", "Guest"));
        users.add(new UserAccount("admin", "admin", "Admin"));
    }
    // tim tai khoan trong danh sach de dang ki
    private static UserAccount getUser(String userName, String password, String role){
        for(UserAccount user: users){
            if(user.getUserName().equals(userName) && user.getPassword().equals(password) && user.getRole().equals(role))
                return user;
        }
        return null;
    }


    // dang ki tai khoan moi
    public static boolean register(String userName, String password, String role){
        if(getUser(userName, password, role) == null) {
            users.add(new UserAccount(userName, password, role));
            return true;
        }
        return false;

    }
    // tim tai khoan dang nhap
    private static UserAccount getUser(String userName){
        for(UserAccount user: users){
            if(user.getUserName().equals(userName))
                return user;
        }
        return null;
    }
    // kiem tra dang nhap
    public static UserAccount loginCheck(String userName, String password, String role){
        if(getUser(userName) != null){
            UserAccount user = getUser(userName);
            if(user.getPassword().equals(password) && user.getRole().equals(role))
                    return user;
        }
        return null;
    }

}
