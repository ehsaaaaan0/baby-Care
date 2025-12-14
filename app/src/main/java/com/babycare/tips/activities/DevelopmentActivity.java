package com.babycare.tips.activities;

import static com.babycare.tips.utils.Utils.readTxtFile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;

import com.babycare.tips.R;
import com.babycare.tips.adapters.DevelopmentAdapter;
import com.babycare.tips.databinding.ActivityDevelopmentBinding;
import com.babycare.tips.models.DevelopmentModel;

import java.util.ArrayList;
import java.util.Locale;

public class DevelopmentActivity extends AppCompatActivity {
    ActivityDevelopmentBinding binding;
    ArrayList<DevelopmentModel> arrayList;
    DevelopmentAdapter adapter;
    ArrayList<Integer> colors;
    ArrayList<Drawable> icons;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDevelopmentBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        arrayList = new ArrayList<>();
        colors = new ArrayList<>();
        icons = new ArrayList<>();
        String title = getIntent().getStringExtra("extra");
        binding.title.setText(title);

        setupRecyclerView();
        getFile(title.toLowerCase(Locale.ROOT).replace(" ","_")+".txt");

        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setupRecyclerView(){
        adapter = new DevelopmentAdapter(arrayList,this);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);
    }

    private void getFile(String file){
        ArrayList<String> list = new ArrayList<>();
        addColors();
        DevelopmentModel model = new DevelopmentModel();
        list = readTxtFile(file,this);
        binding.content.setText(list.get(0));
        int j = 0;
        for (int i = 1;i<list.size();i++){
            if (i%2!=0){
                model.setTitle(list.get(i));
                String[] str = list.get(i).toLowerCase(Locale.ROOT).split(" ");
                model.setIcon(addIcons(str[0]));
            }else{
                model.setContent(list.get(i));
                model.setColor(colors.get(j));
                j++;
                arrayList.add(model);
                adapter.notifyDataSetChanged();
                model = new DevelopmentModel();
            }
        }
    }

    private void addColors(){
            colors.add(getColor(R.color.color1));
            colors.add(getColor(R.color.color2));
            colors.add(getColor(R.color.color3));
            colors.add(getColor(R.color.color4));
            colors.add(getColor(R.color.color5));
            colors.add(getColor(R.color.color6));
            colors.add(getColor(R.color.color7));
            colors.add(getColor(R.color.color8));
            colors.add(getColor(R.color.color9));
            colors.add(getColor(R.color.color10));
        }

    @SuppressLint("UseCompatLoadingForDrawables")
    private Drawable addIcons(String name){
        return getDrawable(getResources().getIdentifier(name,"drawable",getPackageName()));
    }
}