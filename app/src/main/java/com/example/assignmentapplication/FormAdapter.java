package com.example.assignmentapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FormAdapter extends RecyclerView.Adapter<FormAdapter.MyViewHolder> {
      Context context;
    List<FormModel> formModelList;

    public FormAdapter(Context context, List<FormModel> formModels) {
        this.context = context;
        this.formModelList = formModels;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_form_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        FormModel formModel = formModelList.get(position);
        holder.txtUniversityName.setText(formModel.getClgname());
        holder.txtName.setText(formModel.getName());
        holder.txtEmail.setText(formModel.getEmail());
        holder.txtNumber.setText(formModel.getNumber());

    }

    @Override
    public int getItemCount() {
        if (formModelList != null) {
            return formModelList.size();
        } else return 0;
    }
    public interface ItemClickListener {
        void onClick(UniversityModel universityModel);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView txtUniversityName,txtName,txtEmail,txtNumber;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            txtUniversityName = itemView.findViewById(R.id.txtUniversityName);
            txtName = itemView.findViewById(R.id.txtName);
            txtEmail = itemView.findViewById(R.id.txtEmail);
            txtNumber = itemView.findViewById(R.id.txtNumber);

        }
    }
}
