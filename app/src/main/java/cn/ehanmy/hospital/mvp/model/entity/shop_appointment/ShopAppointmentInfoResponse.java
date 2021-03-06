package cn.ehanmy.hospital.mvp.model.entity.shop_appointment;

import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

public class ShopAppointmentInfoResponse extends BaseResponse {
    private OrderProjectDetailBean orderProjectDetail;

    @Override
    public String toString() {
        return "ShopAppointmentInfoResponse{" +
                "orderProjectDetail=" + orderProjectDetail +
                '}';
    }

    public OrderProjectDetailBean getOrderProjectDetail() {
        return orderProjectDetail;
    }

    public void setOrderProjectDetail(OrderProjectDetailBean orderProjectDetail) {
        this.orderProjectDetail = orderProjectDetail;
    }
}
