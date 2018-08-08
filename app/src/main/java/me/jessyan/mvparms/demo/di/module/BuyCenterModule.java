package me.jessyan.mvparms.demo.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import me.jessyan.mvparms.demo.mvp.contract.BuyCenterContract;
import me.jessyan.mvparms.demo.mvp.model.BuyCenterModel;


@Module
public class BuyCenterModule {
    private BuyCenterContract.View view;

    /**
     * 构建BuyCenterModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public BuyCenterModule(BuyCenterContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    BuyCenterContract.View provideBuyCenterView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    BuyCenterContract.Model provideBuyCenterModel(BuyCenterModel model) {
        return model;
    }
}