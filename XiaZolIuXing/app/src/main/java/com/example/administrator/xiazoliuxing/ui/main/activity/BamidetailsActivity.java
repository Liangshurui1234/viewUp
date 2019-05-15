package com.example.administrator.xiazoliuxing.ui.main.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.example.administrator.xiazoliuxing.R;
import com.example.administrator.xiazoliuxing.base.BaseActivity;
import com.example.administrator.xiazoliuxing.bean.BandetailsBean;
import com.example.administrator.xiazoliuxing.presenter.Bandetailsp;
import com.example.administrator.xiazoliuxing.ui.main.adapter.Framentadapter;
import com.example.administrator.xiazoliuxing.ui.main.fragment.DongtaiFragment;
import com.example.administrator.xiazoliuxing.ui.main.fragment.XianluFragment;
import com.example.administrator.xiazoliuxing.util.StatusBarManager;
import com.example.administrator.xiazoliuxing.view.main.BandetailsV;
import java.util.ArrayList;
import butterknife.BindView;

public class BamidetailsActivity extends BaseActivity<BandetailsV, Bandetailsp> implements BandetailsV {


    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.toolBar_title)
    TextView toolBarTitle;
    @BindView(R.id.iv_share)
    ImageView ivShare;
    @BindView(R.id.iv_head)
    ImageView ivHead;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.iv_care)
    ImageView ivCare;
    @BindView(R.id.tv_location)
    TextView tvLocation;
    @BindView(R.id.tv_type)
    TextView tvType;
    @BindView(R.id.tv_content)
    TextView tvContent;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.vp)
    FrameLayout vp;
    private int id;
    private ArrayList<Fragment> list;

    @Override
    protected Bandetailsp initPresenter() {
        return new Bandetailsp();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bamidetails;
    }

    @Override
    protected void initView() {
        int color = ContextCompat.getColor(this, R.color.c_eaeaea);
        StatusBarManager.setStatusBarColor(this, color);
        Intent intent = getIntent();
        list = new ArrayList<>();
        id = intent.getIntExtra("id", 2);
        tab.addTab(tab.newTab().setText("动态"));
        tab.addTab(tab.newTab().setText("线路"));
        list.add(new DongtaiFragment(id));
        list.add(new XianluFragment());


        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                switch (tab.getPosition()){
                    case 0:
                        switchFragment(0);
                        break;
                    case 1:
                        switchFragment(1);
                        break;
                }


            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    private int lastPosition = 0;

    private void switchFragment(int type) {
        FragmentManager manager = getSupportFragmentManager();
        Fragment fragment = list.get(type);
        FragmentTransaction tran = manager.beginTransaction();
        if (!fragment.isAdded()) {
            tran.add(R.id.vp, fragment);
        }
        tran.hide(list.get(lastPosition));
        tran.show(fragment);
        tran.commit();
        lastPosition = type;
    }
    @Override
    protected void initData() {
        mPresenter.getData(id);
    }

    @Override
    public void setData(BandetailsBean bandetailsBean) {
        Glide.with(this).load(bandetailsBean.getResult().getBanmi().getPhoto()).into(ivHead);
        tvName.setText(bandetailsBean.getResult().getBanmi().getName());
        tvLocation.setText(bandetailsBean.getResult().getBanmi().getLocation());
        tvType.setText(bandetailsBean.getResult().getBanmi().getOccupation());
        tvContent.setText(bandetailsBean.getResult().getBanmi().getIntroduction());
    }
}
