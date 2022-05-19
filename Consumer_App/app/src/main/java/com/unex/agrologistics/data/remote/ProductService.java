package com.unex.agrologistics.data.remote;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductService {

    @GET("product")
    Call<ProductResponse> getProducts();
}
