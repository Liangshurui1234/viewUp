package com.example.administrator.xiazoliuxing.model;

import android.support.v4.view.ViewPager;

import com.example.administrator.xiazoliuxing.base.BaseModel;
import com.example.administrator.xiazoliuxing.bean.CollectBean2;
import com.example.administrator.xiazoliuxing.net.MyServer;
import com.example.administrator.xiazoliuxing.net.ResultCallBack;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class CollectM extends BaseModel {
    public void setData(int page,String s, final ResultCallBack<CollectBean2> resultCallBack) {
        Retrofit build = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(MyServer.Url)
                .build();
        MyServer myServer = build.create(MyServer.class);
        Observable<CollectBean2> collectBean2Observable = myServer.requestCollect(MyServer.token,page);
        collectBean2Observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<CollectBean2>() {
                    @Override
                    public void accept(CollectBean2 collectBean2) throws Exception {
                            resultCallBack.onSuccess(collectBean2);
                    }
                });
    }
}
