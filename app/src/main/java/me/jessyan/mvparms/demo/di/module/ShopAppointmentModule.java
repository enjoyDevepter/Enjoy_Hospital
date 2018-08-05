package me.jessyan.mvparms.demo.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import me.jessyan.mvparms.demo.mvp.contract.ShopAppointmentContract;
import me.jessyan.mvparms.demo.mvp.model.ShopAppointmentModel;


@Module
public class ShopAppointmentModule {
    private ShopAppointmentContract.View view;

    /**
     * 构建ShopAppointmentModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ShopAppointmentModule(ShopAppointmentContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ShopAppointmentContract.View provideShopAppointmentView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ShopAppointmentContract.Model provideShopAppointmentModel(ShopAppointmentModel model) {
        return model;
    }
}