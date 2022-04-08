package com.unex.agrologistics.data.repository;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.unex.agrologistics.data.local.AppDatabase;
import com.unex.agrologistics.data.remote.APIRetrofitClient;
import com.unex.agrologistics.data.remote.ProductResponse;
import com.unex.agrologistics.data.remote.ProductResponseItem;
import com.unex.agrologistics.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductRepository {

    // TAG for Log messaging
    private static final String TAG = ProductRepository.class.getSimpleName();

    // Singleton class instance
    private static ProductRepository mInstance;

    // Retrofit AgroLogistics API
    private APIRetrofitClient retrofit = APIRetrofitClient.getInstance();

    // Local database
    private AppDatabase database;

    /**
     * Get the ProductRepository Singleton instance
     * @param application Application context
     * @return ProductRepository Singleton instance
     */
    public static synchronized ProductRepository getInstance(Application application){
        if(mInstance == null){
            mInstance = new ProductRepository(application);
        }

        return mInstance;
    }

    /**
     * ProductRepository constructor
     * @param application Application
     */
    private ProductRepository(Application application) {
        database = AppDatabase.getDatabase(application);
    }

    /**
     * Return all the products stored in the database
     * @return LiveData List of all the products stored in the database
     */
    public LiveData<List<Product>> getAllProducts() {
        return database.getProductDAO().getAll();
    }

    /**
     * Return a product stored in the database given an identifier
     * @param id Product identifier to filter
     * @return LiveData of the product stored in the database with a given identifier
     */
    public LiveData<Product> getProductById(int id) {
        return database.getProductDAO().get(id);
    }

    /**
     * Load the products from the API and stores it in the database
     */
    public void loadProducts() {
        // Make the retrofit call to the service
        Call<ProductResponse> productResponseCall = retrofit.getProductServiceAPI().getProducts();

        // Enqueue the response
        productResponseCall.enqueue(new Callback<ProductResponse>() {
            @Override
            public void onResponse(@NonNull Call<ProductResponse> call, @NonNull Response<ProductResponse> response) {
                if (response.isSuccessful()) {
                    // Retrieve the response body
                    ProductResponse productResponse = response.body();
                    assert productResponse != null;
                    // Retrieve the results of the body
                    ArrayList<ProductResponseItem> listProducts = productResponse.getMessage();
                    if (listProducts != null){
                        for (ProductResponseItem productResponseItem : listProducts) {
                            // Insert the product in the database
                            insertProduct(new MutableLiveData<>(processResponseItem(productResponseItem)));
                        }
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<ProductResponse> call, @NonNull Throwable t) {
                Log.d(TAG, Objects.requireNonNull(t.getMessage()));
            }
        });
    }

    /**
     * Parse a ProductResponseItem object to a Product object
     * @param productResponseItem ProductResponseItem object
     * @return Parsed Product object
     */
    private Product processResponseItem (ProductResponseItem productResponseItem){
        // Create the Product item with its attributes
        Product product = new Product();

        product.setId(productResponseItem.getId());
        product.setName(productResponseItem.getName());

        return product;
    }

    /**
     * Insert a Product in the database
     * @param product LiveData of the Product object
     */
    private void insertProduct(LiveData<Product> product) {
        AppDatabase.databaseWriteExecutor.execute(() -> database.getProductDAO().insert(product.getValue()));
    }
}
