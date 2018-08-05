package me.jessyan.mvparms.demo.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import butterknife.BindView;
import me.jessyan.mvparms.demo.di.component.DaggerShopAppointmentComponent;
import me.jessyan.mvparms.demo.di.module.ShopAppointmentModule;
import me.jessyan.mvparms.demo.mvp.contract.ShopAppointmentContract;
import me.jessyan.mvparms.demo.mvp.model.ShopAppointmentModel;
import me.jessyan.mvparms.demo.mvp.model.entity.Order;
import me.jessyan.mvparms.demo.mvp.model.entity.ShopAppointment;
import me.jessyan.mvparms.demo.mvp.presenter.ShopAppointmentPresenter;

import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.mvp.ui.adapter.OnChildItemClickLinstener;
import me.jessyan.mvparms.demo.mvp.ui.adapter.OrderCenterListAdapter;
import me.jessyan.mvparms.demo.mvp.ui.adapter.ShopAppointmentListAdapter;
import me.jessyan.mvparms.demo.mvp.ui.adapter.ViewName;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ShopAppointmentActivity extends BaseActivity<ShopAppointmentPresenter> implements ShopAppointmentContract.View {

    @BindView(R.id.title_Layout)
    View title;

    @BindView(R.id.appointmentint)
    TextView appointment;
    @BindView(R.id.over)
    TextView over;
    @BindView(R.id.cancel)
    TextView cancel;
    @BindView(R.id.all)
    TextView all;

    private TextView currTextView;
    private int currType;

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

    @BindView(R.id.contentList)
    RecyclerView contentList;

    private int normalColor = Color.parseColor("#333333");
    private int currColor = Color.parseColor("#3DBFE8");


    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerShopAppointmentComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .shopAppointmentModule(new ShopAppointmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_shop_appointment; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title,this,"店铺预约");
        currTextView = appointment;
        appointment.setTextColor(currColor);

        appointment.setOnClickListener(onTypeClickListener);
        over.setOnClickListener(onTypeClickListener);
        all.setOnClickListener(onTypeClickListener);
        cancel.setOnClickListener(onTypeClickListener);

        code.setVisibility(View.GONE);
        search.setOnClickListener(onSearchClickListener);
        clear.setOnClickListener(onSearchClickListener);
        contentList.setLayoutManager(new LinearLayoutManager(this));
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

    private View.OnClickListener onTypeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == currTextView.getId()){
                return;
            }
            currTextView.setTextColor(normalColor);
            switch (v.getId()){
                case R.id.appointmentint:
                    currTextView = appointment;
                    currType = ShopAppointmentModel.SEARCH_TYPE_APPOINTMENT;
                    break;
                case R.id.over:
                    currType = ShopAppointmentModel.SEARCH_TYPE_OVER;
                    currTextView = over;
                    break;
                case R.id.cancel:
                    currTextView = cancel;
                    currType = ShopAppointmentModel.SEARCH_TYPE_CANCEL;
                    break;
                case R.id.all:
                    currType = ShopAppointmentModel.SEARCH_TYPE_ALL;
                    currTextView = all;
                    break;
            }

            currTextView.setTextColor(currColor);
        }
    };

    private void doSearch(){
        String s = searchKey.getText().toString();
        if(TextUtils.isEmpty(s)){
            ArmsUtils.makeText(this,"请输入搜索关键字后重试");
            return;
        }

        mPresenter.doSearch(s,currType);
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
                    contentList.setAdapter(null);
                    break;
            }
            hideImm();
        }
    };


    public void updateList(List<ShopAppointment> orderList){
        ShopAppointmentListAdapter adapter = new ShopAppointmentListAdapter(orderList);
        adapter.setOnChildItemClickLinstener(new OnChildItemClickLinstener() {
            @Override
            public void onChildItemClick(View v, ViewName viewname, int position) {
                if(position == 0){
                    return;
                }
                switch (viewname){
                    case DETAIL:
                        Intent intent = new Intent(ShopAppointmentActivity.this,ShopAppointmentInfoActivity.class);
                        intent.putExtra(ShopAppointmentInfoActivity.KEY_FOR_DATA,adapter.getItem(position));
                        launchActivity(intent);
                        break;
                    case RELATED:
                        break;
                    case CANCEL:
                        break;
                }
            }
        });
        contentList.setAdapter(adapter);
    }

    private void hideImm(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }
}
