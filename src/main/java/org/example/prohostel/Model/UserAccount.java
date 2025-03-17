package org.example.prohostel.Model;

import java.io.Serializable;

public class UserAccount implements Serializable {

    private String userName;
    private String password;
    private String role;

    public UserAccount(String userName, String password, String role){
        this.userName = userName;
        this.password = password;
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
