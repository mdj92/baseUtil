package com.dj.basemoudle.base;

import android.view.View;



import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author Marcus
 * @package krt.com.zhjy.base
 * @description
 * @time 2018/9/12
 */
public abstract class BaseFragment extends MBaseFragment {
    private Unbinder mUnbinder;

//    public SpUtil spUtil;

    @Override
    public void bindButterKnife(View view) {
        mUnbinder = ButterKnife.bind(this, view);
    }

    @Override
    public void unBindButterKnife() {
        mUnbinder.unbind();
    }

    @Override
    public void init() {
//        spUtil = ((BaseActivity) mContext).spUtil;
    }

}
