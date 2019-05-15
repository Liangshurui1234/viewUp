package com.example.administrator.xiazoliuxing.ui.main.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.xiazoliuxing.R;
import com.example.administrator.xiazoliuxing.base.BaseActivity;
import com.example.administrator.xiazoliuxing.base.Constants;
import com.example.administrator.xiazoliuxing.bean.CollectBean;
import com.example.administrator.xiazoliuxing.bean.PathInfoBean;
import com.example.administrator.xiazoliuxing.net.MyServer;
import com.example.administrator.xiazoliuxing.presenter.PathInfoP;
import com.example.administrator.xiazoliuxing.ui.main.adapter.PathInfoRlvAdapter;
import com.example.administrator.xiazoliuxing.util.SpUtil;
import com.example.administrator.xiazoliuxing.util.StatusBarManager;
import com.example.administrator.xiazoliuxing.util.ToastUtil;
import com.example.administrator.xiazoliuxing.view.main.PathInfoV;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PathInfoActivity extends BaseActivity<PathInfoV, PathInfoP> implements PathInfoV, View.OnClickListener {


    private TextView mTvCity;
    private TextView mTvNow;
    private TextView mTvTitle;
    private ImageView mIvClose;
    private Banner mBanner;
    private ImageView mIvTou;
    private TextView mTvName;
    private TextView mTvJop;
    private TextView mTvCityNow;
    private TextView mTvText;
    private RecyclerView mRlv;
    private TextView mTvAll;
    /**
     * 分享
     */
    private Button mBtShare;
    /**
     * 收藏
     */
    private Button mBtCollect;
    /**
     * 预览路线
     */
    private Button mBtPath;
    private Button mBtBuy;
    private String mToken;
    private PathInfoRlvAdapter mAdapter;
    private int id;
    private int id1;
    private PathInfoBean bean1;
    private List<String> banner;

    @Override
    protected void initView() {
        int color = ContextCompat.getColor(this, R.color.c_eaeaea);
        StatusBarManager.setStatusBarColor(this,color);
        mToken = (String) SpUtil.getParam(Constants.TOKEN, "");
        Intent intent = getIntent();
        id1 = intent.getIntExtra("id", 0);
        mTvCity = findViewById(R.id.tv_city);
        mTvNow = findViewById(R.id.tv_now);
        mTvTitle = findViewById(R.id.tv_title);
        mIvClose = findViewById(R.id.iv_close);
        mIvClose.setOnClickListener(this);
        mBanner = findViewById(R.id.banner);
        mIvTou = findViewById(R.id.iv_tou);
        mTvName = findViewById(R.id.tv_name);
        mTvJop = findViewById(R.id.tv_jop);
        mTvCityNow = findViewById(R.id.tv_city_now);
        mTvText = findViewById(R.id.tv_text);
        mRlv = findViewById(R.id.rlv);
        mTvAll = findViewById(R.id.tv_all);
        mTvAll.setOnClickListener(this);
        mBtShare = findViewById(R.id.bt_share);
        mBtShare.setOnClickListener(this);
        mBtCollect = findViewById(R.id.bt_collect);
        mBtCollect.setOnClickListener(this);
        mBtPath = findViewById(R.id.bt_path);
        mBtPath.setOnClickListener(this);
        mBtBuy = findViewById(R.id.bt_buy);
        mBtBuy.setOnClickListener(this);

        mPresenter.getPathInfo(MyServer.token, id1 +"");
        mRlv.setLayoutManager(new LinearLayoutManager(this));
        ArrayList<PathInfoBean.ResultBean.ReviewsBean> list = new ArrayList<>();
        mAdapter = new PathInfoRlvAdapter(list);
        mRlv.setAdapter(mAdapter);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_path_info;
    }

    @SuppressLint("SetTextI18n")
    private void initData(PathInfoBean bean) {
        bean1 = bean;
        banner = bean.getResult().getCarousel();
        mBanner.setImages(banner).setImageLoader(new ImageLoader() {
            @Override
            public void displayImage(Context context, Object path, ImageView imageView) {
                Glide.with(context).load(path).into(imageView);
            }
        }).start();
        mTvCity.setText(bean.getResult().getRoute().getCity());
        mTvNow.setText(bean.getResult().getRoute().getTitle());
        mTvTitle.setText(bean.getResult().getRoute().getIntro());
        mTvCityNow.setText(bean.getResult().getBanmi().getLocation());
        mTvName.setText(bean.getResult().getBanmi().getName());
        RequestOptions request = new RequestOptions().circleCrop();
        Glide.with(PathInfoActivity.this).load(bean.getResult().getBanmi().getPhoto()).apply(request).into(mIvTou);
        mTvJop.setText(bean.getResult().getBanmi().getOccupation());
        mTvText.setText(bean.getResult().getBanmi().getIntroduction());
        mBtBuy.setText("¥ "+bean.getResult().getRoute().getPriceInCents());
        mAdapter.addData(bean.getResult().getReviews());

        if (Build.VERSION.SDK_INT >= 23) {
            String[] mPermissionList = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.CALL_PHONE, Manifest.permission.READ_LOGS, Manifest.permission.READ_PHONE_STATE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.SET_DEBUG_APP, Manifest.permission.SYSTEM_ALERT_WINDOW, Manifest.permission.GET_ACCOUNTS, Manifest.permission.WRITE_APN_SETTINGS};
            ActivityCompat.requestPermissions(this, mPermissionList, 123);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
    }
    @Override
    protected PathInfoP initPresenter() {
        return new PathInfoP();
    }


    @Override
    public void toastShort(String string) {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.iv_close:
                break;
            case R.id.bt_share:
                shareNoBorad(SHARE_MEDIA.QQ);
                break;
            case R.id.bt_collect:

                mPresenter.collect(id1);

                break;
            case R.id.bt_path:

                break;
            case R.id.bt_buy:
                break;
            case R.id.tv_all:
                Intent intent = new Intent(PathInfoActivity.this, LookAllActivity.class);
                intent.putExtra("id",id1);
                startActivity(intent);

                break;
        }
    }

    @Override
    public void success(PathInfoBean bean) {
        initData(bean);
    }

    @Override
    public void success1(CollectBean bean) {
        String desc = bean.getDesc();
        ToastUtil.showShort(desc);

    }

    @Override
    public void onFiled(String msg) {
        ToastUtil.showShort(msg);
    }
    UMAuthListener authListener = new UMAuthListener() {
        /**
         * @desc 授权开始的回调
         * @param platform 平台名称
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @desc 授权成功的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param data 用户资料返回
         */
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            log(data);
            Toast.makeText(PathInfoActivity.this, "成功了", Toast.LENGTH_LONG).show();

        }

        /**
         * @desc 授权失败的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {

            Toast.makeText(PathInfoActivity.this, "失败：" + t.getMessage(),Toast.LENGTH_LONG).show();
        }

        /**
         * @desc 授权取消的回调
         * @param platform 平台名称
         * @param action 行为序号，开发者用不上
         */
        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText(PathInfoActivity.this, "取消了", Toast.LENGTH_LONG).show();
        }
    };
    private void log(Map<String,String>map){
        for(Map.Entry<String,String> set : map.entrySet()){
            String key=set.getKey();
            String value = set.getValue();
            Log.d("tag","key:"+key +"value:"+value);
        }
    }





    public void login(SHARE_MEDIA media) {
        UMShareAPI umShareAPI = UMShareAPI.get(this);
        umShareAPI.getPlatformInfo(PathInfoActivity.this, media, authListener);
    }

    public void shareNoBorad(SHARE_MEDIA share_media) {
        UMImage image = new UMImage(PathInfoActivity.this,
               banner.get(0));//网络图片
        image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
        new ShareAction(PathInfoActivity.this)
                .setPlatform(share_media)//传入平台
                .withText("hello")//分享内容
                .withMedia(image)
                .setCallback(shareListener)//回调监听器
                .share();
    }


    /**
     * 带面板的分享
     * 未改appkey和appId
     * 新浪失败
     * qq不用自己的appkey和appId,也能成功,但是以后也一定用自己的
     * wechat,肯定不成功
     */
    private void shareBoard() {
        UMImage image = new UMImage(PathInfoActivity.this, "http://ww1.sinaimg" +
                ".cn/large/7a8aed7bgw1esq1fnt1s1j20h10pd0us.jpg");//网络图片
        image.compressStyle = UMImage.CompressStyle.QUALITY;//质量压缩，适合长图的分享
        new ShareAction(PathInfoActivity.this).withText("hello")
                .withMedia(image)
                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QQ, SHARE_MEDIA.WEIXIN)
                .setCallback(shareListener).open();
    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {

        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(PathInfoActivity.this, "成功了", Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(PathInfoActivity.this, "失败" + t.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(PathInfoActivity.this, "取消了",
                    Toast.LENGTH_LONG).show();

        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        UMShareAPI.get(this).release();
    }

}
