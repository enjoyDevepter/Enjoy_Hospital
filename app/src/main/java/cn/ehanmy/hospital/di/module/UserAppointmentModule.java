package cn.ehanmy.hospital.di.module;

import com.jess.arms.di.scope.ActivityScope;

import dagger.Module;
import dagger.Provides;

import cn.ehanmy.hospital.mvp.contract.UserAppointmentContract;
import cn.ehanmy.hospital.mvp.model.UserAppointmentModel;


@Module
public class UserAppointmentModule {
    private UserAppointmentContract.View view;

    /**
     * 构建UserAppointmentModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public UserAppointmentModule(UserAppointmentContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    UserAppointmentContract.View provideUserAppointmentView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    UserAppointmentContract.Model provideUserAppointmentModel(UserAppointmentModel model) {
        return model;
    }
}