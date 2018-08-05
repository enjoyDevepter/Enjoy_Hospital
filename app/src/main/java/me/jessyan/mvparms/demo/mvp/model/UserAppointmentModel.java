package me.jessyan.mvparms.demo.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.mvparms.demo.mvp.contract.UserAppointmentContract;
import me.jessyan.mvparms.demo.mvp.model.entity.ShopAppointment;
import me.jessyan.mvparms.demo.mvp.model.entity.UserAppointment;


@ActivityScope
public class UserAppointmentModel extends BaseModel implements UserAppointmentContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    public static final int SEARCH_TYPE_NEW = 1;
    public static final int SEARCH_TYPE_CONFIRMED = 2;
    public static final int SEARCH_TYPE_OVER = 3;
    public static final int SEARCH_TYPE_ALL = 4;

    @Inject
    public UserAppointmentModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    public List<UserAppointment> doSearch(String searchKey, int SearchType){
        // 啥也别说，先造点假数据
        List<UserAppointment> list = new ArrayList<>();
        for(int i = 0;i<10;i++){
            list.add(new UserAppointment(i+"","1234567890","有关于"+searchKey+"的一些项目","未关联","20180808"));
        }
        return list;
    }
}