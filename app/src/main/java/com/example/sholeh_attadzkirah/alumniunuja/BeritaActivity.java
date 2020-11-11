/*
 * Copyright (c) Muhammad Solehudin
 */

package com.example.sholeh_attadzkirah.alumniunuja;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sholeh_attadzkirah.alumniunuja.config.koneksi;
import com.example.sholeh_attadzkirah.alumniunuja.mRecycler.Model_Berita;
import com.example.sholeh_attadzkirah.alumniunuja.mRecycler.adapter_berita;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.key_id_lembaga_alumni;
import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.key_jenis_kegiatan;
import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.key_masabakti;
import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.tampil_itemBerita;
import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.tampil_masa_bakti;

public class BeritaActivity extends AppCompatActivity {


    ArrayList<Model_Berita> daftar_item = new ArrayList<Model_Berita>();
    adapter_berita adapter_berita;
    RecyclerView recycler;
    View v;

    Toolbar  toolBarisi;

    private ProgressDialog pDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_berita);
        toolBarisi =  findViewById(R.id.toolbar);
//        toolBarisi.setTitle("Berita");
        setSupportActionBar(toolBarisi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        recycler= findViewById(R.id.rvBerita);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recycler.setLayoutManager(layoutManager);

       // get_id_lembaga_alumni, get Jenis Kegiatan nya
        tampil_item(getIntent().getStringExtra("id_lembaga_alumni"),getIntent().getStringExtra("jenis_kegiatan"));
//        Toast.makeText(getApplication(),getIntent().getStringExtra("id_lembaga_alumni")+"  "+getIntent().getStringExtra("jenis_kegiatan"),Toast.LENGTH_SHORT).show();


    }

    @Override
    public boolean onSupportNavigateUp(){
        finish(); //  kalau tanpa ini tombol panahnya tak berfungsi
        return true;
    }


    private void tampil_item(String idL, String jk) {
        pDialog = new ProgressDialog(BeritaActivity.this);
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        String url = tampil_itemBerita;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//                        Toast.makeText(getApplication(), "RESPON"+response, Toast.LENGTH_SHORT).show();
                        if(response.contains("1")) {
                            try {
                                pDialog.dismiss();
                                JSONObject jsonObject;
                                jsonObject = new JSONObject(response);
                                JSONArray result = jsonObject.getJSONArray("Hasil");

                                daftar_item.clear();
                                for (int i = 0; i < result.length(); i++) {

                                    JSONObject c = result.getJSONObject(i);
                                    String id = c.getString("id_kegiatan");
                                    String judul = c.getString("judul_kegiatan");
                                    String tanggal = c.getString("tanggal_posting");
                                    String image = c.getString("foto_kegiatan");
//                                    String deskrip = c.getString("deskripsi");
                                    String id_lembaga = c.getString("id_lembaga_alumni");

                                    String  title = c.getString("jenis_kegiatan");
                                    toolBarisi.setTitle(title);


                                    daftar_item.add(new Model_Berita(id, judul, tanggal, image, id_lembaga));

                                }
                                pDialog.dismiss();
                                adapter_berita = new adapter_berita(daftar_item);
                                recycler.setAdapter(adapter_berita);
                            } catch (JSONException e) {
                                pDialog.dismiss();
                                Toast.makeText(getApplication(), "Belum Ada Berita", Toast.LENGTH_SHORT).show();
                                e.printStackTrace();
                            }

                        }else{
                            pDialog.dismiss();
                            Toast.makeText(getApplication(), "Tidak ada", Toast.LENGTH_SHORT).show();
                        }
                        pDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Toast.makeText(getApplication(), "Tidak terhubung ke server", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplication(),error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(key_id_lembaga_alumni,idL);
                params.put(key_jenis_kegiatan,jk);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
