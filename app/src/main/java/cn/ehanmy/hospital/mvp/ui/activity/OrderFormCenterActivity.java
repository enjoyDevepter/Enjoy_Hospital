package cn.ehanmy.hospital.mvp.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.base.DefaultAdapter;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;
import com.paginate.Paginate;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.di.component.DaggerOrderFormCenterComponent;
import cn.ehanmy.hospital.di.module.OrderFormCenterModule;
import cn.ehanmy.hospital.mvp.contract.OrderFormCenterContract;
import cn.ehanmy.hospital.mvp.model.OrderFormCenterModel;
import cn.ehanmy.hospital.mvp.model.entity.order.OrderBean;
import cn.ehanmy.hospital.mvp.presenter.OrderFormCenterPresenter;

import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.ui.adapter.OnChildItemClickLinstener;
import cn.ehanmy.hospital.mvp.ui.adapter.OrderCenterListAdapter;
import cn.ehanmy.hospital.mvp.ui.adapter.ViewName;
import cn.ehanmy.hospital.mvp.ui.widget.CustomProgressDailog;


import static com.jess.arms.utils.Preconditions.checkNotNull;

/**订单中心页面*/
public class OrderFormCenterActivity extends BaseActivity<OrderFormCenterPresenter> implements OrderFormCenterContract.View {

    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    RecyclerView.Adapter mAdapter;

    @BindView(R.id.title_Layout)
    View title_Layout;
    @BindView(R.id.search_layout)
    View search_layout;

    @BindView(R.id.code)
    View code;
    @BindView(R.id.search_btn)
    View search;
    @BindView(R.id.clear_btn)
    View clear;
    @BindView(R.id.search_key)
    EditText searchKey;

    @BindView(R.id.unpaid)
    TextView unpaid;
    @BindView(R.id.secend)
    TextView secend;
    @BindView(R.id.over)
    TextView over;
    @BindView(R.id.all)
    TextView all;

    @BindView(R.id.contentList)
    RecyclerView contentList;

    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private Paginate mPaginate;
    private boolean isLoadingMore;
    private CustomProgressDailog progressDailog;

    private void initPaginate() {
        if (mPaginate == null) {
            Paginate.Callbacks callbacks = new Paginate.Callbacks() {
                @Override
                public void onLoadMore() {
                    mPresenter.nextPage();
                }

                @Override
                public boolean isLoading() {
                    return isLoadingMore;
                }

                @Override
                public boolean hasLoadedAllItems() {
                    return isEnd;
                }
            };

            mPaginate = Paginate.with(contentList, callbacks)
                    .setLoadingTriggerThreshold(0)
                    .build();
            mPaginate.setHasMoreDataToLoad(false);
        }
    }

    private int normalColor = Color.parseColor("#333333");
    private int currColor = Color.parseColor("#3DBFE8");

    // 当前选中的textview
    private TextView currentTab;
    private String currentSearchType = OrderFormCenterModel.SEARCH_TYPE_UNPAID;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerOrderFormCenterComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .orderFormCenterModule(new OrderFormCenterModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_order_form_center; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title_Layout,this,"订单中心");
        unpaid.setOnClickListener(onTabClickListener);
        all.setOnClickListener(onTabClickListener);
        secend.setOnClickListener(onTabClickListener);
        over.setOnClickListener(onTabClickListener);
        currentTab = unpaid;
        currentTab.setTextColor(currColor);

        code.setVisibility(View.GONE);
        search.setOnClickListener(onSearchClickListener);
        clear.setOnClickListener(onSearchClickListener);

        OrderCenterListAdapter orderCenterListAdapter = (OrderCenterListAdapter) mAdapter;
        orderCenterListAdapter.setOnChildItemClickLinstener(new OnChildItemClickLinstener() {
            @Override
            public void onChildItemClick(View v, ViewName viewname, int position) {
                if(position == 0){
                    return;
                }
                switch (viewname){
                    case DETAIL:
                        Intent intent = new Intent(OrderFormCenterActivity.this,OrderInfoActivity.class);
                        intent.putExtra(OrderInfoActivity.KEY_FOR_ORDER_ID,orderCenterListAdapter.getItem(position).getOrderId());
                        startActivity(intent);
                        break;
                    case PAY:
                        break;
                }
            }
        });

        ArmsUtils.configRecyclerView(contentList, mLayoutManager);
        contentList.setAdapter(mAdapter);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mPresenter.requestOrderList(currentSearchType);
            }
        });
        initPaginate();
    }


    @Override
    public void showLoading() {
        if(progressDailog == null){
            progressDailog = new CustomProgressDailog(this);
            progressDailog.show();
        }
    }

    @Override
    public void hideLoading() {
        if(progressDailog != null && progressDailog.isShowing()){
            progressDailog.dismiss();
            progressDailog = null;
        }
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

    private void doSearch(){
        String s = searchKey.getText().toString();
        if(TextUtils.isEmpty(s)){
            ArmsUtils.makeText(this,"请输入搜索关键字后重试");
            return;
        }

    }

    public void updateList(List<OrderBean> orderList){
        contentList.setAdapter(mAdapter);
    }

    private View.OnClickListener onSearchClickListener = new View.OnClickListener(){

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.search_btn:
                    doSearch();
                    break;
                case R.id.clear_btn:
                    searchKey.setText("");
                    contentList.setAdapter(null);
                    break;
            }
            hideImm();
        }
    };

    private View.OnClickListener onTabClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(v.getId() == currentTab.getId()){
                return;
            }
            currentTab.setTextColor(normalColor);
            TextView newText = null;
            switch (v.getId()){
                case R.id.unpaid:
                    newText = unpaid;
                    currentSearchType = OrderFormCenterModel.SEARCH_TYPE_UNPAID;
                    break;
                case R.id.all:
                    newText = all;
                    currentSearchType = OrderFormCenterModel.SEARCH_TYPE_ALL;
                    break;
                case R.id.secend:
                    newText = secend;
                    currentSearchType = OrderFormCenterModel.SEARCH_TYPE_SECEND;
                    break;
                case R.id.over:
                    newText = over;
                    currentSearchType = OrderFormCenterModel.SEARCH_TYPE_OK;
                    break;
            }

            if(newText == null){
                return;
            }
            currentTab = newText;
            currentTab.setTextColor(currColor);
            mPresenter.requestOrderList(currentSearchType);
        }
    };

    public Activity getActivity(){
        return this;
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

    private boolean isEnd;

    @Override
    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    @Override
    protected void onDestroy() {
        DefaultAdapter.releaseAllHolder(contentList);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
        this.mPaginate = null;
    }
}
