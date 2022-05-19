package com.unex.agrologistics.data.remote;

import com.unex.agrologistics.model.ConsumerEvent;
import com.unex.agrologistics.model.ProducerEvent;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ConsumerEventService {

    @POST("consumer_event")
    Call<PostResponse> postConsumerEvent(@Body ConsumerEvent consumerEvent);

    @GET("consumer_event")
    Call<ConsumerEventResponse> getConsumerEvents();

}