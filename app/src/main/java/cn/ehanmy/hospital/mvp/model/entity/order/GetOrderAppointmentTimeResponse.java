package cn.ehanmy.hospital.mvp.model.entity.order;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.response.BaseResponse;
import cn.ehanmy.hospital.mvp.model.entity.user_appointment.ReservationDateBean;

public class GetOrderAppointmentTimeResponse extends BaseResponse {
    private int nextPageIndex;
    private List<ReservationDateBean> reservationDateList;

    @Override
    public String toString() {
        return "GetUserAppointmentTimeResponse{" +
                "nextPageIndex=" + nextPageIndex +
                ", reservationDateList=" + reservationDateList +
                '}';
    }

    public int getNextPageIndex() {
        return nextPageIndex;
    }

    public void setNextPageIndex(int nextPageIndex) {
        this.nextPageIndex = nextPageIndex;
    }

    public List<ReservationDateBean> getReservationDateList() {
        return reservationDateList;
    }

    public void setReservationDateList(List<ReservationDateBean> reservationDateList) {
        this.reservationDateList = reservationDateList;
    }
}
