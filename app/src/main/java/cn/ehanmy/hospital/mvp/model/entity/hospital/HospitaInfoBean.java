package cn.ehanmy.hospital.mvp.model.entity.hospital;

import java.io.Serializable;

public class HospitaInfoBean implements Serializable {
    private String hospitalId;
    private String name;
    private String province;
    private String provinceName;
    private String city;
    private String cityName;
    private String county;
    private String countyName;
    private String address;
    private String distance;
    private String distanceDesc;
    private String tellphone;
    private String endTime;
    private String starTime;
    private String image;

    @Override
    public String toString() {
        return "HospitaInfoBean{" +
                "hospitalId='" + hospitalId + '\'' +
                ", name='" + name + '\'' +
                ", province='" + province + '\'' +
                ", provinceName='" + provinceName + '\'' +
                ", city='" + city + '\'' +
                ", cityName='" + cityName + '\'' +
                ", county='" + county + '\'' +
                ", countyName='" + countyName + '\'' +
                ", address='" + address + '\'' +
                ", distance='" + distance + '\'' +
                ", distanceDesc='" + distanceDesc + '\'' +
                ", tellphone='" + tellphone + '\'' +
                ", endTime='" + endTime + '\'' +
                ", starTime='" + starTime + '\'' +
                ", image='" + image + '\'' +
                '}';
    }

    public String getHospitalId() {
        return hospitalId;
    }

    public void setHospitalId(String hospitalId) {
        this.hospitalId = hospitalId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProvinceName() {
        return provinceName;
    }

    public void setProvinceName(String provinceName) {
        this.provinceName = provinceName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getCountyName() {
        return countyName;
    }

    public void setCountyName(String countyName) {
        this.countyName = countyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getDistanceDesc() {
        return distanceDesc;
    }

    public void setDistanceDesc(String distanceDesc) {
        this.distanceDesc = distanceDesc;
    }

    public String getTellphone() {
        return tellphone;
    }

    public void setTellphone(String tellphone) {
        this.tellphone = tellphone;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStarTime() {
        return starTime;
    }

    public void setStarTime(String starTime) {
        this.starTime = starTime;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
