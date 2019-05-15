package com.example.administrator.xiazoliuxing.presenter;

import com.example.administrator.xiazoliuxing.base.BaseMvpView;
import com.example.administrator.xiazoliuxing.base.BasePresenter;
import com.example.administrator.xiazoliuxing.bean.AttentionBean;
import com.example.administrator.xiazoliuxing.bean.Mingxingbean;
import com.example.administrator.xiazoliuxing.model.Banmim;
import com.example.administrator.xiazoliuxing.net.MyServer;
import com.example.administrator.xiazoliuxing.net.ResultCallBack;
import com.example.administrator.xiazoliuxing.view.main.BanmiV;

public class Banmip extends BasePresenter<BanmiV> {
    private Banmim banmim;

    String s= MyServer.token;
    @Override
    protected void initModel() {
    banmim=new Banmim();
    mModels.add(banmim);
    }
    public void getData(int page){
        banmim.getData(page,s, new ResultCallBack<Mingxingbean>() {
            @Override
            public void onSuccess(Mingxingbean bean) {
                if(bean!=null){
                    if(mMvpView!=null){
                        mMvpView.setData(bean);
                    }
                }
            }

            @Override
            public void onFail(String msg) {

            }
        });

        }
    public void guanzhu(int id){
        banmim.guanzhu(id,s, new ResultCallBack<AttentionBean>() {
            @Override
            public void onSuccess(AttentionBean bean) {
                mMvpView.setguanzhu(bean);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }




    }
