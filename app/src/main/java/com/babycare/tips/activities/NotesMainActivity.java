package com.babycare.tips.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.babycare.tips.R;
import com.babycare.tips.adapters.NotesAdapter;
import com.babycare.tips.databinding.ActivityMainBinding;
import com.babycare.tips.databinding.ActivityNotesMainBinding;
import com.babycare.tips.models.NotesModel;
import com.babycare.tips.models.NotesUserModel;
import com.babycare.tips.roomDatabase.Database;

import java.util.ArrayList;
import java.util.Collections;

public class NotesMainActivity extends AppCompatActivity {
    ActivityNotesMainBinding binding;
    String email;
    SharedPreferences sharedPreferences;
    ArrayList<NotesModel> arrayList;
    ArrayList<NotesUserModel> userArrayList;
    NotesAdapter adapter;
    long backClicked = 0;
    Database database;
    NotesUserModel userModel;
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityNotesMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        database = Database.getDatabase(this);
        arrayList = new ArrayList<>();
        userArrayList = new ArrayList<>();
        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email","null");
        binding.addNote.setOnClickListener(v -> startActivity(new Intent(NotesMainActivity.this,AddNoteActivity.class).putExtra("Extra",false)));
        setAdapter();
        binding.back.setOnClickListener(v -> onBackPressed());
//        binding.logout.setOnClickListener(v -> logout());
//        binding.appBarLayout.addOnOffsetChangedListener((appBarLayout, verticalOffset) -> {
//            if (Math.abs(verticalOffset) == appBarLayout.getTotalScrollRange()) {
//                binding.detailData.setVisibility(View.GONE);
//            } else {
//                binding.detailData.setVisibility(View.VISIBLE);
//            }
//        });
    }


    private void getData(){
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            userArrayList = new ArrayList<NotesUserModel>(database.dao().getAllUsers());
            arrayList = new ArrayList<NotesModel>(database.dao().getAllNotes(email));
            //Update the value background thread to UI thread
            handler.post(() -> {
                for (int i = 0;i<userArrayList.size();i++){
                    if (email.equals(userArrayList.get(i).getEmail())){
                        userModel = userArrayList.get(i);
                    }
                }
//                binding.name.setText(userModel.fName+" "+userModel.getlName());
//                binding.email.setText(userModel.getEmail());
//                binding.notes.setText(arrayList.size()+" notes");
                Collections.reverse(arrayList);
                if (arrayList.size()!=0){
                    Log.d(TAG, "getData: "+arrayList.size());
                    binding.animationView1.setVisibility(View.GONE);
                    setAdapter();
                } else{
                    binding.animationView1.setVisibility(View.VISIBLE);
                }
            });
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        arrayList.clear();
        getData();
    }

    private void setAdapter(){
        binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        adapter = new NotesAdapter(arrayList, NotesMainActivity.this,binding.animationView1);
        binding.recyclerView.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
//        Log.d(TAG, "onBackPressedTime: "+System.currentTimeMillis());
//        if (backClicked==0){
//            toast();
//            backClicked = System.currentTimeMillis();
//        }else {
//            if (System.currentTimeMillis()<backClicked+500){
//                finish();
//            }else {
//                toast();
//                backClicked = System.currentTimeMillis();
//            }
//        }
        super.onBackPressed();
        finish();
    }

    private void toast(){
        LayoutInflater inflater = getLayoutInflater();
        View layout = inflater.inflate(R.layout.custom_toast,
                findViewById(R.id.toast_layout_root));
        TextView toastTextView =  layout.findViewById(R.id.toastTextView);
        toastTextView.setText("Press back again to exit!");
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(layout);
        toast.show();
    }
}