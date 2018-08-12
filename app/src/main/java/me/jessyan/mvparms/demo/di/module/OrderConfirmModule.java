package me.jessyan.mvparms.demo.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import me.jessyan.mvparms.demo.mvp.contract.OrderConfirmContract;
import me.jessyan.mvparms.demo.mvp.model.OrderConfirmModel;


@Module
public class OrderConfirmModule {
    private OrderConfirmContract.View view;

    /**
     * 构建OrderConfirmModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public OrderConfirmModule(OrderConfirmContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    OrderConfirmContract.View provideOrderConfirmView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    OrderConfirmContract.Model provideOrderConfirmModel(OrderConfirmModel model) {
        return model;
    }
}