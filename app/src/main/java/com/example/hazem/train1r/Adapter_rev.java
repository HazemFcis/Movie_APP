package com.example.hazem.train1r;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Hazem on 4/24/2018.
 */

public class Adapter_rev extends RecyclerView.Adapter<Adapter_rev.Holder> {

    List<String>list;
    Context context ;
    public Adapter_rev(Context c, List<String> l) {
        list = l;
        context = c;
    }
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.rev_item,parent,false);
        return new Adapter_rev.Holder(v);
    }

    @Override
    public void onBindViewHolder(Adapter_rev.Holder holder, int position) {
        holder.textView.setText(list.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public  static  class Holder extends RecyclerView.ViewHolder {

        protected  TextView textView;
        public Holder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.TV_rev);
        }

}}
