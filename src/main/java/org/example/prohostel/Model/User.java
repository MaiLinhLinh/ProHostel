package org.example.prohostel.Model;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class User {
    private String name;
    private String numberPhone;
    private String birthday;
    private String IDCard;
    private String address;
    private String national;
    private String sex;

    private ArrayList<Booking> guestBookings;// danh sach dat phong cua khach

    public User(String name, String birthday, String sex, String numberPhone, String IDCard, String address, String national){
        this.name = name;
        this.birthday = birthday;
        this.sex = sex;
        this.numberPhone = numberPhone;
        this.IDCard = IDCard;
        this.address = address;
        this.national = national;

        this.guestBookings = new ArrayList<Booking>();
    }


    public void addGuestBooking(Booking booking){
        guestBookings.add(booking);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIDCard() {
        return IDCard;
    }

    public void setIDCard(String IDCard) {
        this.IDCard = IDCard;
    }

    public String getNational() {
        return national;
    }

    public void setNational(String national) {
        this.national = national;
    }

    public String getNumberPhone() {
        return numberPhone;
    }

    public void setNumberPhone(String numberPhone) {
        this.numberPhone = numberPhone;
    }

    public ArrayList<Booking> getGuestBooking() {
        return guestBookings;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


}
