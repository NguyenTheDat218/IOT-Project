package com.example.datn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

import model.user;

public class register extends AppCompatActivity {

    private EditText username,email,pass,re_pass;
    Button btn_register, btn_cancel;
    private TextView textlogin;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        email = findViewById(R.id.email_register);
        mAuth = FirebaseAuth.getInstance();
        username = findViewById(R.id.username_register);
        pass = findViewById(R.id.password_register);
        re_pass = findViewById(R.id.re_password_register);
        btn_register = findViewById(R.id.btn_register);
        btn_cancel = findViewById(R.id.btn_cancel);
        textlogin = findViewById(R.id.textlogin);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1, password, name;
                email1 = email.getText().toString();
                password = pass.getText().toString();
                name = username.getText().toString();
                if(TextUtils.isEmpty(email1)){
                    Toast.makeText(register.this, "Enter email", Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    Toast.makeText(register.this, "Enter email", Toast.LENGTH_LONG).show();
                    return;
                }

                mAuth.createUserWithEmailAndPassword(email1, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseUser user = mAuth.getCurrentUser();
                                    String userId = user.getUid();
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference usersRef = database.getReference("users");
                                    model.user User = new user(name, email1, password);
                                    usersRef.child(userId).setValue(User);
                                    Toast.makeText(register.this, "Account created.",Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(),login_page.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(register.this, "Authentication failed.",Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }
        });

        textlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),login_page.class);
                startActivity(intent);
                finish();
            }
        });

    }
}