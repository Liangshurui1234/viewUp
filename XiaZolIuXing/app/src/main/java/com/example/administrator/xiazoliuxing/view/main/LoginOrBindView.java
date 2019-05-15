package com.example.administrator.xiazoliuxing.view.main;


import android.app.Activity;

import com.example.administrator.xiazoliuxing.base.BaseMvpView;


public interface LoginOrBindView extends BaseMvpView {
    String getPhone();
    Activity getAct();
    void setData(String code);
    void go2MainActivity();
}
