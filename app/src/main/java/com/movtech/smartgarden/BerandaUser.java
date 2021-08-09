package com.movtech.smartgarden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.movtech.smartgarden.data.DHT;

import static com.movtech.smartgarden.data.ChildDatabase.dht;

public class BerandaUser extends AppCompatActivity {
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
    TextView tvVolume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_user);

        sharedPreferences = getSharedPreferences("data_user", Context.MODE_PRIVATE);

        setting = findViewById(R.id.setting);
        tvCuaca = findViewById(R.id.tv_hujan);
        tvSuhu1 = findViewById(R.id.tv_suhu_1);
        tvSuhu2 = findViewById(R.id.tv_suhu_2);
        tvSuhu3 = findViewById(R.id.tv_suhu_3);
        tvSuhu4 = findViewById(R.id.tv_suhu_4);
        tvHum1 = findViewById(R.id.tv_hum_1);
        tvHum2 = findViewById(R.id.tv_hum_2);
        tvHum3 = findViewById(R.id.tv_hum_3);
        tvHum4 = findViewById(R.id.tv_hum_4);
        tvMoist1a = findViewById(R.id.tv_moist_1a);
        tvMoist1b = findViewById(R.id.tv_moist_1b);
        tvMoist1c = findViewById(R.id.tv_moist_1c);
        tvMoist2a = findViewById(R.id.tv_moist_2a);
        tvMoist2b = findViewById(R.id.tv_moist_2b);
        tvMoist2c = findViewById(R.id.tv_moist_2c);
        tvMoist3a = findViewById(R.id.tv_moist_3a);
        tvMoist3b = findViewById(R.id.tv_moist_3b);
        tvMoist3c = findViewById(R.id.tv_moist_3c);
        tvMoist4a = findViewById(R.id.tv_moist_4a);
        tvMoist4b = findViewById(R.id.tv_moist_4b);
        tvMoist4c = findViewById(R.id.tv_moist_4c);
        tvVolume = findViewById(R.id.tv_volume);
        myRef.child("Cuaca").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.getValue(Integer.class)==0){
                    tvCuaca.setText("Hujan");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        tvCuaca.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.ic_hujan), null, null, null);
                    }
                }
                else {
                    tvCuaca.setText("Tidak Hujan");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        tvCuaca.setCompoundDrawablesWithIntrinsicBounds(getDrawable(R.drawable.ic_tidakhujan),null, null, null);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BerandaUser.this, "Gagal Mengambil Data \n"+error.toException(), Toast.LENGTH_LONG).show();
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
                Toast.makeText(BerandaUser.this, "Gagal Mengambil Data \n"+error.toException(), Toast.LENGTH_LONG).show();
            }
        });
        myRef.child("DHT").addValueEventListener(new ValueEventListener() {
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
                Toast.makeText(BerandaUser.this, "Gagal Mengambil Data \n"+error.toException(), Toast.LENGTH_LONG).show();
            }
        });
        myRef.child("Tanaman1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.child("moist1") == null){
//                    Log.i("cobaa", "onDataChange: "+snapshot.getChildren().toString());
//                    tvMoist1a.setText("-");
//                    tvMoist1b.setText("-");
//                    tvMoist1c.setText("-");
//                }
//                else {
                    Float dataMoist1 = snapshot.child("Kelembaban1").getValue(Float.class);
                    Float dataMoist2 = snapshot.child("Kelembaban2").getValue(Float.class);
                    Float dataMoist3 = snapshot.child("Kelembaban3").getValue(Float.class);
                    Log.i("cobaa", dataMoist1+ "/" +dataMoist2);
                    tvMoist1a.setText(dataMoist1.toString());
                    tvMoist1b.setText(dataMoist2.toString());
                    tvMoist1c.setText(dataMoist3.toString());
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BerandaUser.this, "Gagal Mengambil Data \n"+error.toException(), Toast.LENGTH_LONG).show();
            }
        });
        myRef.child("Tanaman2").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.child("moist1") == null){
//                    Log.i("cobaa", "onDataChange: "+snapshot.getChildren().toString());
//                    tvMoist2a.setText("-");
//                    tvMoist2b.setText("-");
//                    tvMoist2c.setText("-");
//                }
//                else {
                    Float dataMoist1 = snapshot.child("Kelembaban4").getValue(Float.class);
                    Float dataMoist2 = snapshot.child("Kelembaban5").getValue(Float.class);
                    Float dataMoist3 = snapshot.child("Kelembaban6").getValue(Float.class);
                    Log.i("cobaa", dataMoist1+ "/" +dataMoist2);
                    tvMoist2a.setText(dataMoist1.toString());
                    tvMoist2b.setText(dataMoist2.toString());
                    tvMoist2c.setText(dataMoist3.toString());
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BerandaUser.this, "Gagal Mengambil Data \n"+error.toException(), Toast.LENGTH_LONG).show();
            }
        });
        myRef.child("Tanaman3").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.child("moist1") == null){
//                    Log.i("cobaa", "onDataChange: "+snapshot.getChildren().toString());
//                    tvMoist3a.setText("-");
//                    tvMoist3b.setText("-");
//                    tvMoist3c.setText("-");
//                }
//                else {
                    Float dataMoist1 = snapshot.child("Kelembaban7").getValue(Float.class);
                    Float dataMoist2 = snapshot.child("Kelembaban8").getValue(Float.class);
                    Float dataMoist3 = snapshot.child("Kelembaban9").getValue(Float.class);
                    Log.i("cobaa", dataMoist1+ "/" +dataMoist2);
                    tvMoist3a.setText(dataMoist1.toString());
                    tvMoist3b.setText(dataMoist2.toString());
                    tvMoist3c.setText(dataMoist3.toString());
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BerandaUser.this, "Gagal Mengambil Data \n"+error.toException(), Toast.LENGTH_LONG).show();
            }
        });
        myRef.child("Tanaman4").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.child("moist1").getValue() == null){
//                    Log.i("cobaa", "onDataChange: "+snapshot.getChildren().toString());
//                    tvMoist4a.setText("-");
//                    tvMoist4b.setText("-");
//                    tvMoist4c.setText("-");
//                }
//                else {
                    Float dataMoist1 = snapshot.child("Kelembaban10").getValue(Float.class);
                    Float dataMoist2 = snapshot.child("Kelembaban11").getValue(Float.class);
                    Float dataMoist3 = snapshot.child("Kelembaban12").getValue(Float.class);
                    Log.i("cobaa", dataMoist1+ "/" +dataMoist2);
                    tvMoist4a.setText(dataMoist1.toString());
                    tvMoist4b.setText(dataMoist2.toString());
                    tvMoist4c.setText(dataMoist3.toString());
//                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BerandaUser.this, "Gagal Mengambil Data \n"+error.toException(), Toast.LENGTH_LONG).show();
            }
        });
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });
    }

    public void logout(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("id", "null");
        editor.putString("user", "null");
        editor.putBoolean("sudahLogin", false);
        editor.apply();
        finish();
        startActivity(new Intent(BerandaUser.this, LoginRole.class));
    }
}