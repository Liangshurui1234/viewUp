package com.example.administrator.xiazoliuxing.ui.main.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.xiazoliuxing.R;
import com.example.administrator.xiazoliuxing.base.BaseFragment;
import com.example.administrator.xiazoliuxing.bean.Homepageadbean;
import com.example.administrator.xiazoliuxing.presenter.XianluP;
import com.example.administrator.xiazoliuxing.ui.main.adapter.Homepageadapter;
import com.example.administrator.xiazoliuxing.view.main.XianluV;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class XianluFragment extends BaseFragment<XianluV, XianluP> implements XianluV {

    @BindView(R.id.lv)
    RecyclerView lv;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;
    Unbinder unbinder;
    private int page=1;
    private ArrayList<Homepageadbean.ResultBean.BannersBean> beans;
    private ArrayList<Homepageadbean.ResultBean.RoutesBean> list;
    private Homepageadapter homepageadapter;
    public XianluFragment() {

    }

    @Override
    protected XianluP initPresenter() {
        return new XianluP();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_xianlu;
    }

    @Override
    protected void initData() {
        mPresenter.getData(page);
    }

    @Override
    protected void initView() {
        list = new ArrayList<>();
        beans = new ArrayList<>();
        homepageadapter = new Homepageadapter(list, beans, getActivity());
        lv.setAdapter(homepageadapter);
        lv.setLayoutManager(new LinearLayoutManager(getActivity()));
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page++;
                initData();
                homepageadapter.notifyDataSetChanged();
                refreshLayout.finishLoadMore(true);//加载完成
            }
        });
        //刷新
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                list.clear();
                beans.clear();
                initData();
                homepageadapter.notifyDataSetChanged();
                refreshLayout.finishRefresh(true);//刷新完成
            }
        });
    }

    @Override
    public void setData(Homepageadbean homepageadbean) {
        beans.addAll(homepageadbean.getResult().getBanners());
        list.addAll(homepageadbean.getResult().getRoutes());
        homepageadapter.setList(list);
        homepageadapter.notifyDataSetChanged();
    }

}
