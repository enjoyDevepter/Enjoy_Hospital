package cn.ehanmy.hospital.mvp.model.entity.response;

import cn.ehanmy.hospital.mvp.model.entity.hospital.HospitaInfoBean;

public class HospitalInfoResponse extends BaseResponse {
    private HospitaInfoBean hospital;

    @Override
    public String toString() {
        return "HospitalInfoResponse{" +
                "hospital=" + hospital +
                '}';
    }

    public HospitaInfoBean getHospital() {
        return hospital;
    }

    public void setHospital(HospitaInfoBean hospital) {
        this.hospital = hospital;
    }
}
