package com.example.administrator.xiazoliuxing.view.main;


import com.example.administrator.xiazoliuxing.base.BaseMvpView;
import com.example.administrator.xiazoliuxing.bean.CollectBean;
import com.example.administrator.xiazoliuxing.bean.PathInfoBean;

public interface PathInfoV extends BaseMvpView {
    void success(PathInfoBean bean);
    void success1(CollectBean bean);

    void onFiled(String msg);
}
