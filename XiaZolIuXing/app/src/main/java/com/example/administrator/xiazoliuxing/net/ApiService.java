package com.example.administrator.xiazoliuxing.net;



import com.example.administrator.xiazoliuxing.bean.VerifyCodeBean;

import io.reactivex.Observable;
import retrofit2.http.GET;



public interface ApiService {
    String sBaseUrl = "http://yun918.cn/study/public/index.php/";
    int SUCCESS_CODE =200;

    /**
     * 获取验证码
     * @return
     */
    @GET("verify")
    Observable<VerifyCodeBean> getVerifyCode();

}
