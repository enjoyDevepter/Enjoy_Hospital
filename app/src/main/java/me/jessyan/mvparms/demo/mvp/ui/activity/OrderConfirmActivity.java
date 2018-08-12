package me.jessyan.mvparms.demo.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;

import java.io.Serializable;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.mvparms.demo.di.component.DaggerOrderConfirmComponent;
import me.jessyan.mvparms.demo.di.module.OrderConfirmModule;
import me.jessyan.mvparms.demo.mvp.contract.OrderConfirmContract;
import me.jessyan.mvparms.demo.mvp.model.entity.goods_list.GoodsListBean;
import me.jessyan.mvparms.demo.mvp.presenter.OrderConfirmPresenter;

import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.util.CacheUtil;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class OrderConfirmActivity extends BaseActivity<OrderConfirmPresenter> implements OrderConfirmContract.View {

    public static final String KEY_FOR_GOODS_INFO = "key_for_goods_info";

    @BindView(R.id.title_Layout)
    View title;

    @BindView(R.id.member_code)
    TextView member_code;

    @BindView(R.id.hosptial)
    TextView hosptial;  // 等待接口

    @BindView(R.id.remark_edit)
    EditText remark_edit;

    @BindView(R.id.image)
    ImageView image;

    @BindView(R.id.price)
    TextView price;

    @Inject
    ImageLoader mImageLoader;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderConfirmComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .orderConfirmModule(new OrderConfirmModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order_confirm; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    private GoodsListBean goodsListBean;

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        GoodsListBean serializableExtra = (GoodsListBean) getIntent().getSerializableExtra(KEY_FOR_GOODS_INFO);
        if(serializableExtra instanceof GoodsListBean){
            goodsListBean = serializableExtra;
            fillGoodsList();
        }

        new TitleUtil(title,this,"确认订单");
        String memberCode = CacheUtil.getConstant(CacheUtil.CACHE_KEY_MEMBER_CODE);
        member_code.setText(memberCode);
    }

    private void fillGoodsList(){
        if(goodsListBean == null){
            return;
        }

        mImageLoader.loadImage(this,
                ImageConfigImpl
                        .builder()
                        .url(goodsListBean.getImage())
                        .imageView(image)
                        .build());
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
