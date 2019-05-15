package com.example.administrator.xiazoliuxing.model;


import com.example.administrator.xiazoliuxing.base.BaseModel;
import com.example.administrator.xiazoliuxing.bean.LoginInfo;
import com.example.administrator.xiazoliuxing.net.BaseObserver;
import com.example.administrator.xiazoliuxing.net.EveryWhereService;
import com.example.administrator.xiazoliuxing.net.HttpUtils;
import com.example.administrator.xiazoliuxing.net.ResultCallBack;
import com.example.administrator.xiazoliuxing.net.RxUtils;

import io.reactivex.disposables.Disposable;


public class LoginOrBindModel extends BaseModel {
    public void loginSina(String uid, final ResultCallBack<LoginInfo> callBack) {
        EveryWhereService apiserver = HttpUtils.getInstance().getApiserver(EveryWhereService.BASE_URL, EveryWhereService.class);
        apiserver.postWeiboLogin(uid)
                .compose(RxUtils.<LoginInfo>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<LoginInfo>() {
                    @Override
                    public void error(String msg) {

                    }

                    @Override
                    protected void subscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(LoginInfo loginInfo) {
                        if (loginInfo != null){
                            if (loginInfo.getCode() == EveryWhereService.SUCCESS_CODE){
                                callBack.onSuccess(loginInfo);
                            }else {
                                callBack.onFail(loginInfo.getDesc());
                            }
                        }
                    }
                });
    }
}
