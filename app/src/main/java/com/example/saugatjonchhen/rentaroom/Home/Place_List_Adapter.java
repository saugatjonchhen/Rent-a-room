package com.example.saugatjonchhen.rentaroom.Home;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.saugatjonchhen.rentaroom.R;

import java.util.List;

/**
 * Created by Saugat Jonchhen on 11/23/2017.
 */

public class Place_List_Adapter extends RecyclerView.Adapter<Place_List_Adapter.MyViewHolder>{

    public List<Place_List> placeList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView Place_name;
        public ImageView img;

        public MyViewHolder(View view) {
            super(view);
            Place_name = (TextView) view.findViewById(R.id.txt);
            img = (ImageView) view.findViewById(R.id.img);
        }
    }

    public Place_List_Adapter(List<Place_List> placeList){
        this.placeList = placeList;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Place_List pl = placeList.get(position);
        holder.Place_name.setText(pl.name);
        holder.img.setImageBitmap(pl.img);

    }

    @Override
    public int getItemCount() {
        return placeList.size();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.custom_recycler_view,parent, false);
        return new MyViewHolder(v);
    }


}
