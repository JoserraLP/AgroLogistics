package com.unex.agrologistics.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.unex.agrologistics.model.Producer;

import java.util.List;

@Dao
public interface ProducerDAO {

    @Query("SELECT * FROM producer WHERE id = :id")
    LiveData<Producer> get(int id);

    @Query("SELECT * FROM producer WHERE email = :email")
    LiveData<Producer> getByEmail(String email);

    @Query("SELECT * FROM producer")
    LiveData<List<Producer>> getAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Producer producer);

    @Query("DELETE FROM producer WHERE id=:id")
    void delete(int id);
}
