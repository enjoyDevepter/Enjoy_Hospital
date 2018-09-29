package cn.ehanmy.hospital.mvp.model.entity.user_appointment;

import java.io.Serializable;

public class ReservationTimeBean implements Serializable {
    private String full;  // 1:是0:否
    private String time;

    @Override
    public String toString() {
        return "ReservationTimeBean{" +
                "full='" + full + '\'' +
                ", time='" + time + '\'' +
                '}';
    }

    public String getFull() {
        return full;
    }

    public void setFull(String full) {
        this.full = full;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
