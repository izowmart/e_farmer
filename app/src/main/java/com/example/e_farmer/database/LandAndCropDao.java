package com.example.e_farmer.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.e_farmer.models.LandAndCrop;

import java.util.List;

@Dao
public interface LandAndCropDao {

    @Insert
    void insert(LandAndCrop lands);

    @Update
    void update(LandAndCrop lands);

    @Delete
    void delete(LandAndCrop lands);

    @Query("DELETE FROM lands")
    void deleteAllLands();

    @Query("SELECT * FROM lands WHERE userId = :user_id ORDER BY id DESC")
    LiveData<List<LandAndCrop>> getAllLands(String user_id);
}
