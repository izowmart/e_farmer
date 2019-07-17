package com.example.e_farmer.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.e_farmer.models.Machine;

import java.util.List;

@Dao
public interface MachineDao {
    @Insert
    void insert(Machine machine);

    @Update
    void update(Machine machine);

    @Delete
    void delete(Machine machine);

    @Query("SELECT * FROM machines WHERE userId = :user_id ORDER BY id DESC")
    LiveData<List<Machine>> getAllMachines(String user_id);
}
