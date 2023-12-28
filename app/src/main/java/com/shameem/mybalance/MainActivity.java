package com.shameem.mybalance;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView nameView, emailView, balanceView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nameView = findViewById(R.id.name);
        emailView = findViewById(R.id.email);
        balanceView = findViewById(R.id.balance);
        Intent intent = getIntent();
        String email = intent.getStringExtra("email");
        emailView.setText(email);
    }
}