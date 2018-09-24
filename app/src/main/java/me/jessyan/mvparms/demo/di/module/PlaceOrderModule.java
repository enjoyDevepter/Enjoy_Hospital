package me.jessyan.mvparms.demo.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;
import me.jessyan.mvparms.demo.mvp.contract.PlaceOrderContract;
import me.jessyan.mvparms.demo.mvp.model.PlaceOrderModel;


@Module
public class PlaceOrderModule {
    private PlaceOrderContract.View view;

    /**
     * 构建PlaceOrderModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public PlaceOrderModule(PlaceOrderContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    PlaceOrderContract.View providePlaceOrderView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    PlaceOrderContract.Model providePlaceOrderModel(PlaceOrderModel model) {
        return model;
    }
}