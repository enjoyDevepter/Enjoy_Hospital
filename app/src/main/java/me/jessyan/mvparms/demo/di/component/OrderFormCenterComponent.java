package me.jessyan.mvparms.demo.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.mvparms.demo.di.module.OrderFormCenterModule;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.mvparms.demo.mvp.ui.activity.OrderFormCenterActivity;

@ActivityScope
@Component(modules = OrderFormCenterModule.class, dependencies = AppComponent.class)
public interface OrderFormCenterComponent {
    void inject(OrderFormCenterActivity activity);
}