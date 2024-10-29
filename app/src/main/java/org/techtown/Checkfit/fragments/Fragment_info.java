package org.techtown.Checkfit.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.ekn.gruzer.gaugelibrary.ArcGauge;
import com.ekn.gruzer.gaugelibrary.Range;

import org.techtown.Checkfit.R;
import org.techtown.Checkfit.activities.RegisterActivity;
import org.techtown.Checkfit.managers.UserManager;

import java.util.Objects;

public class Fragment_info extends Fragment {
    public static final int USER_INFO_CODE = 11;

    private TextView name;
    private TextView height;
    private TextView kg;
    private TextView bmr;
    private UserManager userManager;
    private ArcGauge arcGauge;
    private Double bmiValue;
    private TextView bmi;
    private TextView bmiValueText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        name = view.findViewById(R.id.info_name);
        height = view.findViewById(R.id.info_height);
        kg = view.findViewById(R.id.info_kg);
        bmi = view.findViewById(R.id.bmiText);
        bmiValueText = view.findViewById(R.id.bmi);
        bmr = view.findViewById(R.id.bmr);
        arcGauge = view.findViewById(R.id.arcGauge);
        userManager = new UserManager(view.getContext());

        String heightStr = userManager.getHeight();
        String kgStr = userManager.getKg();

        if (heightStr != null && !heightStr.isEmpty() && kgStr != null && !kgStr.isEmpty()) {
            double bmiValue = getBmi(Integer.parseInt(heightStr), Integer.parseInt(kgStr));
            arcGauge.setValue(bmiValue);
            getBmiResult(bmiValue);
            setupArcGauge();
        } else {
            arcGauge.setValue(0.0);
            bmi.setText("BMI 값이 없습니다.");
        }

        name.setText(userManager.getUsername());
        height.setText(heightStr + "cm");
        kg.setText(kgStr + "kg");
        bmr.setText("기초대사량 : " + userManager.getBmr() + "kcal");

        Button button = view.findViewById(R.id.profileButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), RegisterActivity.class);
                startActivityForResult(intent, USER_INFO_CODE);
            }
        });

    }

    private void setupArcGauge() {
        Range range = new Range();
        range.setColor(Color.parseColor("#00b20b"));
        range.setFrom(0.0);
        range.setTo(25.0);

        Range range2 = new Range();
        range2.setColor(Color.parseColor("#E3E500"));
        range2.setFrom(25.0);
        range2.setTo(35.0);

        Range range3 = new Range();
        range3.setColor(Color.parseColor("#ce0000"));
        range3.setFrom(35.0);
        range3.setTo(50.0);

        arcGauge.addRange(range);
        arcGauge.addRange(range2);
        arcGauge.addRange(range3);

        arcGauge.setMinValue(0.0);
        arcGauge.setMaxValue(50.0);
        arcGauge.setValue(getBmi(Integer.parseInt(userManager.getHeight()), Integer.parseInt(userManager.getKg())));
    }

    private void updateUserInfo() {
        bmiValue = getBmi(Integer.parseInt(userManager.getHeight()), Integer.parseInt(userManager.getKg()));
        getBmr(userManager.getSex(), Integer.parseInt(userManager.getHeight()), Integer.parseInt(userManager.getKg()), Integer.parseInt(userManager.getAge()));
        name.setText(userManager.getUsername());
        height.setText(userManager.getHeight() + "cm");
        kg.setText(userManager.getKg() + "kg");
        bmiValueText.setText("BMI : " + bmiValue);
        bmr.setText("기초대사량 : " + userManager.getBmr() + "kcal");
        getBmiResult(bmiValue);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);  // 기본 동작 유지
        if (resultCode == AppCompatActivity.RESULT_OK && requestCode == USER_INFO_CODE) {
            updateUserInfo();
        }
    }

    public double getBmi(int height, int kg) {
        float changeHeight = (float) height / 100;
        float bmi = kg / (changeHeight * changeHeight);
        return (double) Math.round(bmi * 10) / 10;
    }

    public void getBmiResult(double bmiValue) {
        if (bmiValue < 18.5) {
            bmi.setText("저체중");
        } else if (bmiValue < 23) {
            bmi.setText("정상");
        } else if (bmiValue < 25) {
            bmi.setText("과체중");
        } else if (bmiValue < 30) {
            bmi.setText("비만");
        } else {
            bmi.setText("고도비만");
        }
    }

    public void getBmr(String sex, int height, int kg, int age) {
        double bmr;
        if (Objects.equals(sex, "man")) {
            bmr = 66 + (13.7 * kg) + (5 * height) - (6.8 * age);
        } else {
            bmr = 655.1 + (9.563 * kg) + (1.85 * height) - (4.676 * age);
        }
        float flbmr = (float) bmr;
        userManager.userKcalSave(flbmr);
    }
}


