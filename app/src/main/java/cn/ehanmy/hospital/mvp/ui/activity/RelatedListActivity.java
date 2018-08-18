package cn.ehanmy.hospital.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.List;

import butterknife.BindView;
import cn.ehanmy.hospital.di.component.DaggerRelatedListComponent;
import cn.ehanmy.hospital.di.module.RelatedListModule;
import cn.ehanmy.hospital.mvp.contract.RelatedListContract;
import cn.ehanmy.hospital.mvp.model.entity.Order;
import cn.ehanmy.hospital.mvp.presenter.RelatedListPresenter;

import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.ui.adapter.RelatedListAdapter;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class RelatedListActivity extends BaseActivity<RelatedListPresenter> implements RelatedListContract.View {

    @BindView(R.id.title_Layout)
    View title;

    @BindView(R.id.contentList)
    RecyclerView contentList;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerRelatedListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .relatedListModule(new RelatedListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_related_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title,this,"关联列表");
        contentList.setLayoutManager(new LinearLayoutManager(this));
        mPresenter.requestRelatedList();
    }

    public void updateList(List<Order> orderList){
        this.contentList.setAdapter(new RelatedListAdapter(orderList));
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
