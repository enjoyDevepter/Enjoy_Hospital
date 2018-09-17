package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.integration.cache.Cache;
import com.jess.arms.utils.ArmsUtils;
import com.paginate.Paginate;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindColor;
import butterknife.BindDrawable;
import butterknife.BindView;
import cn.ehanmy.hospital.di.component.DaggerGoodsListComponent;
import cn.ehanmy.hospital.di.module.GoodsListModule;
import cn.ehanmy.hospital.mvp.contract.GoodsListContract;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.Category;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsListBean;
import cn.ehanmy.hospital.mvp.presenter.GoodsListPresenter;

import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.ui.adapter.GoodsFilterSecondAdapter;
import cn.ehanmy.hospital.mvp.ui.adapter.GoodsFilterThirdAdapter;
import cn.ehanmy.hospital.mvp.ui.adapter.GoodsListAdapter;
import cn.ehanmy.hospital.mvp.ui.widget.SpacesItemDecoration;


import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.jess.arms.utils.ArmsUtils.getContext;
import static com.jess.arms.utils.Preconditions.checkNotNull;

/**
 * 商品列表页面
 */
public class GoodsListActivity extends BaseActivity<GoodsListPresenter> implements GoodsListContract.View, View.OnClickListener, DefaultAdapter.OnRecyclerViewItemClickListener, TabLayout.OnTabSelectedListener, SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.title_Layout)
    View title;
    @BindView(R.id.goodsList)
    RecyclerView mRecyclerView;
    @BindView(R.id.type_layout)
    View typeV;
    @BindView(R.id.type)
    TextView typeTV;
    @BindView(R.id.type_status)
    View typeStatusV;
    @BindView(R.id.sale_layout)
    View saleV;
    @BindView(R.id.sale)
    TextView saleTV;
    @BindView(R.id.sale_status)
    View saleStatusV;
    @BindView(R.id.price_layout)
    View priceV;
    @BindView(R.id.price)
    TextView priceTV;
    @BindView(R.id.price_status)
    View priceStautsV;

    @BindView(R.id.secondCategory)
    RecyclerView secondFilterRV;
    @BindView(R.id.thirdCategory)
    RecyclerView thirdFilterRV;
    @BindView(R.id.filter_layout)
    View filterV;
    @BindView(R.id.mask)
    View maskV;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RecyclerView.Adapter mAdapter;
    @Inject
    GoodsFilterSecondAdapter secondAdapter;
    GoodsFilterThirdAdapter thirdAdapter;

    private List<Category> thirdCategoryList;

    @BindDrawable(R.mipmap.arrow_up)
    Drawable asceD;
    @BindDrawable(R.mipmap.arrow_down)
    Drawable descD;
    @BindColor(R.color.choice)
    int choiceColor;

    private Paginate mPaginate;
    private boolean isLoadingMore;
    private boolean hasLoadedAllItems;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerGoodsListComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .goodsListModule(new GoodsListModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_goods_list; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title, this, "下单中心");
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
        mRecyclerView.setAdapter(mAdapter);
        thirdFilterRV.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//        ((GoodsListAdapter) mAdapter).setOnItemClickListener(this);

        typeV.setOnClickListener(this);
        saleV.setOnClickListener(this);
        priceV.setOnClickListener(this);
        maskV.setOnClickListener(this);
        swipeRefreshLayout.setOnRefreshListener(this);
        ArmsUtils.configRecyclerView(mRecyclerView, mLayoutManager);
        ArmsUtils.configRecyclerView(secondFilterRV, new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        secondFilterRV.setAdapter(secondAdapter);
        secondAdapter.setOnItemClickListener(this);
        mPresenter.getCategory();
    }

    @Override
    public void showLoading() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void hideLoading() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showMessage(@NonNull String message) {
        checkNotNull(message);
        ArmsUtils.snackbarText(message);
    }

    @Override
    public void launchActivity(@NonNull Intent intent) {
        checkNotNull(intent);
        ArmsUtils.startActivity(intent);
    }

    @Override
    public void killMyself() {
        finish();
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void refreshNaviTitle(List<Category> categories) {
    }

    private void showFilter(boolean show) {
        typeV.setSelected(show);
        if (show && secondAdapter.getInfos().size() > 0) {
            thirdCategoryList = new ArrayList<>();
            if (currentSecentIndex != 0) {
                thirdCategoryList.addAll(secondAdapter.getInfos().get(currentSecentIndex).getGoodsCategoryList());
            }
            thirdAdapter = new GoodsFilterThirdAdapter(thirdCategoryList);
            ArmsUtils.configRecyclerView(thirdFilterRV, new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            List<Category> grands = thirdAdapter.getInfos();
            for (int i = 0; i < grands.size(); i++) {
                grands.get(i).setChoice(i == currentThirdIndex ? true : false);
            }
            secondAdapter.notifyDataSetChanged();
            thirdAdapter.setOnItemClickListener(this);
            List<Category> childs = secondAdapter.getInfos();
            for (int i = 0; i < childs.size(); i++) {
                childs.get(i).setChoice(i == currentSecentIndex ? true : false);
            }
            thirdFilterRV.setAdapter(thirdAdapter);
            filterV.setVisibility(VISIBLE);
            filterV.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.menu_in));
            maskV.setVisibility(VISIBLE);
            maskV.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.mask_in));
        } else {
            filterV.setVisibility(GONE);
            filterV.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.menu_out));
            maskV.setVisibility(GONE);
            maskV.setAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.mask_out));
        }
    }

    /**
     * 开始加载更多
     */
    @Override
    public void startLoadMore() {
        isLoadingMore = true;
    }

    /**
     * 结束加载更多
     */
    @Override
    public void endLoadMore() {
        isLoadingMore = false;
    }

    @Override
    public void setLoadedAllItems(boolean has) {
        this.hasLoadedAllItems = has;
    }


    /**
     * 初始化Paginate,用于加载更多
     */
    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    mPresenter.getGoodsList(false);
                }

                @Override
                public boolean isLoading() {
                    return isLoadingMore;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return hasLoadedAllItems;
                }
            };

            mPaginate = Paginate.with(mRecyclerView, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.mask:
                showFilter(false);
                break;
            case R.id.type_layout:
                typeV.setSelected(!typeV.isSelected());
                showFilter(typeV.isSelected());
//                typeTV.setTextColor(choiceColor);
//                typeStatusV.setBackground(typeV.isSelected() ? asceD : descD);
                break;
            case R.id.sale_layout:
                if(typeV.isSelected()){
                    showFilter(false);
                    return;
                }
                saleV.setSelected(!saleV.isSelected());
                priceV.setSelected(false);
                provideCache().put("orderByField", "sales");
                provideCache().put("orderByAsc", saleTV.isSelected());
                saleTV.setTextColor(saleV.isSelected() ? choiceColor : Color.BLACK);
                priceTV.setTextColor(Color.BLACK);
                saleStatusV.setBackground(saleV.isSelected() ? asceD : descD);
                priceStautsV.setBackground(descD);
                mPresenter.getGoodsList(true);
                showFilter(false);
                break;
            case R.id.price_layout:
                if(typeV.isSelected()){
                    showFilter(false);
                    return;
                }
                priceV.setSelected(!priceV.isSelected());
                saleV.setSelected(false);
                provideCache().put("orderByField", "salesPrice");
                provideCache().put("orderByAsc", priceV.isSelected());
                saleTV.setTextColor(Color.BLACK);
                priceTV.setTextColor(priceV.isSelected() ? choiceColor : Color.BLACK);
                priceStautsV.setBackground(priceV.isSelected() ? asceD : descD);
                saleStatusV.setBackground(descD);
                showFilter(false);
                mPresenter.getGoodsList(true);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.mPaginate = null;
        DefaultAdapter.releaseAllHolder(mRecyclerView);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        DefaultAdapter.releaseAllHolder(secondFilterRV);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        DefaultAdapter.releaseAllHolder(thirdFilterRV);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
    }

    @Override
    public Cache getCache() {
        return provideCache();
    }

    private int currentSecentIndex = 0;
    private int currentThirdIndex = 0;
    private int cacheSecendIndex = 0;

    @Override
    public void onItemClick(View view, int viewType, Object data, int position) {
        // 如何区分
        switch (viewType) {
            case R.layout.goods_filter_second_item:
                List<Category> childs = secondAdapter.getInfos();
                for (int i = 0; i < childs.size(); i++) {
                    childs.get(i).setChoice(i == position ? true : false);
                }
                cacheSecendIndex = position;
                secondAdapter.notifyDataSetChanged();
                if (position == 0) {
                    currentThirdIndex = -1;
                    provideCache().put("secondCategoryId", null);
                    provideCache().put("categoryId", null);
                    showFilter(false);
                    typeTV.setText("全部商品");
                    mPresenter.getGoodsList(true);
                    for(Category c1 : secondAdapter.getInfos()){
                        if(c1 == null || c1.getGoodsCategoryList() == null){
                            continue;
                        }
                        for(Category c2 : c1.getGoodsCategoryList()){
                            c2.setChoice(false);
                        }
                    }
                    return;
                }
                thirdCategoryList.clear();
                thirdCategoryList.addAll(childs.get(position).getGoodsCategoryList());
                thirdAdapter.notifyDataSetChanged();
                break;
            case R.layout.goods_filter_third_item:
                currentThirdIndex = position;
                currentSecentIndex = cacheSecendIndex;
                for(Category c1 : secondAdapter.getInfos()){
                    if(c1 == null || c1.getGoodsCategoryList() == null){
                        continue;
                    }
                    for(Category c2 : c1.getGoodsCategoryList()){
                        c2.setChoice(false);
                    }
                }
                List<Category> grands = thirdAdapter.getInfos();
                for (int i = 0; i < grands.size(); i++) {
                    grands.get(i).setChoice(i == position ? true : false);
                }
                thirdAdapter.notifyDataSetChanged();
                typeTV.setTextColor(choiceColor);
                typeTV.setText(grands.get(position).getName());
                provideCache().put("categoryId", grands.get(position).getCategoryId());
                provideCache().put("secondCategoryId", secondAdapter.getInfos().get(currentSecentIndex).getCategoryId());
                showFilter(false);
                mPresenter.getGoodsList(true);
                break;
        }
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        mRecyclerView.setAdapter(mAdapter);
        mPresenter.getGoodsList(true);
        initPaginate();
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }


    @Override
    public void onRefresh() {
        mPresenter.getGoodsList(true);
    }
}
