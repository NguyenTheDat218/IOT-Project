package com.example.datn;

import static com.google.android.exoplayer2.ExoPlayerLibraryInfo.TAG;
import static java.lang.Float.parseFloat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

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
    private EditText settime1, settime2, settime3, settime4;
    private boolean isChecked = false;
    Switch Switch_settime1, Switch_settime2;
    private int key_xac_nhan;
    private boolean key_xac_nhan_lap_lai;
    private boolean isEditing = false;

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
        settime2 = view.findViewById(R.id.edit_time2);
        settime3 = view.findViewById(R.id.edit_time3);
        settime4 = view.findViewById(R.id.edit_timerepeat);
        AlertDialog.Builder builder;
        AlertDialog dialog;
        //status_Weather = view.findViewById(R.id.img_change_weather);
        Switch_settime1 = view.findViewById(R.id.check_time1);
        Switch_settime2 = view.findViewById(R.id.check_time2);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference("CamBien");
        DatabaseReference child = FirebaseDatabase.getInstance().getReference().child("HenGio").child("ThietBi_1");
        DatabaseReference databaseReference2 = firebaseDatabase.getReference("HenGio");
        DatabaseReference childata2 = databaseReference2.child("ThietBi_1");
        key_xac_nhan_lap_lai = true;
        Switch_settime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Switch_settime2.isChecked() == true) {
                    settime3.setEnabled(false);
                    settime3.setTextColor(getResources().getColor(R.color.text_lock));
                    key_xac_nhan_lap_lai = true;
                    child.child("XacNhanLapLai").setValue(key_xac_nhan_lap_lai);
                } else if (Switch_settime2.isChecked() == false) {
                    settime3.setEnabled(true);
                    settime3.setTextColor(getResources().getColor(R.color.black));
                    key_xac_nhan_lap_lai = false;
                    child.child("XacNhanLapLai").setValue(key_xac_nhan_lap_lai);
                }
            }
        });

        childata2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String a = snapshot.child("KhoangTG").child("KhoangTG_Phut").getValue().toString();
                String b = snapshot.child("KhoangTG").child("KhoangTG_Giay").getValue().toString();
                String c = snapshot.child("KhoangTG_LapLai").getValue().toString();
                settime1.setText(snapshot.child("Gio").getValue().toString() + ":" + snapshot.child("Phut").getValue().toString());
                settime2.setText(a + ":" + b);
                settime3.setText(snapshot.child("LapLai").getValue().toString());
                settime4.setText(c);
                String d = snapshot.child("XacNhan").getValue().toString();
                String e = snapshot.child("XacNhanLapLai").getValue().toString();
                if (d.equals("3")) {
                    Switch_settime1.setChecked(true);
                    settime1.setEnabled(false);
                    settime2.setEnabled(false);
                    settime4.setEnabled(false);
                } else if (d.equals("2")) {
                    Switch_settime1.setChecked(false);

                    settime1.setEnabled(true);
                    settime2.setEnabled(true);
                    settime4.setEnabled(true);
                }
                if (e.equals("true")) {
                    Switch_settime2.setChecked(true);

                    settime3.setEnabled(false);
                } else if (e.equals("false")) {
                    Switch_settime2.setChecked(false);
                    settime3.setEnabled(true);

                }
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
        settime2.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    final Dialog dialog = new Dialog(requireContext());
                    dialog.setContentView(R.layout.second_picker_dialog);

                    final NumberPicker minutePicker = dialog.findViewById(R.id.minutePicker);
                    minutePicker.setMinValue(0);
                    minutePicker.setMaxValue(59);

                    final NumberPicker secondPicker = dialog.findViewById(R.id.secondPicker);
                    secondPicker.setMinValue(0);
                    secondPicker.setMaxValue(59);

                    Button okButton = dialog.findViewById(R.id.okButton);
                    okButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            int selectedMinute = minutePicker.getValue();
                            int selectedSecond = secondPicker.getValue();

                            String formattedTime = String.format("%d:%d", selectedMinute, selectedSecond);
                            settime2.setText(formattedTime);

                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
                return true;
            }
        });


        settime4.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
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
                            settime4.setText(String.valueOf(selectedValue));
                            dialog.dismiss();
                        }
                    });

                    dialog.show();
                }
                return true;
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
        child.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                key_xac_nhan = Integer.parseInt(snapshot.child("XacNhan").getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Switch_settime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Switch_settime1.isChecked() == true) {
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
                } else if (Switch_settime1.isChecked() == false) {
                    Switch_settime2.setChecked(false);
                    child.child("XacNhanLapLai").setValue(false);
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
        settime3.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    final String[] hours = new String[24];
                    for (int i = 0; i < 24; i++) {
                        hours[i] = String.valueOf(i);
                    }

                    boolean[] checkedHours = new boolean[24];

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle("Chọn giờ")
                            .setMultiChoiceItems(hours, checkedHours, new DialogInterface.OnMultiChoiceClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                                    checkedHours[which] = isChecked;
                                }
                            })
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    StringBuilder selectedHours = new StringBuilder();
                                    for (int i = 0; i < 24; i++) {
                                        if (checkedHours[i]) {
                                            selectedHours.append(hours[i]).append(",");
                                        }
                                    }
                                    if (selectedHours.length() > 0) {
                                        selectedHours.deleteCharAt(selectedHours.length() - 1); // Xóa dấu phẩy cuối cùng
                                    }
                                    settime3.setText(selectedHours.toString());
                                    String LapLai = settime3.getText().toString();
                                    child.child("LapLai").setValue(LapLai);
                                }
                            })
                            .setNegativeButton("Hủy", null)
                            .show();
                }
                return true;
            }
        });

        return view;
    }
}
