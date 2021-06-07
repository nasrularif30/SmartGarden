package com.movtech.smartgarden.ui.login;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.movtech.smartgarden.AdminActivity;
import com.movtech.smartgarden.BerandaUser;
import com.movtech.smartgarden.LoginRole;
import com.movtech.smartgarden.MainActivity;
import com.movtech.smartgarden.R;

public class LoginActivity extends AppCompatActivity {

    private LoginViewModel loginViewModel;
    private FirebaseAuth mAuth;
    FirebaseDatabase firebaseDatabase= FirebaseDatabase.getInstance();
    DatabaseReference myRef = firebaseDatabase.getReference();
    SharedPreferences sharedPreferences;
    String role, id, username, password;
    int kode;
    LoginRole roleActivity = new LoginRole();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        mAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("data_user", Context.MODE_PRIVATE);
        loginViewModel = new ViewModelProvider(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        final EditText usernameEditText = findViewById(R.id.etusername);
        final EditText passwordEditText = findViewById(R.id.etpassword);
        final CardView loginButton = findViewById(R.id.btn_login);
        final ProgressBar loadingProgressBar = findViewById(R.id.loading);

        switch (kode){
            case 1:
                usernameEditText.setHint("Email");
                break;
            case 2:
                usernameEditText.setHint("Username");
                break;
        }

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    usernameEditText.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    passwordEditText.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });


        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                loginViewModel.loginDataChanged(usernameEditText.getText().toString(),
                        passwordEditText.getText().toString());
            }
        };
        usernameEditText.addTextChangedListener(afterTextChangedListener);
        passwordEditText.addTextChangedListener(afterTextChangedListener);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (usernameEditText.getText().toString().isEmpty() && !passwordEditText.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Username atau Email tidak boleh kosong", Toast.LENGTH_LONG).show();
                }
                else if (!usernameEditText.getText().toString().isEmpty() && passwordEditText.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Password tidak boleh kosong", Toast.LENGTH_LONG).show();
                }
                else if (usernameEditText.getText().toString().isEmpty() && passwordEditText.getText().toString().isEmpty()){
                    Toast.makeText(LoginActivity.this, "Username dan Password tidak boleh kosong", Toast.LENGTH_LONG).show();
                }
                else {

                    switch (kode){
                        case 1:
                            mAuth.signInWithEmailAndPassword(usernameEditText.getText().toString(), passwordEditText.getText().toString())
                                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<AuthResult> task) {
                                            if (task.isSuccessful()){
                                                final String id = task.getResult().getUser().getUid();
                                                myRef.child("admin/"+id).addValueEventListener(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                        Log.i("viewid", "id: "+id);
                                                        Log.i("viewExist", "onDataChange: "+snapshot.exists());
                                                        if (snapshot.exists()){
                                                            LoginRole.getInstance().finish();
                                                            login(1);
                                                            finish();
                                                            startActivity(new Intent(LoginActivity.this, AdminActivity.class));
                                                        } else {
                                                            loadingProgressBar.setVisibility(View.INVISIBLE);
                                                            Toast.makeText(LoginActivity.this, "Login Gagal, User Tidak Ditemukan", Toast.LENGTH_LONG).show();
                                                            FirebaseAuth.getInstance().signOut();
                                                        }
                                                    }

                                                    @Override
                                                    public void onCancelled(@NonNull DatabaseError error) {

                                                    }
                                                });
                                            } else {
                                                loadingProgressBar.setVisibility(View.INVISIBLE);
                                                Toast.makeText(LoginActivity.this, "Authentification Failed. "+task.getException().toString(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                            break;
                        case 2:
                            myRef.child("login/viewer").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    username = usernameEditText.getText().toString();
                                    password = passwordEditText.getText().toString();
                                    if (snapshot.hasChild(username)){
                                        String value = snapshot.child(username).getValue(String.class);
                                        if (value.equals(password)){
                                            login(0);
                                            finish();
                                            startActivity(new Intent(LoginActivity.this, BerandaUser.class));

                                        } else {
                                            Toast.makeText(LoginActivity.this, "salah", Toast.LENGTH_LONG).show();
                                        }
                                    }else {
                                        loadingProgressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(LoginActivity.this, "Salah", Toast.LENGTH_LONG).show();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    Log.w("canceled", "onCancelled: ",error.toException() );
                                }
                            });
                            break;
                    }
                }
            }
        });
    }

    @Override
    public void onStart(){
        super.onStart();
        Bundle bundle = getIntent().getExtras();
        kode = bundle.getInt("kode");
        Drawable drawable = null;
    }
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
    }
    public void login(int user){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (user == 1){
            editor.putString("user", "admin");
        }
        else
            editor.putString("user", "viewer");
        editor.putString("id", username);
        editor.putBoolean("sudahLogin", true);
        editor.apply();
        LoginRole.getInstance().finish();
    }
    private void updateUI(FirebaseUser user){
        if (user != null){
            finish();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        }
    }
}