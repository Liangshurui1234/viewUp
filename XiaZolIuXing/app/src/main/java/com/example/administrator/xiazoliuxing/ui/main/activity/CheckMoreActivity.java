package com.example.administrator.xiazoliuxing.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.xiazoliuxing.R;
import com.example.administrator.xiazoliuxing.bean.CheckBean;
import com.example.administrator.xiazoliuxing.net.Checkmore;
import com.example.administrator.xiazoliuxing.net.MyServer;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CheckMoreActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView mImg1;
    private ImageView mImg2;
    private List<CheckBean.ResultBean.BundlesBean> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_more);
        initView();
        getInitData();
    }

    private void getInitData() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(Checkmore.baseUrl)
                .build();
        Checkmore checkMore = retrofit.create(Checkmore.class);
        Observable<CheckBean> check = checkMore.getCheck(MyServer.token);
        check.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CheckBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CheckBean checkBean) {
                        list = checkBean.getResult().getBundles();
                        list = checkBean.getResult().getBundles();
                        Glide.with(CheckMoreActivity.this).load(list.get(0).getCardURL()).into(mImg1);
                        Glide.with(CheckMoreActivity.this).load(list.get(1).getCardURL()).into(mImg2);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initView() {
        mImg1 = (ImageView) findViewById(R.id.img_1);
        mImg2 = (ImageView) findViewById(R.id.img_2);
        mImg1.setOnClickListener(this);
        mImg2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.img_1:
                Intent intent = new Intent(CheckMoreActivity.this, WebHomepageActivity.class);
                intent.putExtra("id", list.get(0).getContentURL());
                startActivity(intent);
                break;
            case R.id.img_2:
                Intent intent1= new Intent(CheckMoreActivity.this, WebHomepageActivity.class);
                intent1.putExtra("id", list.get(1).getContentURL());
                startActivity(intent1);
                break;
        }
    }
    // case R.id.bg_img1:
    //                Intent intent = new Intent(CheckMoreActivity.this, WebActivity.class);
    //                intent.putExtra("url",list.get(0).getContentURL());
    //                startActivity(intent);
    //                break;
}
