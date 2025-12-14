package com.babycare.tips.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.babycare.tips.R;
import com.babycare.tips.databinding.ActivityAddNoteBinding;
import com.babycare.tips.models.NotesModel;
import com.babycare.tips.roomDatabase.Database;

import java.util.Objects;

public class AddNoteActivity extends AppCompatActivity {

    Database db;
    ActivityAddNoteBinding binding;
    String email;
    SharedPreferences sharedPreferences;
    NotesModel model;
    boolean editMode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddNoteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        editMode = getIntent().getBooleanExtra("Extra",false);
        db = Database.getDatabase(this);
        if (editMode){
            model = (NotesModel) getIntent().getSerializableExtra("details");
            binding.qTitle.setText(model.getTitle());
            binding.desc.setText(model.getNote());
        }
        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email","null");

        binding.back.setOnClickListener(view -> onBackPressed());

        binding.addNote.setOnClickListener(view -> {
            if (TextUtils.isEmpty(binding.qTitle.getText().toString())){
                toast("Please enter title");
            }else if (TextUtils.isEmpty(Objects.requireNonNull(binding.desc.getText()).toString())){
                toast("Please enter description");
            }else{
                addNote();
            }
        });

        new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        while (true) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    String text = binding.desc.getText().toString();
                                    binding.counter.setText(text.length()+"/2,000");
                                }
                            });
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
        ).start();
    }

    private void addNote() {
        NotesModel notesModel = new NotesModel(binding.qTitle.getText().toString(), Objects.requireNonNull(binding.desc.getText()).toString(),email,String.valueOf(System.currentTimeMillis()));
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            if (editMode){
                db.dao().deleteNoteById(model.getId());
                db.dao().insertNote(notesModel);
            }else{
                db.dao().insertNote(notesModel);
            }
            handler.post(() -> finish());
        }).start();
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
}