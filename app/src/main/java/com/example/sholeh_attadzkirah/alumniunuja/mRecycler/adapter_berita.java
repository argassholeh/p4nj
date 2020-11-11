/*
 * Copyright (c) Muhammad Solehudin
 */

package com.example.sholeh_attadzkirah.alumniunuja.mRecycler;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.sholeh_attadzkirah.alumniunuja.IsiBeritaActivity;
import com.example.sholeh_attadzkirah.alumniunuja.R;
import com.example.sholeh_attadzkirah.alumniunuja.config.koneksi;

import java.util.ArrayList;

public class adapter_berita extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private ArrayList<Model_Berita> dataList;
    public adapter_berita(ArrayList<Model_Berita> dataList){
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v;
        LayoutInflater inflat = LayoutInflater.from(parent.getContext());
        v = inflat.inflate(R.layout.custom_content_berita,parent,false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, final int position) {
        final MyHolder myHolder = (MyHolder) holder;
        Glide.with(myHolder.context).load(koneksi.tampilFotoItemBerita+dataList.get(position).getImage()).apply(new RequestOptions().placeholder(R.drawable.no_image).centerCrop()).into(myHolder.img);
        myHolder.judul.setText(dataList.get(position).getJudul());
        myHolder.tanggal.setText(dataList.get(position).getTanggal());
//        myHolder.deskrip.setText(dataList.get(position).getDescription());
        myHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(myHolder.context, IsiBeritaActivity.class);
                i.putExtra("id kegiatan", dataList.get(position).getId());
                i.putExtra("judul kegiatan", dataList.get(position).getJudul());
                i.putExtra("foto kegiatan", koneksi.tampilFotoItemBerita+dataList.get(position).getImage());
                i.putExtra("id lembaga alumni", dataList.get(position).getIdlembaga());
                myHolder.context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {
        TextView judul, tanggal;
        ImageView img;
        CardView card;
        Context context;
        // create constructor to get widget reference
        public MyHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            img = itemView.findViewById(R.id.img);
            judul = itemView.findViewById(R.id.judul);
            tanggal = itemView.findViewById(R.id.tanggal);
            card = itemView.findViewById(R.id.card);
//            deskrip = itemView.findViewById(R.id.deskrip);
        }
    }
}
