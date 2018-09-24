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

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.OrderProjectDetailBean;

import static cn.ehanmy.hospital.mvp.ui.adapter.ViewName.CANCEL;
import static cn.ehanmy.hospital.mvp.ui.adapter.ViewName.CHANGE_APPOINTMENT;
import static cn.ehanmy.hospital.mvp.ui.adapter.ViewName.OK;


/**
 * ================================================
 * 展示 {@link BaseHolder} 的用法
 * <p>
 * Created by JessYan on 9/4/16 12:56
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class UserAppointmentHolder extends BaseHolder<OrderProjectDetailBean> {

    @BindView(R.id.order_id)
    TextView order_id;
    @BindView(R.id.order_phone)
    TextView order_phone;
    @BindView(R.id.order_project)
    TextView order_project;
    @BindView(R.id.order_status)
    TextView order_status;
    @BindView(R.id.order_time)
    TextView order_time;

    @BindView(R.id.ok)
    View ok;
    @BindView(R.id.change)
    View change;
    @BindView(R.id.cancel)
    View cancel;
    @BindView(R.id.button_group)
    View button_group;

    @BindView(R.id.parent)
    View parent;

    private OnChildItemClickLinstener onChildItemClickLinstener;

    public UserAppointmentHolder(View itemView, OnChildItemClickLinstener onChildItemClickLinstener) {
        super(itemView);
        ok.setOnClickListener(this);
        change.setOnClickListener(this);
        cancel.setOnClickListener(this);
        this.onChildItemClickLinstener = onChildItemClickLinstener;
    }

    @Override
    public void setData(OrderProjectDetailBean order, int position) {

        if (position == 0) {
            order_id.setText("编号");
            order_phone.setText("手机");
            order_project.setText("项目");
            order_status.setText("状态");
            order_time.setText("时间");
            button_group.setVisibility(View.INVISIBLE);
            parent.setBackgroundColor(Color.parseColor("#E4E4E4"));
        } else {
            parent.setBackgroundColor(Color.parseColor("#FFFFFF"));
            button_group.setVisibility(View.VISIBLE);
            order_id.setText(order.getReservationId());
            order_phone.setText(order.getMember().getMobile());
            order_time.setText(order.getCreateDate());
            order_status.setText(order.getStatusDesc());
            order_project.setText(order.getGoods().getName());
        }
    }

    @Override
    public void onClick(View view) {
        if (null != onChildItemClickLinstener) {
            switch (view.getId()) {
                case R.id.ok:
                    onChildItemClickLinstener.onChildItemClick(view, OK, getAdapterPosition());
                    return;
                case R.id.change:
                    onChildItemClickLinstener.onChildItemClick(view, CHANGE_APPOINTMENT, getAdapterPosition());
                    return;
                case R.id.cancel:
                    onChildItemClickLinstener.onChildItemClick(view, CANCEL, getAdapterPosition());
                    return;
            }
        }
        super.onClick(view);
    }

    public interface OnChildItemClickLinstener {
        void onChildItemClick(View v, cn.ehanmy.hospital.mvp.ui.adapter.ViewName viewname, int position);
    }

    public enum ViewName {
        CANCEL,
        OK,
        CHANGE_APPOINTMENT
    }

    @Override
    protected void onRelease() {
        this.order_id = null;
        this.order_phone = null;
        this.order_project = null;
        this.order_status = null;
        this.order_time = null;
        this.ok = null;
        this.change = null;
        this.cancel = null;
        this.button_group = null;
    }
}
