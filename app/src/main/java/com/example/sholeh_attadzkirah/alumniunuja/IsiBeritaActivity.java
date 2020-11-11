/*
 * Copyright (c) Muhammad Solehudin
 */

package com.example.sholeh_attadzkirah.alumniunuja;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sholeh_attadzkirah.alumniunuja.config.koneksi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.key_id_kegiatan;
import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.key_id_lembaga_alumni;

public class IsiBeritaActivity extends AppCompatActivity {

    TextView judulnya, penulisnya;
    ImageView img;
    WebView web;
    String HTML;
    String teksberita;
    String id;
    String idlembaga_alumni;

    String image;
    String judul;

    RecyclerView rv;
    // adpter_komentar adapter;
    ArrayList<HashMap<String, String>> tampil = new ArrayList<HashMap<String, String>>();
//    ArrayList<HashMap<String, String>> tampil_detail_berita = new ArrayList<HashMap<String, String>>();


    EditText nama;
    //  String q,w;
    Toolbar toolBarisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_berita);

        judulnya = findViewById(R.id.judulnya);
        penulisnya = findViewById(R.id.penulis);
        web = findViewById(R.id.web);
        img = findViewById(R.id.img1);
        toolBarisi=  findViewById(R.id.toolbar);
        setSupportActionBar(toolBarisi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true); // untuk back (panah)

        WebSettings setting = web.getSettings();
        setting.setJavaScriptEnabled(true);
        web.setWebViewClient(new WebViewClient());

        HTML = "<html><body>%s</body></html>";


        cari_isi(getIntent().getStringExtra("id kegiatan"),getIntent().getStringExtra("id lembaga alumni"));
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish(); //  kalau tanpa ini tombol panahnya tak berfungsi
        return true;
    }





    private void cari_isi(String idK, String idL ) {
        image = getIntent().getStringExtra("foto kegiatan");
        judul = getIntent().getStringExtra("judul kegiatan");
        Toast.makeText(this, "isi", Toast.LENGTH_SHORT).show();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, koneksi.tampil_isiBerita,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(IsiBeritaActivity.this, ""+response, Toast.LENGTH_SHORT).show();
                        if(response.contains("1")) {
                            try {
                                JSONObject jsonObject;
                                jsonObject = new JSONObject(response);
                                JSONArray result = jsonObject.getJSONArray("Hasil");
                                tampil.clear();
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject c = result.getJSONObject(i);
                                    String teks = c.getString("deskripsi");
                                    String  title = c.getString("jenis_kegiatan");
                                    toolBarisi.setTitle(title);
                                    teksberita = teks;
                                    judulnya.setText(judul);
                                    String penulis = c.getString("nama");
                                    penulisnya.setText(penulis);

                                    Glide.with(getApplicationContext()).load(image).apply(new RequestOptions().placeholder(R.drawable.no_image)).into(img);
                                    web.getSettings().getJavaScriptEnabled();
                                    web.loadData(String.format(HTML, teksberita),"text/html","utf-8");

                                    }
                            } catch (JSONException e) {
                                Toast.makeText(getApplicationContext(), "Data belum ada", Toast.LENGTH_SHORT).show();

                                e.printStackTrace();
                            }

                        }else{
                            Toast.makeText(getApplicationContext(), "Data belum ada", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Koneksi Gagal"+error.getMessage(), Toast.LENGTH_SHORT).show();
                        AlertDialog.Builder builder = new AlertDialog.Builder(IsiBeritaActivity.this);
                        builder.setMessage("Tidak dapat menyambung ke internet");
                        builder.setCancelable(false);

                        builder.setPositiveButton(
                                "Coba Lagi",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                        cari_isi(getIntent().getStringExtra("id kegiatan"),getIntent().getStringExtra("id lembaga alumni"));
                                    }
                                });

                        AlertDialog alert11 = builder.create();
                        alert11.show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(key_id_kegiatan,idK);
                params.put(key_id_lembaga_alumni,idL);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}

