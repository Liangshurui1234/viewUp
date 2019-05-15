package com.example.administrator.xiazoliuxing.model;

import com.example.administrator.xiazoliuxing.base.BaseModel;
import com.example.administrator.xiazoliuxing.bean.BandetailsBean;
import com.example.administrator.xiazoliuxing.net.MyServer;
import com.example.administrator.xiazoliuxing.net.ResultCallBack;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class BandetailsM extends BaseModel {
    public void getData(String s, int id, final ResultCallBack<BandetailsBean> resultCallBack) {
        Retrofit build = new Retrofit.Builder()
                .baseUrl(MyServer.Url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        MyServer myServer = build.create(MyServer.class);
        Observable<BandetailsBean> detail = myServer.detail(s,id);
        detail.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<BandetailsBean>() {
                    @Override
                    public void accept(BandetailsBean bandetailsBean) throws Exception {
                        resultCallBack.onSuccess(bandetailsBean);
                    }
                });
    }
}
