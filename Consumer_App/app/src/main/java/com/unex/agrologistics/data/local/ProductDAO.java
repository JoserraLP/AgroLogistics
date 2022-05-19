package com.unex.agrologistics.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.unex.agrologistics.model.Product;

import java.util.List;

@Dao
public interface ProductDAO {

    @Query("SELECT * FROM product WHERE id = :id")
    LiveData<Product> get(int id);

    @Query("SELECT * FROM product")
    LiveData<List<Product>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Product product);
}
