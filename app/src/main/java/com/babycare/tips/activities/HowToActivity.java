package com.babycare.tips.activities;

import static com.babycare.tips.utils.Utils.readTxtFile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.os.Bundle;

import com.babycare.tips.R;
import com.babycare.tips.adapters.HowToDetailsAdapter;
import com.babycare.tips.databinding.ActivityHowToBinding;

import java.util.ArrayList;
import java.util.Objects;

public class HowToActivity extends AppCompatActivity {
    ActivityHowToBinding binding;
    ArrayList<String> list;
    HowToDetailsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHowToBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String title = getIntent().getStringExtra("extra");
        list = new ArrayList<>();
        binding.title.setText(title);
        setTitleImage(title);
        binding.back.setOnClickListener(v -> onBackPressed());
    }

    @SuppressLint({"UseCompatLoadingForDrawables", "SetTextI18n"})
    private void setTitleImage(String title){
        list = new ArrayList<>();
        if (Objects.equals(title, "Sleeping")){
            binding.titleImage.setImageDrawable(getDrawable(R.drawable.sleepreal));
            list = readTxtFile("sleeping.txt",this);
        }else if (Objects.equals(title, "Nappy hygiene")){
            binding.titleImage.setImageDrawable(getDrawable(R.drawable.babynapping));
            list = readTxtFile("nappy.txt",this);
        }else if (Objects.equals(title, "Bathing your baby")){
            binding.titleImage.setImageDrawable(getDrawable(R.drawable.babbybatting));
            list = readTxtFile("bathing.txt",this);
        }else if (Objects.equals(title, "Feeding your Baby")){
            binding.titleImage.setImageDrawable(getDrawable(R.drawable.babyfeeding));
            list = readTxtFile("feeding.txt",this);
        }else{
            binding.titleImage.setImageDrawable(getDrawable(R.drawable.babyclotting));
            list = readTxtFile("clothing.txt",this);
        }
        setupRecyclerView();
    }

    private void setupRecyclerView(){
        adapter = new HowToDetailsAdapter(list,this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
    }
}