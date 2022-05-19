package com.unex.agrologistics.data.local;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.unex.agrologistics.model.Consumer;
import com.unex.agrologistics.model.ConsumerEvent;
import com.unex.agrologistics.model.LogisticCenter;
import com.unex.agrologistics.model.Producer;
import com.unex.agrologistics.model.ProducerEvent;
import com.unex.agrologistics.model.Product;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {LogisticCenter.class, Consumer.class, ConsumerEvent.class, Product.class},
                      version = 1, exportSchema = false)

public abstract class AppDatabase extends RoomDatabase {

    // Singleton class instance
    private static AppDatabase INSTANCE;

    // Number of threads for the database
    private static final int NUMBER_OF_THREADS = 4;

    // Database writer executor
    public static ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    // Database name
    private static final String DB_NAME = "app_agro_consumers2.db";

    // Callback for opening the db
    private static  Callback sAppDatabaseCallback = new Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);
        }
    };

    /**
     * Return the database connection
     * @param context Application context
     * @return The instance of the connection to the database
     */
    public static AppDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class, DB_NAME)
                            .addCallback(sAppDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    // LogisticCenterDAO
    abstract LogisticCenterDAO logisticCenterDAO();

    /**
     * Returns the LogisticCenterDAO
     * @return LogisticCenterDAO
     */
    public LogisticCenterDAO getLogisticCenterDAO (){
        return this.logisticCenterDAO();
    }

    // ProductDAO
    abstract ProductDAO productDAO();

    /**
     * Returns the ProductDAO
     * @return ProductDAO
     */
    public ProductDAO getProductDAO (){
        return this.productDAO();
    }

    // ConsumerDAO
    abstract ConsumerDAO consumerDAO();

    /**
     * Returns the ConsumerDAO
     * @return ConsumerDAO
     */
    public ConsumerDAO getConsumerDAO(){
        return this.consumerDAO();
    }

    // ConsumerEventDAO
    abstract ConsumerEventDAO consumerEventDAO();

    /**
     * Returns the ConsumerEventDAO
     * @return ConsumerEventDAO
     */
    public ConsumerEventDAO getConsumerEventDAO(){
        return this.consumerEventDAO();
    }
}
