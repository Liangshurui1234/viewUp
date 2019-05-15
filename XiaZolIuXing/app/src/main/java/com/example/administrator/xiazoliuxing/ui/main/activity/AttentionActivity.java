package com.example.administrator.xiazoliuxing.ui.main.activity;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.xiazoliuxing.R;
import com.example.administrator.xiazoliuxing.base.BaseActivity;
import com.example.administrator.xiazoliuxing.bean.Attention1;
import com.example.administrator.xiazoliuxing.presenter.AttentionP;
import com.example.administrator.xiazoliuxing.ui.main.adapter.Attentionadapter;
import com.example.administrator.xiazoliuxing.util.StatusBarManager;
import com.example.administrator.xiazoliuxing.view.main.AttentionV;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AttentionActivity extends BaseActivity<AttentionV, AttentionP> implements AttentionV {


    @BindView(R.id.lv)
    RecyclerView lv;
    private ArrayList<Attention1.ResultBean.BanmiBean>list;
    private Attentionadapter attentionadapter;

    @Override
    protected AttentionP initPresenter() {
        return new AttentionP();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_attention;
    }

    @Override
    public void setData(Attention1 attention1) {
    list.addAll(attention1.getResult().getBanmi());
    attentionadapter.setList(list);
    attentionadapter.notifyDataSetChanged();


    }

    @Override
    protected void initData() {
        mPresenter.getData();
    }

    @Override
    protected void initView() {
        int color = ContextCompat.getColor(this, R.color.c_eaeaea);
        StatusBarManager.setStatusBarColor(this,color);
        list=new ArrayList<>();
        attentionadapter = new Attentionadapter(list, this);
        lv.setAdapter(attentionadapter);
        lv.setLayoutManager(new LinearLayoutManager(this));
    }
}
