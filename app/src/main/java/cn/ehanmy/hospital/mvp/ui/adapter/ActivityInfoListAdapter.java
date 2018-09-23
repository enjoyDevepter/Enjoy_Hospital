package cn.ehanmy.hospital.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.activity.ActivityInfoBean;
import cn.ehanmy.hospital.mvp.ui.holder.ActivityInfoListHolder;

public class ActivityInfoListAdapter extends DefaultAdapter<ActivityInfoBean> {
    public ActivityInfoListAdapter(List<ActivityInfoBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<ActivityInfoBean> getHolder(View v, int viewType) {
        return new ActivityInfoListHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.activity_info_list_item;
    }
}
