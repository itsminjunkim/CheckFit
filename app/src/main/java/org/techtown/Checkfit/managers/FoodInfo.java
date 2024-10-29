package org.techtown.Checkfit.managers;

public class FoodInfo {
    String name;
    String kcal;
    String carbohydrate;
    String protein;
    String fat;

    public FoodInfo (String name , String kcal , String carbohydrate , String protein , String fat){
        this.name = name;
        this.kcal = kcal;
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
    }

    public String getName(){
        return name;
    }

    public String getKcal(){
        return kcal;
    }

    public String getCarbohydrate(){
        return carbohydrate;
    }

    public String getProtein(){
        return protein;
    }

    public String getFat(){
        return fat;
    }

    public void setName(String name){
        this.name = name;
    }
    public void setKcal(String kcal){
        this.kcal = kcal;
    }
    public void setCarbohydrate(String carbohydrate){
        this.carbohydrate = carbohydrate;
    }
    public void setProtein(String protein){
        this.protein = protein;
    }
    public void setFat(String fat){
        this.fat = fat;
    }
}
