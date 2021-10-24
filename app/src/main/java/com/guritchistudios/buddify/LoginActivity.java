package com.guritchistudios.buddify;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.w3c.dom.Text;

public class LoginActivity extends AppCompatActivity {

    private EditText name, email, password;
    private Button mLogin;
    private TextView needNewAccount, recoveryPass;
    private ProgressDialog progressDialog;
    private FirebaseAuth mAuth;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle("Create Account");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        email = findViewById(R.id.login_email);
        password = findViewById(R.id.login_pass);
        mLogin = findViewById(R.id.login_button);
        needNewAccount = findViewById(R.id.new_account);
        recoveryPass = findViewById(R.id.forget_pass);
        mAuth = FirebaseAuth.getInstance();


        mLogin.setOnClickListener(view -> {
            String userEmail = email.getText().toString().trim();
            String userPass = password.getText().toString().trim();

            loginUser(userEmail, userPass);
        });
    }

    private void loginUser(String userEmail, String userPass) {
    }
}