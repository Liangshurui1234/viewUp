package com.example.administrator.xiazoliuxing.presenter;

import com.example.administrator.xiazoliuxing.base.BasePresenter;
import com.example.administrator.xiazoliuxing.bean.Homepageadbean;
import com.example.administrator.xiazoliuxing.model.XianluM;
import com.example.administrator.xiazoliuxing.net.MyServer;
import com.example.administrator.xiazoliuxing.net.ResultCallBack;
import com.example.administrator.xiazoliuxing.view.main.XianluV;

public class XianluP extends BasePresenter<XianluV> {
    private XianluM xianluM;
    private String s= MyServer.token;
    @Override
    protected void initModel() {
        xianluM=new XianluM();
        mModels.add(xianluM);
    }
    public void getData(int i){
        xianluM.getData(i,s, new ResultCallBack<Homepageadbean>() {
            @Override
            public void onSuccess(Homepageadbean bean) {
                mMvpView.setData(bean);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
