package me.jessyan.mvparms.demo.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.mvparms.demo.di.module.OrderInfoModule;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.mvparms.demo.mvp.ui.activity.OrderInfoActivity;

@ActivityScope
@Component(modules = OrderInfoModule.class, dependencies = AppComponent.class)
public interface OrderInfoComponent {
    void inject(OrderInfoActivity activity);
}