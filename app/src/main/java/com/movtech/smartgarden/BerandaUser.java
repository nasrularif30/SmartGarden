package com.movtech.smartgarden;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beranda_user);

        sharedPreferences = getSharedPreferences("data_user", Context.MODE_PRIVATE);

        setting=findViewById(R.id.setting);
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
                Toast.makeText(BerandaUser.this, "Gagal Mengambil Data \n"+error.toException(), Toast.LENGTH_LONG).show();
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
                Toast.makeText(BerandaUser.this, "Gagal Mengambil Data \n"+error.toException(), Toast.LENGTH_LONG).show();
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
                Toast.makeText(BerandaUser.this, "Gagal Mengambil Data \n"+error.toException(), Toast.LENGTH_LONG).show();
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
                Toast.makeText(BerandaUser.this, "Gagal Mengambil Data \n"+error.toException(), Toast.LENGTH_LONG).show();
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