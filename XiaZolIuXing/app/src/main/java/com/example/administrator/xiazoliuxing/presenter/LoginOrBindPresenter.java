package com.example.administrator.xiazoliuxing.presenter;


import android.util.Log;
import com.example.administrator.xiazoliuxing.R;
import com.example.administrator.xiazoliuxing.base.BaseApp;
import com.example.administrator.xiazoliuxing.base.BasePresenter;
import com.example.administrator.xiazoliuxing.base.Constants;
import com.example.administrator.xiazoliuxing.bean.LoginInfo;
import com.example.administrator.xiazoliuxing.bean.VerifyCodeBean;
import com.example.administrator.xiazoliuxing.model.LoginModel;
import com.example.administrator.xiazoliuxing.model.LoginOrBindModel;
import com.example.administrator.xiazoliuxing.net.ApiService;
import com.example.administrator.xiazoliuxing.net.ResultCallBack;

import com.example.administrator.xiazoliuxing.util.SpUtil;
import com.example.administrator.xiazoliuxing.util.ToastUtil;
import com.example.administrator.xiazoliuxing.view.main.LoginOrBindView;

import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.Map;


public class LoginOrBindPresenter extends BasePresenter<LoginOrBindView> {
    private static final String TAG = "LoginOrBindPresenter";
    private LoginOrBindModel mLoginOrBindModel;
    private LoginModel mLoginModel;

    @Override
    protected void initModel() {
        mLoginOrBindModel = new LoginOrBindModel();
        mModels.add(mLoginOrBindModel);
        mLoginModel = new LoginModel();
        mModels.add(mLoginModel);
    }

    public void oauthLogin(SHARE_MEDIA type) {
        UMShareAPI umShareAPI = UMShareAPI.get(mMvpView.getAct());
        //media,三方平台
        //authListener,登录回调
        umShareAPI.getPlatformInfo(mMvpView.getAct(), type, authListener);
    }

    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            logMap(data);
            //login();
            //只写微博的,微信的成功不了
            if (platform == SHARE_MEDIA.SINA){
                String uid = data.get("uid");
                loginSina(uid);
                Log.e(TAG, "onComplete: id"+uid);

            }
        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            ToastUtil.showShort(BaseApp.getRes().getString(R.string.oauth_error));
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            ToastUtil.showShort(BaseApp.getRes().getString(R.string.oauth_cancel));
        }
    };

    /**
     * 新浪微博登录
     * @param uid
     */
    private void loginSina(String uid) {
        mLoginOrBindModel.loginSina(uid, new ResultCallBack<LoginInfo>() {
            @Override
            public void onSuccess(LoginInfo bean) {
                //登录成功了,需要做什么
                //1.跳转主页面
                //2.保存用户信息
                if (bean.getResult() != null) {
                    saveUserInfo(bean.getResult());
                    if (mMvpView != null){
                        mMvpView.toastShort(BaseApp.getRes().getString(R.string.login_success));
                        mMvpView.go2MainActivity();
                    }
                }else {
                    if (mMvpView != null){
                        mMvpView.toastShort(BaseApp.getRes().getString(R.string.login_fail));
                    }
                }
            }

            @Override
            public void onFail(String msg) {
                if (mMvpView != null){
                    mMvpView.toastShort(msg);
                }
            }
        });
    }

    /**
     * 保存用户信息
     * @param result
     */
    private void saveUserInfo(LoginInfo.ResultBean result) {
        SpUtil.setParam(Constants.TOKEN,result.getToken());
        SpUtil.setParam(Constants.DESC,result.getDescription());
        SpUtil.setParam(Constants.USERNAME,result.getUserName());
        SpUtil.setParam(Constants.GENDER,result.getGender());
        SpUtil.setParam(Constants.EMAIL,result.getEmail());
        SpUtil.setParam(Constants.PHOTO,result.getPhoto());
        SpUtil.setParam(Constants.PHONE,result.getPhone());
    }

    private void logMap(Map<String, String> data) {
        for (Map.Entry<String, String> entry:data.entrySet()){
            Log.d(TAG, "logMap: "+entry.getKey()+","+entry.getValue());
        }
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
