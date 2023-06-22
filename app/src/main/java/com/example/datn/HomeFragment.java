package com.example.datn;

import static android.content.Context.MODE_PRIVATE;
import static java.lang.String.format;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextPaint;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private  ImageView weather;
    private TextView huniland1,huniland,tvDay,tvTime;
    private Object DoAmDat,DoAmDat1,status_tb2;
    TextView username;
    ImageView exit, avatar,notify_pump;
    String check;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        weather = view.findViewById(R.id.img_change_weather);
        avatar = view.findViewById(R.id.imgAvata);
        tvTime = view.findViewById(R.id.tv_Time);
        tvDay = view.findViewById(R.id.tv_Date);
        huniland = view.findViewById(R.id.tv_DoAmDat);
        huniland1 = view.findViewById(R.id.tv_DoAmDat1);
        exit = view.findViewById(R.id.imgexit);
        username = view.findViewById(R.id.username_home);
        notify_pump = view.findViewById(R.id.img_notify_pump);
        loadDateTime();

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String currentUserId = mAuth.getCurrentUser().getUid();
        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference().child("users").child(currentUserId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String name = dataSnapshot.child("name").getValue(String.class);
                    username.setText(name);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Xử lý lỗi nếu có
            }
        });

        huniland.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        huniland.setSelected(true);
        huniland1.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        huniland1.setSelected(true);


        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("CamBien");
        DatabaseReference databaseReference1 = firebaseDatabase.getReference("CheDoHoatDong");
        databaseReference1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                status_tb2 = snapshot.child("MayBom").getValue().toString();
                if(status_tb2=="true"){
                    notify_pump.setBackgroundResource(R.drawable.notify_pump);
                }else if(status_tb2=="false"){
                    notify_pump.setBackgroundResource(R.drawable.notify_pump_off);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DoAmDat = snapshot.child("DoAmDat").getValue();
                DoAmDat1 = snapshot.child("DoAmDat_1").getValue();
                check = snapshot.child("ThoiTiet").getValue().toString();

                huniland.setText(DoAmDat.toString() + "%");
                huniland1.setText(DoAmDat1.toString() + "%");
                if (check == "true"){
                    weather.setBackgroundResource(R.drawable.icon_rain);
                }else if(check =="false"){
                    weather.setBackgroundResource(R.drawable.sunsight1_png);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("MyPrefs", MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putStringSet("password", null);
                editor.putStringSet("email", null);
                editor.apply();
                Intent intent = new Intent(getActivity(), login_page.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), setting_page.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
}

    private void loadDateTime() {
        String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String currentDate = dateFormat.format(calendar.getTime());
        tvDay.setText("Ngày : " + currentDate);
        tvTime.setText("Giờ : " + time);
    }
}