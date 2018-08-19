package cn.ehanmy.hospital.mvp.ui.activity;

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
import cn.ehanmy.hospital.di.component.DaggerOrderInfoComponent;
import cn.ehanmy.hospital.di.module.OrderInfoModule;
import cn.ehanmy.hospital.mvp.contract.OrderInfoContract;
import cn.ehanmy.hospital.mvp.model.entity.Order;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderBean;
import cn.ehanmy.hospital.mvp.presenter.OrderInfoPresenter;

import cn.ehanmy.hospital.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;

/**订单详情页面*/
public class OrderInfoActivity extends BaseActivity<OrderInfoPresenter> implements OrderInfoContract.View {

    public static final String KEY_FOR_ORDER_ID = "key_for_order_id";

    @BindView(R.id.title_Layout)
    View title;
    @BindView(R.id.form_id)
    TextView form_id;
    @BindView(R.id.form_state)
    TextView form_state;
    @BindView(R.id.form_remark)
    TextView form_remark;

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
