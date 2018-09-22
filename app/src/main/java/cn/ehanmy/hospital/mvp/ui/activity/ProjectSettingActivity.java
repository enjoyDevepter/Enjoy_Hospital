package cn.ehanmy.hospital.mvp.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jess.arms.base.BaseActivity;
import com.jess.arms.di.component.AppComponent;
import com.jess.arms.utils.ArmsUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import cn.ehanmy.hospital.di.component.DaggerProjectSettingComponent;
import cn.ehanmy.hospital.di.module.ProjectSettingModule;
import cn.ehanmy.hospital.mvp.contract.ProjectSettingContract;
import cn.ehanmy.hospital.mvp.model.entity.goods_list.Category;
import cn.ehanmy.hospital.mvp.presenter.ProjectSettingPresenter;

import cn.ehanmy.hospital.R;


import static com.jess.arms.utils.Preconditions.checkNotNull;


public class ProjectSettingActivity extends BaseActivity<ProjectSettingPresenter> implements ProjectSettingContract.View {

    @BindView(R.id.title_Layout)
    View title;

    @BindView(R.id.list1)
    RecyclerView list1;
    @BindView(R.id.list2)
    RecyclerView list2;
    @BindView(R.id.list3)
    RecyclerView list3;

    @Override
    public void setupActivityComponent(@NonNull AppComponent appComponent) {
        DaggerProjectSettingComponent //如找不到该类,请编译一下项目
                .builder()
                .appComponent(appComponent)
                .projectSettingModule(new ProjectSettingModule(this))
                .build()
                .inject(this);
    }

    @Override
    public int initView(@Nullable Bundle savedInstanceState) {
        return R.layout.activity_project_setting; //如果你不需要框架帮你设置 setContentView(id) 需要自行设置,请返回 0
    }

    @Override
    public void initData(@Nullable Bundle savedInstanceState) {
        new TitleUtil(title,this,"项目设置");
        list1.setLayoutManager(new LinearLayoutManager(this));
        list2.setLayoutManager(new LinearLayoutManager(this));
        list3.setLayoutManager(new LinearLayoutManager(this));
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

    private int currentIndex1 = 0;
    // 保存1级->2级页面的选中状态
    private Map<Integer,Integer> selectList1 = new HashMap<>();
    private Map<Integer,List<Integer>> selectList2 = new HashMap<>();

    public void updateCategory(List<Category> categoryList){
        categoryList1.clear();
        categoryList1.addAll(categoryList);
        selectList1.put(currentIndex1,0);
        list1.setAdapter(list1Adapter);
        list2.setAdapter(list2Adapter);
        list3.setAdapter(list3Adapter);
    }


    private class ListHolder extends RecyclerView.ViewHolder{

        TextView content;

        public ListHolder(View itemView) {
            super(itemView);
            content = itemView.findViewById(R.id.content);
        }
    }
    private List<Category> categoryList1 = new ArrayList<>();
    private List1Adapter list1Adapter = new List1Adapter();
    private class List1Adapter extends RecyclerView.Adapter<ListHolder>{

        @NonNull
        @Override
        public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            int layoutId = R.layout.project_setting_normal_item;
            if(viewType == ITEM_TYPE_SELECT){
                layoutId = R.layout.project_setting_selected_item;
            }
            View inflate = LayoutInflater.from(parent.getContext()).inflate(layoutId, null);
            inflate.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ArmsUtils.dip2px(ArmsUtils.getContext(),30)));
            return new ListHolder(inflate);
        }

        @Override
        public void onBindViewHolder(@NonNull ListHolder holder, int position) {
            TextView content = holder.content;
            content.setText(categoryList1.get(position).getName());
            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentIndex1 = position;
                    list1.setAdapter(list1Adapter);
                    list2.setAdapter(list2Adapter);
                    list3.setAdapter(list3Adapter);
                }
            });
        }

        @Override
        public int getItemViewType(int position) {
            return position == currentIndex1 ? ITEM_TYPE_SELECT : ITEM_TYPE_NORMAL;
        }

        @Override
        public int getItemCount() {
            return categoryList1.size();
        }
    }

    private List2Adapter list2Adapter = new List2Adapter();
    private class List2Adapter extends RecyclerView.Adapter<ListHolder>{

        @NonNull
        @Override
        public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            int layoutId = R.layout.project_setting_normal_item;
            if(viewType == ITEM_TYPE_SELECT){
                layoutId = R.layout.project_setting_selected_item;
            }
            View inflate = LayoutInflater.from(parent.getContext()).inflate(layoutId, null);
            inflate.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ArmsUtils.dip2px(ArmsUtils.getContext(),30)));
            return new ListHolder(inflate);
        }

        @Override
        public void onBindViewHolder(@NonNull ListHolder holder, int position) {
            TextView content = holder.content;
            content.setText(getList().get(position).getName());
            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectList1.put(currentIndex1,position);
                    list2.setAdapter(list2Adapter);
                    list3.setAdapter(list3Adapter);
                }
            });
        }

        @Override
        public int getItemViewType(int position) {
            if(!selectList1.containsKey(currentIndex1)){
                selectList1.put(currentIndex1,0);
            }
            Integer integer = selectList1.get(currentIndex1);
            return position == integer ? ITEM_TYPE_SELECT : ITEM_TYPE_NORMAL;
        }

        @Override
        public int getItemCount() {
            return getList().size();
        }

        private List<Category> getList(){
            return categoryList1.get(currentIndex1).getGoodsCategoryList();
        }
    }

    private List3Adapter list3Adapter = new List3Adapter();
    private class List3Adapter extends RecyclerView.Adapter<ListHolder>{

        @NonNull
        @Override
        public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            int layoutId = R.layout.project_setting_normal_item;
            if(viewType == ITEM_TYPE_SELECT){
                layoutId = R.layout.project_setting_choose_item;
            }
            View inflate = LayoutInflater.from(parent.getContext()).inflate(layoutId, null);
            inflate.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ArmsUtils.dip2px(ArmsUtils.getContext(),30)));
            return new ListHolder(inflate);
        }

        @Override
        public void onBindViewHolder(@NonNull ListHolder holder, int position) {
            TextView content = holder.content;
            Integer key = selectList1.get(currentIndex1);
            content.setText(categoryList1.get(currentIndex1).getGoodsCategoryList().get(key).getGoodsCategoryList().get(position).getName());
            content.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!selectList2.containsKey(key)) {
                        selectList2.put(key,new ArrayList<>());
                    }
                    List<Integer> integers = selectList2.get(key);
                    if(integers.contains(position)){
                        integers.remove(Integer.valueOf(position));
                    }else{
                        integers.add(position);
                    }
                    list3.setAdapter(list3Adapter);
                }
            });
        }

        @Override
        public int getItemViewType(int position) {
            Integer key = selectList1.get(currentIndex1);
            if(!selectList2.containsKey(key)){
                return ITEM_TYPE_NORMAL;
            }
            return selectList2.get(key).contains(position) ? ITEM_TYPE_SELECT : ITEM_TYPE_NORMAL;
        }

        @Override
        public int getItemCount() {
            return categoryList1.get(currentIndex1).getGoodsCategoryList().get(get2Index()).getGoodsCategoryList().size();
        }

        private int get2Index(){
            return selectList1.get(currentIndex1);
        }
    }

    private static final int ITEM_TYPE_SELECT = 1;
    private static final int ITEM_TYPE_NORMAL = 0;
}
