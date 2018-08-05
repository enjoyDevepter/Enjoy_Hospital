package me.jessyan.mvparms.demo.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import me.jessyan.mvparms.demo.di.module.RelatedListModule;

import com.jess.arms.di.scope.ActivityScope;

import me.jessyan.mvparms.demo.mvp.ui.activity.RelatedListActivity;

@ActivityScope
@Component(modules = RelatedListModule.class, dependencies = AppComponent.class)
public interface RelatedListComponent {
    void inject(RelatedListActivity activity);
}