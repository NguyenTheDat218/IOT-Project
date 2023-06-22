package com.example.datn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class login_page extends AppCompatActivity {

    private EditText pass, email;
    private Button btn_login, btn_to_register;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_page);
        pass = findViewById(R.id.password_login);
        email = findViewById(R.id.username_login);
        btn_login = findViewById(R.id.btn_login);
        btn_to_register = findViewById(R.id.btn_to_register);
        mAuth = FirebaseAuth.getInstance();

        btn_to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), register.class);
                startActivity(intent);
                finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1, password;
                email1 = email.getText().toString();
                password = pass.getText().toString();
                if (TextUtils.isEmpty(email1)) {
                    Toast.makeText(login_page.this, "Enter email", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(login_page.this, "Enter email", Toast.LENGTH_LONG).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email1, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference userRef = database.getReference("users");
                                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Lấy user ID hiện tại
                                    DatabaseReference userIdRef = userRef.child(userId);
                                    DatabaseReference nameRef = userIdRef.child("name");
                                    nameRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                                            if (snapshot.exists()) {
                                                String name = snapshot.getValue(String.class);
                                                Toast.makeText(login_page.this, "Login success.", Toast.LENGTH_SHORT).show();
                                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                intent.putExtra("name", name);
                                                // Lưu thông tin đăng nhập
                                                SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
                                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                                editor.putString("email", email1);
                                                editor.putString("password", password);
                                                editor.apply();
                                                startActivity(intent);
                                                finish();
                                            }
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {
                                            Toast.makeText(login_page.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                        }
                                    });


                                } else {
                                    Toast.makeText(login_page.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
    }
}