package me.jessyan.mvparms.demo.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import me.jessyan.mvparms.demo.mvp.contract.RelatedListContract;
import me.jessyan.mvparms.demo.mvp.model.RelatedListModel;


@Module
public class RelatedListModule {
    private RelatedListContract.View view;

    /**
     * 构建RelatedListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public RelatedListModule(RelatedListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    RelatedListContract.View provideRelatedListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    RelatedListContract.Model provideRelatedListModel(RelatedListModel model) {
        return model;
    }
}