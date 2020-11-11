/*
 * Copyright (c) Muhammad Solehudin
 */

package com.example.sholeh_attadzkirah.alumniunuja;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.key_id_lembaga_alumni;

public class InfoKegiatan extends AppCompatActivity {

    TextView tvxJudul,tvxDeskripsi, tvxInfoKegiatan;
    ImageView imgInfo ;
    ArrayList<HashMap<String, String>> tampil_kegiatan = new ArrayList<HashMap<String, String>>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_kegiatan);
        tvxJudul = findViewById(R.id.tv_judulnya);
        tvxDeskripsi = findViewById(R.id.tv_des);
        tvxInfoKegiatan = findViewById(R.id.tv_InfoKegiatan);
        imgInfo = findViewById(R.id.img_kegiatan);

        tampilKegiatan(getIntent().getStringExtra("Kegiatan"));
    }

   public void  tampilKegiatan(String idK) {
       StringRequest stringRequest = new StringRequest(Request.Method.POST, koneksi.tampilKegiatan,
               new Response.Listener<String>() {
                   @Override
                   public void onResponse(String response) {
                       if(response.contains("1")) {
                           try {

                               JSONObject jsonObject;
                               jsonObject = new JSONObject(response);
                               JSONArray result = jsonObject.getJSONArray("Hasil");
                               tampil_kegiatan.clear();
                               for (int i = 0; i < result.length(); i++) {
                                   JSONObject c = result.getJSONObject(i);

                                   tvxJudul.setText(c.getString("judul_kegiatan"));
                                   tvxDeskripsi.setText(c.getString("deskripsi"));
                                   tvxInfoKegiatan.setText(c.getString("jenis_kegiatan"));
                               }

                           } catch (JSONException e) {
                               e.printStackTrace();
                           }

                       }else{
                           Toast.makeText(getApplication(),"gagal"+ response, Toast.LENGTH_SHORT).show();
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
               params.put( key_id_lembaga_alumni, idK);
               return params;
           }
       };
       RequestQueue requestQueue = Volley.newRequestQueue(this);
       requestQueue.add(stringRequest);
   }
}
