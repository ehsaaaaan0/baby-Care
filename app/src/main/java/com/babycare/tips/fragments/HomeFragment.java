package com.babycare.tips.fragments;

import static android.content.ContentValues.TAG;
import static com.babycare.tips.utils.Utils.getAge;
import static com.babycare.tips.utils.Utils.readTxtFile;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.babycare.tips.R;
import com.babycare.tips.adapters.DevelopmentAdapter;
import com.babycare.tips.databinding.FragmentHomeBinding;
import com.babycare.tips.models.DevelopmentModel;
import com.babycare.tips.models.UserModel;

import java.util.ArrayList;
import java.util.Locale;

public class HomeFragment extends Fragment {
    FragmentHomeBinding binding;
    Activity activity;
    String email;
    UserModel userModel;
    SharedPreferences sharedPreferences;
    ArrayList<DevelopmentModel> arrayList;
    DevelopmentAdapter adapter;
    int days,month,years;
    ArrayList<Integer> colors;
    ArrayList<Drawable> icons;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(getLayoutInflater(),container,false);
        activity = getActivity();
        arrayList = new ArrayList<>();
        colors = new ArrayList<>();
        icons = new ArrayList<>();
        if (activity!=null)sharedPreferences = activity.getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email","null");
        assert getArguments() != null;
        userModel = (UserModel) getArguments().getSerializable(
                "user");
        setupRecyclerView();
        getData();

        return binding.getRoot();
    }

    private void getData(){
        if (userModel!=null){
            binding.bName.setText(userModel.getBabyName());
            int[] age = getAge(userModel.getBabyDob());
            days = age[0];
            month = age[1];
            years = age[2];
            String gender = "He is ";
            if (userModel.getBabyGender().equals("Baby Girl")){
                gender = "She is ";
            }
            checkAge();
            if (years==0){
                if (month==0)
                    binding.age.setText(gender+days+" days old");
                else
                    binding.age.setText(gender+month+" months & "+days+" days old");
            }else
                binding.age.setText(gender+years+" years "+month+" months & "+days+" days old");

        }
    }
    private void checkAge(){
        addColors();
        if (years==0){
            if (month<=1){
                if (days<=7)getFile("first_week.txt");
                else if (days<=14)getFile("second_week.txt");
                else if (days<=21)getFile("third_week.txt");
                else if (days<=28)getFile("fourth_week.txt");
                else if (month==1&&days<=7)getFile("fifth_week.txt");
                else if (month==1&&days<=14)getFile("sixth_week.txt");
                else if (month==1&&days<=21)getFile("seventh_week.txt");
                else if (month==1&&days<=28)getFile("eighth_week.txt");
                else getFile("second_month.txt");
            }else if (month==2)getFile("second_month.txt");
            else if (month==3)getFile("third_month.txt");
            else if (month==4)getFile("fourth_month.txt");
            else if (month==5)getFile("fifth_month.txt");
            else if (month==6)getFile("sixth_month.txt");
            else if (month==7)getFile("seventh_month.txt");
            else if (month==8)getFile("eighth_month.txt");
            else if (month==9)getFile("ninth_month.txt");
            else if (month==10)getFile("tenth_month.txt");
            else if (month==11)getFile("eleventh_month.txt");
            else getFile("twelfth_month.txt");
        }else{
            binding.animationView1.setVisibility(View.VISIBLE);
            binding.text.setVisibility(View.GONE);
        }
    }

    private void getFile(String file){
        ArrayList<String> list = new ArrayList<>();
        DevelopmentModel model = new DevelopmentModel();
        if (activity!=null)list = readTxtFile(file,activity);
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

    private void setupRecyclerView(){
        adapter = new DevelopmentAdapter(arrayList,activity);
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerView.setAdapter(adapter);
    }

    private void addColors(){
        if (activity!=null){
            colors.add(activity.getColor(R.color.color1));
            colors.add(activity.getColor(R.color.color2));
            colors.add(activity.getColor(R.color.color3));
            colors.add(activity.getColor(R.color.color4));
            colors.add(activity.getColor(R.color.color5));
            colors.add(activity.getColor(R.color.color6));
            colors.add(activity.getColor(R.color.color7));
            colors.add(activity.getColor(R.color.color8));
            colors.add(activity.getColor(R.color.color9));
            colors.add(activity.getColor(R.color.color10));
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private Drawable addIcons(String name){
        Drawable drawable = null;
        try {
            if (activity!=null){
                drawable = activity.getDrawable(getResources().getIdentifier(name,"drawable",activity.getPackageName()));
            }
        }catch (Exception e){
            Log.d(TAG, "addIcons: "+e);
        }
        return drawable;
    }
}