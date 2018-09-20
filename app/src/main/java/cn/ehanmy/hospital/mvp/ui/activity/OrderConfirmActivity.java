package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.http.imageloader.ImageLoader;
import com.jess.arms.http.imageloader.glide.ImageConfigImpl;
import com.jess.arms.utils.ArmsUtils;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.di.component.DaggerOrderConfirmComponent;
import cn.ehanmy.hospital.di.module.OrderConfirmModule;
import cn.ehanmy.hospital.mvp.contract.OrderConfirmContract;
import cn.ehanmy.hospital.mvp.model.entity.Order;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsListBean;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsSpecValueBean;
import cn.ehanmy.hospital.mvp.model.entity.hospital.HospitaInfoBean;
import cn.ehanmy.hospital.mvp.model.entity.member_info.MemberBean;
import cn.ehanmy.hospital.mvp.model.entity.response.GoodsConfirmResponse;
import cn.ehanmy.hospital.mvp.presenter.OrderConfirmPresenter;

import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.util.CacheUtil;


import static com.jess.arms.utils.Preconditions.checkNotNull;

/**确认订单页面，支付页面前通过这个页面确定订单*/
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
    @BindView(R.id.xiaofeibi_edit)
    EditText xiaofeibi_edit;
    @BindView(R.id.three_point)
    View three_point;

    @BindView(R.id.expend)
    View expend;
    @BindView(R.id.expend_image)
    ImageView expend_image;
    @BindView(R.id.expend_project_name)
    TextView expend_project_name;
    @BindView(R.id.expend_price)
    TextView expend_price;
    @BindView(R.id.expend_project_id)
    TextView expend_project_id;
    @BindView(R.id.skill_list)
    TagFlowLayout skill_list;
    @BindView(R.id.expend_close)
    View expend_close;

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

    private GoodsSpecValueBean goodsSpecValueBean;

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order_confirm; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    private GoodsConfirmResponse goodsConfirmResponse;
    private long balance;  // 用户当前剩余的消费币（单位分）

    public void update(GoodsConfirmResponse goodsConfirmResponse){

        this.goodsConfirmResponse = goodsConfirmResponse;

        GoodsListBean goods = goodsConfirmResponse.getGoods();
        mImageLoader.loadImage(this,
                ImageConfigImpl
                        .builder()
                        .url(goods.getImage())
                        .imageView(image)
                        .build());
        mImageLoader.loadImage(this,
                ImageConfigImpl
                        .builder()
                        .url(goods.getImage())
                        .imageView(expend_image)
                        .build());

        order_name.setText(goods.getName());
        expend_project_name.setText(goods.getName());
        price.setText(String.format("%.2f",goods.getSalePrice()));
        expend_price.setText(String.format("%.2f",goods.getSalePrice()));
        List<GoodsSpecValueBean> goodsSpecValueList = goodsConfirmResponse.getGoodsSpecValueList();
        if(goodsSpecValueList != null && goodsSpecValueList.size() != 0){
            goodsSpecValueBean = goodsSpecValueList.get(0);
            skill.setText(goodsSpecValueBean.getSpecValueName());
        }
        balance = goodsConfirmResponse.getBalance();
        xiaofeibi_shengyu.setText(""+ balance);
        goods_price.setText(String.format("%.2f",goodsConfirmResponse.getPrice() * 1.0 / 100));
        xiaofeibi_count.setText(String.format("%.2f",goodsConfirmResponse.getMoney() * 1.0 / 100));
        all_price.setText(String.format("%.2f",goodsConfirmResponse.getPayMoney() * 1.0 / 100));
        expend_project_id.setText(goodsConfirmResponse.getGoods().getMerchId());

        TagAdapter<GoodsSpecValueBean> adapter = new TagAdapter<GoodsSpecValueBean>(new ArrayList<>(goodsSpecValueList)) {
            @Override
            public View getView(FlowLayout parent, int position, GoodsSpecValueBean s) {
                TextView tv = (TextView) LayoutInflater.from(ArmsUtils.getContext()).inflate(R.layout.order_confirm_skill_item, null, false);
                tv.setText(s.getSpecValueName());
                tv.setTextColor(Color.BLACK);
                return tv;
            }
        };
        skill_list.setAdapter(adapter);
        skill_list.setMaxSelectCount(1);
        ViewGroup childAt = (ViewGroup) skill_list.getChildAt(0);
        childAt.setSelected(true);
        ((TextView)childAt.getChildAt(0)).setTextColor(Color.WHITE);
        TagFlowLayout.OnTagClickListener onTagClickListener = new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                if (view.isSelected()) {
                    return true;
                }
                updateSpecSelect(view, parent);
                currGoodsSpec = goodsSpecValueList.get(position);
                currGoodsSpecIndex = position;
                requestConfirmInfo();
                expend.setVisibility(View.GONE);
                return true;
            }
        };
        updateSpecSelect(skill_list.getChildAt(currGoodsSpecIndex),skill_list);
        skill_list.setOnTagClickListener(onTagClickListener);
    }

    private void updateSpecSelect(View view, FlowLayout parent) {
        int childCount = parent.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = parent.getChildAt(i);
            childAt.setSelected(false);
            if (childAt instanceof ViewGroup && ((ViewGroup) childAt).getChildAt(0) instanceof TextView) {
                ((TextView) ((ViewGroup) childAt).getChildAt(0)).setTextColor(Color.BLACK);
            }
        }

        view.setSelected(true);
        if (view instanceof ViewGroup && ((ViewGroup) view).getChildAt(0) instanceof TextView) {
            TextView childAt1 = (TextView) ((ViewGroup) view).getChildAt(0);
            childAt1.setTextColor(Color.WHITE);
            skill.setText(childAt1.getText());
        }
    }

    private void requestConfirmInfo(){
        if(currGoodsSpec == null){
            mPresenter.requestConfirmInfo(money);
        }else{
            mPresenter.requestConfirmInfoWithSpec(money,currGoodsSpec.getSpecValueId());
        }
    }

    private GoodsSpecValueBean currGoodsSpec = null;
    private int currGoodsSpecIndex = 0;
    private long money = 0;

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        xiaofeibi_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                updateMoney(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        xiaofeibi_edit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus){
                    xiaofeibi_edit.setText(""+money);
                    requestConfirmInfo();
                }
            }
        });
        new TitleUtil(title,this,"确认订单");
        expend_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expend.setVisibility(View.GONE);
            }
        });
        three_point.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expend.setVisibility(View.VISIBLE);
            }
        });
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
                updateMoney(xiaofeibi_edit.getText());
                intent.putExtra(CommitOrderActivity.KEY_FOR_MONEY,money);
                ArmsUtils.startActivity(intent);
            }
        });
    }

    private void updateMoney(CharSequence text){
        if(text == null || TextUtils.isEmpty(text.toString())){
            money = 0;
        }else{
            try{
                money = Integer.parseInt(text.toString());
                if(money * 100 > balance){
                    money = balance / 100;
                }
            }catch (Exception e){
                money = 0;
            }
        }
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
