package com.example.administrator.xiazoliuxing.model;


import com.example.administrator.xiazoliuxing.base.BaseModel;
import com.example.administrator.xiazoliuxing.bean.CollectBean;
import com.example.administrator.xiazoliuxing.bean.PathInfoBean;
import com.example.administrator.xiazoliuxing.net.ApiService;
import com.example.administrator.xiazoliuxing.net.BaseObserver;
import com.example.administrator.xiazoliuxing.net.HttpUtils;
import com.example.administrator.xiazoliuxing.net.MyServer;
import com.example.administrator.xiazoliuxing.net.ResultCallBack;
import com.example.administrator.xiazoliuxing.net.RxUtils;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;



public class PathInfoM extends BaseModel {
    public void getPathInfo(String token,String id, final ResultCallBack<PathInfoBean> resultCallBack) {
        MyServer apiserver = HttpUtils.getInstance().getApiserver(MyServer.Url, MyServer.class);
        Observable<PathInfoBean> pathfo = apiserver.getPathfo(token, id);
        pathfo.compose(RxUtils.<PathInfoBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<PathInfoBean>() {
                    @Override
                    public void error(String msg) {
                        resultCallBack.onFail(msg);
                    }

                    @Override
                    protected void subscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PathInfoBean pathInfoBean) {
                        resultCallBack.onSuccess(pathInfoBean);
                    }
                });
    }

    public void collect(int id1, final ResultCallBack<CollectBean> resultCallBack) {
        MyServer apiserver = HttpUtils.getInstance().getApiserver(MyServer.Url, MyServer.class);
//        String s = String.valueOf(id1);
        Observable<CollectBean> collect = apiserver.collect(MyServer.token,id1);
        collect.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<CollectBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(CollectBean collectBean) {
                            resultCallBack.onSuccess(collectBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                            resultCallBack.onFail("收藏失败");
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
