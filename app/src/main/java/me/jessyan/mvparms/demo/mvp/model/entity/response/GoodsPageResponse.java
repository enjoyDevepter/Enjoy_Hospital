package me.jessyan.mvparms.demo.mvp.model.entity.response;

import java.util.List;

import me.jessyan.mvparms.demo.mvp.model.entity.goods_list.GoodsListBean;

public class GoodsPageResponse extends BaseResponse {
    private List<GoodsListBean> goodsList;
    private int nextPageIndex;

    @Override
    public String toString() {
        return "GoodsPageResponse{" +
                "goodsList=" + goodsList +
                ", nextPageIndex=" + nextPageIndex +
                '}';
    }

    public List<GoodsListBean> getGoodsList() {
        return goodsList;
    }

    public void setGoodsList(List<GoodsListBean> goodsList) {
        this.goodsList = goodsList;
    }

    public int getNextPageIndex() {
        return nextPageIndex;
    }

    public void setNextPageIndex(int nextPageIndex) {
        this.nextPageIndex = nextPageIndex;
    }
}
