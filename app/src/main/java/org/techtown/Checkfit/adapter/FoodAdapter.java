package org.techtown.Checkfit.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.techtown.Checkfit.managers.BreakFastOnItemClickListener;
import org.techtown.Checkfit.managers.FoodInfo;
import org.techtown.Checkfit.R;
import org.techtown.Checkfit.managers.LunchOnItemClickListener;

import java.util.ArrayList;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.ViewHolder> {

    ArrayList<FoodInfo> items = new ArrayList<FoodInfo>();

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View itemView = inflater.inflate(R.layout.mealdata , parent , false);

        return new ViewHolder(itemView);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        FoodInfo food = items.get(position);
        holder.setItem(food);
    }

    public void addItem(FoodInfo item){
        items.add(item);
        notifyDataSetChanged();
    }

    public void setItems(ArrayList<FoodInfo> items){
        this.items = items;
    }

    public FoodInfo getItem(int position){
        return items.get(position);
    }

    public void setItem(int position , FoodInfo item){
        items.set(position , item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView text1;
        TextView text2;
        TextView text3;
        TextView text4;
        TextView text5;

        public ViewHolder(View itemView){
            super(itemView);

            text1 = itemView.findViewById(R.id.foodname_text);
            text2 = itemView.findViewById(R.id.kcal_text);
            text3 = itemView.findViewById(R.id.carbo_text);
            text4 = itemView.findViewById(R.id.protein_text);
            text5 = itemView.findViewById(R.id.fat_text);
        }

        public void setItem(FoodInfo item){
            text1.setText(item.getName());
            text2.setText(item.getKcal());
            text3.setText(item.getCarbohydrate());
            text4.setText(item.getProtein());
            text5.setText(item.getFat());
        }
    }
}
