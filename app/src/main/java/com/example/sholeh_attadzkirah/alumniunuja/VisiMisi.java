/*
 * Copyright (c) Muhammad Solehudin
 */

package com.example.sholeh_attadzkirah.alumniunuja;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sholeh_attadzkirah.alumniunuja.config.koneksi;

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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.key_id_lembaga_alumni;
import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.tampilLembaganya;
import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.tampilVisiMisiFksJ;

public class VisiMisi extends AppCompatActivity {
    TextView tvVisi, tvMisi;
    ArrayList<HashMap<String, String>> tampil_visimisi = new ArrayList<HashMap<String, String>>();
    WebView webvisi, webmisi;
    String HTML;
    String teksnya1, teksnya2;
    Toolbar toolBarisi;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visi_misi);
         toolBarisi=  findViewById(R.id.toolbar);
         setSupportActionBar(toolBarisi);
         getSupportActionBar().setDisplayHomeAsUpEnabled(true); // untuk back (panah)
         // judul dari halaman main activity
        webvisi = findViewById(R.id.web_visi);
        webmisi = findViewById(R.id.web_misi);
        WebSettings setting = webvisi.getSettings();
        setting.setJavaScriptEnabled(true);
        webvisi.setWebViewClient(new WebViewClient());
        WebSettings setting2 = webmisi.getSettings();
        setting2.setJavaScriptEnabled(true);
        webmisi.setWebViewClient(new WebViewClient());
        HTML = "<html><body>%s</body></html>";
        tampilkanVisiMisi (getIntent().getStringExtra("lembaga"));
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish(); //  kalau tanpa ini tombol panahnya tak berfungsi
        return true;
    }

    public  void tampilkanVisiMisi (String idV){
        pDialog = new ProgressDialog(VisiMisi.this);
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        String url = tampilVisiMisiFksJ;
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
                                Log.d("haha",response);
                                tampil_visimisi.clear();

                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject c = result.getJSONObject(i);
                                    String teksvisi = c.getString("visi");
                                    String teksmisi = c.getString("misi");
                                    String  title = c.getString("nama_lembaga");
                                    toolBarisi.setTitle(title);

//                                    tvVisi.setText(c.getString("visi"));
//                                    tvMisi.setText(c.getString("misi"));


                                    teksnya1 = teksvisi;
                                    teksnya2 = teksmisi;
                                    webvisi.getSettings().getJavaScriptEnabled();
                                    webvisi.loadData(String.format(HTML, teksnya1),"text/html","utf-8");

                                    webmisi.getSettings().getJavaScriptEnabled();
                                    webmisi.loadData(String.format(HTML, teksnya2),"text/html","utf-8");


                                }
                                pDialog.dismiss();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                pDialog.dismiss();
                            }

                        }else{
                            Toast.makeText(VisiMisi.this, "Tidak ada", Toast.LENGTH_SHORT).show();
                            pDialog.dismiss();
                        }
                        pDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(VisiMisi.this, "Tidak terhubung ke server", Toast.LENGTH_SHORT).show();
                        pDialog.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put(key_id_lembaga_alumni,idV);
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}
