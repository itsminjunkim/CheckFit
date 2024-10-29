package org.techtown.Checkfit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.Checkfit.R;
import org.techtown.Checkfit.managers.LunchOnItemClickListener;
import org.techtown.Checkfit.managers.MealManager;
import org.techtown.Checkfit.managers.mealInfoData;

import java.util.ArrayList;
import java.util.List;

public class LunchAdapter extends RecyclerView.Adapter<LunchAdapter.ViewHolder> implements LunchOnItemClickListener{
    private List<mealInfoData> lunchList;
    LunchOnItemClickListener listener;

    public LunchAdapter(List<mealInfoData> lunch){
        this.lunchList = lunch;
    }

    public void deleteItem(MealManager mealManager , int position){
        lunchList.remove(position);
        mealManager.clearMeal("Lunch" , position);
        notifyItemRemoved(position);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LunchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.mealdata , parent , false);

        return new LunchAdapter.ViewHolder(itemView , this);
    }

    @Override
    public void onBindViewHolder(@NonNull LunchAdapter.ViewHolder holder, int position) {
        mealInfoData meal = lunchList.get(position);
        holder.setItem(meal);
    }


    public void addItem(mealInfoData lunch){
        lunchList.add(lunch);
        notifyDataSetChanged();
    }

    public void setItems(ArrayList<mealInfoData> lunchList){
        this.lunchList = lunchList;
    }

    public List<mealInfoData> getItem(){
        return lunchList;
    }

    public void setItem(int position , mealInfoData item){
        lunchList.set(position , item);
    }

    @Override
    public int getItemCount() {

        return lunchList.size();
    }


    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if(listener != null){
            listener.onItemClick(holder , view , position);
        }
    }

    public void setOnItemClickListener(LunchOnItemClickListener listener){
        this.listener = listener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1;
        TextView text2;
        TextView text3;
        TextView text4;
        TextView text5;
        Button button;
        public ViewHolder(View itemView , final LunchOnItemClickListener listener){
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

        public void setItem(mealInfoData lunch){
            text1.setText(lunch.name);
            text2.setText(lunch.kcal + "kcal");
            text3.setText(lunch.carbohydrate + " g");
            text4.setText(lunch.protein + " g");
            text5.setText(lunch.fat +" g");
        }
    }
}
