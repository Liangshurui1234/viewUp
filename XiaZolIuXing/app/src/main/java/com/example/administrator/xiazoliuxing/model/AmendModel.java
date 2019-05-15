package com.example.administrator.xiazoliuxing.model;

import android.util.Log;

import com.example.administrator.xiazoliuxing.base.BaseModel;
import com.example.administrator.xiazoliuxing.base.Constants;
import com.example.administrator.xiazoliuxing.bean.Mingxingbean;
import com.example.administrator.xiazoliuxing.bean.Yonghu;
import com.example.administrator.xiazoliuxing.net.MyServer;
import com.example.administrator.xiazoliuxing.net.ResultCallBack;
import com.example.administrator.xiazoliuxing.util.SpUtil;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class AmendModel extends BaseModel {
    private static final String TAG = "AmendModel";
    String name = (String) SpUtil.getParam(Constants.USERNAME, "");
    String sig = (String) SpUtil.getParam(Constants.SIG, "");
    String sex = (String) SpUtil.getParam(Constants.SEX, "");
    String photo = (String) SpUtil.getParam(Constants.PHOTO, "");


    public void getDate(String s,String username,String description,String gender,String photo,final ResultCallBack<Mingxingbean> resultCallBack) {
        new Retrofit.Builder()
                .baseUrl(MyServer.Url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(MyServer.class)
                .updateInfo(s,
                        username, description, gender, photo)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Mingxingbean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Mingxingbean mingxingbean) {
                        Log.d(TAG, "onNext: " + mingxingbean.toString());
                        resultCallBack.onSuccess(mingxingbean);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    public void setData(final ResultCallBack<Yonghu> resultCallBack) {
        Retrofit build = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(MyServer.Url)
                .build();
        MyServer myServer = build.create(MyServer.class);
        Observable<Yonghu> yonghu = myServer.getYonghu(MyServer.token);
            yonghu.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Yonghu>() {
                        @Override
                        public void accept(Yonghu yonghu) throws Exception {
                            resultCallBack.onSuccess(yonghu);
                        }
                    });
    }
}
