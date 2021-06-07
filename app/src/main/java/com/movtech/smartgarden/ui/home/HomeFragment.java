package com.movtech.smartgarden.ui.home;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
    TextView tvSuhu1, tvSuhu2, tvSuhu3, tvSuhu4, tvHum1, tvHum2, tvHum3, tvHum4;
    TextView tvMoist1a, tvMoist1b, tvMoist1c;
    TextView tvMoist2a, tvMoist2b, tvMoist2c;
    TextView tvMoist3a, tvMoist3b, tvMoist3c;
    TextView tvMoist4a, tvMoist4b, tvMoist4c;
    private HomeViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        sharedPreferences = this.getActivity().getSharedPreferences("data_user", Context.MODE_PRIVATE);
        setting = root.findViewById(R.id.setting);
        tvCuaca = root.findViewById(R.id.tv_hujan);
        tvSuhu1 = root.findViewById(R.id.tv_suhu_1);
        tvSuhu2 = root.findViewById(R.id.tv_suhu_2);
        tvSuhu3 = root.findViewById(R.id.tv_suhu_3);
        tvSuhu4 = root.findViewById(R.id.tv_suhu_4);
        tvHum1 = root.findViewById(R.id.tv_hum_1);
        tvHum2 = root.findViewById(R.id.tv_hum_2);
        tvHum3 = root.findViewById(R.id.tv_hum_3);
        tvHum4 = root.findViewById(R.id.tv_hum_4);
        tvMoist1a = root.findViewById(R.id.tv_moist_1a);
        tvMoist1b = root.findViewById(R.id.tv_moist_1b);
        tvMoist1c = root.findViewById(R.id.tv_moist_1c);
        tvMoist2a = root.findViewById(R.id.tv_moist_2a);
        tvMoist2b = root.findViewById(R.id.tv_moist_2b);
        tvMoist2c = root.findViewById(R.id.tv_moist_2c);
        tvMoist3a = root.findViewById(R.id.tv_moist_3a);
        tvMoist3b = root.findViewById(R.id.tv_moist_3b);
        tvMoist3c = root.findViewById(R.id.tv_moist_3c);
        tvMoist4a = root.findViewById(R.id.tv_moist_4a);
        tvMoist4b = root.findViewById(R.id.tv_moist_4b);
        tvMoist4c = root.findViewById(R.id.tv_moist_4c);

        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                myRef.child("DHT").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.child("Temp") == null){
//                    Log.i("cobaa", "onDataChange: "+snapshot.getChildren().toString());
//                    tvSuhu1.setText("-");
//                    tvSuhu2.setText("-");
//                    tvSuhu3.setText("-");
//                    tvSuhu4.setText("-");
//
//                    tvHum1.setText("-");
//                    tvHum2.setText("-");
//                    tvHum3.setText("-");
//                    tvHum4.setText("-");
//                }
//                else {
                        Float dataSuhu = snapshot.child("Temp").getValue(Float.class);
                        Float dataHum = snapshot.child("Hum").getValue(Float.class);
                        Log.i("cobaa", dataSuhu+ "/" +dataHum);
                        tvSuhu1.setText(dataSuhu.toString());
                        tvSuhu2.setText(dataSuhu.toString());
                        tvSuhu3.setText(dataSuhu.toString());
                        tvSuhu4.setText(dataSuhu.toString());

                        tvHum1.setText(dataHum.toString());
                        tvHum2.setText(dataHum.toString());
                        tvHum3.setText(dataHum.toString());
                        tvHum4.setText(dataHum.toString());
//                }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity().getApplicationContext(), "Gagal Mengambil Data \n"+error.toException(), Toast.LENGTH_LONG).show();
                    }
                });
                myRef.child("Tanaman1").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.child("moist1") == null){
//                    Log.i("cobaa", "onDataChange: "+snapshot.getChildren().toString());
//                    tvMoist1a.setText("-");
//                    tvMoist1b.setText("-");
//                    tvMoist1c.setText("-");
//                }
//                else {
                        Float dataMoist1 = snapshot.child("moist1").getValue(Float.class);
                        Float dataMoist2 = snapshot.child("moist2").getValue(Float.class);
                        Float dataMoist3 = snapshot.child("moist3").getValue(Float.class);
                        Log.i("cobaa", dataMoist1+ "/" +dataMoist2);
                        tvMoist1a.setText(dataMoist1.toString());
                        tvMoist1b.setText(dataMoist2.toString());
                        tvMoist1c.setText(dataMoist3.toString());
//                }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity().getApplicationContext(), "Gagal Mengambil Data \n"+error.toException(), Toast.LENGTH_LONG).show();
                    }
                });
                myRef.child("Tanaman2").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.child("moist1") == null){
//                    Log.i("cobaa", "onDataChange: "+snapshot.getChildren().toString());
//                    tvMoist2a.setText("-");
//                    tvMoist2b.setText("-");
//                    tvMoist2c.setText("-");
//                }
//                else {
                        Float dataMoist1 = snapshot.child("moist1").getValue(Float.class);
                        Float dataMoist2 = snapshot.child("moist2").getValue(Float.class);
                        Float dataMoist3 = snapshot.child("moist3").getValue(Float.class);
                        Log.i("cobaa", dataMoist1+ "/" +dataMoist2);
                        tvMoist2a.setText(dataMoist1.toString());
                        tvMoist2b.setText(dataMoist2.toString());
                        tvMoist2c.setText(dataMoist3.toString());
//                }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity().getApplicationContext(), "Gagal Mengambil Data \n"+error.toException(), Toast.LENGTH_LONG).show();
                    }
                });
                myRef.child("Tanaman3").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.child("moist1") == null){
//                    Log.i("cobaa", "onDataChange: "+snapshot.getChildren().toString());
//                    tvMoist3a.setText("-");
//                    tvMoist3b.setText("-");
//                    tvMoist3c.setText("-");
//                }
//                else {
                        Float dataMoist1 = snapshot.child("moist1").getValue(Float.class);
                        Float dataMoist2 = snapshot.child("moist2").getValue(Float.class);
                        Float dataMoist3 = snapshot.child("moist3").getValue(Float.class);
                        Log.i("cobaa", dataMoist1+ "/" +dataMoist2);
                        tvMoist3a.setText(dataMoist1.toString());
                        tvMoist3b.setText(dataMoist2.toString());
                        tvMoist3c.setText(dataMoist3.toString());
//                }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity().getApplicationContext(), "Gagal Mengambil Data \n"+error.toException(), Toast.LENGTH_LONG).show();
                    }
                });
                myRef.child("Tanaman4").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.child("moist1").getValue() == null){
//                    Log.i("cobaa", "onDataChange: "+snapshot.getChildren().toString());
//                    tvMoist4a.setText("-");
//                    tvMoist4b.setText("-");
//                    tvMoist4c.setText("-");
//                }
//                else {
                        Float dataMoist1 = snapshot.child("moist1").getValue(Float.class);
                        Float dataMoist2 = snapshot.child("moist2").getValue(Float.class);
                        Float dataMoist3 = snapshot.child("moist3").getValue(Float.class);
                        Log.i("cobaa", dataMoist1+ "/" +dataMoist2);
                        tvMoist4a.setText(dataMoist1.toString());
                        tvMoist4b.setText(dataMoist2.toString());
                        tvMoist4c.setText(dataMoist3.toString());
//                }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(getActivity().getApplicationContext(), "Gagal Mengambil Data \n"+error.toException(), Toast.LENGTH_LONG).show();
                    }
                });
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