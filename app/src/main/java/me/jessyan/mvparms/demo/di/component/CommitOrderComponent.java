package me.jessyan.mvparms.demo.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.mvparms.demo.di.module.CommitOrderModule;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.mvparms.demo.mvp.ui.activity.CommitOrderActivity;

@ActivityScope
@Component(modules = CommitOrderModule.class, dependencies = AppComponent.class)
public interface CommitOrderComponent {
    void inject(CommitOrderActivity activity);
}