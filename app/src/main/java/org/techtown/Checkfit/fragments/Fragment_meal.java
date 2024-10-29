package org.techtown.Checkfit.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.type.Date;

import org.techtown.Checkfit.activities.MainActivity;
import org.techtown.Checkfit.activities.MealDataActivity;
import org.techtown.Checkfit.managers.MealManager;
import org.techtown.Checkfit.R;
import org.techtown.Checkfit.managers.ResetTodayData;
import org.techtown.Checkfit.managers.mealInfoData;
import org.techtown.Checkfit.mealdatabase.MealEntity;
import org.techtown.Checkfit.mealdatabase.MealViewModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class Fragment_meal extends Fragment {



    TextView morningKcal;
    TextView lunchKcal;
    TextView dinnerKcal;
    TextView today;
    TextView date;
    TextView todayKcal , todayCarbohydrate , todayProtein , todayFat;
    MealManager mealManager;
    MealViewModel mealViewModel;
    Fragment dateFragment;
    private String todayDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_meal, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        View breakfastView= view.findViewById(R.id.breakfast);
        View lunchView = view.findViewById(R.id.lunch);
        View dinnerView = view.findViewById(R.id.dinner);
        View dateView = view.findViewById(R.id.dateView);

        dateFragment = new DateFragment();
        mealManager = new MealManager(getContext());
        mealViewModel = new ViewModelProvider(this).get(MealViewModel.class);
        date = view.findViewById(R.id.dateText);
        morningKcal = getView().findViewById(R.id.morningKcalText);
        lunchKcal = getView().findViewById(R.id.lunchKcalText);
        dinnerKcal = getView().findViewById(R.id.dinnerKcalText);
        today = getView().findViewById(R.id.today);
        todayKcal = getView().findViewById(R.id.todayKcal);
        todayCarbohydrate = getView().findViewById(R.id.todayCar);
        todayProtein = getView().findViewById(R.id.todayProtein);
        todayFat = getView().findViewById(R.id.todayFat);

        Calendar cal = Calendar.getInstance();
        date.setText(cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE));
        todayDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE);

        breakfastView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(getActivity(), MealDataActivity.class);
                intent.putExtra("mealType", "breakfast");
                startActivityForResult(intent, 1);
                return false;
            }
        });
        lunchView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                    Intent intent = new Intent(getActivity(), MealDataActivity.class);
                    intent.putExtra("mealType", "lunch");
                    startActivityForResult(intent, 2);
                }
                return true;
            }
        });
        dinnerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                Intent intent = new Intent(getActivity(), MealDataActivity.class);
                intent.putExtra("mealType", "dinner");
                startActivityForResult(intent, 3);
                return false;
            }
        });
        dateView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                ((MainActivity) getActivity()).showFragment(dateFragment);
                return true;
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == AppCompatActivity.RESULT_OK && data != null){
            String mealType = data.getStringExtra("mealType");
            Bundle bundle = data.getExtras();
            ArrayList<mealInfoData> mealData = bundle.getParcelableArrayList("data");
            if(mealData != null) {
                for (int i = 0; i < mealData.size(); i++) {
                    mealInfoData meal = mealData.get(i);
                    if (mealType.equals("breakfast")) {
                        mealManager.saveMeal("Breakfast", i, meal.name, meal.kcal, meal.carbohydrate, meal.protein, meal.fat);
                    } else if (mealType.equals("lunch")) {
                        mealManager.saveMeal("Lunch", i, meal.name, meal.kcal, meal.carbohydrate, meal.protein, meal.fat);
                    } else if (mealType.equals("dinner")) {
                        mealManager.saveMeal("Dinner", i, meal.name, meal.kcal, meal.carbohydrate, meal.protein, meal.fat);
                    }
                }
                updateMeal();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        updateMeal();
    }

    public void updateMeal(){
        if(mealManager != null) {
            morningKcal.setText(String.valueOf(mealManager.mealTotal("Breakfast" , "_kcal" ,mealManager.getItemCount("Breakfast"))) + " Kcal");
            lunchKcal.setText(String.valueOf(mealManager.mealTotal("Lunch" , "_kcal" ,mealManager.getItemCount("Lunch")))+ " Kcal");
            dinnerKcal.setText(String.valueOf(mealManager.mealTotal("Dinner" , "_kcal" ,mealManager.getItemCount("Dinner")))+ " Kcal");
            todayKcal.setText(String.valueOf(mealManager.todayTotal("_kcal")) + "Kcal");
            todayCarbohydrate.setText("탄수화물 :" + String.valueOf(mealManager.todayTotal("_car") + " g"));
            todayProtein.setText("단백질 :" + String.valueOf(mealManager.todayTotal("_protein")) + " g");
            todayFat.setText("지방 :" + String.valueOf(mealManager.todayTotal("_fat")) + " g");

            saveTodayMealInDb();
        } else {
            morningKcal.setText("");
            lunchKcal.setText("");
            dinnerKcal.setText("");
            today.setText("");
        }
    }

    public void saveTodayMealInDb(){
        MealEntity mealEntity = new MealEntity();
        if(mealManager != null) {
            mealEntity.date = todayDate;
            mealEntity.totalKcal = mealManager.todayTotal("_kcal");
            mealEntity.carbohydrate = mealManager.todayTotal("_car");
            mealEntity.protein = mealManager.todayTotal("_protein");
            mealEntity.fat = mealManager.todayTotal("_fat");

            mealViewModel.insertOrUpdate(mealEntity);
        }
    }
}