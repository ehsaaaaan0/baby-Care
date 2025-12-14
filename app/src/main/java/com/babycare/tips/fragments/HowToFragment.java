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
import com.babycare.tips.databinding.FragmentHowToBinding;
import com.babycare.tips.models.DevelopmentListModel;

import java.util.ArrayList;

public class HowToFragment extends Fragment {
    FragmentHowToBinding binding;
    Activity activity;
    ArrayList<Integer> colors;
    ArrayList<String> title;
    DevelopmentListAdapter adapter;
    ArrayList<DevelopmentListModel> arrayList;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentHowToBinding.inflate(getLayoutInflater(), container, false);
        activity = getActivity();
        arrayList = new ArrayList<>();
        colors = new ArrayList<>();
        title = new ArrayList<>();

        getData();
        setupRecyclerView();

        return binding.getRoot();
    }

    private void setupRecyclerView() {
        adapter = new DevelopmentListAdapter(arrayList, activity,"how");
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
        title.add("Sleeping");
        title.add("Nappy hygiene");
        title.add("Bathing your baby");
        title.add("Feeding your Baby");
        title.add("Clothing and Layette");
    }

    private void addColors() {
        if (activity != null) {
            colors.add(activity.getColor(R.color.color1));
            colors.add(activity.getColor(R.color.color6));
            colors.add(activity.getColor(R.color.color3));
            colors.add(activity.getColor(R.color.color7));
            colors.add(activity.getColor(R.color.color9));
        }
    }
}