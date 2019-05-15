package com.example.administrator.xiazoliuxing.view.main;

import com.example.administrator.xiazoliuxing.base.BaseMvpView;
import com.example.administrator.xiazoliuxing.bean.AttentionBean;
import com.example.administrator.xiazoliuxing.bean.Mingxingbean;

public interface BanmiV extends BaseMvpView {
    void setData(Mingxingbean mingxingbean);

    void setguanzhu(AttentionBean bean);
}
