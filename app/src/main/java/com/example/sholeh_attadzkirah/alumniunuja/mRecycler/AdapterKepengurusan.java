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
import android.widget.TextView;

import com.example.sholeh_attadzkirah.alumniunuja.DetailPengurus;
import com.example.sholeh_attadzkirah.alumniunuja.R;

import java.util.ArrayList;

public class AdapterKepengurusan extends RecyclerView.Adapter<AdapterKepengurusan.ViewHolderKepengurusan>{

    Context context;
    ArrayList<DetailDataKepengurusanModel> detailDataKepengurusanModel;

    public AdapterKepengurusan(ArrayList<DetailDataKepengurusanModel> detailDataMahasiswaModel, Context context){
        this.context = context;
        this.detailDataKepengurusanModel = detailDataMahasiswaModel;
    }

    @NonNull
    @Override
    public AdapterKepengurusan.ViewHolderKepengurusan onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.custom_kepengurusan, viewGroup, false);
        return new AdapterKepengurusan.ViewHolderKepengurusan(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterKepengurusan.ViewHolderKepengurusan viewHolderKepengurusan, int i) {
        viewHolderKepengurusan.Nama.setText(detailDataKepengurusanModel.get(i).getNama());
        viewHolderKepengurusan.Jabatan.setText(detailDataKepengurusanModel.get(i).getJabatan());
        viewHolderKepengurusan.Devisi.setText(detailDataKepengurusanModel.get(i).getDevisi());
        viewHolderKepengurusan.MasaBakti.setText(detailDataKepengurusanModel.get(i).getMasaBakti());
        viewHolderKepengurusan.txtPengurus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent pindah = new Intent(context, DetailPengurus.class);
                pindah.putExtra("id_alumni", detailDataKepengurusanModel.get(i).getId_alumni());
                context.startActivity(pindah);
            }
        });
    }

    @Override
    public int getItemCount() {
        return detailDataKepengurusanModel.size();
    }

    public class ViewHolderKepengurusan extends RecyclerView.ViewHolder {
        TextView Nama, Jabatan, Devisi, MasaBakti;
        private CardView txtPengurus;
        public ViewHolderKepengurusan(@NonNull View itemView) {
            super(itemView);
            Nama= itemView.findViewById(R.id.tv_NamaK);
            Jabatan = itemView.findViewById(R.id.tv_jabatanK);
            Devisi = itemView.findViewById(R.id.tv_devisiK);
            MasaBakti = itemView.findViewById(R.id.tv_masabaktiK);
            txtPengurus = itemView.findViewById(R.id.cara_kepengurusan);
        }
    }
}
