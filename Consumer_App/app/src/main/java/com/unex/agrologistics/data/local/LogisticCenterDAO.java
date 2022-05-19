package com.unex.agrologistics.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.unex.agrologistics.model.LogisticCenter;

import java.util.List;

@Dao
public interface LogisticCenterDAO {

    @Query("SELECT * FROM logistic_center WHERE id = :id")
    LiveData<LogisticCenter> get(int id);

    @Query("SELECT * FROM logistic_center")
    LiveData<List<LogisticCenter>> getAll();

    @Query("SELECT * FROM logistic_center WHERE name LIKE :search")
    LiveData<List<LogisticCenter>> getByName(String search);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(LogisticCenter logisticCenter);
}
