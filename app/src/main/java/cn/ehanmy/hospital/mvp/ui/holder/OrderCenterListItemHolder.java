/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ehanmy.hospital.mvp.ui.holder;

import android.graphics.Color;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.order.GoodsOrderBean;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderBean;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderMemberInfoBean;
import cn.ehanmy.hospital.mvp.ui.adapter.OrderCenterListAdapter;
import cn.ehanmy.hospital.mvp.ui.widget.MoneyView;

/**
 * ================================================
 * 展示 {@link BaseHolder} 的用法
 * <p>
 * Created by JessYan on 9/4/16 12:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class OrderCenterListItemHolder extends BaseHolder<OrderBean> {

    private static final SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.line2)
    View line2;

    @BindView(R.id.order_project)
    TextView order_project;
    @BindView(R.id.order_phone)
    TextView order_phone;

    @BindView(R.id.order_price_title)
    View order_price_title;
    @BindView(R.id.order_price)
    MoneyView order_price;

    @BindView(R.id.order_secend_parent)
    View order_secend_parent;
    @BindView(R.id.order_secend_price_title)
    View order_secend_price_title;
    @BindView(R.id.order_secend_price)
    MoneyView order_secend_price;

    @BindView(R.id.order_time)
    TextView order_time;

    @BindView(R.id.order_id)
    TextView order_id;
    @BindView(R.id.order_status)
    TextView order_status;

    @BindView(R.id.detail)
    View detail;
    @BindView(R.id.pay)
    View pay;

    private OrderCenterListAdapter.OnChildItemClickLinstener onChildItemClickLinstener;

    public OrderCenterListItemHolder(View itemView, OrderCenterListAdapter.OnChildItemClickLinstener onChildItemClickLinstener) {
        super(itemView);
        detail.setOnClickListener(this);
        pay.setOnClickListener(this);
        this.onChildItemClickLinstener = onChildItemClickLinstener;
    }

    @Override
    public void onClick(View view) {
        if (null != onChildItemClickLinstener) {
            switch (view.getId()) {
                case R.id.detail:
                    onChildItemClickLinstener.onChildItemClick(view, OrderCenterListAdapter.ViewName.DETAIL, getAdapterPosition());
                    return;
                case R.id.pay:
                    onChildItemClickLinstener.onChildItemClick(view, OrderCenterListAdapter.ViewName.PAY, getAdapterPosition());
                    return;
            }
        }
        super.onClick(view);
    }

    @Override
    public void setData(OrderBean order, int position) {

        order_id.setVisibility(View.VISIBLE);
        order_status.setVisibility(View.VISIBLE);
        order_id.setText(order.getOrderId());
        order_status.setText(order.getOrderStatusDesc());
        String orderStatus = order.getOrderStatus();
        if (TextUtils.isEmpty(orderStatus)) {
            order_status.setTextColor(Color.parseColor("#0096FF"));
        } else {
            switch (orderStatus) {
                case "1":
                case "2":
                    order_status.setTextColor(Color.parseColor("#0096FF"));
                    break;
                case "5":
                    order_status.setTextColor(Color.parseColor("#FE0000"));
                    break;
            }
        }

        GoodsOrderBean goodsOrderBean = order.getGoodsList().get(0);
        OrderMemberInfoBean member = order.getMember();
        order_price_title.setVisibility(View.GONE);
        order_secend_price_title.setVisibility(View.GONE);
        order_price.setVisibility(View.VISIBLE);
        order_secend_price.setVisibility(View.VISIBLE);
        order_project.setGravity(Gravity.LEFT | Gravity.CENTER_VERTICAL);
        if (goodsOrderBean != null) {
            order_project.setText(goodsOrderBean.getName());
            order_price.setMoneyText(String.valueOf(goodsOrderBean.getSalePrice()));
            order_secend_price.setMoneyText(String.valueOf(goodsOrderBean.getTailMoney()));
        }
        if (member != null) {
            order_phone.setText(member.getMobile());
        }
        order_time.setText(SIMPLE_DATE_FORMAT.format(new Date(order.getOrderTime())));
        line2.setBackgroundColor(Color.WHITE);
        line1.setVisibility(View.VISIBLE);
        String orderListStatus = order.getOrderListStatus();
        if (TextUtils.isEmpty(orderListStatus)) {
            order_secend_parent.setVisibility(View.VISIBLE);
            switch (order.getOrderStatus()) {
                case "1":
                case "2":
                    pay.setVisibility(View.VISIBLE);
                    break;
                default:
                    pay.setVisibility(View.GONE);
                    break;
            }
        } else {
            switch (orderListStatus) {
                case "1":
                    pay.setVisibility(View.VISIBLE);
                    order_secend_parent.setVisibility(View.GONE);
                    break;
                case "2":
                    pay.setVisibility(View.VISIBLE);
                    order_secend_parent.setVisibility(View.VISIBLE);
                    break;
                case "5":
                    order_secend_parent.setVisibility(View.GONE);
                    pay.setVisibility(View.GONE);
                    break;
            }
        }

    }

    @Override
    protected void onRelease() {
        this.line1 = null;
        this.line2 = null;
        this.order_project = null;
        this.order_phone = null;
        this.order_price_title = null;
        this.order_price = null;
        this.order_secend_parent = null;
        this.order_secend_price_title = null;
        this.order_secend_price = null;
        this.order_time = null;
        this.detail = null;
        this.pay = null;
    }
}

