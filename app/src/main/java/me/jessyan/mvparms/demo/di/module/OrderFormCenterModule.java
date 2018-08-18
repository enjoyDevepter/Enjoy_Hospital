package me.jessyan.mvparms.demo.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

import me.jessyan.mvparms.demo.mvp.contract.OrderFormCenterContract;
import me.jessyan.mvparms.demo.mvp.model.OrderFormCenterModel;
import me.jessyan.mvparms.demo.mvp.model.entity.goods_list.GoodsListBean;
import me.jessyan.mvparms.demo.mvp.model.entity.order.OrderBean;
import me.jessyan.mvparms.demo.mvp.ui.adapter.GoodsListAdapter;
import me.jessyan.mvparms.demo.mvp.ui.adapter.OrderCenterListAdapter;


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
        return new OrderCenterListAdapter(list);
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