package com.example.administrator.xiazoliuxing.ui.main.fragment;

import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import com.example.administrator.xiazoliuxing.R;
import com.example.administrator.xiazoliuxing.base.BaseFragment;
import com.example.administrator.xiazoliuxing.bean.AttentionBean;
import com.example.administrator.xiazoliuxing.bean.Mingxingbean;
import com.example.administrator.xiazoliuxing.presenter.Banmip;
import com.example.administrator.xiazoliuxing.ui.main.adapter.Banmiadapter;
import com.example.administrator.xiazoliuxing.util.ToastUtil;
import com.example.administrator.xiazoliuxing.view.main.BanmiV;
import java.util.ArrayList;
import butterknife.BindView;

public class BanmiFragment extends BaseFragment<BanmiV, Banmip> implements BanmiV, Banmiadapter.Liseterentent {


    private static final String TAG = "呵呵";
    int page = 1;
    @BindView(R.id.lv)
    RecyclerView lv;
    private ArrayList<Mingxingbean.ResultBean.BanmiBean> list;
    private Banmiadapter banmiadapter;
    private int id;

    public BanmiFragment() {

    }

    @Override
    protected Banmip initPresenter() {
        return new Banmip();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_banmi;
    }

    @Override
    protected void initData() {
        mPresenter.getData(page);
    }

    @Override
    protected void initView() {
        list = new ArrayList<>();
        banmiadapter = new Banmiadapter(list, getActivity());
        lv.setAdapter(banmiadapter);
        lv.setLayoutManager(new LinearLayoutManager(getActivity()));
        banmiadapter.setLiseterentent(this);
    }

    @Override
    public void setData(Mingxingbean mingxingbean) {
    list.addAll(mingxingbean.getResult().getBanmi());
    banmiadapter.setList(list);
    banmiadapter.notifyDataSetChanged();
    }

    @Override
    public void setguanzhu(AttentionBean bean) {
        String message = bean.getResult().getMessage();
        ToastUtil.showShort(message);
    }

    @Override
    public void Liseteren(Button but) {
        for (int i = 0; i <list.size() ; i++) {
            id = list.get(i).getId();
            mPresenter.guanzhu(id);
        }

    }
}
