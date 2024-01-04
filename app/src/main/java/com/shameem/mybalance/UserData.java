package com.shameem.mybalance;

class UserData {
    String name;
    String phoneNumber;
    String createdAt;
    String uid;
    String balance;
    String email;

    String costCategory;

    private static  volatile UserData instance = null;

    private UserData(){

    }
    private UserData(String name, String phoneNumber, String createdAt, String uid, String balance, String email, String costCategory) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.createdAt = createdAt;
        this.uid = uid;
        this.balance = balance;
        this.email = email;
        this.costCategory = costCategory;
    }

    public static synchronized UserData getInstance(){
        if (instance == null){
            instance = new UserData();
        }
        return instance;
    }
}
