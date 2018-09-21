package cn.ehanmy.hospital.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import cn.ehanmy.hospital.di.component.DaggerChangePasswordComponent;
import cn.ehanmy.hospital.di.module.ChangePasswordModule;
import cn.ehanmy.hospital.mvp.contract.ChangePasswordContract;
import cn.ehanmy.hospital.mvp.presenter.ChangePasswordPresenter;

import cn.ehanmy.hospital.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ChangePasswordActivity extends BaseActivity<ChangePasswordPresenter> implements ChangePasswordContract.View {

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerChangePasswordComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .changePasswordModule(new ChangePasswordModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_change_password; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
}
