package cn.ehanmy.hospital.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.OrderChoiceTimeContract;
import cn.ehanmy.hospital.mvp.model.api.service.InterfaceService;
import cn.ehanmy.hospital.mvp.model.entity.order.GetOrderAppointmentTimeRequest;
import cn.ehanmy.hospital.mvp.model.entity.order.GetOrderAppointmentTimeResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentTimeRequest;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.GetUserAppointmentTimeResponse;
import io.reactivex.Observable;


@ActivityScope
public class OrderChoiceTimeModel extends BaseModel implements OrderChoiceTimeContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public OrderChoiceTimeModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }


    @Override
    public Observable<GetOrderAppointmentTimeResponse> getOrderAppointmentTime(GetOrderAppointmentTimeRequest request) {
        return mRepositoryManager.obtainRetrofitService(InterfaceService.class)
                .getOrderAppointmentTime(request);
    }

}