package org.techtown.Checkfit.managers;

import android.content.Context;
import android.content.SharedPreferences;

public class UserManager {
    SharedPreferences sharedPreferences;

    public UserManager(Context context){
        sharedPreferences = context.getSharedPreferences("context", Context.MODE_PRIVATE);
    }

    public void userSave(String username, String height, String kg, String age, String sex){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("age" , age);
        editor.putString("username" , username);
        editor.putString("height" , height);
        editor.putString("kg" , kg);
        editor.putString("sex" , sex);
        editor.apply();
    }
    public void userKcalSave(float bmr){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat("bmr" , bmr);
        editor.apply();
    }
    public void saveStep(String type , int step){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(type + "Step" , step);
        editor.apply();
    }
//    public void saveStepPercent(float percent){
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putFloat("stepPercent" , percent);
//        editor.apply();
//    }
//    public int getStepPercent(){
//        int percent;
//        percent = (int) sharedPreferences.getFloat("stepPercent" , 0);
//        return percent;
//    }
    public void clearStep(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("initialStep");
        editor.remove("currentStep");
        editor.apply();
    }

    public int getStep(String type){
        return sharedPreferences.getInt(type + "Step" , -1);
    }
    public float getBmr(){
        return sharedPreferences.getFloat("bmr" , 0);
    }
    public String getSex(){
        return sharedPreferences.getString("sex" , "");
    }
    public String getUsername(){
        return sharedPreferences.getString("username" , "사용자");
    }
    public String getHeight(){
        return sharedPreferences.getString("height" , "");
    }
    public String getKg() {
        return sharedPreferences.getString("kg" , "");
    }
    public String getAge() {
        return sharedPreferences.getString("age" , "");
    }

    public void clearUser(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
    
    public boolean userLoginCheck(){
        return getUsername() != null && getHeight() != null && getKg() != null;
    }
}
