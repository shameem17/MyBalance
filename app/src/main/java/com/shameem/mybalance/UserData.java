package com.shameem.mybalance;

public class UserData {
    String name;
    String phoneNumber;
    String createdAt;
    String uid;
    String balance;
    String email;

    String costCategory;


    public UserData(String name, String phoneNumber, String createdAt, String uid, String balance, String email, String costCategory) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.uid = uid;
        this.balance = balance;
        this.email = email;
        this.costCategory = costCategory;
    }
}
