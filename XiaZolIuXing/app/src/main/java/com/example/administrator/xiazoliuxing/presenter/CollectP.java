package com.example.administrator.xiazoliuxing.presenter;

import com.example.administrator.xiazoliuxing.base.BasePresenter;
import com.example.administrator.xiazoliuxing.bean.CollectBean2;
import com.example.administrator.xiazoliuxing.model.CollectM;
import com.example.administrator.xiazoliuxing.net.MyServer;
import com.example.administrator.xiazoliuxing.net.ResultCallBack;
import com.example.administrator.xiazoliuxing.view.main.CollectV;

public class CollectP extends BasePresenter<CollectV> {
    private CollectM collectM;
    String s= MyServer.token;
    @Override
    protected void initModel() {
    collectM=new CollectM();
    mModels.add(collectM);
    }
    public void getData(int page){
        collectM.setData(page, s, new ResultCallBack<CollectBean2>() {
                    @Override
                    public void onSuccess(CollectBean2 bean) {
                        mMvpView.setData(bean);
                    }

                    @Override
                    public void onFail(String msg) {

                    }
                });
    }
}
