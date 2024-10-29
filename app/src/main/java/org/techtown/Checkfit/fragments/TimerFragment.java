package org.techtown.Checkfit.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.techtown.Checkfit.activities.MainActivity;
import org.techtown.Checkfit.R;
import org.techtown.Checkfit.managers.UserManager;

import java.util.Timer;
import java.util.TimerTask;

public class TimerFragment extends Fragment implements SensorEventListener {

    private static final int STEPPER_RECOGNITION_CODE = 1;
    private SensorManager sensorManager;
    private Sensor stepCountSensor;
    private EditText setInput, workoutTimeInput, restTimeInput;
    private TextView minText, secText, setCheck, stepCount , stepPercentText;
    private Button startButton, skipButton, endButton , findGymButton;
    private ProgressBar progressBar;

    private int set, workoutMin, restMin, sec;
    private Timer timer;
    private TimerTask timerTask;
    private Handler handler = new Handler(Looper.getMainLooper());
    private int initialStepCount = -1;

    UserManager userManager;
    int fullStep = 10000;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_timer, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setInput = view.findViewById(R.id.setInput);
        workoutTimeInput = view.findViewById(R.id.workoutTimeInput);
        restTimeInput = view.findViewById(R.id.restTimeInput);
        minText = view.findViewById(R.id.min);
        secText = view.findViewById(R.id.sec);
        stepCount = view.findViewById(R.id.stepCounterText);
        skipButton = view.findViewById(R.id.skipButton);
        endButton = view.findViewById(R.id.endButton);
        stepPercentText = view.findViewById(R.id.steppercent);
        startButton = view.findViewById(R.id.startButton);
        findGymButton = view.findViewById(R.id.findGymButton);
        setCheck = view.findViewById(R.id.setCheck);
        progressBar = view.findViewById(R.id.progressBar);
        userManager = new UserManager(getContext());
        progressBar.setScaleY(5f);

        if(ContextCompat.checkSelfPermission(getContext(), Manifest.permission.ACTIVITY_RECOGNITION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.ACTIVITY_RECOGNITION}, STEPPER_RECOGNITION_CODE);
        }

        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        if(sensorManager != null){
            stepCountSensor = sensorManager.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        } else {
            Toast.makeText(getContext(), "No sensor in device", Toast.LENGTH_SHORT).show();
        }

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    set = Integer.parseInt(setInput.getText().toString());
                    workoutMin = Integer.parseInt(workoutTimeInput.getText().toString());
                    restMin = Integer.parseInt(restTimeInput.getText().toString());
                    sec = 0;
                } catch (NumberFormatException e) {
                    Toast.makeText(getContext(), "Invalid input values", Toast.LENGTH_SHORT).show();
                    return;
                }
                startTimer();
            }
        });

        endButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timer != null) {
                    timer.cancel();
                }
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (timer != null) {
                    timer.cancel();
                }
                if (workoutMin > 0 || sec > 0) {
                    workoutMin = 0;
                    sec = 0;
                } else if (restMin > 0 || sec > 0) {
                    restMin = 0;
                    sec = 0;
                }
                updateTimer();
            }
        });

        findGymButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((MainActivity) getActivity()).showFragment(new GymMapFragment());
            }
        });
    }

    private void startTimer() {
        if (timer != null) {
            timer.cancel();
        }
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        updateTimer();
                    }
                });
            }
        };
        timer.schedule(timerTask, 0, 1000);
    }

    private void updateTimer() {
        if (workoutMin > 0 || sec > 0) {
            if (sec == 0) {
                workoutMin--;
                sec = 59;
            } else {
                sec--;
            }
        } else if (restMin > 0 || sec > 0) {
            if (sec == 0) {
                restMin--;
                sec = 59;
            } else {
                sec--;
            }
            setCheck.setText("휴식");
        } else {
            if (set > 1) {
                set--;
                workoutMin = Integer.parseInt(workoutTimeInput.getText().toString());
                restMin = Integer.parseInt(restTimeInput.getText().toString());
                sec = 0;
                setCheck.setText((Integer.parseInt(setInput.getText().toString()) - set) + " 번째 세트");
            } else {
                timer.cancel();
                setCheck.setText("완료");
                return;
            }
        }

        secText.setText(String.valueOf(sec));
        minText.setText(String.valueOf(workoutMin > 0 ? workoutMin : (restMin > 0 ? restMin : 0)));
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        if (sensorEvent.sensor.getType() == Sensor.TYPE_STEP_COUNTER){
            int totalSteps = (int) sensorEvent.values[0];
            if (initialStepCount == -1) {
                initialStepCount = totalSteps;
                userManager.saveStep("initial" , initialStepCount);
            }
            int currentSteps = totalSteps - initialStepCount;
            float stepPercent = (float) (currentSteps / fullStep) * 100;

            stepCount.setText("" + currentSteps);
            stepPercentText.setText("" + stepPercent + "%");
            progressBar.setProgress(currentSteps);
            userManager.saveStep("current" , currentSteps);

        }
    }

    @Override
    public void onStart() {
        super.onStart();

        initialStepCount = userManager.getStep("initial");
        int saveSteps = userManager.getStep("current");
        if(saveSteps != -1){
            stepCount.setText("" + saveSteps);
            progressBar.setProgress(saveSteps);
        }

        if(stepCountSensor != null){
            sensorManager.registerListener(this, stepCountSensor, SensorManager.SENSOR_DELAY_FASTEST);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }


}
