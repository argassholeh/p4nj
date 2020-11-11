/*
 * Copyright (c) Muhammad Solehudin
 */

package com.example.sholeh_attadzkirah.alumniunuja;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

public class Soal extends AppCompatActivity implements View.OnClickListener {

    TextView soal1;
    RadioGroup rJawaban;
    RadioButton rPilihJawaban1,rPilihJawaban2,rPilihJawaban3,rPilihJawaban4 ;
    Button lanjut;

    /// tampilkan data berbentuk array
    ArrayList<HashMap<String, String>> tampilsoal = new ArrayList<HashMap<String, String>>();
    ArrayList<HashMap<String, String>> tampiljawaban = new ArrayList<HashMap<String, String>>();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_soal);
        soal1 = findViewById(R.id.soal1);
        rJawaban = findViewById(R.id.radiogroup1);
        rPilihJawaban1 = findViewById(R.id.jawab1);
        rPilihJawaban2 = findViewById(R.id.jawab2);
        rPilihJawaban3 = findViewById(R.id.jawab3);
        rPilihJawaban4 = findViewById(R.id.jawab4);
        lanjut = findViewById(R.id.btn_Lanjut);


        //menngatur rotasi pada tampilan data.
        LinearLayoutManager llm = new LinearLayoutManager(this);
        // tipe oriention ada vertical dan horizontal
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        koneksi.benar="";

        tampilkanSoal(koneksi.ids);
        lanjut.setOnClickListener(this);


    }



    public void tampilkanSoal (String ids){


        StringRequest stringRequest = new StringRequest(Request.Method.POST, koneksi.tampilSoal,

        new Response.Listener<String>() {

                 @Override
                    public void onResponse(String response) {
                        if(response.contains("1")) {
                            try {
//                                Toast.makeText(getApplicationContext(), "s", Toast.LENGTH_SHORT).show();

                                JSONObject jsonObject;
                                jsonObject = new JSONObject(response);
                                JSONArray result = jsonObject.getJSONArray("Hasil");
                                tampilsoal.clear();
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject c = result.getJSONObject(i);
                                    String id = c.getString("id_soal");
                                    soal1.setText(c.getString("pertanyaan"));

                                    koneksi.ids= "0";
                                    koneksi.ids = koneksi.ids+","+id;
                                    tampilkanJawab(id);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }else{
                            Toast.makeText(getApplicationContext(), "gagal", Toast.LENGTH_SHORT).show();
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
                params.put("id", ids);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
    public void tampilkanJawab (String id){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, koneksi.tampilJawab,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {
                        if(response.contains("1")) {
                            try {
                                JSONObject jsonObject;
                                jsonObject = new JSONObject(response);
                                JSONArray result = jsonObject.getJSONArray("Hasil");
                                tampiljawaban.clear();
                                koneksi.jawabanbenar.clear();
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject c = result.getJSONObject(i);
                                    HashMap<String, String> map = new HashMap<String, String>();
                                    map.put("jawab",c.getString("jawaban"));
                                    map.put("benar", c.getString("status"));
                                   koneksi.jawabanbenar.add(map);
                                    tampiljawaban.add(map);

                                }

                                rPilihJawaban1.setText(tampiljawaban.get(0).get("jawab").toString());
                                rPilihJawaban2.setText(tampiljawaban.get(1).get("jawab").toString());
                                rPilihJawaban3.setText(tampiljawaban.get(2).get("jawab").toString());
                                rPilihJawaban4.setText(tampiljawaban.get(3).get("jawab").toString());
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }else{
                            Toast.makeText(getApplicationContext(), "gagal", Toast.LENGTH_SHORT).show();
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
                params.put("id_soal",id);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }


    @Override
    public void onClick(View v) {
        lanjut();
    }
    public void lanjut (){
        if(rPilihJawaban1.isChecked()){
            if (tampiljawaban.get(0).get("benar").toString().equals("s")){
                koneksi.benar=koneksi.benar+"s";
            }else{
                koneksi.benar=koneksi.benar+"b";
            }
        }else if(rPilihJawaban2.isChecked()){
            if (tampiljawaban.get(1).get("benar").toString().equals("s")){
                koneksi.benar=koneksi.benar+"s";
            }else{
                koneksi.benar=koneksi.benar+"b";
            }
        }else if(rPilihJawaban3.isChecked()){
            if (tampiljawaban.get(2).get("benar").toString().equals("s")){
                koneksi.benar=koneksi.benar+"s";
            }else{
                koneksi.benar=koneksi.benar+"b";
            }
        }else if(rPilihJawaban4.isChecked()){
            if (tampiljawaban.get(3).get("benar").toString().equals("s")){
                koneksi.benar=koneksi.benar+"s";
            }else{
                koneksi.benar=koneksi.benar+"b";
            }
        }
          Intent intent = new Intent(getApplicationContext(),Soal2.class);
          startActivity(intent);
          finish();
    }
}
