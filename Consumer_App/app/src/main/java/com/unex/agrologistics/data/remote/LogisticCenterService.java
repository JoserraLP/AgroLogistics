package com.unex.agrologistics.data.remote;

import retrofit2.Call;
import retrofit2.http.GET;

public interface LogisticCenterService {

    @GET("logistic_center")
    Call<LogisticCenterResponse> getLogisticCenters();
}
