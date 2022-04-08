package com.unex.agrologistics.data.remote;

import com.unex.agrologistics.model.Producer;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ProducerService {

    @GET("producer")
    Call<ProducerResponse> getProducers();

    @POST("producer")
    Call<PostResponse> postProducer(@Body Producer producer);

}
