package org.techtown.Checkfit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.Checkfit.managers.MealManager;
import org.techtown.Checkfit.managers.BreakFastOnItemClickListener;
import org.techtown.Checkfit.R;
import org.techtown.Checkfit.managers.mealInfoData;

import java.util.ArrayList;
import java.util.List;

public class BreakfastAdapter extends RecyclerView.Adapter<BreakfastAdapter.ViewHolder> implements BreakFastOnItemClickListener {
    private List<mealInfoData> breakfastList;
    BreakFastOnItemClickListener listener;

    public BreakfastAdapter(List<mealInfoData> breakfast){
        this.breakfastList = breakfast;
    }

    public void deleteItem(MealManager mealManager , int position){
        breakfastList.remove(position);
        mealManager.clearMeal("Breakfast" , position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BreakfastAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.mealdata , parent , false);

        return new BreakfastAdapter.ViewHolder(itemView , this);
    }

    @Override
    public void onBindViewHolder(@NonNull BreakfastAdapter.ViewHolder holder, int position) {
        mealInfoData meal = breakfastList.get(position);
        holder.setItem(meal);
    }


    public void addItem(mealInfoData breakfast){
        breakfastList.add(breakfast);
        notifyDataSetChanged();
    }

    public mealInfoData getgetItem(int position){
        return breakfastList.get(position);
    }

    public void setItems(ArrayList<mealInfoData> breakfastList){
        this.breakfastList = breakfastList;
    }

    public List<mealInfoData> getItem(){
        return breakfastList;
    }

    public void setItem(int position , mealInfoData item){
        breakfastList.set(position , item);
    }

    @Override
    public int getItemCount() {

        return breakfastList.size();
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder , view , position);
        }
    }

    public void setOnItemClickListener(BreakFastOnItemClickListener listener){
        this.listener = listener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1;
        TextView text2;
        TextView text3;
        TextView text4;
        TextView text5;
        Button button;
        public ViewHolder(View itemView , final BreakFastOnItemClickListener listener){
            super(itemView);

            text1 = itemView.findViewById(R.id.foodname_text);
            text2 = itemView.findViewById(R.id.kcal_text);
            text3 = itemView.findViewById(R.id.carbo_text);
            text4 = itemView.findViewById(R.id.protein_text);
            text5 = itemView.findViewById(R.id.fat_text);
            button = itemView.findViewById(R.id.deleteButton);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(listener != null){
                        listener.onItemClick(ViewHolder.this , button , position);
                    }
                }
            });
        }

        public void setItem(mealInfoData breakfast){
            text1.setText(breakfast.name);
            text2.setText(breakfast.kcal + "kcal");
            text3.setText(breakfast.carbohydrate + " g");
            text4.setText(breakfast.protein + " g");
            text5.setText(breakfast.fat +" g");
        }


    }

}
