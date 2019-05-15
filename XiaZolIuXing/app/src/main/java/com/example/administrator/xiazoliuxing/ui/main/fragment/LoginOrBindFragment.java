package com.example.administrator.xiazoliuxing.ui.main.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.example.administrator.xiazoliuxing.MainActivity;
import com.example.administrator.xiazoliuxing.R;
import com.example.administrator.xiazoliuxing.base.BaseFragment;
import com.example.administrator.xiazoliuxing.base.Constants;
import com.example.administrator.xiazoliuxing.presenter.LoginOrBindPresenter;
import com.example.administrator.xiazoliuxing.ui.main.activity.LoginActivity;
import com.example.administrator.xiazoliuxing.ui.main.activity.WebViewActivity;
import com.example.administrator.xiazoliuxing.util.Tools;
import com.example.administrator.xiazoliuxing.view.main.LoginOrBindView;
import com.umeng.socialize.bean.SHARE_MEDIA;

import butterknife.BindView;
import butterknife.OnClick;

public class LoginOrBindFragment extends BaseFragment<LoginOrBindView, LoginOrBindPresenter> implements LoginOrBindView{

    @BindView(R.id.iv_back)
    ImageView mIvBack;
    @BindView(R.id.tv_hello)
    TextView mTvHello;
    @BindView(R.id.tv_login)
    TextView mTvLogin;
    @BindView(R.id.tv_coutry_code)
    TextView mTvCoutryCode;
    @BindView(R.id.et_phone)
    EditText mEtPhone;
    @BindView(R.id.btn_send_verify)
    Button mBtnSendVerify;
    @BindView(R.id.ll_container)
    LinearLayout mLlContainer;
    @BindView(R.id.view)
    View mView;
    @BindView(R.id.ll_or)
    LinearLayout mLlOr;
    @BindView(R.id.iv_wechat)
    ImageView mIvWechat;
    @BindView(R.id.iv_qq)
    ImageView mIvQq;
    @BindView(R.id.iv_sina)
    ImageView mIvSina;
    @BindView(R.id.tv_protocol)
    TextView mTvProtocol;
    @BindView(R.id.ll_oauth_login)
    LinearLayout mLlOauthLogin;
    private int mType;

    private VerifyFragment mVerifyFragment;
    private static int COUNT_DOWN_TIME = 60;
    private int mTime = COUNT_DOWN_TIME;
    private Handler mHandler;

    //验证码
    private String mVerifyCode = "";

    public static LoginOrBindFragment newIntance(int type){
        LoginOrBindFragment fragment = new LoginOrBindFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.TYPE,type);
        fragment.setArguments(bundle);
        return fragment;
    }


    @Override
    protected LoginOrBindPresenter initPresenter() {
        return new LoginOrBindPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_login_bind;
    }

    @OnClick({R.id.iv_back, R.id.btn_send_verify, R.id.iv_wechat, R.id.iv_qq, R.id.iv_sina})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_back:
                break;
            case R.id.btn_send_verify:
                String s = mEtPhone.getText().toString();
                if (!s.isEmpty()){
                    getVerifyCode();
                    addVerifyFragment();
                    time();
               }else {
                    Toast.makeText(getContext(), "号码不能为空", Toast.LENGTH_SHORT).show();
               }
                break;
            case R.id.iv_wechat:
                mPresenter.oauthLogin(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.iv_qq:
                mPresenter.oauthLogin(SHARE_MEDIA.QQ);
                break;
            case R.id.iv_sina:
                mPresenter.oauthLogin(SHARE_MEDIA.SINA);
                break;
        }

    }
    private void time() {
        //避免多次执行倒计时
        if (mTime>0 && mTime<COUNT_DOWN_TIME){
            return;
        }
        countDown();
    }

    /**
     * 倒计时,如果执行中,不要再调用
     */
    public void countDown() {
        if (mHandler  == null){
            mHandler = new Handler();
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //避免倒计时变成负的
                if (mTime <= 0){
                    mTime = COUNT_DOWN_TIME;
                    return;
                }
                mTime--;
                if (mVerifyFragment != null){
                    mVerifyFragment.setCountDownTime(mTime);
                }
                countDown();
            }
        },1000);
    }
    private void getVerifyCode() {
        //if (60s之内如果发送过,就不用发送)
        /*if (mTime>0 && mTime != COUNT_DOWN_TIME){
            //倒计时中
            return;
        }
        mPresenter.getVerifyCode();*/

        if (mTime>0 && mTime<COUNT_DOWN_TIME-1){
            //倒计时中
            return;
        }
        mVerifyCode = "";
        mPresenter.getVerifyCode();
    }
    private void addVerifyFragment() {
        if (TextUtils.isEmpty(getPhone())){
            return;
        }
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        //添加到回退栈
        fragmentTransaction.addToBackStack(null);
        mVerifyFragment = VerifyFragment.newIntance(mVerifyCode);
        fragmentTransaction.add(R.id.fl_container,mVerifyFragment).commit();
        Tools.closeKeyBoard(getActivity());
    }


    @Override
    protected void initListener() {
        //文本发生改变监听
        mEtPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                switchBtnState(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    /**
     * 根据输入框中是否有内容,切换发送验证码的背景
     * @param s
     */
    private void switchBtnState(CharSequence s) {
        if (TextUtils.isEmpty(s)){
            mBtnSendVerify.setBackgroundResource(R.drawable.bg_btn_ea_r15);
        }else {
            mBtnSendVerify.setBackgroundResource(R.drawable.bg_btn_fa6a13_r15);
        }

    }

    @Override
    public String getPhone() {
        return mEtPhone.getText().toString().trim();
    }

    @Override
    public Activity getAct() {
        return getActivity();
    }

    @Override
    public void go2MainActivity() {
        MainActivity.startAct(getContext());
    }

    @Override
    public void setData(String code) {
        this.mVerifyCode = code;
        if (mVerifyFragment != null){
            mVerifyFragment.setData(code);
        }
    }

    @Override
    protected void initView() {
        getArgumentsData();
        setProtocol();
        showOrHideView();


    }
    private void showOrHideView() {
        if (mType == LoginActivity.TYPE_LOGIN){
            //登录
            //View.VISIBLE 显示
            //View.INVISIBLE 隐藏,占位置
            //View.GONE 隐藏 不占位置
            mIvBack.setVisibility(View.INVISIBLE);
            mLlOauthLogin.setVisibility(View.VISIBLE);
            mLlOr.setVisibility(View.VISIBLE);
        }else {
            //绑定
            mIvBack.setVisibility(View.VISIBLE);
            mLlOauthLogin.setVisibility(View.GONE);
            mLlOr.setVisibility(View.GONE);
        }
    }

    private void getArgumentsData() {
        Bundle arguments = getArguments();
        mType = arguments.getInt(Constants.TYPE);
    }
    private void setProtocol() {
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder(getResources().getString(R.string.agree_protocol));
        ClickableSpan clickableSpan = new ClickableSpan(){

            @Override
            public void onClick(View widget) {
                WebViewActivity.startAct(getActivity());
            }
        };
        spannableStringBuilder.setSpan(clickableSpan,13,17, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        UnderlineSpan underlineSpan = new UnderlineSpan();
        spannableStringBuilder.setSpan(underlineSpan,13,17, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //前景色
        ForegroundColorSpan what = new ForegroundColorSpan(
                getResources().getColor(R.color.c_fa6a13));
        spannableStringBuilder.setSpan(what,13,17, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //需要设置这个ClickableSpan才会有效果
        mTvProtocol.setMovementMethod(LinkMovementMethod.getInstance());
        mTvProtocol.setText(spannableStringBuilder);
    }
}
