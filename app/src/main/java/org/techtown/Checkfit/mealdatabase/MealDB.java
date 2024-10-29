package org.techtown.Checkfit.mealdatabase;

import android.app.Application;
import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {MealEntity.class}, version = 1)
public abstract class MealDB extends RoomDatabase {

    private static volatile MealDB INSTANCE = null;

    public abstract MealDao mealDao();

    public static MealDB getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                    MealDB.class, "meal.db").build();
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }
}

