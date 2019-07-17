package com.example.e_farmer.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.e_farmer.models.Animals;

import java.util.List;

@Dao
public interface AnimalsDao {
    @Insert
    void insert(Animals animal);

    @Update
    void update(Animals animal);

    @Delete
    void delete(Animals animal);

    @Query("SELECT * FROM animals WHERE userId = :user_id ORDER BY id DESC")
    LiveData<List<Animals>> getAllAnimals(String user_id);
}
