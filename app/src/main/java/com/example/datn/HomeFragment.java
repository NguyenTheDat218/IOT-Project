package com.example.datn;

import static java.lang.String.format;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

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
    private TextView temp,huniland,huniair,light,tvDay,tvTime;
    private Object AnhSang, DoAmKK, DoAmDat,NhietDo;
    TextView username;
    ImageView exit, avatar;

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
        temp = view.findViewById(R.id.tv_NhietDo);
        avatar = view.findViewById(R.id.imgAvata);
        tvTime = view.findViewById(R.id.tv_Time);
        tvDay = view.findViewById(R.id.tv_Date);
        huniair = view.findViewById(R.id.tv_DoAmKK);
        huniland = view.findViewById(R.id.tv_DoAmDat);
        light = view.findViewById(R.id.tv_AnhSang);
        exit = view.findViewById(R.id.imgexit);
        username = view.findViewById(R.id.username_home);
        Bundle bundle = getArguments();
        if (bundle != null) {
            String name = bundle.getString("name");
            username.setText(name);
        }
        loadDateTime();

        temp.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        temp.setSelected(true);
        huniair.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        huniair.setSelected(true);
        huniland.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        huniland.setSelected(true);
        light.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        light.setSelected(true);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("CamBien");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                AnhSang = snapshot.child("AnhSang").getValue();
                DoAmDat = snapshot.child("DoAmDat").getValue();
                DoAmKK = snapshot.child("DoAm").getValue();
                NhietDo = snapshot.child("NhietDo").getValue();
                light.setText(AnhSang.toString() + "");
                temp.setText(NhietDo.toString() + "°C");
                huniair.setText(DoAmKK.toString() + "%");
                huniland.setText(DoAmDat.toString() + "%");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
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
        String day = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
        String time = new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date());
        tvDay.setText("Ngày : " + day);
        tvTime.setText("Giờ : " + time);
    }
}