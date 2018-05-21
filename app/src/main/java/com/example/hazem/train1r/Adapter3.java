package com.example.hazem.train1r;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hazem on 4/23/2018.
 */

public class Adapter3 extends RecyclerView.Adapter<Adapter3.Hold> {
    List<String> list;
    Context context;

    public Adapter3(Context c, ArrayList<String> l) {
        list = l;
        context = c;
    }
    @Override
    public Adapter3.Hold onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.trai_item,parent,false);
        return new Adapter3.Hold(v);
    }

    @Override
    public void onBindViewHolder(Adapter3.Hold holder, final int position) {
        String ss=list.get(position).toString();
        String s=ss.substring(0,10);
        holder.textView.setText(s);
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s,ss;
                s=list.get(position);
                ss=s.substring(10);
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+ss)));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

   public  static  class Hold extends RecyclerView.ViewHolder {

        protected  TextView textView;
        public Hold(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.TV_tra);
        }
}}
