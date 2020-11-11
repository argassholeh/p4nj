/*
 * Copyright (c) Muhammad Solehudin
 */

package com.example.sholeh_attadzkirah.alumniunuja;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import com.example.sholeh_attadzkirah.alumniunuja.config.koneksi;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;

import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.akanLogin;
import static com.example.sholeh_attadzkirah.alumniunuja.config.koneksi.simpanAlumni;

public class IsiData extends AppCompatActivity {


    Button Btnsimpan, btnFotoUsaha;

    EditText ed_noktp, ed_nama, ed_alamat, ed_desa, ed_kecamatan,
            ed_hp, ed_thn_mndook, ed_thn_keluarmndok, ed_pendidikan,ed_pekerjaan, ed_NamaUsaha,
            ed_bidangUsaha, ed_AkunFb, ed_Email, ed_UserName, ed_password;
    ImageView ivFotoUsaha;
    Bitmap bitmap = null;
    Uri imageUri;
    private static final int PICK_IMAGE = 1;
    private static final int PICK_Camera_IMAGE = 2;

    RadioGroup rg_Status;
    RadioButton rb_Aktif, rb_TidakAktif;
    String status;

    Spinner spindesa, spinkec, point;
    ArrayList<String> arrayDataKec = new ArrayList<>();
    ArrayList<String> listID_kec = new ArrayList<>();

    private ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> listID_des = new ArrayList<>();

    private ProgressDialog progres;

    adapterspinner adapterspinner;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_isi_data);
        ed_noktp = findViewById(R.id.edNoKtp);
        ed_nama = findViewById(R.id.edNama);
        spindesa = findViewById(R.id.spin_desa);
        spinkec = findViewById(R.id.spin_kec);
        ed_alamat= findViewById(R.id.edAlamat);
        ed_hp = findViewById(R.id.edNoHP);
        ed_thn_mndook = findViewById(R.id.edThunMondok);
        ed_thn_keluarmndok = findViewById(R.id.edThnKeluar);
        ed_pendidikan= findViewById(R.id.edPendidikan);
        ed_pekerjaan = findViewById(R.id.edPekerjaan);
        ed_NamaUsaha = findViewById(R.id.edNamaUsaha);
        ed_bidangUsaha  = findViewById(R.id.edBidangUsaha);
        ed_AkunFb = findViewById(R.id.edAkunFb);
        ed_Email = findViewById(R.id.edEmail);
        ed_UserName  = findViewById(R.id.edUser);
        ed_password  = findViewById(R.id.edPassword);
        ivFotoUsaha = findViewById(R.id.img_ftousaha);

        perizinan();
        spinkec.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getBaseContext(), listID_kec.get(position) + " selected", Toast.LENGTH_LONG).show();
//                tampilDataDesa(listID_kec.get(position));
                if (spinkec.getSelectedItem().equals("Pilih Kecamatan")){
//                     Toast.makeText(getBaseContext(), listID_kec.get(position) + " terpilih", Toast.LENGTH_LONG).show();
                }else{
                    tampilDataDesa(listID_kec.get(position));
                    // Toast.makeText(getBaseContext(), listID_kec.get(position) + " terpilih", Toast.LENGTH_LONG).show();
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spindesa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                tampilPoint(listID_jen.get(position));
//                Toast.makeText(getApplicationContext(), listID_des.get(position) + "selected", Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        tampilKecload();

        Btnsimpan = findViewById(R.id.btnSimpan);
        Btnsimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                simpan();
//                finish();
//                kosong();
            }
        });
        btnFotoUsaha = findViewById(R.id.btnftoUsaha);
        btnFotoUsaha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

    }

    @Override
    public void onBackPressed() {
       keluar();
    }

    @Override
    protected void onResume() {
        super.onResume();
        AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
            @Override
            public void onSuccess(Account account) {
                ed_hp.setText(String.format(account.getPhoneNumber() == null ? "" : account.getPhoneNumber().toString()));
            }

            @Override
            public void onError(AccountKitError accountKitError) {

            }
        });
    }

    public void keluar(){
        AlertDialog.Builder build = new AlertDialog.Builder(IsiData.this);
        build.setMessage("Apakah Anda yakin ingin keluar ? ");
        build.setPositiveButton("Ya", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(IsiData.this, SignIn.class);
                startActivity(intent);
                finish();
            }
        });

        build.setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        build.create().show();
    }

    public  void  tampilKecload(){
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.POST,  koneksi.tampilkec, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {

                            for (int i = 0; i < response.length(); i++) {
                                JSONObject data = response.getJSONObject(i);
                                arrayDataKec.add(data.getString("nama_kecamatan"));
                                listID_kec.add(data.getString("id_kecamatan"));
                            }
//                            ArrayAdapter<String> adap = new ArrayAdapter<String>(IsiData.this, R.layout.support_simple_spinner_dropdown_item, arrayDataKec);
//                            spinkec.setAdapter(adap);
                            adapterspinner = new adapterspinner(IsiData.this, R.layout.support_simple_spinner_dropdown_item);
                            adapterspinner.addAll(arrayDataKec);
                            adapterspinner.add("Pilih Kecamatan");
                            spinkec.setAdapter(adapterspinner);
                            spinkec.setSelection(adapterspinner.getCount());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },


                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(IsiData.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    private void tampilDataDesa(String idkat) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, koneksi.tampildesa, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int sukses;
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    sukses = jsonObject.getInt("success");
                    arrayList.clear();
                    listID_des.clear();
                    if (sukses == 1) {
                        JSONArray jsonArray = jsonObject.getJSONArray("Hasil");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject object = jsonArray.getJSONObject(i);
                            arrayList.add(object.getString("nama_desa"));
                            listID_des.add(object.getString("id_desa"));
                            //Toast.makeText(IsiData.this, response, Toast.LENGTH_SHORT).show();
                        }
                        Log.d("Hahaha", response);
//                        ArrayAdapter<String> adap = new ArrayAdapter<String>(IsiData.this, R.layout.support_simple_spinner_dropdown_item, arrayList);
//                        spindesa.setAdapter(adap);
                        adapterspinner = new adapterspinner(IsiData.this, R.layout.support_simple_spinner_dropdown_item);
                        adapterspinner.addAll(arrayList);
                        adapterspinner.add("Pilih Desa");
                        spindesa.setAdapter(adapterspinner);
                        spindesa.setSelection(adapterspinner.getCount());
                    } else {
                        Toast.makeText(IsiData.this, "Data Belum Ada", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Log.d("Data Belum Ada", e.getMessage());
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(IsiData.this, "Eror Cari " + error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> mapp = new HashMap<>();
                mapp.put("id_kecamatan", idkat);
                return mapp;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void perizinan() {
        ActivityCompat.requestPermissions(IsiData.this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.CAMERA},
                99);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 99: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                } else {
                    perizinan();
                }
                return;
            }
        }
    }

    private void selectImage() {
        final CharSequence[] options = {"Ambil Foto", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(
                IsiData.this);
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (options[item].equals("Ambil Foto")) {
                    String fileName = "new-photo-name.jpg";
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, fileName);
                    values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");
                    imageUri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                    intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                    startActivityForResult(intent, PICK_Camera_IMAGE);

                } else if (options[item].equals("Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, PICK_IMAGE);
                }
            }
        });
        builder.show();
    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Uri selectedImageUri = null;
        String filePath = null;
        switch (requestCode) {
            case PICK_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    selectedImageUri = data.getData();
                }
                break;
            case PICK_Camera_IMAGE:
                if (resultCode == RESULT_OK) {
                    selectedImageUri = imageUri;
                } else if (resultCode == RESULT_CANCELED) {
                    Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "Picture was not taken", Toast.LENGTH_SHORT).show();
                }
                break;
        }

        if (selectedImageUri != null) {
            try {
                String filemanagerstring = selectedImageUri.getPath();
                String selectedImagePath = getPath(selectedImageUri);

                if (selectedImagePath != null) {
                    filePath = selectedImagePath;
                } else if (filemanagerstring != null) {
                    filePath = filemanagerstring;
                } else {
                    Toast.makeText(IsiData.this, "Unknown path",
                            Toast.LENGTH_LONG).show();
                    Log.e("Bitmap", "Unknown path");
                }

                if (filePath != null) {
                    decodeFile(filePath);
                } else {
                    bitmap = null;
                }
            } catch (Exception e) {
                Toast.makeText(IsiData.this, "Internal error",
                        Toast.LENGTH_LONG).show();
                Log.e(e.getClass().getName(), e.getMessage(), e);
            }
        }

    }

    public String getPath(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;
    }

    public void decodeFile(String filePath) {
        // Decode image size
        BitmapFactory.Options o = new BitmapFactory.Options();
        o.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, o);
        final int REQUIRED_SIZE = 1024;
        int width_tmp = o.outWidth, height_tmp = o.outHeight;
        int scale = 1;
        while (true) {
            if (width_tmp < REQUIRED_SIZE && height_tmp < REQUIRED_SIZE)
                break;
            width_tmp /= 2;
            height_tmp /= 2;
            scale *= 2;
        }
        BitmapFactory.Options o2 = new BitmapFactory.Options();
        o2.inSampleSize = scale;
        bitmap = BitmapFactory.decodeFile(filePath, o2);
        ivFotoUsaha.setImageBitmap(bitmap);

    }

    public String getStringImage(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 90, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }


    private void simpan() {
        final String noktp__ = ed_noktp.getText().toString();
        final String nama_ = ed_nama.getText().toString();
        final String idkec_ = listID_kec.get(spinkec.getSelectedItemPosition());
        final String iddesa_ = listID_des.get(spindesa.getSelectedItemPosition());
        final String alamat_ = ed_alamat.getText().toString();
        final String telepon_ = ed_hp.getText().toString();
        final String tahunmondok_ = ed_thn_mndook.getText().toString();
        final String tahunkeluar_ = ed_thn_keluarmndok.getText().toString();
        final String pendidikan_ = ed_pendidikan.getText().toString();
        final String pekerjaan_ = ed_pekerjaan.getText().toString();
        final String namausaha_ = ed_NamaUsaha.getText().toString();
        final String bidangusaha_ = ed_bidangUsaha.getText().toString();
        final String akunfb_ = ed_AkunFb.getText().toString();
        final String email_ = ed_Email.getText().toString();
        final String username_ = ed_UserName.getText().toString();
        final String pass_ = ed_password.getText().toString();

        if (!validasi()) return;

        progres = new ProgressDialog(this);
        progres.setMessage("Proses...");
        progres.show();
        String url = simpanAlumni;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //untuk menerima respon ketika berhasil / tidak berhasil pada php.
//                        Log.d("ini error", response);
                        if (response.equalsIgnoreCase("0")) {
//                            Toast.makeText(IsiData.this, "Username sudah ada yang pakai.
                            Toast.makeText(IsiData.this, "Username atau Nomor Telepon Akun Anda Sudah Terdaftar", Toast.LENGTH_SHORT).show();
                            progres.dismiss();


                        }else{
                            progres.dismiss();
                            Toast.makeText(IsiData.this, "Berhasil", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(),Login.class);
                            startActivity(intent);

                            finish();

                        }
                        progres.dismiss();

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //berfungsi memberi tahu jika ada kesalahan pada server atau koneksi aplikasi.
//                        Toast.makeText(IsiData.this, "Tidak terhubung ke server", Toast.LENGTH_SHORT).show();
                        Toast.makeText(IsiData.this, "Pilih foto usaha terlebih dahulu", Toast.LENGTH_LONG).show();
//                        Toast.makeText(IsiData.this,"D"+ error, Toast.LENGTH_LONG).show();
                        progres.dismiss();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                //untuk mengirim request pada php
                params.put(koneksi.key_noktp,noktp__);
                params.put(koneksi.key_nama,nama_);
                params.put(koneksi.key_kecamatan,idkec_);
                params.put(koneksi.key_desa,iddesa_);
                params.put(koneksi.key_alamat,alamat_);
                params.put(koneksi.key_telepon,telepon_);
                params.put(koneksi.key_thn_mondok,tahunmondok_);
                params.put(koneksi.key_thn_keluar,tahunkeluar_);
                params.put(koneksi.key_pendidikan_terakhir,pendidikan_);
                params.put(koneksi.key_pekerjaan,pekerjaan_);
                params.put(koneksi.key_namausaha,namausaha_);
                params.put(koneksi.key_bidang_usaha,bidangusaha_);
                params.put(koneksi.key_akun,akunfb_);
                params.put(koneksi.key_username,username_);
                params.put(koneksi.key_email,email_);
                params.put(koneksi.key_password,pass_);
                params.put(koneksi.key_fotousaha, getStringImage(bitmap));
                return params;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private boolean validasi() {
        boolean valid = true;

        final String snoktp_ = ed_noktp.getText().toString();
        final String snama_ = ed_nama.getText().toString();
        final String salamat_ = ed_alamat.getText().toString();
        final String stelepon_ = ed_hp.getText().toString();
        final String stahunmondok_ = ed_thn_mndook.getText().toString();
        final String stahunkeluar_ = ed_thn_keluarmndok.getText().toString();
        final String spekerjaan_ = ed_pekerjaan.getText().toString();
        final String sbidangusaha_ = ed_bidangUsaha.getText().toString();
        final String sakunfb_ = ed_AkunFb.getText().toString();
        final String semail_ = ed_Email.getText().toString();
        final String susername_ = ed_UserName.getText().toString();
        final String spass_ = ed_password.getText().toString();

        if (snoktp_.isEmpty() || snoktp_.length() < 16) {
            ed_noktp.setError("isi nik minimal 16 digit");
            Toast.makeText(IsiData.this, "nik minimal 16 digit", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            ed_noktp.setError(null);
        }
        if (snama_.isEmpty() || snama_.length() < 3) {
            ed_nama.setError("isi nama minimal 3 digit");
            Toast.makeText(IsiData.this, "nama minimal 3 digit", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            ed_nama.setError(null);
        }
        if (salamat_.isEmpty() || salamat_.length() < 3) {
            ed_alamat.setError("isi alamat minimal 3 digit");
            Toast.makeText(IsiData.this, "alamat minimal 3 digit", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            ed_alamat.setError(null);
        }

        if (stelepon_.isEmpty() || stelepon_.length() < 8) {
            ed_hp.setError("isi nomor telepon minimal 8 digit");
            Toast.makeText(IsiData.this, "nomor telepon minimal 8 digit", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            ed_hp.setError(null);
        }

        if (stahunmondok_.isEmpty() || stahunmondok_.length() < 4) {
            ed_thn_mndook.setError("isi tahun mondok  minimal 4 digit ");
            Toast.makeText(IsiData.this, "tahun mondok  minimal 4 digit", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            ed_thn_mndook.setError(null);
        }
        if (stahunkeluar_.isEmpty() || stahunkeluar_.length() < 4 ) {
            ed_thn_keluarmndok.setError("isi tahun keluar minimal 4 digit");
            Toast.makeText(IsiData.this, "tahun keluar minimal 4 digit", Toast.LENGTH_SHORT).show();

            valid = false;
        } else {
            ed_thn_keluarmndok.setError(null);
        }
        if (spekerjaan_.isEmpty() || spekerjaan_.length() < 2) {
            ed_pekerjaan.setError("isi pekerjaan  minimal 2 digit");
            Toast.makeText(IsiData.this, "pekerjaan  minimal 2 digit", Toast.LENGTH_SHORT).show();

            valid = false;
        } else {
            ed_pekerjaan.setError(null);
        }

        if (sbidangusaha_.isEmpty() || sbidangusaha_.length() < 2) {
            ed_bidangUsaha.setError("isi bidang usaha  minimal 2 digit");
            Toast.makeText(IsiData.this, "bidang usaha  minimal 2 digit", Toast.LENGTH_SHORT).show();

            valid = false;
        } else {
            ed_bidangUsaha.setError(null);
        }

        if (semail_.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(semail_).matches()) {
            ed_Email.setError("Email tidak valid");
            Toast.makeText(IsiData.this, "Email tidak valid", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            ed_Email.setError(null);
        }
        if (susername_.isEmpty() || susername_.length() < 3 ) {
            ed_UserName.setError("isi username minimal 3 digit");
            Toast.makeText(IsiData.this, "username minimal 3 digit", Toast.LENGTH_SHORT).show();
            valid = false;
        } else {
            ed_UserName.setError(null);
        }

        if (spass_.isEmpty() || spass_.length() < 3) {
            ed_password.setError("isi password minimal 3 digit");
            Toast.makeText(IsiData.this, "password minimal 3 digit", Toast.LENGTH_SHORT).show();

            valid = false;
        } else {
            ed_password.setError(null);
        }
        return valid;
    }
}
