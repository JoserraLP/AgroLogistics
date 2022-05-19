package com.unex.agrologistics.ui.delivery.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.unex.agrologistics.data.repository.ProductRepository;
import com.unex.agrologistics.model.Product;

import java.util.List;

public class ProductsViewModel extends AndroidViewModel {

    // LiveData of the products
    private LiveData<List<Product>> mAllProducts;

    // Product repository
    private ProductRepository mRepository;
    /**
     * ProductsViewModel constructor
     * @param application Application
     */
    public ProductsViewModel(Application application) {
        super(application);
        // Get repository instance
        mRepository = ProductRepository.getInstance(application);
        // Get all products
        mAllProducts = mRepository.getAllProducts();
    }

    /**
     * Get all products
     * @return LiveData list with all products in the database
     */
    public LiveData<List<Product>> getAllProducts() {
        return mAllProducts;
    }

    /**
     * Get a product given an identifier
     * @param id Product identifier to filter
     * @return LiveData of the product stored in the database with a given identifier
     */
    public LiveData<Product> getProductById(int id) {
        return mRepository.getProductById(id);
    }
}