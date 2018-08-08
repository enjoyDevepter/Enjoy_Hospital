package me.jessyan.mvparms.demo.mvp.model.entity.response;

import me.jessyan.mvparms.demo.mvp.model.entity.member_info.City;
import me.jessyan.mvparms.demo.mvp.model.entity.member_info.County;
import me.jessyan.mvparms.demo.mvp.model.entity.member_info.Province;
import me.jessyan.mvparms.demo.mvp.model.entity.member_info.Rank;

public class MemberInfoResponse extends BaseResponse {

    private String username;
    private String headImage;
    private String sex;
    private String realName;
    private String mobile;
    private County county;
    private String address;
    private City city;
    private Province province;
    private Rank rank;

    @Override
    public String toString() {
        return "HospitalInfoResponse{" +
                "username='" + username + '\'' +
                ", headImage='" + headImage + '\'' +
                ", sex='" + sex + '\'' +
                ", realName='" + realName + '\'' +
                ", mobile='" + mobile + '\'' +
                ", county=" + county +
                ", address='" + address + '\'' +
                ", city=" + city +
                ", province=" + province +
                ", rank=" + rank +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public County getCounty() {
        return county;
    }

    public void setCounty(County county) {
        this.county = county;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public City getCity() {
        return city;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public Province getProvince() {
        return province;
    }

    public void setProvince(Province province) {
        this.province = province;
    }

    public Rank getRank() {
        return rank;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }
}
