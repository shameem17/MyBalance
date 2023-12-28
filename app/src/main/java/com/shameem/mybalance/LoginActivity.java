package com.shameem.mybalance;

import static android.view.View.VISIBLE;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

public class LoginActivity extends AppCompatActivity {


    EditText loginEmail, loginPassword;
    TextView signupRedirectText;
    Button loginButton;
    FirebaseAuth auth;
    String email, password;
    ProgressBar progressBar;
    private static final String TAG = "LoginActivity";
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        FirebaseApp.initializeApp(this);

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        signupRedirectText = findViewById(R.id.signupRedirectText);
        loginButton = findViewById(R.id.login_button);
        progressBar = findViewById(R.id.login_activityBar);
        auth = FirebaseAuth.getInstance();

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                    email = loginEmail.getText().toString();
                    password = loginPassword.getText().toString();

                    if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
                        auth.signInWithEmailAndPassword(email, password)
                                .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            FirebaseUser user = auth.getCurrentUser();
                                            //progressBar.setVisibility(View.GONE);
                                            Toast.makeText(LoginActivity.this,"Log in Successful: "+user.getUid(), Toast.LENGTH_SHORT).show();

                                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                                            DocumentReference docRef = db.collection("users").document(user.getUid());
                                            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if (task.isSuccessful()) {
                                                        DocumentSnapshot document = task.getResult();
                                                        if (document.exists()) {
                                                            Log.i(TAG, "DocumentSnapshot data: " + document.getData());
                                                            progressBar.setVisibility(View.GONE);
                                                            finish();
                                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                            intent.putExtra("email", email);
                                                            startActivity(intent);
                                                        } else {
                                                            Log.i(TAG, "No such document");
                                                        }
                                                    } else {
                                                        Log.i(TAG, "get failed with ", task.getException());
                                                    }
                                                }
                                            });





                                        } else {
                                            progressBar.setVisibility(View.GONE);
                                            Toast.makeText(LoginActivity.this,"Log in Failed", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
            }
        });

        signupRedirectText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });

    }
}