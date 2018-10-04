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
package cn.ehanmy.hospital.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.OrderProjectDetailBean;
import cn.ehanmy.hospital.mvp.ui.activity.TitleUtil;
import cn.ehanmy.hospital.mvp.ui.holder.UserAppointmentHolder;
import cn.ehanmy.hospital.mvp.ui.holder.UserAppointmentTitleHolder;

/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class UserAppointmentAdapter extends DefaultAdapter<OrderProjectDetailBean> {

    public enum ListType{
        NEW,  // 新预约
        CONFIRM,  // 已确认
        OVER,  // 已完成
        ALL,  // 所有
    }

    public static final int ITEM_TYPE_TITLE = 1;
    public static final int ITEM_TYPE_ITEM = 2;

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? ITEM_TYPE_TITLE : ITEM_TYPE_ITEM;
    }

    public void setOnChildItemClickLinstener(UserAppointmentHolder.OnChildItemClickLinstener onChildItemClickLinstener) {
        this.onChildItemClickLinstener = onChildItemClickLinstener;
        setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {

            }
        });
    }

    private UserAppointmentHolder.OnChildItemClickLinstener onChildItemClickLinstener;

    public UserAppointmentAdapter(List<OrderProjectDetailBean> ordres) {
        super(ordres);
    }

    @Override
    public BaseHolder<OrderProjectDetailBean> getHolder(View v, int viewType) {
        if(viewType == ITEM_TYPE_TITLE){
            return new UserAppointmentTitleHolder(v);
        }

        return new UserAppointmentHolder(v, new UserAppointmentHolder.OnChildItemClickLinstener() {
            @Override
            public void onChildItemClick(View v, UserAppointmentHolder.ViewName viewname, int position) {
                if (onChildItemClickLinstener != null) {
                    onChildItemClickLinstener.onChildItemClick(v, viewname, position);
                }
            }
        });
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.user_appointment_item;
    }

    public UserAppointmentHolder.OnChildItemClickLinstener getOnChildItemClickLinstener() {
        return onChildItemClickLinstener;
    }


}
