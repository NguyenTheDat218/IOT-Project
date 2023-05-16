package com.example.datn;

import static java.lang.Float.parseFloat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.ViewSwitcher;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SetTimeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SetTimeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private EditText settime1,settime2,settime3,settime4;
    private boolean isChecked = false;
    Switch Switch_settime1, Switch_settime2;
    private int key_xac_nhan;
    private boolean key_xac_nhan_lap_lai;
    private NumberPicker numberPicker ;
    public SetTimeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment SetTimeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SetTimeFragment newInstance(String param1, String param2) {
        SetTimeFragment fragment = new SetTimeFragment();
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
        View view = inflater.inflate(R.layout.fragment_set_time, container, false);
        //realtime_temp = view.findViewById(R.id.text_real_temp);
        //realtime_humi = view.findViewById(R.id.text_real_humi);
        settime1 = view.findViewById(R.id.edit_time1);
        settime2 =view.findViewById(R.id.edit_time2);
        settime3=view.findViewById(R.id.edit_time3);
        settime4=view.findViewById(R.id.edit_timerepeat);
        numberPicker = view.findViewById(R.id.numberPicker);
        AlertDialog.Builder builder;
        AlertDialog dialog;
        numberPicker.setWrapSelectorWheel(false);
        //status_Weather = view.findViewById(R.id.img_change_weather);
        Switch_settime1 = view.findViewById(R.id.check_time1);
        Switch_settime2 = view.findViewById(R.id.check_time2);
        Switch_settime1.setChecked(true);
        Switch_settime2.setChecked(true);
        settime1.setEnabled(false);
        settime2.setEnabled(false);
        settime3.setEnabled(false);
        settime4.setEnabled(false);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("CamBien");
        DatabaseReference databaseReference2 = firebaseDatabase.getReference("HenGio");
        DatabaseReference childata2 = databaseReference2.child("ThietBi_1");


        childata2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String a = snapshot.child("KhoangTG").child("KhoangTG_Phut").getValue().toString();
                String c = snapshot.child("KhoangTG_LapLai").getValue().toString();
                settime1.setText(snapshot.child("Gio").getValue().toString() + ":" + snapshot.child("Phut").getValue().toString());
                settime2.setText( a+"phút");
                settime3.setText(snapshot.child("LapLai").getValue().toString());
                settime4.setText(c);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        settime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar c = Calendar.getInstance();
                int hour = c.get(Calendar.HOUR_OF_DAY);
                int minute = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(SetTimeFragment.this.getContext(),
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int selectedHour, int selectedMinute) {
                                settime1.setText(selectedHour + ":" + selectedMinute);
                            }
                        }, hour, minute, true);

                timePickerDialog.show();
            }
        });
        settime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.number_picker_dialog);
                final NumberPicker numberPicker = dialog.findViewById(R.id.numberPicker);
                numberPicker.setMinValue(1);
                numberPicker.setMaxValue(60);

                Button okButton = dialog.findViewById(R.id.okButton);
                okButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int selectedValue = numberPicker.getValue();
                        settime2.setText(String.valueOf(selectedValue));
                        dialog.dismiss();
                    }
                });

                dialog.show();
            }
        });



        settime1.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    view.performClick();
                }
            }
        });
        DatabaseReference child = FirebaseDatabase.getInstance().getReference().child("HenGio").child("ThietBi_1");
        child.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                key_xac_nhan = Integer.parseInt(snapshot.child("XacNhan").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        })
        ;
        Switch_settime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Switch_settime1.isChecked()==true){
                    settime1.setEnabled(false);
                    settime2.setEnabled(false);
                    settime1.setTextColor(getResources().getColor(R.color.text_lock));
                    settime2.setTextColor(getResources().getColor(R.color.text_lock));
                    settime4.setTextColor(getResources().getColor(R.color.text_lock));
                    settime4.setEnabled(false);
                    String[] parts = settime1.getText().toString().split(":");
                    String hour = parts[0];
                    String minute = parts[1];
                    String[] parts1 = settime2.getText().toString().split(":");
                    String minutes = parts1[0];
                    String seconds = parts1[1];
                    String parts2 = settime4.getText().toString();
                    child.child("Gio").setValue(Float.parseFloat(hour));
                    child.child("Phut").setValue(Float.parseFloat(minute));
                    child.child("KhoangTG").child("KhoangTG_Phut").setValue(Float.parseFloat(minutes));
                    child.child("KhoangTG").child("KhoangTG_Giay").setValue(Float.parseFloat(seconds));
                    child.child("KhoangTG_LapLai").setValue(Float.parseFloat(parts2));
                    child.child("XacNhan").setValue(3);
                }else if(Switch_settime1.isChecked()==false){
                    settime1.setEnabled(true);
                    settime1.setTextColor(getResources().getColor(R.color.black));
                    settime2.setTextColor(getResources().getColor(R.color.black));
                    settime4.setTextColor(getResources().getColor(R.color.black));
                    settime2.setEnabled(true);
                    settime4.setEnabled(true);
                    child.child("XacNhan").setValue(2);
                }
            }
        });
        key_xac_nhan_lap_lai = true;
        Switch_settime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Switch_settime2.isChecked()==true){
                    settime3.setEnabled(false);
                    settime3.setTextColor(getResources().getColor(R.color.text_lock));
                    String LapLai = settime3.getText().toString();
                    child.child("LapLai").setValue(LapLai);
                    key_xac_nhan_lap_lai = true;
                    child.child("XacNhanLapLai").setValue(key_xac_nhan_lap_lai);
                }else if(Switch_settime2.isChecked()==false){
                    settime3.setEnabled(true);
                    settime3.setTextColor(getResources().getColor(R.color.black));
                    key_xac_nhan_lap_lai = false;
                    child.child("XacNhanLapLai").setValue(key_xac_nhan_lap_lai);
                }
            }
        });
        /*databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                realtime_humi.setText(snapshot.child("DoAm").getValue().toString() + "%");
                realtime_temp.setText(snapshot.child("NhietDo").getValue().toString() + "°C");
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        return view;
    }
    public void showNumberPicker(View view) {
        ViewSwitcher switcher = view.findViewById(R.id.switcher);
        switcher.showNext();
        EditText settime2 = view.findViewById(R.id.edit_time2);
        NumberPicker numberPicker = view.findViewById(R.id.number_picker);
        numberPicker.setValue(Integer.parseInt(settime2.getText().toString()));
    }
    public void updateEditText(View view) {
        ViewSwitcher switcher = view.findViewById(R.id.switcher);
        switcher.showPrevious();

        EditText settime2 = view.findViewById(R.id.edit_time2);
        NumberPicker numberPicker = view.findViewById(R.id.number_picker);
        settime2.setText(String.valueOf(numberPicker.getValue()));
    }

}