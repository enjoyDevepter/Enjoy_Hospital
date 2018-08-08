package me.jessyan.mvparms.demo.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.mvparms.demo.di.module.BuyCenterModule;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.mvparms.demo.mvp.ui.activity.BuyCenterActivity;

@ActivityScope
@Component(modules = BuyCenterModule.class, dependencies = AppComponent.class)
public interface BuyCenterComponent {
    void inject(BuyCenterActivity activity);
}