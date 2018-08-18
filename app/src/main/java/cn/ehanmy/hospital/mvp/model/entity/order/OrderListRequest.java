package cn.ehanmy.hospital.mvp.model.entity.order;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class OrderListRequest extends BaseRequest {
    private final int cmd = 5151;
    private String orderStatus;
    private int pageIndex;
    private int pageSize;
    private String token;

    @Override
    public String toString() {
        return "OrderListRequest{" +
                "cmd=" + cmd +
                ", orderStatus='" + orderStatus + '\'' +
                ", pageIndex=" + pageIndex +
                ", pageSize=" + pageSize +
                ", token='" + token + '\'' +
                '}';
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        /*
        * 不填时显示全部订单
            1:待付款2:待二次付款;5:已完成

        * */
        this.orderStatus = orderStatus;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCmd() {
        return cmd;
    }
}
