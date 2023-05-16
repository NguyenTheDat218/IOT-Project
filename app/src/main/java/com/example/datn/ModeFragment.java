package com.example.datn;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private SwitchCompat tb1,tb2,mode;
    private ImageView light,pump;
    private Object status_tb1,status_tb2,status_mode;
    Button set;
    EditText doam;
    int key=1;
    public ModeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ModeFragment newInstance(String param1, String param2) {
        ModeFragment fragment = new ModeFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mode, container, false);
        tb1 = view.findViewById(R.id.swOnOff1);
        tb2 = view.findViewById(R.id.swOnOff2);
        mode = view.findViewById(R.id.CheDo);
        light = view.findViewById(R.id.img_light);
        pump = view.findViewById(R.id.img_pump);
        set = view.findViewById(R.id.btn_set_sensor_threshold2);
        doam = view.findViewById(R.id.edit_NguongCamBien3);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("CheDoHoatDong");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                status_tb1 = snapshot.child("BongDen").getValue().toString();
                status_tb2 = snapshot.child("MayBom").getValue().toString();
                if(status_tb1 == "true"){
                    light.setBackgroundResource(R.drawable.lighton);
                    tb1.setChecked(true);
                }else if (status_tb1 == "false"){
                    light.setBackgroundResource(R.drawable.lightoff);
                    tb1.setChecked(false);
                }

                if(status_tb2 == "true"){
                    pump.setBackgroundResource(R.drawable.water_pump_on);
                    tb2.setChecked(true);
                }else if (status_tb2 == "false"){
                    pump.setBackgroundResource(R.drawable.water_pump_off);
                    tb2.setChecked(false);
                }
                status_mode = snapshot.child("CheDo").getValue().toString();
                if(status_mode == "false"){
                    tb1.setVisibility(View.GONE);
                    tb2.setVisibility(View.GONE);
                    mode.setChecked(false);
                }else if (status_mode == "true"){
                    tb1.setVisibility(View.VISIBLE);
                    tb2.setVisibility(View.VISIBLE);
                    mode.setChecked(true);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        mode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isChecked = mode.isChecked();
                if(!isChecked){
                    tb1.setVisibility(View.GONE);
                    tb2.setVisibility(View.GONE);
                    databaseReference.child("CheDo").setValue(false);
                } else {
                    tb1.setVisibility(View.VISIBLE);
                    tb2.setVisibility(View.VISIBLE);
                    databaseReference.child("CheDo").setValue(true);
                }
            }
        });
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                status_tb1 = snapshot.child("BongDen").getValue().toString();
                status_tb2 = snapshot.child("MayBom").getValue().toString();
                tb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            databaseReference.child("BongDen").setValue(true);
                            light.setBackgroundResource(R.drawable.lighton);
                        } else {
                            databaseReference.child("BongDen").setValue(false);
                            light.setBackgroundResource(R.drawable.lightoff);
                        }
                    }
                });
                tb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked) {
                            databaseReference.child("MayBom").setValue(true);
                            pump.setBackgroundResource(R.drawable.water_pump_on);
                        } else {
                            databaseReference.child("MayBom").setValue(false);
                            pump.setBackgroundResource(R.drawable.water_pump_off);
                        }
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String a = snapshot.child("SetDoAmDat").getValue().toString();
                doam.setText(a);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(key==1){
                    doam.setEnabled(true);
                    set.setText("OK");
                    databaseReference.child("XacNhanNguongCamBien").setValue(3);
                    key=key+1;
                }else if(key==2){
                    doam.setEnabled(false);
                    databaseReference.child("SetDoAmDat").setValue(Integer.parseInt(doam.getText().toString()));
                    databaseReference.child("XacNhanNguongCamBien").setValue(2);
                    set.setText("SET");
                    key=key-1;
                }
            }
        });
        return view;
    }
}