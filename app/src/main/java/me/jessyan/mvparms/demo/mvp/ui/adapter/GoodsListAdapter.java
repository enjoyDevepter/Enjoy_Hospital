package me.jessyan.mvparms.demo.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import me.jessyan.mvparms.demo.R;
import me.jessyan.mvparms.demo.mvp.model.entity.goods_list.GoodsListBean;
import me.jessyan.mvparms.demo.mvp.ui.holder.GoodsListHolder;

public class GoodsListAdapter extends DefaultAdapter<GoodsListBean> {
    public GoodsListAdapter(List<GoodsListBean> infos) {
        super(infos);
    }

    @Override
    public BaseHolder<GoodsListBean> getHolder(View v, int viewType) {
        return new GoodsListHolder(v);
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.goods_list_item;
    }
}
