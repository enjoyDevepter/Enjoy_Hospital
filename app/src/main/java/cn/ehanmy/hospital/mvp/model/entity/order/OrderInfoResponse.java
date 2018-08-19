package cn.ehanmy.hospital.mvp.model.entity.order;

import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

public class OrderInfoResponse extends BaseResponse {
    private OrderInfoRequest order;

    @Override
    public String toString() {
        return "OrderInfoResponse{" +
                "order=" + order +
                '}';
    }

    public OrderInfoRequest getOrder() {
        return order;
    }

    public void setOrder(OrderInfoRequest order) {
        this.order = order;
    }
}
