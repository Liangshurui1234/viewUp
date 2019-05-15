package com.example.administrator.xiazoliuxing.model;

import com.example.administrator.xiazoliuxing.base.BaseModel;
import com.example.administrator.xiazoliuxing.bean.AttentionBean;
import com.example.administrator.xiazoliuxing.bean.Mingxingbean;
import com.example.administrator.xiazoliuxing.net.MyServer;
import com.example.administrator.xiazoliuxing.net.ResultCallBack;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Banmim extends BaseModel {
    public void getData(int pagh, String s, final ResultCallBack<Mingxingbean> resultCallBack) {
        Retrofit build = new Retrofit.Builder()
                .baseUrl(MyServer.Url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyServer myServer = build.create(MyServer.class);
        Observable<Mingxingbean> data1 = myServer.getData1( s);
        data1.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Mingxingbean>() {
                    @Override
                    public void accept(Mingxingbean mingxingbean) throws Exception {
                      resultCallBack.onSuccess(mingxingbean);
                    }
                });
    }

    public void guanzhu(int id, String s, final ResultCallBack<AttentionBean> resultCallBack) {
        Retrofit build = new Retrofit.Builder()
                .baseUrl(MyServer.Url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        MyServer myServer = build.create(MyServer.class);
        Observable<AttentionBean> guanzhu = myServer.guanzhu(s, id);
        guanzhu.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<AttentionBean>() {
                    @Override
                    public void accept(AttentionBean attentionBean) throws Exception {
                        resultCallBack.onSuccess(attentionBean);
                    }
                });

    }
}
