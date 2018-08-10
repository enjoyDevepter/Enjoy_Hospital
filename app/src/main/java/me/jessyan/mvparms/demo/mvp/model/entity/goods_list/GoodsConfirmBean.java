package me.jessyan.mvparms.demo.mvp.model.entity.goods_list;

public class GoodsConfirmBean {
    private String goodsId;
    private String merchId;
    private int nums;
    private double salesPrice;

    @Override
    public String toString() {
        return "GoodsConfirmBean{" +
                "goodsId='" + goodsId + '\'' +
                ", merchId='" + merchId + '\'' +
                ", nums=" + nums +
                ", salesPrice=" + salesPrice +
                '}';
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getMerchId() {
        return merchId;
    }

    public void setMerchId(String merchId) {
        this.merchId = merchId;
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    public double getSalesPrice() {
        return salesPrice;
    }

    public void setSalesPrice(double salesPrice) {
        this.salesPrice = salesPrice;
    }
}
