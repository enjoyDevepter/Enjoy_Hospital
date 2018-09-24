package me.jessyan.mvparms.demo.di.component;

import com.jess.arms.di.component.AppComponent;
import com.jess.arms.di.scope.ActivityScope;

import dagger.Component;
import me.jessyan.mvparms.demo.di.module.PlaceOrderModule;
import me.jessyan.mvparms.demo.mvp.ui.activity.PlaceOrderActivity;

@ActivityScope
@Component(modules = PlaceOrderModule.class, dependencies = AppComponent.class)
public interface PlaceOrderComponent {
    void inject(PlaceOrderActivity activity);
}