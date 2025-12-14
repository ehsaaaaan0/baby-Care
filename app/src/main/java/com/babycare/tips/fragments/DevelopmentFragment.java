package com.babycare.tips.fragments;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.babycare.tips.R;
import com.babycare.tips.adapters.DevelopmentListAdapter;
import com.babycare.tips.databinding.FragmentDevelopmentBinding;
import com.babycare.tips.models.DevelopmentListModel;

import java.util.ArrayList;

public class DevelopmentFragment extends Fragment {
    FragmentDevelopmentBinding binding;
    ArrayList<DevelopmentListModel> arrayList;
    Activity activity;
    ArrayList<Integer> colors;
    ArrayList<String> title;
    DevelopmentListAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentDevelopmentBinding.inflate(getLayoutInflater(), container, false);
        activity = getActivity();
        arrayList = new ArrayList<>();
        colors = new ArrayList<>();
        title = new ArrayList<>();
        getData();
        setupRecyclerView();

        return binding.getRoot();
    }

    private void setupRecyclerView() {
        adapter = new DevelopmentListAdapter(arrayList, activity,"dev");
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        binding.recyclerView.setAdapter(adapter);
    }

    private void getData() {
        DevelopmentListModel model = new DevelopmentListModel();
        addColors();
        addTitle();
        for (int i = 0; i < title.size(); i++) {
            model.setTitle(title.get(i));
            model.setColor(colors.get(i));
            arrayList.add(model);
            model = new DevelopmentListModel();
        }
    }

    private void addTitle() {
        title.add("First Week");
        title.add("Second Week");
        title.add("Third Week");
        title.add("Fourth Week");
        title.add("Fifth Week");
        title.add("Sixth Week");
        title.add("Seventh Week");
        title.add("Eighth Week");
        title.add("Second Month");
        title.add("Third Month");
        title.add("Fourth Month");
        title.add("Fifth Month");
        title.add("Sixth Month");
        title.add("Seventh Month");
        title.add("Eighth Month");
        title.add("Ninth Month");
        title.add("Tenth Month");
        title.add("Eleventh Month");
        title.add("Twelfth Month");
    }

    private void addColors() {
        if (activity != null) {
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
}