package me.jessyan.mvparms.demo.mvp.model.entity.order;

import java.util.List;

import me.jessyan.mvparms.demo.mvp.model.entity.goods_list.GoodsListBean;

public class OrderBean {
    private long orderTime;
    private int nums;
    private String orderId;
    private String orderType;
    private String orderTypeDesc;
    private String orderStatus;
    private long payMoney;
    private long price;
    private long totalPrice;
    private List<GoodsOrderBean> goodsList;
}
