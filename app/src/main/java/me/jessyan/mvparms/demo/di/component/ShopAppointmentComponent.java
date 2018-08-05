package me.jessyan.mvparms.demo.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.mvparms.demo.di.module.ShopAppointmentModule;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.mvparms.demo.mvp.ui.activity.ShopAppointmentActivity;

@ActivityScope
@Component(modules = ShopAppointmentModule.class, dependencies = AppComponent.class)
public interface ShopAppointmentComponent {
    void inject(ShopAppointmentActivity activity);
}