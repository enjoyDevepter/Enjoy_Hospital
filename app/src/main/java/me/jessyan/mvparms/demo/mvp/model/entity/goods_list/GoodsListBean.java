package me.jessyan.mvparms.demo.mvp.model.entity.goods_list;

import java.io.Serializable;

public class GoodsListBean implements Serializable{
    private int attention;
    private int cnt;
    private float costPrice;
    private int favorite;
    private String goodsId;
    private GoodsSpecValueBean goodsSpecValue;
    private String image;
    private float marketPrice;
    private String merchId;
    private String name;
    private float salePrice;
    private int sales;
    private String title;

    @Override
    public String toString() {
        return "GoodsListBean{" +
                "attention=" + attention +
                ", cnt=" + cnt +
                ", costPrice=" + costPrice +
                ", favorite=" + favorite +
                ", goodsId='" + goodsId + '\'' +
                ", goodsSpecValue=" + goodsSpecValue +
                ", image='" + image + '\'' +
                ", marketPrice=" + marketPrice +
                ", merchId='" + merchId + '\'' +
                ", name='" + name + '\'' +
                ", salePrice=" + salePrice +
                ", sales=" + sales +
                ", title='" + title + '\'' +
                ", nums=" + nums +
                '}';
    }

    public int getNums() {
        return nums;
    }

    public void setNums(int nums) {
        this.nums = nums;
    }

    private int nums = 1;

    public int getAttention() {
        return attention;
    }

    public void setAttention(int attention) {
        this.attention = attention;
    }

    public int getCnt() {
        return cnt;
    }

    public void setCnt(int cnt) {
        this.cnt = cnt;
    }

    public float getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(float costPrice) {
        this.costPrice = costPrice;
    }

    public int getFavorite() {
        return favorite;
    }

    public void setFavorite(int favorite) {
        this.favorite = favorite;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public GoodsSpecValueBean getGoodsSpecValue() {
        return goodsSpecValue;
    }

    public void setGoodsSpecValue(GoodsSpecValueBean goodsSpecValue) {
        this.goodsSpecValue = goodsSpecValue;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public float getMarketPrice() {
        return marketPrice;
    }

    public void setMarketPrice(float marketPrice) {
        this.marketPrice = marketPrice;
    }

    public String getMerchId() {
        return merchId;
    }

    public void setMerchId(String merchId) {
        this.merchId = merchId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(float salePrice) {
        this.salePrice = salePrice;
    }

    public int getSales() {
        return sales;
    }

    public void setSales(int sales) {
        this.sales = sales;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
