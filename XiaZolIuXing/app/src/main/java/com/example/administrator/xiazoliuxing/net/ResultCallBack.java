package com.example.administrator.xiazoliuxing.net;



public interface ResultCallBack<T> {
    void onSuccess(T bean);
    void onFail(String msg);
}
