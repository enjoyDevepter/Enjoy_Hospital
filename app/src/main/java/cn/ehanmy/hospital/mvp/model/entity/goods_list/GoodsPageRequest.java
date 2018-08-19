package cn.ehanmy.hospital.mvp.model.entity.goods_list;

import cn.ehanmy.hospital.mvp.model.entity.goods_list.OrderBy;
import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class GoodsPageRequest extends BaseRequest {
    private int pageIndex;  // 从1开始

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    private int pageSize;
    private OrderBy orderBy;
    private String token;
    private int cmd = 5101;


    @Override
    public String toString() {
        return "GoodsPageRequest{" +
                "pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
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
