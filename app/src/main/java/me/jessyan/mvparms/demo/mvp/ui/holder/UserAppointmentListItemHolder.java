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

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import com.jess.arms.base.BaseHolder;

import butterknife.BindView;
import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.mvp.model.entity.ShopAppointment;
import me.jessyan.mvparms.demo.mvp.model.entity.UserAppointment;
import me.jessyan.mvparms.demo.mvp.ui.adapter.OnChildItemClickLinstener;
import me.jessyan.mvparms.demo.mvp.ui.adapter.ViewName;

/**
 * ================================================
 * 展示 {@link BaseHolder} 的用法
 * <p>
 * Created by JessYan on 9/4/16 12:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class UserAppointmentListItemHolder extends BaseHolder<UserAppointment> {

    @BindView(R.id.parent)
    View parent;
    @BindView(R.id.order_id)
    TextView orderIdTV;
    @BindView(R.id.order_phone)
    TextView phoneTV;
    @BindView(R.id.order_project)
    TextView projectTV;
    @BindView(R.id.order_status)
    TextView statusTV;
    @BindView(R.id.order_time)
    TextView timeTV;
    @BindView(R.id.change_appointment)
    View change_appointment;
    @BindView(R.id.ok)
    View ok;
    @BindView(R.id.cancel)
    View cancel;
    @BindView(R.id.button_group)
    View buttonGroup;

    private OnChildItemClickLinstener onChildItemClickLinstener;

    public UserAppointmentListItemHolder(View itemView, OnChildItemClickLinstener onChildItemClickLinstener) {
        super(itemView);
        ok.setOnClickListener(this);
        cancel.setOnClickListener(this);
        change_appointment.setOnClickListener(this);
        this.onChildItemClickLinstener = onChildItemClickLinstener;
    }

    @Override
    public void setData(UserAppointment userAppointment, int position) {
        if(position == 0){
            orderIdTV.setText("编号");
            phoneTV.setText("手机");
            projectTV.setText("项目");
            statusTV.setText("状态");
            timeTV.setText("时间");
            buttonGroup.setVisibility(View.INVISIBLE);
            parent.setBackgroundColor(Color.parseColor("#E4E4E4"));
        }else{
            parent.setBackgroundColor(Color.parseColor("#FFFFFF"));
            buttonGroup.setVisibility(View.VISIBLE);
            orderIdTV.setText(userAppointment.getOrderId());
            phoneTV.setText(userAppointment.getPhone());
            projectTV.setText(userAppointment.getProject());
            statusTV.setText(userAppointment.getStatus());
            timeTV.setText(userAppointment.getTime());
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
                case R.id.ok:
                    onChildItemClickLinstener.onChildItemClick(view, ViewName.OK, getAdapterPosition());
                    return;
                case R.id.change_appointment:
                    onChildItemClickLinstener.onChildItemClick(view, ViewName.CHANGE_APPOINTMENT, getAdapterPosition());
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
        this.projectTV = null;
        this.statusTV = null;
        this.timeTV = null;
        this.cancel = null;
        ok = null;
        change_appointment = null;
    }
}