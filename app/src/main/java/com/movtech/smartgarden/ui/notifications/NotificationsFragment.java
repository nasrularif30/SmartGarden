package com.movtech.smartgarden.ui.notifications;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.movtech.smartgarden.LoginRole;
import com.movtech.smartgarden.R;

import java.util.Calendar;

public class NotificationsFragment extends Fragment {
    ImageView setting;
    SharedPreferences sharedPreferences;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private NotificationsViewModel notificationsViewModel;
    TextView tvWaktuMenit, tvWaktuJam;
    EditText tvDurasiDetik, tvDurasiMenit, tvDurasiJam, tvBA, tvBB;
    Button btnSimpan;
    TimePickerDialog timePickerDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);
        sharedPreferences = this.getActivity().getSharedPreferences("data_user", Context.MODE_PRIVATE);
        setting = root.findViewById(R.id.setting);
        tvBA = root.findViewById(R.id.tv_ba);
        tvBB = root.findViewById(R.id.tv_bb);
        tvWaktuJam = root.findViewById(R.id.tv_waktujam);
        tvWaktuMenit = root.findViewById(R.id.tv_waktumenit);
        tvDurasiDetik = root.findViewById(R.id.tv_durasidetik);
        tvDurasiMenit = root.findViewById(R.id.tv_durasimenit);
        tvDurasiJam = root.findViewById(R.id.tv_durasijam);
        btnSimpan = root.findViewById(R.id.btn_simpan);

        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        myRef.child("konfigurasi").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String dtWaktuJam = snapshot.child("waktu_jam").getValue(String.class);
                String dtWaktuMenit = snapshot.child("waktu_menit").getValue(String.class);
                String dtDurasiDetik = snapshot.child("durasi_detik").getValue(String.class);
                String dtDurasiMenit = snapshot.child("durasi_menit").getValue(String.class);
                String dtDurasiJam = snapshot.child("durasi_jam").getValue(String.class);
                Integer dtBA = snapshot.child("batas_basah").getValue(Integer.class);
                Integer dtBB = snapshot.child("batas_kering").getValue(Integer.class);

                if (dtBA==null){
                    Toast.makeText(getActivity().getApplicationContext(), "Gagal Mengambil Data!", Toast.LENGTH_LONG).show();
                }
                else {
                    tvWaktuJam.setText(dtWaktuJam);
                    tvWaktuMenit.setText(dtWaktuMenit);
                    tvDurasiDetik.setText(dtDurasiDetik);
                    tvDurasiMenit.setText(dtDurasiMenit);
                    tvDurasiJam.setText(dtDurasiJam);
                    tvBB.setText(String.valueOf(dtBB));
                    tvBA.setText(String.valueOf(dtBA));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Gagal Mengambil Data!\nPeriksa koneksi internet!", Toast.LENGTH_LONG).show();
            }
        });
        tvWaktuJam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(getActivity(), R.style.DialogTheme, (view, hourOfDay, minute1) -> {
                    tvWaktuJam.setText(String.valueOf(hourOfDay));
                    tvWaktuMenit.setText(String.valueOf(minute1));
                },hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
                timePickerDialog.show();
            }
        });
        tvWaktuMenit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePickerDialog = new TimePickerDialog(getActivity(), R.style.DialogTheme, (view, hourOfDay, minute12) -> {
                    tvWaktuJam.setText(String.valueOf(hourOfDay));
                    tvWaktuMenit.setText(String.valueOf(minute12));
                },hour, minute, android.text.format.DateFormat.is24HourFormat(getActivity()));
                timePickerDialog.show();
            }
        });

        btnSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String detik = tvDurasiDetik.getText().toString().trim();
                String menit = tvDurasiMenit.getText().toString().trim();
                String jam = tvDurasiJam.getText().toString().trim();
                String batasKering = tvBB.getText().toString().trim();
                String batasBasah = tvBA.getText().toString().trim();
                String waktuJam = tvWaktuJam.getText().toString().trim();
                String waktuMenit = tvWaktuMenit.getText().toString().trim();
                if (Integer.parseInt(batasBasah) > 100 || Integer.parseInt(batasBasah)<0 || Integer.parseInt(batasKering)>100 || Integer.parseInt(batasKering)<0){
                    Toast.makeText(getActivity(), "Silakan masukkan data dengan benar", Toast.LENGTH_LONG).show();
                }
                if (detik.isEmpty()||menit.isEmpty()||jam.isEmpty()||batasBasah.isEmpty()||batasKering.isEmpty()||waktuJam.isEmpty()||waktuMenit.isEmpty()){
                    Toast.makeText(getActivity(), "Silakan masukkan data dengan lengkap", Toast.LENGTH_LONG).show();
                }
                else {
                    myRef.child("konfigurasi").child("waktu_jam").setValue(tvWaktuJam.getText().toString().trim());
                    myRef.child("konfigurasi").child("waktu_menit").setValue(tvWaktuMenit.getText().toString().trim());
                    myRef.child("konfigurasi").child("durasi_detik").setValue(detik);
                    myRef.child("konfigurasi").child("durasi_menit").setValue(menit);
                    myRef.child("konfigurasi").child("durasi_jam").setValue(jam);
                    myRef.child("konfigurasi").child("batas_basah").setValue(Integer.parseInt(batasBasah));
                    myRef.child("konfigurasi").child("batas_kering").setValue(Integer.parseInt(batasKering));
                    Toast.makeText(getActivity().getApplicationContext(), "Data Berhasil Disimpan!", Toast.LENGTH_LONG).show();
                }
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
        return root;
    }
    public void logout(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", "null");
        editor.putString("user", "null");
        editor.putBoolean("sudahLogin", false);
        editor.apply();
        this.getActivity().finish();
        startActivity(new Intent(this.getActivity(), LoginRole.class));
    }

}