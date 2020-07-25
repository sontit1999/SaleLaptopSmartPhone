package com.example.appbanhang.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appbanhang.R;
import com.example.appbanhang.callback.Menucallback;
import com.example.appbanhang.model.Loaisp;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyviewHoder> {
    ArrayList<Loaisp> list;
    Menucallback menucallback;
    @NonNull
    @Override
    public MyviewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View heroView = inflater.inflate(R.layout.dong_listview_loaisp, parent, false);
        MyviewHoder viewHolder = new MyviewHoder(heroView);
        return viewHolder;
    }
    public void setList(ArrayList<Loaisp> arr){
        list = arr;
        notifyDataSetChanged();
    }

    public void setMenucallback(Menucallback menucallback) {
        this.menucallback = menucallback;
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHoder holder, int position) {
         final Loaisp loaisp = list.get(position);
         holder.bindData(loaisp);
         holder.itemView.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 if(menucallback!=null){
                     menucallback.onItemMenuClick(loaisp.id);
                 }
             }
         });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyviewHoder extends RecyclerView.ViewHolder{
        ImageView ivLoaisp;
        TextView tvLoaiSp;
        public MyviewHoder(@NonNull View itemView) {
            super(itemView);
            ivLoaisp = itemView.findViewById(R.id.imageviewloaisp);
            tvLoaiSp = itemView.findViewById(R.id.textviewloaisp);
        }
        public void bindData(Loaisp loaisp){
            ivLoaisp.setImageResource(loaisp.linkimage);
            tvLoaiSp.setText(loaisp.TenLoaisp);
        }
    }
}
