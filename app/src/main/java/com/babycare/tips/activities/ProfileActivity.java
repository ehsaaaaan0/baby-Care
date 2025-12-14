package com.babycare.tips.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.babycare.tips.R;
import com.babycare.tips.databinding.ActivityProfileBinding;
import com.babycare.tips.models.UserModel;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class ProfileActivity extends AppCompatActivity {
    ActivityProfileBinding binding;

    UserModel userModel;
    String babyName,babyGender,babyDob,motherName, encodedImage;
    Uri uri;
    boolean image = false;
    int check=0;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityProfileBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        userModel = (UserModel) getIntent().getSerializableExtra("userModel");

        if (!Objects.equals(userModel.getEncodedImage(), "null")){
            if( !userModel.getEncodedImage().equalsIgnoreCase("") ){
                byte[] b = Base64.decode(userModel.getEncodedImage(), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                binding.profile.setImageBitmap(bitmap);
            }
        }
        encodedImage = userModel.getEncodedImage();
        binding.bName.setText(userModel.getBabyName());
        binding.dob.setText(userModel.getBabyDob());
        binding.mName.setText(userModel.getMotherName());
        if (Objects.equals(userModel.getBabyGender(), "Baby Boy")){
            binding.boy.setChecked(true);
            binding.girl.setChecked(false);
        }else{
            binding.boy.setChecked(false);
            binding.girl.setChecked(true);
        }
        if (Objects.equals(userModel.getBabyName(), userModel.getMotherName())){
            binding.mName.setText("");
        }


        binding.signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int genId=binding.gender.getCheckedRadioButtonId();
                RadioButton radioButton = findViewById(genId);
                babyGender = radioButton.getText().toString();
                sharedPreferences.edit().putString("babyName",binding.bName.getText().toString()).apply();
                sharedPreferences.edit().putString("babyGender",babyGender).apply();
                sharedPreferences.edit().putString("babyDob",binding.dob.getText().toString()).apply();
                sharedPreferences.edit().putString("motherName",binding.mName.getText().toString().trim()).apply();
                sharedPreferences.edit().putString("image",encodedImage).apply();
                sharedPreferences.edit().putBoolean("login",true).apply();
                toast("Profile Updated");
                check++;
            }
        });



        binding.profile.setOnClickListener(view -> ImagePicker.Companion.with(ProfileActivity.this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start());
        binding.openDatePicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                binding.layout.setVisibility(View.VISIBLE);
            }
        });
        binding.dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                binding.layout.setVisibility(View.VISIBLE);
            }
        });
        binding.chooseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                binding.layout.setVisibility(View.VISIBLE);
            }
        });
        binding.layout.setOnClickListener(view -> binding.layout.setVisibility(View.GONE));
        binding.cancel.setOnClickListener(view -> binding.layout.setVisibility(View.GONE));
        binding.datePicker.setMaxDate(new Date().getTime());
        binding.ok.setOnClickListener(view -> {
            int mYear = binding.datePicker.getYear();
            int mMonth = binding.datePicker.getMonth()+1;
            int mDay = binding.datePicker.getDayOfMonth();
            binding.dob.setText(mDay+"/"+mMonth+"/"+mYear);
            binding.layout.setVisibility(View.GONE);
        });
        binding.back.setOnClickListener(view -> onBackPressed());
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            //Image Uri will not be null for RESULT_OK
            assert data != null;
            uri=data.getData();
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



    private void toast(String message){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                findViewById(R.id.toast_layout_root));
        TextView toastTextView =  layout.findViewById(R.id.toastTextView);
        toastTextView.setText(message);
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }


    @Override
    public void onBackPressed() {
        if (check!=0){
            boolean login = sharedPreferences.getBoolean("login",false);
            String babyName = sharedPreferences.getString("babyName","");
            String babyGender = sharedPreferences.getString("babyGender","");
            String babyDob = sharedPreferences.getString("babyDob","");
            String motherName = sharedPreferences.getString("motherName","");
            String image = sharedPreferences.getString("image","");
            userModel = new UserModel(babyName,babyGender,babyDob,motherName,image,motherName);
            startActivity(new Intent(ProfileActivity.this, MainActivity.class)
                    .putExtra("userModel",userModel));
        }else{
            finish();
        }
    }
}