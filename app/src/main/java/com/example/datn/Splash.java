package com.example.datn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Splash extends AppCompatActivity {
    private static final int SPLASH_SCREEN_TIMEOUT = 3000;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mAuth = FirebaseAuth.getInstance();
        // Kiểm tra thông tin đăng nhập đã lưu trữ
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("email", "");
        String savedPassword = sharedPreferences.getString("password", "");
        if (!TextUtils.isEmpty(savedEmail) && !TextUtils.isEmpty(savedPassword)) {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mAuth.signInWithEmailAndPassword(savedEmail, savedPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
                                        mAuth.signInWithEmailAndPassword(savedEmail, savedPassword)
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
                                                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                                                        intent.putExtra("name", name);
                                                                        startActivity(intent);
                                                                        finish();
                                                                    }
                                                                }

                                                                @Override
                                                                public void onCancelled(@NonNull DatabaseError error) {

                                                                }
                                                            });
                                                        } else {

                                                        }
                                                    }
                                                });
                                    } else {
                                        // Xảy ra lỗi đăng nhập
                                    }
                                }
                            });
                }
            }, SPLASH_SCREEN_TIMEOUT);

        } else {
            // Chuyển đến login sau khi hiển thị splash screen
            Intent homeIntent = new Intent(Splash.this, login_page.class);
            startActivity(homeIntent);
            finish();
        }


    }
}