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

import java.nio.ByteBuffer;
import java.util.List;

public class UniversityAdapter extends RecyclerView.Adapter<UniversityAdapter.MyViewHolder> {
      Context context;
    List<UniversityModel> universityModels;
    ItemClickListener itemClickListener;

    public UniversityAdapter(Context context, List<UniversityModel> universityModels, ItemClickListener itemClickListener) {
        this.context = context;
        this.universityModels = universityModels;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layout_university_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        UniversityModel universityModelList = universityModels.get(position);
        holder.txtUniversityName.setText(universityModelList.getUniversity_Name());
        byte[] decodedByte = Base64.decode(universityModelList.getUniversity_Logo(), 0);
        Bitmap b =  BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        holder.imgUniversityLogo.setImageBitmap(b);
        holder.btnApply.setOnClickListener(v -> {
            itemClickListener.onClick(universityModels.get(holder.getAdapterPosition()));
            notifyDataSetChanged();
        });

    }

    @Override
    public int getItemCount() {
        if (universityModels != null) {
            return universityModels.size();
        } else return 0;
    }
    public interface ItemClickListener {
        void onClick(UniversityModel universityModel);
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView imgUniversityLogo;
        TextView txtUniversityName;
        AppCompatButton btnApply;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            imgUniversityLogo = itemView.findViewById(R.id.imgUniversityLogo);
            txtUniversityName = itemView.findViewById(R.id.txtUniversityName);
            btnApply = itemView.findViewById(R.id.btnApply);
        }
    }
}
