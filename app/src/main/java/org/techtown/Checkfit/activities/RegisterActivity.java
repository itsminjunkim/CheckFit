package org.techtown.Checkfit.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.techtown.Checkfit.R;
import org.techtown.Checkfit.managers.UserManager;

public class RegisterActivity extends AppCompatActivity {
    EditText name, kg, height , age;
    RadioButton man , woman;
    String sexCheck = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        UserManager userManager = new UserManager(this);


        man = (RadioButton) findViewById(R.id.radioButtonMan);
        woman = (RadioButton) findViewById(R.id.radioButtonGirl);
        man.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sexCheck = "man";
            }
        });
        woman.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sexCheck = "woman";
            }
        });

        age = findViewById(R.id.age_text);
        name = findViewById(R.id.name_text);
        height = findViewById(R.id.height_text);
        kg = findViewById(R.id.kg_checktext);
        if(userManager != null){
            age.setText(userManager.getAge());
            name.setText(userManager.getUsername());
            height.setText(userManager.getHeight());
            kg.setText(userManager.getKg());
        }

        Button infochangeButton = findViewById(R.id.infochange_button);
        infochangeButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String heightValue = height.getText().toString();
                String kgValue = kg.getText().toString();

                if (Integer.parseInt(heightValue) <= 0 || Integer.parseInt(heightValue) > 300
                        || Integer.parseInt(kgValue) <= 0 || Integer.parseInt(kgValue) > 1000
                        || age.getText() == null || name.getText() == null
                        || height.getText() == null || kg.getText() == null) {
                    Toast.makeText(RegisterActivity.this, "잘못된 입력입니다.", Toast.LENGTH_SHORT).show();
                } else {
                    userManager.clearUser();
                    userManager.userSave(name.getText().toString(), height.getText().toString(), kg.getText().toString() , age.getText().toString() , sexCheck);
//                    userManager.saveBmrValue(bmrCheck , Integer.parseInt(heightValue) , Integer.parseInt(kgValue) , Integer.parseInt(age.getText().toString()));
                    setResult(RESULT_OK);
                    finish();
                }
            }
        });
        Button clearUserButton = findViewById(R.id.clearUserButton);
        clearUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userManager.clearUser();
                finish();
            }
        });
    }
}