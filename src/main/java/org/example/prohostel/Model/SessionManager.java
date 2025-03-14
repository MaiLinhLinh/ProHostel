package org.example.prohostel.Model;

public class SessionManager {
    private static UserAccount currentAccount;

    public static void setCurrentAccount(UserAccount Account){
        currentAccount = Account;
    }

    public static UserAccount getCurrentAccount() {
        return currentAccount;
    }
}
