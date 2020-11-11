/*
 * Copyright (c) Muhammad Solehudin
 */

package com.example.sholeh_attadzkirah.alumniunuja;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import com.example.sholeh_attadzkirah.alumniunuja.config.koneksi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.akanLogin;
import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.key_password;
import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.key_username;
import  com.example.sholeh_attadzkirah.alumniunuja.Preferences;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import custom_font.MyTextView;

import com.android.volley.toolbox.Volley;


import java.util.HashMap;
import java.util.Map;



public class Login extends AppCompatActivity {
    private EditText ed_Username;
    private EditText ed_Password;
    private Button btn_Login;

    private String username;
    private String password;
    private String idnya;
    private ProgressDialog progres;


    Preferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ed_Username = findViewById(R.id.edUser);
        ed_Password = findViewById(R.id.edPassword);
        btn_Login = findViewById(R.id.btnLogin);
        btn_Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            login();
            }
        });

        preferences = new Preferences(this);

        if (preferences.getSPSudahLogin() && (preferences.getSPStatus().equalsIgnoreCase("korcam"))){
            this.finish();
            startActivity(new Intent(Login.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));

        }else if (preferences.getSPSudahLogin() && (preferences.getSPStatus().equalsIgnoreCase("pengurus"))){
            this.finish();
            startActivity(new Intent(Login.this, MainActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));

        }else if (preferences.getSPSudahLogin() && (preferences.getSPStatus().equalsIgnoreCase("alumni"))){
        this.finish();
        startActivity(new Intent(Login.this, MainActivity.class)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));

    }

    }

//    @Override
//    public void onBackPressed() {
//      finish();
//
//    }40



    public void login(){
        userLogin();
    }
    private void userLogin() {
        username = ed_Username.getText().toString().trim();
        password = ed_Password.getText().toString().trim();

        if (!validasi()) return;

        progres = new ProgressDialog(this);
        progres.setMessage("Proses...");
        progres.show();
        String url = koneksi.akanLogin;


        StringRequest stringRequest = new StringRequest(Request.Method.POST,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            String aktif = "";
                            String username = "";
//                            String usernis = "";
                            JSONObject jsonObject;
                            jsonObject = new JSONObject(response);
                            String status = jsonObject.getString("status");
                            JSONArray result = jsonObject.getJSONArray("Hasil");
                            for(int i =0; i < result.length(); i++){
                                JSONObject c = result.getJSONObject(i);
                                aktif = c.getString("status");
                                username = c.getString("id_alumni"); // get id alumni
//                                usernis = c.getString("nis"); // ketika di tambah ini tidak ada yg bisa login

                            }

                            if(status.equalsIgnoreCase("korcam")){
//                                Toast.makeText(Login.this,"Sukses Login Korcam",Toast.LENGTH_LONG).show();
                                Toast.makeText(Login.this,"Login Sukses",Toast.LENGTH_LONG).show();
                                openUtama(username, aktif);
                                preferences.saveSPBoolean(Preferences.SP_SUDAH_LOGIN, true);
                                preferences.saveSPString(preferences.SP_STATUS, "korcam");
                                preferences.saveSPString(preferences.SP_Aktif,aktif);
                                preferences.saveSPString(preferences.SP_UserName,username);
                                finish();
                                progres.dismiss();
                            }else if (status.equalsIgnoreCase("pengurus")){
                                openUtama(username, aktif);
//                                Toast.makeText(Login.this,"Sukses Login Pengurus",Toast.LENGTH_LONG).show();
                                Toast.makeText(Login.this,"Login Sukses",Toast.LENGTH_LONG).show();
                                preferences.saveSPBoolean(Preferences.SP_SUDAH_LOGIN, true);
                                preferences.saveSPString(preferences.SP_STATUS, "pengurus");
                                preferences.saveSPString(preferences.SP_Aktif,aktif);
                                preferences.saveSPString(preferences.SP_UserName,username);
                                finish();
                                progres.dismiss();
                            }else if (status.equalsIgnoreCase("alumni")){
                                openUtama(username, aktif);
                                preferences.saveSPBoolean(Preferences.SP_SUDAH_LOGIN, true);
                                preferences.saveSPString(preferences.SP_STATUS, "alumni");
                                preferences.saveSPString(preferences.SP_Aktif,aktif);
                                preferences.saveSPString(preferences.SP_UserName,username);
                                finish();
                                Toast.makeText(Login.this,"Login Sukses",Toast.LENGTH_LONG).show();
                                progres.dismiss();

//                            }else if (status.equalsIgnoreCase("pengurusfks")){
//                                openUtama(usernis, aktif);
//                                preferences.saveSPBoolean(Preferences.SP_SUDAH_LOGIN, true);
//                                preferences.saveSPString(preferences.SP_STATUS, "pengurusfks");
//                                preferences.saveSPString(preferences.SP_Aktif,aktif);
//                                preferences.saveSPString(preferences.SP_UserName,usernis);
//                                finish();
//                                Toast.makeText(Login.this,"Login Sukses",Toast.LENGTH_LONG).show();
////                                Toast.makeText(Login.this,"Login Sukses Pengurus FKS ",Toast.LENGTH_LONG).show();
//                                progres.dismiss();
                            } else {
                                Toast.makeText(Login.this,"User Name dan Password Salah",Toast.LENGTH_LONG).show();
                                progres.dismiss();

                            }
                        } catch (JSONException e) {
                            progres.dismiss();
//                            Toast.makeText(Login.this,"Login Gagal "+e,Toast.LENGTH_LONG).show();
                            Toast.makeText(Login.this,"User Name dan Password Salah",Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                        progres.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(Login.this,error.toString(),Toast.LENGTH_LONG ).show();
                        Toast.makeText(Login.this,"Koneksi Internet Anda Kurang Stabil"+error,Toast.LENGTH_LONG).show();
                        progres.dismiss();

                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> map = new HashMap<String,String>();
                map.put(key_username,username);
                map.put(key_password,password);

                return map;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void openUtama(String username, String aktif){
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", username);
        intent.putExtra("aktif", aktif);
        startActivity(intent);
        finish();

    }



    private boolean validasi() {
//        if (TextUtils.isEmpty(txt_username) || TextUtils.isEmpty(txt_password)){
//            Toast.makeText(LoginActivity.this, "Isi harus lengkap", Toast.LENGTH_SHORT).show();
//        } else {
//            Toast.makeText(LoginActivity.this, "kosong", Toast.LENGTH_SHORT).show();
//        }
        boolean valid = true;

        String susername = ed_Username.getText().toString().trim();
       String spassword = ed_Password.getText().toString().trim();

        if (susername.isEmpty() || ed_Username.length() < 3 ) {
            ed_Username.setError("masukkan username minimal 3 digit");
//            Toast.makeText(Login.this, "username minimal 3 digit", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            ed_Username.setError(null);
        }

        if (spassword.isEmpty() || ed_Password.length() < 3) {
            ed_Password.setError("masukkan password minimal 3 digit");
            valid = false;
        } else {
            ed_Password.setError(null);
        }

        return valid;
    }


}
