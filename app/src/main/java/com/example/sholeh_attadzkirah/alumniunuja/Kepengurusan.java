/*
 * Copyright (c) Muhammad Solehudin
 */

package com.example.sholeh_attadzkirah.alumniunuja;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.sholeh_attadzkirah.alumniunuja.mRecycler.AdapterKepengurusan;
import com.example.sholeh_attadzkirah.alumniunuja.mRecycler.DetailDataKepengurusanModel;
import com.example.sholeh_attadzkirah.alumniunuja.config.koneksi;

import java.util.ArrayList;

import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.key_id_lembaga_alumni;
import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.key_masabakti;
import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.tampilMasaKepengurusan;
import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.tampilVisiMisiFksJ;
import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.tampil_masa_bakti;

public class Kepengurusan extends AppCompatActivity {
    RecyclerView rv;
    /// tampilkan data berbentuk array
    ArrayList<HashMap<String, String>> tampil = new ArrayList<HashMap<String, String>>();
    //mana class adapter_berita dibuat varibel
    RecyclerView recycler3;
    AdapterKepengurusan adapter;
    Spinner spin_masa_Bakti;
    ArrayList<String> arrayDataMasaBakti = new ArrayList<>();
    ArrayList<String> listID_masabakti = new ArrayList<>();
    ArrayList<String> listID_LembagaAlumni = new ArrayList<>();

    private ProgressDialog pDialog;

    private ArrayList<DetailDataKepengurusanModel> DetailDataKepengurusanModelArraylist;

    Toolbar toolBarisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kepengurusan);
        toolBarisi =  findViewById(R.id.toolbar);
        toolBarisi.setTitle("Pengurus ");
        setSupportActionBar(toolBarisi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        spin_masa_Bakti = findViewById(R.id.spin_masabakti);
        spin_masa_Bakti.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tampilMasaPengurus(getIntent().getStringExtra("Pengurus"),arrayDataMasaBakti.get(position)); // get_id_lembaga_alumni, getMasaBakti nya
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        recycler3 = findViewById(R.id.rvK);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycler3.setLayoutManager(layoutManager);

        tampilMasaBaktiloadFksJ(getIntent().getStringExtra("Pengurus"));
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish(); //  kalau tanpa ini tombol panahnya tak berfungsi
        return true;
    }

    private void tampilMasaPengurus(String idV, String MasaBakti ) {
    StringRequest stringRequest = new StringRequest(Request.Method.POST,koneksi.tampilMasaKepengurusan,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                      //  Toast.makeText(Kepengurusan.this, response, Toast.LENGTH_SHORT).show();

                        if (response.contains("1")) {
                            try {
                                JSONObject jsonObject;
                                jsonObject = new JSONObject(response);
                                JSONArray result = jsonObject.getJSONArray("Hasil");
                                DetailDataKepengurusanModelArraylist = new ArrayList<>();


                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject c = result.getJSONObject(i);
                                    String nama = c.getString("nama");
                                    String jabatan = c.getString("nama_jabatan");
                                    String devisi= c.getString("nama_devisi");
                                    String masa_bakti = c.getString("masa_bakti");
                                    String id_alumni = c.getString("id_alumni");
                                    DetailDataKepengurusanModelArraylist.add(new DetailDataKepengurusanModel("Nama : " + nama, "Jabatan : " + jabatan, "Devisi : " + devisi, "Masa Bakti: " + masa_bakti, id_alumni));
                                }

                                adapter = new AdapterKepengurusan(DetailDataKepengurusanModelArraylist, Kepengurusan.this);
                                recycler3.setAdapter(adapter);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else {
                            pDialog.dismiss();
                            Toast.makeText(Kepengurusan.this, "Tidak Ada", Toast.LENGTH_SHORT).show();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(Kepengurusan.this, "Tidak Terhubung Ke Server", Toast.LENGTH_SHORT).show();
                        Log.d("volley error", error.getMessage());
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put(key_id_lembaga_alumni,idV);
                params.put(key_masabakti,MasaBakti);
//                params.put("id", id);
//                params.put("status", status);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(Kepengurusan.this);
        requestQueue.add(stringRequest);
    }
    private void tampilMasaBaktiloadFksJ(String idL) {
        pDialog = new ProgressDialog(Kepengurusan.this);
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        String url = tampil_masa_bakti;
        StringRequest stringRequest = new StringRequest(Request.Method.POST,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int sukses;
                try {
                    pDialog.dismiss();
                    JSONObject jsonObject = new JSONObject(response);
                    Log.d("ini coba", response);
                    sukses = jsonObject.getInt("success");
                    arrayDataMasaBakti.clear();
                    listID_masabakti.clear();
                    if (sukses == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("Hasil");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            arrayDataMasaBakti.add(object.getString("masa_bakti"));
                            listID_masabakti.add(object.getString("id_struktur"));

                        }
                        //Log.d("Hahaha", response);
                        pDialog.dismiss();
                        ArrayAdapter<String> adap = new ArrayAdapter<String>(Kepengurusan.this, R.layout.support_simple_spinner_dropdown_item, arrayDataMasaBakti);
                        spin_masa_Bakti.setAdapter(adap);
                    } else {
                        pDialog.dismiss();
                        Toast.makeText(Kepengurusan.this, "Data Belum Ada", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    pDialog.dismiss();
                    Toast.makeText(Kepengurusan.this, "Data Belum Ada", Toast.LENGTH_SHORT).show();

                    Log.d("Data Belum Ada", e.getMessage());
                }
                pDialog.dismiss();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pDialog.dismiss();
                Toast.makeText(Kepengurusan.this, "Tidak Terhubung Ke Server", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> mapp = new HashMap<>();
                mapp.put(key_id_lembaga_alumni, idL );
                return mapp;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
