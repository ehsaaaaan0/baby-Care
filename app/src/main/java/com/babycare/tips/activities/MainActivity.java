package com.babycare.tips.activities;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.navigation.NavigationView;
import com.babycare.tips.GifImageView;
import com.babycare.tips.R;
import com.babycare.tips.databinding.ActivityMainBinding;
import com.babycare.tips.fragments.DevelopmentFragment;
import com.babycare.tips.fragments.HomeFragment;
import com.babycare.tips.fragments.HowToFragment;
import com.babycare.tips.models.UserModel;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    ActivityMainBinding binding;
    String email;
    UserModel userModel;

    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);


        userModel = (UserModel) getIntent().getSerializableExtra("userModel");

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, binding.drawerLayout, binding.barLayout.toolBarContainer, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        binding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        binding.navView.setNavigationItemSelectedListener(this);
        binding.navView.bringToFront();

        binding.barLayout.chipNavigation.setOnNavigationItemSelectedListener(item -> {
            int i = item.getItemId();
            for (int j = 0; j < binding.barLayout.chipNavigation.getMenu().size(); j++) {
                MenuItem menuItem = binding.barLayout.chipNavigation.getMenu().getItem(j);
                boolean isChecked = menuItem.getItemId() == item.getItemId();
                menuItem.setChecked(isChecked);
            }
            Fragment fragment = null;
            String extra = "";
            if (i==R.id.homeFrag){
                fragment = new HomeFragment();
                extra = "Home";
            }else if (i==R.id.devFrag){
                fragment = new DevelopmentFragment();
                extra = "Development";
            }else if (i==R.id.howFrag){
                fragment = new HowToFragment();
                extra = "How to?";
            }

            return loadFragment(fragment,extra);
        });

        getData();
    }

    private boolean loadFragment(Fragment fragment, String extra) {
        //switching fragment
        if (fragment != null) {
            Bundle bundle = new Bundle();
            bundle.putString("extra", extra);
            bundle.putString("email", email);
            bundle.putSerializable("user", userModel);
            binding.barLayout.title.setText(extra);
            fragment.setArguments(bundle);
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    private void getData(){
        View headerView = binding.navView.getHeaderView(0);
        TextView navUsername = headerView.findViewById(R.id.mName);
        TextView navEmail = headerView.findViewById(R.id.email);
        CircleImageView navProfile = headerView.findViewById(R.id.profile);
        navUsername.setText(userModel.getMotherName());
        if (!Objects.equals(userModel.getEncodedImage(), "null")){
            if( !userModel.getEncodedImage().equalsIgnoreCase("") ){
                byte[] b = Base64.decode(userModel.getEncodedImage(), Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
                navProfile.setImageBitmap(bitmap);
            }

        }
        navEmail.setText("");
        loadFragment(new HomeFragment(),"Home");
    }

// In MainActivity.java

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();

        if (itemId == R.id.home) {
            loadFragment(new HomeFragment(), "Home");
            binding.barLayout.chipNavigation.setSelectedItemId(R.id.homeFrag);
            closeDrawer();
            return false;
        } else if (itemId == R.id.development) {
            loadFragment(new DevelopmentFragment(), "Development");
            binding.barLayout.chipNavigation.setSelectedItemId(R.id.devFrag);
            closeDrawer();
            return false;
        } else if (itemId == R.id.howTo) {
            loadFragment(new HowToFragment(), "How to?");
            binding.barLayout.chipNavigation.setSelectedItemId(R.id.howFrag);
            closeDrawer();
            return false;
        } else if (itemId == R.id.note) {
            startActivity(new Intent(MainActivity.this, NotesMainActivity.class));
            closeDrawer();
            return false;
        } else if (itemId == R.id.profile) {
            intentToProfile();
            closeDrawer();
            return false;
        }

        return false;
    }

    private void closeDrawer(){
        binding.drawerLayout.close();
    }

    private void intentToProfile(){
        Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
        intent.putExtra("userModel",userModel);
        overridePendingTransition(0,0);
        startActivity(intent);
    }


    public void onBackPressed() {


        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.exit_popup);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(
                Color.TRANSPARENT
        ));
        ((TextView) dialog.findViewById(R.id.cancel)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        ((TextView) dialog.findViewById(R.id.rate)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                try {
                    MainActivity mainActivity = MainActivity.this;
                    mainActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.babycare.tips")));
                } catch (ActivityNotFoundException unused) {
                    MainActivity mainActivity2 = MainActivity.this;
                    mainActivity2.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=com.babycare.tips")));
                }
            }
        });
        ((TextView) dialog.findViewById(R.id.exit)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                dialog.dismiss();
                finishAffinity();
            }
        });
        dialog.show();
    }



}