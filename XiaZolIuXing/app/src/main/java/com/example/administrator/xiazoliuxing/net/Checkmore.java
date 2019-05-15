package com.example.administrator.xiazoliuxing.net;



import com.example.administrator.xiazoliuxing.bean.CheckBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface Checkmore {
    public String baseUrl="http://api.banmi.com/api/3.0/";
    @GET("content/bundles")
    Observable<CheckBean> getCheck(@Header("token") String head);

}
