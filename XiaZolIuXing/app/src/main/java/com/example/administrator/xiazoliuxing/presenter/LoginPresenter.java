package com.example.administrator.xiazoliuxing.presenter;


import com.example.administrator.xiazoliuxing.base.BasePresenter;
import com.example.administrator.xiazoliuxing.bean.VerifyCodeBean;
import com.example.administrator.xiazoliuxing.model.LoginModel;
import com.example.administrator.xiazoliuxing.net.ResultCallBack;
import com.example.administrator.xiazoliuxing.view.main.LoginView;



public class LoginPresenter extends BasePresenter<LoginView> {
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

            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
