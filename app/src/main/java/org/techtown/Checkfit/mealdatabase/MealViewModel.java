package org.techtown.Checkfit.mealdatabase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import org.techtown.Checkfit.mealdatabase.MealEntity;

import org.techtown.Checkfit.mealdatabase.MealRepository;
import java.util.List;

public class MealViewModel extends AndroidViewModel {

    private MealRepository repository;
    private LiveData<List<MealEntity>> allMeals;

    public MealViewModel(@NonNull Application application) {
        super(application);
        repository = new MealRepository(application);
        allMeals = repository.getAllMeals();
    }

    public LiveData<List<MealEntity>> getAllMeals() {
        return allMeals;
    }

    public LiveData<MealEntity> getMealByDate(String date) {
        return repository.loadMealByDate(date);
    }

    public void insert(MealEntity meal) {
        repository.insert(meal);
    }

    public void update(MealEntity meal) {
        repository.update(meal);
    }

    public void delete(MealEntity meal) {
        repository.delete(meal);
    }

    public void insertOrUpdate(MealEntity meal) {
        repository.insertOrUpdate(meal);
    }
}
