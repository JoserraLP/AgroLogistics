package com.unex.agrologistics.data.remote;

import com.unex.agrologistics.model.Consumer;
import com.unex.agrologistics.model.Producer;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ConsumerService {

    @GET("consumer")
    Call<ConsumerResponse> getConsumers();

    @POST("consumer")
    Call<PostResponse> postConsumer(@Body Consumer consumer);

}
