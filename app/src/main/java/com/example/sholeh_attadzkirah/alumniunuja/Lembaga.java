/*
 * Copyright (c) Muhammad Solehudin
 */

package com.example.sholeh_attadzkirah.alumniunuja;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
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

import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.simpanFotoProfile;
import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.tampilLembaganya;


public class Lembaga extends AppCompatActivity {

    ArrayList<String>tampilLembaga = new ArrayList<String>();
    ArrayList<String>tampilAlamatLembaga = new ArrayList<String>();

    ListView listLembaga;
    ArrayAdapter adapter;

    private ProgressDialog pDialog;
     Toolbar toolBarisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lembaga);
        toolBarisi=  findViewById(R.id.toolbar);
        toolBarisi.setTitle("Lembaga - Lembaga NJ ");
        setSupportActionBar(toolBarisi);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // menampilkan panah untuk kembali ke halaman sebelumnya
        // yg di ovveride onSupportNavigateUp() sehingga ketika di klik panahnya hilang (finish)
//        getSupportActionBar().setTitle("Lembaga - Lembaga NJ");


        //menngatur rotasi pada tampilan data.
        LinearLayoutManager llm = new LinearLayoutManager(this);
        // tipe oriention ada vertical dan horizontal
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        listLembaga = findViewById(R.id.list_lembaga);
        tampilLembaga();
        koneksi.namaLembaga="";
        listLembaga.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent it = new Intent(Lembaga.this,viewWebLembaga.class);
                it.putExtra("alamat", tampilAlamatLembaga.get(position).toString());
                startActivity(it);


            }
        });
    }

    @Override
    public boolean onSupportNavigateUp(){
        finish(); // agar activity ini tertutup ketika di tekan tanda panah dan kembali ke activity sebelumnya
        return true;
    }


    public void tampilLembaga(){
            pDialog = new ProgressDialog(Lembaga.this);
            pDialog.setMessage("Loading...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
            String url = tampilLembaganya;
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if(response.contains("1")) {
                            try {
                                pDialog.dismiss();
                                JSONObject jsonObject;
                                jsonObject = new JSONObject(response);
                                JSONArray result = jsonObject.getJSONArray("Hasil");
                                pDialog.dismiss();
                                tampilLembaga.clear();
                                tampilAlamatLembaga.clear();
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject c = result.getJSONObject(i);
                                    String lembaga= c.getString(koneksi.key_lembaga);
                                    String alamatLembaga= c.getString(koneksi.key_alamatLembaga);
                                    tampilLembaga.add(lembaga);
                                    tampilAlamatLembaga.add(alamatLembaga);
                                }
                                adapter = new ArrayAdapter(Lembaga.this,R.layout.costum_viewlembaga,tampilLembaga);
                                listLembaga.setAdapter(adapter);
                                pDialog.dismiss();


                            } catch (JSONException e) {
                                e.printStackTrace();
                                pDialog.dismiss();

                            }

                        }else{
                            Toast.makeText(getApplicationContext(), "Tidak ada", Toast.LENGTH_SHORT).show();
                            pDialog.dismiss();
                        }
                        pDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Tidak Terhubung ke server", Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
