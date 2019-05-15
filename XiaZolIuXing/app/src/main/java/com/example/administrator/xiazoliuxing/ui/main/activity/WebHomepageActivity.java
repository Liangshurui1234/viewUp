package com.example.administrator.xiazoliuxing.ui.main.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.webkit.WebView;

import com.example.administrator.xiazoliuxing.R;
import com.example.administrator.xiazoliuxing.base.BaseActivity;
import com.example.administrator.xiazoliuxing.net.JsAndroid;
import com.example.administrator.xiazoliuxing.presenter.EmptyPresenter;
import com.example.administrator.xiazoliuxing.util.StatusBarManager;
import com.example.administrator.xiazoliuxing.view.main.EmptyView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WebHomepageActivity extends BaseActivity<EmptyView, EmptyPresenter> implements EmptyView {

    @BindView(R.id.view)
    WebView view;

    @Override
    protected EmptyPresenter initPresenter() {
        return new EmptyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_web_homepage;
    }

    @Override
    protected void initView() {
        int color = ContextCompat.getColor(this, R.color.c_eaeaea);
        StatusBarManager.setStatusBarColor(this,color);
        Intent intent = getIntent();
        String id = intent.getStringExtra("id");
        view.loadUrl(id);
        view.getSettings().setJavaScriptEnabled(true);
        view.addJavascriptInterface(new JsAndroid(this),"android");

    }
}
