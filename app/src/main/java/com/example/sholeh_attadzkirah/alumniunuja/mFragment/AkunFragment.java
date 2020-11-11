/*
 * Copyright (c) Muhammad Solehudin
 */

package com.example.sholeh_attadzkirah.alumniunuja.mFragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sholeh_attadzkirah.alumniunuja.Add;
import com.example.sholeh_attadzkirah.alumniunuja.DetailPengurus;
import com.example.sholeh_attadzkirah.alumniunuja.GlidModuleMe;
import com.example.sholeh_attadzkirah.alumniunuja.GlideApp;
import com.example.sholeh_attadzkirah.alumniunuja.ImageProfile;
import com.example.sholeh_attadzkirah.alumniunuja.Kepengurusan;
import com.example.sholeh_attadzkirah.alumniunuja.Preferences;
import com.example.sholeh_attadzkirah.alumniunuja.SignIn;
import com.example.sholeh_attadzkirah.alumniunuja.ViewAkun;
import com.example.sholeh_attadzkirah.alumniunuja.config.koneksi;
import com.example.sholeh_attadzkirah.alumniunuja.R;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.key_id_alumni;
import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.tampilDataAkun;
import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.tampil_masa_bakti;


public class AkunFragment extends Fragment implements View.OnClickListener {
    FloatingActionMenu materialDesignFAM;
    FloatingActionButton floatingActionButton1, floatingActionButton2, floatingActionButton3, floatingActionButton4;

    Button btnEditProfile, Exit;
    ListView listView;
    Preferences preferences;
    CircleImageView cimgProfile;
    TextView tvNama, tvHp,tvPekerjaan, tvBidang, tvAkunFb, tvEmail, tvUser;
    ArrayList<HashMap<String, String>> tampil_akun = new ArrayList<HashMap<String, String>>();
    GlidModuleMe moduleMe = new GlidModuleMe();
    private ProgressDialog pDialog;

    @Override
    public void onResume()
    {
        tampilDataAkun(); // biar reload data atau refresh ketika sudah ganti foto profile
        super.onResume();
        // Load data and do stuff
    }
    @Nullable
    @Override
    public View onCreateView( LayoutInflater inflater, ViewGroup container,  Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_akun,container,false);

        btnEditProfile= rootView.findViewById(R.id.btn_EditProfil);
        btnEditProfile.setOnClickListener(this);

        tvNama = rootView.findViewById(R.id.tv_namaProfil);
        tvHp = rootView.findViewById(R.id.tv_NoHP_a);
        tvPekerjaan = rootView.findViewById(R.id.tv_Pekerjaan_a);
        tvBidang = rootView.findViewById(R.id.tv_BidangUsaha_A);
        tvAkunFb =rootView.findViewById(R.id.tvakunFb_a);
        tvEmail =rootView.findViewById(R.id.tv_Email_a);
        tvUser = rootView.findViewById(R.id.tv_UserName_a);
        Exit = rootView.findViewById(R.id.btn_exit_profile);
        cimgProfile = rootView.findViewById(R.id.imagecircle_profile);

        preferences = new Preferences(getActivity());
       // tampilDataAkun();

        materialDesignFAM =rootView.findViewById(R.id.floating_action_menu);
        floatingActionButton1 =rootView.findViewById(R.id.fb_tambah);



        if(!preferences.getSPAktif().equalsIgnoreCase("Y")){
            materialDesignFAM.setVisibility(View.GONE);
        }

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                //TODO something when floating action menu first item clicked
//                //untuk aksi ketika di klik
//                Toast.makeText(getActivity(), "1", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getActivity(), Add.class);
                startActivity(intent);
            }
        });

        cimgProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ImageProfile.class);
                startActivity(intent);
            }
        });

        Exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getActivity(), "Berhasil Keluar", Toast.LENGTH_LONG).show();
                exit();
            }
        });

        return rootView;

    }


    @Override
    public void onClick(View v) {
    editprofile();

    }

    public  void exit (){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Apakah anda yakin ingin keluar?");
        builder.setCancelable(true);
        builder.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                preferences.saveSPBoolean(preferences.SP_SUDAH_LOGIN, false);
                startActivity(new Intent(getActivity(), SignIn.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK));
                getActivity().finish();
                Toast.makeText(getActivity(), "Berhasil Keluar", Toast.LENGTH_LONG).show();
            }
        });
        builder.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    public void editprofile(){
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ViewAkun.class);
                getActivity().startActivity(intent);
            }
        });
    }
    public  void tampilDataAkun (){
        pDialog = new ProgressDialog(getActivity());
        pDialog.setMessage("Loading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
        String url = tampilDataAkun;
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
                                tampil_akun.clear();
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject c = result.getJSONObject(i);
                                    GlideApp.with(AkunFragment.this)
                                            .load(koneksi.tampilFotoDetailPengurus+c.getString("foto"))
                                            .placeholder(R.drawable.iconuser)
                                            .into(cimgProfile);
                                    tvNama.setText(c.getString("nama"));
                                    tvHp.setText(c.getString("telepon"));
                                    tvPekerjaan.setText(c.getString("pekerjaan"));
                                    tvBidang.setText(c.getString("bidang_usaha"));
                                    tvAkunFb.setText(c.getString("akun_fb"));
                                    tvEmail.setText(c.getString("email"));
                                    tvUser.setText(c.getString("username"));


                                }
                                pDialog.dismiss();

                            } catch (JSONException e) {
                                pDialog.dismiss();
                                e.printStackTrace();
                            }

                        }else{
                            pDialog.dismiss();
                            Toast.makeText(getActivity(), "Gagal", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pDialog.dismiss();
                        Toast.makeText(getActivity(), "Tidak Terhubung Ke Server" +error, Toast.LENGTH_SHORT).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put( key_id_alumni, preferences.getUsername());

                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }
}
