package com.dj.basemoudle.update;

import android.app.Notification;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.widget.ProgressBar;

import com.blankj.utilcode.util.AppUtils;
import com.dj.basemoudle.base.App;
import com.dj.basemoudle.util.MUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;


import java.io.File;
import java.lang.reflect.Field;

/**
 * @author dj
 * @description
 * @Date 2019/2/22
 */
public class DownLoadService extends Service {

    private String url;

    private String path;

    private String ver;

    private ProgressDialog mDialog;

    private boolean forceUpdate;

    private NotificationHelper mHelper;


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        getPath();
        mHelper=new NotificationHelper(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent != null){
            url=intent.getStringExtra("url");
            ver=intent.getStringExtra("ver");
            startDownload();
        }

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 获取下载的路径
     */
    private void getPath(){
        if (MUtil.checkSDCard()){
            path=Environment.getExternalStorageDirectory().getPath() +File.separator +AppUtils.getAppName();
        }else {
            path=Environment.getDataDirectory().getPath() +File.separator +AppUtils.getAppName();
        }
    }

    private void startDownload() {
        if (url == null || url.isEmpty())return;;
        OkGo.<File>get(url)
                .tag(this)
                .execute(new FileCallback(path,AppUtils.getAppName() + ver + ".apk") {
                    @Override
                    public void onSuccess(Response<File> response) {
                        //完成下载,自动安装
                        mHelper.showDownloadCompleteNotification(response.body());
                    }

                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        super.onStart(request);
                        //下载开始
                        mHelper.showNotification();
                    }


                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                        //更新通知栏进度
                        mHelper.updateNotification((int)(progress.fraction * 100));
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                        //下载失败
                        mHelper.showDownloadFailedNotification();
                    }
                });
    }
}
