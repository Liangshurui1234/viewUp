package com.example.administrator.xiazoliuxing.presenter;


import com.example.administrator.xiazoliuxing.R;
import com.example.administrator.xiazoliuxing.base.BaseApp;
import com.example.administrator.xiazoliuxing.base.BasePresenter;
import com.example.administrator.xiazoliuxing.bean.VerifyCodeBean;
import com.example.administrator.xiazoliuxing.model.LoginModel;
import com.example.administrator.xiazoliuxing.net.ApiService;
import com.example.administrator.xiazoliuxing.net.ResultCallBack;
import com.example.administrator.xiazoliuxing.view.main.VerifyView;

public class VerifyPresenter extends BasePresenter<VerifyView> {
    private LoginModel mLoginModel;

    @Override
    protected void initModel() {
        mLoginModel = new LoginModel();
        mModels.add(mLoginModel);
    }

    public void getVerifyCode() {
        mLoginModel.getVerifyCode(new ResultCallBack<VerifyCodeBean>() {
            @Override
            public void onSuccess(VerifyCodeBean bean) {
                if (bean != null && bean.getCode() == ApiService.SUCCESS_CODE){
                    if (mMvpView != null){
                        mMvpView.setData(bean.getData());
                    }
                }else {
                    if (mMvpView != null){
                        mMvpView.toastShort(BaseApp.getRes().getString(R.string.get_verify_fail));
                    }
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
