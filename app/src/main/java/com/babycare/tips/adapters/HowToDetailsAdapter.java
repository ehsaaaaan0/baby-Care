package com.babycare.tips.adapters;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.babycare.tips.R;
import com.babycare.tips.databinding.HowToDetailsItemBinding;

import java.util.ArrayList;

public class HowToDetailsAdapter extends RecyclerView.Adapter<HowToDetailsAdapter.ViewHolder> {
        ArrayList<String> arrayList;
        Context context;
        HowToDetailsItemBinding binding;

    public HowToDetailsAdapter(ArrayList<String> arrayList, Context context) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public HowToDetailsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = HowToDetailsItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull HowToDetailsAdapter.ViewHolder holder, int position) {

        if (arrayList.get(holder.getAdapterPosition()).length()>40)
        holder.binding.details.setText(arrayList.get(holder.getAdapterPosition()));
        else{
            Typeface typeface = ResourcesCompat.getFont(context, R.font.inter_bold);
            holder.binding.details.setTypeface(typeface);
            holder.binding.view.setVisibility(View.GONE);
            holder.binding.details.setText(arrayList.get(holder.getAdapterPosition()));
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        HowToDetailsItemBinding binding;
        public ViewHolder(@NonNull HowToDetailsItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
