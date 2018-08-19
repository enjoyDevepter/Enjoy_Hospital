package cn.ehanmy.hospital.di.module;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jess.arms.di.scope.ActivityScope;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.List;

import cn.ehanmy.hospital.mvp.ui.activity.OrderFormCenterActivity;
import cn.ehanmy.hospital.mvp.ui.activity.OrderInfoActivity;
import cn.ehanmy.hospital.mvp.ui.adapter.OnChildItemClickLinstener;
import cn.ehanmy.hospital.mvp.ui.adapter.ViewName;
import dagger.Module;
import dagger.Provides;

import cn.ehanmy.hospital.mvp.contract.OrderFormCenterContract;
import cn.ehanmy.hospital.mvp.model.OrderFormCenterModel;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsListBean;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderBean;
import cn.ehanmy.hospital.mvp.ui.adapter.GoodsListAdapter;
import cn.ehanmy.hospital.mvp.ui.adapter.OrderCenterListAdapter;


@Module
public class OrderFormCenterModule {
    private OrderFormCenterContract.View view;

    /**
     * 构建OrderFormCenterModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public OrderFormCenterModule(OrderFormCenterContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    OrderFormCenterContract.View provideOrderFormCenterView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    OrderFormCenterContract.Model provideOrderFormCenterModel(OrderFormCenterModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    RecyclerView.Adapter provideStoreAdapter(List<OrderBean> list) {
        OrderCenterListAdapter orderCenterListAdapter = new OrderCenterListAdapter(list);
        orderCenterListAdapter.setOnChildItemClickLinstener(new OnChildItemClickLinstener() {
            @Override
            public void onChildItemClick(View v, ViewName viewname, int position) {
                if(position == 0){
                    return;
                }
                switch (viewname){
                    case DETAIL:
                        Intent intent = new Intent(ArmsUtils.getContext(),OrderInfoActivity.class);
                        intent.putExtra(OrderInfoActivity.KEY_FOR_DATA,orderCenterListAdapter.getItem(position));
                        ArmsUtils.startActivity(intent);
                        break;
                    case PAY:
                        break;
                }
            }
        });
        return orderCenterListAdapter;
    }


    @ActivityScope
    @Provides
    List<OrderBean> provideOrderBeanList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getActivity(), LinearLayoutManager.VERTICAL, false);
    }
}