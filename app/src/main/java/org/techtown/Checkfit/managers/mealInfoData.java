package org.techtown.Checkfit.managers;

import android.os.Parcelable;
import android.os.Parcel;

public class mealInfoData implements Parcelable {
    public String name;
    public String kcal;
    public String carbohydrate;
    public String protein;
    public String fat;

    public mealInfoData (String mealName , String cal , String tan , String dan , String ji){
         name = mealName;
         kcal = cal;
         carbohydrate = tan;
         protein = dan;
         fat = ji;
    }

    public mealInfoData(Parcel src){
        name = src.readString();
        kcal = src.readString();
        carbohydrate = src.readString();
        protein = src.readString();
        fat = src.readString();
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {

        @Override
        public Object createFromParcel(Parcel in) {
            return new mealInfoData(in);
        }

        @Override
        public Object[] newArray(int size) {
            return new mealInfoData[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public void writeToParcel (Parcel dest , int flags){
        dest.writeString(name);
        dest.writeString(kcal);
        dest.writeString(carbohydrate);
        dest.writeString(protein);
        dest.writeString(fat);
    }
}

