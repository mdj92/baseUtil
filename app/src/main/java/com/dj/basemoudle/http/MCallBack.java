package com.dj.basemoudle.http;

import android.app.Activity;

import com.dj.basemoudle.config.BaseModule;
import com.dj.basemoudle.config.BaseModuleConfig;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

/**
 * @author dj
 * @description
 * @Date 2019/2/21
 */
public abstract class MCallBack<T> extends JsonCallback<T> {

    LoadingDialog mDialog;

    boolean showDialog;

    public MCallBack(Activity activity) {
        this(activity, true);
    }

    public MCallBack(Activity activity,boolean showDialog) {
        this.showDialog = showDialog;
        initLoadingDialog(activity);
    }

    private void initLoadingDialog(Activity activity){
        BaseModuleConfig config=BaseModule.getMbaseModuleConfig();
        mDialog=new LoadingDialog(activity,config.getDialogStyle());
        mDialog.setCanceledOnTouchOutside(false);
    }

    @Override
    public void onStart(Request<T, ? extends Request> request) {
        super.onStart(request);
        if (mDialog != null && showDialog && !mDialog.isShowing()) {
            mDialog.show();
        }
    }

    @Override
    public void onFinish() {
        super.onFinish();
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
