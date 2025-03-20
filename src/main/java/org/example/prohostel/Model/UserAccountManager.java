package org.example.prohostel.Model;

import java.io.*;
import java.util.ArrayList;

public class UserAccountManager {
    private static  ArrayList<UserAccount> users = new ArrayList<UserAccount>();
    public UserAccountManager(){
        loadAccountsFromFile();
    }
    // Them tai khoan mac dinh
    static {
        users.add(new UserAccount("guest", "guest", "Guest"));
        users.add(new UserAccount("admin", "admin", "Admin"));
        saveAccountsToFile();
    }
    // tim tai khoan trong danh sach de dang ki
    private static UserAccount getUser(String userName, String password){
        for(UserAccount user: users){
            if(user.getUserName().equals(userName) && user.getPassword().equals(password))
                return user;
        }
        return null;
    }


    // dang ki tai khoan moi
    public static boolean register(String userName, String password){
        if(getUser(userName, password) == null) {
            users.add(new UserAccount(userName, password, "Guest"));
            saveAccountsToFile();
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
    public static UserAccount loginCheck(String userName, String password){
        if(getUser(userName) != null){
            UserAccount user = getUser(userName);
            if(user.getPassword().equals(password))
                    return user;
        }
        return null;
    }
    public static void saveAccountsToFile() {
        try {
            FileOutputStream fileAccount = new FileOutputStream("Accounts.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fileAccount);
            oos.writeObject(users);
            oos.close();
            fileAccount.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static void loadAccountsFromFile(){
        File file = new File("Accounts.dat");
        if (!file.exists() || file.length() == 0) {
            return;
        }
        try{
            FileInputStream fileAccount = new FileInputStream("Accounts.dat");
            ObjectInputStream ois = new ObjectInputStream(fileAccount);
            users = (ArrayList<UserAccount>) ois.readObject();
            ois.close();
            fileAccount.close();
        }catch(FileNotFoundException e){
            e.printStackTrace();
        } catch(IOException e){
            e.printStackTrace();
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }

    }
    public static ArrayList<UserAccount> getAccounts(){
        return users;
    }

}
