package cn.ehanmy.hospital.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import cn.ehanmy.hospital.di.component.DaggerActivityInfoComponent;
import cn.ehanmy.hospital.di.module.ActivityInfoModule;
import cn.ehanmy.hospital.mvp.contract.ActivityInfoContract;
import cn.ehanmy.hospital.mvp.presenter.ActivityInfoPresenter;

import cn.ehanmy.hospital.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ActivityInfoActivity extends BaseActivity<ActivityInfoPresenter> implements ActivityInfoContract.View {

    @BindView(R.id.title_Layout)
    View title;
    @BindView(R.id.add_activity)
    View add_activity;
    @BindView(R.id.tab)
    TabLayout tab;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerActivityInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .activityInfoModule(new ActivityInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title,this,"活动信息");
        tab.addTab(tab.newTab().setText("待审核"));
        tab.addTab(tab.newTab().setText("已审核"));
        tab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                System.out.println("onTabSelected : "+tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
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
