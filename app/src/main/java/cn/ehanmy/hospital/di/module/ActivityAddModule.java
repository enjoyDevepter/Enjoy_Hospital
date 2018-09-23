package cn.ehanmy.hospital.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import cn.ehanmy.hospital.mvp.contract.ActivityAddContract;
import cn.ehanmy.hospital.mvp.model.ActivityAddModel;


@Module
public class ActivityAddModule {
    private ActivityAddContract.View view;

    /**
     * 构建ActivityAddModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public ActivityAddModule(ActivityAddContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    ActivityAddContract.View provideActivityAddView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    ActivityAddContract.Model provideActivityAddModel(ActivityAddModel model) {
        return model;
    }
}