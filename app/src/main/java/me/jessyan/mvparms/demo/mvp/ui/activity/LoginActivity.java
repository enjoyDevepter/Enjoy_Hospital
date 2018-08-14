package me.jessyan.mvparms.demo.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.di.component.DaggerLoginComponent;
import me.jessyan.mvparms.demo.di.module.LoginModule;
import me.jessyan.mvparms.demo.mvp.contract.LoginContract;
import me.jessyan.mvparms.demo.mvp.presenter.LoginPresenter;
import me.jessyan.mvparms.demo.mvp.ui.widget.CustomProgressDailog;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginContract.View, View.OnClickListener {

    @BindView(R.id.username)
    EditText userNameET;
    @BindView(R.id.password)
    EditText passwordET;
    @BindView(R.id.login)
    View loginV;
    @Inject
    RxPermissions mRxPermissions;

    @BindView(R.id.parent)
    View parent;

    CustomProgressDailog progressDailog;

    @Override
    public void setupActivityComponent(AppComponent appComponent) {
        DaggerLoginComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .loginModule(new LoginModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(Bundle savedInstanceState) {
        return R.layout.activity_login; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(Bundle savedInstanceState) {
        loginV.setOnClickListener(this);
        mPresenter.requestPermissions();
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideImm();
            }
        });
    }


    @Override
    public void showLoading() {
        progressDailog = new CustomProgressDailog(this);
        progressDailog.show();
    }

    @Override
    public void hideLoading() {
        progressDailog.dismiss();
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                login();
                break;
        }
    }

    private void login() {
        mPresenter.login(userNameET.getText().toString(), passwordET.getText().toString());
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void goMainPage() {
        ArmsUtils.startActivity(MainActivity.class);
    }

    @Override
    public RxPermissions getRxPermissions() {
        return mRxPermissions;
    }
}
