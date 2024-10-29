package org.techtown.Checkfit.fragments;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import org.techtown.Checkfit.R;
import org.techtown.Checkfit.mealdatabase.MealEntity;
import org.techtown.Checkfit.mealdatabase.MealViewModel;
import org.w3c.dom.Text;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;


public class DateFragment extends Fragment {

    MealViewModel mealViewModel;
    DatePicker datePicker;
    TextView dateText, todayKcal, todayCarbohydrate, todayProtein, todayFat;
    Calendar cal;
    String todayDate;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_date, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cal = Calendar.getInstance();
        todayDate = cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE);
        dateText = view.findViewById(R.id.fragmentDateView);
        dateText.setText(cal.get(Calendar.YEAR) + "-" + (cal.get(Calendar.MONTH) + 1) + "-" + cal.get(Calendar.DATE));

        todayKcal = view.findViewById(R.id.dateTodayKcal);
        todayCarbohydrate = view.findViewById(R.id.dateTodayCarbo);
        todayProtein = view.findViewById(R.id.dateTodayProtein);
        todayFat = view.findViewById(R.id.dateTodayFat);

        mealViewModel = new ViewModelProvider(this).get(MealViewModel.class);
        datePicker = view.findViewById(R.id.datePicker);
        datePicker.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DATE),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int yy, int mm, int dd) {
                        String date = String.format("%d-%d-%d", yy, mm + 1, dd);
                        dateText.setText(date);
                        changeDataInDate(date);
                    }
                });

    }

    public void changeDataInDate(String date){
        mealViewModel.getMealByDate(todayDate).observe(getViewLifecycleOwner(), new Observer<MealEntity>() {
            @Override
            public void onChanged(MealEntity mealEntity) {
                if (mealEntity != null && mealEntity.getDate().equals(date)) {
                    todayKcal.setText(mealEntity.getTotalKcal() + " Kcal");
                    todayCarbohydrate.setText("탄수화물: " + mealEntity.getCarbohydrate() + " g");
                    todayProtein.setText("단백질: " + mealEntity.getProtein() + " g");
                    todayFat.setText("지방: " + mealEntity.getFat() + " g");
                } else {
                    todayKcal.setText("");
                    todayCarbohydrate.setText("");
                    todayProtein.setText("");
                    todayFat.setText("");
                }
            }
        });
    }
}