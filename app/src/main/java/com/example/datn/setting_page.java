package com.example.datn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class setting_page extends AppCompatActivity {
    private TextView name,email;
    private EditText old_pass, new_pass, re_new_pass;
    ImageView btn_OK;
    String getEmail1, getOldpass, getUsername;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_page);
        name = findViewById(R.id.username_setting);
        email = findViewById(R.id.email_setting);
        old_pass = findViewById(R.id.et_old_password);
        new_pass = findViewById(R.id.et_new_password);
        re_new_pass = findViewById(R.id.et_re_new_password);
        btn_OK = findViewById(R.id.btn_OK);

        String et_old_pass,et_new_pass,et_re_new_pass;

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference userRef = database.getReference("users");
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid(); // Lấy user ID hiện tại
        DatabaseReference userIdRef = userRef.child(userId);
        DatabaseReference nameRef = userIdRef.child("name");
        nameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getUsername = snapshot.getValue(String.class);
                name.setText(getUsername);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference emailref = userIdRef.child("email");
        emailref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getEmail1 = snapshot.getValue(String.class);
                email.setText(getEmail1);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        DatabaseReference passref = userIdRef.child("password");
        passref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                getOldpass = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        et_old_pass = old_pass.getText().toString();
        et_new_pass = new_pass.getText().toString();
        et_re_new_pass = re_new_pass.getText().toString();
        btn_OK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et_old_pass == "" || et_new_pass == "" || et_re_new_pass == ""){
                    Toast.makeText(setting_page.this,"Vui lòng điền đầy đủ thông tin",Toast.LENGTH_LONG).show();
                }else if(et_old_pass != getOldpass){
                    Toast.makeText(setting_page.this,"Pass cũ không khớp",Toast.LENGTH_LONG).show();
                }else if(et_new_pass != et_re_new_pass){
                    Toast.makeText(setting_page.this,"Pass mới không khớp",Toast.LENGTH_LONG).show();
                }else{
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                    AuthCredential credential = EmailAuthProvider.getCredential(email.getText().toString(),new_pass.getText().toString());
                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful())
                            {
                                passref.setValue(new_pass);
                                Toast.makeText(getApplicationContext(), "Thay đổi mật khẩu thành công", Toast.LENGTH_LONG).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(), "Có lỗi xảy ra, vui lòng thử lại sau", Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }
            }
        });
    }
}