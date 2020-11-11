/*
 * Copyright (c) Muhammad Solehudin
 */

package com.example.sholeh_attadzkirah.alumniunuja.mFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.sholeh_attadzkirah.alumniunuja.Download;
import com.example.sholeh_attadzkirah.alumniunuja.FksJ;
import com.example.sholeh_attadzkirah.alumniunuja.InfoLainnya;
import com.example.sholeh_attadzkirah.alumniunuja.IsiSaldo;
import com.example.sholeh_attadzkirah.alumniunuja.Lainnya;
import com.example.sholeh_attadzkirah.alumniunuja.Lembaga;
import com.example.sholeh_attadzkirah.alumniunuja.MainActivity;
import com.example.sholeh_attadzkirah.alumniunuja.Njic;
import com.example.sholeh_attadzkirah.alumniunuja.P4nj;
import com.example.sholeh_attadzkirah.alumniunuja.ProductAlumni;
import com.example.sholeh_attadzkirah.alumniunuja.R;
import com.example.sholeh_attadzkirah.alumniunuja.SignIn;
import com.example.sholeh_attadzkirah.alumniunuja.SumbanganMasjid;

public class HomeFrgament extends Fragment {
    ImageView imgFksJ, imgP4nj, imgNjic, imgProductA, imgSumbangan, imgInfoLainnya;
    TextView tvxLembaga, tvxIsiSaldo, tvxDownload, tvxLainnya;

    ListView listView = null;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home,container,false);
        imgFksJ = rootView.findViewById(R.id.img_fksj);
        tvxLembaga = rootView.findViewById(R.id.tv_lembaga);
        tvxIsiSaldo = rootView.findViewById(R.id.tv_isiSaldo);
        tvxDownload = rootView.findViewById(R.id.tv_Download);
        tvxLainnya= rootView.findViewById(R.id.tv_lainnya);
        imgP4nj= rootView.findViewById(R.id.img_P4nj);
        imgNjic= rootView.findViewById(R.id.img_Njic);
        imgProductA= rootView.findViewById(R.id.img_ProductAlumni);
        imgSumbangan= rootView.findViewById(R.id.img_Sumbangan);
        imgInfoLainnya= rootView.findViewById(R.id.img_InfoLainnya);

        tvxLembaga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Lembaga.class);
                getActivity().startActivity(intent);
            }
        });

        tvxIsiSaldo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), IsiSaldo.class);
                getActivity().startActivity(intent);
            }
        });
        tvxDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Download.class);
                getActivity().startActivity(intent);
            }
        });
        tvxLainnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Lainnya.class);
                getActivity().startActivity(intent);
            }
        });
        imgFksJ.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FksJ.class);
                intent.putExtra("slide", "1");
                getActivity().startActivity(intent);
            }
        });

        imgP4nj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), P4nj.class);
                intent.putExtra("slide", "2");
                getActivity().startActivity(intent);
            }
        });

        imgNjic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), Njic.class);
                intent.putExtra("slide", "3");
                getActivity().startActivity(intent);
            }
        });
        imgProductA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ProductAlumni.class);
                getActivity().startActivity(intent);
            }
        });
        imgSumbangan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), SumbanganMasjid.class);
                getActivity().startActivity(intent);
            }
        });
        imgInfoLainnya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InfoLainnya.class);
                getActivity().startActivity(intent);
            }
        });
        return rootView;
    }
}

