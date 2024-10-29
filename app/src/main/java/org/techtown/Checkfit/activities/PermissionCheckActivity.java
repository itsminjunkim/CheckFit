package org.techtown.Checkfit.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.techtown.Checkfit.R;

public class PermissionCheckActivity extends AppCompatActivity {

    Switch alarmSwitch;
    Switch physicalSwitch;
    Switch locationSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_permission_check);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        alarmSwitch = findViewById(R.id.alarmSwitch);
        physicalSwitch = findViewById(R.id.physicalSwitch);
        locationSwitch = findViewById(R.id.locationSwitch);

        alarmSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission(Manifest.permission.POST_NOTIFICATIONS);
            }
        });

        physicalSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission(Manifest.permission.ACTIVITY_RECOGNITION);
            }
        });

        locationSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission(Manifest.permission.ACCESS_FINE_LOCATION);
            }
        });

        Button button = findViewById(R.id.permissionCheckButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // 각 권한을 요청하는 메서드
    private void requestPermission(String permission) {
        // 권한이 이미 허용되어 있는지 체크
        if (ContextCompat.checkSelfPermission(this, permission) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this,  " 권한이 이미 허용되어 있습니다.", Toast.LENGTH_SHORT).show();
        } else {
            ActivityCompat.requestPermissions(PermissionCheckActivity.this, new String[]{permission}, 1);
        }
    }

}
