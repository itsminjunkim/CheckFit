package org.techtown.Checkfit.managers;

import android.content.Context;
import android.content.SharedPreferences;

public class MealManager {
    SharedPreferences sharedPreferences;

    public MealManager(Context context){
        sharedPreferences = context.getSharedPreferences("Meal_Type" , Context.MODE_PRIVATE);
    }

    public void saveMeal(String type, int index, String name, String kcal, String car, String protein, String fat){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        String baseKey = type + "_item" + index;
        editor.putString(baseKey + "_name" , name);
        editor.putString(baseKey + "_kcal" , kcal);
        editor.putString(baseKey + "_car" , car);
        editor.putString(baseKey + "_protein" , protein);
        editor.putString(baseKey + "_fat" , fat);
        editor.apply();
    }


    public mealInfoData loadMeal(String mealType , int index){
        String baseKey = mealType + "_item" + index;
        String name = sharedPreferences.getString(baseKey + "_name" , "");
        String kcal = sharedPreferences.getString(baseKey + "_kcal" , "");
        String carbo = sharedPreferences.getString(baseKey + "_car" , "");
        String protein = sharedPreferences.getString(baseKey + "_protein" , "");
        String fat = sharedPreferences.getString(baseKey + "_fat" , "");

        if (name.isEmpty() && kcal.isEmpty() && carbo.isEmpty() && protein.isEmpty() && fat.isEmpty()) {
            return null;
        }

        return new mealInfoData(name , kcal , carbo , protein , fat);
    }

    public String getMeal(String mealType , int index , String type){
        String baseKey = mealType + "_item" + index + type;
        return sharedPreferences.getString(baseKey , "");
    }

    public void saveItemCount(String type , int index){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(type + "Count" , index);
        editor.apply();
    }

    public int getItemCount(String type){
        return sharedPreferences.getInt(type + "Count" , 0);
    }

    public void clearMeal(String mealType, int index){
        SharedPreferences.Editor editor = sharedPreferences.edit();

        String baseKey = mealType + "_item" + index;
        editor.remove(baseKey + "_name");
        editor.remove(baseKey + "_kcal");
        editor.remove(baseKey + "_car");
        editor.remove(baseKey + "_protein");
        editor.remove(baseKey + "_fat");
        editor.apply();

        int itemCount = getItemCount(mealType);
        for (int i = index; i < itemCount - 1; i++) {
            mealInfoData nextMeal = loadMeal(mealType, i + 1);
            saveMeal(mealType, i, nextMeal.name, nextMeal.kcal, nextMeal.carbohydrate, nextMeal.protein, nextMeal.fat);
        }

        editor.remove(mealType + "_item" + (itemCount - 1) + "_name");
        editor.remove(mealType + "_item" + (itemCount - 1) + "_kcal");
        editor.remove(mealType + "_item" + (itemCount - 1) + "_car");
        editor.remove(mealType + "_item" + (itemCount - 1) + "_protein");
        editor.remove(mealType + "_item" + (itemCount - 1) + "_fat");
        editor.apply();

        saveItemCount(mealType, itemCount - 1);
    }

    public void clearData(){
        sharedPreferences.edit().clear().apply();
    }

    public int mealTotal(String mealType, String type , int itemCount){
        int totalKcal = 0;
        String baseKey = mealType + "_item";
        for(int i = 0; i < itemCount; i++){
            String kcalStr = sharedPreferences.getString(baseKey + i + type, "0");
            try {
                int kcal = Integer.parseInt(kcalStr);
                totalKcal += kcal;
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return totalKcal;
    }

    public int todayTotal(String type){
        if(sharedPreferences == null){
            return 0;
        }
        int bCount = getItemCount("Breakfast");
        int lCount = getItemCount("Lunch");
        int dCount = getItemCount("Dinner");

        int breakfast = mealTotal("Breakfast", type, bCount);
        int lunch = mealTotal("Lunch", type, lCount);
        int dinner = mealTotal("Dinner", type, dCount);

        return breakfast + lunch + dinner;
    }
}
