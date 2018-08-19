package cn.ehanmy.hospital.mvp.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.utils.ArmsUtils;

import butterknife.BindView;
import cn.ehanmy.hospital.di.component.DaggerBuyCenterComponent;
import cn.ehanmy.hospital.di.module.BuyCenterModule;
import cn.ehanmy.hospital.mvp.contract.BuyCenterContract;
import cn.ehanmy.hospital.mvp.presenter.BuyCenterPresenter;

import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.util.CacheUtil;


import static com.jess.arms.utils.Preconditions.checkNotNull;

/**订单中心，查询会员编号页面*/
public class BuyCenterActivity extends BaseActivity<BuyCenterPresenter> implements BuyCenterContract.View {

    @BindView(R.id.title)
    View title;

    @BindView(R.id.clear_btn)
    View clear_btn;

    @BindView(R.id.search_btn)
    View search_btn;

    @BindView(R.id.search_key)
    EditText search_key;

    @BindView(R.id.image)
    View image;

    @BindView(R.id.hide)
    TextView hide;

    @BindView(R.id.buy)
    View buy;

    private String memberCode;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerBuyCenterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .buyCenterModule(new BuyCenterModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_buy_center; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title,this,"下单中心");
        clear_btn.setVisibility(View.GONE);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                killMyself();
                ArmsUtils.startActivity(GoodsListActivity.class);

            }
        });
        search_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideImm();
                String s = search_key.getText().toString();
                if(TextUtils.isEmpty(s)){
                    ArmsUtils.makeText(BuyCenterActivity.this,"请输入会员编号后查询");
                }else{
                    memberCode = s;
                    mPresenter.requestHospitalInfo(s);
                }
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

    @Override
    public void updateCodeisRight(boolean codeIsRight) {
        image.setVisibility(View.VISIBLE);
        image.setBackground(getResources().getDrawable(codeIsRight? R.mipmap.member_code_right : R.mipmap.member_code_wrong));

        hide.setText(codeIsRight ? "会员编号正确，请继续下单" : "会员编号错误，请重新查询！");
        buy.setVisibility(codeIsRight ? View.VISIBLE : View.GONE);
    }
}