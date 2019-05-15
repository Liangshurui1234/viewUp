package com.example.administrator.xiazoliuxing.ui.main.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.xiazoliuxing.R;
import com.example.administrator.xiazoliuxing.bean.Homepageadbean;
import com.example.administrator.xiazoliuxing.ui.main.activity.PathInfoActivity;
import com.example.administrator.xiazoliuxing.ui.main.activity.WebHomepageActivity;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

public class Homepageadapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<Homepageadbean.ResultBean.RoutesBean>list;
    private ArrayList<Homepageadbean.ResultBean.BannersBean>beans;
    private Context context;
    private int newPosition;

    public void setList(ArrayList<Homepageadbean.ResultBean.RoutesBean> list) {
        this.list = list;
    }

    public void setBeans(ArrayList<Homepageadbean.ResultBean.BannersBean> beans) {
        this.beans = beans;
    }

    public Homepageadapter(ArrayList<Homepageadbean.ResultBean.RoutesBean> list, ArrayList<Homepageadbean.ResultBean.BannersBean> beans, Context context) {

        this.list = list;
        this.beans = beans;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        if(i==1){
            View inflate = LayoutInflater.from(context).inflate(R.layout.layout_bann, null);
          return new BViewHolder(inflate);
        }else if(i==2){
            View view = LayoutInflater.from(context).inflate(R.layout.item_main_home_img, null);
            return new CViewHolder(view);

        } else {
            View inflate = LayoutInflater.from(context).inflate(R.layout.layout_shouye, null);
            return new ViewHolder(inflate);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
        int itemViewType = getItemViewType(i);
        if(itemViewType==1){
            BViewHolder bViewHolder=(BViewHolder)viewHolder;
            bViewHolder.banner.setImages(beans);
            bViewHolder.banner.setImageLoader(new ImageLoader() {
                @Override
                public void displayImage(Context context, Object path, ImageView imageView) {
                    Homepageadbean.ResultBean.BannersBean bannersBean = (Homepageadbean.ResultBean.BannersBean) path;
                    Glide.with(context).load(bannersBean.getImageURL()).into(imageView);


                }
            }).start();
        }else if(itemViewType==2){
            CViewHolder cViewHolder= (CViewHolder) viewHolder;
            final Homepageadbean.ResultBean.RoutesBean routesBean = list.get(i);
            Glide.with(context).load(routesBean.getCardURL()).into(cViewHolder.img);
            cViewHolder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, WebHomepageActivity.class);
                    String contentURL = list.get(i).getContentURL();
                    Log.e("zak", "onClick: "+contentURL );
                    intent.putExtra("id",contentURL+"?os=android");
                    context.startActivity(intent);
                }
            });


        }else {
            ViewHolder viewHolder1= (ViewHolder) viewHolder;
            Glide.with(context).load(list.get(i).getCardURL()).into(viewHolder1.img);
            viewHolder1.text_1.setText(list.get(i).getTitle());
            viewHolder1.text_4.setText(list.get(i).getPurchasedTimes()+"");
            viewHolder1.text_3.setText(list.get(i).getCity());
            viewHolder1.text_2.setText(list.get(i).getIntro());
            viewHolder1.but.setText("￥"+list.get(i).getPrice()+"");
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PathInfoActivity.class);
                    int id = list.get(i).getId();
                    intent.putExtra("id",id);
                    context.startActivity(intent);
                }
            });

        }

    }


    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return 1;
        } else if (list.get(position).getType().equals("bundle")) {
            return 2;
        } else {
            return 3;
        }

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
    public class BViewHolder extends RecyclerView.ViewHolder {
      Banner banner;
        public BViewHolder(@NonNull View itemView) {
            super(itemView);
            banner=itemView.findViewById(R.id.bann);

        }
    }
    public class CViewHolder extends RecyclerView.ViewHolder {
      ImageView img;
        public CViewHolder(@NonNull View itemView) {
            super(itemView);
            img=itemView.findViewById(R.id.img);

        }
    }
}
