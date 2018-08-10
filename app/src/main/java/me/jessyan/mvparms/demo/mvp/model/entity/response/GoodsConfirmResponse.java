package me.jessyan.mvparms.demo.mvp.model.entity.response;

import java.util.List;

import me.jessyan.mvparms.demo.mvp.model.entity.goods_list.GoodsListBean;
import me.jessyan.mvparms.demo.mvp.model.entity.goods_list.GoodsSpecValueBean;

public class GoodsConfirmResponse extends BaseResponse {
    private long balance;
    private long money;
    private long price;
    private long totalPrice;
    private long payMoney;
    private GoodsListBean goods;
    private List<GoodsSpecValueBean> goodsSpecValueList;
}
