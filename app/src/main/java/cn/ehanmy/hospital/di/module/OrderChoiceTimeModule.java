package cn.ehanmy.hospital.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import cn.ehanmy.hospital.mvp.contract.OrderChoiceTimeContract;
import cn.ehanmy.hospital.mvp.model.OrderChoiceTimeModel;


@Module
public class OrderChoiceTimeModule {
    private OrderChoiceTimeContract.View view;

    /**
     * 构建OrderChoiceTimeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public OrderChoiceTimeModule(OrderChoiceTimeContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    OrderChoiceTimeContract.View provideOrderChoiceTimeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    OrderChoiceTimeContract.Model provideOrderChoiceTimeModel(OrderChoiceTimeModel model) {
        return model;
    }
}