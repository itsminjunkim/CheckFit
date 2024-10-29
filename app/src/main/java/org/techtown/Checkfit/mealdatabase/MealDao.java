package org.techtown.Checkfit.mealdatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MealDao {
    @Query("SELECT * FROM meals")
    LiveData<List<MealEntity>> getAll();

    @Query("SELECT * FROM meals WHERE date = :date")
    LiveData<MealEntity> loadMealByDate(String date);

    @Query("SELECT * FROM meals WHERE date = :date LIMIT 1")
    MealEntity getMealByDateSync(String date);

    @Insert
    void insertAll(MealEntity... meals);

    @Insert
    void insert(MealEntity meal);

    @Update
    void update(MealEntity meal);

    @Delete
    void delete(MealEntity meal);
}
