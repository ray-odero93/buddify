package com.guritchistudios.buddify;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

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

        existingAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
            }
        });
        
    }

    private void registerUser(String userName, String userEmail, String userPass) {
    }
}