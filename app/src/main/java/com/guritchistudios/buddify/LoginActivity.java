package com.guritchistudios.buddify;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.HashMap;

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
        progressDialog = new ProgressDialog(this);


        mLogin.setOnClickListener(view -> {
            String userEmail = email.getText().toString().trim();
            String userPass = password.getText().toString().trim();

            loginUser(userEmail, userPass);
        });

        needNewAccount.setOnClickListener(view -> startActivity(new Intent(LoginActivity.this, RegistrationActivity.class)));

        recoveryPass.setOnClickListener(view -> showRecoveryPassDialog());
    }

    private void showRecoveryPassDialog() {
    }

    private void loginUser(String userEmail, String userPass) {
        progressDialog.setMessage("Logging in...");
        progressDialog.show();
        mAuth.signInWithEmailAndPassword(userEmail, userPass).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                progressDialog.dismiss();
                FirebaseUser user = mAuth.getCurrentUser();
                if (task.getResult().getAdditionalUserInfo().isNewUser()) {
                    String userEmail1 = user.getEmail();
                    String userId = user.getUid();
                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("email", userEmail1);
                    hashMap.put("uid", userId);
                    hashMap.put("name", "");
                    hashMap.put("onLineStatus", "online");
                    hashMap.put("typingTo", "noOne");
                    hashMap.put("phone", "");
                    hashMap.put("image", "");
                    hashMap.put("cover", "");
                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                    DatabaseReference reference = database.getReference("Users");
                    reference.child(userId).setValue(hashMap);
                }
                assert user != null;
                Toast.makeText(LoginActivity.this, "Registered user" + user.getEmail(), Toast.LENGTH_LONG).show();
                Intent mainIntent = new Intent(LoginActivity.this, RegistrationActivity.class);
                mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(mainIntent);
                finish();
            } else {
                progressDialog.dismiss();
                Toast.makeText(LoginActivity.this, "Login failed.", Toast.LENGTH_LONG).show();
            }
        })
                .addOnFailureListener(e -> {
                    progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Error occurred.", Toast.LENGTH_SHORT).show();
                });
    }
}