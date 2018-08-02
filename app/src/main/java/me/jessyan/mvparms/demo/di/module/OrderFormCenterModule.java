package me.jessyan.mvparms.demo.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import me.jessyan.mvparms.demo.mvp.contract.OrderFormCenterContract;
import me.jessyan.mvparms.demo.mvp.model.OrderFormCenterModel;


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
}