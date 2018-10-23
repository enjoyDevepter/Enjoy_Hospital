package cn.ehanmy.hospital.mvp.model.entity.order;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

public class GetOrderAppointmentTimeRequest extends BaseRequest {
    private final int cmd = 5108;
    private String token;
    private String goodsId;
    private String merchId;

    @Override
    public String toString() {
        return "GetOrderAppointmentTimeRequest{" +
                "cmd=" + cmd +
                ", token='" + token + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", merchId='" + merchId + '\'' +
                '}';
    }

    public int getCmd() {
        return cmd;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
}
