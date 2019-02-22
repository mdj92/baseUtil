package com.dj.basemoudle;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.dj.basemoudle.base.BaseActivity;
import com.dj.basemoudle.constan.Constants;
import com.dj.basemoudle.http.MCallBack;
import com.dj.basemoudle.http.Result;
import com.dj.basemoudle.util.MToast;
import com.dj.basemoudle.util.MUpdate;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

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

    }

    @Override
    public void loadData() {

    }

    @OnClick({R.id.login,R.id.update})
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                doLogin("zs", "123456");
                break;
            case R.id.update:
                doUpdate();
                break;
            default:

        }

    }

    private void doUpdate(){
        MUpdate.newBuilder(this)
                .setUrl(Constants.APP_Upload)
                .setForceUpdate(true)
                .build();
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
