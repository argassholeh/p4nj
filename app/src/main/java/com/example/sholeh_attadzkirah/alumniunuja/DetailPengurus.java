/*
 * Copyright (c) Muhammad Solehudin
 */

package com.example.sholeh_attadzkirah.alumniunuja;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sholeh_attadzkirah.alumniunuja.config.koneksi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.key_id_alumni;

public class DetailPengurus extends AppCompatActivity {

    private TextView tvxnama, tvxjabatan, tvxdevisi, tvxmasabakti,tvxemail, tvxnohp, tvxakunfb ;
    private ImageView detailfoto;
    private SharedPreferences pref1;

    GlidModuleMe moduleMe = new GlidModuleMe();
    ArrayList<HashMap<String, String>> tampil_detail_akun = new ArrayList<HashMap<String, String>>();

    Toolbar toolBarisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_pengurus);
        toolBarisi =  findViewById(R.id.toolbar);
        toolBarisi.setTitle("Detail Pengurus ");
        setSupportActionBar(toolBarisi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tvxnama = findViewById(R.id.tv_detailnama);
        tvxjabatan = findViewById(R.id.tv_detailJabatan);
        tvxdevisi =  findViewById(R.id.tv_detailDevisi);
        tvxmasabakti = findViewById(R.id.tv_detail_masa_bakti);
        tvxemail =  findViewById(R.id.tv_detailemail);
        tvxnohp = findViewById(R.id.tv_detail_nohp);
        tvxakunfb = findViewById(R.id.tv_detail_Akun_fb);
        detailfoto = findViewById(R.id.image_detailprofile);

        tampilDetailPengurus(getIntent().getStringExtra("id_alumni"));
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish(); //  kalau tanpa ini tombol panahnya tak berfungsi
        return true;
    }

    public  void tampilDetailPengurus(String idP){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, koneksi.tampilDetailPengurus,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("1")) {
                            try {
                                JSONObject jsonObject;
                                jsonObject = new JSONObject(response);
                                JSONArray result = jsonObject.getJSONArray("Hasil");
                                tampil_detail_akun.clear();
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject c = result.getJSONObject(i);
                                    GlideApp.with(DetailPengurus.this)
                                            .load(koneksi.tampilFotoDetailPengurus+c.getString("foto"))
                                            .placeholder(R.drawable.ic_orang_grey_24dp)
                                            .into(detailfoto);

                                    tvxnama.setText(c.getString("nama"));
                                    tvxjabatan.setText(c.getString("nama_jabatan"));
                                    tvxdevisi.setText(c.getString("nama_devisi"));
                                    tvxmasabakti.setText(c.getString("masa_bakti"));
                                    tvxemail.setText(c.getString("email"));
                                    tvxnohp.setText(c.getString("telepon"));
                                    tvxakunfb.setText(c.getString("username"));
                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }else{
                            Toast.makeText(getApplication(), response, Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
//                params.put( key_id_alumni, preferences.getUsername());
                params.put( key_id_alumni, idP);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        requestQueue.add(stringRequest);
    }
}
