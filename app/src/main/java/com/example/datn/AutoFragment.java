//package com.example.datn;
//
//import android.os.Bundle;
//
//import androidx.annotation.DrawableRes;
//import androidx.annotation.NonNull;
//import androidx.appcompat.widget.SwitchCompat;
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.CompoundButton;
//import android.widget.ImageView;
//import android.widget.Switch;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link AutoFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//
//public class AutoFragment extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//    private SwitchCompat tb1,tb2;
//    private ImageView light,pump;
//    private String status_tb1,status_tb2,status_mode;
//    public AutoFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment AutoFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static AutoFragment newInstance(String param1, String param2) {
//        AutoFragment fragment = new AutoFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//
//        //View view = inflater.inflate(R.layout.fragment_auto, container, false);
//        tb1 = view.findViewById(R.id.swOnOff1);
//        tb2 = view.findViewById(R.id.swOnOff2);
//        light = view.findViewById(R.id.img_light);
//        pump = view.findViewById(R.id.img_pump);
//
//
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = firebaseDatabase.getReference("CheDoHoatDong");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                status_tb1 = snapshot.child("BongDen").getValue().toString();
//                status_tb2 = snapshot.child("MayBom").getValue().toString();
//                tb1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked) {
//                            databaseReference.child("BongDen").setValue(true);
//                            light.setBackground(getContext().getResources().getDrawable(R.drawable.lighton));
//                        } else {
//                            databaseReference.child("BongDen").setValue(false);
//                            light.setBackground(getContext().getResources().getDrawable(R.drawable.lightoff));
//                        }
//                    }
//                });
//                tb2.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked) {
//                            databaseReference.child("MayBom").setValue(true);
//                            pump.setBackground(getContext().getResources().getDrawable(R.drawable.water_pump_on));
//                        } else {
//                            databaseReference.child("MayBom").setValue(false);
//                            pump.setBackground(getContext().getResources().getDrawable(R.drawable.water_pump_off));
//                        }
//                    }
//                });
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        /*check.setChecked(true);
//        tb1.setEnabled(false);
//        tb2.setEnabled(false);
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = firebaseDatabase.getReference("CheDoHoatDong");
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                status_tb1 = snapshot.child("BongDen").getValue().toString();
//                status_tb2 = snapshot.child("MayBom").getValue().toString();
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//        check.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(check.isChecked()==true){
//                    tb1.setEnabled(false);
//                    tb2.setEnabled(false);
//                }else if(check.isChecked()==false){
//                    tb1.setEnabled(true);
//                    tb2.setEnabled(true);
//                }
//            }
//        });
//        tb1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(status_tb1 == "false"){
//                    databaseReference.child("BongDen").setValue(true);
//                }else if (status_tb1 == "true"){
//                    databaseReference.child("BongDen").setValue(false);
//                }
//            }
//        });
//        tb2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(status_tb2 == "false"){
//                    databaseReference.child("MayBom").setValue(true);
//                }else if (status_tb2 == "true"){
//                    databaseReference.child("MayBom").setValue(false);
//                }
//            }
//        });*/
//        return view;
//    }
//}