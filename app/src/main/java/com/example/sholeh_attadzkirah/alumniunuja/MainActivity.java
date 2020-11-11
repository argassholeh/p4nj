/*
 * Copyright (c) Muhammad Solehudin
 */

package com.example.sholeh_attadzkirah.alumniunuja;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.example.sholeh_attadzkirah.alumniunuja.config.koneksi;
import com.example.sholeh_attadzkirah.alumniunuja.mFragment.AkunFragment;
import com.example.sholeh_attadzkirah.alumniunuja.mFragment.HomeFrgament;
import com.example.sholeh_attadzkirah.alumniunuja.mFragment.InboxFragment;
import com.example.sholeh_attadzkirah.alumniunuja.mFragment.PesananFragment;

import com.google.firebase.iid.FirebaseInstanceId;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.accountswitcher.AccountHeader;
import com.roughike.bottombar.BottomBar;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;

import java.util.ArrayList;
import java.util.HashMap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.key_id_alumni;


public class MainActivity extends AppCompatActivity {

    private BottomBar bottomBar;

    private Drawer.Result navigationDrawerLeft;
    private AccountHeader.Result headerNavigationLeft;
    private TextView mTextMessage;
    BottomNavigationView navigation;
    Button btnP4nj, btnNjic, btnSumbangan, btnBelanja, btnLainnya;
    Toolbar toolbar;


    AHBottomNavigation bottomNavigation;

    RecyclerView list;
    RecyclerView.LayoutManager layoutManager;
    Preferences preferences;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        preferences = new Preferences(getApplication());
        cekPermission();
//        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

//        if(refreshedToken != null){
//            sendRegistrationToServer(refreshedToken);
//        }

        HomeFrgament homeFrgament = new HomeFrgament();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main_id, homeFrgament).commit();


        mTextMessage = findViewById(R.id.message);
        navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        HomeFrgament homeFrgament = new HomeFrgament();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_main_id, homeFrgament).commit();
                        return true;
                    case R.id.navigation_pesanan:
                        PesananFragment pesananFragment = new PesananFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_main_id, pesananFragment).commit();
                        return true;
                    case R.id.navigation_inbox:
                        InboxFragment inboxFragment = new InboxFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_main_id, inboxFragment).commit();
                        return true;
                    case R.id.navigation_mydata:
                        AkunFragment akunFragment = new AkunFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.content_main_id, akunFragment).commit();
                        return true;


                    default:
                        break;
                }
                return false;
            }
        });
     }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //  getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }

    public void cekPermission(){
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.INTERNET)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.INTERNET}, 1);
        }
    }

    private void sendRegistrationToServer(String token) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, koneksi.simpanToken,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //untuk menerima respon ketika berhasil / tidak berhasil pada php.

                        if (response.contains("1")) {
                          //  Toast.makeText(MainActivity.this, "Token Berhasil", Toast.LENGTH_LONG).show();

                        } else {
                          //  Toast.makeText(MainActivity.this, "Token Gagal", Toast.LENGTH_LONG).show();

                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(MainActivity.this, "Gagal Token", Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //untuk mengirim request pada php
                params.put( key_id_alumni, preferences.getUsername());
                params.put("token_device", token);

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

}



