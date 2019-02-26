package com.dj.baseutil.util;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.v4.content.ContextCompat;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.FileUtils;
import com.blankj.utilcode.util.ServiceUtils;

import com.dj.baseutil.R;
import com.dj.baseutil.bean.UpdateInfo;
import com.dj.baseutil.http.JsonCallback;
import com.dj.baseutil.update.DownLoadService;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.io.File;

/**
 * @author dj
 * @description 检测更新
 * @Date 2019/2/21
 */
public class MUpdate {

    private Context mContext;

    private String url;

    private boolean forceUpdate;

    private String title;

    private boolean checkNewVer;

    private MUpdate(Builder builder) {
        this.mContext = builder.mContext;
        this.forceUpdate = builder.forceUpdate;
        this.url = builder.url;
        this.title = builder.dialogTitle;
        this.checkNewVer = builder.checkNewVer;
        check();
    }


    public static Builder newBuilder(Context context) {
        return new Builder(context);
    }

    /**
     * 检测是否需要进行更新操作
     */
    private void check() {
        OkGo.<UpdateInfo>get(url)
                .tag(this)
                .execute(new JsonCallback<UpdateInfo>() {
                    @Override
                    public void onSuccess(Response<UpdateInfo> response) {
                        UpdateInfo updateInfo=response.body();
                        if (MUtil.versionCheck(MUtil.getVersionName(mContext),updateInfo.getAndroid().getVer())){
                            createBaseDialog(updateInfo);
                        }else {
                            if (checkNewVer){
                                MToast.showToast(mContext, "当前已经是最新版本了");
                            }
                        }
                    }
                });
    }

    /**
     * 创建提示弹窗
     * @param info
     */
    private void createBaseDialog(final UpdateInfo info) {
        AlertDialog.Builder builder=new AlertDialog.Builder(mContext);
        builder.setTitle(title != null ? title : "新版本" +info.getAndroid().getVer())
                .setMessage(info.getAndroid().getInfo())
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        checkPermission(info);
                    }
                });
        if (!forceUpdate){
            builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
        }
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(!forceUpdate);
        dialog.show();
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(mContext, R.color.color_1b9ef7));
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(mContext, R.color.color_1b9ef7));
    }

    /**
     * 权限验证
     * @param info
     */
    private void checkPermission(final UpdateInfo info) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
            MPermissions.getInstance().request((Activity) mContext, permissions, new MPermissions.PermissionListener() {
                @Override
                public void callBack(boolean value) {
                    if (value) {
                        startDownload(info);
                    } else {
                        MToast.showToast(mContext, "您没有授权该权限，请在设置中打开授权!");
                    }
                }
            });
        } else {
            startDownload(info);
        }
    }

    //开始下载- 先判断本地是否已经下载apk
    private void startDownload(UpdateInfo info){
        String path = Environment.getExternalStorageDirectory().getPath() + File.separator + AppUtils.getAppName()
                + File.separator + AppUtils.getAppName() + info.getAndroid().getVer() + ".apk";
        if (FileUtils.isFileExists(path)) {
            MUtil.installApp(mContext, new File(path));
        } else {
            downLoad(info);
        }
    }

    //开启下载Service
    private void downLoad(UpdateInfo info) {
        if (ServiceUtils.isServiceRunning(DownLoadService.class)) {
            mContext.stopService(new Intent(mContext, DownLoadService.class));
        }
        Intent intent = new Intent(mContext, DownLoadService.class);
        intent.putExtra("url", info.getAndroid().getUrl());
        intent.putExtra("ver", info.getAndroid().getVer());
        intent.putExtra("forceUpdate", forceUpdate);
        mContext.startService(intent);
    }

    /**
     * 显示更新信息
     */
    private void showDialgInfo(UpdateInfo info) {
        //当选择更新activity界面已经被销毁的话就显示相应的dialog
        if (((Activity) mContext).isFinishing()) return;
        createBaseDialog(info);
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
         * 是否进行强制更新
         */
        private boolean forceUpdate;

        /**
         * 更新内容对话框的标题
         */
        private String dialogTitle;


        private Dialog customDialog;
        //衍生   检测更新功能 默认是自动检测
        private boolean checkNewVer = false;

        private Builder(Context context) {
            this.mContext = context;
        }

        public Builder setUrl(String url) {
            this.url = url;
            return this;
        }

        public Builder setForceUpdate(boolean forceUpdate) {
            this.forceUpdate = forceUpdate;
            return this;
        }

        public Builder setDialogTitle(String dialogTitle) {
            this.dialogTitle = dialogTitle;
            return this;
        }

        public Builder setCheckNewVer(boolean checkNewVer) {
            this.checkNewVer = checkNewVer;
            return this;
        }

        public MUpdate build() {
            return new MUpdate(this);
        }

    }

}
