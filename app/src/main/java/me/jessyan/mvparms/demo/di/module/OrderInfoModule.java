package me.jessyan.mvparms.demo.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import me.jessyan.mvparms.demo.mvp.contract.OrderInfoContract;
import me.jessyan.mvparms.demo.mvp.model.OrderInfoModel;


@Module
public class OrderInfoModule {
    private OrderInfoContract.View view;

    /**
     * 构建OrderInfoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public OrderInfoModule(OrderInfoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    OrderInfoContract.View provideOrderInfoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    OrderInfoContract.Model provideOrderInfoModel(OrderInfoModel model) {
        return model;
    }
}