package com.example.administrator.xiazoliuxing.ui.main.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.widget.RelativeLayout;

import com.example.administrator.xiazoliuxing.MainActivity;
import com.example.administrator.xiazoliuxing.R;
import com.example.administrator.xiazoliuxing.base.Constants;
import com.example.administrator.xiazoliuxing.util.SpUtil;

import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private RelativeLayout rlSplash;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        initView();
        //startAnimation();
        Timer timer=new Timer();
        TimerTask task =new TimerTask() {
            @Override
            public void run() {
                guide();
                finish();
            }
        };
        timer.schedule(task,3000);
    }

    private void initView() {
        rlSplash = (RelativeLayout) findViewById(R.id.rlSplash);
    }

    private void startAnimation(){
        AnimationSet animationSet=new AnimationSet(false);

//        RotateAnimation rotate=new RotateAnimation(0,360,
//                Animation.RELATIVE_TO_SELF,0.5f,
//                Animation.RELATIVE_TO_SELF,0.5f);
//        rotate.setDuration(5000);
//        rotate.setFillAfter(true);

        ScaleAnimation scale=new ScaleAnimation(0,1,0,1,Animation.RELATIVE_TO_SELF,0.5f,
                Animation.RELATIVE_TO_SELF,0.5f);
        scale.setDuration(5000);
        scale.setFillAfter(true);

        AlphaAnimation alpha=new AlphaAnimation(0,1);
        alpha.setDuration(3000);
        alpha.setFillAfter(true);

//        animationSet.addAnimation(rotate);
        animationSet.addAnimation(scale);
        animationSet.addAnimation(alpha);

        rlSplash.startAnimation(animationSet);
    }

    public void guide(){
        //是否是第一次登陆
        boolean isFirstRun = (boolean) SpUtil.getParam("name",true);
        if (isFirstRun){
            Log.e("debug", "第一次运行");
            Intent intent = new Intent();
            intent.setClass(SplashActivity.this,GuideActivity.class);
            startActivity(intent);
        } else {
            Log.e("debug", "不是第一次运行");
            String token = (String) SpUtil.getParam(Constants.TOKEN, "");
            if (TextUtils.isEmpty(token)){
                Intent intent = new Intent();
                intent.setClass(SplashActivity.this,LoginActivity.class);
                startActivity(intent);
            }else {
                Intent intent = new Intent();
                intent.setClass(SplashActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
