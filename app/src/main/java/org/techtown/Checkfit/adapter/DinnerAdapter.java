package org.techtown.Checkfit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.Checkfit.R;
import org.techtown.Checkfit.managers.BreakFastOnItemClickListener;
import org.techtown.Checkfit.managers.DinnerOnItemClickListener;
import org.techtown.Checkfit.managers.MealManager;
import org.techtown.Checkfit.managers.mealInfoData;

import java.util.ArrayList;
import java.util.List;

public class DinnerAdapter extends RecyclerView.Adapter<DinnerAdapter.ViewHolder> implements DinnerOnItemClickListener {
    private List<mealInfoData> dinnerList;
    DinnerOnItemClickListener listener;

    public DinnerAdapter(List<mealInfoData> dinner){
        this.dinnerList = dinner;
    }

    public void deleteItem(MealManager mealManager , int position){
        dinnerList.remove(position);
        mealManager.clearMeal("Dinner" , position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DinnerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.mealdata , parent , false);

        return new DinnerAdapter.ViewHolder(itemView , this);
    }

    @Override
    public void onBindViewHolder(@NonNull DinnerAdapter.ViewHolder holder, int position) {
        mealInfoData meal = dinnerList.get(position);
        holder.setItem(meal);
    }


    public void addItem(mealInfoData dinner){
        dinnerList.add(dinner);
        notifyDataSetChanged();
    }

    public void setOnItemClickListener(DinnerOnItemClickListener listener){
        this.listener = listener;
    }

    public void setItems(ArrayList<mealInfoData> dinnerList){
        this.dinnerList = dinnerList;
    }

    public List<mealInfoData> getItem(){
        return dinnerList;
    }

    public void setItem(int position , mealInfoData item){
        dinnerList.set(position , item);
    }

    @Override
    public int getItemCount() {

        return dinnerList.size();
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder , view , position);
        }
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1;
        TextView text2;
        TextView text3;
        TextView text4;
        TextView text5;
        Button button;

        public ViewHolder(View itemView , final DinnerOnItemClickListener listener){
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

        public void setItem(mealInfoData dinner){
            text1.setText(dinner.name);
            text2.setText(dinner.kcal + "kcal");
            text3.setText(dinner.carbohydrate + " g");
            text4.setText(dinner.protein + " g");
            text5.setText(dinner.fat +" g");
        }
    }
}
