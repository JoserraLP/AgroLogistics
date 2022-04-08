package com.unex.agrologistics.data.remote;

import com.unex.agrologistics.utils.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIRetrofitClient {

    // URL for the Retrofit Connection
    private static final String BASE_URL = Constants.AGROLOGISTICS_API_SERVER_URL;

    // Singleton class instance
    private static APIRetrofitClient mInstance;

    // okHttpClient
    private static OkHttpClient okHttpClient = new OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build();

    // Retrofit variable
    private Retrofit retrofit;

    /**
     * Create the retrofit connection to the URL
     */
    private APIRetrofitClient(){
        // Create API Retrofit instance
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    /**
     * Get the API Retrofit Client Singleton instance
     * @return APIRetrofitClient singleton instance
     */
    public static synchronized APIRetrofitClient getInstance(){
        if(mInstance == null){
            mInstance = new APIRetrofitClient();
        }
        return mInstance;
    }

    /**
     * Create the retrofit logistic center service
     * @return Logistic Center Service
     */
    public LogisticCenterService getLogisticCenterServiceAPI(){
        return retrofit.create(LogisticCenterService.class);
    }

    /**
     * Create the retrofit product service
     * @return Product Service
     */
    public ProductService getProductServiceAPI(){
        return retrofit.create(ProductService.class);
    }

    /**
     * Create the retrofit producer service
     * @return Producer Service
     */
    public ProducerService getProducerServiceAPI(){
        return retrofit.create(ProducerService.class);
    }

    /**
     * Create the retrofit producer event service
     * @return Producer Event Service
     */
    public ProducerEventService getProducerEventServiceAPI(){
        return retrofit.create(ProducerEventService.class);
    }
}
