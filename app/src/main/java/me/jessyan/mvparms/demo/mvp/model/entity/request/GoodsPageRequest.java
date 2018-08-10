package me.jessyan.mvparms.demo.mvp.model.entity.request;

import me.jessyan.mvparms.demo.mvp.model.entity.goods_list.OrderBy;

public class GoodsPageRequest extends BaseRequest {
    private int pageIndex;  // 从1开始
//    private int pageSize;
    private OrderBy orderBy;
    private String token;
    private int cmd = 5101;

    @Override
    public String toString() {
        return "GoodsPageRequest{" +
                "pageIndex=" + pageIndex +
                ", orderBy=" + orderBy +
                ", token='" + token + '\'' +
                ", cmd=" + cmd +
                '}';
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public OrderBy getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(OrderBy orderBy) {
        this.orderBy = orderBy;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
