/*
 * Copyright (c) Muhammad Solehudin
 */

package com.example.sholeh_attadzkirah.alumniunuja.mRecycler;

public class DetailDataKepengurusanModel {

    private String Nama;
    private String Jabatan;
    private String Devisi;
    private String MasaBakti;
    private String id_alumni;

    public DetailDataKepengurusanModel(String Nama, String Jabatan, String Devisi, String MasaBakti, String id_alumni) {
        this.Nama = Nama;
        this.Jabatan = Jabatan;
        this.Devisi = Devisi;
        this.MasaBakti = MasaBakti;
        this.id_alumni = id_alumni;

    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = Nama;
    }

    public String getJabatan() {
        return Jabatan;
    }

    public void setJabatan(String jabatan) {
        Jabatan = jabatan;
    }

    public String getDevisi() {
        return Devisi;
    }

    public void setDevisi(String devisi) {
        this.Devisi = devisi;
    }

    public String getMasaBakti() {
        return MasaBakti;
    }

    public void setMasaBakti(String masaBakti) {
        MasaBakti = masaBakti;
    }

    public String getId_alumni() {
        return id_alumni;
    }

    public void setId_alumni(String id_alumni) {
        this.id_alumni = id_alumni;
    }
}
