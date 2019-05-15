package com.example.administrator.xiazoliuxing.ui.main.activity;


import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.administrator.xiazoliuxing.R;
import com.example.administrator.xiazoliuxing.base.BaseActivity;
import com.example.administrator.xiazoliuxing.bean.CollectBean2;
import com.example.administrator.xiazoliuxing.presenter.CollectP;
import com.example.administrator.xiazoliuxing.ui.main.adapter.Collectadapter;
import com.example.administrator.xiazoliuxing.util.StatusBarManager;
import com.example.administrator.xiazoliuxing.view.main.CollectV;
import com.just.agentweb.LogUtils;
import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;

public class CollectActivity extends BaseActivity<CollectV, CollectP> implements CollectV {


    @BindView(R.id.lv)
    RecyclerView lv;
    private ArrayList<CollectBean2.ResultBean.CollectedRoutesBean> mArraylist = new ArrayList<>();

    private Collectadapter collectadapter;

    @Override
    protected CollectP initPresenter() {
        return new CollectP();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_collect;
    }

    @Override
    protected void initData() {
        mPresenter.getData(1);
    }

    @Override
    protected void initView() {
        int color = ContextCompat.getColor(this, R.color.c_eaeaea);
        StatusBarManager.setStatusBarColor(this,color);
        collectadapter = new Collectadapter(mArraylist, this);
        lv.setAdapter(collectadapter);
        lv.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public void setData(CollectBean2 collectBean2) {
        LogUtils.e("asdfsdf", collectBean2.getResult().getCollectedRoutes().toString());
        List<CollectBean2.ResultBean.CollectedRoutesBean> collectedRoutes = collectBean2.getResult().getCollectedRoutes();
        mArraylist.addAll(collectedRoutes);
        collectadapter.notifyDataSetChanged();

    }

}
