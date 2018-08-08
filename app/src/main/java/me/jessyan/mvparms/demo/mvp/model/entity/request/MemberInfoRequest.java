package me.jessyan.mvparms.demo.mvp.model.entity.request;

public class MemberInfoRequest extends BaseRequest {
    private String token;
    private String username;
    private int cmd = 5050;

    @Override
    public String toString() {
        return "HospitalInfoRequest{" +
                "token='" + token + '\'' +
                ", username='" + username + '\'' +
                ", cmd=" + cmd +
                '}';
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getCmd() {
        return cmd;
    }

    public void setCmd(int cmd) {
        this.cmd = cmd;
    }
}
