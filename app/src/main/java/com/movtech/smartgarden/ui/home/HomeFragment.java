package com.movtech.smartgarden.ui.home;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
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
import com.movtech.smartgarden.BerandaUser;
import com.movtech.smartgarden.LoginRole;
import com.movtech.smartgarden.R;

public class HomeFragment extends Fragment {
    ImageView setting;
    SharedPreferences sharedPreferences;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();
    String role;
    TextView tvCuaca;
    TextView tvSuhu, tvHum;
    TextView tvMoist1, tvMoist2, tvMoist3;
    TextView tvMoist4, tvMoist5, tvMoist6;
    TextView tvMoist7, tvMoist8, tvMoistAvg;
    TextView tvVolume;
    Float dataMoist1, dataMoist2, dataMoist3, dataMoist4, dataMoist5, dataMoist6, dataMoist7, dataMoist8, moistAvg;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        sharedPreferences = this.getActivity().getSharedPreferences("data_user", Context.MODE_PRIVATE);
        setting = root.findViewById(R.id.setting);
        tvCuaca = root.findViewById(R.id.tv_hujan);
        tvSuhu = root.findViewById(R.id.tv_suhu);
        tvHum = root.findViewById(R.id.tv_hum);
        tvMoistAvg = root.findViewById(R.id.tv_moist_avg);
        tvMoist1 = root.findViewById(R.id.tv_moist_1);
        tvMoist2 = root.findViewById(R.id.tv_moist_2);
        tvMoist3 = root.findViewById(R.id.tv_moist_3);
        tvMoist4 = root.findViewById(R.id.tv_moist_4);
        tvMoist5 = root.findViewById(R.id.tv_moist_5);
        tvMoist6 = root.findViewById(R.id.tv_moist_6);
        tvMoist7 = root.findViewById(R.id.tv_moist_7);
        tvMoist8 = root.findViewById(R.id.tv_moist_8);
        tvVolume = root.findViewById(R.id.tv_volume);
        myRef.child("Cuaca").addValueEventListener(new ValueEventListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue(Integer.class)==0){
                    tvCuaca.setText("Hujan");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                      tvCuaca.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_hujan), null, null, null);
                    }
                }
                else {
                    tvCuaca.setText("Tidak Hujan");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        tvCuaca.setCompoundDrawablesWithIntrinsicBounds(getResources().getDrawable(R.drawable.ic_tidakhujan), null, null, null);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Gagal Mengambil Data \n"+error.toException(), Toast.LENGTH_LONG).show();
            }
        });
        myRef.child("LEVEL_AIR").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int volume = snapshot.child("level").getValue(Integer.class);
                tvVolume.setText(String.valueOf(volume)+"%");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Gagal Mengambil Data \n"+error.toException(), Toast.LENGTH_LONG).show();
            }
        });

        myRef.child("DHT").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Float dataSuhu = snapshot.child("Temp").getValue(Float.class);
                Float dataHum = snapshot.child("Hum").getValue(Float.class);
                Log.i("cobaa", dataSuhu+ "/" +dataHum);
                tvSuhu.setText(String.valueOf(dataSuhu));
                tvHum.setText(String.valueOf(dataHum));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Gagal Mengambil Data \n"+error.toException(), Toast.LENGTH_LONG).show();
            }
        });
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                dataMoist1 = snapshot.child("Tanaman1").child("Kelembaban1").getValue(Float.class);
                dataMoist2 = snapshot.child("Tanaman1").child("Kelembaban2").getValue(Float.class);
                dataMoist3 = snapshot.child("Tanaman1").child("Kelembaban3").getValue(Float.class);
                Log.i("cobaa", dataMoist1+ "/" +dataMoist2);
                tvMoist1.setText(String.valueOf(dataMoist1));
                tvMoist2.setText(String.valueOf(dataMoist2));
                tvMoist3.setText(String.valueOf(dataMoist3));
                dataMoist4 = snapshot.child("Tanaman2").child("Kelembaban4").getValue(Float.class);
                dataMoist5 = snapshot.child("Tanaman2").child("Kelembaban5").getValue(Float.class);
                dataMoist6 = snapshot.child("Tanaman2").child("Kelembaban6").getValue(Float.class);
                tvMoist4.setText(String.valueOf(dataMoist4));
                tvMoist5.setText(String.valueOf(dataMoist5));
                tvMoist6.setText(String.valueOf(dataMoist6));
                dataMoist7 = snapshot.child("Tanaman3").child("Kelembaban7").getValue(Float.class);
                dataMoist8 = snapshot.child("Tanaman3").child("Kelembaban8").getValue(Float.class);
                tvMoist7.setText(String.valueOf(dataMoist7));
                tvMoist8.setText(String.valueOf(dataMoist8));
                if (dataMoist1 != null || dataMoist2 != null || dataMoist3 != null || dataMoist4 != null || dataMoist5 != null || dataMoist6 != null || dataMoist7 != null || dataMoist8 != null){
                    moistAvg = (dataMoist1+dataMoist2+dataMoist3+dataMoist4+dataMoist5+dataMoist6+dataMoist7+dataMoist8)/8;
                    tvMoistAvg.setText(String.valueOf(moistAvg)+"%");
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getActivity().getApplicationContext(), "Gagal Mengambil Data \n"+error.toException(), Toast.LENGTH_LONG).show();
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