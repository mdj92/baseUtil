package com.dj.basemoudle.base;

import android.content.pm.ActivityInfo;

import com.dj.basemoudle.base.MBase.MBaseActivity;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author dj
 * @description
 * @Date 2019/2/20
 */
public abstract class BaseActivity extends MBaseActivity {
    private Unbinder mUnbinder;


    @Override
    public void bindButterKnife() {
        mUnbinder = ButterKnife.bind(this);
    }

    @Override
    public void unbindButterknife() {
        mUnbinder.unbind();
    }

    @Override
    public void init() {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }




}
