package me.jessyan.mvparms.demo.mvp.model.entity.response;

import java.util.List;

import me.jessyan.mvparms.demo.mvp.model.entity.goods_list.GoodsListBean;
import me.jessyan.mvparms.demo.mvp.model.entity.goods_list.PayEntry;

public class GoodsBuyResponse extends BaseResponse {
    private String orderId;
    private long orderTime;
    private long payMoney;
    private String payStatus;
    private List<GoodsListBean> goodsList;
    private List<PayEntry> payEntryList;
}
