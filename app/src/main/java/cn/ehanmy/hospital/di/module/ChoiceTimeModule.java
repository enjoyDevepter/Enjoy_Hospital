package cn.ehanmy.hospital.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import cn.ehanmy.hospital.mvp.contract.ChoiceTimeContract;
import cn.ehanmy.hospital.mvp.model.ChoiceTimeModel;


@Module
public class ChoiceTimeModule {
    private ChoiceTimeContract.View view;

    /**
     * 构建ChoiceTimeModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ChoiceTimeModule(ChoiceTimeContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ChoiceTimeContract.View provideChoiceTimeView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ChoiceTimeContract.Model provideChoiceTimeModel(ChoiceTimeModel model) {
        return model;
    }
}