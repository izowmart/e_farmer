package com.example.e_farmer.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.e_farmer.Settings;
import com.example.e_farmer.models.AnimalTreatment;
import com.example.e_farmer.models.Animals;
import com.example.e_farmer.models.FarmTask;
import com.example.e_farmer.models.Finance;
import com.example.e_farmer.models.LandAndCrop;
import com.example.e_farmer.models.Machine;
import com.example.e_farmer.models.User;

@Database(entities = {AnimalTreatment.class, Animals.class, FarmTask.class, Finance.class, LandAndCrop.class, Machine.class, User.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;

    public static synchronized AppDatabase getInstance(Context context) {

        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "database")
//                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract AnimalsDao animalsDao();

    public abstract AnimalTreatmentDao animalTreatmentDao();

    public abstract FinanceDao financeDao();

    public abstract FarmTaskDao farmTaskDao();

    public abstract LandAndCropDao landAndCropDao();

    public abstract MachineDao machineDao();

    public abstract UserDao userDao();


}
