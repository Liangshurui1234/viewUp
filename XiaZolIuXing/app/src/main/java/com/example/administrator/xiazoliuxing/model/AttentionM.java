package com.example.administrator.xiazoliuxing.model;

import com.example.administrator.xiazoliuxing.base.BaseModel;
import com.example.administrator.xiazoliuxing.bean.Attention1;
import com.example.administrator.xiazoliuxing.net.MyServer;
import com.example.administrator.xiazoliuxing.net.ResultCallBack;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AttentionM extends BaseModel {
    public void getData(String s, final ResultCallBack<Attention1> resultCallBack) {
        Retrofit build = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(MyServer.Url)
                .build();
        MyServer myServer = build.create(MyServer.class);
        Observable<Attention1> woguanzhu = myServer.woguanzhu(s);
        woguanzhu.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Attention1>() {
                    @Override
                    public void accept(Attention1 attention1) throws Exception {
                        resultCallBack.onSuccess(attention1);
                    }
                });


    }
}
