package org.techtown.Checkfit.managers;

import android.view.View;

import org.techtown.Checkfit.adapter.DinnerAdapter;

public interface DinnerOnItemClickListener {
    public void onItemClick(DinnerAdapter.ViewHolder holder , View view , int position);
}
