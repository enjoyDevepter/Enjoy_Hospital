package cn.ehanmy.hospital.mvp.model;

import android.app.Application;

import com.google.gson.Gson;
import com.jess.arms.integration.IRepositoryManager;
import com.jess.arms.mvp.BaseModel;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import cn.ehanmy.hospital.mvp.contract.RelatedListContract;
import cn.ehanmy.hospital.mvp.model.entity.Order;


@ActivityScope
public class RelatedListModel extends BaseModel implements RelatedListContract.Model {
    @Inject
    Gson mGson;
    @Inject
    Application mApplication;

    @Inject
    public RelatedListModel(IRepositoryManager repositoryManager) {
        super(repositoryManager);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mGson = null;
        this.mApplication = null;
    }

    public List<Order> requestRelatedList(){
        // 啥也别说，先造点假数据
        List<Order> list = new ArrayList<>();
        for(int i = 0;i<10;i++){
            list.add(new Order(i+"","1234567890","12345","有关于"+" balabala "+"的一些项目","未支付","20180808"));
        }
        return list;
    }
}