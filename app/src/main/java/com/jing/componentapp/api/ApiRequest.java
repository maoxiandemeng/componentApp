package com.jing.componentapp.api;

import com.jing.componentapp.bean.FuLiBean;

import java.util.ArrayList;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by liujing on 2017/7/20.
 */

public interface ApiRequest {
    String base_url = "http://gank.io/api/";

    @GET("data/福利/10/{page}")
    Observable<GankResult<ArrayList<FuLiBean>>> getData(@Path("page") int page);

}
