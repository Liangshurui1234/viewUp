package com.example.administrator.xiazoliuxing.presenter;

import com.example.administrator.xiazoliuxing.base.BasePresenter;
import com.example.administrator.xiazoliuxing.bean.Attention1;
import com.example.administrator.xiazoliuxing.model.AttentionM;
import com.example.administrator.xiazoliuxing.net.MyServer;
import com.example.administrator.xiazoliuxing.net.ResultCallBack;
import com.example.administrator.xiazoliuxing.view.main.AttentionV;

public class AttentionP extends BasePresenter<AttentionV> {
    private AttentionM attentionM;
    private String s= MyServer.token;
    @Override
    protected void initModel() {
        attentionM=new AttentionM();
        mModels.add(attentionM);
    }
    public void getData(){
        attentionM.getData(s, new ResultCallBack<Attention1>() {
            @Override
            public void onSuccess(Attention1 bean) {
                mMvpView.setData(bean);

            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
