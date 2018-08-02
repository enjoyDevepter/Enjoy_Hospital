package me.jessyan.mvparms.demo.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import me.jessyan.mvparms.demo.di.component.DaggerOrderFormCenterComponent;
import me.jessyan.mvparms.demo.di.module.OrderFormCenterModule;
import me.jessyan.mvparms.demo.mvp.contract.OrderFormCenterContract;
import me.jessyan.mvparms.demo.mvp.presenter.OrderFormCenterPresenter;

import me.jessyan.mvparms.demo.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class OrderFormCenterActivity extends BaseActivity<OrderFormCenterPresenter> implements OrderFormCenterContract.View {

    @BindView(R.id.title_Layout)
    View title_Layout;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderFormCenterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .orderFormCenterModule(new OrderFormCenterModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order_form_center; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title_Layout,this,"订单中心");
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
