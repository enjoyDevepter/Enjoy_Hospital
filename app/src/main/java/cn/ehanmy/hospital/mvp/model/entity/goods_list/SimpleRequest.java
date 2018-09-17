package cn.ehanmy.hospital.mvp.model.entity.goods_list;

import cn.ehanmy.hospital.mvp.model.entity.request.BaseRequest;

/**
 * Created by guomin on 2018/7/25.
 */

public class SimpleRequest extends BaseRequest {
    private final int cmd = 5201;

    public int getCmd() {
        return cmd;
    }

    @Override
    public String toString() {
        return "SimpleRequest{" +
                "cmd=" + cmd +
                '}';
    }
}
