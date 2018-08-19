package cn.ehanmy.hospital.mvp.contract;

import com.jess.arms.mvp.IView;
import com.jess.arms.mvp.IModel;

import java.util.List;

import cn.ehanmy.hospital.mvp.model.entity.ShopAppointment;
import cn.ehanmy.hospital.mvp.model.entity.User;
import cn.ehanmy.hospital.mvp.model.entity.UserAppointment;


public interface UserAppointmentContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View extends IView {
        void updateList(List<UserAppointment> userAppointments);
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,即是否使用缓存
    interface Model extends IModel {
        List<UserAppointment> doSearch(String searchKey, int SearchType);
    }
}