package com.dj.basemoudle;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dj.basemoudle.base.BaseActivity;
import com.dj.basemoudle.constan.Constants;
import com.dj.baseutil.download.DownLoadCallBack;
import com.dj.baseutil.http.MCallBack;
import com.dj.baseutil.http.Result;
import com.dj.baseutil.upload.UploadCallBack;
import com.dj.baseutil.util.MDownLoad;
import com.dj.baseutil.util.MToast;
import com.dj.baseutil.util.MUpdate;
import com.dj.baseutil.util.MUpload;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {


    @BindView(R.id.login)
    Button login;
    @BindView(R.id.update)
    Button update;

    @Override
    public int bindLayout() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
//        login=findViewById(R.id.login);
//        login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                doLogin("ls", "123456");
//            }
//        });
    }

    @Override
    public void loadData() {

    }

    @OnClick({R.id.login, R.id.update})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                doLogin("ls", "123456");
                break;
            case R.id.update:
//                doUpdate();
                doDownloadFile();
                break;
            default:
        }
    }

    private void doDownloadFile(){
       new MDownLoad.Builder(this)
               .setUrl("http://223.84.197.214:80/group1/M00/00/02/rAA0RFrcLiuAOQUdAAAvp7JQ9Ow63.xlsx")
               .setFileName("rAA0RFrcLiuAOQUdAAAvp7JQ9Ow63.xlsx")
               .setShowDialog(true)
               .execute(new DownLoadCallBack() {
                   @Override
                   public void onSuccess(File file) {
                       String f=file.getPath();
                   }
               });

    }


    private void doUpdate() {
        MUpdate.newBuilder(this)
                .setUrl(Constants.APP_Upload)
                .setForceUpdate(true)
                .build();

//        MUpload.<Result<String>>newBuilder(this)
//                .addHeader("accessToken", spUtil.getAccessToken())
//                .addFile("file", Arrays.asList(path))
//                .addParam("dir", "all")
//                .setUrl(Constants.BASE_URL + "upload/fileUpload")
//                //是否压缩
//                .setNeedCompress(true)
//                .execute(new UploadCallBack<Result<String>>() {
//                    @Override
//                    public void onSuccess(Result<String> data) {
//                        if (data.isSuccess()) {
//                            finishUpload(data.data);
//                        }
//                    }
//                });
    }

    private void doLogin(final String username, final String password) {
        OkGo.<Result<String>>post(Constants.BASE_URL + "login")
                .params("username", username)
                .params("password", password)
                .execute(new MCallBack<Result<String>>(this) {
                    @Override
                    public void onSuccess(Response<Result<String>> response) {
                        Result<String> mResult = response.body();
                        if (mResult.isSuccess()) {
                            MToast.showToast(MainActivity.this, "登陆成功");
                        } else {
                            MToast.showToast(MainActivity.this, mResult.msg);
                        }
                    }

                    @Override
                    public void onError(Response<Result<String>> response) {
                        super.onError(response);
                    }
                });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
