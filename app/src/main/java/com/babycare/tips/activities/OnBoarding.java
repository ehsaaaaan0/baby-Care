package com.babycare.tips.activities;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.babycare.tips.models.UserModel;
import com.babycare.tips.R;
import com.babycare.tips.databinding.ActivityOnBoardingBinding;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;

public class OnBoarding extends AppCompatActivity {
    ActivityOnBoardingBinding binding;
    int exit = 0;
    String babyName, babyGender, babyDob, motherName, encodedImage;
    Uri uri;
    boolean image = false;
    int check = 0;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOnBoardingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        hideSystemUI();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().getAttributes().layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_SHORT_EDGES;
        }

        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        binding.openDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                binding.layout.setVisibility(View.VISIBLE);
            }
        });
        binding.dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                binding.layout.setVisibility(View.VISIBLE);
            }
        });
        binding.chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                binding.layout.setVisibility(View.VISIBLE);
            }
        });
        binding.datePicker.setMaxDate(new Date().getTime());
        binding.ok.setOnClickListener(view -> {
            int mYear = binding.datePicker.getYear();
            int mMonth = binding.datePicker.getMonth() + 1;
            int mDay = binding.datePicker.getDayOfMonth();
            binding.dob.setText(mDay + "/" + mMonth + "/" + mYear);
            binding.layout.setVisibility(View.GONE);
        });

        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == 0) {
                    babyName = binding.bName.getText().toString().trim();
                    if (TextUtils.isEmpty(babyName)) {
                        binding.bName.setError("Please Enter Baby Name");
                        binding.bName.requestFocus();
                    } else {
                        Animation rightSwipe = AnimationUtils.loadAnimation(OnBoarding.this, R.anim.anim_left);
                        binding.back.setVisibility(View.VISIBLE);
                        check++;
                        binding.babyNameLayout.setVisibility(View.GONE);
                        binding.babyGenderlayout.setVisibility(View.VISIBLE);
                        binding.babyGenderlayout.startAnimation(rightSwipe);
                    }
                } else if (check == 1) {
                    Animation rightSwipe = AnimationUtils.loadAnimation(OnBoarding.this, R.anim.anim_left);
                    int genId = binding.gender.getCheckedRadioButtonId();
                    RadioButton radioButton = findViewById(genId);
                    babyGender = radioButton.getText().toString();
                    check++;
                    binding.babyGenderlayout.setVisibility(View.GONE);
                    binding.babyDOblayout.setVisibility(View.VISIBLE);
                    binding.babyDOblayout.startAnimation(rightSwipe);
                } else if (check == 2) {
                    babyDob = binding.dob.getText().toString().trim();
                    if (TextUtils.isEmpty(babyDob)) {
                        binding.dob.setError("please Enter baby Date of birth");
                    } else {
                        Animation rightSwipe = AnimationUtils.loadAnimation(OnBoarding.this, R.anim.anim_left);
                        check++;
                        binding.babyDOblayout.setVisibility(View.GONE);
                        binding.selectImageLayout.setVisibility(View.VISIBLE);
                        binding.selectImageLayout.startAnimation(rightSwipe);
                    }
                } else if (check == 3) {
                    if (!image) {
                        encodedImage = "null";
                    }
                    Animation rightSwipe = AnimationUtils.loadAnimation(OnBoarding.this, R.anim.anim_left);
                    binding.signupText.setText("Finish");
                    binding.selectImageLayout.setVisibility(View.GONE);
                    check++;
                    binding.motherNameLayout.setVisibility(View.VISIBLE);
                    binding.motherNameLayout.startAnimation(rightSwipe);
                } else if (check == 4) {
                    motherName = binding.mName.getText().toString().trim();
                    if (TextUtils.isEmpty(motherName)) {
                        motherName = babyName;
                        signUpNow();
                    } else {
                        signUpNow();
                    }
                }
            }
        });


        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (check == 1) {
                    Animation rightSwipe = AnimationUtils.loadAnimation(OnBoarding.this, R.anim.anim_right);
                    binding.back.setVisibility(View.GONE);
                    binding.babyGenderlayout.setVisibility(View.GONE);
                    check--;
                    binding.babyNameLayout.setVisibility(View.VISIBLE);
                    binding.babyNameLayout.startAnimation(rightSwipe);

                } else if (check == 2) {

                    Animation rightSwipe = AnimationUtils.loadAnimation(OnBoarding.this, R.anim.anim_right);
                    binding.babyGenderlayout.setVisibility(View.VISIBLE);
                    binding.babyDOblayout.setVisibility(View.GONE);
                    binding.babyGenderlayout.startAnimation(rightSwipe);
                    check--;
                } else if (check == 3) {
                    Animation rightSwipe = AnimationUtils.loadAnimation(OnBoarding.this, R.anim.anim_right);
                    check--;
                    binding.babyDOblayout.setVisibility(View.VISIBLE);
                    binding.selectImageLayout.setVisibility(View.GONE);
                    binding.babyDOblayout.startAnimation(rightSwipe);

                } else if (check == 4) {

                    Animation rightSwipe = AnimationUtils.loadAnimation(OnBoarding.this, R.anim.anim_right);
                    binding.signupText.setText("Next");
                    binding.selectImageLayout.setVisibility(View.VISIBLE);
                    check--;
                    binding.motherNameLayout.setVisibility(View.GONE);
                    binding.selectImageLayout.startAnimation(rightSwipe);
                } else {
                    toast("Already at the End! \n Press again to Go Back");
                    if (exit == 2) {
                        overridePendingTransition(0, 0);
                        startActivity(new Intent(OnBoarding.this, OnBoardoing.class));
                        finish();
                    } else {
                        exit++;
                    }
                }
            }
        });

        binding.profile.setOnClickListener(view -> ImagePicker.Companion.with(OnBoarding.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start());
    }

    private void signUpNow() {
        sharedPreferences.edit().putString("babyName", babyName).apply();
        sharedPreferences.edit().putString("babyGender", babyGender).apply();
        sharedPreferences.edit().putString("babyDob", babyDob).apply();
        sharedPreferences.edit().putString("motherName", motherName).apply();
        sharedPreferences.edit().putString("image", encodedImage).apply();
        sharedPreferences.edit().putBoolean("login", true).apply();
        UserModel model = new UserModel(babyName, babyGender, babyDob, motherName, encodedImage, motherName);
        overridePendingTransition(0, 0);
        startActivity(new Intent(OnBoarding.this, MainActivity.class)
                .putExtra("userModel", model));
        finish();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            assert data != null;
            uri = data.getData();
            //  val uri: Uri = data?.data!!
            Bitmap imageBitmap = null;
            try {
                imageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
            byte[] b = baos.toByteArray();
            encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
            binding.profile.setImageBitmap(imageBitmap);
            binding.profileIcon.setVisibility(View.GONE);
            image = true;
            // Use Uri object instead of File to avoid storage permissions
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            toast("Error");
        } else {
            toast("Task Cancelled");
        }
    }

    private void toast(String message) {
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                findViewById(R.id.toast_layout_root));
        TextView toastTextView = layout.findViewById(R.id.toastTextView);
        toastTextView.setText(message);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }


    @Override
    public void onBackPressed() {
        {
            if (check == 1) {

                Animation rightSwipe = AnimationUtils.loadAnimation(OnBoarding.this, R.anim.anim_right);
                binding.back.setVisibility(View.GONE);
                binding.babyGenderlayout.setVisibility(View.GONE);
                check--;
                binding.babyNameLayout.setVisibility(View.VISIBLE);
                binding.babyNameLayout.startAnimation(rightSwipe);

            } else if (check == 2) {

                Animation rightSwipe = AnimationUtils.loadAnimation(OnBoarding.this, R.anim.anim_right);
                binding.babyGenderlayout.setVisibility(View.VISIBLE);
                binding.babyDOblayout.setVisibility(View.GONE);
                binding.babyGenderlayout.startAnimation(rightSwipe);
                check--;
            } else if (check == 3) {
                Animation rightSwipe = AnimationUtils.loadAnimation(OnBoarding.this, R.anim.anim_right);
                check--;
                binding.selectImageLayout.setVisibility(View.GONE);
                binding.babyDOblayout.setVisibility(View.VISIBLE);
                binding.babyDOblayout.startAnimation(rightSwipe);

            } else if (check == 4) {

                Animation rightSwipe = AnimationUtils.loadAnimation(OnBoarding.this, R.anim.anim_right);
                binding.signupText.setText("Next");
                check--;
                binding.motherNameLayout.setVisibility(View.GONE);
                binding.selectImageLayout.setVisibility(View.VISIBLE);
                binding.selectImageLayout.startAnimation(rightSwipe);
            } else {
                overridePendingTransition(0, 0);
                startActivity(new Intent(OnBoarding.this, OnBoardoing.class));
                finish();
            }
        }
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