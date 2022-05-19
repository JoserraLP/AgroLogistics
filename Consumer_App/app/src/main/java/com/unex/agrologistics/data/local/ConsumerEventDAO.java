package com.unex.agrologistics.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.unex.agrologistics.model.ConsumerEvent;
import com.unex.agrologistics.model.ProducerEvent;

import java.util.List;

@Dao
public interface ConsumerEventDAO {

    @Query("SELECT * FROM consumer_event WHERE id = :id")
    LiveData<ConsumerEvent> get(int id);

    @Query("SELECT * FROM consumer_event WHERE consumer_id = :consumer_id")
    LiveData<List<ConsumerEvent>> getFromConsumer(int consumer_id);

    @Query("SELECT * FROM consumer_event WHERE logistic_center_id = :logistic_center_id " +
            "AND date BETWEEN :ini_date AND :fin_date")
    LiveData<List<ConsumerEvent>> getFromLogisticCenterByDate(int logistic_center_id,
                                                              String ini_date, String fin_date);

    @Query("SELECT * FROM consumer_event WHERE logistic_center_id = :logistic_center_id AND " +
            "storage_type = :storage_type AND date BETWEEN :ini_date AND :fin_date")
    LiveData<List<ConsumerEvent>> getFromLogisticCenterByDateAndType(int logistic_center_id,
                                                                     String ini_date, String fin_date, String storage_type);

    @Query("SELECT * FROM consumer_event WHERE logistic_center_id = :logistic_center_id AND " +
            "storage_type = :storage_type AND date BETWEEN DATE(:ini_date) AND DATE(:fin_date)")
    LiveData<List<ConsumerEvent>> getFromLogisticCenterByDayAndType(int logistic_center_id,
                                                                    String ini_date,
                                                                    String fin_date,
                                                                    String storage_type);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ConsumerEvent producerEvent);

    @Query("DELETE FROM consumer_event WHERE id=:id")
    void delete(int id);
}
