package com.dj.basemoudle.http;

import com.dj.basemoudle.config.BaseModule;

/**
 * @author dj
 * @description
 * @Date 2019/2/20
 */
public class Result <T>{

    public int code;

    public String msg;

    public T date;

    public boolean isSuccess() {
        return code == BaseModule.getMbaseModuleConfig().getServerSuccessCode();
    }


}
