package com.babycare.tips.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.babycare.tips.R;
import com.babycare.tips.activities.DevelopmentActivity;
import com.babycare.tips.activities.HowToActivity;
import com.babycare.tips.databinding.DevelopmentListItemBinding;
import com.babycare.tips.models.DevelopmentListModel;

import java.util.ArrayList;
import java.util.Objects;

public class DevelopmentListAdapter extends RecyclerView.Adapter<DevelopmentListAdapter.ViewHolder>{
    ArrayList<DevelopmentListModel> arrayList;
    Context context;
    String from;
    DevelopmentListItemBinding binding;

    public DevelopmentListAdapter(ArrayList<DevelopmentListModel> arrayList, Context context, String from) {
        this.arrayList = arrayList;
        this.context = context;
        this.from = from;
    }

    public DevelopmentListAdapter(ArrayList<DevelopmentListModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public DevelopmentListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DevelopmentListItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DevelopmentListAdapter.ViewHolder holder, int position) {
        DevelopmentListModel model = arrayList.get(holder.getAdapterPosition());
        String basic = "Your baby in ";
        if (Objects.equals(from, "how")){
            holder.binding.title.setText(model.getTitle());
        }else{
            holder.binding.title.setText(basic+model.getTitle());
        }
        holder.binding.bg.setBackgroundColor(model.getColor());
        holder.binding.item.setOnClickListener(v -> {
            Intent intent;
            if (!Objects.equals(from, "how")){
                intent = new Intent(context, DevelopmentActivity.class);
            }else{
                intent = new Intent(context, HowToActivity.class);
                intent.putExtra("color",model.getColor());
            }
            intent.putExtra("extra",model.getTitle());
            Activity activity = (Activity) context;
            activity.overridePendingTransition(0,0);
            activity.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        DevelopmentListItemBinding binding;
        public ViewHolder(@NonNull DevelopmentListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
