package me.jessyan.mvparms.demo.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.mvparms.demo.di.module.UserAppointmentModule;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.mvparms.demo.mvp.ui.activity.UserAppointmentActivity;

@ActivityScope
@Component(modules = UserAppointmentModule.class, dependencies = AppComponent.class)
public interface UserAppointmentComponent {
    void inject(UserAppointmentActivity activity);
}