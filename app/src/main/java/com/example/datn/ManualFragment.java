//package com.example.datn;
//
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.EdgeEffect;
//import android.widget.EditText;
//import android.widget.TextView;
//
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
///**
// * A simple {@link Fragment} subclass.
// * Use the {@link ManualFragment#newInstance} factory method to
// * create an instance of this fragment.
// */
//public class ManualFragment extends Fragment {
//
//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;
//    TextView tb1,tb3;
//    EditText nhietdo,doam;
//    Button set;
//    int key=1;
//    public ManualFragment() {
//        // Required empty public constructor
//    }
//
//    /**
//     * Use this factory method to create a new instance of
//     * this fragment using the provided parameters.
//     *
//     * @param param1 Parameter 1.
//     * @param param2 Parameter 2.
//     * @return A new instance of fragment ManualFragment.
//     */
//    // TODO: Rename and change types and number of parameters
//    public static ManualFragment newInstance(String param1, String param2) {
//        ManualFragment fragment = new ManualFragment();
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
//        View view = inflater.inflate(R.layout.fragment_manual, container, false);
//
//        tb1 = view.findViewById(R.id.tv_tb1_status);
//        tb3 = view.findViewById(R.id.tv_tb3_status3);
//        nhietdo = view.findViewById(R.id.edit_NguongCamBien2);
//        doam = view.findViewById(R.id.edit_NguongCamBien3);
//        set = view.findViewById(R.id.btn_set_sensor_threshold2);
//
//
//        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
//        DatabaseReference databaseReference = firebaseDatabase.getReference("SetNguongCamBien");
//        DatabaseReference databaseReference1 = firebaseDatabase.getReference("CheDoHoatDong");
//
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String a = snapshot.child("SetDoAmDat").getValue().toString();
//                //String b = snapshot.child("SetNhietDo").getValue().toString();
//                //nhietdo.setText(b);
//                doam.setText(a);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        databaseReference1.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                String k1,k2;
//                k1 = snapshot.child("BongDen").getValue().toString();
//                k2 = snapshot.child("MayBom").getValue().toString();
//                tb1.setText(k1);
//                tb3.setText(k2);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        set.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(key==1){
//                    doam.setEnabled(true);
//                    nhietdo.setEnabled(true);
//                    set.setText("OK");
//                    databaseReference.child("XacNhanNguongCamBien").setValue(3);
//                    key=key+1;
//                }else if(key==2){
//                    doam.setEnabled(false);
//                    nhietdo.setEnabled(false);
//                    databaseReference.child("SetDoAmDat").setValue(Integer.parseInt(doam.getText().toString()));
//                    databaseReference.child("SetNhietDo").setValue(Integer.parseInt(nhietdo.getText().toString()));
//                    databaseReference.child("XacNhanNguongCamBien").setValue(2);
//                    set.setText("SET");
//                    key=key-1;
//                }
//            }
//        });
//        return view;
//    }
//}