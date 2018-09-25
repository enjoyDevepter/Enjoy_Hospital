/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.ehanmy.hospital.mvp.ui.adapter;

import android.view.View;

import com.jess.arms.base.BaseHolder;
import com.jess.arms.base.DefaultAdapter;

import java.util.List;

import cn.ehanmy.hospital.R;
import cn.ehanmy.hospital.mvp.model.entity.Order;
import cn.ehanmy.hospital.mvp.ui.holder.OrderCenterListItemHolder;
import cn.ehanmy.hospital.mvp.ui.holder.RelatedListItemHolder;

/**
 * ================================================
 * 展示 {@link DefaultAdapter} 的用法
 * <p>
 * Created by JessYan on 09/04/2016 12:57
 * <a href="mailto:jess.yan.effort@gmail.com">Contact me</a>
 * <a href="https://github.com/JessYanCoding">Follow me</a>
 * ================================================
 */
public class RelatedListAdapter extends DefaultAdapter<Order> {

    public void setOnChildItemClickLinstener(RelatedListItemHolder.OnChildItemClickLinstener onChildItemClickLinstener) {
        this.onChildItemClickLinstener = onChildItemClickLinstener;
        setOnItemClickListener(new OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int viewType, Object data, int position) {

            }
        });
    }

    private RelatedListItemHolder.OnChildItemClickLinstener onChildItemClickLinstener;

    public RelatedListAdapter(List<Order> ordres) {
        super(ordres);
        ordres.add(0,new Order());
    }

    @Override
    public BaseHolder<Order> getHolder(View v, int viewType) {
        return new RelatedListItemHolder(v, new RelatedListItemHolder.OnChildItemClickLinstener() {
            @Override
            public void onChildItemClick(View v, RelatedListItemHolder.ViewName viewname, int position) {
                if (onChildItemClickLinstener != null) {
                    onChildItemClickLinstener.onChildItemClick(v, viewname, position);
                }
            }
        });
    }

    @Override
    public int getLayoutId(int viewType) {
        return R.layout.related_list_item;
    }

    public RelatedListItemHolder.OnChildItemClickLinstener getOnChildItemClickLinstener() {
        return onChildItemClickLinstener;
    }


}
