package com.example.administrator.xiazoliuxing.net;

import android.content.Context;
import android.content.Intent;
import android.webkit.JavascriptInterface;
import com.example.administrator.xiazoliuxing.base.Constants;
import com.example.administrator.xiazoliuxing.ui.main.activity.CheckMoreActivity;
import com.example.administrator.xiazoliuxing.ui.main.activity.PathInfoActivity;
import com.example.administrator.xiazoliuxing.ui.main.activity.WebHomepageActivity;

public class JsAndroid {
    Context context;

    public JsAndroid(Context context) {
        this.context = context;
    }
    @JavascriptInterface
    public void callAndroid(String detil,int id){
        Intent intent = new Intent(context, PathInfoActivity.class);
        intent.putExtra("id",id);
        context.startActivity(intent);
    }

    @JavascriptInterface
    public void callAndroid(String detil){
        //Toast.makeText(context, "hahaha", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(context, CheckMoreActivity.class);
        context.startActivity(intent);
    }
}
