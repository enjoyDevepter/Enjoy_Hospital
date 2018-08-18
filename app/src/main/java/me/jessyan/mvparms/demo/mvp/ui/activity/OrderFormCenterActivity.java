package me.jessyan.mvparms.demo.mvp.ui.activity;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.mvparms.demo.di.component.DaggerOrderFormCenterComponent;
import me.jessyan.mvparms.demo.di.module.OrderFormCenterModule;
import me.jessyan.mvparms.demo.mvp.contract.OrderFormCenterContract;
import me.jessyan.mvparms.demo.mvp.model.OrderFormCenterModel;
import me.jessyan.mvparms.demo.mvp.model.entity.order.OrderBean;
import me.jessyan.mvparms.demo.mvp.presenter.OrderFormCenterPresenter;

import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.mvp.ui.adapter.OnChildItemClickLinstener;
import me.jessyan.mvparms.demo.mvp.ui.adapter.OrderCenterListAdapter;
import me.jessyan.mvparms.demo.mvp.ui.adapter.ViewName;


import static com.jess.arms.utils.Preconditions.checkNotNull;

/**订单中心页面*/
public class OrderFormCenterActivity extends BaseActivity<OrderFormCenterPresenter> implements OrderFormCenterContract.View {

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RecyclerView.Adapter mAdapter;

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

    @BindView(R.id.unpaid)
    TextView unpaid;
    @BindView(R.id.secend)
    TextView secend;
    @BindView(R.id.over)
    TextView over;
    @BindView(R.id.all)
    TextView all;

    @BindView(R.id.contentList)
    RecyclerView contentList;

    private int normalColor = Color.parseColor("#333333");
    private int currColor = Color.parseColor("#3DBFE8");

    // 当前选中的textview
    private TextView currentTab;
    private int currentSearchType;

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
        unpaid.setOnClickListener(onTabClickListener);
        all.setOnClickListener(onTabClickListener);
        secend.setOnClickListener(onTabClickListener);
        over.setOnClickListener(onTabClickListener);
        currentTab = unpaid;
        currentTab.setTextColor(currColor);

        code.setVisibility(View.GONE);
        search.setOnClickListener(onSearchClickListener);
        clear.setOnClickListener(onSearchClickListener);

        contentList.setLayoutManager(mLayoutManager);
        contentList.setAdapter(mAdapter);
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

    }

    public void updateList(List<OrderBean> orderList){
        OrderCenterListAdapter adapter = new OrderCenterListAdapter(orderList);
        adapter.setOnChildItemClickLinstener(new OnChildItemClickLinstener() {
            @Override
            public void onChildItemClick(View v, ViewName viewname, int position) {
                if(position == 0){
                    return;
                }
                switch (viewname){
                    case DETAIL:
                        Intent intent = new Intent(OrderFormCenterActivity.this,OrderInfoActivity.class);
                        intent.putExtra(OrderInfoActivity.KEY_FOR_DATA,adapter.getItem(position));
                        launchActivity(intent);
                        break;
                    case PAY:
                        break;
                }
            }
        });
        contentList.setAdapter(adapter);
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

    private View.OnClickListener onTabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == currentTab.getId()){
                return;
            }
            currentTab.setTextColor(normalColor);
            TextView newText = null;
            switch (v.getId()){
                case R.id.unpaid:
                    newText = unpaid;
                    currentSearchType = OrderFormCenterModel.SEARCH_TYPE_UNPAID;
                    break;
                case R.id.all:
                    newText = all;
                    currentSearchType = OrderFormCenterModel.SEARCH_TYPE_ALL;
                    break;
                case R.id.secend:
                    newText = secend;
                    currentSearchType = OrderFormCenterModel.SEARCH_TYPE_SECEND;
                    break;
                case R.id.over:
                    newText = over;
                    currentSearchType = OrderFormCenterModel.SEARCH_TYPE_OK;
                    break;
            }

            if(newText == null){
                return;
            }
            currentTab = newText;
            currentTab.setTextColor(currColor);
        }
    };

    public Activity getActivity(){
        return this;
    }
}
