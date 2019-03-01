package com.dj.baseutil.util;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Environment;

import com.blankj.utilcode.util.AppUtils;
import com.dj.baseutil.download.DownLoadCallBack;
import com.dj.baseutil.view.CircleProgerssDialog;
import com.github.lzyzsd.circleprogress.DonutProgress;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

import java.io.File;

/**
 * @author dj
 * @description
 * @Date 2019/3/1
 * 下载类工具
 */
public class MDownLoad {

    private Context mContext;

    private String url;

    private String path;

    private boolean showDialog;

    private CircleProgerssDialog dialog;

    private DonutProgress mProgress;

    private DownLoadCallBack downLoadListener;


    private MDownLoad(MDownLoad.Builder builder){
        this.mContext = builder.mContext;
        this.url = builder.url;
        this.downLoadListener = builder.mCallBack;
        this.showDialog = builder.showDialog;
        dialog = new CircleProgerssDialog(mContext);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                OkGo.getInstance().cancelTag(this);
            }
        });
        //如果有sd卡，则获取sd卡的路径/storage/sdcard；如果没有，获取Android目录/data
        if (MUtil.checkSDCard()){
            path = Environment.getExternalStorageDirectory().getPath() + File.separator + AppUtils.getAppName();
        }else {
            path = Environment.getDataDirectory().getPath() + File.separator + AppUtils.getAppName();
        }
        start();
    }

    private void start(){
        OkGo.<File>get(url)
                .tag(this)
                .execute(new FileCallback() {
                    @Override
                    public void onSuccess(Response<File> response) {
                        if (downLoadListener != null){
                            downLoadListener.onSuccess(response.body());
                        }
                    }

                    @Override
                    public void onStart(Request<File, ? extends Request> request) {
                        super.onStart(request);
                        if (showDialog) {
                            dialog.show();
                            mProgress = dialog.getdProgress();
                            mProgress.setMax(100);
                        }
                    }

                    @Override
                    public void onError(Response<File> response) {
                        super.onError(response);
                        if (dialog != null && dialog.isShowing()) {
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void downloadProgress(Progress progress) {
                        super.downloadProgress(progress);
                        if ((int) (progress.fraction * 100) == 100) {
                            if (dialog != null && dialog.isShowing()) {
                                dialog.dismiss();
                            }
                        } else {
                            if (dialog != null && dialog.isShowing()) {
                                mProgress.setProgress((int) (progress.fraction * 100));
                            }
                        }
                    }
                });
    }




    public static final class Builder {
        /**
         * 上下文参数
         */
        private Context mContext;
        /**
         * 更新的地址
         */
        private String url;
        /**
         * 是否需要显示进度对话框
         */
        private boolean showDialog;

        private DownLoadCallBack mCallBack;

        public Builder(Context context) {
            mContext = context;
        }

        public Builder setUrl(String url){
            this.url = url;
            return this;
        }


        public Builder setShowDialog(boolean showDialog) {
            this.showDialog = showDialog;
            return this;
        }

        public void execute(DownLoadCallBack downLoadCallBack) {
            this.mCallBack = downLoadCallBack;
            new MDownLoad(this);
        }


    }


}
