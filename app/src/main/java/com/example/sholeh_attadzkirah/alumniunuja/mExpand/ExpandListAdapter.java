/*
 * Copyright (c) Muhammad Solehudin
 */

package com.example.sholeh_attadzkirah.alumniunuja.mExpand;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.example.sholeh_attadzkirah.alumniunuja.R;

import java.util.ArrayList;
import java.util.List;

public class ExpandListAdapter extends BaseExpandableListAdapter{
//menwariskan class BaseExpandableListAdapter

    private Context context;
    private ArrayList<Group> groups;
    //deklarasi var context dan groups

    public ExpandListAdapter(Context context, ArrayList<Group> groups) {
        this.context = context;
        this.groups = groups;
        //constructor
    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        ArrayList<Child> childList = groups.get(groupPosition).getItem();
        return childList.get(childPosition);
        //mengambil nilai atau value dari child
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
        //mengambil id dari child
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild,
                             View convertView, ViewGroup parent) {

        Child child = (Child)getChild(groupPosition,childPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)context
                    .getSystemService(context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.expandable_list_item,null);

        }

        TextView textView = (TextView)convertView.findViewById(R.id.textViewchild);
        String text = child.getNama();
        textView.setText(text);

        //menampilkan data ke view layout child_item.xml
        return convertView;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
        //mengambil nilai atau value dari jumlah grup
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        List<Child> child ;
        child = groups.get(groupPosition).getItem();
        //Mengambil jumlah child berdasarkan grup tertentu
        return child.size();
    }

    @Override
    public Object getGroup(int groupPosition) {

        // Mengambil data yang terkait object grup
        return groups.get(groupPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
//Mengambil id yang terkait grup
        return groupPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
        //Menunjukan apakah id dari grup dan child stabil terkait perubahan data di dalamnya
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {

        Group group = (Group)getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater layoutInflater = (LayoutInflater)
                    context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

            convertView = layoutInflater.inflate(R.layout.expandable_list_group,null);
            TextView textView = (TextView)convertView.findViewById(R.id.textViewGroup);
            String text = group.getNama();
            textView.setText(text);
        }
        // //menampilkan data ke view layout grup_item.xml
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
        //Menunjukan apakah posisi child tertentu dapat di selectable
    }
}
