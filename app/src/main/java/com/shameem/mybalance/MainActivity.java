package com.shameem.mybalance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.firestore.auth.User;

public class MainActivity extends AppCompatActivity {

    TextView nameView, emailView, balanceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameView = findViewById(R.id.name);
        emailView = findViewById(R.id.email);
        balanceView = findViewById(R.id.balance);

        UserData userData = UserData.getInstance();

        String email = userData.email;
        String name = userData.name;
        String phone = userData.phoneNumber;
        String balance = userData.balance;
        balanceView.setText(balance);
        emailView.setText(email);
        nameView.setText(name);
    }
}