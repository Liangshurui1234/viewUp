package com.example.administrator.xiazoliuxing.presenter;

import android.util.Log;

import com.example.administrator.xiazoliuxing.base.BasePresenter;
import com.example.administrator.xiazoliuxing.bean.Mingxingbean;
import com.example.administrator.xiazoliuxing.bean.Yonghu;
import com.example.administrator.xiazoliuxing.model.AmendModel;
import com.example.administrator.xiazoliuxing.net.ResultCallBack;
import com.example.administrator.xiazoliuxing.view.main.AmendView;

public class AmendPresenter extends BasePresenter<AmendView> {

    private AmendModel amendModel;

    @Override
    protected void initModel() {
        amendModel = new AmendModel();
        mModels.add(amendModel);
    }

    private static final String TAG = "AmendPresenter";
    public void getData(String s,String username,String description,String gender,String photo) {
        amendModel.getDate(s,username,description,gender,photo,new ResultCallBack<Mingxingbean>(){
            @Override
            public void onSuccess(Mingxingbean bean) {
                if (bean != null) {
                    Log.d(TAG, "onSuccess: "+ bean.toString());
                    if (bean.getCode() == 0) {
                        mMvpView.OnSuccess();
                    }
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
        public void getData1(){
        amendModel.setData(new ResultCallBack<Yonghu>() {
            @Override
            public void onSuccess(Yonghu bean) {
                mMvpView.setData(bean);
            }

            @Override
            public void onFail(String msg) {

            }
        });
        }



}
