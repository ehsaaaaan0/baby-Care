package com.babycare.tips.adapters;

import static com.babycare.tips.utils.Utils.convertDate;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.babycare.tips.R;
import com.babycare.tips.activities.AddNoteActivity;
import com.babycare.tips.databinding.DeleteQuestionPopupBinding;
import com.babycare.tips.databinding.NotesItemBinding;
import com.babycare.tips.models.NotesModel;
import com.babycare.tips.roomDatabase.Database;

import java.util.ArrayList;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.ViewHolder> {
    ArrayList<NotesModel> arrayList;
    Context context;
    NotesItemBinding binding;
    SharedPreferences sharedPreferences;
    LottieAnimationView animationView;
    String email;

    public NotesAdapter(ArrayList<NotesModel> arrayList, Context context, LottieAnimationView animationView) {
        this.arrayList = arrayList;
        this.context = context;
        this.animationView = animationView;
    }

    @NonNull
    @Override
    public NotesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = NotesItemBinding.inflate(LayoutInflater.from(context),parent,false);
        return new NotesAdapter.ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesAdapter.ViewHolder holder, int position) {
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.app_name), Context.MODE_PRIVATE);
        email = sharedPreferences.getString("email","");
        NotesModel model = arrayList.get(holder.getAdapterPosition());
        holder.binding.title.setText(model.getTitle());
        holder.binding.desc.setText(model.getNote());
        holder.binding.date.setText(convertDate(model.getDate()));
        holder.binding.delete.setOnClickListener(view -> showDialog(context,model));
        holder.binding.edit.setOnClickListener(v -> context.startActivity(new Intent(context, AddNoteActivity.class).putExtra("details",model).putExtra("Extra",true)));
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        NotesItemBinding binding;
        public ViewHolder(@NonNull NotesItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    public void showDialog(final Context context, NotesModel model) {
        Dialog dialog = new Dialog(context);
        DeleteQuestionPopupBinding bindingPopup = DeleteQuestionPopupBinding.inflate(LayoutInflater.from(context));
        dialog.setContentView(bindingPopup.getRoot());
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(
                Color.TRANSPARENT
        ));
        bindingPopup.cancel.setOnClickListener(v -> dialog.dismiss());
        bindingPopup.delete.setOnClickListener(v -> {
            dialog.dismiss();
            delete(model);
        });
        dialog.show();
    }

    private void delete(NotesModel model){
        Database db = Database.getDatabase(context);
        Handler handler = new Handler(Looper.getMainLooper());
        new Thread(() -> {
            db.dao().deleteNoteById(model.getId());
            handler.post(() -> {
                arrayList.remove(model);
                notifyDataSetChanged();
                if (arrayList.size()==0){
                    animationView.setVisibility(View.VISIBLE);
                }
            });
        }).start();
    }
}

