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
package me.jessyan.mvparms.demo.mvp.ui.holder;

import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;

import butterknife.BindView;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.mvp.model.entity.Order;
import me.jessyan.mvparms.demo.mvp.ui.adapter.OrderCenterListAdapter;

/**
 * ================================================
 * 展示 {@link BaseHolder} 的用法
 * <p>
 * Created by JessYan on 9/4/16 12:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class OrderCenterListItemHolder extends BaseHolder<Order> {

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

    private OrderCenterListAdapter.OnChildItemClickLinstener onChildItemClickLinstener;

    public OrderCenterListItemHolder(View itemView, OrderCenterListAdapter.OnChildItemClickLinstener onChildItemClickLinstener) {
        super(itemView);
        detailV.setOnClickListener(this);
        payV.setOnClickListener(this);
        this.onChildItemClickLinstener = onChildItemClickLinstener;
    }

    @Override
    public void setData(Order order, int position) {
//        Observable.just(data.getName())
//                .subscribe(s -> mName.setText(s));
//        Observable.just(data.getImageId())
//                .subscribe(s -> mImage.setBackgroundResource(s));
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
    protected void onRelease() {
        this.orderIdTV = null;
        this.phoneTV = null;
        this.priceTV = null;
        this.projectTV = null;
        this.statusTV = null;
        this.timeTV = null;
        this.detailV = null;
        this.payV = null;
    }
}
