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
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.order.GoodsOrderBean;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderBean;
import cn.ehanmy.hospital.mvp.ui.adapter.OrderCenterListAdapter;

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

    @BindView(R.id.parent)
    View parent;
    @BindView(R.id.order_id)
    TextView orderIdTV;
    @BindView(R.id.order_phone)
    TextView phoneTV;
    @BindView(R.id.order_price)
    TextView priceTV;
    @BindView(R.id.order_project)
    TextView projectTV;
    @BindView(R.id.order_status)
    TextView statusTV;
    @BindView(R.id.order_time)
    TextView timeTV;
    @BindView(R.id.detail)
    View detailV;
    @BindView(R.id.pay)
    View payV;
    @BindView(R.id.button_group)
    View buttonGroup;
    @BindView(R.id.order_secend_price)
    TextView order_secend_price;

    @BindView(R.id.btn_group_title)
    View btn_group_title;

    private OrderCenterListAdapter.OnChildItemClickLinstener onChildItemClickLinstener;

    public OrderCenterListItemHolder(View itemView, OrderCenterListAdapter.OnChildItemClickLinstener onChildItemClickLinstener) {
        super(itemView);
        detailV.setOnClickListener(this);
        payV.setOnClickListener(this);
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

        if (position == 0) {
            orderIdTV.setText("编号");
            phoneTV.setText("手机");
            priceTV.setText("金额");
            projectTV.setText("项目");
            statusTV.setText("状态");
            timeTV.setText("时间");
            order_secend_price.setText("尾款");
            buttonGroup.setVisibility(View.GONE);
            btn_group_title.setVisibility(View.VISIBLE);
            parent.setBackgroundColor(Color.parseColor("#FFE4E4E4"));
        } else {
            btn_group_title.setVisibility(View.GONE);
            parent.setBackgroundColor(Color.parseColor("#FFFFFFFF"));
            buttonGroup.setVisibility(View.VISIBLE);
            orderIdTV.setText(order.getOrderId());
            GoodsOrderBean goodsOrderBean = order.getGoodsList().get(0);
            order_secend_price.setText(String.format("¥%.0f", goodsOrderBean.getTailMoney()));
            phoneTV.setText("");
            if (goodsOrderBean != null) {
                priceTV.setText(String.format("¥%.0f", goodsOrderBean.getSalePrice()));
                projectTV.setText(goodsOrderBean.getName());
            }
            statusTV.setText(order.getOrderStatusDesc());
            timeTV.setText(SIMPLE_DATE_FORMAT.format(new Date(order.getOrderTime())));
        }

        String orderListStatus = order.getOrderListStatus();
        if(TextUtils.isEmpty(orderListStatus)){
            order_secend_price.setVisibility(View.VISIBLE);
            switch (order.getOrderStatus()){
                case "1": case "2":
                    payV.setVisibility(View.VISIBLE);
                    break;
                default:
                    payV.setVisibility(View.GONE);
                    break;
            }
        }else{
            switch (orderListStatus){
                case "1":
                    payV.setVisibility(View.VISIBLE);
                    order_secend_price.setVisibility(View.GONE);
                    break;
                case "2":
                    payV.setVisibility(View.VISIBLE);
                    order_secend_price.setVisibility(View.VISIBLE);
                    break;
                case "5":
                    order_secend_price.setVisibility(View.GONE);
                    payV.setVisibility(View.GONE);
                    break;
            }
        }

    }

    @Override
    protected void onRelease() {
        this.orderIdTV = null;
        this.phoneTV = null;
        this.priceTV = null;
        this.projectTV = null;
        this.statusTV = null;
        this.timeTV = null;
        this.detailV = null;
        this.payV = null;
        this.order_secend_price = null;
    }
}

