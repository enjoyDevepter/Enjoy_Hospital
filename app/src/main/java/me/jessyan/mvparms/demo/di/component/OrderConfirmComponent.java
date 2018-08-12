package me.jessyan.mvparms.demo.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.mvparms.demo.di.module.OrderConfirmModule;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.mvparms.demo.mvp.ui.activity.OrderConfirmActivity;

@ActivityScope
@Component(modules = OrderConfirmModule.class, dependencies = AppComponent.class)
public interface OrderConfirmComponent {
    void inject(OrderConfirmActivity activity);
}