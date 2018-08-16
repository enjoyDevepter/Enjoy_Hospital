package me.jessyan.mvparms.demo.mvp.model.entity.order;

import java.util.List;

import me.jessyan.mvparms.demo.mvp.model.entity.response.BaseResponse;

public class OrderListResponse extends BaseResponse {
    private int nextPageIndex;
    private List<OrderBean> orderList;
}
