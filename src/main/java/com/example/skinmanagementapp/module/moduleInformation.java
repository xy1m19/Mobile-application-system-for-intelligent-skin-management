package com.example.skinmanagementapp.module;
import android.media.Image;
import androidx.annotation.NonNull;

public class moduleInformation {

    public static String Last_Name;
    public static String First_Name;
    public static String Email;
    public static String password;
    public static String Age;
    public static String Address_1;
    public static String Address_2;
    public static String Address_3;
    public static String uid;
    public static String Conform;
    /*private String Last_Name;
    private String First_Name;
    private String Email;
    private String password;
    private String Conform;
    private String uid;
    private String Age;
    private String Address_1;
    private String Address_2;
    private String Address_3;
    */


    public moduleInformation() {
    }

    public moduleInformation(String last_Name, String first_Name,
                             String email, String password, String conform,
                             String uid, String age,
                             String address_1, String address_2, String address_3) {
        Last_Name = last_Name;
        First_Name = first_Name;
        Email = email;
        this.password = password;
        Conform = conform;
        this.uid = uid;
        Age = age;
        Address_1 = address_1;
        Address_2 = address_2;
        Address_3 = address_3;
    }


    public String getLast_Name() {
        return Last_Name;
    }

    public void setLast_Name(String last_Name) {
        Last_Name = last_Name;
    }

    public String getFirst_Name() {
        return First_Name;
    }

    public void setFirst_Name(String first_Name) {
        First_Name = first_Name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConform() {
        return Conform;
    }

    public void setConform(String conform) {
        Conform = conform;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getAddress_1() {
        return Address_1;
    }

    public void setAddress_1(String address_1) {
        Address_1 = address_1;
    }

    public String getAddress_2() {
        return Address_2;
    }

    public void setAddress_2(String address_2) {
        Address_2 = address_2;
    }

    public String getAddress_3() {
        return Address_3;
    }

    public void setAddress_3(String address_3) {
        Address_3 = address_3;
    }
}


