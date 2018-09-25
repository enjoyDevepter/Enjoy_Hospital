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

import javax.inject.Inject;

import butterknife.BindView;
import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.di.component.DaggerUserAppointmentComponent;
import cn.ehanmy.hospital.di.module.UserAppointmentModule;
import cn.ehanmy.hospital.mvp.contract.UserAppointmentContract;
import cn.ehanmy.hospital.mvp.model.UserAppointmentModel;
import cn.ehanmy.hospital.mvp.presenter.UserAppointmentPresenter;
import cn.ehanmy.hospital.mvp.ui.adapter.UserAppointmentAdapter;
import cn.ehanmy.hospital.mvp.ui.holder.UserAppointmentHolder;
import cn.ehanmy.hospital.mvp.ui.widget.CustomProgressDailog;

import static com.jess.arms.utils.Preconditions.checkNotNull;


public class UserAppointmentActivity extends BaseActivity<UserAppointmentPresenter> implements UserAppointmentContract.View {


    @BindView(R.id.title_Layout)
    View title_Layout;
    @BindView(R.id.search_layout)
    View search_layout;

    @BindView(R.id.new_appointment)
    TextView new_appointment;
    @BindView(R.id.confirmed)
    TextView confirmed;
    @BindView(R.id.over)
    TextView over;
    @BindView(R.id.all)
    TextView all;
    @BindView(R.id.code)
    View code;
    @BindView(R.id.search_btn)
    View search;
    @BindView(R.id.clear_btn)
    View clear;
    @BindView(R.id.search_key)
    EditText searchKey;
    @Inject
    RecyclerView.LayoutManager mLayoutManager;
    @Inject
    UserAppointmentAdapter mAdapter;
    @BindView(R.id.contentList)
    RecyclerView contentList;
    @BindView(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;
    private TextView currTextView;
    private String currType = UserAppointmentModel.SEARCH_TYPE_NEW;
    private int normalColor = Color.parseColor("#333333");
    private int currColor = Color.parseColor("#3DBFE8");
    private Paginate mPaginate;
    private boolean isLoadingMore;

    private CustomProgressDailog progressDailog;
    private boolean isEnd;
    private View.OnClickListener onTypeClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (v.getId() == currTextView.getId()) {
                return;
            }
            currTextView.setTextColor(normalColor);
            TextView newText = null;
            switch (v.getId()) {
                case R.id.new_appointment:
                    newText = new_appointment;
                    currType = UserAppointmentModel.SEARCH_TYPE_NEW;
                    break;
                case R.id.over:
                    currType = UserAppointmentModel.SEARCH_TYPE_OVER;
                    newText = over;
                    break;
                case R.id.confirmed:
                    newText = confirmed;
                    currType = UserAppointmentModel.SEARCH_TYPE_CONFIRMED;
                    break;
                case R.id.all:
                    currType = UserAppointmentModel.SEARCH_TYPE_ALL;
                    newText = all;
                    break;
            }

            if (newText == null) {
                return;
            }

            currTextView = newText;
            currTextView.setTextColor(currColor);
            mPresenter.requestOrderList(currType);
        }
    };
    private View.OnClickListener onSearchClickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
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

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerUserAppointmentComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .userAppointmentModule(new UserAppointmentModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_user_appointment; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title_Layout,this,"用户预约");
        currTextView = new_appointment;
        new_appointment.setTextColor(currColor);

        new_appointment.setOnClickListener(onTypeClickListener);
        over.setOnClickListener(onTypeClickListener);
        all.setOnClickListener(onTypeClickListener);
        confirmed.setOnClickListener(onTypeClickListener);

        code.setVisibility(View.GONE);
        search.setOnClickListener(onSearchClickListener);
        clear.setOnClickListener(onSearchClickListener);
        ArmsUtils.configRecyclerView(contentList, mLayoutManager);
        mAdapter.setOnChildItemClickLinstener(new UserAppointmentHolder.OnChildItemClickLinstener() {
            @Override
            public void onChildItemClick(View v, UserAppointmentHolder.ViewName viewname, int position) {
                switch (viewname){
                    case CHANGE_APPOINTMENT:
                        break;
                    case OK:
                        mPresenter.confirmAppointment(mAdapter.getItem(position).getReservationId());
                        break;
                    case CANCEL:
                        break;
                }
            }
        });
        contentList.setAdapter(mAdapter);
        initPaginate();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

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
    public void setEnd(boolean isEnd) {
        this.isEnd = isEnd;
    }

    public Activity getActivity(){
        return this;
    }

    private void doSearch(){
        String s = searchKey.getText().toString();
        if(TextUtils.isEmpty(s)){
            showMessage("请输入搜索关键字后重试");
            return;
        }
    }

    @Override
    protected void onDestroy() {
        DefaultAdapter.releaseAllHolder(contentList);//super.onDestroy()之后会unbind,所有view被置为null,所以必须在之前调用
        super.onDestroy();
        this.mPaginate = null;
    }

//    public void updateList(List<UserAppointment> userAppointments){
//        UserAppointmentListAdapter adapter = new UserAppointmentListAdapter(userAppointments);
//        adapter.setOnChildItemClickLinstener(new OnChildItemClickLinstener() {
//            @Override
//            public void onChildItemClick(View v, ViewName viewname, int position) {
//                if(position == 0){
//                    return;
//                }
//                switch (viewname){
//                    case OK:
//                        break;
//                    case CHANGE_APPOINTMENT:
//                        break;
//                    case CANCEL:
//                        break;
//                }
//            }
//        });
//        contentList.setAdapter(adapter);
//    }

}
