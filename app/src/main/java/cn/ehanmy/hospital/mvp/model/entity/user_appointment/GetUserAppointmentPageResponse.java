package cn.ehanmy.hospital.mvp.model.entity.user_appointment;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;

public class GetUserAppointmentPageResponse extends BaseResponse {
    private int nextPageIndex;
    private List<OrderProjectDetailBean> orderProjectDetailList;

    @Override
    public String toString() {
        return "GetUserAppointmentPageResponse{" +
                "nextPageIndex=" + nextPageIndex +
                ", orderProjectDetailList=" + orderProjectDetailList +
                '}';
    }

    public int getNextPageIndex() {
        return nextPageIndex;
    }

    public void setNextPageIndex(int nextPageIndex) {
        this.nextPageIndex = nextPageIndex;
    }

    public List<OrderProjectDetailBean> getOrderProjectDetailList() {
        return orderProjectDetailList;
    }

    public void setOrderProjectDetailList(List<OrderProjectDetailBean> orderProjectDetailList) {
        this.orderProjectDetailList = orderProjectDetailList;
    }
}
