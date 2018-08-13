package me.jessyan.mvparms.demo.mvp.ui.activity;

import android.app.Activity;
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

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import me.jessyan.mvparms.demo.di.component.DaggerOrderConfirmComponent;
import me.jessyan.mvparms.demo.di.module.OrderConfirmModule;
import me.jessyan.mvparms.demo.mvp.contract.OrderConfirmContract;
import me.jessyan.mvparms.demo.mvp.model.entity.Order;
import me.jessyan.mvparms.demo.mvp.model.entity.goods_list.GoodsListBean;
import me.jessyan.mvparms.demo.mvp.model.entity.goods_list.GoodsSpecValueBean;
import me.jessyan.mvparms.demo.mvp.model.entity.hospital.HospitaInfoBean;
import me.jessyan.mvparms.demo.mvp.model.entity.member_info.MemberBean;
import me.jessyan.mvparms.demo.mvp.model.entity.response.GoodsConfirmResponse;
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
    TextView hosptial;
    @BindView(R.id.remark_edit)
    EditText remark_edit;
    @BindView(R.id.image)
    ImageView image;
    @BindView(R.id.price)
    TextView price;  // 项目中的金额
    @BindView(R.id.order_name)
    TextView order_name;
    @BindView(R.id.goods_price)
    TextView goods_price;  // 商品金额
    @BindView(R.id.all_price)
    TextView all_price;  // 订单总金额
    @BindView(R.id.skill)
    TextView skill;
    @BindView(R.id.xiaofeibi_shengyu)
    TextView xiaofeibi_shengyu;
    @BindView(R.id.xiaofeibi_count)
    TextView xiaofeibi_count;
    @BindView(R.id.member_phone)
    TextView member_phone;
    @BindView(R.id.confirm_order)
    TextView confirm_order;

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

    private GoodsConfirmResponse goodsConfirmResponse;

    public void update(GoodsConfirmResponse goodsConfirmResponse){

        this.goodsConfirmResponse = goodsConfirmResponse;

        GoodsListBean goods = goodsConfirmResponse.getGoods();
        mImageLoader.loadImage(this,
                ImageConfigImpl
                        .builder()
                        .url(goods.getImage())
                        .imageView(image)
                        .build());

        order_name.setText(goods.getName());
        price.setText(String.format("%.2f",goods.getSalePrice()));
        List<GoodsSpecValueBean> goodsSpecValueList = goodsConfirmResponse.getGoodsSpecValueList();
        if(goodsSpecValueList != null && goodsSpecValueList.size() != 0){
            skill.setText(goodsSpecValueList.get(0).getSpecValueName());
        }
        xiaofeibi_shengyu.setText(""+goodsConfirmResponse.getBalance());
        goods_price.setText(String.format("%.2f",goodsConfirmResponse.getPrice() * 1.0 / 100));
        xiaofeibi_count.setText(String.format("%.2f",goodsConfirmResponse.getMoney() * 1.0 / 100));
        all_price.setText(String.format("%.2f",goodsConfirmResponse.getPayMoney() * 1.0 / 100));
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title,this,"确认订单");
        MemberBean memberBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_MEMBER);
        System.out.println("member = "+memberBean);
        member_code.setText(memberBean.getUserName());
        member_phone.setText(memberBean.getMobile());

        HospitaInfoBean hospitalInfoBean = CacheUtil.getConstant(CacheUtil.CACHE_KEY_USER_HOSPITAL_INFO);
        hosptial.setText(hospitalInfoBean.getName());
        confirm_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ArmsUtils.getContext(),CommitOrderActivity.class);
                intent.putExtra(CommitOrderActivity.KEY_FOR_ORDER_INDO,goodsConfirmResponse);
                intent.putExtra(CommitOrderActivity.KEY_FOR_REMARK,remark_edit.getText().toString());
                ArmsUtils.startActivity(intent);
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
    public Activity getActivity() {
        return this;
    }
}
