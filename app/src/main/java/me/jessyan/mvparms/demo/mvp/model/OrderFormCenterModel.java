package me.jessyan.mvparms.demo.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import me.jessyan.mvparms.demo.mvp.contract.OrderFormCenterContract;
import me.jessyan.mvparms.demo.mvp.model.entity.Order;


@ActivityScope
public class OrderFormCenterModel extends BaseModel implements OrderFormCenterContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    public static final int SEARCH_TYPE_UNPAID = 1;
    public static final int SEARCH_TYPE_SECEND = 2;
    public static final int SEARCH_TYPE_OK = 3;
    public static final int SEARCH_TYPE_ALL = 4;

    @Inject
    public OrderFormCenterModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    public List<Order> doSearch(String searchKey,int SearchType){
        // 啥也别说，先造点假数据
        List<Order> list = new ArrayList<>();
        for(int i = 0;i<10;i++){
            list.add(new Order(i+"","1234567890","12345","有关于"+searchKey+"的一些项目","未支付","20180808"));
        }
        return list;
    }
}