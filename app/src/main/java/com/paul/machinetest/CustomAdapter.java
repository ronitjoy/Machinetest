package com.paul.machinetest;

import android.content.Context;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;

public class CustomAdapter implements ListAdapter {
    ArrayList<DownloadModel> arrayList;
    Context context;
    public CustomAdapter(Context context, ArrayList<DownloadModel> arrayList) {
        this.arrayList=arrayList;
        this.context=context;
    }
    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }
    @Override
    public boolean isEnabled(int position) {
        return true;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return arrayList.size();
    }
    @Override
    public Object getItem(int position) {
        return position;
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public boolean hasStableIds() {
        return false;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        DownloadModel subjectData = arrayList.get(position);
        if(convertView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.list_row, null);
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Gson gson = new Gson();
                    String json = gson.toJson(arrayList.get(position));
                    Log.d("onClick: ",json);
                    Intent intent = new Intent(context,Detail.class);
                    intent.putExtra("list",json);
                    context.startActivity(intent);
                }
            });
            TextView name = convertView.findViewById(R.id.tv_title);
            TextView company = convertView.findViewById(R.id.tv_comp);
            ImageView imag = convertView.findViewById(R.id.iv_action);
            name.setText(subjectData.getName());
            if (subjectData.getCompany()!=null){
            company.setText(subjectData.getCompany().getName());}
            if (subjectData.getProfileImage()!=null){
            Picasso.with(context)
                    .load(subjectData.getProfileImage())
                    .into(imag);}
        }

        return convertView;
    }
    @Override
    public int getItemViewType(int position) {
        return position;
    }
    @Override
    public int getViewTypeCount() {
        return arrayList.size();
    }
    @Override
    public boolean isEmpty() {
        return false;
    }
}