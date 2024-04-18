package com.mbl.lottery.main;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.mbl.lottery.login.LoginActivity;
import com.mbl.lottery.model.EmployeeModel;
import com.mbl.lottery.utils.SharedPref;

public class SplashScreenActivity extends AppCompatActivity {
    /**
     * Duration of wait
     **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(() -> {
            /* Create an Intent that will start the Menu-Activity. */
            SharedPref sharedPref = new SharedPref(getBaseContext());
            EmployeeModel employeeModel = sharedPref.getEmployeeModel();
            Intent intent;
            if (employeeModel != null) {
                intent = new Intent(SplashScreenActivity.this, MainActivity.class);
            } else {
                intent = new Intent(SplashScreenActivity.this, LoginActivity.class);
            }
            startActivity(intent);
            finish();
        }, SPLASH_DISPLAY_LENGTH);
    }
}
