package com.unex.agrologistics.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.unex.agrologistics.model.Consumer;
import com.unex.agrologistics.model.Producer;

import java.util.List;

@Dao
public interface ConsumerDAO {

    @Query("SELECT * FROM consumer WHERE id = :id")
    LiveData<Consumer> get(int id);

    @Query("SELECT * FROM consumer WHERE email = :email")
    LiveData<Consumer> getByEmail(String email);

    @Query("SELECT * FROM consumer")
    LiveData<List<Consumer>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Consumer consumer);

    @Query("DELETE FROM consumer WHERE id=:id")
    void delete(int id);
}
