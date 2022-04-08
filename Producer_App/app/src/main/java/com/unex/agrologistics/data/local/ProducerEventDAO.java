package com.unex.agrologistics.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.unex.agrologistics.model.ProducerEvent;

import java.util.List;

@Dao
public interface ProducerEventDAO {

    @Query("SELECT * FROM producer_event WHERE id = :id")
    LiveData<ProducerEvent> get(int id);

    @Query("SELECT * FROM producer_event WHERE producer_id = :producer_id")
    LiveData<List<ProducerEvent>> getFromProducer(int producer_id);

    @Query("SELECT * FROM producer_event WHERE logistic_center_id = :logistic_center_id " +
            "AND date BETWEEN :ini_date AND :fin_date")
    LiveData<List<ProducerEvent>> getFromLogisticCenterByDate(int logistic_center_id,
                                                              String ini_date, String fin_date);

    @Query("SELECT * FROM producer_event WHERE logistic_center_id = :logistic_center_id AND " +
            "storage_type = :storage_type AND date BETWEEN :ini_date AND :fin_date")
    LiveData<List<ProducerEvent>> getFromLogisticCenterByDateAndType(int logistic_center_id,
                                                                     String ini_date, String fin_date, String storage_type);

    @Query("SELECT * FROM producer_event WHERE logistic_center_id = :logistic_center_id AND " +
            "storage_type = :storage_type AND date BETWEEN DATE(:ini_date) AND DATE(:fin_date)")
    LiveData<List<ProducerEvent>> getFromLogisticCenterByDayAndType(int logistic_center_id,
                                                                    String ini_date,
                                                                    String fin_date,
                                                                    String storage_type);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ProducerEvent producerEvent);

    @Query("DELETE FROM producer_event WHERE id=:id")
    void delete(int id);
}
