package com.example.administrator.xiazoliuxing.ui.main.activity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.administrator.xiazoliuxing.R;
import com.example.administrator.xiazoliuxing.base.BaseActivity;
import com.example.administrator.xiazoliuxing.base.Constants;
import com.example.administrator.xiazoliuxing.bean.Bean;
import com.example.administrator.xiazoliuxing.bean.Yonghu;
import com.example.administrator.xiazoliuxing.net.MyServer;
import com.example.administrator.xiazoliuxing.presenter.AmendPresenter;
import com.example.administrator.xiazoliuxing.util.SpUtil;
import com.example.administrator.xiazoliuxing.util.StatusBarManager;
import com.example.administrator.xiazoliuxing.view.main.AmendView;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MymessageActivity extends BaseActivity<AmendView, AmendPresenter> implements AmendView {

    private Uri mImageUri;
    private File mFile;
    @BindView(R.id.tesc)
    TextView tesc;
    @BindView(R.id.tab)
    Toolbar tab;
    @BindView(R.id.sv)
    LinearLayout sv;
    @BindView(R.id.te)
    TextView te;
    @BindView(R.id.tou)
    TextView tou;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.text_1)
    TextView text1;
    @BindView(R.id.text_2)
    TextView text2;
    @BindView(R.id.text_3)
    TextView text3;
    @BindView(R.id.ka)
    CardView ka;
    @BindView(R.id.teste)
    TextView teste;
    @BindView(R.id.ka2)
    CardView ka2;
    @BindView(R.id.but11)
    Button but11;
    @BindView(R.id.lv)
    RelativeLayout lv;
    private String url1;

    @Override
    protected AmendPresenter initPresenter() {
        return new AmendPresenter();
    }

    @Override
    protected void initView() {
        mPresenter.getData1();
        int color = ContextCompat.getColor(this, R.color.c_eaeaea);
        StatusBarManager.setStatusBarColor(this, color);
        setSupportActionBar(tab);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);//左侧添加一个默认的返回图标
        getSupportActionBar().setHomeButtonEnabled(true); //设置返回键可用
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_mymessage;
    }


    //设置监听事件
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
        }
        return true;
    }

    @OnClick({R.id.lv_tou, R.id.lv_name, R.id.lv_sex, R.id.lv_gexing, R.id.lv})
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.lv_tou:
                popup();
                break;
            case R.id.lv_name:
                Intent intent = new Intent(MymessageActivity.this, AmendActivity.class);
                intent.putExtra("name", text1.getText().toString());
                intent.putExtra("type", 11);
                startActivityForResult(intent, 1);
                break;
            case R.id.lv_sex:
                popupSex();
                break;
            case R.id.lv_gexing:
                Intent intent1 = new Intent(MymessageActivity.this, AmendActivity.class);
                intent1.putExtra("name", text3.getText().toString());
                intent1.putExtra("type", 22);
                startActivityForResult(intent1, 2);
                break;
            case R.id.lv:
                break;
        }
    }

    private void popupSex() {
        final PopupWindow popupWindow = new PopupWindow(MymessageActivity.this);
        View inflate = LayoutInflater.from(MymessageActivity.this).inflate(R.layout.layout_popu, null);
        popupWindow.setContentView(inflate);
        popupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(but11, Gravity.BOTTOM, 0, 0);
        Button viewById = inflate.findViewById(R.id.but_1);
        Button viewById1 = inflate.findViewById(R.id.but_2);
        Button viewById2 = inflate.findViewById(R.id.but_3);
        Button viewById3 = inflate.findViewById(R.id.but_4);
        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text2.setText("男");
                SpUtil.setParam(Constants.SEX,"男");
                popupWindow.dismiss();
                updateInfo();
            }
        });
        viewById1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text2.setText("女");
                SpUtil.setParam(Constants.SEX,"女");
                popupWindow.dismiss();
                updateInfo();
            }
        });
        viewById2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text2.setText("保密");
                SpUtil.setParam(Constants.SEX,"保密");
                popupWindow.dismiss();
                updateInfo();
            }
        });
        viewById3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
//        mPresenter.getData();

    }

    private void popup() {
        final PopupWindow popupWindow1 = new PopupWindow(MymessageActivity.this);
        View inflate6 = LayoutInflater.from(MymessageActivity.this).inflate(R.layout.layout_popu1, null);
        popupWindow1.setContentView(inflate6);
        popupWindow1.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        popupWindow1.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow1.setBackgroundDrawable(null);
        popupWindow1.setOutsideTouchable(true);
        Button viewById9 = inflate6.findViewById(R.id.but_1);
        Button viewById8 = inflate6.findViewById(R.id.but_2);
        inflate6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow1.dismiss();
            }
        });

        viewById8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePick();

                popupWindow1.dismiss();
            }
        });

        viewById9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePhoto();
                popupWindow1.dismiss();
            }
        });
        popupWindow1.showAtLocation(but11, Gravity.BOTTOM, 0, 0);
        //
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }*/

    private static final String TAG = "MainActivity";
    private static final int CAMERA_CODE = 100;
    private static final int ALBUM_CODE = 200;

    private void takePick() {//相册Sd卡权限
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            openAlbum();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 200);
        }
    }

    private void takePhoto() {//相机权限

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            openCamera();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            if (requestCode == 100) {
                openCamera();
            } else if (requestCode == 200) {
                openAlbum();
            }
        }
    }

    //开启相册
    private void openAlbum() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, ALBUM_CODE);
    }

    //开启相机
    private void openCamera() {
        //1.创建空白文件
        mFile = new File(getExternalCacheDir(), System.currentTimeMillis() + ".jpg");
        if (!mFile.exists()) {
            try {
                mFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //2.将File文件转换为Uri路径
        //适配7.0
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            mImageUri = Uri.fromFile(mFile);
        } else {
            //第二个参数要和清单文件中的配置保持一致
            mImageUri = FileProvider.getUriForFile(this, "com.baidu.upload.provider", mFile);
        }

        //3.启动相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageUri);//将拍照图片存入mImageUri
        startActivityForResult(intent, CAMERA_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 || requestCode == 2) {
            String psw = data.getStringExtra("psw");
            if (resultCode == 1) {
                text1.setText(psw);
                SpUtil.setParam(Constants.USERNAME,text1.getText().toString());
            } else {
                text3.setText(psw);
                SpUtil.setParam(Constants.DESC,text3.getText().toString());
            }
            updateInfo();
        }
        if (resultCode == RESULT_OK) {

            if (requestCode == CAMERA_CODE) {//拍照

                //显示拍照后的图片
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(mImageUri));
                    img.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //文件上传
                uploadFile(mFile);
            } else if (requestCode == ALBUM_CODE) {//相册

                //1.获取相册中选中的图片的URi路径
                Uri imageUri = data.getData();

                //显示相册中选中的图片
                try {
                    Bitmap bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri));
                    img.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

                //2.将Uri路径转换为File文件
                File file = getFileFromUri(imageUri, this);

                //3.文件上传
                if (file.exists()) {
                    uploadFile(file);
                }
            }
        }
    }

    public void updateInfo(){
        mPresenter.getData(MyServer.token,text1.getText().toString(),text3.getText().toString(),text2.getText().toString(),url1);
    }

    public File getFileFromUri(Uri uri, Context context) {
        if (uri == null) {
            return null;
        }
        switch (uri.getScheme()) {
            case "content":
                return getFileFromContentUri(uri, context);
            case "file":
                return new File(uri.getPath());
            default:
                return null;
        }
    }

    /**
     * 通过内容解析中查询uri中的文件路径
     */
    private File getFileFromContentUri(Uri contentUri, Context context) {
        if (contentUri == null) {
            return null;
        }
        File file = null;
        String filePath;
        String[] filePathColumn = {MediaStore.MediaColumns.DATA};
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(contentUri, filePathColumn, null,
                null, null);
        if (cursor != null) {
            cursor.moveToFirst();
            filePath = cursor.getString(cursor.getColumnIndex(filePathColumn[0]));
            cursor.close();

            if (!TextUtils.isEmpty(filePath)) {
                file = new File(filePath);
            }
        }
        return file;
    }

    private void uploadFile(File mFile) {

        String url = "http://yun918.cn/study/public/file_upload.php";

        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        RequestBody requestBody = RequestBody.create(MediaType.parse("image/jpg"), mFile);

        MultipartBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key", "H1808B")
                .addFormDataPart("file", mFile.getName(), requestBody)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e(TAG, "onFailure: " + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String string = response.body().string();
                Gson gson = new Gson();
                final Bean upLoadBean = gson.fromJson(string, Bean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (upLoadBean != null) {
                            if (upLoadBean.getCode() == 200 && upLoadBean.getCode() > 0) {
                                Toast.makeText(MymessageActivity.this, upLoadBean.getRes(), Toast.LENGTH_SHORT).show();

                                url1 = upLoadBean.getData().getUrl();
                                SpUtil.setParam(Constants.PHOTO, url1);
                                RequestOptions options = new RequestOptions().circleCrop();
//                                mPresenter.getData();
                                Glide.with(MymessageActivity.this).load(url1).apply(options).into(img);
                                SpUtil.setParam(Constants.PHOTO,url1);
                                updateInfo();
                                Log.e(TAG, "run: " + upLoadBean.getData().getUrl());
                            } else {
                                Toast.makeText(MymessageActivity.this, upLoadBean.getRes(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

            }
        });

    }

    @Override
    public void OnSuccess() {
        mPresenter.getData1();
    }

    @Override
    public void setData(Yonghu yonghu) {
        Yonghu.ResultBean result = yonghu.getResult();
        RequestOptions requestOptions = new RequestOptions().circleCrop();
        url1 = result.getPhoto();
        Glide.with(this).load(url1).apply(requestOptions).into(img);
        text1.setText(result.getUserName());
        String gender = result.getGender();
        if(gender.equals("M")){
            text2.setText("男");
        }else if(gender.equals("F")){
            text2.setText("女");
        }else {
            text2.setText("保密");
        }

        text3.setText(result.getDescription());
    }
}
