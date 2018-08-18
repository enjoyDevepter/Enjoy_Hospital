package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.di.component.DaggerCommitOrderComponent;
import cn.ehanmy.hospital.di.module.CommitOrderModule;
import cn.ehanmy.hospital.mvp.contract.CommitOrderContract;
import cn.ehanmy.hospital.mvp.model.entity.UserBean;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.PayEntry;
import cn.ehanmy.hospital.mvp.model.entity.hospital.HospitaInfoBean;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberBean;
import cn.ehanmy.hospital.mvp.model.entity.response.GoodsBuyResponse;
import cn.ehanmy.hospital.mvp.presenter.CommitOrderPresenter;

import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.ui.widget.CustomDialog;
import cn.ehanmy.hospital.util.CacheUtil;


import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 显示订单信息并提供支付入口（支付二维码）
 * */
public class CommitOrderActivity extends BaseActivity<CommitOrderPresenter> implements CommitOrderContract.View {

    public static final String KEY_FOR_ORDER_INDO = "key_for_order_indo";
    public static final String KEY_FOR_REMARK = "KEY_FOR_REMARK";  // 留言
    public static final String KEY_FOR_MONEY = "key_for_money";  // 用户输入的消费币

    @BindView(R.id.title_Layout)
    View title;
    @BindView(R.id.head_image)
    ImageView head_image;  // 用户头像
    @BindView(R.id.image)
    ImageView image;  // 项目图片
    @BindView(R.id.order_name)
    TextView order_name;  // 项目名称
    @BindView(R.id.price)
    TextView price;  // 订单金额
    @BindView(R.id.name)
    TextView name;  // 用户姓名
    @BindView(R.id.phone)
    TextView phone;  // 用户手机
    @BindView(R.id.hospital)
    TextView hospital;  // 用户医院
    @BindView(R.id.member_code)
    TextView member_code;  // 会员名
    @BindView(R.id.addr)
    TextView addr;
    @BindView(R.id.pay_title)
    TextView pay_title;
    @BindView(R.id.pay_image)
    ImageView pay_image;
    @BindView(R.id.make_sure_btn)
    TextView make_sure_btn;

    @Inject
    ImageLoader mImageLoader;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerCommitOrderComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .commitOrderModule(new CommitOrderModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_commit_order; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    public void update(GoodsBuyResponse response){
        mImageLoader.loadImage(this,
                ImageConfigImpl
                        .builder()
                        .url(response.getGoods().getImage())
                        .imageView(image)
                        .build());
        PayEntry payEntry = response.getPayEntryList().get(0);
        mImageLoader.loadImage(this,
                ImageConfigImpl
                        .builder()
                        .url(payEntry.getImage())
                        .imageView(pay_image)
                        .build());
        order_name.setText(response.getGoods().getName());
        price.setText(String.format("%.2f",response.getPayMoney() * 1.0 / 100));
        pay_title.setText(payEntry.getName());
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title,this,"提交订单");
        MemberBean memberBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_MEMBER);
        HospitaInfoBean hospitaInfoBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER_HOSPITAL_INFO);
        mImageLoader.loadImage(this,
                ImageConfigImpl
                        .builder()
                        .url(memberBean.getHeadImage())
                        .imageView(head_image)
                        .build());

        name.setText(memberBean.getRealName());
        phone.setText(memberBean.getMobile());
        hospital.setText(hospitaInfoBean.getName());
        addr.setText(hospitaInfoBean.getAddress());
        member_code.setText(memberBean.getUserName());
        make_sure_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.makeSureOrder();
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
    public Activity getActivity(){
        return this;
    }

    private CustomDialog payOkDialog;

    private void showCancelDailog() {
        payOkDialog = CustomDialog.create(getSupportFragmentManager())
                .setViewListener(new CustomDialog.ViewListener() {
                    @Override
                    public void bindView(View view) {
                        view.findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ArmsUtils.startActivity(GoodsListActivity.class);
                            }
                        });
                    }
                })
                .setLayoutRes(R.layout.pay_ok_dialog_layout)
                .setDimAmount(0.5f)
                .isCenter(true)
                .setWidth(ArmsUtils.dip2px(CommitOrderActivity.this,228))
                .setHeight(ArmsUtils.dip2px(CommitOrderActivity.this,240))
                .show();
    }

    public void makeSure(boolean ok){
        if(ok){
            showCancelDailog();
        }else{
            ArmsUtils.makeText(this,"支付未完成");
        }
    }
}
