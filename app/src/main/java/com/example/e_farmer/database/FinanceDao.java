package com.example.e_farmer.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.e_farmer.models.Finance;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface FinanceDao {
    @Insert
    void insert(Finance finance);

    @Update
    void update(Finance finance);

    @Delete
    void delete(Finance finance);

    @Query("SELECT * FROM finance WHERE userId = :user_id ORDER BY id DESC")
    LiveData<List<Finance>> getAllFinance(String user_id);
}
