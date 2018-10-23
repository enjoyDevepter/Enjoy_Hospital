package cn.ehanmy.hospital.di.component;

import dagger.Component;

import com.jess.arms.di.component.AppComponent;

import cn.ehanmy.hospital.di.module.OrderChoiceTimeModule;

import com.jess.arms.di.scope.ActivityScope;

import cn.ehanmy.hospital.mvp.ui.activity.OrderChoiceTimeActivity;

@ActivityScope
@Component(modules = OrderChoiceTimeModule.class, dependencies = AppComponent.class)
public interface OrderChoiceTimeComponent {
    void inject(OrderChoiceTimeActivity activity);
}