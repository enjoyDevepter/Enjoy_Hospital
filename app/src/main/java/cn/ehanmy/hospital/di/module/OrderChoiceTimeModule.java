package cn.ehanmy.hospital.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ReservationDateBean;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ReservationTimeBean;
import cn.ehanmy.hospital.mvp.ui.adapter.DateAdapter;
import cn.ehanmy.hospital.mvp.ui.adapter.TimeAdapter;
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



    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getActivity(), LinearLayoutManager.VERTICAL, false);
    }

    @ActivityScope
    @Provides
    List<ReservationDateBean> provideAppointmentList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    DateAdapter provideDateAdapter(List<ReservationDateBean> list) {
        return new DateAdapter(list);
    }

    @ActivityScope
    @Provides
    TimeAdapter provideTimeAdapter(List<ReservationTimeBean> list) {
        return new TimeAdapter(list);
    }

    @ActivityScope
    @Provides
    List<ReservationTimeBean> provideAppointmenTimetList() {
        return new ArrayList<>();
    }

}