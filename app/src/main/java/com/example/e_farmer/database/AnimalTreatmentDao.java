package com.example.e_farmer.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.e_farmer.models.AnimalTreatment;

import java.util.List;

@Dao
public interface AnimalTreatmentDao {

    @Insert
    void insert(AnimalTreatment treatment);

    @Update
    void update(AnimalTreatment treatment);

    @Delete
    void delete(AnimalTreatment treatment);

    @Query("DELETE FROM treatments")
    void deleteAllTreatment();

    @Query("SELECT * FROM treatments WHERE userId = :user_id ORDER BY id DESC")
    LiveData<List<AnimalTreatment>> getAllTreatment(String user_id);
}
