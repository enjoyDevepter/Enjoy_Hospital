package cn.ehanmy.hospital.mvp.model.entity.order;

import java.io.Serializable;
import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsListBean;

public class OrderBean implements Serializable{
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

    @Override
    public String toString() {
        return "OrderBean{" +
                "orderTime=" + orderTime +
                ", nums=" + nums +
                ", orderId='" + orderId + '\'' +
                ", orderType='" + orderType + '\'' +
                ", orderTypeDesc='" + orderTypeDesc + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", payMoney=" + payMoney +
                ", price=" + price +
                ", totalPrice=" + totalPrice +
                ", goodsList=" + goodsList +
                '}';
    }

    public long getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(long orderTime) {
        this.orderTime = orderTime;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getOrderTypeDesc() {
        return orderTypeDesc;
    }

    public void setOrderTypeDesc(String orderTypeDesc) {
        this.orderTypeDesc = orderTypeDesc;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public long getPayMoney() {
        return payMoney;
    }

    public void setPayMoney(long payMoney) {
        this.payMoney = payMoney;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<GoodsOrderBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsOrderBean> goodsList) {
        this.goodsList = goodsList;
    }
}
