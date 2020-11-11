/*
 * Copyright (c) Muhammad Solehudin
 */

package com.example.sholeh_attadzkirah.alumniunuja.mExpand;

import java.util.ArrayList;

public class Group {

    private String nama;
    private ArrayList<Child> item;

    public String getNama() {
        return nama;
        //getter
    }

    public void setNama(String nama) {
        this.nama = nama;
        //setter
    }

    public ArrayList<Child> getItem() {
        return item;
        //getter
    }

    public void setItem(ArrayList<Child> item) {
        this.item = item;
        //setter
    }
}
