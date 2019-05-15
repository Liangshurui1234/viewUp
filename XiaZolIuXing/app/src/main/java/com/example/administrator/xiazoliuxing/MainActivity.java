package com.example.administrator.xiazoliuxing;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.xiazoliuxing.base.BaseActivity;
import com.example.administrator.xiazoliuxing.bean.Yonghu;
import com.example.administrator.xiazoliuxing.presenter.AmendPresenter;
import com.example.administrator.xiazoliuxing.presenter.EmptyPresenter;
import com.example.administrator.xiazoliuxing.ui.main.activity.AttentionActivity;
import com.example.administrator.xiazoliuxing.ui.main.activity.CollectActivity;
import com.example.administrator.xiazoliuxing.ui.main.activity.MymessageActivity;
import com.example.administrator.xiazoliuxing.ui.main.adapter.Framentadapter;
import com.example.administrator.xiazoliuxing.ui.main.fragment.BanmiFragment;
import com.example.administrator.xiazoliuxing.ui.main.fragment.HomepageFragment;
import com.example.administrator.xiazoliuxing.util.StatusBarManager;
import com.example.administrator.xiazoliuxing.view.main.AmendView;
import com.example.administrator.xiazoliuxing.view.main.EmptyView;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity<AmendView, AmendPresenter> implements AmendView, View.OnClickListener {


    @BindView(R.id.taoob)
    Toolbar mTaoob;
    @BindView(R.id.imgh)
    ImageView mImgh;
    @BindView(R.id.view)
    ViewPager mView;
    @BindView(R.id.tab)
    TabLayout mTab;
    @BindView(R.id.nav)
    NavigationView mNav;
    @BindView(R.id.draw)
    DrawerLayout mDraw;
    @BindView(R.id.rl)
    RelativeLayout mRl;

    private ArrayList<Fragment> list;
    private ImageView img;
    private TextView text_1;
    private TextView text_2;

    public static void startAct(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected AmendPresenter initPresenter() {
        return new AmendPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    public void initView() {
        int color = ContextCompat.getColor(this, R.color.c_eaeaea);
        StatusBarManager.setStatusBarColor(this,color);
        mImgh.setOnClickListener(this);
        mTab.addTab(mTab.newTab().setText("首页").setIcon(R.mipmap.shoye));
        mTab.addTab(mTab.newTab().setText("伴米").setIcon(R.mipmap.banmi_unselected));
        list = new ArrayList<>();
        list.add(new HomepageFragment());
        list.add(new BanmiFragment());
        Framentadapter framentadapter = new Framentadapter(getSupportFragmentManager(), list);
        mView.setAdapter(framentadapter);
        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mView.setCurrentItem(tab.getPosition());
                int position = tab.getPosition();
                mView.setCurrentItem(position);
                if (position == 0){
                    mTab.getTabAt(0).setIcon(R.mipmap.home_highlight);
                    mTab.getTabAt(1).setIcon(R.mipmap.banmi_unselected);
                }
                if (position == 1){
                    mTab.getTabAt(0).setIcon(R.mipmap.home_unselected);
                    mTab.getTabAt(1).setIcon(R.mipmap.banmi_highlight);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        mView.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTab));
        mTaoob.setTitle("");
        setSupportActionBar(mTaoob);
        View headerView = mNav.getHeaderView(0);
        text_1 = headerView.findViewById(R.id.text_1);
        text_2 = headerView.findViewById(R.id.text_2);
        RelativeLayout shoucang = headerView.findViewById(R.id.shoucang);

        shoucang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CollectActivity.class);
                startActivity(intent);
            }
        });

        RelativeLayout guanzhui = headerView.findViewById(R.id.guanzhu);
        guanzhui.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AttentionActivity.class);
                startActivity(intent);
            }
        });

        View viewById = headerView.findViewById(R.id.bianji);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MymessageActivity.class);
                startActivity(intent);
            }
        });
        img = headerView.findViewById(R.id.img);
        img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MymessageActivity.class);
                startActivity(intent);
            }
        });

        mPresenter.getData1();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.imgh:
                mDraw.openDrawer(Gravity.LEFT);
                break;
        }
    }

    @Override
    public void OnSuccess() {

    }

    @Override
    public void setData(Yonghu yonghu) {
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        Glide.with(this).load(yonghu.getResult().getPhoto()).apply(requestOptions).into(img);
        Glide.with(this).load(yonghu.getResult().getPhoto()).apply(requestOptions).into(mImgh);
        text_1.setText(yonghu.getResult().getUserName());
        text_2.setText(yonghu.getResult().getDescription());
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        mPresenter.getData1();
    }
}
