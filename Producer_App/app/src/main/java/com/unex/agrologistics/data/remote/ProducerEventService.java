package com.unex.agrologistics.data.remote;

import com.unex.agrologistics.model.ProducerEvent;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ProducerEventService {

    @POST("producer_event")
    Call<PostResponse> postProducerEvent(@Body ProducerEvent producerEvent);

    @GET("producer_event")
    Call<ProducerEventResponse> getProducerEvents();

}