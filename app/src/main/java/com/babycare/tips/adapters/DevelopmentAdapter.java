package com.babycare.tips.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.babycare.tips.R;
import com.babycare.tips.databinding.DevelopmentItemBinding;
import com.babycare.tips.models.DevelopmentModel;

import java.util.ArrayList;

public class DevelopmentAdapter extends RecyclerView.Adapter<DevelopmentAdapter.ViewHolder> {
    ArrayList<DevelopmentModel> arrayList;
    Context context;
    DevelopmentItemBinding binding;

    public DevelopmentAdapter(ArrayList<DevelopmentModel> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }


    @NonNull
    @Override
    public DevelopmentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DevelopmentItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull DevelopmentAdapter.ViewHolder holder, int position) {
        DevelopmentModel model = arrayList.get(holder.getAdapterPosition());

        holder.binding.title.setText(model.getTitle());
        holder.binding.content.setText(model.getContent());
        holder.binding.bg.setBackgroundColor(model.getColor());
        holder.binding.icon.setImageDrawable(model.getIcon());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        DevelopmentItemBinding binding;
        public ViewHolder(@NonNull DevelopmentItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
