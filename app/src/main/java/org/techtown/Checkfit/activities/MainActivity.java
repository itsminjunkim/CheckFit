package org.techtown.Checkfit.activities;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


import com.google.android.material.bottomnavigation.BottomNavigationView;

import org.techtown.Checkfit.R;
import org.techtown.Checkfit.fragments.Fragment_info;
import org.techtown.Checkfit.fragments.Fragment_meal;
import org.techtown.Checkfit.fragments.Fragment_workout;
import org.techtown.Checkfit.managers.AlarmUtil;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_CODE_MEAL = 101;

    TextView maintext;
    Fragment_meal fragmentMeal;
    Fragment_workout fragmentWorkout;
    Fragment_info fragmentInfo;
    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AlarmUtil.resetAlarm(this);
        checkPermission();


        maintext = findViewById(R.id.maintext);
        fragmentMeal = new Fragment_meal();
        fragmentWorkout = new Fragment_workout();
        fragmentInfo = new Fragment_info();

        bottomNavigationView = findViewById(R.id.bottom_navigationview);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment blankfragment = null;
                if(item.getItemId() == R.id.meal_menu){
                    blankfragment = fragmentMeal;
                    maintext.setText("식단");
                } else if (item.getItemId() == R.id.workout_menu) {
                    blankfragment = fragmentWorkout;
                    maintext.setText("운동");
                } else if (item.getItemId() == R.id.myinfo_menu) {
                    blankfragment = fragmentInfo;
                    maintext.setText("MY");
                }
                changedFragment(blankfragment);
                return true;
            }
        });
        bottomNavigationView.setSelectedItemId(R.id.meal_menu);

    }

    private void changedFragment (Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_container , fragment).commit();
    }

    public void showFragment(Fragment fragment){
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container , fragment)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit();
    }

    public void checkPermission(){
        if(ContextCompat.checkSelfPermission(this , Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this , Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this , Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(this , PermissionCheckActivity.class);
            startActivity(intent);
            finish();
        }
    }
}

