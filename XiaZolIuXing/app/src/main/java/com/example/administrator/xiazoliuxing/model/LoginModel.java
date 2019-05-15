package com.example.administrator.xiazoliuxing.model;


import com.example.administrator.xiazoliuxing.base.BaseModel;
import com.example.administrator.xiazoliuxing.bean.VerifyCodeBean;
import com.example.administrator.xiazoliuxing.net.ApiService;
import com.example.administrator.xiazoliuxing.net.BaseObserver;
import com.example.administrator.xiazoliuxing.net.HttpUtils;
import com.example.administrator.xiazoliuxing.net.ResultCallBack;
import com.example.administrator.xiazoliuxing.net.RxUtils;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import retrofit2.http.HTTP;



public class LoginModel extends BaseModel {
    private static final String TAG = "LoginModel";

    public void getVerifyCode(final ResultCallBack<VerifyCodeBean> callBack) {
        ApiService apiserver = HttpUtils.getInstance().getApiserver(ApiService.sBaseUrl, ApiService.class);
        final Observable<VerifyCodeBean> verifyCode = apiserver.getVerifyCode();

        /*verifyCode.compose(RxUtils.<VerifyCodeBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<VerifyCodeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        mCompositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(VerifyCodeBean verifyCodeBean) {
                        //Logger.logD(TAG,verifyCodeBean.toString());
                        callBack.onSuccess(verifyCodeBean);
                    }
                });*/
        verifyCode.compose(RxUtils.<VerifyCodeBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<VerifyCodeBean>() {
                    @Override
                    public void error(String msg) {

                    }

                    @Override
                    protected void subscribe(Disposable d) {
                       addDisposable(d);
                    }

                    @Override
                    public void onNext(VerifyCodeBean verifyCodeBean) {
                        callBack.onSuccess(verifyCodeBean);
                    }
                });
    }
}
