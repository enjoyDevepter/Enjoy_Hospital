package me.jessyan.mvparms.demo.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.mvparms.demo.di.component.DaggerGoodsListComponent;
import me.jessyan.mvparms.demo.di.module.GoodsListModule;
import me.jessyan.mvparms.demo.mvp.contract.GoodsListContract;
import me.jessyan.mvparms.demo.mvp.presenter.GoodsListPresenter;

import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.mvp.ui.adapter.GoodsListAdapter;
import me.jessyan.mvparms.demo.mvp.ui.widget.SpacesItemDecoration;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class GoodsListActivity extends BaseActivity<GoodsListPresenter> implements GoodsListContract.View {

    @BindView(R.id.title_Layout)
    View title;
    @BindView(R.id.goodsList)
    RecyclerView goodsList;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RecyclerView.Adapter mAdapter;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGoodsListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .goodsListModule(new GoodsListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_goods_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title,this,"下单中心");
        ArmsUtils.configRecyclerView(goodsList, mLayoutManager);
        goodsList.setAdapter(mAdapter);
        goodsList.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//        ((GoodsListAdapter) mAdapter).setOnItemClickListener(this);
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

    @Override
    public Activity getActivity() {
        return this;
    }

    private void hideImm() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        // 隐藏软键盘
        imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
    }
}
