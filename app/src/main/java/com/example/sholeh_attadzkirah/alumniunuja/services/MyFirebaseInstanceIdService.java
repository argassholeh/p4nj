/*
 * Copyright (c) Muhammad Solehudin
 */

package com.example.sholeh_attadzkirah.alumniunuja.services;

import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.sholeh_attadzkirah.alumniunuja.Preferences;
import com.example.sholeh_attadzkirah.alumniunuja.config.koneksi;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.HashMap;
import java.util.Map;

import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.key_id_alumni;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {

//    private static final String TAG = "MyFirebaseIIDService";
    private static final String TAG = "MyFirebaseIdService";
    private static final String TOPIC_GLOBAL = "global";
    Preferences preferences;

    @Override
    public void onTokenRefresh() {
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d(TAG, "Refreshed token: " + refreshedToken);
        preferences = new Preferences(getApplication());
        // now subscribe to `global` topic to receive app wide notifications
        FirebaseMessaging.getInstance().subscribeToTopic(TOPIC_GLOBAL);

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String token) {
//        String android_id = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, koneksi.simpanToken,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            //untuk menerima respon ketika berhasil / tidak berhasil pada php.
                            if (response.contains("1")) {
                              //  Toast.makeText(MyFirebaseInstanceIdService.this, "FToken Berhasil", Toast.LENGTH_LONG).show();
                            } else {
                              //  Toast.makeText(MyFirebaseInstanceIdService.this, "FToken Gagal", Toast.LENGTH_LONG).show();
                            }
                          }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            //berfungsi memberi tahu jika ada kesalahan pada server atau koneksi aplikasi.
//                            Toast.makeText(MyFirebaseInstanceIdService.this, "Gagal Token", Toast.LENGTH_LONG).show();
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