package me.jessyan.mvparms.demo.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import me.jessyan.mvparms.demo.mvp.contract.ShopAppointmentInfoContract;
import me.jessyan.mvparms.demo.mvp.model.ShopAppointmentInfoModel;


@Module
public class ShopAppointmentInfoModule {
    private ShopAppointmentInfoContract.View view;

    /**
     * 构建ShopAppointmentInfoModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ShopAppointmentInfoModule(ShopAppointmentInfoContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ShopAppointmentInfoContract.View provideShopAppointmentInfoView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ShopAppointmentInfoContract.Model provideShopAppointmentInfoModel(ShopAppointmentInfoModel model) {
        return model;
    }
}