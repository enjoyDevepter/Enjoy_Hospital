package me.jessyan.mvparms.demo.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import me.jessyan.mvparms.demo.di.component.DaggerOrderInfoComponent;
import me.jessyan.mvparms.demo.di.module.OrderInfoModule;
import me.jessyan.mvparms.demo.mvp.contract.OrderInfoContract;
import me.jessyan.mvparms.demo.mvp.model.entity.Order;
import me.jessyan.mvparms.demo.mvp.presenter.OrderInfoPresenter;

import me.jessyan.mvparms.demo.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class OrderInfoActivity extends BaseActivity<OrderInfoPresenter> implements OrderInfoContract.View {

    public static final String KEY_FOR_DATA = "data";

    @BindView(R.id.title_Layout)
    View title;

    private Order order;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .orderInfoModule(new OrderInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title,this,"订单详情");
        Object data = getIntent().getSerializableExtra(KEY_FOR_DATA);
        if(data == null || !(data instanceof Order)){
            ArmsUtils.makeText(this,"详情信息不存在");
            killMyself();
            return;
        }

        order = (Order) data;
        showOrder();
    }

    private void showOrder(){
        System.out.println("order = "+order);
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