package me.jessyan.mvparms.demo.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.mvparms.demo.di.module.ShopAppointmentInfoModule;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.mvparms.demo.mvp.ui.activity.ShopAppointmentInfoActivity;

@ActivityScope
@Component(modules = ShopAppointmentInfoModule.class, dependencies = AppComponent.class)
public interface ShopAppointmentInfoComponent {
    void inject(ShopAppointmentInfoActivity activity);
}