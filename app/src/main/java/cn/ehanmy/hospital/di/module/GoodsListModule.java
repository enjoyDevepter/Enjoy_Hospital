package cn.ehanmy.hospital.di.module;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.jess.arms.di.scope.ActivityScope;

import java.util.ArrayList;
import java.util.List;

import dagger.Module;
import dagger.Provides;

import cn.ehanmy.hospital.mvp.contract.GoodsListContract;
import cn.ehanmy.hospital.mvp.model.GoodsListModel;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsListBean;
import cn.ehanmy.hospital.mvp.ui.adapter.GoodsListAdapter;


@Module
public class GoodsListModule {
    private GoodsListContract.View view;

    /**
     * 构建GoodsListModule时,将View的实现类传进来,这样就可以提供View的实现类给presenter
     *
     * @param view
     */
    public GoodsListModule(GoodsListContract.View view) {
        this.view = view;
    }

    @ActivityScope
    @Provides
    GoodsListContract.View provideGoodsListView() {
        return this.view;
    }

    @ActivityScope
    @Provides
    RecyclerView.LayoutManager provideLayoutManager() {
        return new LinearLayoutManager(view.getActivity(), LinearLayoutManager.VERTICAL, false);
    }

    @ActivityScope
    @Provides
    GoodsListContract.Model provideGoodsListModel(GoodsListModel model) {
        return model;
    }

    @ActivityScope
    @Provides
    List<GoodsListBean> provideStoreList() {
        return new ArrayList<>();
    }

    @ActivityScope
    @Provides
    RecyclerView.Adapter provideStoreAdapter(List<GoodsListBean> list) {
        return new GoodsListAdapter(list);
    }
}