package com.example.administrator.xiazoliuxing.model;

import com.example.administrator.xiazoliuxing.base.BaseModel;
import com.example.administrator.xiazoliuxing.bean.Homepageadbean;
import com.example.administrator.xiazoliuxing.net.MyServer;
import com.example.administrator.xiazoliuxing.net.ResultCallBack;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class XianluM extends BaseModel {
    public void getData(int i, String s, final ResultCallBack<Homepageadbean> resultCallBack) {
        Retrofit build = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(MyServer.Url)
                .build();
        MyServer myServer = build.create(MyServer.class);
        //HashMap<String, String> map = new HashMap<>();
        // map.put("banmi-app-token","JVy0IvZamK7f7FBZLKFtoniiixKMlnnJ6dWZ6NlsY4HGsxcAA9qvFo8yacHCKHE8YAcd0UF9L59nEm7zk9AUixee0Hl8EeWA880c0ikZBW0KEYuxQy5Z9NP3BNoBi3o3Q0g");
        Observable<Homepageadbean> data = myServer.getData(i, s);
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Homepageadbean>() {
                    @Override
                    public void accept(Homepageadbean homepageadbean) throws Exception {
                       resultCallBack.onSuccess(homepageadbean);

                    }
                });
    }
}
