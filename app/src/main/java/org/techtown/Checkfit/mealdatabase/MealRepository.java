package org.techtown.Checkfit.mealdatabase;

import android.app.Application;

import androidx.lifecycle.LiveData;

import org.techtown.Checkfit.mealdatabase.MealDB;
import org.techtown.Checkfit.mealdatabase.MealDao;
import org.techtown.Checkfit.mealdatabase.MealEntity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MealRepository {

    private MealDao mealDao;
    private LiveData<List<MealEntity>> allMeals;
    private ExecutorService executorService;

    public MealRepository(Application application) {
        MealDB db = MealDB.getInstance(application);
        mealDao = db.mealDao();
        allMeals = mealDao.getAll();
        executorService = Executors.newSingleThreadExecutor();
    }

    public LiveData<List<MealEntity>> getAllMeals() {
        return allMeals;
    }

    public LiveData<MealEntity> loadMealByDate(String date) {
        return mealDao.loadMealByDate(date);
    }

    public void insert(MealEntity meal) {
        executorService.execute(() -> mealDao.insert(meal));
    }

    public void update(MealEntity meal) {
        executorService.execute(() -> mealDao.update(meal));
    }

    public void delete(MealEntity meal) {
        executorService.execute(() -> mealDao.delete(meal));
    }

    public void insertOrUpdate(MealEntity meal) {
        executorService.execute(() -> {
            MealEntity existingMeal = mealDao.getMealByDateSync(meal.getDate());
            if (existingMeal != null) {
                existingMeal.setTotalKcal(meal.getTotalKcal());
                existingMeal.setCarbohydrate(meal.getCarbohydrate());
                existingMeal.setProtein(meal.getProtein());
                existingMeal.setFat(meal.getFat());
                mealDao.update(existingMeal);
            } else {
                mealDao.insert(meal);
            }
        });
    }
}
