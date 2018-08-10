package me.jessyan.mvparms.demo.mvp.model.entity.request;

import me.jessyan.mvparms.demo.mvp.model.entity.goods_list.GoodsConfirmBean;
import me.jessyan.mvparms.demo.mvp.model.entity.goods_list.GoodsListBean;

public class GoodsBuyRequest extends BaseRequest {
    private final int cmd = 5103;
    private String token;
    private GoodsConfirmBean goods;
    private String memberId;
    private long money;
    private long price;
    private long totalPrice;
    private long payMoney;
    private String remark;
}
