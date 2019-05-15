package com.example.administrator.xiazoliuxing.ui.main.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.xiazoliuxing.R;
import com.example.administrator.xiazoliuxing.bean.Attention1;

import java.util.ArrayList;

public class Attentionadapter extends RecyclerView.Adapter<Attentionadapter.ViewHolder> {
    private ArrayList<Attention1.ResultBean.BanmiBean>list;
    private Context context;

    public void setList(ArrayList<Attention1.ResultBean.BanmiBean> list) {
        this.list = list;
    }

    public Attentionadapter(ArrayList<Attention1.ResultBean.BanmiBean> list, Context context) {

        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.item_guanzhu, null);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Glide.with(context).load(list.get(i).getPhoto()).into(viewHolder.img);
        viewHolder.text_1.setText(list.get(i).getName());
        viewHolder.text_2.setText(list.get(i).getFollowing()+""+"人关注");
        viewHolder.text_3.setText(list.get(i).getLocation());
        viewHolder.text_4.setText(list.get(i).getOccupation());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView img;
        TextView text_1;
        TextView text_2;
        TextView text_3;
        TextView text_4;
        Button but;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);
            text_1=itemView.findViewById(R.id.text_1);
            text_2=itemView.findViewById(R.id.text_2);
            text_3=itemView.findViewById(R.id.text_3);
            text_4=itemView.findViewById(R.id.text_4);
            but=itemView.findViewById(R.id.but);
        }
    }
}
