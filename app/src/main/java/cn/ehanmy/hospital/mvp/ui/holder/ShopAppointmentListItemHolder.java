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
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.ShopAppointment;

/**
 * ================================================
 * 展示 {@link BaseHolder} 的用法
 * <p>
 * Created by JessYan on 9/4/16 12:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class ShopAppointmentListItemHolder extends BaseHolder<ShopAppointment> {

    @BindView(R.id.parent)
    View parent;
    @BindView(R.id.order_id)
    TextView orderIdTV;
    @BindView(R.id.order_phone)
    TextView phoneTV;
    @BindView(R.id.order_project)
    TextView projectTV;
    @BindView(R.id.order_related)
    TextView order_related;
    @BindView(R.id.order_status)
    TextView statusTV;
    @BindView(R.id.order_time)
    TextView timeTV;
    @BindView(R.id.related)
    View related;
    @BindView(R.id.cancel)
    View cancel;
    @BindView(R.id.detail)
    View detailV;
    @BindView(R.id.button_group)
    View buttonGroup;

    private OnChildItemClickLinstener onChildItemClickLinstener;

    public ShopAppointmentListItemHolder(View itemView, OnChildItemClickLinstener onChildItemClickLinstener) {
        super(itemView);
        detailV.setOnClickListener(this);
        cancel.setOnClickListener(this);
        related.setOnClickListener(this);
        this.onChildItemClickLinstener = onChildItemClickLinstener;
    }


    public interface OnChildItemClickLinstener {
        void onChildItemClick(View v, ViewName viewname, int position);
    }

    public enum ViewName{
        DETAIL,RELATED,CANCEL
    }


    @Override
    public void setData(ShopAppointment shopAppointment, int position) {
        if(position == 0){
            orderIdTV.setText("编号");
            phoneTV.setText("手机");
            order_related.setText("关联");
            projectTV.setText("项目");
            statusTV.setText("状态");
            timeTV.setText("时间");
            buttonGroup.setVisibility(View.INVISIBLE);
            parent.setBackgroundColor(Color.parseColor("#E4E4E4"));
        }else{
            parent.setBackgroundColor(Color.parseColor("#FFFFFF"));
            buttonGroup.setVisibility(View.VISIBLE);
            orderIdTV.setText(shopAppointment.getOrderId());
            phoneTV.setText(shopAppointment.getPhone());
            order_related.setText(shopAppointment.getRelated());
            projectTV.setText(shopAppointment.getProject());
            statusTV.setText(shopAppointment.getStatus());
            timeTV.setText(shopAppointment.getTime());
        }
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
                    onChildItemClickLinstener.onChildItemClick(view, ViewName.DETAIL, getAdapterPosition());
                    return;
                case R.id.related:
                    onChildItemClickLinstener.onChildItemClick(view, ViewName.RELATED, getAdapterPosition());
                    return;
                case R.id.cancel:
                    onChildItemClickLinstener.onChildItemClick(view,ViewName.CANCEL,getAdapterPosition());
                    return;
            }
        }
        super.onClick(view);
    }

    @Override
    protected void onRelease() {
        this.orderIdTV = null;
        this.phoneTV = null;
        this.order_related = null;
        this.projectTV = null;
        this.statusTV = null;
        this.timeTV = null;
        this.detailV = null;
        this.cancel = null;
        this.related = null;
    }
}
