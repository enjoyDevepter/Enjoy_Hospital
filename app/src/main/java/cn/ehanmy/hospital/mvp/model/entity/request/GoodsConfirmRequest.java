package cn.ehanmy.hospital.mvp.model.entity.request;

import cn.ehanmy.hospital.mvp.model.entity.goods_list.GoodsConfirmBean;

public class GoodsConfirmRequest extends BaseRequest {
    private String token;
    private GoodsConfirmBean goods;
    private String memberId;
    private int money;
    private final int cmd = 5102;

    @Override
    public String toString() {
        return "GoodsConfirmRequest{" +
                "token='" + token + '\'' +
                ", goods=" + goods +
                ", memberId='" + memberId + '\'' +
                ", money=" + money +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public GoodsConfirmBean getGoods() {
        return goods;
    }

    public void setGoods(GoodsConfirmBean goods) {
        this.goods = goods;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
