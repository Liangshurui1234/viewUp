package com.example.administrator.xiazoliuxing.ui.main.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.administrator.xiazoliuxing.bean.Homepageadbean;
import com.example.administrator.xiazoliuxing.bean.Mingxingbean;
import com.example.administrator.xiazoliuxing.ui.main.activity.BamidetailsActivity;

import java.util.ArrayList;

public class Banmiadapter extends RecyclerView.Adapter<Banmiadapter.ViewHolder> {
    private ArrayList<Mingxingbean.ResultBean.BanmiBean>list;
    private Context context;

    public void setList(ArrayList<Mingxingbean.ResultBean.BanmiBean> list) {
        this.list = list;
    }

    public Banmiadapter(ArrayList<Mingxingbean.ResultBean.BanmiBean> list, Context context) {

        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.layout_banmi, null);

        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder,final int i) {
        Glide.with(context).load(list.get(i).getPhoto()).into(viewHolder.img);
        viewHolder.text_1.setText(list.get(i).getName());
        viewHolder.text_2.setText(list.get(i).getFollowing()+""+"人关注");
        viewHolder.text_3.setText(list.get(i).getLocation());
        viewHolder.text_4.setText(list.get(i).getOccupation());
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, BamidetailsActivity.class);
                intent.putExtra("id",list.get(i).getId());
                context.startActivity(intent);
            }
        });
        viewHolder.but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (liseterentent != null){
                    liseterentent.Liseteren(viewHolder.but);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size() >0 ? list.size():0;
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
    public interface Liseterentent{
        void  Liseteren(Button but);
    }
    private Liseterentent liseterentent;

    public void setLiseterentent(Liseterentent liseterentent) {
        this.liseterentent = liseterentent;
    }
}
