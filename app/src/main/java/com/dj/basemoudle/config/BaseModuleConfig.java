package com.dj.basemoudle.config;

import android.os.Build;

import java.util.Map;

/**
 * @author dj
 * @description
 * @Date 2019/2/20
 */
public class BaseModuleConfig {

    /**
     * 请求公共头部参数
     */
    private Map<String,String> headers;

    /**
     * 请求公共参数
     */
    private Map<String,String> params;

    /**
     * 连接超时时间
     */
    private int mConnectTimeout = 8;
    /**
     * 读取超时时间
     */
    private int mReadTimeout = 8;

    /**
     * 写入超时时间
     */
    private int mWriteTimeout = 8;

    /*
     * 服务端接口返回正常code值  默认200
     */
    private int serverSuccessCode;


    /**
     * 默认LoadingView样式
     */
    private String dialogStyle;
    /**
     * 刷新控件的颜色
     */
    private int refreshColor;
    /**
     * LoadingDialog颜色
     *
     * @return
     */
    private int dialogColor;


    public static Builder newBuilder(){
        return new Builder();
    }

    private BaseModuleConfig(Builder builder){
        this.headers=builder.headers;
        this.params = builder.params;
        this.mConnectTimeout = builder.mConnectTimeout;
        this.mReadTimeout = builder.mReadTimeout;
        this.serverSuccessCode=builder.serverSuccessCode;
    }


    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public int getmConnectTimeout() {
        return mConnectTimeout;
    }

    public int getmReadTimeout() {
        return mReadTimeout;
    }

    public int getmWriteTimeout() {
        return mWriteTimeout;
    }

    public int getServerSuccessCode() {
        return serverSuccessCode;
    }

    public String getDialogStyle() {
        return dialogStyle;
    }

    public int getRefreshColor() {
        return refreshColor;
    }

    public int getDialogColor() {
        return dialogColor;
    }





    public final static class Builder{
        private Builder(){

        }



        /**
         * 默认LoadingView样式
         */
        private String dialogStyle = "LineScaleIndicator";

        /**
         * LaddingView的颜色
         */
        private int dialogColor = -1;
        /**
         * 请求公共头部参数
         */
        private Map<String,String> headers;

        /**
         * 请求公共参数
         */
        private Map<String,String> params;

        /**
         * 连接超时时间
         */
        private int mConnectTimeout = 8;
        /**
         * 读取超时时间
         */
        private int mReadTimeout = 8;

        /**
         * 写入超时时间
         */
        private int mWriteTimeout = 8;

        /*
         * 服务端接口返回正常code值  默认200
         */
        private int serverSuccessCode=200;



        public int getServerSuccessCode() {
            return serverSuccessCode;
        }

        public Map<String, String> getHeaders() {
            return headers;
        }

        public void setHeaders(Map<String, String> headers) {
            this.headers = headers;
        }

        public Map<String, String> getParams() {
            return params;
        }

        public void setParams(Map<String, String> params) {
            this.params = params;
        }

        public int getmConnectTimeout() {
            return mConnectTimeout;
        }

        public void setmConnectTimeout(int mConnectTimeout) {
            this.mConnectTimeout = mConnectTimeout;
        }

        public int getmReadTimeout() {
            return mReadTimeout;
        }

        public void setmReadTimeout(int mReadTimeout) {
            this.mReadTimeout = mReadTimeout;
        }

        public int getmWriteTimeout() {
            return mWriteTimeout;
        }

        public void setmWriteTimeout(int mWriteTimeout) {
            this.mWriteTimeout = mWriteTimeout;
        }


        public String getDialogStyle() {
            return dialogStyle;
        }

        public int getDialogColor() {
            return dialogColor;
        }

        public BaseModuleConfig build() {
            return new BaseModuleConfig(this);
        }

    }
}
