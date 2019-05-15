package com.example.administrator.xiazoliuxing.presenter;

import com.example.administrator.xiazoliuxing.base.BasePresenter;
import com.example.administrator.xiazoliuxing.bean.CollectBean;
import com.example.administrator.xiazoliuxing.bean.PathInfoBean;
import com.example.administrator.xiazoliuxing.model.PathInfoM;
import com.example.administrator.xiazoliuxing.net.ResultCallBack;
import com.example.administrator.xiazoliuxing.view.main.PathInfoV;



public class PathInfoP extends BasePresenter<PathInfoV> {

    private PathInfoM mModel;

    @Override
    protected void initModel() {
        mModel = new PathInfoM();
        mModels.add(mModel);
    }

    public void getPathInfo(String token,String id){
        mModel.getPathInfo(token,id, new ResultCallBack<PathInfoBean>() {
            @Override
            public void onSuccess(PathInfoBean bean) {
                mMvpView.success(bean);
            }

            @Override
            public void onFail(String msg) {
            }
        });
    }

    public void collect(int id1) {
        mModel.collect(id1, new ResultCallBack<CollectBean>() {
            @Override
            public void onSuccess(CollectBean bean) {
                mMvpView.success1(bean);
            }

            @Override
            public void onFail(String msg) {
                mMvpView.onFiled(msg);
            }
        });
    }
}
