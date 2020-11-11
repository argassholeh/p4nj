/*
 * Copyright (c) Muhammad Solehudin
 */

package com.example.sholeh_attadzkirah.alumniunuja;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.sholeh_attadzkirah.alumniunuja.config.koneksi;
import com.example.sholeh_attadzkirah.alumniunuja.mExpand.Child;
import com.example.sholeh_attadzkirah.alumniunuja.mExpand.ExpandListAdapter;
import com.example.sholeh_attadzkirah.alumniunuja.mExpand.Group;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.key_id_lembaga_alumni;

public class Njic extends AppCompatActivity implements BaseSliderView.OnSliderClickListener{

    ListView listView;
    Intent intent;

    private ExpandableListAdapter expandableListAdapter;
    private ArrayList<Group> groupExpandList;
    private ExpandableListView expandableListView;
    //mendeklarasikan object Expandable ListAdapter ,ArrayList dan ExpandableListView

    Toolbar  toolBarisi;

    SliderLayout mDemoSlider;
    HashMap<String, String> file_maps = new HashMap<String, String>();
    String foto, judul;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_njic);
        toolBarisi =  findViewById(R.id.toolbar);
        toolBarisi.setTitle("");
        setSupportActionBar(toolBarisi);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mDemoSlider = findViewById(R.id.slidernjic);
        tampilSlide(getIntent().getStringExtra("slide"));


        listView = findViewById(R.id.listviewNjic);
        ArrayAdapter<String> mAdapter = new ArrayAdapter<String>(Njic.this,
                android.R.layout.simple_list_item_1,
                getResources().getStringArray(R.array.item_menu));

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                if (position == 0) {
                    intent = new Intent(Njic.this, VisiMisi.class);
                    intent.putExtra("lembaga", "3");
                    startActivity(intent);
                } else if (position == 1) {
                    intent = new Intent(Njic.this, Kepengurusan.class);
                    intent.putExtra("Pengurus", "3");
                    startActivity(intent);
                 }
            }
        });
        listView.setAdapter(mAdapter);
        ///////////////
        expandableListView = findViewById(R.id.expandlist_Njic);
        //menginisialisasi object dari container ExpandableListView yang berada di activity_main.xml

        groupExpandList = inputData();
        //memberikan nilai pada object groupExpandList berdasarkan method inputData()

        expandableListAdapter = new ExpandListAdapter(this, groupExpandList);
        //menginstansiasi object dari class Adapter ExpandListAdapter

        expandableListView.setAdapter(expandableListAdapter);
        //mensetting nilai berdasarkan  objectexpandableListAdapter
        // dari method di object  expandableListView

        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {

                String namagrup = groupExpandList.get(groupPosition).getNama();

                ArrayList<Child> childList = groupExpandList.get(groupPosition).getItem();

                String namachild = childList.get(childPosition).getNama();

                if (childPosition == 0) {
                    intent = new Intent(Njic.this, BeritaActivity.class);
                    intent.putExtra("id_lembaga_alumni", "3");
                    intent.putExtra("jenis_kegiatan", "aksi pengajian");
                    startActivity(intent);

                }else if (childPosition == 1){
                    intent = new Intent(Njic.this, BeritaActivity.class);
                    intent.putExtra("id_lembaga_alumni", "3");
                    intent.putExtra("jenis_kegiatan", "aksi umum");
                    startActivity(intent);

                }else if (childPosition == 2){
                    intent = new Intent(Njic.this, BeritaActivity.class);
                    intent.putExtra("id_lembaga_alumni", "3");
                    intent.putExtra("jenis_kegiatan", "aksi sosial");
                    startActivity(intent);

                }
                //method dari object  expandableListView yang dimana ketika di klik akan menampilkan
                //data dari Child yang ada di dalam Grup
                //serta menampilkan pesan text Toast

                return false;
            }

        });


        expandableListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(int groupPosition) {
//                String namagrup = groupExpandList.get(groupPosition).getNama();
//                tampilPesan(namagrup+" Di buka ");
                //method untuk membuka list data dari Child

            }
        });
        expandableListView.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
            @Override
            public void onGroupCollapse(int groupPosition) {
//                String namagrup = groupExpandList.get(groupPosition).getNama();
//                tampilPesan(namagrup+" Di tutup");
                //method untuk menutup list data dari Child
            }
        });
    }
    @Override
    public boolean onSupportNavigateUp(){
        finish(); //  kalau tanpa ini tombol panahnya tak berfungsi
        return true;
    }

    public ArrayList<Group> inputData() {
        //method untuk menginput data
        ArrayList<Group> groupArrayList = new ArrayList<Group>();
        ArrayList<Child> childArrayList ;

        //Setting Grup 1
        childArrayList = new ArrayList<Child>();
        Group grup1 = new Group();
        grup1.setNama("Info Kegiatan");

        Child kegiatan1 = new Child();
        kegiatan1.setNama("Pengajian");
        childArrayList.add(kegiatan1);

        Child kegiatan2 = new Child();
        kegiatan2.setNama("Umum");
        childArrayList.add(kegiatan2);

        Child kegiatan3 = new Child();
        kegiatan3.setNama("Sosial");
        childArrayList.add(kegiatan3);
        //mensetting data object childArrayList dari object grup1
        grup1.setItem(childArrayList);
        groupArrayList.add(grup1);
        return groupArrayList;
    }
    public void tampilPesan(String pesan){
    //method untuk menampilkan pesan text toast
        Toast.makeText(this, pesan, Toast.LENGTH_SHORT).show();
     }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }
    public  void tampilSlide(String idS){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, koneksi.tampilSlide,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("koceng", "onResponse: "+response);
                        if(response.contains("1")) {
                            try {
                                JSONObject jsonObject;
                                jsonObject = new JSONObject(response);
                                JSONArray result = jsonObject.getJSONArray("Hasil");
                                //file_maps.clear();
                                for (int i = 0; i < result.length(); i++) {
                                    JSONObject c = result.getJSONObject(i);
                                    foto = koneksi.tampilFotoItemBerita+c.getString("foto_kegiatan");
                                    judul= c.getString("judul_kegiatan");

                                    TextSliderView textSliderView = new TextSliderView(Njic.this);
                                    // initialize a SliderLayout
                                    textSliderView
                                            .description(judul)
                                            .image(foto)
                                            .setScaleType(BaseSliderView.ScaleType.Fit);
//                                          .setOnSliderClickListener(this);
                                    Log.d("kocor", "onResponse: "+judul);
                                    textSliderView.bundle(new Bundle());
                                    textSliderView.getBundle().putString("judul", judul);
                                    mDemoSlider.addSlider(textSliderView);
                                }
                                mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                                mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Top);
                                mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                                mDemoSlider.setDuration(5000);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }else{
                            Toast.makeText(getApplication(), response, Toast.LENGTH_SHORT).show();
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
                params.put(key_id_lembaga_alumni,idS);


                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(getApplication());
        requestQueue.add(stringRequest);
    }
}

