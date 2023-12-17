package com.shameem.mybalance;

import com.google.firebase.auth.FirebaseAuth;

public class Authentication {

    private static volatile Authentication INSTANCE = null;

    private Authentication() {}
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public void signUp(){

    }

    private void signIn(){

    }

    public static Authentication getInstance() {

        if(INSTANCE == null) {
            synchronized (Authentication.class) {
                if (INSTANCE == null) {
                    INSTANCE = new Authentication();
                }
            }
        }
        return INSTANCE;
    }

}
