package com.example.administrator.xiazoliuxing.ui.main.fragment;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.xiazoliuxing.R;
import com.example.administrator.xiazoliuxing.base.BaseApp;
import com.example.administrator.xiazoliuxing.base.BaseFragment;
import com.example.administrator.xiazoliuxing.base.Constants;
import com.example.administrator.xiazoliuxing.presenter.VerifyPresenter;
import com.example.administrator.xiazoliuxing.ui.main.activity.LoginActivity;
import com.example.administrator.xiazoliuxing.util.Logger;
import com.example.administrator.xiazoliuxing.view.main.VerifyView;
import com.example.administrator.xiazoliuxing.widget.IdentifyingCodeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class VerifyFragment extends BaseFragment<VerifyView, VerifyPresenter> implements VerifyView {


    @BindView(R.id.tv_send_again)
    TextView tvSendAgain;
    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.icv)
    IdentifyingCodeView mIcv;
    @BindView(R.id.tv_wait)
    TextView mTvWait;
    private int mTime;
    /**
     *
     * @param code 验证码,没有传递""
     * @return
     */
    public static VerifyFragment newIntance(String code){
        VerifyFragment verifyFragment = new VerifyFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.VERIFY_CODE,code);
        verifyFragment.setArguments(bundle);
        return verifyFragment;
    }

    @Override
    protected VerifyPresenter initPresenter() {
        return new VerifyPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_verify;
    }


    @OnClick({R.id.iv_back, R.id.tv_send_again})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                pop();
                break;
            case R.id.tv_send_again:
                if (mTime == 0){
                    mPresenter.getVerifyCode();
                    //重新发起倒计时
                    LoginOrBindFragment fragment = (LoginOrBindFragment) getActivity().getSupportFragmentManager().findFragmentByTag(LoginActivity.TAG);
                    fragment.countDown();
                }
                break;
        }
    }
    private void pop() {
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.popBackStack();
    }
    @Override
    protected void initData() {
        mPresenter.getVerifyCode();
    }
    @Override
    public void setData(String data) {
        if (!TextUtils.isEmpty(data)) {
            mTvWait.setText(BaseApp.getRes().getString(R.string.verify_code)+data);
        }
    }
    @Override
    protected void initListener() {
        mIcv.setOnEditorActionListener(new IdentifyingCodeView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                return false;
            }

            @Override
            public void onTextChanged(String s) {
                autoLogin();
            }
        });
    }
    private void autoLogin() {
        Logger.println(mIcv.getTextContent());
        if (mIcv.getTextContent().length()>=4){
            //自动登录
            toastShort("自动登录");
            mIcv.setBackgroundEnter(false);
            mTvWait.setText(BaseApp.getRes().getString(R.string.wait_please));
            showLoading();
        }
    }

    public void setCountDownTime(int time) {
        mTime = time;
        if (tvSendAgain != null){
            if (time != 0){
                String format = String.format(getResources().getString(R.string.send_again) + "(%ss)", time);
                tvSendAgain.setText(format);
                tvSendAgain.setTextColor(getResources().getColor(R.color.c_999));
            }else {
                tvSendAgain.setText(getResources().getString(R.string.send_again));
                tvSendAgain.setTextColor(getResources().getColor(R.color.c_fa6a13));
            }
        }
    }

    @Override
    protected void initView() {
        String code = getArguments().getString(Constants.VERIFY_CODE);
        setData(code);
    }
}
