/*
 * Copyright (c) Muhammad Solehudin
 */

package com.example.sholeh_attadzkirah.alumniunuja.mRecycler;

public class Model_Berita {

    private String judul;
    private String id;
    private String image;
    private String tanggal;
    private String idlembaga;


    public Model_Berita(String id, String judul, String tanggal,  String image, String idlembaga){
        super();
        this.judul = judul;
        this.id = id;
        this.image = image;
        this.tanggal = tanggal;
        this.idlembaga = idlembaga;

    }

    public String getJudul() {
        return judul;
    }

    public String getId() {
        return id;
    }

    public String getTanggal(){return tanggal;}


    public String getImage() {
        return image;
    }

    public String getIdlembaga() {
        return idlembaga;
    }

}
