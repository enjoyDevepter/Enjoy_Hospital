package cn.ehanmy.hospital.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

import cn.ehanmy.hospital.mvp.contract.OrderFormCenterContract;
import cn.ehanmy.hospital.mvp.model.OrderFormCenterModel;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderBean;
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
        return orderCenterListAdapter;
    }


    @ActivityScope
    @Provides
    List<OrderBean> provideOrderBeanList() {
        ArrayList<OrderBean> orderBeans = new ArrayList<>();
        orderBeans.add(new OrderBean());
        return orderBeans;
    }

    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getActivity(), LinearLayoutManager.VERTICAL, false);
    }
}