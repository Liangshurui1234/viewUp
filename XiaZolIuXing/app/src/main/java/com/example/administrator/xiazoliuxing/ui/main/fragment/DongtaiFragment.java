package com.example.administrator.xiazoliuxing.ui.main.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.administrator.xiazoliuxing.R;
import com.example.administrator.xiazoliuxing.bean.BaiMiDetailedTreadBean;
import com.example.administrator.xiazoliuxing.net.BaseObserver;
import com.example.administrator.xiazoliuxing.net.HttpUtils;
import com.example.administrator.xiazoliuxing.net.MyServer;
import com.example.administrator.xiazoliuxing.net.RxUtils;
import com.example.administrator.xiazoliuxing.ui.main.adapter.BaiMiDetailedAndTabRlvAdapter;

import java.util.ArrayList;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;


public class DongtaiFragment extends Fragment {

    private RecyclerView mLv;
    private ArrayList<BaiMiDetailedTreadBean.ResultBean.ReviewsBean> mArr;
    private BaiMiDetailedAndTabRlvAdapter mAdapter;
    private int page = 1;

    public DongtaiFragment() {
        // Required empty public constructor
    }

    public int id;

    @SuppressLint("ValidFragment")
    public DongtaiFragment(int id) {
        this.id = id;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_dongtai, container, false);
        initView(inflate);
        getBaiMiTread(MyServer.token, id, page);
        return inflate;

    }

    public void getBaiMiTread(String tokenContent, int id, int page) {
        MyServer api = HttpUtils.getInstance().getApiserver(MyServer.Url, MyServer.class);
        Observable<BaiMiDetailedTreadBean> baiMiTread = api.getBaiMiTread(tokenContent, id, page);
        baiMiTread.compose(RxUtils.<BaiMiDetailedTreadBean>rxObserableSchedulerHelper())
                .subscribe(new BaseObserver<BaiMiDetailedTreadBean>() {
                    @Override
                    public void onNext(final BaiMiDetailedTreadBean baiMiDetailedTreadBean) {
                        if (baiMiDetailedTreadBean != null) {
                            mArr.addAll(baiMiDetailedTreadBean.getResult().getReviews());
                            mAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void error(String msg) {

                    }

                    @Override
                    protected void subscribe(Disposable d) {

                    }
                });
    }

    private void initView(View inflate) {
        mLv = (RecyclerView) inflate.findViewById(R.id.lv);
        mArr = new ArrayList<>();
        mAdapter = new BaiMiDetailedAndTabRlvAdapter(mArr, getActivity());
        mLv.setAdapter(mAdapter);
        mLv.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
}
