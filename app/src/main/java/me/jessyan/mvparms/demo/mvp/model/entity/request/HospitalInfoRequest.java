package me.jessyan.mvparms.demo.mvp.model.entity.request;

public class HospitalInfoRequest extends BaseRequest {
    private final int cmd = 5051;
    private String token;

    @Override
    public String toString() {
        return "HospitalInfoRequest{" +
                "cmd=" + cmd +
                ", token='" + token + '\'' +
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
}
