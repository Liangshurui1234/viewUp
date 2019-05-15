package com.example.administrator.xiazoliuxing.presenter;

import com.example.administrator.xiazoliuxing.base.BasePresenter;
import com.example.administrator.xiazoliuxing.bean.BandetailsBean;
import com.example.administrator.xiazoliuxing.model.BandetailsM;
import com.example.administrator.xiazoliuxing.net.MyServer;
import com.example.administrator.xiazoliuxing.net.ResultCallBack;
import com.example.administrator.xiazoliuxing.view.main.BandetailsV;

public class Bandetailsp extends BasePresenter<BandetailsV> {
    private BandetailsM bandetailsM;
    private String s= MyServer.token;
    @Override
    protected void initModel() {
        bandetailsM=new BandetailsM();
        mModels.add(bandetailsM);
    }
    public void getData(int id){
        bandetailsM.getData(s,id, new ResultCallBack<BandetailsBean>() {
            @Override
            public void onSuccess(BandetailsBean bean) {
                    mMvpView.setData(bean);
            }

            @Override
            public void onFail(String msg) {

            }
        });
    }
}
