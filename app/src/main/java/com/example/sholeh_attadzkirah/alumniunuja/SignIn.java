/*
 * Copyright (c) Muhammad Solehudin
 */

package com.example.sholeh_attadzkirah.alumniunuja;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;




import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignIn extends AppCompatActivity {
    TextView tv_alumni, getstart;
    String nama,picture;
    ImageView btlog;

    Button masuk, daftar, fb;
    private ProgressDialog progres;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        masuk = findViewById(R.id.btnMasuk);
        daftar = findViewById(R.id.btnDaftar);
        fb = findViewById(R.id.btn_FB);
        masuk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Login.class);
                startActivity(intent);
            }
        });
        daftar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(SignIn.this,VerivikasiData.class);
                startActivity(it);
                finish();
            }
        });
        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignIn.this,"Mohon Maaf Konten Ini Masih Dalam Tahap Pengembangan",Toast.LENGTH_LONG).show();
            }
        });
    }
}

