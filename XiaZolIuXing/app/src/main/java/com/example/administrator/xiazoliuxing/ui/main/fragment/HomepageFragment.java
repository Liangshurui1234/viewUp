package com.example.administrator.xiazoliuxing.ui.main.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.administrator.xiazoliuxing.R;
import com.example.administrator.xiazoliuxing.bean.Homepageadbean;
import com.example.administrator.xiazoliuxing.net.MyServer;
import com.example.administrator.xiazoliuxing.ui.main.adapter.Homepageadapter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomepageFragment extends Fragment {


    private RecyclerView mLv;

    private ArrayList<Homepageadbean.ResultBean.BannersBean> beans;
    private ArrayList<Homepageadbean.ResultBean.RoutesBean> list;
    private Homepageadapter homepageadapter;
    int page = 1;
    private SmartRefreshLayout mRefreshLayout;

    public HomepageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_homepage, container, false);
        initView(inflate);
        initData();
        return inflate;
    }

    private void initData() {
        Retrofit build = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(MyServer.Url)
                .build();
        MyServer myServer = build.create(MyServer.class);
        //HashMap<String, String> map = new HashMap<>();
        // map.put("banmi-app-token","JVy0IvZamK7f7FBZLKFtoniiixKMlnnJ6dWZ6NlsY4HGsxcAA9qvFo8yacHCKHE8YAcd0UF9L59nEm7zk9AUixee0Hl8EeWA880c0ikZBW0KEYuxQy5Z9NP3BNoBi3o3Q0g");
        Observable<Homepageadbean> data = myServer.getData(page, MyServer.token);
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Homepageadbean>() {
                    @Override
                    public void accept(Homepageadbean homepageadbean) throws Exception {
                        beans.addAll(homepageadbean.getResult().getBanners());
                        list.addAll(homepageadbean.getResult().getRoutes());
                        homepageadapter.setList(list);
                        homepageadapter.notifyDataSetChanged();

                    }
                });

    }

    private void initView(View inflate) {
        mLv = (RecyclerView) inflate.findViewById(R.id.lv);
        beans = new ArrayList<>();
        list = new ArrayList<>();
        homepageadapter = new Homepageadapter(list,beans, getActivity());
        mLv.setAdapter(homepageadapter);
        mLv.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRefreshLayout = (SmartRefreshLayout) inflate.findViewById(R.id.refreshLayout);
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                page ++ ;
                initData();
                homepageadapter.notifyDataSetChanged();
                refreshLayout.finishLoadMore(true);//加载完成
            }
        });
        //刷新
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
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
}
