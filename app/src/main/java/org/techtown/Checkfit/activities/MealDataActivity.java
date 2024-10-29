package org.techtown.Checkfit.activities;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.Checkfit.managers.DinnerOnItemClickListener;
import org.techtown.Checkfit.managers.LunchOnItemClickListener;
import org.techtown.Checkfit.managers.MealManager;
import org.techtown.Checkfit.managers.BreakFastOnItemClickListener;
import org.techtown.Checkfit.R;
import org.techtown.Checkfit.adapter.BreakfastAdapter;
import org.techtown.Checkfit.adapter.DinnerAdapter;
import org.techtown.Checkfit.adapter.LunchAdapter;
import org.techtown.Checkfit.managers.mealInfoData;
import org.techtown.Checkfit.managers.AlarmUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MealDataActivity extends AppCompatActivity {
    String mealType;
    MealManager mealManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meal_data);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        mealManager = new MealManager(this);

        List<mealInfoData> breakfast = new ArrayList<>();
        for(int i = 0; ; i++){
            mealInfoData mealData = mealManager.loadMeal("Breakfast", i);
            if (mealData == null) break;
            breakfast.add(mealData);
        }
        List<mealInfoData> lunch = new ArrayList<>();
        for(int i = 0; ; i++){
            mealInfoData mealData = mealManager.loadMeal("Lunch", i);
            if (mealData == null) break;
            lunch.add(mealData);
        }
        List<mealInfoData> dinner = new ArrayList<>();
        for(int i = 0; ; i++){
            mealInfoData mealData = mealManager.loadMeal("Dinner", i);
            if (mealData == null) break;
            dinner.add(mealData);
        }

        mealManager.saveItemCount("Breakfast" , breakfast.size());
        mealManager.saveItemCount("Lunch" , lunch.size());
        mealManager.saveItemCount("Dinner" , dinner.size());

        RecyclerView recyclerView = findViewById(R.id.food_recyclerview);
        mealType = getIntent().getStringExtra("mealType");
        TextView textView = findViewById(R.id.mealTimeText);

        if(mealType.equals("breakfast")){
            textView.setText("아침");
        } else if (mealType.equals("lunch")) {
            textView.setText("점심");
        } else if (mealType.equals("dinner")) {
            textView.setText("저녁");
        }

        EditText nameInput = findViewById(R.id.foodname_input);
        EditText kcalInput = findViewById(R.id.kcal_input);
        EditText carboInput = findViewById(R.id.carbo_input);
        EditText proteinInput = findViewById(R.id.protein_input);
        EditText fatInput = findViewById(R.id.fat_input);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this , LinearLayoutManager.VERTICAL , false);
        recyclerView.setLayoutManager(layoutManager);

        BreakfastAdapter breakfastAdapter = new BreakfastAdapter(breakfast);
        breakfastAdapter.setOnItemClickListener(new BreakFastOnItemClickListener() {
            @Override
            public void onItemClick(BreakfastAdapter.ViewHolder holder, View view, int position) {
                breakfastAdapter.deleteItem(mealManager , position);
            }
        });

        LunchAdapter lunchAdapter = new LunchAdapter(lunch);
        lunchAdapter.setOnItemClickListener(new LunchOnItemClickListener() {
            @Override
            public void onItemClick(LunchAdapter.ViewHolder holder, View view, int position) {
                lunchAdapter.deleteItem(mealManager , position);
            }
        });
        DinnerAdapter dinnerAdapter = new DinnerAdapter(dinner);
        dinnerAdapter.setOnItemClickListener(new DinnerOnItemClickListener() {
            @Override
            public void onItemClick(DinnerAdapter.ViewHolder holder, View view, int position) {
                dinnerAdapter.deleteItem(mealManager , position);
            }
        });

        switch (mealType) {
            case "breakfast":
                recyclerView.setAdapter(breakfastAdapter);
                break;
            case "lunch":
                recyclerView.setAdapter(lunchAdapter);
                break;
            case "dinner":
                recyclerView.setAdapter(dinnerAdapter);
                break;
        }

        Button inputButton = findViewById(R.id.plusButton);
        inputButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mealInfoData data = new mealInfoData(
                        nameInput.getText().toString(),
                        kcalInput.getText().toString(),
                        carboInput.getText().toString(),
                        proteinInput.getText().toString(),
                        fatInput.getText().toString()
                );
                switch (mealType) {
                    case "breakfast":
                        breakfastAdapter.addItem(data);
                        break;
                    case "lunch":
                        lunchAdapter.addItem(data);
                        break;
                    case "dinner":
                        dinnerAdapter.addItem(data);
                        break;
                }
                
            }
        });
        Button endButton = findViewById(R.id.endButton);
        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<mealInfoData> mealList = Collections.emptyList();
                Intent intent = new Intent();
                switch (mealType) {
                    case "breakfast":
                       mealList = breakfastAdapter.getItem();
                        break;
                    case "lunch":
                        mealList = lunchAdapter.getItem();
                        break;
                    case "dinner":
                        mealList = dinnerAdapter.getItem();
                        break;
                }
                intent.putExtra("mealType" , mealType);
                intent.putParcelableArrayListExtra("data", new ArrayList<>(mealList));
                setResult(RESULT_OK , intent);
                finish();
            }
        });
    }

}

