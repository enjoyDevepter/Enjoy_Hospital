package me.jessyan.mvparms.demo.mvp.model.api.service;

import io.reactivex.Observable;
import me.jessyan.mvparms.demo.mvp.model.entity.request.LoginRequest;
import me.jessyan.mvparms.demo.mvp.model.entity.response.LoginResponse;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by guomin on 2018/1/29.
 */

public interface LoginService {

    @POST("gateway")
    Observable<LoginResponse> login(@Body LoginRequest request);

}
