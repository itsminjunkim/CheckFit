package org.techtown.Checkfit.managers;

import android.view.View;

import org.techtown.Checkfit.adapter.LunchAdapter;

public interface LunchOnItemClickListener {
    public void onItemClick(LunchAdapter.ViewHolder holder , View view , int position);
}
