package com.example.administrator.xiazoliuxing.ui.main.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.xiazoliuxing.R;
import com.example.administrator.xiazoliuxing.base.BaseActivity;
import com.example.administrator.xiazoliuxing.base.BaseMvpView;
import com.example.administrator.xiazoliuxing.base.Constants;
import com.example.administrator.xiazoliuxing.bean.Yonghu;
import com.example.administrator.xiazoliuxing.presenter.AmendPresenter;
import com.example.administrator.xiazoliuxing.util.SpUtil;
import com.example.administrator.xiazoliuxing.util.StatusBarManager;
import com.example.administrator.xiazoliuxing.view.main.AmendView;

public class AmendActivity extends BaseActivity<BaseMvpView, AmendPresenter> implements AmendView {

    /**
     * 个人中心
     */
    private TextView tesc;
    private Toolbar tab;
    private EditText edit;
    private Intent intent;
    private int type;


    @Override
    protected AmendPresenter initPresenter() {
        return new AmendPresenter();
    }

    @Override
    protected void initView() {
        int color = ContextCompat.getColor(this, R.color.c_eaeaea);
        StatusBarManager.setStatusBarColor(this,color);
        intent = getIntent();
        tesc = (TextView) findViewById(R.id.tesc);
        tab = (Toolbar) findViewById(R.id.tab);
        edit = (EditText) findViewById(R.id.edit);
        String name = intent.getStringExtra("name");
        type = intent.getIntExtra("type", 11);
        edit.setText(name);
        tab.setTitle("");
        setSupportActionBar(tab);
        if(type==11){
            tesc.setText("修改名称");
        }else {
            tesc.setText("修改个性签名");
        }
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
//        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_amend;
    }

    @Override
    protected void initData() {
        super.initData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    //设置监听事件
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case android.R.id.home:
//                finish();
//                break;
            case R.id.textit:
                String s = edit.getText().toString();
                Intent intent1 = new Intent();
                intent1.putExtra("psw", s);
                if (type == 11) {
                    SpUtil.setParam(Constants.USERNAME,s);
//                    mPresenter.getData();
                    setResult(1,intent1);
                } else {
                    SpUtil.setParam(Constants.SIG,s);
//                    mPresenter.getData();
                    setResult(2, intent1);
                }
                finish();
                break;
        }
        return true;
    }

    @Override
    public void OnSuccess() {
        Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setData(Yonghu yonghu) {

    }
}
