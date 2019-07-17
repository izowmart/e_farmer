package com.example.e_farmer.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.e_farmer.models.FarmTask;

import java.util.List;

@Dao
public interface FarmTaskDao {

    @Insert
    void insert(FarmTask task);

    @Update
    void update(FarmTask task);

    @Delete
    void delete(FarmTask task);

    @Query("DELETE FROM tasks")
    void deleteAllTasks();

    @Query("SELECT * FROM tasks WHERE userId = :user_id ORDER BY id DESC")
    LiveData<List<FarmTask>> getAllTasks(String user_id);
}
