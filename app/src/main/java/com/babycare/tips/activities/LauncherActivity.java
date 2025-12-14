package com.babycare.tips.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;


import com.babycare.tips.R;
import com.babycare.tips.databinding.ActivityLauncherBinding;
import com.babycare.tips.models.UserModel;

//Dated: 17 aug 2022
public class LauncherActivity extends AppCompatActivity {

    ActivityLauncherBinding binding;

    UserModel userModel;

    Intent intent;
    String email;
    SharedPreferences sharedPreferences;
    String logInAs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLauncherBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        hideSystemUI();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }
        userModel = new UserModel();

        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        boolean login = sharedPreferences.getBoolean("login",false);
        String babyName = sharedPreferences.getString("babyName","");
        String babyGender = sharedPreferences.getString("babyGender","");
        String babyDob = sharedPreferences.getString("babyDob","");
        String motherName = sharedPreferences.getString("motherName","");
        String image = sharedPreferences.getString("image","");

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            if (login) {
                userModel = new UserModel(babyName,babyGender,babyDob,motherName,image,motherName);
                intent = new Intent(LauncherActivity.this, MainActivity.class);
                intent.putExtra("userModel",userModel);
                startActivity(intent);
                finish();
            }else{
                overridePendingTransition(0, 0);
                startActivity(new Intent(LauncherActivity.this, OnBoardoing.class));
                finish();
            }

        }, 2000);
    }


    public void hideSystemUI() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_LOW_PROFILE
                        | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_IMMERSIVE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
}