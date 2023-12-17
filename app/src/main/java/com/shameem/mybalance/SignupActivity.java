package com.shameem.mybalance;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.auth.User;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class SignupActivity extends AppCompatActivity {


    EditText signUpName, signUpEmail, signUpPhone, signUpPassword, signUpConfirmPassword;
    TextView loginRedirectText;
    ProgressBar progressBar;
    Button signUpButton;
    String email = "", password = "", confirmPassword = "", phoneNumber = "", name = "";
    FirebaseAuth auth;
    FirebaseFirestore database;
    private static final String TAG = "SignupActivity";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        FirebaseApp.initializeApp(this);
        signUpName = findViewById(R.id.signup_name);
        signUpEmail = findViewById(R.id.signup_email);
        signUpPhone = findViewById(R.id.signup_phone);
        signUpPassword = findViewById(R.id.signup_password);
        signUpConfirmPassword = findViewById(R.id.signup_confirmpassword);
        loginRedirectText = findViewById(R.id.loginRedirectText);
        signUpButton = findViewById(R.id.signup_button);
        progressBar = findViewById(R.id.signup_activityBar);

        auth = FirebaseAuth.getInstance();
        database = FirebaseFirestore.getInstance();

        loginRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                //Log.println("Here");
                email = signUpEmail.getText().toString();
                name = signUpName.getText().toString();
                password = signUpPassword.getText().toString();
                confirmPassword = signUpConfirmPassword.getText().toString();
                phoneNumber = signUpPhone.getText().toString();

                if(isVaild(email,name,phoneNumber,password,confirmPassword)){
                   // progressBar.setVisibility(View.VISIBLE);

                    auth.createUserWithEmailAndPassword(email, password)
                            .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                      Toast.makeText(SignupActivity.this, "User Authenticated Successfully",
                                              Toast.LENGTH_SHORT).show();

                                       FirebaseUser user = auth.getCurrentUser();
                                        // Get the current time
                                        Date currentTime = new Date();

                                        // Define the desired format
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());

                                        // Format the current time
                                        String formattedTime = dateFormat.format(currentTime);
                                       Map userData = new HashMap<>();
                                       userData.put("name", name);
                                       userData.put("phone", phoneNumber);
                                       userData.put("createdAt", formattedTime);


                                        database.collection("users").document(user.getUid())
                                                .set(userData)
                                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                    @Override
                                                    public void onSuccess(Void aVoid) {
                                                        // Data has been successfully written to Firestore
                                                        progressBar.setVisibility(View.GONE);
                                                        Log.i(TAG, "successful");
                                                        Toast.makeText(SignupActivity.this, "User data stored in Firestore", Toast.LENGTH_SHORT).show();
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        // Handle errors
                                                        progressBar.setVisibility(View.GONE);
                                                        Log.i(TAG, "successful" + e.getMessage());
                                                        Toast.makeText(SignupActivity.this, "Error storing user data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    } else {
                                        // If sign in fails, display a message to the user.

                                        Toast.makeText(SignupActivity.this, "Authentication failed.",
                                                Toast.LENGTH_SHORT).show();
                                        progressBar.setVisibility(View.GONE);
                                       // updateUI(null);
                                    }
                                }
                            });
                } else{
                    progressBar.setVisibility(View.GONE);
                }


            }

        });



    }
    private boolean isVaild(String email, String name,String phoneNumber, String password, String confirmPassword){

        if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(phoneNumber) || TextUtils.isEmpty(password) || TextUtils.isEmpty(confirmPassword)){
            Toast.makeText(SignupActivity.this, "One or more fields are empty!", Toast.LENGTH_SHORT).show();
            return false;
        }
     else if(TextUtils.equals(password,confirmPassword))
        {
            return true;
        }


        return false;
    }


}