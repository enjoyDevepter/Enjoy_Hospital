package cn.ehanmy.hospital.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.io.Serializable;

import butterknife.BindView;
import cn.ehanmy.hospital.di.component.DaggerShopAppointmentInfoComponent;
import cn.ehanmy.hospital.di.module.ShopAppointmentInfoModule;
import cn.ehanmy.hospital.mvp.contract.ShopAppointmentInfoContract;
import cn.ehanmy.hospital.mvp.model.entity.ShopAppointment;
import cn.ehanmy.hospital.mvp.presenter.ShopAppointmentInfoPresenter;

import cn.ehanmy.hospital.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ShopAppointmentInfoActivity extends BaseActivity<ShopAppointmentInfoPresenter> implements ShopAppointmentInfoContract.View {

    public static final String KEY_FOR_DATA = "data";

    @BindView(R.id.title_Layout)
    View title;

    @BindView(R.id.related)
    View related;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerShopAppointmentInfoComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .shopAppointmentInfoModule(new ShopAppointmentInfoModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_shop_appointment_info; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    private ShopAppointment shopAppointment;

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title,this,"预约详细");
        related.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ShopAppointmentInfoActivity.this,RelatedListActivity.class);
                launchActivity(intent);
            }
        });
        Intent intent = getIntent();
        Serializable serializableExtra = intent.getSerializableExtra(KEY_FOR_DATA);
        if(serializableExtra == null || !(serializableExtra instanceof ShopAppointment)){
            ArmsUtils.makeText(this,"店铺预约数据不存在");
            return;
        }

        shopAppointment = (ShopAppointment) serializableExtra;
        updateView();
    }

    private void updateView() {

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
