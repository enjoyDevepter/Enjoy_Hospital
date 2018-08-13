package me.jessyan.mvparms.demo.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import me.jessyan.mvparms.demo.mvp.contract.CommitOrderContract;
import me.jessyan.mvparms.demo.mvp.model.CommitOrderModel;


@Module
public class CommitOrderModule {
    private CommitOrderContract.View view;

    /**
     * 构建CommitOrderModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public CommitOrderModule(CommitOrderContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    CommitOrderContract.View provideCommitOrderView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    CommitOrderContract.Model provideCommitOrderModel(CommitOrderModel model) {
        return model;
    }
}