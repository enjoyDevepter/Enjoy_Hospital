package cn.ehanmy.hospital.mvp.ui.activity;

import android.content.Intent;
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
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.di.component.DaggerBuyCenterComponent;
import cn.ehanmy.hospital.di.module.BuyCenterModule;
import cn.ehanmy.hospital.mvp.contract.BuyCenterContract;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberBean;
import cn.ehanmy.hospital.mvp.presenter.BuyCenterPresenter;
import cn.ehanmy.hospital.util.CacheUtil;

import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 订单中心，查询会员编号页面
 */
public class BuyCenterActivity extends BaseActivity<BuyCenterPresenter> implements BuyCenterContract.View, View.OnClickListener {

    @BindView(R.id.title_Layout)
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
        new TitleUtil(title, this, "下单中心");
        clear_btn.setVisibility(View.GONE);
        buy.setOnClickListener(this);
        search_btn.setOnClickListener(this);
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
        image.setBackground(getResources().getDrawable(codeIsRight ? R.mipmap.member_code_right : R.mipmap.member_code_wrong));
        String defaultStr = "会员编号错误，请重新查询！";
        if(codeIsRight){
            MemberBean memberBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_MEMBER);
            defaultStr = memberBean.getNickName();
            if(!TextUtils.isEmpty(memberBean.getRealName())){
                defaultStr += ("("+memberBean.getRealName()+")");
            }
        }
        hide.setText(defaultStr);
        buy.setVisibility(codeIsRight ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_btn:
                hideImm();
                String s = search_key.getText().toString();
                if (ArmsUtils.isEmpty(s)) {
                    showMessage("请输入会员编号后查询");
                } else {
                    mPresenter.requestHospitalInfo(s);
                }
                break;
            case R.id.buy:
                ArmsUtils.startActivity(GoodsListActivity.class);
                break;

        }
    }
}
