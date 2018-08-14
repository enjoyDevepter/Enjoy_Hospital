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
import me.jessyan.mvparms.demo.di.component.DaggerUserAppointmentComponent;
import me.jessyan.mvparms.demo.di.module.UserAppointmentModule;
import me.jessyan.mvparms.demo.mvp.contract.UserAppointmentContract;
import me.jessyan.mvparms.demo.mvp.model.ShopAppointmentModel;
import me.jessyan.mvparms.demo.mvp.model.UserAppointmentModel;
import me.jessyan.mvparms.demo.mvp.model.entity.ShopAppointment;
import me.jessyan.mvparms.demo.mvp.model.entity.UserAppointment;
import me.jessyan.mvparms.demo.mvp.presenter.UserAppointmentPresenter;

import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.mvp.ui.adapter.OnChildItemClickLinstener;
import me.jessyan.mvparms.demo.mvp.ui.adapter.ShopAppointmentListAdapter;
import me.jessyan.mvparms.demo.mvp.ui.adapter.UserAppointmentListAdapter;
import me.jessyan.mvparms.demo.mvp.ui.adapter.ViewName;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class UserAppointmentActivity extends BaseActivity<UserAppointmentPresenter> implements UserAppointmentContract.View {

    @BindView(R.id.title_Layout)
    View title;


    @BindView(R.id.new_appointment)
    TextView new_appointment;
    @BindView(R.id.confirmed)
    TextView confirmed;
    @BindView(R.id.over)
    TextView over;
    @BindView(R.id.all)
    TextView all;

    private TextView currTextView;
    private int currType;

    private int normalColor = Color.parseColor("#333333");
    private int currColor = Color.parseColor("#3DBFE8");

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

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUserAppointmentComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .userAppointmentModule(new UserAppointmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_user_appointment; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title,this,"用户预约");
        currTextView = new_appointment;
        new_appointment.setTextColor(currColor);

        new_appointment.setOnClickListener(onTypeClickListener);
        over.setOnClickListener(onTypeClickListener);
        all.setOnClickListener(onTypeClickListener);
        confirmed.setOnClickListener(onTypeClickListener);

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
                case R.id.new_appointment:
                    currTextView = new_appointment;
                    currType = UserAppointmentModel.SEARCH_TYPE_NEW;
                    break;
                case R.id.over:
                    currType = UserAppointmentModel.SEARCH_TYPE_OVER;
                    currTextView = over;
                    break;
                case R.id.confirmed:
                    currTextView = confirmed;
                    currType = UserAppointmentModel.SEARCH_TYPE_CONFIRMED;
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


    public void updateList(List<UserAppointment> userAppointments){
        UserAppointmentListAdapter adapter = new UserAppointmentListAdapter(userAppointments);
        adapter.setOnChildItemClickLinstener(new OnChildItemClickLinstener() {
            @Override
            public void onChildItemClick(View v, ViewName viewname, int position) {
                if(position == 0){
                    return;
                }
                switch (viewname){
                    case OK:
                        break;
                    case CHANGE_APPOINTMENT:
                        break;
                    case CANCEL:
                        break;
                }
            }
        });
        contentList.setAdapter(adapter);
    }

}
