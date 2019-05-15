package com.example.administrator.xiazoliuxing.net;

import com.example.administrator.xiazoliuxing.bean.Attention1;
import com.example.administrator.xiazoliuxing.bean.AttentionBean;
import com.example.administrator.xiazoliuxing.bean.BaiMiDetailedTreadBean;
import com.example.administrator.xiazoliuxing.bean.BandetailsBean;
import com.example.administrator.xiazoliuxing.bean.CollectBean;
import com.example.administrator.xiazoliuxing.bean.CollectBean2;
import com.example.administrator.xiazoliuxing.bean.Homepageadbean;
import com.example.administrator.xiazoliuxing.bean.Mingxingbean;
import com.example.administrator.xiazoliuxing.bean.PathInfoBean;
import com.example.administrator.xiazoliuxing.bean.QuxiaoBean;
import com.example.administrator.xiazoliuxing.bean.Yonghu;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface MyServer {
    String token="QU22nhySpxSDtz9uiKjR0XMkt9KvKkD994RQIwV6aJOvQh54gxG8o3OTlCLiymJMc3YchmfSC5vsG5ZfxGozEOMsDEs0JUGzPiJKmQccMCZEdZSEtvSeF3aHnyJvvWAbEVQ";

     String Url="http://api.banmi.com/";
    @GET("api/3.0/content/routesbundles")
    Observable<Homepageadbean>getData(@Query("page") int page,@Header("banmi-app-token") String s);



    @GET("api/3.0/banmi")
    Observable<Mingxingbean>getData1( @Header("banmi-app-token") String s);

    @GET("api/3.0/content/routes/{routeId}")
    Observable<PathInfoBean> getPathfo(@Header("banmi-app-token") String s, @Path("routeId") String id);
    //?page=1
    @POST("api/3.0/account/updateInfo")
    @FormUrlEncoded
    Observable<Mingxingbean> updateInfo(@Header("banmi-app-token") String s,
                                        @Field("userName") String username,
                                        @Field("description") String description,
                                        @Field("gender") String gender,
                                        @Field("photo") String photo);

    //用户信息
@GET("api/3.0/account/info")
Observable<Yonghu>getYonghu(@Header("banmi-app-token") String s);

    //搜藏
    @POST("api/3.0/content/routes/{routeId}/like")
    Observable<CollectBean> collect(
            @Header("banmi-app-token")String s,
            @Path("routeId") int id);
    //请求搜藏成功的数据
     @GET("api/3.0/account/collectedRoutes")
    Observable<CollectBean2> requestCollect(
            @Header("banmi-app-token")String s,
            @Query("page") int id);

     @POST("api/3.0/banmi/{banmiId}/follow")
     //关注
    Observable<AttentionBean> guanzhu(@Header("banmi-app-token")String s,
                                      @Path("banmiId") int id);
     //显示关注
    @GET("api/3.0/account/followedBanmi")
    Observable<Attention1>woguanzhu(@Header("banmi-app-token")String s);

    //伴米详情
    @GET("api/3.0/banmi/{banmiId}")
    Observable<BandetailsBean> detail(@Header("banmi-app-token")String s,
                                      @Path("banmiId") int id);

    @POST("api/3.0/content/routes/{banmiId}/dislike")
    Observable<QuxiaoBean>quxiao(@Header("banmi-app-token")String s,
                                 @Path("banmiId") int id);


    @GET("api/3.0/content/routes/{routeId}/reviews")
    Observable<BaiMiDetailedTreadBean> getBaiMiTread(@Header("banmi-app-token") String token, @Path("routeId") int id, @Query("page") int page);

}
