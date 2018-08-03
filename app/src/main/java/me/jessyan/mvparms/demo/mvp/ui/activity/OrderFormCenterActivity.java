package me.jessyan.mvparms.demo.mvp.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import butterknife.OnClick;
import me.jessyan.mvparms.demo.di.component.DaggerOrderFormCenterComponent;
import me.jessyan.mvparms.demo.di.module.OrderFormCenterModule;
import me.jessyan.mvparms.demo.mvp.contract.OrderFormCenterContract;
import me.jessyan.mvparms.demo.mvp.presenter.OrderFormCenterPresenter;

import me.jessyan.mvparms.demo.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class OrderFormCenterActivity extends BaseActivity<OrderFormCenterPresenter> implements OrderFormCenterContract.View {

    @BindView(R.id.title_Layout)
    View title_Layout;
    @BindView(R.id.search_layout)
    View search_layout;

    @BindView(R.id.code)
    View code;
    @BindView(R.id.search_btn)
    View search;
    @BindView(R.id.clear_btn)
    View clear;
    @BindView(R.id.search_key)
    EditText searchKey;

    @BindView(R.id.appointment)
    TextView appointment;
    @BindView(R.id.over)
    TextView over;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.all)
    TextView all;

    private int normalColor = Color.parseColor("#333333");
    private int currColor = Color.parseColor("#3DBFE8");

    // 当前选中的textview
    private TextView currentTab;

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
        appointment.setOnClickListener(onTabClickListener);
        all.setOnClickListener(onTabClickListener);
        over.setOnClickListener(onTabClickListener);
        cancel.setOnClickListener(onTabClickListener);
        currentTab = appointment;
        currentTab.setTextColor(currColor);

        code.setVisibility(View.GONE);
        search.setOnClickListener(onSearchClickListener);
        clear.setOnClickListener(onSearchClickListener);
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

    private void doSearch(){
        String s = searchKey.getText().toString();
        if(TextUtils.isEmpty(s)){
            ArmsUtils.makeText(this,"请输入搜索关键字后重试");
            return;
        }

        mPresenter.doSearch(s);
    }

    private View.OnClickListener onSearchClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.search_btn:
                    doSearch();
                    break;
                case R.id.clear_btn:
                    searchKey.setText("");
                    break;
            }
        }
    };

    private View.OnClickListener onTabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == currentTab.getId()){
                return;
            }
            currentTab.setTextColor(normalColor);
            TextView newText = null;
            switch (v.getId()){
                case R.id.appointment:
                    newText = appointment;
                    break;
                case R.id.all:
                    newText = all;
                    break;
                case R.id.over:
                    newText = over;
                    break;
                case R.id.cancel:
                    newText = cancel;
                    break;
            }

            if(newText == null){
                return;
            }
            currentTab = newText;
            currentTab.setTextColor(currColor);
        }
    };
}
