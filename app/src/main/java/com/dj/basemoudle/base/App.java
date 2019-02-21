package com.dj.basemoudle.base;

import android.util.Log;

import com.dj.basemoudle.R;
import com.dj.basemoudle.config.BaseModule;
import com.dj.basemoudle.config.BaseModuleConfig;


/**
 * @author Marcus
 * @package krt.com.zhjy.base
 * @description
 * @time 2018/9/12
 */
public class App extends MApp {

    @Override
    protected void init() {
        BaseModule.initialize(BaseModuleConfig.newBuilder()
                .setLoadingViewColor(R.color.color_DA0101).build());

    }

}
