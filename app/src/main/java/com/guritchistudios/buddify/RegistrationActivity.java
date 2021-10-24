package com.guritchistudios.buddify;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {

    private EditText name, email, password;
    private Button mRegister;
    private TextView existingAccount;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Create Account");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        name = findViewById(R.id.register_name);
        email = findViewById(R.id.register_email);
        password = findViewById(R.id.register_pass);
        mRegister = findViewById(R.id.register_button);
        existingAccount = findViewById(R.id.homepage);
        mAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Register");

        mRegister.setOnClickListener(view -> {
            String userName = name.getText().toString().trim();
            String userEmail = email.getText().toString().trim();
            String userPass = password.getText().toString().trim();
            if (!Patterns.EMAIL_ADDRESS.matcher(userEmail).matches()) {
                email.setError("Invalid e-mail address!");
                email.setFocusable(true);
            } else if (userPass.length() < 8) {
                password.setError("Password length must be more than 8 characters.");
                password.setFocusable(true);
            } else {
                registerUser(userName, userEmail, userPass);
            }
        });

        existingAccount.setOnClickListener(view -> startActivity(new Intent(RegistrationActivity.this, LoginActivity.class)));
    }

    private void registerUser(String userName, String userEmail, String userPass) {
        progressDialog.show();
        mAuth.createUserWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                progressDialog.dismiss();
                FirebaseUser user = mAuth.getCurrentUser();
                assert user != null;
                String email = user.getEmail();
                String userId = user.getUid();
                HashMap<Object, String> hashMap = new HashMap<>();
                hashMap.put("email", email);
                hashMap.put("userId", userId);
                hashMap.put("name", userName);
                hashMap.put("onlineStatus", "online");
                hashMap.put("typingTo", "noOne");
                hashMap.put("image", "");
                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                DatabaseReference databaseReference = firebaseDatabase.getReference("Users");
                databaseReference.child(userId).setValue(hashMap);
                Toast.makeText(RegistrationActivity.this, "Registered User" + user.getEmail(), Toast.LENGTH_LONG).show();
                Intent mainIntent = new Intent(RegistrationActivity.this, DashboardActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                finish();
            } else {
                progressDialog.dismiss();
                Toast.makeText(RegistrationActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(RegistrationActivity.this, "Error occurred", Toast.LENGTH_LONG).show();
                });
    }
}