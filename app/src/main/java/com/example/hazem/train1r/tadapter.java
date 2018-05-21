package com.example.hazem.train1r;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Hazem on 3/25/2018.
 */

public class tadapter extends RecyclerView.Adapter<tadapter.Holder> {
    List<movies> list;
    Context context;

    public tadapter(Context c, List<movies> l) {
        list = l;
        context = c;
    }
    @Override
    public tadapter.Holder onCreateViewHolder(ViewGroup parent, int viewType) {
     View v= LayoutInflater.from(context).inflate(R.layout.item_test,parent,false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(tadapter.Holder holder, final int position) {
         movies m=list.get(position);
         String s=m.getImage();
         Picasso.with(context).load(s).into(holder.imageView);
         holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(context,details.class);
                intent.putExtra("get-tit",list.get(position).getTitle());
                intent.putExtra("get-over",list.get(position).getOverview());
                intent.putExtra("get-im",list.get(position).getImage());
                intent.putExtra("get-rate",list.get(position).getRating());
                //intent.putExtra("get-rdate",list.get(position).getRelease_date());
                intent.putExtra("get_id",list.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public Holder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.iv_poster);
        }
}}
