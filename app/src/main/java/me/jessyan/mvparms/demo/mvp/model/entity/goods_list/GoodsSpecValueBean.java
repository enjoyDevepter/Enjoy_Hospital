package me.jessyan.mvparms.demo.mvp.model.entity.goods_list;

public class GoodsSpecValueBean {
    private String specValueId;
    private String specValueName;

    @Override
    public String toString() {
        return "GoodsSpecValue{" +
                "specValueId='" + specValueId + '\'' +
                ", specValueName='" + specValueName + '\'' +
                '}';
    }

    public String getSpecValueName() {
        return specValueName;
    }

    public void setSpecValueName(String specValueName) {
        this.specValueName = specValueName;
    }

    public String getSpecValueId() {
        return specValueId;
    }

    public void setSpecValueId(String specValueId) {
        this.specValueId = specValueId;
    }
}
