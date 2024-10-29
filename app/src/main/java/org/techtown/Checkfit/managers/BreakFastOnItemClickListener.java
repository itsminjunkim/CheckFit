package org.techtown.Checkfit.managers;

import android.view.View;

import org.techtown.Checkfit.adapter.BreakfastAdapter;

public interface BreakFastOnItemClickListener {
    public void onItemClick(BreakfastAdapter.ViewHolder holder , View view , int position);
}
