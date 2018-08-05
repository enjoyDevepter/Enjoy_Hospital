package me.jessyan.mvparms.demo.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.mvparms.demo.mvp.contract.ShopAppointmentContract;
import me.jessyan.mvparms.demo.mvp.model.entity.Order;
import me.jessyan.mvparms.demo.mvp.model.entity.ShopAppointment;


@ActivityScope
public class ShopAppointmentModel extends BaseModel implements ShopAppointmentContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    public static final int SEARCH_TYPE_APPOINTMENT = 1;
    public static final int SEARCH_TYPE_OVER = 2;
    public static final int SEARCH_TYPE_CANCEL = 3;
    public static final int SEARCH_TYPE_ALL = 4;

    @Inject
    public ShopAppointmentModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    public List<ShopAppointment> doSearch(String searchKey, int SearchType){
        // 啥也别说，先造点假数据
        List<ShopAppointment> list = new ArrayList<>();
        for(int i = 0;i<10;i++){
            list.add(new ShopAppointment(i+"","1234567890","未关联","有关于"+searchKey+"的一些项目","未支付","20180808"));
        }
        return list;
    }
}