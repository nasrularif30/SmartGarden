package com.movtech.smartgarden.ui.dashboard;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

public class DashboardFragment extends Fragment {
    ImageView setting;
    CheckBox tgSiram, tgMode;
    SharedPreferences sharedPreferences;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference();

    private DashboardViewModel dashboardViewModel;

    @SuppressLint("UseCompatLoadingForDrawables")
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(DashboardViewModel.class);
        View root = inflater.inflate(R.layout.fragment_dashboard, container, false);

        sharedPreferences = this.getActivity().getSharedPreferences("data_user", Context.MODE_PRIVATE);
        setting= root.findViewById(R.id.setting);
        tgSiram = root.findViewById(R.id.tg_siram);
        tgMode = root.findViewById(R.id.tg_mode);

        dashboardViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
            }
        });

        myRef.addValueEventListener(new ValueEventListener() {
            @SuppressLint("UseCompatLoadingForDrawables")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int mode = snapshot.child("Kontrol").child("getMode").getValue(Integer.class);
                int siram = snapshot.child("Kontrol").child("getSiram").getValue(Integer.class);
                if (mode == 1) {
                    tgSiram.setClickable(false);
                    tgSiram.setEnabled(false);
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                tgMode.setBackground(getResources().getDrawable(R.drawable.ic_baseline_toggle_on_24, getActivity().getTheme()));
//                            } else
                    tgMode.setBackground(getResources().getDrawable(R.drawable.ic_baseline_toggle_on_24));
                } else {
                    tgSiram.setClickable(true);
                    tgSiram.setEnabled(true);
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                tgMode.setButtonDrawable(getResources().getDrawable(R.drawable.ic_baseline_toggle_off_24, getActivity().getTheme()));
//                            } else
                    tgMode.setBackground(getResources().getDrawable(R.drawable.ic_baseline_toggle_off_24));
                }
                if (siram == 1) {
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                tgSiram.setBackground(getResources().getDrawable(R.drawable.ic_baseline_toggle_on_24, getActivity().getTheme()));
//                            } else
                    tgSiram.setBackground(getResources().getDrawable(R.drawable.ic_baseline_toggle_on_24));
                } else {
//                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                                tgSiram.setBackground(getResources().getDrawable(R.drawable.ic_baseline_toggle_off_24, getActivity().getTheme()));
//                            } else
                    tgSiram.setBackground(getResources().getDrawable(R.drawable.ic_baseline_toggle_off_24));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (tgMode.isChecked()) {
            tgSiram.setEnabled(false);
            tgSiram.setClickable(false);
            tgMode.setBackground(getResources().getDrawable(R.drawable.ic_baseline_toggle_on_24));
            myRef.child("Kontrol").child("getMode").setValue(1);
        } else {
            tgSiram.setEnabled(true);
            tgSiram.setClickable(true);
            tgMode.setBackground(getResources().getDrawable(R.drawable.ic_baseline_toggle_off_24));
            myRef.child("Kontrol").child("getMode").setValue(0);
        }
        tgMode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (tgMode.isChecked()) {
                    tgSiram.setEnabled(false);
                    tgSiram.setClickable(false);
                    tgMode.setBackground(getResources().getDrawable(R.drawable.ic_baseline_toggle_on_24));
                    myRef.child("Kontrol").child("getMode").setValue(1);
                } else {
                    tgSiram.setEnabled(true);
                    tgSiram.setClickable(true);
                    tgMode.setBackground(getResources().getDrawable(R.drawable.ic_baseline_toggle_off_24));
                    myRef.child("Kontrol").child("getMode").setValue(0);
                }
            }
        });
        tgSiram.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (tgSiram.isChecked()) {
                    tgSiram.setBackground(getResources().getDrawable(R.drawable.ic_baseline_toggle_on_24));
                    myRef.child("Kontrol").child("getSiram").setValue(1);
                } else {
                    tgSiram.setBackground(getResources().getDrawable(R.drawable.ic_baseline_toggle_off_24));
                    myRef.child("Kontrol").child("getSiram").setValue(0);
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